package org.github.sriki77.edgesh.command;

import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.github.sriki77.edgesh.data.ContextNode;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;
import org.reflections.Reflections;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

public final class CommandRegistry {
    private static final CommandRegistry registry = new CommandRegistry();

    private LinkedList<Command> commands = new LinkedList<>();

    private CommandRegistry() {
        registerCommands();
    }

    public static CommandRegistry instance() {
        return registry;
    }

    public void register(Command command) {
        commands.add(command);
    }

    public void handle(ShellCommand command, ShellContext context, PrintWriter out) {
        for (Command c : context.currentNode().commands()) {
            final ShellCommand handles = c.handles();
            if (handles == null) {
                if (c.handle(command, context, out)) {
                    break;
                }
                continue;
            }

            if (command == handles) {
                if (c.handlesParamPrefix() == null) {
                    if (c.handle(command, context, out)) {
                        break;
                    }
                    continue;
                }
                if (c.handlesParamPrefix().equals(c.handles().paramPrefix())) {
                    if (c.handle(command, context, out)) {
                        break;
                    }
                }
            }
        }
    }

    public void registerCommands() {
        final Reflections reflections = new Reflections("org.github.sriki77.edgesh");
        reflections.getTypesAnnotatedWith(EdgeMgmtCommand.class).forEach(
                c -> {
                    try {
                        register((Command) c.newInstance());
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to load command: " + c, e);
                    }
                });
    }

    public boolean init(ShellContext context, PrintWriter out) {
        if (!checkConnect(context, out)) {
            return false;
        }
        bindCommands(context);
        return true;
    }

    private void bindCommands(ShellContext context) {
        final ContextNode root = context.rootNode();
        ArrayList<Command> allEntityCommands = new ArrayList<>();
        for (Command command : commands) {
            final EdgeEntity entity = command.applicableTo();
            if (entity == EdgeEntity.ALL) {
                allEntityCommands.add(command);
                continue;
            }
            root.bindTo(entity, command);
        }
        allEntityCommands.forEach(c -> root.bindTo(EdgeEntity.ALL, c));
    }

    private boolean checkConnect(ShellContext context, PrintWriter out) {
        out.println("Contacting Edge Mgmt Server...");
        final Response response = context.request().get("/organizations");
        final int statusCode = response.statusCode();
        if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_FORBIDDEN) {
            out.println("Connected to Apigee Mgmt Server: " + context);
            return true;
        }
        out.println("Failed to connect to given Apigee Mgmt Server: " + context);
        out.println(response.statusLine());
        out.println(response.headers());
        out.println(response.prettyPrint());
        return false;
    }

}

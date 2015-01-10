package org.github.sriki77.edgesh.command;

import org.github.sriki77.edgesh.data.ShellContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class CommandLoop {
    private final ShellContext context;
    private final BufferedReader in;
    private final PrintWriter out;

    public CommandLoop(ShellContext context) {
        this.context = context;
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out, true);
    }

    public void start() throws IOException {
        final CommandRegistry registry = CommandRegistry.instance();
        if (!registry.init(context, out)) {
            return;
        }
        while (true) {
            String command = prompt();
            if (shouldExit(command)) {
                break;
            }
            registry.handle(command, context, out);
            out.flush();
        }
        exit();
    }

    private void exit() {
        out.println("Bye...");
        out.flush();
    }

    private boolean shouldExit(String command) {
        return "q".equals(command) ||
                "quit".equals(command) || "exit".equals(command);
    }

    private String prompt() throws IOException {
        out.print("$ ");
        out.flush();
        return in.readLine();
    }
}

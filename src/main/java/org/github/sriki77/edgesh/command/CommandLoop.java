package org.github.sriki77.edgesh.command;

import org.github.sriki77.edgesh.data.ShellContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class CommandLoop {
    public static final String PROMPT_STRING = "$ ";
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
            final ShellCommand command = prompt();
            switch (command){
                case QUIT:
                    out.println("Bye...");
                    out.flush();
                    return;
                default:
                    registry.handle(command,context,out);
            }
            out.flush();
        }
    }

    private ShellCommand prompt() throws IOException {
        out.print(PROMPT_STRING);
        out.flush();
        return ShellCommand.toCommand(in.readLine());
    }
}

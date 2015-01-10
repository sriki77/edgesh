package org.github.sriki77.edgesh.command;

import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

public interface Command {
    boolean handle(String command, ShellContext context, PrintWriter out);
}

package org.github.sriki77.edgesh.command;

import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

public interface Command {
    boolean handle(ShellCommand command, ShellContext context, PrintWriter out);

    EdgeEntity applicableTo();

    ShellCommand handles();

    default String handlesParamPrefix() {
        return null;
    }

}

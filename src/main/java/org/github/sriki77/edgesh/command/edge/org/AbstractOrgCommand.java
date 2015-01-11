package org.github.sriki77.edgesh.command.edge.org;

import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

public abstract class AbstractOrgCommand implements Command {
    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (!context.containsOnly()) {
            return false;
        }
        return handleOrgCommand(command, context, out);
    }

    protected abstract boolean handleOrgCommand(ShellCommand command, ShellContext context, PrintWriter out);
}

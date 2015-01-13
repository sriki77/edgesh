package org.github.sriki77.edgesh.command.edge.org;

import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.data.EdgeEntity.ORG;

public abstract class AbstractOrgCommand implements Command {
    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (!context.containsOnly(ORG)) {
            return false;
        }
        return handleOrgCommand(command, context, out);
    }

    @Override
    public EdgeEntity applicableTo() {
        return ORG;
    }

    protected abstract boolean handleOrgCommand(ShellCommand command, ShellContext context, PrintWriter out);
}

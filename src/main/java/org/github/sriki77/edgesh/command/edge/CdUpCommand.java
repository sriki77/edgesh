package org.github.sriki77.edgesh.command.edge;

import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.printMsg;
import static org.github.sriki77.edgesh.command.ShellCommand.CD;

@EdgeMgmtCommand
public class CdUpCommand implements Command {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (command.dotParam()) {
            return true;
        }
        if (!command.dotdotParam()) {
            return false;
        }
        if (context.atRoot()) {
            return true;
        }
        final EdgeEntity entity = context.currentNode().entity();
        printMsg("Moved out of " + entity + ":" + context.get(entity), out);
        context.moveUp();
        return true;
    }

    @Override
    public EdgeEntity applicableTo() {
        return EdgeEntity.ALL;
    }

    @Override
    public ShellCommand handles() {
        return CD;
    }
}

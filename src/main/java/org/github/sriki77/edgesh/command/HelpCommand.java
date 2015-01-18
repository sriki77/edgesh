package org.github.sriki77.edgesh.command;

import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.printMsg;
import static org.github.sriki77.edgesh.command.ShellCommand.HELP;
import static org.github.sriki77.edgesh.command.ShellCommand.INVALID;
import static org.github.sriki77.edgesh.data.EdgeEntity.ALL;

@EdgeMgmtCommand
public class HelpCommand implements Command {
    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        printMsg("Valid Commands:", out);
        printMsg("---------------", out);
        for (ShellCommand sc : ShellCommand.values()) {
            if (sc != INVALID) {
                printMsg(sc.toString(), out);
            }
        }
        printMsg("---------------", out);
        return false;
    }

    @Override
    public EdgeEntity applicableTo() {
        return ALL;
    }

    @Override
    public ShellCommand handles() {
        return HELP;
    }
}

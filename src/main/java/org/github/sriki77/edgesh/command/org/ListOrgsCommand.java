package org.github.sriki77.edgesh.command.org;

import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

@EdgeMgmtCommand
public class ListOrgsCommand implements Command {
    @Override
    public boolean handle(String command, ShellContext context, PrintWriter out) {
        return false;
    }
}

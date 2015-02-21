package org.github.sriki77.edgesh.command;

import org.apache.commons.io.IOUtils;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.printError;
import static org.github.sriki77.edgesh.EdgeUtil.printMsg;
import static org.github.sriki77.edgesh.command.ShellCommand.MAN;
import static org.github.sriki77.edgesh.command.ShellCommand.toCommand;
import static org.github.sriki77.edgesh.data.EdgeEntity.ALL;

@EdgeMgmtCommand
public class ManCommand implements Command {
    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        final String param = command.param();
        final InputStream manPage = this.getClass().getResourceAsStream("/man/" + param + ".man.txt");
        if (manPage == null) {
            printMsg(toCommand(param).toString(), out);
            return true;
        }
        printMsg("---------------", out);
        try {
            printMsg(IOUtils.toString(manPage), out);
        } catch (IOException e) {
            printError(e, out);
        }
        printMsg("---------------", out);
        return true;
    }

    @Override
    public EdgeEntity applicableTo() {
        return ALL;
    }

    @Override
    public ShellCommand handles() {
        return MAN;
    }
}

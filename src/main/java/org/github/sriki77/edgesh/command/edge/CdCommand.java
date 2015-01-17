package org.github.sriki77.edgesh.command.edge;

import com.jayway.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;
import java.util.LinkedList;

import static org.github.sriki77.edgesh.EdgeUtil.handleResponse;
import static org.github.sriki77.edgesh.command.ShellCommand.CD;

@EdgeMgmtCommand
public class CdCommand extends AbstractCommand {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (command.dotParam() || command.dotdotParam()) {
            return false;
        }
        final LinkedList<Pair<EdgeEntity, String>> pairs = entityValues(command);
        final Response response = context.contextRequest().get(buildUrl(pairs));
        if (!handleResponse(buildErrMsg("failed to change to ",pairs), out, response)) {
            return true;
        }
        for (Pair<EdgeEntity, String> p : pairs) {
            context.setAndMoveDown(p.getLeft(), p.getRight());
        }
        return true;
    }

    @Override
    public ShellCommand handles() {
        return CD;
    }
}

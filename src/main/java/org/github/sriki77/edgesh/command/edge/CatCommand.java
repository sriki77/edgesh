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
import static org.github.sriki77.edgesh.command.ShellCommand.CAT;

@EdgeMgmtCommand
public class CatCommand extends AbstractCommand {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (command.dotParam() || command.dotdotParam()) {
            return false;
        }
        final LinkedList<Pair<EdgeEntity, String>> pairs = entityValues(command);
        final Response response = request(command,context).get(buildUrl(pairs));
        handleResponse(buildErrMsg("failed get details of: ", pairs), out, response);
        return true;
    }

    @Override
    public ShellCommand handles() {
        return CAT;
    }
}

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
import static org.github.sriki77.edgesh.data.EdgeEntity.*;

@EdgeMgmtCommand
public class CatCommand extends AbstractCommand {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (command.dotParam() || command.dotdotParam()) {
            return false;
        }

        final LinkedList<Pair<EdgeEntity, String>> pairs = entityValues(command);
        Response response;
        if (endsWithEntity(pairs)) {
            response = handleCatEntity(command, context, pairs);
        }else {
            response = request(command, context).get(buildUrl(pairs));
        }
        handleResponse(buildErrMsg("failed get details of: ", pairs), out, response);
        return true;
    }

    private Response handleCatEntity(ShellCommand command, ShellContext context, LinkedList<Pair<EdgeEntity, String>> pairs) {
        Response response;
        pairs.remove(pairs.size() - 1);
        response = request(command, context).get(buildUrl(pairs)+lastSuffix(command.param()));
        return response;
    }

    private String lastSuffix(String param) {
        final int loc = param.lastIndexOf('/');
        String slash = (loc == -1 ? "" : "/");
        return slash + toEntity(param.substring(loc + 1)).prefix();
    }

    private boolean endsWithEntity(LinkedList<Pair<EdgeEntity, String>> pairs) {
        final Pair<EdgeEntity, String> lastPair = pairs.get(pairs.size() - 1);
        return lastPair.getLeft() == ORG && toEntity(lastPair.getRight()) != INVALID;
    }

    @Override
    public ShellCommand handles() {
        return CAT;
    }
}

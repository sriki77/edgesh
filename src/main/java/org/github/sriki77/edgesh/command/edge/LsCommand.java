package org.github.sriki77.edgesh.command.edge;

import com.jayway.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.github.sriki77.edgesh.EdgeUtil;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.ContextNode;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringJoiner;

import static org.github.sriki77.edgesh.EdgeUtil.handleResponse;
import static org.github.sriki77.edgesh.command.ShellCommand.LS;
import static org.github.sriki77.edgesh.data.EdgeEntity.toEntity;

@EdgeMgmtCommand
public class LsCommand extends AbstractCommand {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (command.dotParam()) {
            listEntities(context.currentNode(), out);
            return true;
        }
        if (command.dotdotParam()) {
            ContextNode node = context.currentNode();
            listEntities(context.atRoot() ? node : node.parent(), out);
            return false;
        }
        final LinkedList<Pair<EdgeEntity, String>> pairs = entityValues(command, 1);
        final String lastSuffix = lastSuffix(command.param());
        final Response response = request(command, context).get(buildUrl(pairs) + lastSuffix);
        handleResponse("failed get details of: " + command.param(), out, response);
        return true;
    }

    private String lastSuffix(String param) {
        final int loc = param.lastIndexOf('/');
        String slash = (loc == -1 ? "" : "/");
        return slash + toEntity(param.substring(loc + 1)).prefix();
    }

    protected void listEntities(ContextNode node, PrintWriter out) {
        StringJoiner joiner = new StringJoiner(",");
        for (EdgeEntity entity : node.childEntities()) {
            joiner.add(entity.name());
        }
        EdgeUtil.printMsg("Entities: " + joiner.toString(), out);
    }

    @Override
    public ShellCommand handles() {
        return LS;
    }
}

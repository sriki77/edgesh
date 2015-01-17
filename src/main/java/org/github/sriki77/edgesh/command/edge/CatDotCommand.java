package org.github.sriki77.edgesh.command.edge;

import com.jayway.restassured.response.Response;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.ContextNode;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.handleResponse;
import static org.github.sriki77.edgesh.command.ShellCommand.CD;

@EdgeMgmtCommand
public class CatDotCommand extends AbstractCommand {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (!command.dotParam() && !command.dotdotParam()) {
            return false;
        }
        ContextNode node = context.currentNode();
        if (command.dotdotParam()) {
            if (!context.atRoot()) {
                node = context.currentNode().parent();
            }
        }
        final Response response = context.contextRequest(node).get();
        handleResponse("failed to get details of: " + command.param(), out, response);
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

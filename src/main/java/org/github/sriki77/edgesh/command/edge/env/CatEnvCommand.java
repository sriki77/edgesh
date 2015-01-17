package org.github.sriki77.edgesh.command.edge.env;

import com.jayway.restassured.response.Response;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.handleResponse;
import static org.github.sriki77.edgesh.command.ShellCommand.CAT;

@EdgeMgmtCommand
public class CatEnvCommand extends AbstractEnvCommand {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        String envName = context.currentNode().value();
        String orgName = context.currentNode().parent().value();
        final Response response = context.requestSpecification().get("/o/" + orgName + "/e/" + envName);
        handleResponse("failed get details of: " + envName, out, response);
        return true;
    }

    @Override
    public ShellCommand handles() {
        return CAT;
    }
}

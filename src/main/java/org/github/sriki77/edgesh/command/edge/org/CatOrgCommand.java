package org.github.sriki77.edgesh.command.edge.org;

import com.jayway.restassured.response.Response;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.handleResponse;
import static org.github.sriki77.edgesh.command.ShellCommand.CAT;

@EdgeMgmtCommand
public class CatOrgCommand extends AbstractOrgCommand {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        String orgName = context.currentNode().value();
        final Response response = context.requestSpecification().get("/o/" + orgName);
        handleResponse("failed get details of: " + orgName, out, response);
        return true;
    }

    @Override
    public ShellCommand handles() {
        return CAT;
    }
}

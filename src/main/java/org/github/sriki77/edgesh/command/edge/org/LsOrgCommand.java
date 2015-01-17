package org.github.sriki77.edgesh.command.edge.org;

import com.jayway.restassured.response.Response;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.errorMsg;
import static org.github.sriki77.edgesh.EdgeUtil.handleResponse;
import static org.github.sriki77.edgesh.command.ShellCommand.LS;

@EdgeMgmtCommand
public class LsOrgCommand extends AbstractOrgCommand {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (command.dotParam()) {
            listEntities(out);
        }
        switch (EdgeEntity.toEntity(command.param())) {
            case ENV:
                listEnv(context, out);
            default:
                errorMsg("Invalid entity under org: " + command.param());
        }
        return true;
    }

    private void listEnv(ShellContext context, PrintWriter out) {
        String orgName = context.currentNode().value();
        final Response response = context.requestSpecification().get("/o/" + orgName + "/environments");
        handleResponse("failed get details of environments for org: " + orgName, out, response);
    }

    @Override
    public ShellCommand handles() {
        return LS;
    }
}

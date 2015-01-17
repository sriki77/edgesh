package org.github.sriki77.edgesh.command.edge.org;

import com.jayway.restassured.response.Response;
import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.handleResponse;
import static org.github.sriki77.edgesh.command.ShellCommand.CD;

@EdgeMgmtCommand
public class ChangeOrgCommand implements Command {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (command.dotParam() || command.dotdotParam()) {
            return false;
        }

        final boolean valid = validateOrg(context, command.param(), out);

        if (!valid) {
            return true;
        }
        context.setAndMakeCurrent(EdgeEntity.ORG, command.param());
        return true;
    }

    private boolean validateOrg(ShellContext context, String orgName, PrintWriter out) {
        final Response response = context.requestSpecification().get("/o/" + orgName);
        return handleResponse(
                "changed to org: " + orgName,
                "failed to change to org: " + orgName, out, response);
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

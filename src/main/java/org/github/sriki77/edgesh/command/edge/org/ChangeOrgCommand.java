package org.github.sriki77.edgesh.command.edge.org;

import com.jayway.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.errorMsg;
import static org.github.sriki77.edgesh.EdgeUtil.printError;
import static org.github.sriki77.edgesh.EdgeUtil.printMsg;
import static org.github.sriki77.edgesh.command.ShellCommand.CD;

@EdgeMgmtCommand
public class ChangeOrgCommand implements Command {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (command != CD) {
            return false;
        }

        final String param = command.param();
        if (StringUtils.isBlank(param)) {
            out.println(errorMsg("no org specified as parameter."));
            return true;
        }

        final boolean valid = validateOrg(context, param, out);
        if (!valid) {
            return true;
        }
        context.setAndMakeCurrent(EdgeEntity.ORG, param);
        return true;
    }

    private boolean validateOrg(ShellContext context, String orgName, PrintWriter out) {
        final Response response = context.requestSpecification().get("/o/" + orgName);
        final int statusCode = response.statusCode();
        if (statusCode == HttpStatus.SC_OK) {
            printMsg("changed to org: " + orgName, out, response);
            return true;
        }
        printError("failed to change to org: " + orgName, out, response);
        return false;
    }




    @Override
    public EdgeEntity applicableTo() {
        return EdgeEntity.ALL;
    }
}

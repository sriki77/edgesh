package org.github.sriki77.edgesh.command.edge.org;

import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.printBody;
import static org.github.sriki77.edgesh.EdgeUtil.printError;
import static org.github.sriki77.edgesh.command.ShellCommand.CAT;

@EdgeMgmtCommand
public class CatOrgCommand extends AbstractOrgCommand {
    @Override
    protected boolean handleOrgCommand(ShellCommand command, ShellContext context, PrintWriter out) {
        if (command != CAT) {
            return false;
        }
        printOrg(context,context.currentNode().value(),out);
        return true;
    }

    private boolean printOrg(ShellContext context, String orgName, PrintWriter out) {
        final Response response = context.requestSpecification().get("/o/" + orgName);
        final int statusCode = response.statusCode();
        if (statusCode == HttpStatus.SC_OK) {
            printBody(out, response);
            return true;
        }
        printError("failed get details of: " + orgName, out, response);
        return false;
    }
}

package org.github.sriki77.edgesh.command.edge;

import com.jayway.restassured.response.Response;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.handleResponse;
import static org.github.sriki77.edgesh.command.ShellCommand.CAT;

@EdgeMgmtCommand
public class CatCommand extends AbstractCommand {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        if (command.dotParam() || command.dotdotParam()) {
            return false;
        }
        final Response response = context.contextRequest().get(buildUrl(entityValues(command)));
        handleResponse(buildErrMsg("failed get details of: ", entityValues(command)), out, response);
        return true;
    }

    @Override
    public ShellCommand handles() {
        return CAT;
    }
}

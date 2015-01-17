package org.github.sriki77.edgesh.command.edge.env;

import com.jayway.restassured.response.Response;
import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.command.EdgeMgmtCommand;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.io.PrintWriter;

import static org.github.sriki77.edgesh.EdgeUtil.handleResponse;
import static org.github.sriki77.edgesh.command.ShellCommand.CD;
import static org.github.sriki77.edgesh.data.EdgeEntity.ENV;
import static org.github.sriki77.edgesh.data.EdgeEntity.ORG;

@EdgeMgmtCommand
public class ChangeEnvCommand implements Command {

    @Override
    public boolean handle(ShellCommand command, ShellContext context, PrintWriter out) {
        final String envName = command.paramSuffix();
        final boolean valid = validateEnv(context, envName, out);
        if (!valid) {
            return true;
        }
        context.setAndMakeCurrent(ENV, envName);
        return true;
    }

    @Override
    public EdgeEntity applicableTo() {
        return ORG;
    }

    private boolean validateEnv(ShellContext context, String envName, PrintWriter out) {
        String orgName = context.get(ORG);
        final Response response = context.requestSpecification().get("/o/" + orgName + "/e/" + envName);
        return handleResponse(
                "changed to env: " + envName,
                "failed to change to env: " + envName, out, response);
    }

    @Override
    public ShellCommand handles() {
        return CD;
    }

    @Override
    public String handlesParamPrefix() {
        return EdgeEntity.ENV.name();
    }
}

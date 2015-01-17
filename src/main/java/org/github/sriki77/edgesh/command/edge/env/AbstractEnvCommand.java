package org.github.sriki77.edgesh.command.edge.env;

import org.github.sriki77.edgesh.EdgeUtil;
import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.data.EdgeEntity;

import java.io.PrintWriter;
import java.util.StringJoiner;

import static org.github.sriki77.edgesh.data.EdgeEntity.ENV;

public abstract class AbstractEnvCommand implements Command {

    protected EdgeEntity[] entities = {};

    @Override
    public EdgeEntity applicableTo() {
        return ENV;
    }

    protected void listEntities(PrintWriter out) {
        StringJoiner joiner = new StringJoiner(",");
        for (EdgeEntity entity : entities) {
            joiner.add(entity.name());
        }
        EdgeUtil.printMsg("Entities: " + joiner.toString(), out);
    }

}

package org.github.sriki77.edgesh.command.edge.org;

import org.github.sriki77.edgesh.EdgeUtil;
import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.data.EdgeEntity;

import java.io.PrintWriter;
import java.util.StringJoiner;

import static org.github.sriki77.edgesh.data.EdgeEntity.APIS;
import static org.github.sriki77.edgesh.data.EdgeEntity.APPS;
import static org.github.sriki77.edgesh.data.EdgeEntity.AUD;
import static org.github.sriki77.edgesh.data.EdgeEntity.COMP;
import static org.github.sriki77.edgesh.data.EdgeEntity.DEVS;
import static org.github.sriki77.edgesh.data.EdgeEntity.ENV;
import static org.github.sriki77.edgesh.data.EdgeEntity.OAUTH;
import static org.github.sriki77.edgesh.data.EdgeEntity.ORG;
import static org.github.sriki77.edgesh.data.EdgeEntity.PRODS;
import static org.github.sriki77.edgesh.data.EdgeEntity.REP;
import static org.github.sriki77.edgesh.data.EdgeEntity.ROLE;
import static org.github.sriki77.edgesh.data.EdgeEntity.STAT;
import static org.github.sriki77.edgesh.data.EdgeEntity.VAULT;

public abstract class AbstractOrgCommand implements Command {

    protected EdgeEntity[] entities = {ENV, APIS, PRODS, APPS, COMP, DEVS, AUD, STAT, OAUTH, REP, ROLE, VAULT};

    @Override
    public EdgeEntity applicableTo() {
        return ORG;
    }

    protected void listEntities(PrintWriter out) {
        StringJoiner joiner = new StringJoiner(",");
        for (EdgeEntity entity : entities) {
            joiner.add(entity.name());
        }
        EdgeUtil.printMsg("Entities: " + joiner.toString(), out);
    }

}

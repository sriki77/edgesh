package org.github.sriki77.edgesh.command.edge;

import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.EdgeEntity;
import org.github.sriki77.edgesh.data.ShellContext;

import java.util.LinkedList;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.github.sriki77.edgesh.data.EdgeEntity.ORG;
import static org.github.sriki77.edgesh.data.EdgeEntity.ROOT;
import static org.github.sriki77.edgesh.data.EdgeEntity.toEntity;

public abstract class AbstractCommand implements Command {

    protected String buildErrMsg(String errMsgPrefix, LinkedList<Pair<EdgeEntity, String>> pairs) {
        final String msg = pairs.stream().map(p -> p.getLeft() + ":" + p.getRight()).collect(Collectors.joining("/"));
        return errMsgPrefix + msg;
    }

    protected String buildUrl(LinkedList<Pair<EdgeEntity, String>> pairs) {
        StringJoiner joiner = new StringJoiner("/");
        for (Pair<EdgeEntity, String> pair : pairs) {
            if (pair.getLeft() != ROOT) {
                joiner.add(pair.getLeft().prefix());
            }
            joiner.add(suffix(pair.getRight()));
        }
        return "/" + joiner.toString();
    }

    protected LinkedList<Pair<EdgeEntity, String>> entityValues(ShellCommand command) {
        return entityValues(command, 0);
    }

    protected LinkedList<Pair<EdgeEntity, String>> entityValues(ShellCommand command, int indexFromLast) {
        final LinkedList<Pair<EdgeEntity, String>> entityValuePairs = new LinkedList<>();
        final String[] parts = paramParts(command);
        for (int i = 0; i < parts.length - indexFromLast; i++) {
            String part = parts[i];
            final String prefix = prefix(part);
            final String suffix = suffix(part);
            final Pair<EdgeEntity, String> edgeEntityValuePair
                    = ImmutablePair.of(prefix == null ? ORG : toEntity(prefix), suffix);
            entityValuePairs.add(edgeEntityValuePair);
        }
        return entityValuePairs;
    }

    private String[] paramParts(ShellCommand command) {
        String param = command.param();
        param = param.startsWith("/") ? param.substring(1) : param;
        return param.split("/");
    }

    private String suffix(String param) {
        final String[] parts = param.split(":");
        if (parts.length == 1) {
            return param;
        }
        return parts[1];
    }

    private String prefix(String param) {
        final String[] parts = param.split(":");
        if (parts.length == 1) {
            return null;
        }
        return parts[0];
    }

    protected RequestSpecification request(ShellCommand command, ShellContext context) {
        return command.paramAtRoot()?context.contextFromRoot():context.contextRequest();
    }
    @Override
    public EdgeEntity applicableTo() {
        return EdgeEntity.ALL;
    }
}

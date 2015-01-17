package org.github.sriki77.edgesh.command.edge;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.github.sriki77.edgesh.command.Command;
import org.github.sriki77.edgesh.command.ShellCommand;
import org.github.sriki77.edgesh.data.EdgeEntity;

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
        final LinkedList<Pair<EdgeEntity, String>> entityValuePairs = new LinkedList<>();
        final String[] parts = command.param().split("/");
        for (String part : parts) {
            final String prefix = prefix(part);
            final String suffix = suffix(part);
            final Pair<EdgeEntity, String> edgeEntityValuePair
                    = ImmutablePair.of(prefix == null ? ORG : toEntity(prefix), suffix);
            entityValuePairs.add(edgeEntityValuePair);
        }
        return entityValuePairs;
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

    @Override
    public EdgeEntity applicableTo() {
        return EdgeEntity.ALL;
    }
}

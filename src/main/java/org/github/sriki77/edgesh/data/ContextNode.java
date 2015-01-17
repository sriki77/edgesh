package org.github.sriki77.edgesh.data;

import org.github.sriki77.edgesh.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.github.sriki77.edgesh.data.EdgeEntity.ROOT;

public class ContextNode {
    private EdgeEntity entity = ROOT;
    private String value;
    private ArrayList<Command> commands = new ArrayList<>();
    private ArrayList<ContextNode> children = new ArrayList<>();
    private ContextNode parent;

    public ContextNode() {
    }

    public ContextNode(EdgeEntity entity) {
        this.entity = entity;
    }

    public ContextNode addChild(ContextNode child) {
        child.parent = this;
        this.children.add(child);
        return child;
    }

    public void bindTo(EdgeEntity entity, Command command) {
        if (EdgeEntity.ALL == entity || this.entity == entity) {
            commands.add(command);
        }
        for (ContextNode child : children) {
            child.bindTo(entity, command);
        }
    }

    public ContextNode parent() {
        return parent;
    }

    public EdgeEntity entity() {
        return entity;
    }

    public String value() {
        return value;
    }

    public ContextNode nodeOfType(EdgeEntity entity) {
        if (this.entity == entity) {
            return this;
        }

        for (ContextNode child : children) {
            return child.nodeOfType(entity);
        }

        throw new IllegalArgumentException(String.format("Node with entity type %s not found.", entity));
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String retVal = "" + entity;
        if (value != null) {
            retVal += ":" + value;
        }
        return retVal;
    }

    public List<Command> commands() {
        return commands;
    }

    public List<EdgeEntity> childEntities() {
        return children.stream().map(c -> c.entity).collect(Collectors.toList());
    }
}

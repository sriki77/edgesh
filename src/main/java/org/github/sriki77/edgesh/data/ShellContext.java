package org.github.sriki77.edgesh.data;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static com.jayway.restassured.RestAssured.config;
import static com.jayway.restassured.RestAssured.with;
import static org.github.sriki77.edgesh.EdgeUtil.logVerbose;
import static org.github.sriki77.edgesh.data.EdgeEntity.ORG;

public class ShellContext {

    private final String mgmtUrl;
    private final String userName;
    private final String password;

    private final HashMap<EdgeEntity, String> entityMap = new HashMap<>();
    private final ContextNode root = new ContextNode();
    private ContextNode current = root;

    public ShellContext(String mgmtUrl, String userName, String password) {
        this.mgmtUrl = mgmtUrl;
        this.userName = userName;
        this.password = password;
        buildContextTree();
    }

    public ContextNode rootNode() {
        return root;
    }

    public ContextNode currentNode() {
        return current;
    }

    public void setCurrent(ContextNode current) {
        this.current = current;
        this.entityMap.clear();
        ContextNode node = current;
        while (node != null) {
            entityMap.put(node.entity(), node.value());
            node = node.parent();
        }
    }

    private void buildContextTree() {
        ContextNode node = root.addChild(new ContextNode(ORG));
        buildContextTree(node, ORG);
    }

    private void buildContextTree(ContextNode node, EdgeEntity entity) {
        for (EdgeEntity child : entity.getChildren()) {
            buildContextTree(node.addChild(new ContextNode(child)), child);
        }
    }

    public RequestSpecification request() {
        return requestWithURI(mgmtUrl);
    }

    public RequestSpecification contextRequest() {
        return contextRequest(current);
    }

    public RequestSpecification contextRequest(ContextNode node) {
        return requestWithURI(buildContextUri(mgmtUrl, node));
    }

    private String buildContextUri(String mgmtUrl, ContextNode current) {
        if (current == null) {
            return mgmtUrl;
        }
        mgmtUrl = buildContextUri(mgmtUrl, current.parent());
        final String prefix = current.entity().prefix();
        final String value = entityMap.get(current.entity());
        return prefix.length() == 0 ? mgmtUrl : mgmtUrl + prefix + "/" + value + "/";
    }


    public static String urlFragmentFor(EdgeEntity entity, String value) {
        final String prefix = entity.prefix();
        return prefix.length() == 0 ? "" : prefix + "/" + value;
    }

    private RequestSpecification requestWithURI(String uri) {
        RestAssured.reset();
        config = config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
        RequestSpecification request = with().baseUri(uri).auth().preemptive()
                .basic(userName, password);
        if (logVerbose()) {
            request = request.log().path();
        }
        return request;
    }


    public String get(EdgeEntity entity) {
        return entityMap.get(entity);
    }

    @Override
    public String toString() {
        return mgmtUrl;
    }

    public ContextNode setAndMakeCurrent(EdgeEntity entity, String value) {
        final ContextNode node = root.nodeOfType(entity);
        node.setValue(value);
        setCurrent(node);
        return node;
    }

    public void reset() {
        entityMap.clear();
        current = root;
    }

    public void moveUp() {
        final ContextNode parent = current.parent();
        setCurrent(parent == null ? current : parent);
    }

    public boolean atRoot() {
        return current == root;
    }

    public ContextNode setAndMoveDown(EdgeEntity entity, String value) {
        final ContextNode node = current.nodeOfType(entity);
        node.setValue(value);
        setCurrent(node);
        return node;
    }
}

package org.github.sriki77.edgesh.data;

import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static com.jayway.restassured.RestAssured.config;
import static com.jayway.restassured.RestAssured.reset;
import static com.jayway.restassured.RestAssured.with;
import static org.github.sriki77.edgesh.data.EdgeEntity.ENV;
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
        node.addChild(new ContextNode(ENV));
    }

    public RequestSpecification requestSpecification() {
        reset();
        config = config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
        return with().baseUri(mgmtUrl).auth().preemptive()
                .basic(userName, password);
    }

    public boolean containsOnly(EdgeEntity... entities) {
        final HashMap<EdgeEntity, String> entityMapClone
                = (HashMap<EdgeEntity, String>) entityMap.clone();
        boolean entitiesPresent = true;
        for (EdgeEntity entity : entities) {
            entitiesPresent &= (entityMapClone.remove(entity) != null);
        }
        return entitiesPresent && entityMapClone.isEmpty();
    }

    public void set(EdgeEntity entity, String value) {
        entityMap.put(entity, value);
    }


    public void remove(EdgeEntity entity) {
        entityMap.remove(entity);
    }

    public void clearEntities() {
        entityMap.clear();
    }

    public String get(EdgeEntity entity) {
        return entityMap.get(entity);
    }

    @Override
    public String toString() {
        return mgmtUrl;
    }

    public ContextNode nodeOfType(EdgeEntity entity) {
        return root.nodeOfType(entity);
    }

    public void setAndMakeCurrent(EdgeEntity entity, String value) {
        final ContextNode node = root.nodeOfType(entity);
        node.setValue(value);
        setCurrent(node);
    }
}

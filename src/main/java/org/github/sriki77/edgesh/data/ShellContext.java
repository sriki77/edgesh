package org.github.sriki77.edgesh.data;

import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.specification.RequestSpecification;

import static com.jayway.restassured.RestAssured.config;
import static com.jayway.restassured.RestAssured.reset;
import static com.jayway.restassured.RestAssured.with;

public class ShellContext {
    private final String mgmtUrl;
    private final String userName;
    private final String password;

    public ShellContext(String mgmtUrl, String userName, String password) {
        this.mgmtUrl = mgmtUrl;
        this.userName = userName;
        this.password = password;
    }

    public RequestSpecification requestSpecification() {
        reset();
        config = config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
        return with().baseUri(mgmtUrl).auth().preemptive()
                .basic(userName, password);
    }

    @Override
    public String toString() {
        return mgmtUrl;
    }
}

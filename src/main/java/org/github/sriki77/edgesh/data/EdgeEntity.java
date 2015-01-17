package org.github.sriki77.edgesh.data;

import org.apache.commons.lang3.StringUtils;

public enum EdgeEntity {
    ROOT(""), ORG("o"), ENV("e"), APIS("apis"), PRODS("apiproducts"), APPS("apps"),
    COMP("companies"), DEVS("developers"), AUD("audits"), STAT("stat"),
    OAUTH("oauth2"), REP("reports"), ROLE("userrole"), VAULT("vault"),
    ALL("all"), INVALID("invalid");

    private final String prefix;

    EdgeEntity(String prefix) {
        this.prefix = prefix;
    }

    public static EdgeEntity toEntity(String input) {
        if (StringUtils.isBlank(input)) {
            return ROOT;
        }
        try {
            return EdgeEntity.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }

    public String prefix() {
        return prefix;
    }
}

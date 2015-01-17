package org.github.sriki77.edgesh.data;

import org.apache.commons.lang3.StringUtils;

public enum EdgeEntity {
    ROOT, ORG, ENV, APIS, PRODS, APPS, COMP, DEVS, AUD, STAT, OAUTH, REP, ROLE, VAULT, ALL, INVALID;

    public static EdgeEntity toEntity(String input) {
        if (StringUtils.isBlank(input)) {
            return INVALID;
        }
        try {
            return EdgeEntity.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }
}

package org.github.sriki77.edgesh.data;

import org.apache.commons.lang3.StringUtils;

public enum EdgeEntity {
    DEP("deployments"), DEBUG("debugsessions"),
    RESOURCE("resourcefiles"),
    REV("revisions", DEP, DEBUG, RESOURCE),
    KVMS("keyvaluemaps"),
    MASK("maskconfigs"), APIS("apis", REV, MASK, KVMS), ATTR("attributes"),
    PRODS("apiproducts", ATTR), KEYS("keys"), APPS("apps", KEYS),
    APPFAMILIES("appfamilies"), DEVS("developers", APPS,APPFAMILIES),
    COMPS("companies", APPS, APPFAMILIES, DEVS), CACHE("caches"),
    OAUTH1ACCESSTOKENS("oauth1/accesstokens"), OAUTH1REQUESTTOKENS("oauth1/requesttokens"),
    OAUTH1VERIFIERS("oauth1/verifiers"), AUTHCODES("oauth2/authorizationcodes"),
    TOKENS("oauth2/accesstokens"), REPORTS("reports"), USERS("users"),
    PERM("permissions"),
    ROLES("userroles", USERS,PERM),ENTRIES("entries"), VAULTS("vaults",ENTRIES), CERT("certs"),
    STAT("stats/apis"),VHOST("virtualhosts"),
    ALL("all"), INVALID("invalid"), TS("targetservers"), KEYSTORE("keystores", CERT),
    ENV("e", TS, CACHE, KVMS, KEYSTORE,VAULTS,VHOST),
    ORG("o", ENV, APIS, PRODS, APPS, COMPS, DEVS, KVMS,
            AUTHCODES, TOKENS,
            OAUTH1ACCESSTOKENS, OAUTH1REQUESTTOKENS, OAUTH1VERIFIERS, REPORTS, ROLES,VAULTS),
    ROOT("", ORG);

    private final String prefix;
    private final EdgeEntity[] children;

    EdgeEntity(String prefix, EdgeEntity... children) {
        this.prefix = prefix;
        this.children = children;
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

    public EdgeEntity[] getChildren() {
        return children;
    }
}

package org.github.sriki77.edgesh.command;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CommandLoopAllEntitiesTest extends CommandTestBase {

    @Test
    public void shouldCatEnvEntities() throws Exception {
        assertThat(execCommand("cat sriki77/env:test/cache:testCache")
                .contains("overflowToDisk"), is(true));
        assertThat(execCommand("cat sriki77/env:test/kvms:story_map")
                .contains("story_map"), is(true));
        assertThat(execCommand("cat sriki77/env:test/keystores:freetrial")
                .contains("23-apigee-wildcard.apigee.net.crt"), is(true));
        assertThat(execCommand("cat sriki77/env:test/keystores:freetrial/certs:23-apigee-wildcard.apigee.net.crt")
                .contains("subjectAlternativeNames"), is(true));
        assertThat(execCommand("ls sriki77/env:test/vaults")
                .contains("[ ]"), is(true));
        assertThat(execCommand("cat sriki77/env:test/vhosts:secure")
                .contains("hostAliases"), is(true));
    }


    @Test
    public void shouldCatOrgEntities() throws Exception {
        assertThat(execCommand("cat sriki77/kvms:__apigee__.vaults")
                .contains("__apigee__.vaults"), is(true));
    }

    @Test
    public void shouldCatAPIEntities() throws Exception {
        assertThat(execCommand("ls sriki77/apis")
                .contains("Mirror-Reflect-KVM-Policy-Tests"), is(true));
        assertThat(execCommand("ls sriki77/apis:Mirror-Reflect-KVM-Policy-Tests/kvms")
                .contains("[ ]"), is(true));
        assertThat(execCommand("ls sriki77/apis:forecastweatherapi/revs")
                .contains("10"), is(true));
        assertThat(execCommand("ls sriki77/apis:forecastweatherapi/revs:10/deps")
                .contains("message-processor"), is(true));
        assertThat(execCommand("ls sriki77/apis:fwa-test/revs:1/resources")
                .contains("js_moment_test.js"), is(true));
        assertThat(execCommand("ls sriki77/apis:fwa-test/masks")
                .contains("[ ]"), is(true));
    }

    @Test
    public void shouldCatProductEntities() throws Exception {
        assertThat(execCommand("ls sriki77/prods")
                .contains("joke-product"), is(true));
        assertThat(execCommand("cat sriki77/prods:joke-product")
                .contains("proxies"), is(true));
        assertThat(execCommand("ls sriki77/prods:joke-product/attrs")
                .contains("internal"), is(true));
    }

    @Test
    public void shouldCatAppsEntities() throws Exception {
        assertThat(execCommand("ls sriki77/apps")
                .contains("e3ce273c-0870-4e60-8013-8174e4b8781b"), is(true));
        assertThat(execCommand("cat sriki77/apps:b32225d0-9828-4e76-8acb-8dca31bc7870")
                .contains("joke-product"), is(true));

    }

    @Test
    public void shouldCatCompDevAppsEntities() throws Exception {
        assertThat(execCommand("ls sriki77/comps")
                .contains("[ ]"), is(true));
        assertThat(execCommand("cat sriki77/devs:jamesbond@mi7.com")
                .contains("Joker"), is(true));
        assertThat(execCommand("cat sriki77/devs:jamesbond@mi7.com/apps:Joker")
                .contains("b32225d0-9828-4e76-8acb-8dca31bc7870"), is(true));
        assertThat(execCommand("cat sriki77/devs:jamesbond@mi7.com/apps:Joker/keys:ZgdfGTvSsGjCXVJ7V9cvKdz9lXlKXLbs")
                .contains("En5QTXgAOXi1MuRj"), is(true));
        assertThat(execCommand("ls sriki77/devs:jamesbond@mi7.com/appfamilies")
                .contains("[ ]"), is(true));

    }

    @Test
    public void shouldCatOauthEntities() throws Exception {
        assertThat(execCommand("ls sriki77/OAUTH1ACCESSTOKENS")
                .contains("0"), is(true));
        assertThat(execCommand("ls sriki77/OAUTH1REQUESTTOKENS")
                .contains("[ ]"), is(true));
        assertThat(execCommand("ls sriki77/OAUTH1VERIFIERS")
                .contains("[ ]"), is(true));
        assertThat(execCommand("ls sriki77/authcodes")
                .contains("mPMHGrSs"), is(true));
        assertThat(execCommand("ls sriki77/tokens")
                .contains("value"), is(true));

    }

    @Test
    public void shouldCatReportRolesVaultEntities() throws Exception {
        assertThat(execCommand("cat sriki77/reports:dbdcc326-7384-4952-a878-c0f70f770f89")
                .contains("metrics"), is(true));
        assertThat(execCommand("cat sriki77/roles:orgadmin")
                .contains("orgadmin"), is(true));
        assertThat(execCommand("cat sriki77/roles:orgadmin/users:sseshadri@apigee.com")
                .contains("sriki77"), is(true));
        assertThat(execCommand("ls sriki77/roles:orgadmin/perms")
                .contains("put"), is(true));
        assertThat(execCommand("ls sriki77/vaults")
                .contains("[ ]"), is(true));
    }

}
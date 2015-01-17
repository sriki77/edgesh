package org.github.sriki77.edgesh.command;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CommandLoopOrgTest extends CommandTestBase {
    @Test
    public void shouldCatAGivenOrg() throws Exception {
        pwdContains("ROOT");
        assertThat(execCommand("cat .").trim().length(), is(0));
        final String changedOrg = execCommand("cd sriki77");
        pwdContains("ORG:sriki77");
        assertThat(changedOrg.contains(execCommand("cat .").trim()), is(true));
        execCommand("cd ..");
        pwdContains("ROOT");
    }

    @Test
    public void shouldListEntitiesInOrg() throws Exception {
        execCommand("cd sriki77");
        final String result = execCommand("ls");
        assertThat(result.contains("ENV"), is(true));
    }

    @Test
    public void shouldListEnvInOrg() throws Exception {
        execCommand("cd sriki77");
        final String result = execCommand("ls env");
        assertThat(result.contains("test"), is(true));
        assertThat(result.contains("prod"), is(true));
    }

    @Test
    public void shouldChangeToGiveEnv() throws Exception {
        pwdContains("ROOT");
        execCommand("cd sriki77");
        execCommand("cd env:test");
        pwdContains("ENV:test");
        execCommand("cd ..");
        execCommand("cd env:prod");
        pwdContains("ENV:prod");
    }

}
package org.github.sriki77.edgesh.command;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CommandLoopTest extends CommandTestBase {

    @Test
    public void shouldChangeToGiveOrg() throws Exception {
        pwdContains("ROOT");
        execCommand("cd sriki77");
        pwdContains("ORG:sriki77");
        execCommand("cd / ");
        pwdContains("ROOT");
    }

    @Test
    public void shouldListManPage() throws Exception {
        String result = execCommand("man ls ");
        assertThat(result.contains("DESCRIPTION"),is(true));
        result = execCommand("man");
        assertThat(result.contains("error"),is(true));
        result = execCommand("man man");
        assertThat(result.contains("detailed documentation"),is(true));
    }

}
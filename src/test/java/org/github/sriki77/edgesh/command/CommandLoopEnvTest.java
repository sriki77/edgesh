package org.github.sriki77.edgesh.command;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CommandLoopEnvTest extends CommandTestBase {

    @Test
    public void shouldCatAGivenEnv() throws Exception {
        execCommand("cd sriki77");
        assertChange("test");
        execCommand("cd ..");
        assertChange("prod");
    }

    @Test
    public void shouldCatAGivenTargetServer() throws Exception {
        execCommand("cd sriki77/env:test/");
        assertThat(execCommand("ls .").contains("TS"), is(true));
        assertThat(execCommand("ls TS").contains("dead_lb"), is(true));
        assertThat(execCommand("cat TS:dead_lb").contains("dead_lb"), is(true));
        assertThat(execCommand("cd TS:dead_lb").contains("isEnabled"), is(true));
        assertThat(execCommand("cat .").contains("isEnabled"), is(true));
    }

    @Test
    public void shouldCatTargetServerFromAnyDirectory() throws Exception {
        execCommand("cd sriki77/env:test/");
        assertThat(execCommand("ls .").contains("TS"), is(true));
        assertThat(execCommand("ls TS").contains("dead_lb"), is(true));
        assertThat(execCommand("cat /sriki77/env:test/ts:dead_lb").contains("isEnabled"), is(true));
    }

    @Test
    public void checkTheRootBasedCDandLSCommand() throws Exception {
        execCommand("cd sriki77/env:test/");
        assertThat(execCommand("ls .").contains("TS"), is(true));
        assertThat(execCommand("ls TS").contains("dead_lb"), is(true));
        assertThat(execCommand("cd /sriki77/env:test/ts:dead_lb").contains("isEnabled"), is(true));
        assertThat(execCommand("ls /sriki77/env:test/TS").contains("dead_lb"), is(true));
    }

    private void assertChange(final String env) throws IOException {
        final String changedEnv = execCommand("cd env:" + env);
        assertThat(changedEnv.contains(execCommand("cat .").trim()), is(true));
    }
}
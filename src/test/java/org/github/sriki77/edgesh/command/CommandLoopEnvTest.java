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

    private void assertChange(final String env) throws IOException {
        final String changedEnv = execCommand("cd env:" + env);
        assertThat(changedEnv.contains(execCommand("cat .").trim()), is(true));
    }
}
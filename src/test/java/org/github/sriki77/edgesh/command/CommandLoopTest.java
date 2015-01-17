package org.github.sriki77.edgesh.command;

import org.junit.Test;

public class CommandLoopTest extends CommandTestBase {

    @Test
    public void shouldChangeToGiveOrg() throws Exception {
        pwdContains("ROOT");
        execCommand("cd sriki77");
        pwdContains("ORG:sriki77");
    }

}
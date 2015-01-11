package org.github.sriki77.edgesh.command;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum ShellCommand {
    LS("ls"), CD("cd"), CAT("cat"), RM("rm"), NEW("new"), QUIT("q", "quit", "exit"), PWD("pwd"), INVALID;
    private final String[] commandStrings;
    private String param;

    ShellCommand(String... commandStrings) {
        this.commandStrings = commandStrings;
    }

    public String param() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ShellCommand{" +
                "commandStrings=" + Arrays.toString(commandStrings) +
                ", param='" + param + '\'' +
                '}';
    }

    public static ShellCommand toCommand(String input) {
        if (StringUtils.isBlank(input)) {
            return INVALID;
        }
        final String[] parts = input.split(" ");
        final String cmd = parts[0];
        final String param = parts.length > 1 ? parts[1] : null;
        for (ShellCommand shellCommand : values()) {
            if (shellCommand.matches(cmd)) {
                shellCommand.setParam(param);
                return shellCommand;
            }
        }
        return INVALID;
    }

    private boolean matches(String cmd) {
        for (String cmdString : commandStrings) {
            if (cmdString.equalsIgnoreCase(cmd)) {
                return true;
            }
        }
        return false;
    }

}

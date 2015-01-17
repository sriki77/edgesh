package org.github.sriki77.edgesh.command;

import org.apache.commons.lang3.StringUtils;
import org.github.sriki77.edgesh.EdgeUtil;

import java.io.PrintWriter;
import java.util.Arrays;

public enum ShellCommand {
    LS(0, 1, "ls"), CD(1, "cd"), CAT(1, "cat"), RM(1, "rm"), NEW(1, "new"), QUIT("q", "quit", "exit"),
    PWD("pwd"), SET(1, "set"), INVALID();
    public static final String PARAM_PREFIX_SEPARATOR = ":";
    private final int minParamCount;
    private final int maxParamCount;
    private final String[] commandStrings;
    private String param;

    ShellCommand(int minParamCount, int maxParamCount, String... commandStrings) {
        this.minParamCount = minParamCount;
        this.maxParamCount = maxParamCount;
        this.commandStrings = commandStrings;
    }

    ShellCommand(int minParamCount, String... commandStrings) {
        this(minParamCount, minParamCount, commandStrings);
    }

    ShellCommand(String... commandStrings) {
        this(0, commandStrings);
    }

    public String param() {
        return param;
    }

    public String paramPrefix() {
        if (param == null) {
            return null;
        }
        final String[] parts = param.split(PARAM_PREFIX_SEPARATOR);
        if (parts.length == 1) {
            return null;
        }
        return parts[0].toUpperCase();
    }


    public String paramSuffix() {
        if (param == null) {
            return null;
        }
        final String[] parts = param.split(PARAM_PREFIX_SEPARATOR);
        if (parts.length == 1) {
            return param;
        }
        return parts[1];
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

    public static ShellCommand toCommand(String input, PrintWriter out) {
        if (StringUtils.isBlank(input)) {
            return INVALID;
        }
        final String[] parts = input.split(" ");
        final String cmd = parts[0];
        final String param = parts.length > 1 ? parts[1] : null;
        for (ShellCommand shellCommand : values()) {
            if (shellCommand.matches(cmd)) {
                if (shellCommand.minParamCount > parts.length - 1) {
                    EdgeUtil.printError(String.format("'%s' should have %s argument(s)", cmd, shellCommand.maxParamCount), out);
                }
                if (param != null) {
                    shellCommand.setParam(param.trim());
                }
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

    public boolean dotParam() {
        return param == null ? true : ".".equals(param);
    }

    public boolean dotdotParam() {
        return param == null ? false : "..".equals(param);
    }

}

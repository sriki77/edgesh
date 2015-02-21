package org.github.sriki77.edgesh.command;

import org.apache.commons.lang3.StringUtils;
import org.github.sriki77.edgesh.EdgeUtil;

import java.io.PrintWriter;
import java.util.Arrays;

public enum ShellCommand {
    LS(0, 1, "list directory(entity) contents.", "ls"),
    CD(1, "change directory(entity) to given parameter.", "cd"),
    CAT(1, "print details of the given directory(entity).", "cat"),
    RM(1, "remove a given directory(entity). *NOT IMPLEMENTED YET*", "rm"),
    NEW(1, "create a new directory(entity). *NOT IMPLEMENTED YET*", "new"),
    QUIT("quit the shell.", "q", "quit", "exit"),
    PWD("print the current working directory(entity).", "pwd"),
    SET(1, "set the value of a given directory(entity). *NOT IMPLEMENTED YET*", "set"),
    HELP("prints the help message.", "help", "h", "?"),
    MAN(1, "provides detailed documentation of commands.", "man"),
    INVALID("");

    public static final String PARAM_PREFIX_SEPARATOR = ":";
    private final int minParamCount;
    private final int maxParamCount;
    private final String description;
    private final String[] commandStrings;
    private String param;
    private static int maxlen = -1;

    ShellCommand(int minParamCount, int maxParamCount, String description, String... commandStrings) {
        this.minParamCount = minParamCount;
        this.maxParamCount = maxParamCount;
        this.description = description;
        this.commandStrings = commandStrings;
    }

    ShellCommand(int minParamCount, String description, String... commandStrings) {
        this(minParamCount, minParamCount, description, commandStrings);
    }

    ShellCommand(String description, String... commandStrings) {
        this(0, description, commandStrings);
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
        final int maxLength = maxCommandStringsLength();
        final String cmdStrings = Arrays.toString(commandStrings);
        final StringBuilder spaceBuilder = new StringBuilder();
        spaceBuilder.setLength(maxLength - cmdStrings.length());
        final String spaces = spaceBuilder.toString().replace('\u0000', ' ');
        return cmdStrings + spaces + " - " + description;
    }

    private static int maxCommandStringsLength() {
        if (maxlen != -1) {
            return maxlen;
        }
        int len = -1;
        for (ShellCommand command : values()) {
            final int cmdStrLen = Arrays.toString(command.commandStrings).length();
            if (len < cmdStrLen) {
                len = cmdStrLen;
            }
        }
        return maxlen = len;
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
                    return INVALID;
                }
                if (param != null) {
                    shellCommand.setParam(param.trim());
                }
                return shellCommand;
            }
        }
        return INVALID;
    }


    public static ShellCommand toCommand(String input) {
        if (StringUtils.isBlank(input)) {
            return INVALID;
        }
        for (ShellCommand shellCommand : values()) {
            if (shellCommand.matches(input)) {
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

    public boolean paramAtRoot() {
        return param == null ? false : param.startsWith("/");
    }

    public static void clear() {
        for (ShellCommand command : values()) {
            command.param = null;
        }
    }


}

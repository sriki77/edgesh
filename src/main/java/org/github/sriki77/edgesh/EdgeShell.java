package org.github.sriki77.edgesh;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.github.sriki77.edgesh.command.CommandLoop;
import org.github.sriki77.edgesh.data.ShellContext;

public class EdgeShell {

    public static final String OPT_HELP = "help";
    public static final String OPT_URL = "url";
    public static final String OPT_USER = "u";
    public static final String OPT_PASSWORD = "p";
    public static final String DEFAULT_EDGE_URI = "https://api.enterprise.apigee.com:443/v1/";

    public static void main(String... args) throws Exception {
        ShellContext context = processCommandLineArgs(args);
        new CommandLoop(context).start();
    }


    private static ShellContext processCommandLineArgs(String[] args) throws Exception {
        CommandLineParser parser = new BasicParser();
        Options options = buildOptions();
        return processOptions(getCommandLine(args, parser, options));
    }

    private static CommandLine getCommandLine(String[] args, CommandLineParser parser, Options options) throws ParseException {
        try {
            return parser.parse(options, args);
        } catch (MissingOptionException moe) {
            System.out.println("username and password are mandatory");
            printHelpAndExit(options);
        }
        return null;
    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption(new Option(OPT_HELP, "Prints this message"));
        final Option urlOption = OptionBuilder.withArgName("url")
                .hasArg().withDescription("Apigee Edge Mgmt URL")
                .create(OPT_URL);
        final Option userOption = OptionBuilder.withArgName("username")
                .hasArg().withDescription("Edge Username")
                .create(OPT_USER);
        userOption.setRequired(true);
        final Option passOption = OptionBuilder.withArgName("password")
                .hasArg().withDescription("Edge Password")
                .create(OPT_PASSWORD);
        passOption.setRequired(true);

        options.addOption(urlOption);
        options.addOption(userOption);
        options.addOption(passOption);
        return options;
    }

    private static ShellContext processOptions(CommandLine cli) throws Exception {
        return new ShellContext(
                cli.getOptionValue(OPT_URL, DEFAULT_EDGE_URI),
                cli.getOptionValue(OPT_USER),
                cli.getOptionValue(OPT_PASSWORD)
        );
    }

    private static void printHelpAndExit(Options options) {
        System.out.println();
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java " + EdgeShell.class.getName(), options);
        System.exit(-1);
    }


}

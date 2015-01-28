package org.github.sriki77.edgesh.command;

import org.github.sriki77.edgesh.EdgeUtil;
import org.github.sriki77.edgesh.data.ShellContext;
import org.junit.After;
import org.junit.Before;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.StringJoiner;

import static org.github.sriki77.edgesh.TestUtil.buildContext;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public abstract class CommandTestBase {

    public static final String PROMPT = "$ ";
    protected PrintWriter commandIn;
    protected InputStream commandOut;
    private ShellContext context;

    protected BufferedReader initReader() throws IOException {
        final PipedWriter pipedWriter = new PipedWriter();
        commandIn = new PrintWriter(pipedWriter, true);
        return new BufferedReader(new PipedReader(pipedWriter));
    }


    protected PrintWriter initOutputStream() throws IOException {
        final PipedOutputStream outputStream = new PipedOutputStream();
        commandOut = new BufferedInputStream(new PipedInputStream(outputStream));
        return new PrintWriter(outputStream, true);
    }

    protected String execCommand(String cmd) throws IOException {
        commandIn.println(cmd);
        return readOutput();
    }

    protected void pwdContains(String value) throws IOException {
        commandIn.println("pwd");
        assertContains(value);
    }

    protected void pwdEndsWith(String value) throws IOException {
        commandIn.println("pwd");
        assertEndsWith(value);
    }

    private void assertEndsWith(String value) throws IOException {
        final String output = readOutput();
        assertThat("Actual: " + output, output.endsWith(value), is(true));
    }

    private void assertContains(String value) throws IOException {
        final String output = readOutput();
        assertThat("Actual: " + output, output.contains(value), is(true));
    }

    private String readOutput() throws IOException {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        String line;
        do {
            line = nextLine();
            joiner.add(line.trim());
        } while (!line.endsWith(PROMPT));

        return joiner.toString().replace(PROMPT.trim(), "").trim();
    }

    protected String nextLine() throws IOException {
        StringBuilder line = new StringBuilder();
        do {
            final int ch = commandOut.read();
            if (ch == '\n') {
                break;
            }
            line.append((char) ch);
        } while (commandOut.available() > 0);
        System.out.println(line);
        return line.toString();
    }

    @Before
    public void setUp() throws Exception {
        System.setProperty(EdgeUtil.VERBOSE_LOGGING, "true");
        context = buildContext();
        final CommandLoop commandLoop = new CommandLoop(context, initReader(), initOutputStream());
        new Thread() {
            @Override
            public void run() {
                try {
                    commandLoop.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
        for (int i = 0; i < 3; i++) {
            nextLine();
        }
    }


    @After
    public void tearDown() throws Exception {
        context.reset();
    }

}

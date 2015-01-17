package org.github.sriki77.edgesh;

import com.jayway.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import java.io.PrintWriter;

public final class EdgeUtil {
    private EdgeUtil() {
    }


    public static String errorMsg(String msg) {
        return "error: " + msg;
    }

    public static void printBody(PrintWriter out, Response response) {
        final String body = response.asString();
        if (!StringUtils.isBlank(body)) {
            out.println(body);
        }
    }

    public static void printError(String msg, PrintWriter out, Response response) {
        out.println(errorMsg(msg));
        out.println(response.statusLine());
        out.println(response.headers());
        printBody(out, response);
    }

    public static void printError(String msg, PrintWriter out) {
        out.println(errorMsg(msg));
    }

    public static void printMsg(String msg, PrintWriter out) {
        out.println(msg);
    }


    public static void printMsg(String msg, PrintWriter out, Response response) {
        if (msg != null) {
            out.println(msg);
        }
        printBody(out, response);
    }

    public static boolean handleResponse(String errMsg, PrintWriter out, Response response) {
        return handleResponse(null, errMsg, out, response);
    }

    public static boolean handleResponse(String succMsg, String errMsg, PrintWriter out, Response response) {
        final int statusCode = response.statusCode();
        if (statusCode == HttpStatus.SC_OK) {
            printMsg(succMsg, out, response);
            return true;
        }
        printError(errMsg, out, response);
        return false;
    }


}

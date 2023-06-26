package org.logger;

import java.io.PrintStream;

public class LoggerTest {
    public static void main(String[] args) {
        Logger.activateLoggingToFile("logs/", true);
        Logger.logDebug("HelloWorld!");
        Logger.getDebugStream().println("Testing Debug stream");
        Logger.deactivateLoggingToFile();
    }
}

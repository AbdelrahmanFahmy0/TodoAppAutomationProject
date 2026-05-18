package com.todo.utils;

import com.todo.utils.logs.LogsManager;

public class PortUtils {

    /**
     * Kills the process currently using the specified port.
     * Supports Windows, macOS, and Linux.
     *
     * @param port The port number to free up.
     */
    public static void killProcessOnPort(int port) {
        try {
            String pid = switch (OSUtils.getCurrentOS()) {
                case WINDOWS -> findPidWindows(port);
                case MAC, LINUX -> findPidUnix(port);
                default -> {
                    LogsManager.warn("killProcessOnPort is not supported on this OS.");
                    yield null;
                }
            };

            if (pid != null && !pid.isEmpty()) {
                LogsManager.info("Killing PID " + pid + " on port " + port);
                switch (OSUtils.getCurrentOS()) {
                    case WINDOWS -> TerminalUtils.executeTerminalCommand("cmd", "/c", "taskkill", "/PID", pid, "/F");
                    case MAC, LINUX -> TerminalUtils.executeTerminalCommand("kill", "-15", pid);
                    default -> LogsManager.warn("Killing process is not supported on this OS.");
                }
                LogsManager.info("✅ Killed PID " + pid + " on port " + port);
            } else {
                LogsManager.info("No process found on port " + port);
            }

        } catch (Exception e) {
            LogsManager.warn("killProcessOnPort failed: " + e.getMessage());
        }
    }

    /**
     * Finds the PID of the process using the specified port on Windows.
     *
     * @param port The port number to check.
     * @return The PID as a string, or null if not found.
     * @throws Exception If an error occurs while executing the command.
     */
    private static String findPidWindows(int port) throws Exception {
        Process netstat = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "netstat -ano"});
        String output = new String(netstat.getInputStream().readAllBytes());
        netstat.waitFor();

        for (String line : output.split("\n")) {
            if (line.contains(":" + port) && line.contains("LISTENING")) {
                String[] parts = line.trim().split("\\s+");
                return parts[parts.length - 1].trim();
            }
        }
        return null;
    }

    /**
     * Finds the PID of the process using the specified port on Unix-based systems (macOS/Linux).
     *
     * @param port The port number to check.
     * @return The PID as a string, or null if not found.
     * @throws Exception If an error occurs while executing the command.
     */
    private static String findPidUnix(int port) throws Exception {
        Process lsof = Runtime.getRuntime().exec(
                new String[]{"lsof", "-t", "-i", "TCP:" + port, "-sTCP:LISTEN"}
        );
        String output = new String(lsof.getInputStream().readAllBytes()).trim();
        lsof.waitFor();

        if (!output.isEmpty()) {
            return output.split("\n")[0].trim();
        }
        return null;
    }
}

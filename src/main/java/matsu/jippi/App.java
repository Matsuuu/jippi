package matsu.jippi;

import matsu.jippi.util.FileReader;

public class App {
    public static void main(String[] args) {
        String replayFile = null;
        String outputFile = null;
        boolean isRealtime = false;

        for (String arg : args) {
            if (arg.contains("--file")) {
                replayFile = arg.substring("--file=".length());
            }
            if (arg.contains("--output")) {
                outputFile = arg.substring("--output=".length());
            }
            if (arg.contains("--live")) {
                isRealtime = true;
            }
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("       .-..-.             _ ");
        System.out.println("       : :: :            :_;");
        System.out.println("     _ : :: :.---. .---. .-.");
        System.out.println("    : :; :: :: .; `: .; `: :");
        System.out.println("    `.__.':_;: ._.': ._.':_;");
        System.out.println("             : :   : :      ");
        System.out.println("             :_;   :_;      ");

        System.out.println("");
        System.out.println("Running Jippi with the following config:");

        if (replayFile != null) {
            System.out.println("Replay File: " + FileReader.getFullPath(replayFile));
        }
        if (outputFile != null) {
            System.out.println("Output File: " + FileReader.getFullPath(outputFile));
        }
        System.out.println("Realtime reading: " + isRealtime);
        System.out.println("");
        System.out.println("");
    }
}

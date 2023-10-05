package Model;

import java.util.Scanner;

public class GetCommands {
    private static String[] d_validBeginPhaseCmds = new String[]{"editmap", "stopgame", "loadmap","showmap"};
    private static String[] d_validEditMapPhaseCmds = new String[]{"editcontinent", "editcountry", "editneighbor", "validatemap", "savemap", "showmap", "stopgame"};
    private static String[] d_validateStartPlayCmds = new String[]{"gameplayer", "assigncountries","showmap"};
    private static String[] d_validIssueOrderCmds = new String[]{"deploy"};
    private static Scanner d_scan;

    static {
        GetCommands.d_scan = new Scanner(System.in);
    }

    private static boolean isValidPhaseCommand(String p_command, GamePhase p_phase) {
        String[] l_splitCmds = p_command.split("\\s+");
        String l_phaseCmd = l_splitCmds[0];
        if (p_phase == GamePhase.BEGINGAME) {
            for (String validCommand : d_validBeginPhaseCmds) {
                if (validCommand.equalsIgnoreCase(l_phaseCmd)) {
                    return true;
                }
            }
        } else if (p_phase == GamePhase.EDITMAP) {
            for (String validCommand : d_validEditMapPhaseCmds) {
                if (validCommand.equalsIgnoreCase(l_phaseCmd)) {
                    return true;
                }
            }
        } else if (p_phase == GamePhase.STARTPLAY) {
            for (String validCommand : d_validateStartPlayCmds) {
                if (validCommand.equalsIgnoreCase(l_phaseCmd)) {
                    return true;
                }
            }
        } else if (p_phase == GamePhase.ISSUEORDER) {
            for (String validCommand : d_validIssueOrderCmds) {
                if (validCommand.equalsIgnoreCase(l_phaseCmd)) {
                    return true;
                }
            }
        }
        System.out.println("Invalid phase command");
        return false;
    }

    private static String validateBeginPhaseCommand() {
        String l_cmd;
        do {
            l_cmd = d_scan.nextLine();
        } while (!isValidPhaseCommand(l_cmd, GamePhase.BEGINGAME));
        return l_cmd;
    }

    private static String validateEditMapPhaseCommand() {
        String l_cmd;
        do {
            l_cmd = d_scan.nextLine();
        } while (!isValidPhaseCommand(l_cmd, GamePhase.EDITMAP));
        return l_cmd;
    }

    private static String validateGamePhaseCommand() {
        String l_cmd;
        do {
            l_cmd = d_scan.nextLine();
        } while (!isValidPhaseCommand(l_cmd, GamePhase.STARTPLAY));
        return l_cmd;
    }

    private static String validateIssueOrderCommand() {
        String l_cmd;
        do {
            l_cmd = d_scan.nextLine();
        } while (!isValidPhaseCommand(l_cmd, GamePhase.ISSUEORDER));
        return l_cmd;
    }

    public static String validateCommand(GamePhase p_phase) {
        String l_cmd = null;
        if (p_phase == GamePhase.BEGINGAME) {
            l_cmd = validateBeginPhaseCommand();
        } else if (p_phase == GamePhase.EDITMAP) {
            l_cmd = validateEditMapPhaseCommand();
        } else if (p_phase == GamePhase.STARTPLAY) {
            l_cmd = validateGamePhaseCommand();
        } else if (p_phase == GamePhase.ISSUEORDER) {
            l_cmd = validateIssueOrderCommand();
        }
        return l_cmd;
    }
}

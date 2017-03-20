package com.excilys.computerdatabase.computerdb.view.cli;

import com.excilys.computerdatabase.computerdb.view.cli.action.*;

public enum MenuEnum {

    LISTCOMPUTER('A', "List Computer", new ListComputerAction()),
    LISTCOMPANY('Z', "List Company", new ListCompanyAction()),
    SHOW('S', "Show", new ShowComputerDetailsAction()),
    CREATE('C', "Create", new CreateComputerAction()),
    UPDATE('U', "Update", new UpdateComputerAction()),
    DELETE('D', "Delete", new DeleteComputerAction()),
    DELETE_COMPANY('R', "Remove Company", new DeleteCompanyAction()),
    EXIT('E', "Exit", new ExitAction());

    private char commandeChar;
    private String commandeString;
    private ActionMenu commandeAction;

    /**
     * Construct CLI Action .
     *
     * @param c
     *            char to activate Action
     * @param s
     *            String to activate Action
     * @param ac
     *            Cli Action
     */
    MenuEnum(char c, String s, ActionMenu ac) {
        commandeChar = c;
        commandeString = s;
        commandeAction = ac;
    }

    @Override
    public String toString() {
        return "" + commandeString + "\t (" + commandeChar + ").";
    }

    /**
     * Check if the given String is equals ton to Char on String sequence to
     * activate Action.
     *
     * @param s
     *            String to check
     * @return true if the String is equals to the char or the String
     */
    public boolean isEntry(String s) {
        if (s.length() == 1) {
            return new Character(s.toUpperCase().charAt(0)).equals(commandeChar);
        }
        return s.equalsIgnoreCase(commandeString);
    }

    /**
     * Get the Action.
     *
     * @return ActionMenu.
     */
    public ActionMenu getAction() {
        return commandeAction;
    }

}

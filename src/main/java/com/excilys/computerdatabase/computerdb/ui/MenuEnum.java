package com.excilys.computerdatabase.computerdb.ui;

import com.excilys.computerdatabase.computerdb.ui.action.ActionMenu;
import com.excilys.computerdatabase.computerdb.ui.action.CreateComputerAction;
import com.excilys.computerdatabase.computerdb.ui.action.DeleteComputerAction;
import com.excilys.computerdatabase.computerdb.ui.action.ExitAction;
import com.excilys.computerdatabase.computerdb.ui.action.ListCompanyAction;
import com.excilys.computerdatabase.computerdb.ui.action.ListComputerAction;
import com.excilys.computerdatabase.computerdb.ui.action.ShowComputerDetailsAction;
import com.excilys.computerdatabase.computerdb.ui.action.UpdateComputerAction;

public enum MenuEnum {

    LISTCOMPUTER('A', "List Computer", new ListComputerAction()), LISTCOMPANY('Z', "List Company",
            new ListCompanyAction()), SHOW('S', "Show", new ShowComputerDetailsAction()), CREATE('C', "Create",
                    new CreateComputerAction()), UPDATE('U', "Update", new UpdateComputerAction()), DELETE('D',
                            "Delete", new DeleteComputerAction()), EXIT('E', "Exit", new ExitAction());

    private char commandeChar;
    private String commandeString;
    private ActionMenu commandeAction;

    MenuEnum(char c, String s, ActionMenu ac) {
        commandeChar = c;
        commandeString = s;
        commandeAction = ac;
    }

    public String toString() {
        return "" + commandeString + "\t (" + commandeChar + ").";
    }

    public boolean isEntry(String s) {
        if (s.length() == 1) {
            return new Character(s.toUpperCase().charAt(0)).equals(commandeChar);
        }
        return s.equalsIgnoreCase(commandeString);
    }

    public ActionMenu getAction() {
        return commandeAction;
    }

}

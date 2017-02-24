package com.excilys.computerdatabase.computerdb;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.ui.MenuEnum;
import com.excilys.computerdatabase.computerdb.ui.action.ActionMenu;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        
        showMenu();
        
    }
    
    
    
    public static void showMenu(){
    	Scanner sc = new Scanner(System.in);
    	while(true){
    		
    		System.out.println("--------MENU-------------");
        	for(MenuEnum menu : MenuEnum.values()){
      	      System.out.println(menu);
        	}
        	System.out.println("-------------------------");
        	System.out.print("Action : ");
        	
            String s = sc.nextLine();
            System.out.println();
            for(MenuEnum menu : MenuEnum.values()){
              	if(menu.isEntry(s)){
              		menu.getAction().doAction();
              	}
          	}
    	}
    	//sc.close();
    }
}

package com.excilys.computerdatabase.computerdb;

import java.util.Scanner;
import com.excilys.computerdatabase.computerdb.ui.MenuEnum;

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
    }
}

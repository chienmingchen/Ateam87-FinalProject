package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;


/**
 * Filename:   	SocialNetworkManager.java
 * Project:    	Ateam87 Final Project - Social Network
 * Due Date:	11/14/2019
 * 
 * Authors:    	Chien-Ming Chen (cchen556@wisc.edu)
 * 
 * Semester:   	Fall 2019
 * Course: 		CS400
 * Lecture: 	#002 
 * 
 * Desc: 		
 * 
 */

public class SocialNetworkManager {
    
	// We will base on our designed network methods to  
	// manage the soical network 
    private Network network;
    private Person centralPerson = new Person(" ");
    
    /*
     * Package Manager default no-argument constructor.
     */
    public SocialNetworkManager() {
    	this.network = new Network();
    }
    
    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it. 
     * 
     * @param jsonFilepath the name of json data file with package dependency information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException if the give file cannot be read
     * @throws ParseException if the given json cannot be parsed 
     */
    public void constructNetwork(String Filepath) throws FileNotFoundException, IOException, Exception {   
    	
    	try
        {
        	//Read File
    		Scanner parserScanner = new Scanner(new File("friends_000.txt"));
    		while(parserScanner.hasNextLine()) {
    			String currentLine = parserScanner.nextLine();
    			String[] splittedCmd = currentLine.split(" ");
    			
    			//if the instruction is only one string then ignore this instruction
    			if(splittedCmd.length <2)
    				continue;
    			
    			String function = splittedCmd[0];
    			//System.out.print(splittedCmd[0]);
    			Person user1 = new Person(splittedCmd[1]);
    			//System.out.print(splittedCmd[1]);
    			Person user2 = splittedCmd.length>2?new Person(splittedCmd[2]):null;
    			
    			switch(splittedCmd[0]) {
	    			case "a" :
						if(user2!=null)
							this.setFriendship(user1.name, user2.name);
						else
	    			break;
	    			
	    			case "r" :
						if(user2!=null)
							this.setFriendship(user1.name, user2.name);
						else
	    			break;
						
	    			case "s" :
	    				this.centralize(user1.name);
	    			break;
	    			
	    			default :
	    				throw new Exception("Uncompilable commands.");
	    			}	
    		}
        			
            //Hard Code here, need to be read from the file
        	/*
        	network.addVertex("tony");
        	network.addVertex("steve");
        	network.addVertex("Wanda");
        	network.addVertex("Hulk");
        	network.addVertex("Thor");
        	network.addEdge("tony","steve");
        	network.addEdge("tony","Wanda");
        	network.addEdge("tony","Hulk");
        	network.addEdge("tony","Thor");
        	network.addEdge("steve","Wanda");
        	network.addEdge("steve","Hulk");
        	network.addEdge("steve","Thor");
        	network.addEdge("Wanda","Hulk");
        	network.addEdge("Wanda","Thor");
        	network.addEdge("Hulk","Thor");
        	*/
            	
            //network.printGraph();
           
        } 
        catch (FileNotFoundException e) {
        	 //e.printStackTrace();
        	throw new FileNotFoundException();
        } catch (IOException e) {
            //e.printStackTrace();
            throw new IOException();
        }
    }

	public void centralize(String person) {
		//check if person is null
	  	if (person == null) 
	  		return;
	  	this.centralPerson.name = person;
	}
	
	public void addPerson(String person) {
		//check if person is null
	  	if (person == null) 
	  		return;
	  	network.addVertex(person);
	}
	
	public void setFriendship(String person1, String person2) {
		//check if person is null
	  	if (person1 == null && person2 == null) 
	  		return;
	  	network.addEdge(person1, person2);
	  	System.out.println("Add : " + person1 + " " + person2);
	}
	
	public void removePerson(String person) {
		//check if person is null
	  	if (person == null) 
	  		return;
	  	network.removeVertex(person);
	}
	
	public void removeFriendship(String person1, String person2) {
		//check if person is null
	  	if (person1 == null && person2 == null) 
	  		return;
	  	network.removeEdge(person1, person2);
	}
	
	public List<String> getPersonalNetwork(String person) {
		//check if person is null
	  	if (person == null) 
	  		return null;
	  	return network.getAdjacentVerticesOf(person);
	}
	
}

//Need to check howto throw user-defined exception
/*
class PersonNotFoundException extends Exception 
{ 
 public PersonNotFoundException(String s) 
 { 
     // Call constructor of parent Exception 
     super(s); 
 } 
} 
*/

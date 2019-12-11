package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    private List<String> logBuff = new ArrayList<String>();
    
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
    public void constructNetwork(File file) throws FileNotFoundException, IOException, Exception {   
    	
    	try
        {
        	//Read File
    		Scanner parserScanner = new Scanner(file);
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
							this.addPerson(user1.name);
	    			break;
	    			
	    			case "r" :
						if(user2!=null)
							this.removeFriendship(user1.name, user2.name);
						else
							this.removePerson(user1.name);
	    			break;
						
	    			case "s" :
	    				this.centralize(user1.name);
	    			break;
	    			
	    			default :
	    				throw new IOException("Uncompilable commands.");
	    			}	
    		}
    		parserScanner.close();	
//    		network.printGraph();
           
        } 
        catch (FileNotFoundException e) {
        	 //e.printStackTrace();
        	throw new FileNotFoundException();
        }catch(IllegalArgumentException e) {
        	throw new IOException("Uncompilable commands.");
        }catch (IOException e) {
            //e.printStackTrace();
            throw new IOException();
        }
    }

	public void centralize(String person) {
		//check if person is null
	  	if (person == null) 
	  		return;
	  	this.centralPerson.name = person;
	  	this.logBuff.add("s "+person);
	}
	
	public String getCentralPerson() {
		return this.centralPerson.name;
	}
	
	public void addPerson(String person) throws IllegalArgumentException {
		//check if person is null
	  	if (person == null)
	  		return;
	  	if(person.length() == 0) {
	  		return;
	  	}
	  	for(int i = 0; i < person.length(); i++) {
	  		if( !Character.isDigit(person.charAt(i)) && !Character.isLetter(person.charAt(i)) 
	  				&& person.charAt(i) != ' ' && person.charAt(i) != '\''  
	  				&& person.charAt(i) != '_'
	  		   ) {
	  			throw new IllegalArgumentException();
	  		}
	  	}
	  	network.addVertex(person);
                this.logBuff.add("a "+person);
	}
	
	public void setFriendship(String person1, String person2) {
		//check if person is null
	  	if (person1 == null && person2 == null) 
	  		return;
	  	network.addEdge(person1, person2);
	  	System.out.println("Add : " + person1 + " " + person2);
                this.logBuff.add("a " + person1 + " " + person2);
	}
	
	public void removePerson(String person) {
		//check if person is null
	  	if (person == null) 
	  		return;
	  	network.removeVertex(person);
	  	this.logBuff.add("r " + person);
	}
	
	public void removeFriendship(String person1, String person2) {
		//check if person is null
	  	if (person1 == null && person2 == null) 
	  		return;
	  	network.removeEdge(person1, person2);
	  	this.logBuff.add("r " + person1 + " " + person2);
	}
	
	public List<String> getPersonalNetwork(String person) {
		//check if person is null
	  	if (person == null) 
	  		return null;
	  	return network.getAdjacentVerticesOf(person);
	}
	
	public Set<String> getAllUsers() {		
		return network.getAllVertices();
	}
	
	public List<String> mutualFriends(String person1, String person2) {		
		
		List<String> set1 = network.getAdjacentVerticesOf(person1);
		List<String> set2 = network.getAdjacentVerticesOf(person2);
		set1.retainAll(set2);
		return set1;
		 
	}
	
	public List<String> shortestPath(String person1, String person2) {		
	  	if (person1 == null || person2 == null) 
	  		return null;
	  	
		return network.getShortestPathOf(person1, person2);
	}

    public void saveLog(File file)throws IOException{
        try {
             // creates a FileWriter Object
            PrintWriter writer;
            writer = new PrintWriter(file);
            //System.out.println("write log");
            for(int i=0; i < logBuff.size(); i++)
            { 
            	writer.println(logBuff.get(i));
            	//System.out.println(logBuff.get(i));
            } 
            writer.close();
        } catch (IOException ex) {
        	throw new IOException();
        } 
    }
}


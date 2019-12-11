package application;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface SocialNetworkManagerADT {
	/**
	 * Add friendship between user1 and user2
	 * 
	 * 
	 * @param username1
	 * @param username2
	 * @return
	 */
	public void constructNetwork(File file);
	
	/**
	 * Remove friendship between user1 and user2
	 * OR RETURN FALSE?
	 * 
	 * 
	 * @param username1
	 * @param username2
	 * @return
	 */
	public void centralize(String person);
	
	/**
	 * Add a user into the social network
	 * If successful, return true
	 * If the user already exists in the network
	 * RETURN FALSE?
	 * If the user is null
	 * RETURN FALSE? THROW EXCEPTION? INFORM THE USER?
	 * @param user
	 * @return
	 */
	public String getCentralPerson();
	
	/**
	 * Remove a user from the social network
	 * If successful, return true
	 * If the user was not in the network
	 * RETURN FALSE?
	 * If the user is null
	 * RETURN FALSE? THROW EXCEPTION? INFORM THE USER?
	 * @param user
	 * @return
	 */
    public void addPerson(String person);
	
	/**
	 * Get a set of person who are friends of the user
	 * If the user is NOT in the social network
	 * RETURN NULL? OR THROW EXCEPTION? INFORM THE USERS?
	 * 
	 * @param user
	 * @return
	 */
    public void setFriendship(String person1, String person2);
     
     
     
 	public void removePerson(String person);
 	
 	public void removeFriendship(String person1, String person2);
 	
 	public List<String> getPersonalNetwork(String person);
 	public Set<String> getAllUsers();
 	
 	public List<String> mutualFriends(String person1, String person2);
 	
 	public List<String> shortestPath(String person1, String person2);
 	
    public void saveLog(File file);
}

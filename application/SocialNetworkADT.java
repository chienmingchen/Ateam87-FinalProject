package ateamProject;

import java.util.Set;

public interface SocialNetworkADT {
	/**
	 * Add friendship between user1 and user2
	 * If any of the two users were not in the social network,
	 * add the users first
	 * If successful, return true
	 * If there is already a friendship between user1 and user2
	 * no friendship is added and no exception is thrown
	 * SHOULD WE RETURN FALSE HERE?
	 * WE CAN EDIT HERE THO: MAYBE WE CAN INFORM THE USERS THAT THEY ARE ALREADY FRIENDS
	 * If any of the two users is null - 
	 * CREATE APPROPRIATE EXCEPTION CLASS
	 * OR RETURN FALSE?
	 * 
	 * 
	 * @param username1
	 * @param username2
	 * @return
	 */
	public boolean addFriends (String username1,String username2);
	
	/**
	 * Remove friendship between user1 and user2
	 * If successful, return true
	 * If any of the two users were not in the social network,
	 * or there is no friendship between user1 and user2,
	 * no friendship is removed and no exception is thrown
     * SHOULD WE RETURN FALSE HERE?
     * WE CAN INFORM USERS
	 * If any of the two users is null
	 * CREATE APPROPRIATE EXCEPTION CLASS
	 * OR RETURN FALSE?
	 * 
	 * 
	 * @param username1
	 * @param username2
	 * @return
	 */
	public boolean removeFriends(String username1, String username2);
	
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
	public boolean addUser(String user);
	
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
	public boolean removeUser(String user);
	
	/**
	 * Get a set of person who are friends of the user
	 * If the user is NOT in the social network
	 * RETURN NULL? OR THROW EXCEPTION? INFORM THE USERS?
	 * 
	 * @param user
	 * @return
	 */
	public Set<Person> getFriends(String user);
}

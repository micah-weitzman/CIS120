import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Channel {
	
	// List to keep track of the users in a given channel 
	private List<User> userList = new LinkedList<User>();
	
	// Owner of the channel 
	private User owner; 
	
	// Boolean to keep track if the channel is private or not. True if the channel is private 
	public boolean isPrivate; 
	
	/** 
	 * Channel class that keep a record of various data needed by the Channel
	 * The data encolsed is encapsulated and requires the use of function to manipulate
	 * 
	 * @param owner  The owner of the channel inputed as a User type
	 * @param priv   Short for private, is a bool that tells the server if the channel is private. 
	 */
	public Channel(User owner, boolean priv) {
		
		userList.add(owner);
		this.owner = owner;
		this.isPrivate = priv; 
		
	}
	
	/**
	 * A function that returns a collection of strings of the names of the associated user 
	 * in a given channel. 
	 * 
	 * @return a collection<String> of all the current users in the channel. 
	 */
	
	public Collection<String> getUserNames() {
		
		Collection<String> coll = new LinkedList<String>();
		
		for(User u : userList) {
			coll.add(u.toString());
		}
		
		return coll; 
		
	}
	
	
	
	/**
	 * A helper method that returns a list of users in a channel, but with a User class 
	 * 
	 * @return List<User> of the users in a the current channel 
	 */
	
	public List<User> getUserList() {
		return userList; 
	}
	
	
	/** 
	 * A helper method that returns the User that is the current owner of the channel. 
	 * 
	 * @return User that is the channel owner 
	 */
	public User getOwner() {
		return owner; 
	}
	
	/**
	 * A helper method that returns a boolean true if the user is a member of the channel
	 * 
	 * @param uName   	String of the nickname of a user to test
	 * @return			Boolean regarding if the user is a member of the channel 
	 */
	public boolean containsUser(String uName) {
		
		Collection<String> coll = new LinkedList<String>();
		
		for(User u : userList) {
			coll.add(u.toString());
		}
		
		return coll.contains(uName);
		
	}
	
	
	/**
	 * Helper method that adds a user to channel list of users 
	 * 
	 * @param u User instance to add to the channel list of users
	 */
	public void addUser(User u) {
		userList.add(u);
	}
	
	
	/**
	 * Removes a user from the channel list
	 * 
	 * @param u User instance to remove from list 
	 */
	public void removeUser(User u) {
		userList.remove(u); 
	}

}

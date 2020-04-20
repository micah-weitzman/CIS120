import java.util.*;


/**
 * The {@code ServerModel} is the class responsible for tracking the
 * state of the server, including its current users and the channels
 * they are in.
 * This class is used by subclasses of {@link Command} to:
 *     1. handle commands from clients, and
 *     2. handle commands from {@link ServerBackend} to coordinate 
 *        client connection/disconnection. 
 */
public final class ServerModel implements ServerModelApi {
    
	// Keeps track of all the userIds and their associated User classes 
	private Map<Integer, User> userMap;
	
	// Keeps track of the all the channel names and the assocaited Channel classes 
	private Map<String, Channel> channelMap;
	
	
    /**
     * Constructs a {@code ServerModel} and initializes any
     * collections needed for modeling the server state.
     */
    public ServerModel() {
    	this.userMap = new TreeMap<Integer, User>(); 
    	this.channelMap = new TreeMap<String, Channel>(); 
    }


    //==========================================================================
    // Client connection handlers
    //==========================================================================

    /**
     * Informs the model that a client has connected to the server
     * with the given user ID. The model should update its state so
     * that it can identify this user during later interactions. The
     * newly connected user will not yet have had the chance to set a
     * nickname, and so the model should provide a default nickname
     * for the user.  Any user who is registered with the server
     * (without being later deregistered) should appear in the output
     * of {@link #getRegisteredUsers()}.
     *
     * @param userId The unique ID created by the backend to represent this user
     * @return A {@link Broadcast} to the user with their new nickname
     */
    public Broadcast registerUser(int userId) {
        String nickname = generateUniqueNickname();

        userMap.put(userId, new User(nickname)); 
        
        return Broadcast.connected(nickname);
    }

    /**
     * Generates a unique nickname of the form "UserX", where X is the
     * smallest non-negative integer that yields a unique nickname for a user.
     * @return the generated nickname
     */
    private String generateUniqueNickname() {
        int suffix = 0;
        String nickname;
        Collection<String> existingUsers = getRegisteredUsers();
        do {
            nickname = "User" + suffix++;
        } while (existingUsers != null && existingUsers.contains(nickname));
        return nickname;
    }

    /**
     * Determines if a given nickname is valid or invalid (contains at least
     * one alphanumeric character, and no non-alphanumeric characters).
     * @param name The channel or nickname string to validate
     * @return true if the string is a valid name
     */
    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Informs the model that the client with the given user ID has
     * disconnected from the server.  After a user ID is deregistered,
     * the server backend is free to reassign this user ID to an
     * entirely different client; as such, the model should remove all
     * state of the user associated with the deregistered user ID. The
     * behavior of this method if the given user ID is not registered
     * with the model is undefined.  Any user who is deregistered
     * (without later being registered) should not appear in the
     * output of {@link #getRegisteredUsers()}.
     *
     * @param userId The unique ID of the user to deregister
     * @return A {@link Broadcast} instructing clients to remove the
     * user from all channels
     */
    public Broadcast deregisterUser(int userId) {
    	
    	User u = userMap.get(userId);
    	userMap.remove(userId);
    	
    	Collection<String> nameList = new LinkedList<String>(); 
    	for(Channel c : channelMap.values()) {
    		
    		if (c.containsUser(u.toString())) {
    			c.removeUser(u);
    			nameList.addAll(c.getUserNames());
    		}
    	}
    	return Broadcast.disconnected(u.toString(), nameList);
		
    }

    

    //==========================================================================
    // Model update functions
    //==========================================================================

    public ServerError createChannel(String channelName, String owner, boolean inviteOnly) {
        User u = userMap.get(getUserId(owner));
        channelMap.put(channelName, new Channel(u, inviteOnly)); 
        return null;
    }

    public ServerError addUserToChannel(String userName, String channelName) {
        User u = userMap.get(getUserId(userName)); 
        Channel c = channelMap.get(channelName);
        c.addUser(u);
        return null;
    }

    public void removeUserFromChannel(String userName, String channelName) {
    	Channel c = channelMap.get(channelName);
    	User u = userMap.get(getUserId(userName));
    	if (u.equals(c.getOwner())) {
    		channelMap.remove(channelName); 
    	}
    	else {
    		c.removeUser(u);
    	}
    }

    public Broadcast changeNickname(NicknameCommand command) {
    	String newName = command.getNewNickname(); 
    	User u = userMap.get(command.getSenderId());
    	u.changeNickname(newName);
        return command.updateServerModel(this);
    }

    public boolean isPublicChannel(String channelName) {
        return !channelMap.get(channelName).isPrivate;
    }

    //==========================================================================
    // Server model queries
    // These functions provide helpful ways to test the state of your model.
    // You may also use them in your implementation.
    //==========================================================================

    /**
     * Gets the user ID currently associated with the given
     * nickname. The returned ID is -1 if the nickname is not
     * currently in use.
     *
     * @param nickname The nickname for which to get the associated user ID
     * @return The user ID of the user with the argued nickname if
     * such a user exists, otherwise -1
     */
    public int getUserId(String nickname) {

    	for(int u : userMap.keySet()) {
    		if (userMap.get(u).toString().equals(nickname)) {
    			return u; 
    		}
    	}
    	
    	return -1; 
    	
    }

    /**
     * Gets the nickname currently associated with the given user
     * ID. The returned nickname is null if the user ID is not
     * currently in use.
     *
     * @param userId The user ID for which to get the associated
     *        nickname
     * @return The nickname of the user with the argued user ID if
     *          such a user exists, otherwise null
     */
    public String getNickname(int userId) {
        return userMap.get(userId).toString();
    }

    /**
     * Gets a collection of the nicknames of all users who are
     * registered with the server. Changes to the returned collection
     * should not affect the server state.
     * 
     * This method is provided for testing.
     *
     * @return The collection of registered user nicknames
     */
    public Collection<String> getRegisteredUsers() {
    	Collection<User> c = userMap.values();
    	Collection<String> x = new TreeSet<String>();
    	for (User u : c) {
    		x.add(u.toString());
    	}
        return x;
    }

    /**
     * Gets a collection of the names of all the channels that are
     * present on the server. Changes to the returned collection
     * should not affect the server state.
     * 
     * This method is provided for testing.
     *
     * @return The collection of channel names
     */
    public Collection<String> getChannels() {
    	Collection<String> c = channelMap.keySet();
        return c;
    }

    /**
     * Gets a collection of the nicknames of all the users in a given
     * channel. The collection is empty if no channel with the given
     * name exists. Modifications to the returned collection should
     * not affect the server state.
     *
     * This method is provided for testing.
     *
     * @param channelName The channel for which to get member nicknames
     * @return The collection of user nicknames in the argued channel
     */
    public Collection<String> getUsersInChannel(String channelName) {
    	Channel c = channelMap.get(channelName); 
        return c.getUserNames();
    }

    /**
     * Gets the nickname of the owner of the given channel. The result
     * is {@code null} if no channel with the given name exists.
     *
     * This method is provided for testing.
     *
     * @param channelName The channel for which to get the owner nickname
     * @return The nickname of the channel owner if such a channel
     * exists, otherwise null
     */
    public String getOwner(String channelName) {
        Channel c = channelMap.get(channelName); 
        return c.getOwner().toString();
    }

}

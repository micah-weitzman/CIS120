public class User {

	// String that keeps track of the nickname of the user
	private String nickname; 
	
	
	/**
	 * Class that keeps track of a nickname associated with a User 
	 * 
	 * @param nickname   String of the nickname the user is referred to as. 
	 */
	User(String nickname) {
		
		this.toString();
		this.nickname = nickname; 
		
	}

	/**
	 * Changes the nickname associated with a user 
	 * 
	 * @param s		String the User would like to be referred to as 
	 */
	public void changeNickname(String s) {
		nickname = s; 
	}
	
	
	/**
	 * Returns to name the name of the User as a string. 
	 * 
	 */
	@Override
	public String toString() {
		return nickname;
	}
	
}

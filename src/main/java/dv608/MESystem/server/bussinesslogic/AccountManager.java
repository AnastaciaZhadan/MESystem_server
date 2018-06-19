package dv608.MESystem.server.bussinesslogic;

import auxilary.models.User;
import dv608.MESystem.server.dataaccess.DBManagerInterface;
import dv608.MESystem.server.dataaccess.DbManager;

public class AccountManager implements AccountManagerInterface {

	@Override
	public User signUp(String username, String passwordHash) {
		User newUser = new User(username);
		newUser.setPassword(passwordHash);
		
		DBManagerInterface dbManager = new DbManager();
		User registeredUser = dbManager.create(newUser);
		registeredUser.setPassword(null);
		return registeredUser;
	}

	@Override
	public User login(String username, String passwordHash) {
		DBManagerInterface dbManager = new DbManager();
		User user = dbManager.getUser(username);
		if(user.getPassword().equals(passwordHash)){
			User logedInUser = new User(username);
			logedInUser.setId(user.getId());
			return logedInUser;
		}	
		return null;
	}

}

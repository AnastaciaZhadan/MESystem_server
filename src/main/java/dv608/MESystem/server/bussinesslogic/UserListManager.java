package dv608.MESystem.server.bussinesslogic;

import java.util.Collection;

import auxilary.models.User;
import dv608.MESystem.server.dataaccess.DBManagerInterface;
import dv608.MESystem.server.dataaccess.DbManager;

public class UserListManager implements UserListManagerInterface {

	@Override
	public Collection<User> getUserList() {
		DBManagerInterface dbManager = new DbManager();
		return dbManager.getUsers();
	}

}

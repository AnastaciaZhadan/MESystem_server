package dv608.MESystem.server.bussinesslogic;

import auxilary.models.User;

public interface AccountManagerInterface {
	User signUp(String username, String passwordHash);
	User login(String username, String passwordHash);
}

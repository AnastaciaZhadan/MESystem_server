package dv608.MESystem.server.API;

import java.util.Collection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import auxilary.models.User;
import dv608.MESystem.server.bussinesslogic.UserListManager;
import dv608.MESystem.server.bussinesslogic.UserListManagerInterface;

@RestController
public class UserListManagerAPI {
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Collection<User> getUserList(){
		UserListManagerInterface ulManager = new UserListManager();
		return ulManager.getUserList();
	}
	
	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public Collection<User> getFriendsList(Integer userId){
		return null;
	}
}

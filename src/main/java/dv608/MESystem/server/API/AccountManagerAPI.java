package dv608.MESystem.server.API;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auxilary.models.User;
import dv608.MESystem.server.bussinesslogic.AccountManager;
import dv608.MESystem.server.bussinesslogic.AccountManagerInterface;

@RestController
@RequestMapping("/account")
public class AccountManagerAPI {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public User signUp(@RequestParam(value="username", required=true) String username, 
			@RequestParam(value="pass", required=true) String passwordHash){
		AccountManagerInterface accManager = new AccountManager();
		return accManager.signUp(username, passwordHash);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public User login(@RequestParam(value="username", required=true) String username, 
			@RequestParam(value="pass", required=true) String passwordHash){
		AccountManagerInterface accManager = new AccountManager();
		return accManager.login(username, passwordHash);
	}
}

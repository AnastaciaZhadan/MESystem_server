package dv608.MESystem.server.API;

import java.util.Collection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auxilary.models.Group;
import auxilary.models.Message;
import dv608.MESystem.server.bussinesslogic.GroupMessaging;
import dv608.MESystem.server.bussinesslogic.GroupMessagingInterface;

@RestController
@RequestMapping("/group")
public class GroupMessagingAPI {
	@RequestMapping(value = "/creategroup", method = {RequestMethod.POST, RequestMethod.GET})
	public Group createGroup(@RequestParam(value="name", required=true) String groupName, 
			@RequestParam(value="usersIds", required=true) Collection<Integer> usersIds){
		GroupMessagingInterface grMessaging = new GroupMessaging();
		return grMessaging.createGroup(groupName, usersIds);
	}
	
	@RequestMapping(value = "/send", method = {RequestMethod.POST, RequestMethod.GET})
	public boolean sendMessageToGroup(@RequestParam(value="groupId", required=true) Integer groupId, 
			@RequestParam(value="senderId", required=true) Integer senderId, 
			@RequestParam(value="messageBody", required=true) String messageBody){
		GroupMessagingInterface grMessaging = new GroupMessaging();
		return grMessaging.sendMessageToGroup(groupId, senderId, messageBody);
	}
	
	@RequestMapping(value = "/groups", method = {RequestMethod.POST, RequestMethod.GET})
	public Collection<Group> getGroupsOfUser(@RequestParam(value="userId", required=true) Integer userId){
		GroupMessagingInterface grMessaging = new GroupMessaging();
		return grMessaging.getGroupsOfUser(userId);
	}
	
	@RequestMapping(value = "/chat-with-group", method = RequestMethod.GET)
	public Collection<Message> getChatWithGroup(@RequestParam(value="groupId", required=true) Integer groupId){
		GroupMessagingInterface grMessaging = new GroupMessaging();
		return grMessaging.getChatWithGroup(groupId);
	}
}

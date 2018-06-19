package dv608.MESystem.server.API;

import java.util.Collection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auxilary.models.Message;
import auxilary.models.User;
import dv608.MESystem.server.bussinesslogic.Messaging;
import dv608.MESystem.server.bussinesslogic.MessagingInterface;

@RestController
@RequestMapping("/messaging")
public class MessagingAPI {
	
	@RequestMapping(value = "/send", method = {RequestMethod.POST, RequestMethod.GET})
	public boolean sendMessageToUser(@RequestParam(value="receiverId", required=true) Integer receiverId, 
			@RequestParam(value="senderId", required=true) Integer senderId, 
			@RequestParam(value="messageBody", required=true) String messageBody){
		MessagingInterface messaging = new Messaging();
		return messaging.sendMessageTo(receiverId, senderId, messageBody);
	}
	
	@RequestMapping(value = "/new-messages", method = RequestMethod.GET)
	public Collection<Message> getNewMessagesForUser(@RequestParam(value="userId", required=true) Integer userId){
		MessagingInterface messaging = new Messaging();
		return messaging.getNewMessagesForUser(userId);
	}
	
	@RequestMapping(value = "/chat-with-user", method = RequestMethod.GET)
	public Collection<Message> getChatWithUser(@RequestParam(value="userId", required=true) Integer chatedUserId, 
			@RequestParam(value="currentUserId", required=true) Integer currentUserId){
		MessagingInterface messaging = new Messaging();
		return  messaging.getChatWithUser(chatedUserId, currentUserId);
	}
}

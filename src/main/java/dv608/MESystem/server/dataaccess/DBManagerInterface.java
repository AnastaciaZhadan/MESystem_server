package dv608.MESystem.server.dataaccess;

import java.util.Collection;

import auxilary.models.MessageDBView;
import auxilary.models.Group;
import auxilary.models.Message;
import auxilary.models.User;

public interface DBManagerInterface {
	//Account Management
	User create(User user);
	
	User getUser(Integer id);
	
	User getUser(String username);
	
	
	//UserList Management
	Collection<User> getUsers();
	
	
	//Message Management
	MessageDBView create(MessageDBView message);
	
	Collection<Message> getNewMessagesForUser(Integer userId);	
	
	Collection<Message> getChatWithUser(Integer userId, Integer currentUserId);	
	
	boolean setReadStatusForMessage(Message message);
	
	Message getMessage(Integer messageId);
	
	
	//Group Messaging
	Group create(Group group);
	
	Collection<Group> getGroupsOfUser(Integer userId);

	Group getGroup(Integer groupId);

	Collection<Message> getChatWithGroup(Integer groupId);
}

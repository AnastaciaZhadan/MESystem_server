package dv608.MESystem.server.bussinesslogic;

import java.util.Collection;

import auxilary.models.Group;
import auxilary.models.Message;

public interface GroupMessagingInterface {
	Group createGroup(String groupName, Collection<Integer> usersIds);
	
	boolean sendMessageToGroup(Integer groupId, Integer userId, String messageBody);
	
	Collection<Group> getGroupsOfUser(Integer userId);
	
	Collection<Message> getChatWithGroup(Integer groupId);
}

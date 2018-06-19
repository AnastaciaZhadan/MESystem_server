package dv608.MESystem.server.bussinesslogic;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;

import auxilary.models.Group;
import auxilary.models.Message;
import auxilary.models.MessageDBView;
import auxilary.models.User;
import dv608.MESystem.server.dataaccess.*;

public class GroupMessaging implements GroupMessagingInterface {
	DBManagerInterface dbManager = new DbManager();
	
	@Override
	public Group createGroup(String groupName, Collection<Integer> usersIds) {	
		Collection<User> users = new ArrayList<>();
		for (Integer id : usersIds) {
			users.add(dbManager.getUser(id));
		}
		Group group = null;
		try {
			group = new Group(URLDecoder.decode(groupName, "UTF-8"), users);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbManager.create(group);
	}

	@Override
	public boolean sendMessageToGroup(Integer groupId, Integer senderId, String messageBody) {
		MessageDBView newMessage;
		try {
			newMessage = new MessageDBView(senderId, URLDecoder.decode(messageBody, "UTF-8"));
			newMessage.setGroupReceiver(groupId);
			MessageDBView message = dbManager.create(newMessage);
			if(message!=null)
				return true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Collection<Group> getGroupsOfUser(Integer userId) {
		return dbManager.getGroupsOfUser(userId);
	}

	@Override
	public Collection<Message> getChatWithGroup(Integer groupId) {
		return dbManager.getChatWithGroup(groupId);
	}

}

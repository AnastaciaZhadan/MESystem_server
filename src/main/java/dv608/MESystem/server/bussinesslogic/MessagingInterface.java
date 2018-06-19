package dv608.MESystem.server.bussinesslogic;

import java.util.Collection;

import auxilary.models.*;

public interface MessagingInterface {
	boolean sendMessageTo(Integer receiverID, Integer senderId, String messageBody);
	
	Collection<Message> getNewMessagesForUser(Integer userId);
	
	Collection<Message> getChatWithUser(Integer userId, Integer currentUserId);
}

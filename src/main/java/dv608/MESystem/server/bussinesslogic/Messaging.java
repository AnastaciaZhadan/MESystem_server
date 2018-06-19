package dv608.MESystem.server.bussinesslogic;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;

import auxilary.models.MessageDBView;
import auxilary.models.Message;
import dv608.MESystem.server.dataaccess.DBManagerInterface;
import dv608.MESystem.server.dataaccess.DbManager;

public class Messaging implements MessagingInterface {

	@Override
	public boolean sendMessageTo(Integer receiverID, Integer senderId, String messageBody) {
		MessageDBView message = null;
		try {
			String decodedMessageBody = URLDecoder.decode(messageBody, "UTF-8");
			message = new MessageDBView(senderId, decodedMessageBody);
			message.setRecipient(receiverID);
			DBManagerInterface dbManager = new DbManager();
			MessageDBView savedMessage = dbManager.create(message);
			if(savedMessage!=null)
				return true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Collection<Message> getNewMessagesForUser(Integer userId) {
		DBManagerInterface dbManager = new DbManager();
		Collection<Message> messages = dbManager.getNewMessagesForUser(userId);
		/*for (Message message : messages) {
			dbManager.setReadStatusForMessage(message);
		}*/
		return messages;
	}

	@Override
	public Collection<Message> getChatWithUser(Integer userId, Integer currentUserId) {
		DBManagerInterface dbManager = new DbManager();
		List<Message> messages = (List<Message>) dbManager.getChatWithUser(userId, currentUserId);
		messages.sort((Message mes1, Message mes2) -> mes2.getTimestamp().compareTo(mes1.getTimestamp()));
		for (Message message : messages) {
			if(message.getSender().getId()!=currentUserId)
				dbManager.setReadStatusForMessage(message);
		}
		if(messages.size() > 10){
			return messages.subList(messages.size() - 11, messages.size()-1);
		}
		return messages;
	}

}

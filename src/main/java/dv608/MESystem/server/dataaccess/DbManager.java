package dv608.MESystem.server.dataaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import auxilary.models.MessageDBView;
import auxilary.models.Group;
import auxilary.models.Message;
import auxilary.models.User;

public class DbManager implements DBManagerInterface {
	private DbConnection dbConnection = new DbConnection();

	@Override
	public User create(User user) {
		dbConnection.connect();
		String sql = String.format("INSERT INTO user (username, password) VALUES (\'%1s\', \'%2s\');", 
				user.getUsername(), user.getPassword());
		Integer userId = dbConnection.create(sql);
		dbConnection.close();
		
		if(userId != null){
			user.setId(userId);
			return user;
		} else return null;
	}

	@Override
	public User getUser(Integer id) {
		dbConnection.connect();
		String sql = String.format("SELECT * FROM user WHERE userID=%d;", id);
		ResultSet userRes = dbConnection.get(sql);
		User user = null;
		try {
			while (userRes.next()){
				user = new User(userRes.getString("username"));
				user.setId(userRes.getInt("userID"));
				user.setPassword(userRes.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConnection.close();
		return user;
	}
	
	@Override
	public User getUser(String username) {
		dbConnection.connect();
		String sql = String.format("SELECT * FROM user WHERE username=\'%s\';", username);
		ResultSet userRes = dbConnection.get(sql);
		User user = null;
		try {
			while (userRes.next()){
				user = new User(userRes.getString("username"));
				user.setId(userRes.getInt("userID"));
				user.setPassword(userRes.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Collection<User> getUsers() {
		dbConnection.connect();
		String sql = "SELECT userID, username FROM user;";
		ResultSet userRes = dbConnection.get(sql);
		List<User> users = new ArrayList<>();
		try {
			while (userRes.next()){
				User user = new User(userRes.getString("username"));
				user.setId(userRes.getInt("userID"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConnection.close();
		return users;
	}

	@Override
	public MessageDBView create(MessageDBView message) {
		dbConnection.connect();
		String insertMessageSql = String.format("INSERT INTO message (senderID, text, time) VALUES (%1d, \'%2s\', \'%3s\');", 
				message.getSenderId(), message.getMessageBody(), getCurrentTimeStamp());
		Integer messageId = dbConnection.create(insertMessageSql);
		if(messageId != null){
			message.setId(messageId);
			String insertMessageRecepientSql = String.format("INSERT INTO messagerecepient (messageID, chatID, recepientID, received) VALUES (%1d, %2d, %3d, %4d);", 
				messageId, message.getGroupReceiverId(), message.getReceiverId(), 0);
			dbConnection.create(insertMessageRecepientSql);
			dbConnection.close();
			return message;
		} else return null;
	}

	@Override
	public Collection<Message> getNewMessagesForUser(Integer userId) {
		User receiver = this.getUser(userId);
		dbConnection.connect();
		String sql = String.format("SELECT * FROM messagerecepient WHERE recepientID=%d AND received=0;", userId);
		ResultSet recepientMessagesRes = dbConnection.get(sql);
		List<Message> messages = new ArrayList<>();
		try {
			while (recepientMessagesRes.next()){
				Message message = this.getMessage(recepientMessagesRes.getInt("messageID"));
				receiver.setPassword(null);
				message.setReceiver(receiver);
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		dbConnection.close();
		return messages;
	}
	
	@Override
	public Collection<Message> getChatWithUser(Integer userId, Integer currentUserId) {
		User user = this.getUser(userId);
		user.setPassword(null);
		
		User currentUser = this.getUser(currentUserId);
		currentUser.setPassword(null);
		
		dbConnection.connect();
		String sql = String.format("SELECT * FROM messagerecepient WHERE recepientID=%1$d " +
		"OR recepientID=%2$d;", userId, currentUserId);
		
		ResultSet recepientMessagesRes = dbConnection.get(sql);
		List<Message> messages = new ArrayList<>();
		try {
			while (recepientMessagesRes.next()){
				Message message = this.getMessage(recepientMessagesRes.getInt("messageID"));
				if(message.getSender().getId() == userId){
					message.setReceiver(currentUser);
					messages.add(message);
				}
				else if(message.getSender().getId() == currentUserId) {
					message.setReceiver(user);
					messages.add(message);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		dbConnection.close();
		return messages;
	}

	@Override
	public Message getMessage(Integer messageId){
		dbConnection.connect();
		String sql = String.format("SELECT * FROM message WHERE messageID=%d;", messageId);
		ResultSet messagesRes = dbConnection.get(sql);
		try {
			while (messagesRes.next()){
				Message message = new Message(this.getUser(messagesRes.getInt("senderID")), 
						messagesRes.getString("text"));
				message.getSender().setPassword(null);
				message.setId(messagesRes.getInt("messageID"));
				message.setTimeSended(messagesRes.getDate("time"));
				return message;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean setReadStatusForMessage(Message message){
		dbConnection.connect();
		String sql = String.format("UPDATE messagerecepient SET received=1 WHERE messageID=%1d;", message.getId());
		boolean result = dbConnection.update(sql);
		dbConnection.close();
		return result;
	}

	@Override
	public Group create(Group group) {
		dbConnection.connect();
		String sql = String.format("INSERT INTO groupchat (name) VALUES (\'%s\');", 
				group.getGroupName());
		Integer groupId = dbConnection.create(sql);
		if(groupId != null){
			group.setId(groupId);
			Collection<User> groupUsers = group.getUsers();
			for (User user : groupUsers) {
				String userInGroupSql = String.format("INSERT INTO userinchat (chatID, userID) VALUES (%1d, %2d);", 
						groupId, user.getId());
				dbConnection.create(userInGroupSql);
			}
			dbConnection.close();
			return group;
		}
		return null;
	}

	@Override
	public Group getGroup(Integer groupId) {
		dbConnection.connect();
		String sql = String.format("SELECT * FROM groupchat WHERE chatID=%d;", groupId);
		ResultSet groupRes = dbConnection.get(sql);
		Group group = null;
		try {
			while (groupRes.next()){
				group = new Group();
				group.setId(groupRes.getInt("chatID"));
				group.setGroupName(groupRes.getString("name"));
				group.setUsers(getUsersInGroup(group.getGroupId()));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return group;
	}
	
	private Collection<User> getUsersInGroup(Integer groupId) {
		dbConnection.connect();
		String sql = String.format("SELECT userID FROM userinchat WHERE chatID=%1d;", groupId);
		ResultSet userRes = dbConnection.get(sql);
		List<User> users = new ArrayList<>();
		try {
			while (userRes.next()){
				users.add(this.getUser(userRes.getInt("userID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public Collection<Group> getGroupsOfUser(Integer userId) {
		dbConnection.connect();
		String sql = String.format("SELECT * FROM userinchat WHERE userID=%d;", userId);
		ResultSet groupsRes = dbConnection.get(sql);
		List<Group> groups = new ArrayList<>();
		try {
			while (groupsRes.next()){
				groups.add(this.getGroup(groupsRes.getInt("chatID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return groups;
	}

	private static String getCurrentTimeStamp() {
	    return LocalDateTime.now()
	    	       .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}

	@Override
	public Collection<Message> getChatWithGroup(Integer groupId) {
		//User receiver = this.getUser(userId);
		dbConnection.connect();
		String sql = String.format("SELECT messageID FROM messagerecepient WHERE chatID=%d", groupId);
		ResultSet recepientMessagesRes = dbConnection.get(sql);
		List<Message> messages = new ArrayList<>();
		try {
			while (recepientMessagesRes.next()){
				Message message = this.getMessage(recepientMessagesRes.getInt("messageID"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return messages;
	}
}

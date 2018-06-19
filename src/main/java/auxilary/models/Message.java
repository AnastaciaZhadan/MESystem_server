package auxilary.models;

import java.util.Date;

public class Message {
	private Integer id;
	private User sender;
    private User receiver;
    private Group groupReceiver;
    private String messageBody;
    private Date timeSended;
    
    public Message(User sender, String messageBody){
    	this.sender = sender;
    	this.messageBody = messageBody;    	
    }

    public void setId(Integer messageId){
    	this.id = messageId;
    }
    
    public void setReceiver(User receiver){
        this.receiver = receiver;
    }

    public void setTimeSended(Date time){
        this.timeSended = time;
    }
    
    public Integer getId(){
    	return id;
    }

    public User getSender(){
        return sender;
    }

    public String getMessageBody(){
        return messageBody;
    }

    public Date getTimestamp(){
        return timeSended;
    }
     
    public User getReceiver(){
    	 return receiver;
    }
    
    public Group getGroupReceiver(){
    	return groupReceiver;
    }
}

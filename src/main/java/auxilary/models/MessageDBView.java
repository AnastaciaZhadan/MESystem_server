package auxilary.models;

public class MessageDBView {
	private Integer messageId;
	private Integer senderId;
    private Integer receiverId;
    private Integer groupReceiverId;
    private String messageBody;
    private String timeSended;
    
    public MessageDBView(Integer senderId, String messageBody){
    	this.senderId = senderId;
    	this.messageBody = messageBody;
    }

    public void setId(Integer messageId){
    	this.messageId = messageId;
    }
    
    public void setRecipient(Integer receiverId){
    	this.receiverId = receiverId;
    }
    
    public void setGroupReceiver(Integer groupId){
    	this.groupReceiverId = groupId;
    }
    
    public void setTimeSended(String time){
        this.timeSended = time;
    }

    public Integer getSenderId(){
        return senderId;
    }

    public String getMessageBody(){
        return messageBody;
    }

    public String getTimestamp(){
        return timeSended;
    }
     
    public Integer getReceiverId(){
    	 return receiverId;
    }
    
    public Integer getGroupReceiverId(){
    	return groupReceiverId;
    }
    
}

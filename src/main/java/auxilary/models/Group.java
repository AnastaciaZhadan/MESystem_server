package auxilary.models;

import java.util.Collection;
import java.util.List;

public class Group {
	private Integer groupId;
    private String groupName;
    private Collection<User> users;
    
    public Group(){
    	
    }
    
    public Group(String groupName, Collection<User> users){
    	this.groupName = groupName;
    	this. users = users;
    }
    
    public void setId(Integer groupId){
    	this.groupId = groupId;
    }
    
    public void setUsers(Collection<User> users){
    	this.users = users;
    }
    
    public void setGroupName(String groupName){
    	this.groupName = groupName;
    }
    
    public String getGroupName(){
    	return groupName;
    }
    
    public Integer getGroupId(){
    	return groupId;
    }
    
    public Collection<User> getUsers(){
    	return users;
    }
}

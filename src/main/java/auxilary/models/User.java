package auxilary.models;

public class User {
	private Integer id;
    private String name;
    private String password;
    
    public User(String username){
    	this.name = username;
    }
    
    public String getUsername(){
        return name;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }
    
    public void setPassword(String password){
    	this.password = password;
    }
    
    public String getPassword(){
    	return password;
    }
}

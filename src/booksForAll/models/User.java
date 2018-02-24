package booksForAll.models;

public class User {
	private String userName;
	private String password;
	private boolean isAdmin;
	private String NickName;
	private String email;
	private String telephone;
	private String description;
	private String photoUrl;
	private boolean status;
	
	public User(String userName,String password,boolean isAdmin,String NickName,String email,String telephone,String description,String photoUrl,boolean status) {
		this.userName=userName;
		this.password = password;
		this.isAdmin=isAdmin;
		this.NickName=NickName;
		this.email=email;
		this.telephone=telephone;
		this.description=description;
		this.photoUrl=photoUrl;
		this.status=status;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getNickName() {
		return NickName;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	public String getEmail() {
		return email;
	}
	
	
	public String getTelephone() {
		return telephone;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status=status;
	}
}

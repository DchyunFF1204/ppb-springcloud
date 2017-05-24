package com.ppb.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * <br>
 * <b>功能：</b>Ofuser 实体类<br>
 * <b>作者：</b>daizy<br>
 * <b>日期：</b> 2017-05-24 14:45:34 <br>
 */
@SuppressWarnings("serial")
public class Ofuser implements Serializable {
	
	/**
	 * 
	 */
	private Integer id;
	
	/**
	 * 
	 */
	private String username;
	
	/**
	 * 
	 */
	private String plainpassword;
	
	/**
	 * 
	 */
	private String encryptedpassword;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private String email;
	
	/**
	 * 
	 */
	private String creationdate;
	
	/**
	 * 
	 */
	private String modificationdate;
	
	/**
	 * 
	 */
	private Date opTime;
	
	public Integer getId(){
	   return id;
	}
	 
	public void setId(Integer id){
	    this.id = id;
	}
	 
	public String getUsername(){
	   return username;
	}
	 
	public void setUsername(String username){
	    this.username = username;
	}
	 
	public String getPlainpassword(){
	   return plainpassword;
	}
	 
	public void setPlainpassword(String plainpassword){
	    this.plainpassword = plainpassword;
	}
	 
	public String getEncryptedpassword(){
	   return encryptedpassword;
	}
	 
	public void setEncryptedpassword(String encryptedpassword){
	    this.encryptedpassword = encryptedpassword;
	}
	 
	public String getName(){
	   return name;
	}
	 
	public void setName(String name){
	    this.name = name;
	}
	 
	public String getEmail(){
	   return email;
	}
	 
	public void setEmail(String email){
	    this.email = email;
	}
	 
	public String getCreationdate(){
	   return creationdate;
	}
	 
	public void setCreationdate(String creationdate){
	    this.creationdate = creationdate;
	}
	 
	public String getModificationdate(){
	   return modificationdate;
	}
	 
	public void setModificationdate(String modificationdate){
	    this.modificationdate = modificationdate;
	}
	 
	public Date getOpTime(){
	   return opTime;
	}
	 
	public void setOpTime(Date opTime){
	    this.opTime = opTime;
	}
	 
}


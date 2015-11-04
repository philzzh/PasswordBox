package com.example.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Password_entity")
public class PasswordEntity {

	
	public PasswordEntity() {
		// TODO Auto-generated constructor stub
	}
	
	@DatabaseField(generatedId = true)
	private int EntityId;
	
	@DatabaseField
	private String EntityName;
	
	@DatabaseField
	private String EntityPassword;
	
	@DatabaseField
	private String EntityDiscription;

	public int getEntityId() {
		return EntityId;
	}

	public void setEntityId(int entityId) {
		EntityId = entityId;
	}

	public String getEntityName() {
		return EntityName;
	}

	public void setEntityName(String entityName) {
		EntityName = entityName;
	}

	public String getEntityPassword() {
		return EntityPassword;
	}

	public void setEntityPassword(String entityPassword) {
		EntityPassword = entityPassword;
	}

	public String getEntityDiscription() {
		return EntityDiscription;
	}

	public void setEntityDiscription(String entityDiscription) {
		EntityDiscription = entityDiscription;
	}
	
	

}

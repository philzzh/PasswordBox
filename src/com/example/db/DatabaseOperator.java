package com.example.db;

import java.util.List;

import com.example.bean.PasswordEntity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class DatabaseOperator {
	
	private RuntimeExceptionDao<PasswordEntity, Integer> entityDao;
	
	public DatabaseOperator() {
	}
	
	public DatabaseOperator(RuntimeExceptionDao<PasswordEntity, Integer> entityDao) {
		this.entityDao = entityDao;
	}
	
	public List<PasswordEntity> queryEntityList(PasswordEntity passwordEntity) {
		
		List<PasswordEntity> passwordEntityList;

		if (passwordEntity == null) {
			passwordEntityList = entityDao.queryForAll();
		} else {
			passwordEntityList = entityDao.queryForMatching(passwordEntity);
		}
		
		return passwordEntityList;
	}
}

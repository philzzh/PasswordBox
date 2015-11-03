package com.example.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bean.PasswordEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "PasswordBox.db";
	private static final int DATABASE_VERSION = 1;
	
	// the DAO object we use to access the SimpleData table
	private Dao<PasswordEntity, Integer> EntityDao = null;
	private RuntimeExceptionDao<PasswordEntity, Integer> EntityRuntimeDao = null;
	

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, PasswordEntity.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 */
	public Dao<PasswordEntity, Integer> getDao() throws SQLException {
		if (EntityDao == null) {
			EntityDao = getDao(PasswordEntity.class);
		}
		return EntityDao;
	}
	
	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<PasswordEntity, Integer> getEntityDao() {
		if (EntityRuntimeDao == null) {
			EntityRuntimeDao = getRuntimeExceptionDao(PasswordEntity.class);
		}
		return EntityRuntimeDao;
	}
	
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		EntityRuntimeDao = null;
	}


	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, PasswordEntity.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

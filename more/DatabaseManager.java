package com.taazaa.registerationapp;

import java.util.ArrayList;
import java.util.Collections;

import com.taazaa.registerationapp.utils.Users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class DatabaseManager extends SQLiteOpenHelper {  

	private static final int	DATABASE_VERSION= 2;  

	private static final String DATABASE_NAME 	= "userManager";  
	private static final String TABLE_USERS 	= "users";  
	private static final String KEY_USERNAME 	= "username";  
	private static final String KEY_USER_ID 	= "user_id";  
	private static final String KEY_FIRSTNAME 	= "firstname";
	private static final String KEY_LASTNAME 	= "lastname";
	private static final String KEY_ROLL_NO 	= "roll_number";
	private static final String KEY_CITY 		= "city";
	private static final String KEY_PASSWORD 	= "password";
	private static final String KEY_USER_IMAGE 	= "user_image";

	private SQLiteDatabase database;

	public DatabaseManager(Context context) {  
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		String CREATE_USERS_TABLE;

		CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("+ KEY_USER_ID + " INTEGER PRIMARY KEY, " + KEY_USERNAME + " TEXT, " + KEY_FIRSTNAME + " TEXT, " + KEY_LASTNAME + " TEXT, "
				+ KEY_ROLL_NO + " TEXT, "  + KEY_CITY + " TEXT," + KEY_PASSWORD + " TEXT," + KEY_USER_IMAGE + " TEXT" +")";

		database.execSQL(CREATE_USERS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

		String ALTER_USERS_TABLE = "ALTER TABLE "+ TABLE_USERS + " ADD COLUMN " + KEY_USER_IMAGE +" TEXT";  

		if(oldVersion < 2){

			database.execSQL(ALTER_USERS_TABLE);
		}

	}

	public boolean loginUser(String userName, String password) {

		database = this.getReadableDatabase(); 

		Cursor cursor = database.query(TABLE_USERS, new String[] { KEY_USERNAME, KEY_PASSWORD }, KEY_USERNAME + "=?", new String[] { userName }, null, null, null, null);

		if (cursor != null && cursor.getCount() > 0) {

			cursor.moveToFirst();

			if(userName.equals(cursor.getString(0))&& password.equals(cursor.getString(1)))
				return true; 
		}

		return false;
	}


	public void registerUser(Users user) {

		database = getReadableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_USERNAME , user.getUserName());
		values.put(KEY_FIRSTNAME, user.getFirstName());
		values.put(KEY_LASTNAME , user.getLastName());
		values.put(KEY_ROLL_NO 	, user.getRollNum());
		values.put(KEY_CITY 	, user.getCity());
		values.put(KEY_PASSWORD , user.getPassword());
		values.put(KEY_USER_IMAGE , user.getUserImage());

		database.insert(TABLE_USERS, null, values);

		database.close();
	}


	public Users getUser(String userName){

		database = this.getReadableDatabase();  

		Cursor cursor = database.query(TABLE_USERS, new String[] { KEY_USER_ID, KEY_USERNAME, KEY_FIRSTNAME, KEY_LASTNAME, KEY_ROLL_NO, KEY_CITY, KEY_USER_IMAGE}, KEY_USERNAME + "=?", 
				new String[] { userName }, null, null, null, null);


		if (cursor != null)  
			cursor.moveToFirst(); 

		Users user = new Users ();

		user.setUserId(cursor.getInt(0));
		user.setUserName(cursor.getString(1));
		user.setFirstName(cursor.getString(2));
		user.setLastName(cursor.getString(3));
		user.setRollNum(cursor.getString(4));
		user.setCity(cursor.getString(5));
		user.setUserImage(cursor.getString(6));

		return user;
	}


	public ArrayList<Users> getAllUsersData(){

		database = this.getReadableDatabase();  

		Cursor cursor = database.query(TABLE_USERS, null, null, null, null, null, null, null);


		ArrayList<Users> allUsers = new ArrayList<Users>();

		if (cursor != null)  
			cursor.moveToFirst(); 

		Users user;

		while(cursor.moveToNext()){

			user  = new Users ();
			user.setUserId(cursor.getInt(0));
			user.setUserName(cursor.getString(1));
			user.setFirstName(cursor.getString(2));
			user.setLastName(cursor.getString(3));
			user.setRollNum(cursor.getString(4));
			user.setCity(cursor.getString(5));
			user.setUserImage(cursor.getString(6));

			allUsers.add(user);
		}
		return allUsers;

	}
	
	public boolean deleteUsers(String userId){
		
		database = this.getReadableDatabase();
		
		String DELETE_USERS_DATA = "DELETE FROM "+ TABLE_USERS + " WHERE " + KEY_USER_ID +" IN ( "+userId+" )"; 
		database.execSQL(DELETE_USERS_DATA);
		
		return true;
		
	}
}

package com.codepath.todopersist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MySQLiteHelper extends SQLiteOpenHelper{

	// Database details
	private static final String TABLE_TODO 		= "tasks";
	private static final String COLUMN_ID 		= "id";
	private static final String COLUMN_TASK 		= "task";
	private static final String COLUMN_DATE 		= "date";
	private static final String COLUMN_PRIORITY 	= "priority";
	private static final String COLUMN_COMP 		= "completed";
	
	public MySQLiteHelper(Context context, String name, CursorFactory cursor, int version) {
		super(context, name, cursor, version);
	}
	
	//Create db
	@Override
    public void onCreate(SQLiteDatabase db) {
		String CREATE_TODO_TABLE = "CREATE TABLE " +
	             TABLE_TODO + "("
	             + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_TASK 
	             + " TEXT," + COLUMN_PRIORITY+ " INTEGER," + COLUMN_DATE + " STRING," + COLUMN_COMP + " INTEGER" + ")";
				
        db.execSQL(CREATE_TODO_TABLE);
    }
	
	//Upgrade db
	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
	}
} 
package com.codepath.todopersist;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
 
public class SQLHandler {
	private static final String TABLE_TODO 		= "tasks";
	private static final String COLUMN_ID 		= "id";
	private static final String COLUMN_TASK 		= "task";
	private static final String COLUMN_DATE 		= "date";
	private static final String COLUMN_PRIORITY 	= "priority";
	private static final String COLUMN_COMP 		= "completed";
	private static final String DATABASE			= "todo_tasks.db";
	private static final int DATABASE_VERSION 		= 1;
	Context context;
	SQLiteDatabase sqlDatabase;
	MySQLiteHelper dbHelper;
 
	public SQLHandler(Context context) {
		dbHelper = new MySQLiteHelper(context, DATABASE, null, DATABASE_VERSION);
		sqlDatabase = dbHelper.getWritableDatabase();
	}
 
	public void executeQuery(String query) {
		try {
			if (sqlDatabase.isOpen()) {
				sqlDatabase.close();
			}
			sqlDatabase = dbHelper.getWritableDatabase();
			sqlDatabase.execSQL(query); 
		} catch (Exception e) {
			System.out.println("DATABASE ERROR " + e);
		} 
	}
 
	public Cursor selectQuery(String query) {
		Cursor c1 = null;
		try {
			if (sqlDatabase.isOpen()) {
				sqlDatabase.close();
			}
			sqlDatabase = dbHelper.getWritableDatabase();
			c1 = sqlDatabase.rawQuery(query, null); 
		} catch (Exception e) {
			System.out.println("DATABASE ERROR " + e);
		}
		return c1;
	}
	
	//CRUD operations
	Task getTask(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_TODO, new String[] { COLUMN_ID,
                COLUMN_TASK, COLUMN_DATE, COLUMN_PRIORITY, COLUMN_COMP}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Task task = new Task(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), 
                cursor.getString(2), 
                Integer.parseInt(cursor.getString(3)), 
                Integer.parseInt(cursor.getString(4)));
        return task;
    }
	
	void addTask(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, task.getTask()); 
        values.put(COLUMN_DATE, ""); 
        values.put(COLUMN_PRIORITY, 0); 
        values.put(COLUMN_COMP, 0); 
 
        db.insert(TABLE_TODO, null, values);
        db.close();
    }
	
	public int updateTask(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, task.getTask()); 
        values.put(COLUMN_DATE, task.getDate()); 
        values.put(COLUMN_PRIORITY, task.getPriority()); 
        values.put(COLUMN_COMP, task.getCompleted());
 
        // updating row
        return db.update(TABLE_TODO, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }
 
    public void deleteTask(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_TODO, COLUMN_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
        db.close();
    }
    
    public void deleteAll(){
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_TODO +"");
        db.close();
    }
    
    // Get all records
    public ArrayList<Task> getAllTasks() {
		ArrayList<Task> taskList = new ArrayList<Task>();

        String selectQuery = "SELECT  * FROM " + TABLE_TODO;
 
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTask(cursor.getString(1));
                task.setDate(cursor.getString(2));
                task.setPriority(Integer.parseInt(cursor.getString(3)));
                task.setCompleted(Integer.parseInt(cursor.getString(4)));
                
                taskList.add(task);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return taskList;
    }
 
}
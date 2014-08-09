package com.codepath.todopersist;

import java.util.ArrayList;

import com.codepath.todopersist.EditTaskDialog.EditTaskDialogListener;
import com.codepath.todopersist.R;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.AdapterView;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ToDoActivity extends FragmentActivity  implements EditTaskDialogListener{	
	SQLHandler sqlHandler;
	ListView lvTasks;
	EditText newTask;
	Button addButton;
	TextView emptyView;
	ArrayList<Task> taskList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		
		lvTasks=(ListView) findViewById(R.id.lvTasksList);
		newTask= (EditText) findViewById(R.id.newTask);
		newTask.setTextColor(Color.parseColor("#FFEEEEEE"));  
		addButton = (Button) findViewById(R.id.btnAddNewTask);
		addButton.setBackgroundColor(Color.parseColor("#FF0099CC"));
		addButton.setTextColor(Color.parseColor("#FFEEEEEE"));
		sqlHandler = new SQLHandler(this);
		emptyView = (TextView)findViewById(R.id.empty);
		lvTasks.setEmptyView(emptyView);
		lvTasks.setBackgroundColor(Color.LTGRAY);
		lvTasks.getBackground().setAlpha(95);
		registerForContextMenu(lvTasks);
		showList();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.item_menu, menu);
	}
	
	@Override  
	public boolean onContextItemSelected(MenuItem item) {  
        AdapterView.AdapterContextMenuInfo task = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); 
        Task taskToDelete, taskToUpdate;
    	switch(item.getItemId()){  
             case R.id.delete_item:  
            	 taskToDelete = taskList.get((int) task.id);
            	 deleteTask(taskToDelete);
                 return true;
             case R.id.change_priority:
            	 taskToUpdate= taskList.get((int) task.id);
            	 taskToUpdate.alertPriotiry(taskToUpdate.getPriority());
            	 updateTask(taskToUpdate);
            	 return true;
             case R.id.mark_complete:
            	 taskToUpdate= taskList.get((int) task.id);
            	 taskToUpdate.alertCompleted(taskToUpdate.getCompleted());
            	 updateTask(taskToUpdate);
            	 return true;
             case R.id.edit_task:
            	 taskToUpdate= taskList.get((int) task.id);
            	 showEditDialog(taskToUpdate);
            	 showList();
            	 return true;
        }  
    	return super.onContextItemSelected(item);  
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showEditDialog(Task task) {
		FragmentManager fm = getFragmentManager();
	    EditTaskDialog editTaskDialog = EditTaskDialog.newInstance("Edit Task", task);
	    editTaskDialog.show(fm, "EditTaskDialog");      
	}
	
	public void onDialogPositiveClick(DialogFragment dialog, Task taskToBeUpdated) {
		updateTask(taskToBeUpdated);
    }

    public void onDialogNegativeClick(DialogFragment dialog) {
    	dialog.dismiss();
    }

	public void addTodoItem(View v){
		EditText etNewItem = (EditText) findViewById(R.id.newTask);
		Task newTask = new Task(etNewItem.getText().toString());
		sqlHandler.addTask(newTask);
		etNewItem.setText("");
		showList();
	}
	
	public void deleteTask(Task task){
		sqlHandler.deleteTask(task);
		showList();
	}
	
	public void deleteAllTasks(){
		sqlHandler.deleteAll();
		showList();
	}
	
	public void updateTask(Task task){
		sqlHandler.updateTask(task);
		showList();
	}
	
	private void showList(){
		taskList = new ArrayList<Task>();
		taskList.clear();
			String query = "SELECT * FROM tasks ORDER BY completed ASC";
			Cursor c1 = sqlHandler.selectQuery(query);
			if (c1 != null && c1.getCount() != 0) {
				if (c1.moveToFirst()) {
					do {
						Task task = new Task();
						task.setId(c1.getInt(c1.getColumnIndex("id")));
						task.setTask(c1.getString(c1.getColumnIndex("task")));
						task.setDate(c1.getString(c1.getColumnIndex("date")));
						task.setPriority(c1.getInt(c1.getColumnIndex("priority")));
						task.setCompleted(c1.getInt(c1.getColumnIndex("completed")));
						taskList.add(task);
					} while (c1.moveToNext());
				}
			}
			c1.close();				 
			CustomAdapter customAdapter = new CustomAdapter(ToDoActivity.this, taskList);
			lvTasks.setAdapter(customAdapter);	 
	}
}

//Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();


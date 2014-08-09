package com.codepath.todopersist;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	Context context;
	ArrayList<Task> taskList;
	
    /*Constructor*/
	public CustomAdapter(Context context, ArrayList<Task> list) {	 
		  this.context = context;
		  taskList = list;
	}
    
	@Override
    public int getCount() {         
    	 return taskList.size();
    }
	
	@Override
    public Object getItem(int position) {
	     return taskList.get(position);
    }
 
	@Override
    public long getItemId(int position) {
        return position;
    }
    
    /*Create each ListView row */
    public View getView(int position, View convertView, ViewGroup parent) {
    	Task task = taskList.get(position);

    	if (convertView == null) {
    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		convertView = inflater.inflate(R.layout.task_layout, null);
    	  }

    	ImageView thisComplete = (ImageView) convertView.findViewById(R.id.ic_check);
    	
    	if(task.getCompleted() == 0){
    		thisComplete.setVisibility(View.GONE);
    	} else {
    		thisComplete.setVisibility(View.VISIBLE);    		
    	}
    	
		ImageView thisPriority = (ImageView) convertView.findViewById(R.id.ic_imp);
    	if(task.getPriority() == 0){
    		thisPriority.setVisibility(View.GONE);
    	} else {
    		thisPriority.setVisibility(View.VISIBLE);
    	}
    	
    	TextView thisTask = (TextView) convertView.findViewById(R.id.task);
    	thisTask.setText(task.getTask());
    	TextView thisDate = (TextView) convertView.findViewById(R.id.date);
    	thisDate.setText(task.getDate());    	 
    	return convertView;
    }    
}

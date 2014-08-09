package com.codepath.todopersist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ToggleButton;

public class EditTaskDialog extends DialogFragment{

	private DatePicker date;
	private EditText task;
	private ToggleButton priority;

	public EditTaskDialog() {
		// Empty constructor required for DialogFragment
	}
	
	public interface EditTaskDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, Task task);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

	EditTaskDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the Listener
            mListener = (EditTaskDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement EditTaskDialogListener");
        }
    }
    
	public static EditTaskDialog newInstance(String title, Task task) {
		EditTaskDialog frag = new EditTaskDialog();
		Bundle args = new Bundle();
		args.putInt("id", task.getId());
		args.putString("task", task.getTask());
		args.putInt("completed", task.getCompleted());
		args.putInt("priority", task.getPriority());
		args.putString("date", task.getDate());
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View view = View.inflate(getActivity(), R.layout.dialog, null);
		builder.setView(view).
		setPositiveButton(R.string.update_button, new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int id) {
	            // Send the positive button event back to the calling activity
	        	Task taskToBeUpdated = new Task();
	        	taskToBeUpdated.setTask(task.getText().toString());
	        	taskToBeUpdated.setId(getArguments().getInt("id"));
	        	taskToBeUpdated.setPriority((priority.isChecked())? 1 : 0);
	        	taskToBeUpdated.setCompleted(getArguments().getInt("completed"));
	        	
	        	Integer year = date.getYear();
	            Integer month = date.getMonth();
	            Integer day = date.getDayOfMonth();
	        	
	            
	            StringBuilder sbDate=new StringBuilder(month.toString()).append("/").append(day.toString()).append("/").append(year.toString());
	            String taskDate=sbDate.toString();
	        	taskToBeUpdated.setDate(taskDate);
	        	
                mListener.onDialogPositiveClick(EditTaskDialog.this, taskToBeUpdated);
	        }
	    })
	    .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int id) {
	            // Send the negative button event back to the calling activity
                mListener.onDialogNegativeClick(EditTaskDialog.this);
	        }
	    });
		
		String[] taskDate;
		
		task= (EditText) view.findViewById(R.id.editTaskItem);
		date = (DatePicker) view.findViewById(R.id.date);
		priority = (ToggleButton) view.findViewById(R.id.togglePriority);

		task.setText(getArguments().getString("task"));

		priority.setChecked((getArguments().getInt("priority")==1)? true : false);
		
		if(!getArguments().getString("date").isEmpty()){
			taskDate = getArguments().getString("date").split("/");;
			date.updateDate(Integer.parseInt(taskDate[2]), Integer.parseInt(taskDate[0]), Integer.parseInt(taskDate[1]));
		}
		
	    return builder.create();	
		
	}
}

package com.example.todo.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.todo.R;
import com.example.todo.database.DatabaseHandler;
import com.example.todo.database.Task;
import com.example.todo.model.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private DatabaseHandler databaseHandler;
    private Button saveButton;
    private EditText taskName;
    private EditText timeDuration;

    private TaskViewModel mTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTaskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(TaskViewModel.class);
        //databaseHandler = new DatabaseHandler(this);
        //byPassActivity();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });
    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        taskName = view.findViewById(R.id.popup_name);
        timeDuration = view.findViewById(R.id.popup_time_duration);
        saveButton = view.findViewById(R.id.popup_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!taskName.getText().toString().isEmpty()
                && !timeDuration.getText().toString().isEmpty()) {
                    saveTask(v);
                } else {
                    Snackbar.make(v, "Empty fields not allowed", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void saveTask(View view) {
        Task task = new Task();

        String newTaskName = taskName.getText().toString().trim();
        int newTimeDuration = Integer.parseInt(timeDuration.getText().toString().trim());

        task.setNameOfTask(newTaskName);
        task.setTimeDuration(newTimeDuration);
        mTaskViewModel.insert(task);
        //databaseHandler.addTask(task);
        Snackbar.make(view, "Item Saved", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200);
    }

    private void byPassActivity() {
        if(databaseHandler.getTaskCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


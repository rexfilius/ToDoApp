package com.example.todo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todo.model.Task;

import java.util.List;

public interface TaskDao {

    @Insert
    void insert(Task task);

    @Query("DELETE from task_table where id = :id")
    int deleteTask(int id);

    @Query("UPDATE task_table SET task_name_col = :taskNameText WHERE id = :id")
    int updateTask(int id, String taskNameText);

    @Query("SELECT * from task_table ORDER BY date_col DESC")
    LiveData<List<Task>> getAllTask();

    @Query("DELETE from task_table")
    void deleteAll();

    @Query("SELECT * from task_table")
    int getTaskCount();


}
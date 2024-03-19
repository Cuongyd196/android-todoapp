package com.example.todoapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<MyTodo> listTodo; //Mảng dữ liệu
    TodoListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listTodo = new ArrayList<>();
        ListView listviewTodo = findViewById(R.id.listviewTodo);

        listTodo.add(new MyTodo(1,"t", "e", "f", false));
        adapter = new TodoListViewAdapter(this, R.layout.todo_item, listTodo);
        listviewTodo.setAdapter(adapter);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("todo")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // Lấy đối tượng todo từ Bundle
                MyTodo todoItem = (MyTodo) bundle.getSerializable("todo");
                if (todoItem != null) {
                    listTodo.add(todoItem);
                    if (adapter == null) {
                        adapter = new TodoListViewAdapter(this, R.layout.todo_item, listTodo);
                        listviewTodo.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        int id = item.getItemId();
        if (id == R.id.createTodo) {
            createTodo();
            return true;
        }
        if (id == R.id.searchTodo) {
            searchTodo();
            return true;
        }
        if (id == R.id.aboutApp) {
            Toast.makeText(this, "Todo App version 1.0", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.exitApp) {
            System.exit(0);
            return true;
        }
        // Handle other menu item clicks if needed
        return super.onOptionsItemSelected(item);
    }
    public void createTodo(){
        // Tạo Intent để chuyển đến Activity mới
        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Menu Item is createTodo", Toast.LENGTH_SHORT).show();
    }
    public void searchTodo(){
        Toast.makeText(this, "Menu Item is searchTodo", Toast.LENGTH_SHORT).show();
    }


}
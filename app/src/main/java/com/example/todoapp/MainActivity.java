package com.example.todoapp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listviewTodo = findViewById(R.id.listviewTodo);
        db = new DBHelper(this, "todo.sqlite", null, 1);
        listTodo = db.getAllToDos();
        adapter = new TodoListViewAdapter(this, R.layout.todo_item, listTodo);
        listviewTodo.setAdapter(adapter);
        DanhsachCV();
    }



    public void DanhsachCV(){
        listTodo.clear();
        listTodo.addAll(db.getAllToDos());
        this.adapter.notifyDataSetChanged();
    }

    public void editTodo(int idTodo){
        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
        intent.putExtra("idTodo", idTodo); // Đặt dữ liệu vào Intent với key-value pair
        startActivity(intent);
    }

    public void deleteTodo(int idTodo, String tencv){
        AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.setMessage("Bạn có muốn xóa công việc "+ tencv +" này không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.DeleteByID(idTodo);
                Toast.makeText(MainActivity.this,"Đã xóa công việc: " +tencv,Toast.LENGTH_LONG).show();
                DanhsachCV();
            }
        });
        dialog.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
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
        intent.putExtra("idTodo", -1); // Đặt dữ liệu vào Intent với key-value pair
        startActivity(intent);
    }
    public void searchTodo(){
//        Toast.makeText(this, "Menu Item is searchTodo", Toast.LENGTH_SHORT).show();
    }


}
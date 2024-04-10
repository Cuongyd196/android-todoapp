package com.example.todoapp;
import android.content.Intent;
import android.os.Bundle;
import android.app.TimePickerDialog;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AddTodoActivity extends AppCompatActivity {
    EditText editTextTime, editTextTitle, editTextCategory;
    CheckBox checkBoxCompleted;
    Calendar calendar;
    DBHelper db;
    Button buttonSave;
    Button buttonBack;
    int idTodo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_todo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DBHelper(this, "todo.sqlite", null, 1);
        editTextTime = findViewById(R.id.editTextTime);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextCategory = findViewById(R.id.editTextCategory);
        checkBoxCompleted = findViewById(R.id.checkBoxCompleted);
        editTextTime.setOnClickListener(view -> showDateTimePickerDialog());
        calendar = Calendar.getInstance();
        buttonSave = findViewById(R.id.buttonSave);
        buttonBack = findViewById(R.id.buttonBack);

        Intent intent = getIntent();

        if (intent.hasExtra("idTodo")) {
            idTodo = intent.getIntExtra("idTodo", -1);
        }

        if (idTodo != -1) {
            buttonSave.setText("Sửa");
            MyTodo myTodo = db.FindByID(idTodo);
            if (myTodo != null) {
                editTextTime.setText(myTodo.getThoiGian());
                editTextTitle.setText(myTodo.getTenCV());
                editTextCategory.setText(myTodo.getDanhMuc());
                checkBoxCompleted.setChecked(myTodo.isHoanThanh());
            }
        }

        buttonSave.setOnClickListener(view -> {

            if ( idTodo == -1){
                // Tạo một đối tượng TodoItem từ dữ liệu nhập vào
                MyTodo todoItem = new MyTodo();
                todoItem.TenCV = editTextTitle.getText().toString();
                todoItem.DanhMuc = editTextCategory.getText().toString();
                todoItem.ThoiGian = editTextTime.getText().toString();
                todoItem.HoanThanh =  checkBoxCompleted.isChecked();
                db.addToDo(todoItem);
                Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            }else {
                 MyTodo todoItem = db.FindByID(idTodo);
                 todoItem.setTenCV(editTextTitle.getText().toString());
                 todoItem.setDanhMuc(editTextCategory.getText().toString());
                 todoItem.setThoiGian(editTextTime.getText().toString());
                 todoItem.setHoanThanh(checkBoxCompleted.isChecked());
                 db.UpdateByID(todoItem);
                 Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            }
            // Tạo Intent để quay lại MainActivity
            Intent intentReturn = new Intent(AddTodoActivity.this, MainActivity.class);
            startActivity(intentReturn);
            finish();
        });

        buttonBack.setOnClickListener(view -> {
            // Tạo Intent để quay lại MainActivity
            Intent intentReturn = new Intent(AddTodoActivity.this, MainActivity.class);
            startActivity(intentReturn);
            finish();
        });
    }
    private void showDateTimePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    new TimePickerDialog(AddTodoActivity.this,
                            (timePicker, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                // Format và hiển thị ngày và giờ đã chọn
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                                editTextTime.setText(simpleDateFormat.format(calendar.getTime()));
                            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
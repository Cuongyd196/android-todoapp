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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AddTodoActivity extends AppCompatActivity {
    EditText editTextTime, editTextTitle, editTextCategory;
    CheckBox checkBoxCompleted;
    Calendar calendar;

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
        editTextTime = findViewById(R.id.editTextTime);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextCategory = findViewById(R.id.editTextCategory);

        editTextTime.setOnClickListener(view -> showDateTimePickerDialog());
        calendar = Calendar.getInstance();

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(view -> {
            // Tạo một đối tượng TodoItem từ dữ liệu nhập vào
            MyTodo todoItem = new MyTodo(
                    1,
                    editTextTitle.getText().toString(),
                    editTextCategory.getText().toString(),
                    editTextTime.getText().toString(),
                    checkBoxCompleted.isChecked()
            );
            // Tạo Intent để quay lại MainActivity
            Intent intent = new Intent(AddTodoActivity.this, MainActivity.class);
            // Tạo một Bundle và đặt đối tượng todoItem vào Bundle với key là "todo"
            Bundle bundle = new Bundle();
            bundle.putSerializable("todo", (Serializable) todoItem);

            // Đưa Bundle vào Intent
            intent.putExtras(bundle);
            startActivity(intent);
            // Kết thúc AddTodoActivity
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
package com.example.todoapp;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.List;

public class TodoListViewAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<MyTodo> myTodo;

    public TodoListViewAdapter(MainActivity context, int layout, List<MyTodo> myTodo) {
        this.context = context;
        this.layout = layout;
        this.myTodo = myTodo;
    }

    @Override
    public int getCount() {
        return myTodo.size();
    }

    @Override
    public Object getItem(int position) {
        return myTodo.get(position);
    }

    @Override
    public long getItemId(int position) {
        // If MyTodo has a unique identifier, return it here
        // Otherwise, you can return position
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewTodo = convertView;
        if (viewTodo == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            viewTodo = inflater.inflate(layout, parent, false);
        }
        //Bind dữ liệu phần tử vào View
        MyTodo todo = myTodo.get(position);
        TextView txtTenCV = viewTodo.findViewById(R.id.txtTenCV);
        TextView txtThoiGian = viewTodo.findViewById(R.id.txtThoigian);
        CheckBox cbHoanThanh = viewTodo.findViewById(R.id.cbHoanThanh);
        cbHoanThanh.setEnabled(false); // Vô hiệu hóa checkbox
        txtTenCV.setText(String.format("%s", todo.getTenCV()));
        txtThoiGian.setText(String.format("%s", todo.getThoiGian()));

        if (todo.HoanThanh){
            cbHoanThanh.setChecked(true);
            txtTenCV.setPaintFlags(txtTenCV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        // Thiết lập sự kiện nhấn cho mỗi item
        viewTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.editTodo(todo.getID());
            }
        });

        viewTodo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context.deleteTodo(todo.getID(), todo.getTenCV());
                return false;
            }
        });

        return viewTodo;
    }
}

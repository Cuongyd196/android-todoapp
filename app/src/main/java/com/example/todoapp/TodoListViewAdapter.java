package com.example.todoapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        txtTenCV.setText(String.format("%s", todo.getTenCV()));
//        txtThoiGian.setText(String.format("%s", todo.getThoiGian()));

        return viewTodo;
    }
}

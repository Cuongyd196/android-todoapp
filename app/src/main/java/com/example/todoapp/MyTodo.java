package com.example.todoapp;

public class MyTodo {
    public int ID;
    public String TenCV;
    public String DanhMuc;
    public String ThoiGian;
    public boolean HoanThanh;
    public MyTodo(int iD,String tenCV, String danhMuc, String thoiGian, Boolean hoanThanh) {
        this.ID = iD;
        this.TenCV = tenCV;
        this.DanhMuc = danhMuc;
        this.ThoiGian = thoiGian;
        this.HoanThanh= hoanThanh;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenCV() {
        return TenCV;
    }

    public void setTenCV(String tenCV) {
        TenCV = tenCV;
    }

    public String getDanhMuc() {
        return DanhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        DanhMuc = danhMuc;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

    public boolean isHoanThanh() {
        return HoanThanh;
    }

    public void setHoanThanh(boolean hoanThanh) {
        HoanThanh = hoanThanh;
    }
}

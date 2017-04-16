package com.example.hp.attendamce_portal.pojo;

/**
 * Created by hp on 16-Apr-17.
 */

public class Table1List {
    String date;

    public String getIspresent() {
        return ispresent;
    }

    public void setIspresent(String ispresent) {
        this.ispresent = ispresent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String ispresent;

    public Table1List(String date, String ispresent) {
        this.date = date;
        this.ispresent = ispresent;
    }
}

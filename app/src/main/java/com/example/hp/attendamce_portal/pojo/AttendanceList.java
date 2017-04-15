package com.example.hp.attendamce_portal.pojo;

/**
 * Created by hp on 11-Apr-17.
 */

public class AttendanceList {
    String rollno;
    String ispresent;

    public AttendanceList(String rollno, String ispresent) {
        this.ispresent = ispresent;
        this.rollno = rollno;
    }

    public String getIspresent() {
        return ispresent;
    }

    public void setIspresent(String ispresent) {
        this.ispresent = ispresent;
    }


    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }
}

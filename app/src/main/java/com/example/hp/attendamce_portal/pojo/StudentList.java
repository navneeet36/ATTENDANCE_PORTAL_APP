package com.example.hp.attendamce_portal.pojo;

/**
 * Created by hp on 02-Apr-17.
 */

public class StudentList {
    String rollno,name,branchid;
    public StudentList(String rollno, String name,String branchid) {
        this.branchid = branchid;
        this.name = name;
        this.rollno =rollno ;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }
}

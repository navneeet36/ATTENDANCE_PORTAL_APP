package com.example.hp.attendamce_portal.pojo;

/**
 * Created by hp on 02-Apr-17.
 */

public class FacultyList
{
    private String facultyid;
    private String facultyname;
    public FacultyList(String facultyid, String facultyname) {
        this.facultyid = facultyid;
        this.facultyname = facultyname;

    }

    public String getFacultyid() {
        return facultyid;
    }

    public void setFacultyid(String facultyid) {
        this.facultyid = facultyid;
    }

    public String getFacultyname() {
        return facultyname;
    }

    public void setFacultyname(String facultyname) {
        this.facultyname = facultyname;
    }
}

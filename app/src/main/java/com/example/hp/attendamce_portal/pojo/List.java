package com.example.hp.attendamce_portal.pojo;

/**
 * Created by hp on 14-Apr-17.
 */

public class List {
    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public List(String subjectid) {

        this.subjectid = subjectid;
    }

    String subjectid;
}

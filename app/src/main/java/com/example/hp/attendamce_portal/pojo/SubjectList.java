package com.example.hp.attendamce_portal.pojo;

/**
 * Created by hp on 02-Apr-17.
 */

public class SubjectList {
    public SubjectList(String subjectid, String subjectname,String branchid,String semno) {
        this.subjectid = subjectid;
        this.subjectname = subjectname;
        this.branchid =branchid ;
        this.semno=semno;
    }
    private String subjectid;
    private String subjectname;
    private String branchid;
    private String semno;

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getSemno() {
        return semno;
    }

    public void setSemno(String semno) {
        this.semno = semno;
    }
}

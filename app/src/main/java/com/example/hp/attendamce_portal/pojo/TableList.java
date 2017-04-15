package com.example.hp.attendamce_portal.pojo;

/**
 * Created by hp on 13-Apr-17.
 */

public class TableList
{
    String SubjectId,TotalLecture,IsPresent;

    public TableList(String subjectId, String totalLecture, String isPresent) {
        SubjectId = subjectId;
        TotalLecture = totalLecture;
        IsPresent = isPresent;
    }

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public String getTotalLecture() {
        return TotalLecture;
    }

    public void setTotalLecture(String totalLecture) {
        TotalLecture = totalLecture;
    }

    public String getIsPresent() {
        return IsPresent;
    }

    public void setIsPresent(String isPresent) {
        IsPresent = isPresent;
    }
}

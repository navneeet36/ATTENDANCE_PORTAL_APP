package com.example.hp.attendamce_portal.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class BeanAttendance implements Parcelable {
    private String RollNo;
    private String FacultyID;
    private String AttendanceDate;
    private String IsPresent;
    private String SubjectID;
    private String SemNo;
    private String BranchID;

    protected BeanAttendance(Parcel in) {
        RollNo = in.readString();
        FacultyID = in.readString();
        AttendanceDate = in.readString();
        IsPresent = in.readString();
        SubjectID = in.readString();
        SemNo = in.readString();
        BranchID = in.readString();
    }

    public BeanAttendance() {
    }

    public static final Creator<BeanAttendance> CREATOR = new Creator<BeanAttendance>() {
        @Override
        public BeanAttendance createFromParcel(Parcel in) {
            return new BeanAttendance(in);
        }

        @Override
        public BeanAttendance[] newArray(int size) {
            return new BeanAttendance[size];
        }
    };

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public String getFacultyID() {
        return FacultyID;
    }

    public void setFacultyID(String facultyID) {
        FacultyID = facultyID;
    }

    public String getAttendanceDate() {
        return AttendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        AttendanceDate = attendanceDate;
    }

    public String getIsPresent() {
        return IsPresent;
    }

    public void setIsPresent(String isPresent) {
        IsPresent = isPresent;
    }

    public String getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(String subjectID) {
        SubjectID = subjectID;
    }

    public String getSemNo() {
        return SemNo;
    }

    public void setSemNo(String semNo) {
        SemNo = semNo;
    }

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        BranchID = branchID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeanAttendance that = (BeanAttendance) o;

        if (RollNo != null ? !RollNo.equals(that.RollNo) : that.RollNo != null) return false;
        if (FacultyID != null ? !FacultyID.equals(that.FacultyID) : that.FacultyID != null)
            return false;
        if (AttendanceDate != null ? !AttendanceDate.equals(that.AttendanceDate) : that.AttendanceDate != null)
            return false;
        if (SubjectID != null ? !SubjectID.equals(that.SubjectID) : that.SubjectID != null)
            return false;
        if (SemNo != null ? !SemNo.equals(that.SemNo) : that.SemNo != null) return false;
        return BranchID != null ? BranchID.equals(that.BranchID) : that.BranchID == null;

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(RollNo);
        parcel.writeString(FacultyID);
        parcel.writeString(AttendanceDate);
        parcel.writeString(IsPresent);
        parcel.writeString(SubjectID);
        parcel.writeString(SemNo);
        parcel.writeString(BranchID);
    }
}

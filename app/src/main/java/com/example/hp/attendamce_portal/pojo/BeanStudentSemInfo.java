package com.example.hp.attendamce_portal.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class BeanStudentSemInfo implements Parcelable {
	 private String RollNo;
	    private String BranchID;
	    private String SemNo;
	    private String IsActive;
	    private String SubjectID;

	protected BeanStudentSemInfo(Parcel in) {
		RollNo = in.readString();
		BranchID = in.readString();
		SemNo = in.readString();
		IsActive = in.readString();
		SubjectID = in.readString();
	}

	public BeanStudentSemInfo() {
	}

	public static final Creator<BeanStudentSemInfo> CREATOR = new Creator<BeanStudentSemInfo>() {
		@Override
		public BeanStudentSemInfo createFromParcel(Parcel in) {
			return new BeanStudentSemInfo(in);
		}

		@Override
		public BeanStudentSemInfo[] newArray(int size) {
			return new BeanStudentSemInfo[size];
		}
	};

	public String getRollNo() {
			return RollNo;
		}
		public void setRollNo(String rollNo) {
			RollNo = rollNo;
		}
		public String getBranchID() {
			return BranchID;
		}
		public void setBranchID(String branchID) {
			BranchID = branchID;
		}
		public String getSemNo() {
			return SemNo;
		}
		public void setSemNo(String semNo) {
			SemNo = semNo;
		}
		public String getIsActive() {
			return IsActive;
		}
		public void setIsActive(String isActive) {
			IsActive = isActive;
		}
		public String getSubjectID() {
			return SubjectID;
		}
		public void setSubjectID(String subjectID) {
			SubjectID = subjectID;
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(RollNo);
		parcel.writeString(BranchID);
		parcel.writeString(SemNo);
		parcel.writeString(IsActive);
		parcel.writeString(SubjectID);
	}
}

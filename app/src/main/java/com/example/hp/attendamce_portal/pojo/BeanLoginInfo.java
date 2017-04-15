package com.example.hp.attendamce_portal.pojo;

public class BeanLoginInfo {
    private String RollNo;
    private String FacultyID;
    private String UserName;
    private String Password;
    private String RoleName;
    private String EmailID;
    private String SecurityQuestion;
    private String SecurityAnswer;
    private String LastLogin;
    private int face_registered;

    public int getFace_registered() {
        return face_registered;
    }

    public void setFace_registered(int face_registered) {
        this.face_registered = face_registered;
    }

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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRoleName(String role) {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getSecurityQuestion() {
        return SecurityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        SecurityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return SecurityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        SecurityAnswer = securityAnswer;
    }

    public String getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(String lastLogin) {
        LastLogin = lastLogin;
    }


}

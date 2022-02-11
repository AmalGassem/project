package com.best.app.patientmedicalfile.PackageClass;

public class ClassPatient {
    //SELECT `userid`,medicalfileid ,`fullname`,`username`,`password`,`gender`,`age`,`phone`,`userType` FROM `user` ,patient,medicalfile WHERE user.userid=patient.patientid and patient.patientid=medicalfile.patientid
    private  int userid;
    private  int medicalfileid;
    private  String fullname;
    private  String username;
    private  String password;
    private  String gender;
    private  String age;
    private  String phone;
    private  String userType;

    public ClassPatient() {
    }

    public ClassPatient(int userid, int medicalfileid, String fullname, String username, String password, String gender, String age, String phone, String userType) {
        this.userid = userid;
        this.medicalfileid = medicalfileid;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return  fullname  ;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getMedicalfileid() {
        return medicalfileid;
    }

    public void setMedicalfileid(int medicalfileid) {
        this.medicalfileid = medicalfileid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

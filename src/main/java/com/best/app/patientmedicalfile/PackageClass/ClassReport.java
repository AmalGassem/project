package com.best.app.patientmedicalfile.PackageClass;

public class ClassReport {
    // SELECT `reportid`,medicalfile.medicalfileid ,user.fullname,`title`,`healthserviceprovider`,`notices`,`reportdate` FROM `report`, medicalfile,patient,user WHERE report.medicalfileid=medicalfile.medicalfileid AND medicalfile.patientid=patient.patientid and patient.patientid=user.userid
    private  int reportid;
    private  int  medicalfileid ;
    private  String  fullname;
    private  String title;
    private  String  healthserviceprovider;
    private  String  notices;
    private  String reportdate;

    public ClassReport() {
    }

    public ClassReport(int reportid, int medicalfileid, String fullname, String title, String healthserviceprovider, String notices, String reportdate) {
        this.reportid = reportid;
        this.medicalfileid = medicalfileid;
        this.fullname = fullname;
        this.title = title;
        this.healthserviceprovider = healthserviceprovider;
        this.notices = notices;
        this.reportdate = reportdate;
    }


    public int getReportid() {
        return reportid;
    }

    public void setReportid(int reportid) {
        this.reportid = reportid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHealthserviceprovider() {
        return healthserviceprovider;
    }

    public void setHealthserviceprovider(String healthserviceprovider) {
        this.healthserviceprovider = healthserviceprovider;
    }

    public String getNotices() {
        return notices;
    }

    public void setNotices(String notices) {
        this.notices = notices;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }
}

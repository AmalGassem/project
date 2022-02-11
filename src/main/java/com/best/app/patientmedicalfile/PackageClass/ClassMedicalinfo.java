package com.best.app.patientmedicalfile.PackageClass;

public class ClassMedicalinfo {
    //SELECT `infoid`,medicalfile.medicalfileid ,user.fullname,`title`,`description`,`infovalue`FROM `medicalinfo`, medicalfile,patient,user WHERE medicalinfo.medicalfileid=medicalfile.medicalfileid AND medicalfile.patientid=patient.patientid and patient.patientid=user.userid
    private  int infoid;
    private  int   medicalfileid;
    private  String  title;
    private  String  description;
    private  String  infovalue;
    private  String   fullname;


    public ClassMedicalinfo() {
    }

    public ClassMedicalinfo(int infoid, int medicalfileid, String title, String description, String infovalue, String fullname) {
        this.infoid = infoid;
        this.medicalfileid = medicalfileid;
        this.title = title;
        this.description = description;
        this.infovalue = infovalue;
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return   fullname  ;
    }

    public int getInfoid() {
        return infoid;
    }

    public void setInfoid(int infoid) {
        this.infoid = infoid;
    }

    public int getMedicalfileid() {
        return medicalfileid;
    }

    public void setMedicalfileid(int medicalfileid) {
        this.medicalfileid = medicalfileid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInfovalue() {
        return infovalue;
    }

    public void setInfovalue(String infovalue) {
        this.infovalue = infovalue;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

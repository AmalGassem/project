package com.best.app.patientmedicalfile.PackageClass;

public class ClassDisease {
    //SELECT `diseaseid`,medicalfile.medicalfileid ,user.fullname, `diseasename`,`diseasedate`,`healthserviceprovider`,`medicine` FROM `disease`, medicalfile,patient,user WHERE disease.medicalfileid=medicalfile.medicalfileid AND medicalfile.patientid=patient.patientid and patient.patientid=user.userid
    private  int diseaseid;
    private  int  medicalfileid ;
    private  String  fullname;
    private  String   diseasename;
    private  String   diseasedate;
    private  String  healthserviceprovider;
    private  String  medicine;

    public ClassDisease() {
    }

    public ClassDisease(int diseaseid, int medicalfileid, String fullname, String diseasename, String diseasedate, String healthserviceprovider, String medicine) {
        this.diseaseid = diseaseid;
        this.medicalfileid = medicalfileid;
        this.fullname = fullname;
        this.diseasename = diseasename;
        this.diseasedate = diseasedate;
        this.healthserviceprovider = healthserviceprovider;
        this.medicine = medicine;
    }

    public int getDiseaseid() {
        return diseaseid;
    }

    public void setDiseaseid(int diseaseid) {
        this.diseaseid = diseaseid;
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

    public String getDiseasename() {
        return diseasename;
    }

    public void setDiseasename(String diseasename) {
        this.diseasename = diseasename;
    }

    public String getDiseasedate() {
        return diseasedate;
    }

    public void setDiseasedate(String diseasedate) {
        this.diseasedate = diseasedate;
    }

    public String getHealthserviceprovider() {
        return healthserviceprovider;
    }

    public void setHealthserviceprovider(String healthserviceprovider) {
        this.healthserviceprovider = healthserviceprovider;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}

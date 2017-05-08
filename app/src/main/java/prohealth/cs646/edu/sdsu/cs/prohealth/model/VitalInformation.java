package prohealth.cs646.edu.sdsu.cs.prohealth.model;

import java.io.Serializable;

public class VitalInformation implements Serializable{

    private int id;
    private int uid;
    private String vitalHeader;
    private String height;
    private String weight;
    private String systolic;
    private String diastolic;
    private String bpm;
    private String vitalLastUpdated;
    private String vitalValue;
    private String notes;


    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getVitalHeader() {
        return vitalHeader;
    }

    public void setVitalHeader(String vitalHeader) {
        this.vitalHeader = vitalHeader;
    }

    public String getVitalLastUpdated() {
        return vitalLastUpdated;
    }

    public void setVitalLastUpdated(String vitalLastUpdated) {
        this.vitalLastUpdated = vitalLastUpdated;
    }

    public String getVitalValue() {
        return vitalValue;
    }

    public void setVitalValue(String vitalValue) {
        this.vitalValue = vitalValue;
    }
}

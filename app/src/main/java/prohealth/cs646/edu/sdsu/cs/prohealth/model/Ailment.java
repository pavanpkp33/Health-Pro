package prohealth.cs646.edu.sdsu.cs.prohealth.model;

import java.io.Serializable;


public class Ailment implements Serializable {

    private  int id;
    private int uid;
    private String ailment;
    private String encounteredDate;
    private String medication;
    private String notes;


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

    public String getAilment() {
        return ailment;
    }

    public void setAilment(String ailment) {
        this.ailment = ailment;
    }

    public String getEncounteredDate() {
        return encounteredDate;
    }

    public void setEncounteredDate(String encounteredDate) {
        this.encounteredDate = encounteredDate;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

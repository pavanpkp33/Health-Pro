package prohealth.cs646.edu.sdsu.cs.prohealth.model;

import java.io.Serializable;


public class Vaccination implements Serializable{

    private int id;
    private int uid;
    private String vaccination;
    private String administeredDate;
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

    public String getVaccination() {
        return vaccination;
    }

    public void setVaccination(String vaccination) {
        this.vaccination = vaccination;
    }

    public String getAdministeredDate() {
        return administeredDate;
    }

    public void setAdministeredDate(String administeredDate) {
        this.administeredDate = administeredDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

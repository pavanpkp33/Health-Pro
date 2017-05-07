package prohealth.cs646.edu.sdsu.cs.prohealth.model;

public class VitalInformation {

    private int id;
    private int uid;
    private String vitalHeader;
    private String vitalLastUpdated;
    private String vitalValue;
    private String notes;

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

package prohealth.cs646.edu.sdsu.cs.prohealth.helpers;



public class Constants {

    public static String RECORD_COUNT = "SELECT * FROM USERS";
    public static String GET_USER_DATA = "SELECT * FROM USERS WHERE ID = ?";
    public static String GET_BMI_DATA = "SELECT * FROM BMI WHERE UID = ? ORDER BY ID DESC";
    public static String GET_BGC_DATA = "SELECT * FROM BGC WHERE UID = ? ORDER BY ID DESC";
    public static String GET_BP_DATA = "SELECT * FROM BP WHERE UID = ? ORDER BY ID DESC";
    public static String GET_AIL_DATA = "SELECT * FROM AIL WHERE UID = ? ORDER BY ID DESC";
    public static String GET_MED_DATA = "SELECT * FROM VISIT WHERE UID = ? ORDER BY ID DESC";
    public static String GET_VAC_DATA = "SELECT * FROM VAC WHERE UID = ? ORDER BY ID DESC";

}

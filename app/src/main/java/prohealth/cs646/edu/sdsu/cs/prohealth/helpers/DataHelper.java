package prohealth.cs646.edu.sdsu.cs.prohealth.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataHelper {

    private static final String DATABASE_NAME = "healthpro";
    private static final String DATABASE_TABLE = "users";

    private static final int DATABASE_VERSION = 1;

    private final Context helperContext;
    private static SQLiteDatabase db;
    private DBManager manageDB;


    public DataHelper(Context ctx){
        helperContext = ctx;
    }

    public DataHelper openConnetion() throws SQLException{
        manageDB = new DBManager(helperContext);
        db = manageDB.getWritableDatabase();
        return this;
    }

    public void closeConnection(){
        manageDB.close();
    }

    public long insert(ContentValues cvItems){
       return db.insertWithOnConflict(DATABASE_TABLE, null, cvItems, SQLiteDatabase.CONFLICT_IGNORE );
    }

    public Cursor select(String Query, String [] params){
        return db.rawQuery(Query, params);
    }


    private static class DBManager extends SQLiteOpenHelper{


       public DBManager(Context ctx){
           super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
       }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT, LASTNAME TEXT, DOB TEXT, GENDER TEXT, "
                    + " EMAIL TEXT UNIQUE, PHONE TEXT DEFAULT 'N/A', CITY TEXT DEFAULT 'N/A' , STATE TEXT DEFAULT 'N/A', COUNTRY TEXT DEFAULT 'N/A', ZIPCODE TEXT 'N/A' );");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +DATABASE_TABLE);
            onCreate(db);

        }
    }
}



package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.Constants;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataHelper dbHelper = new DataHelper(getApplicationContext());
        dbHelper.openConnetion();
        super.onCreate(savedInstanceState);
        Cursor c = dbHelper.select(Constants.RECORD_COUNT, null);
        c.moveToFirst();
        int count = c.getInt(0);
        if(count > 0){
            Intent intent = new Intent(this,UserHome.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this,SignUp.class);
            startActivity(intent);
            finish();
        }



    }
}

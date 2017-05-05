package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataHelper dbHelper = new DataHelper(getApplicationContext());
        dbHelper.openConnetion();
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
        finish();


    }
}

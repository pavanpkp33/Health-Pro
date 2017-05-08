package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.Constants;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Profile extends AppCompatActivity {
    DataHelper dbHelper;
    int userId = 0;
    TextView tvFirstName, tvLastName, tvDob, tvEmail, tvPhone, tvState, tvCountry, tvGender;
    Button btnDelete;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DataHelper(this);
        setContentView(R.layout.activity_profile);
        Bundle bdl = getIntent().getExtras();
        if(bdl != null){
            userId = bdl.getInt("id");

        }
        tvFirstName = (TextView) findViewById(R.id.tvDisplayName);
        tvDob = (TextView) findViewById(R.id.tvDobValue);
        tvEmail = (TextView) findViewById(R.id.tvEmailValue);
        tvPhone = (TextView) findViewById(R.id.tvProfilePhone);
        tvState = (TextView) findViewById(R.id.tvProfileStateValue);
        tvGender = (TextView) findViewById(R.id.tvProfileGenderValue);
        tvCountry = (TextView) findViewById(R.id.tvProfileCountryValue);
        btnDelete = (Button) findViewById(R.id.btnDeleteAccount) ;

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Profile.this)
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to delete this account?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                getApplicationContext().deleteDatabase("healthpro");
                                Toast.makeText(Profile.this, "Account has been deleted.", Toast.LENGTH_SHORT).show();
                                Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        getUserData(userId);

    }

    private void getUserData(int userId) {
        Cursor dbCursor;
        String [] arr = { String.valueOf(userId)};
        dbCursor = dbHelper.select(Constants.GET_USER_DATA, arr);
        dbCursor.moveToFirst();
        if(dbCursor.getCount() > 0){

            tvFirstName.setText(dbCursor.getString(1)+" "+dbCursor.getString(2));
            tvDob.setText(dbCursor.getString(3));
            tvGender.setText(dbCursor.getString(4));
            tvPhone.setText(dbCursor.getString(6));
            tvEmail.setText(dbCursor.getString(5));
            tvState.setText(dbCursor.getString(8));
            tvCountry.setText(dbCursor.getString(9));


        }
        dbCursor.close();
    }
}

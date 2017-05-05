package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp extends AppCompatActivity implements View.OnClickListener, ShineButton.OnCheckedChangeListener{

    ShineButton btnMale, btnFemale;
    MaterialEditText etFirstName, etLastName, etPhone, etEmail, etDob, etCity, etState, etCountry, etZip;
    ActionProcessButton btnRegister;
    Calendar calObj = Calendar.getInstance();
    String genderValue;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();

    }

    private void initUI() {

        etFirstName = (MaterialEditText) findViewById(R.id.etRegFirstName);
        etLastName = (MaterialEditText) findViewById(R.id.etRegLastName);
        etPhone = (MaterialEditText) findViewById(R.id.etRegPhone);
        etEmail = (MaterialEditText) findViewById(R.id.etRegEmail);
        etDob = (MaterialEditText) findViewById(R.id.etRegDob);
        etCity = (MaterialEditText) findViewById(R.id.etRegCity);
        etState = (MaterialEditText) findViewById(R.id.etRegState);
        etCountry = (MaterialEditText) findViewById(R.id.etRegCountry);
        etZip = (MaterialEditText) findViewById(R.id.etRegZip);
        btnRegister = (ActionProcessButton) findViewById(R.id.btnRegister);
        btnMale = (ShineButton) findViewById(R.id.opMale);
        btnFemale = (ShineButton) findViewById(R.id.opFemale);
        if(btnMale != null){
            btnMale.init(this);
        }

        if(btnFemale != null){
            btnFemale.init(this);
        }


        etDob.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnFemale.setOnCheckStateChangeListener(this);
        btnMale.setOnCheckStateChangeListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.etRegDob:
                new DatePickerDialog(this, date, calObj
                        .get(Calendar.YEAR), calObj.get(Calendar.MONTH),
                        calObj.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.btnRegister:


                break;


        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calObj.set(Calendar.YEAR, year);
            calObj.set(Calendar.MONTH, monthOfYear);
            calObj.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDob();
        }

    };



    private void updateDob() {

        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat formatDate = new SimpleDateFormat(myFormat, Locale.US);
        etDob.setText(formatDate.format(calObj.getTime()));
    }

    @Override
    public void onCheckedChanged(View view, boolean checked) {

        switch (view.getId()){

            case R.id.opMale:
                if(checked){
                    btnFemale.setChecked(false);
                    genderValue = "Male";
                }

                break;


            case R.id.opFemale:
                if(checked){
                    btnMale.setChecked(false);
                    genderValue = "Female";
                }

                break;

        }
    }
}

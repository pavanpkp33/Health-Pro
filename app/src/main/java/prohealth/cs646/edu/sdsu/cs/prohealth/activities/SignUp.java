package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp extends AppCompatActivity implements View.OnClickListener, ShineButton.OnCheckedChangeListener{

    ShineButton btnMale, btnFemale;
    MaterialEditText etFirstName, etLastName, etPhone, etEmail, etDob, etCity, etState, etCountry, etZip;
    ActionProcessButton btnRegister;
    Calendar calObj = Calendar.getInstance();
    String genderValue = "";
    ContentValues userInfo;
    DataHelper dbHelper;
    long uid;

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
        dbHelper = new DataHelper(this);

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
        btnRegister.setMode(ActionProcessButton.Mode.ENDLESS);
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

                validateData();

                break;


        }
    }

    private void validateData() {

        userInfo = new ContentValues();
        String city = "N/A", country = "N/A", state = "N/A", zipcode = "N/A";
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        city = etCity.getText().toString();
        country = etCountry.getText().toString();
        state = etState.getText().toString();
        zipcode = etZip.getText().toString();
        String dob = etDob.getText().toString();
        if(firstName.isEmpty()){
            etFirstName.setError("Enter First name");
            etFirstName.requestFocus();
            return;

        }else if(lastName.isEmpty()){
            etLastName.setError("Enter Last name");
            etLastName.requestFocus();
            return;
        }else if(email.isEmpty()){
            etEmail.setError("Enter valid Email ID");
            etEmail.requestFocus();
            return;
        }else if(dob.isEmpty()){
            etDob.setError("Choose Date of Birth"); return;
        }else if(genderValue.isEmpty()){
            Snackbar.make(this.findViewById(android.R.id.content), "Select a Gender", Snackbar.LENGTH_SHORT).show();
            return;
        }else{
            btnRegister.setEnabled(false);
            btnRegister.setProgress(2);
            userInfo.put("FIRSTNAME", firstName);
            userInfo.put("LASTNAME", lastName);
            userInfo.put("DOB", dob);
            userInfo.put("EMAIL", email);
            userInfo.put("GENDER", genderValue);
            userInfo.put("PHONE", phone);
            userInfo.put("CITY", city);
            userInfo.put("STATE", state);
            userInfo.put("COUNTRY", country);
            userInfo.put("ZIPCODE", zipcode);
            uid = dbHelper.insert(userInfo, "users");

        }
        disableForms();
        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable(){
            public void run() {
                if(uid > 0){
                    btnRegister.setProgress(100);
                    displayHomeScreen(uid);

                }else{
                    btnRegister.setProgress(-1);
                }


            }}, 2000);

    }

    private void displayHomeScreen(long uid) {

        Intent homeIntent = new Intent(this, UserHome.class);
        int id = (int) uid;
        homeIntent.putExtra("id", id);
        startActivity(homeIntent);
        finish();
    }

    private void disableForms() {

        etFirstName.setEnabled(false);
        etLastName.setEnabled(false);
        etDob.setEnabled(false);
        etEmail.setEnabled(false);
        etCity.setEnabled(false);
        etCountry.setEnabled(false);
        etState.setEnabled(false);
        etPhone.setEnabled(false);
        etZip.setEnabled(false);
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
                }else{
                    genderValue = "";
                }

                break;


            case R.id.opFemale:
                if(checked){
                    btnMale.setChecked(false);
                    genderValue = "Female";
                }else{
                    genderValue = "";
                }

                break;

        }
    }
}

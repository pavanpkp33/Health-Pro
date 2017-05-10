package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddRecords extends AppCompatActivity implements View.OnClickListener{
    int id = 0, resCode = 0;
    String tag = "";
    ContentValues dataBundle;
    DataHelper dbHelper;
    Calendar calObj = Calendar.getInstance();
    MaterialEditText etMedicalFname, etMedicalLName, etMedicalDov, etMedicalDept, etMedicalPurpose, etMedicalDrDiag,
                    etMedicalDrMedicines, etMedicalDrNotes, etVaccinationName, etVacDateAdministered, etVacNotes,
                    etAilmentName, etAilmentMed, etAilmentFirstSeen, etAilmentNotes;
    ActionProcessButton btnMedicalSave, btnVaccinationSave, btnAilmentSave;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DataHelper(this);
        Bundle bdl = getIntent().getExtras();
        id = bdl.getInt("id");
        tag = bdl.getString("tag");

        if(tag.equalsIgnoreCase("medvisit")){
            setContentView(R.layout.content_medicalvisit);
            processMedicalUIandSave(id);
        }else if(tag.equalsIgnoreCase("vaccination")){
            setContentView(R.layout.content_vaccination);
            processVaccinationUIandSave(id);
        }else{
            setContentView(R.layout.content_ailment);
            processAilmentUIandSave(id);
        }

    }

    private void processAilmentUIandSave(int id) {
        etAilmentName = (MaterialEditText) findViewById(R.id.etAilmentName);
        etAilmentMed = (MaterialEditText) findViewById(R.id.etAilmentMedication);
        etAilmentFirstSeen = (MaterialEditText) findViewById(R.id.etDateEncountered);
        etAilmentNotes = (MaterialEditText) findViewById(R.id.etAilmentNotes);

        btnAilmentSave = (ActionProcessButton) findViewById(R.id.btnAilmentSave);

        btnAilmentSave.setOnClickListener(this);
        etAilmentFirstSeen.setOnClickListener(this);
        btnAilmentSave.setMode(ActionProcessButton.Mode.ENDLESS);

    }

    private void processVaccinationUIandSave(int id) {

        etVaccinationName = (MaterialEditText) findViewById(R.id.etVaccinationName);
        etVacDateAdministered = (MaterialEditText) findViewById(R.id.etDateAdministered);
        etVacNotes = (MaterialEditText) findViewById(R.id.etVaccinationNotes);

        btnVaccinationSave = (ActionProcessButton) findViewById(R.id.btnVaccinationSave);

        btnVaccinationSave.setOnClickListener(this);
        etVacDateAdministered.setOnClickListener(this);
        btnVaccinationSave.setMode(ActionProcessButton.Mode.ENDLESS);


    }

    private void processMedicalUIandSave(int id) {
        etMedicalFname = (MaterialEditText) findViewById(R.id.etDrFirstName);
        etMedicalLName = (MaterialEditText) findViewById(R.id.etDrLastName);
        etMedicalDov = (MaterialEditText) findViewById(R.id.etDateOfVisit);
        etMedicalDept = (MaterialEditText) findViewById(R.id.etDepartment);
        etMedicalPurpose = (MaterialEditText) findViewById(R.id.etPurposeOfVisit);
        etMedicalDrDiag = (MaterialEditText) findViewById(R.id.etDoctorDiagnosis);
        etMedicalDrMedicines = (MaterialEditText) findViewById(R.id.etMedications);
        etMedicalDrNotes = (MaterialEditText) findViewById(R.id.etMedicalNotes);
        btnMedicalSave = (ActionProcessButton) findViewById(R.id.btnMedicalVisitSave);

        etMedicalDov.setOnClickListener(this);
        btnMedicalSave.setOnClickListener(this);
        btnMedicalSave.setMode(ActionProcessButton.Mode.ENDLESS);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnMedicalVisitSave:
                validateMedicalFormAndSave();
                break;
            case R.id.etDateOfVisit:
                new DatePickerDialog(this, date, calObj
                        .get(Calendar.YEAR), calObj.get(Calendar.MONTH),
                        calObj.get(Calendar.DAY_OF_MONTH)).show();

                break;
            case R.id.btnAilmentSave:
                validateAilmentFormAndSave();
                break;
            case R.id.btnVaccinationSave:
                validateVacFormAndSave();
                break;

            case R.id.etDateAdministered:
                new DatePickerDialog(this, vacDate, calObj
                        .get(Calendar.YEAR), calObj.get(Calendar.MONTH),
                        calObj.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.etDateEncountered:
                new DatePickerDialog(this, ailDate, calObj
                        .get(Calendar.YEAR), calObj.get(Calendar.MONTH),
                        calObj.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }

    }

    private void validateVacFormAndSave() {
        dataBundle = new ContentValues();
        String vacName = etVaccinationName.getText().toString();
        String dateAdm = etVacDateAdministered.getText().toString();
        String vacNotes = etVacNotes.getText().toString();

        if(vacName.isEmpty()){
            etVaccinationName.setError("Vaccination name required");
            etVaccinationName.requestFocus(); return;
        }else if(dateAdm.isEmpty()){
            Toast.makeText(this, "Date administered is required", Toast.LENGTH_LONG).show();
            return;
        }else{
            btnVaccinationSave.setEnabled(false);
            btnVaccinationSave.setProgress(2);

            dataBundle.put("UID", id);
            dataBundle.put("VAC_NAME", vacName);
            dataBundle.put("DATE_ADM", dateAdm);
            dataBundle.put("NOTES", vacNotes);



            resCode = (int) dbHelper.insert(dataBundle, "vac");
            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable(){
                public void run() {

                    if(resCode != -1){

                        btnVaccinationSave.setProgress(100);
                        Intent intent = new Intent();
                        intent.putExtra("id", id);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        btnVaccinationSave.setProgress(-1);
                    }


                }}, 750);
        }
    }

    private void validateAilmentFormAndSave() {
        dataBundle = new ContentValues();
        String ailName = etAilmentName.getText().toString();
        String ailMedication = etAilmentMed.getText().toString();
        String dateEncountered = etAilmentFirstSeen.getText().toString();
        String ailNotes = "N/A";
        ailNotes = etAilmentNotes.getText().toString();

        if(ailName.isEmpty()){
            etAilmentName.setError("Required field.");
            etAilmentName.requestFocus(); return;
        }else if(ailMedication.isEmpty()){
            etAilmentMed.setError("Medication required");
            etAilmentMed.requestFocus(); return;
        }else if(dateEncountered.isEmpty()){
            Toast.makeText(this, "Date encountered is required", Toast.LENGTH_LONG).show();
            return;
        }else{
                btnAilmentSave.setEnabled(false);
                btnAilmentSave.setProgress(2);

                dataBundle.put("UID", id);
                dataBundle.put("AIL_NAME", ailName);
                dataBundle.put("FED", dateEncountered);
                dataBundle.put("MED", ailMedication);
                dataBundle.put("NOTES", ailNotes);


                resCode = (int) dbHelper.insert(dataBundle, "ail");
                Handler handlerTimer = new Handler();
                handlerTimer.postDelayed(new Runnable(){
                    public void run() {

                        if(resCode != -1){

                            btnAilmentSave.setProgress(100);
                            Intent intent = new Intent();
                            intent.putExtra("id", id);
                            setResult(RESULT_OK, intent);
                            finish();
                        }else{
                            btnAilmentSave.setProgress(-1);
                        }


                    }}, 750);
        }


    }

    private void validateMedicalFormAndSave() {
        dataBundle = new ContentValues();
        String drFname = etMedicalFname.getText().toString();
        String drLName = etMedicalLName.getText().toString();
        String dov = etMedicalDov.getText().toString();
        String dept = etMedicalDept.getText().toString();
        String purpose = etMedicalPurpose.getText().toString();
        String diag = etMedicalDrDiag.getText().toString();
        String medicines = "N/A";
        medicines = etMedicalDrMedicines.getText().toString();
        String notes = "N/A";
        notes = etMedicalDrNotes.getText().toString();

        if(drFname.isEmpty()){
            etMedicalFname.setError("Required field");
            etMedicalFname.requestFocus(); return;
        }else if(drLName.isEmpty()){
            etMedicalLName.setError("Required field");
            etMedicalLName.requestFocus(); return;
        }else if(dov.isEmpty()){
            Toast.makeText(this, "Date of visit required", Toast.LENGTH_LONG).show();
            return;
        }else if(dept.isEmpty()){
            etMedicalDept.setError("Required field");
            etMedicalDept.requestFocus(); return;
        }else if(purpose.isEmpty()){
            etMedicalPurpose.setError("Required field");
            etMedicalPurpose.requestFocus(); return;
        }else if(diag.isEmpty()){
            etMedicalDrDiag.setError("Required field");
            etMedicalPurpose.requestFocus(); return;
        }else{
            btnMedicalSave.setEnabled(false);
            btnMedicalSave.setProgress(2);

            dataBundle.put("UID", id);
            dataBundle.put("DR_FNAME", drFname);
            dataBundle.put("DR_LNAME", drLName);
            dataBundle.put("DEPT", dept);
            dataBundle.put("POV", purpose);
            dataBundle.put("DOV", dov);
            dataBundle.put("DIAG", diag);
            dataBundle.put("MED", medicines);
            dataBundle.put("NOTES", notes);

            resCode = (int) dbHelper.insert(dataBundle, "visit");
            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable(){
                public void run() {

                    if(resCode != -1){

                        btnMedicalSave.setProgress(100);
                        Intent intent = new Intent();
                        intent.putExtra("id", id);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        btnMedicalSave.setProgress(-1);
                    }


                }}, 750);

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

    DatePickerDialog.OnDateSetListener vacDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calObj.set(Calendar.YEAR, year);
            calObj.set(Calendar.MONTH, monthOfYear);
            calObj.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateVac();
        }

    };

    private void updateVac() {

        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat formatDate = new SimpleDateFormat(myFormat, Locale.US);
        etVacDateAdministered.setText(formatDate.format(calObj.getTime()));
    }

    DatePickerDialog.OnDateSetListener ailDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calObj.set(Calendar.YEAR, year);
            calObj.set(Calendar.MONTH, monthOfYear);
            calObj.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateAilDate();
        }

    };

    private void updateAilDate() {

        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat formatDate = new SimpleDateFormat(myFormat, Locale.US);
        etAilmentFirstSeen.setText(formatDate.format(calObj.getTime()));
    }

    private void updateDob() {

        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat formatDate = new SimpleDateFormat(myFormat, Locale.US);
        etMedicalDov.setText(formatDate.format(calObj.getTime()));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}

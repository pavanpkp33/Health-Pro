package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.dd.processbutton.iml.ActionProcessButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VitalDetails extends AppCompatActivity implements View.OnClickListener {
    String tag = "";
    MaterialEditText etBMIHeight, etBMIWeight, etBPSystolic, etBPDiastolic, etBPBpm, etBG;
    EditText etBMInotes, etBPNotes, etBGNotes;
    ActionProcessButton btnBMISave, btnBPSave, btnBGSave;
    ContentValues dataBundle;
    DataHelper dbHelper;

    int id = 0, resCode;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DataHelper(this);
        Bundle bdl = getIntent().getExtras();
        if(bdl != null){
            id = bdl.getInt("id");
           tag =  bdl.getString("tag");
        }

        if(!tag.isEmpty()){
            if(tag.equalsIgnoreCase("BMI")){
                setContentView(R.layout.dialog_bmi);
                processBMIUI();
            }else if(tag.equalsIgnoreCase("BGC")){
                setContentView(R.layout.dialog_bgc);
                processBGCUI();
            }else if(tag.equalsIgnoreCase("BP")){
                setContentView(R.layout.dialog_bp);
                processBPUI();
            }
        }


    }

    private void processBPUI() {
        etBPSystolic = (MaterialEditText) findViewById(R.id.etBpSystolic);
        etBPDiastolic = (MaterialEditText) findViewById(R.id.etBpDiastolic);
        etBPBpm = (MaterialEditText) findViewById(R.id.etBpBpm);
        etBPNotes = (EditText) findViewById(R.id.etBpNotes);
        btnBPSave = (ActionProcessButton) findViewById(R.id.btnBpSave);

        btnBPSave.setOnClickListener(this);
    }

    private void processBGCUI() {

        etBG = (MaterialEditText) findViewById(R.id.etBgcGlucose);
        etBGNotes = (EditText) findViewById(R.id.etBGNotes);
        btnBGSave = (ActionProcessButton) findViewById(R.id.btnBGSave);

        btnBGSave.setOnClickListener(this);
    }

    private void processBMIUI() {

        etBMIHeight = (MaterialEditText) findViewById(R.id.etBmiHeight);
        etBMIWeight = (MaterialEditText) findViewById(R.id.etBmiWeight);
        etBMInotes = (EditText) findViewById(R.id.etBMINotes);
        btnBMISave = (ActionProcessButton) findViewById(R.id.btnSave);

        btnBMISave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnSave:
                processAndSaveBMIData();
                break;

            case R.id.btnBpSave:
                processAndSaveBPData();
                break;

            case R.id.btnBGSave:
                processAndSaveBGData();
                break;
        }

    }

    private void processAndSaveBGData() {

        String bgData = etBG.getText().toString();
        String notes = "";
        notes = etBGNotes.getText().toString();

        if(bgData.isEmpty()){
            etBG.setError("Enter a value.");
            etBG.requestFocus(); return;
        }else{
            btnBGSave.setEnabled(false);
            btnBGSave.setProgress(2);
            storeBGData(bgData, notes);
        }
    }

    private void storeBGData(String bgData, String notes) {

        dataBundle = new ContentValues();
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());

        dataBundle.put("UID", id);
        dataBundle.put("BLDG", Double.parseDouble(bgData));
        dataBundle.put("NOTES", notes);
        dataBundle.put("LAST_MODIFIED", timeStamp);

        resCode = (int) dbHelper.insert(dataBundle, "bgc");
        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable(){
            public void run() {

                if(resCode != -1){

                    btnBGSave.setProgress(100);
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    btnBGSave.setProgress(-1);
                }


            }}, 750);

    }

    private void processAndSaveBPData() {
        String sys = etBPSystolic.getText().toString();
        String dias = etBPDiastolic.getText().toString();
        String hRate = etBPBpm.getText().toString();
        String notes = "";
        notes = etBPNotes.getText().toString();
        
        if(sys.isEmpty()){
            etBPSystolic.setError("Enter a value.");
            etBPSystolic.requestFocus(); return;
        }else if(dias.isEmpty()){
            etBPDiastolic.setError("Enter a value.");
            etBPDiastolic.requestFocus(); return;
        }else if(hRate.isEmpty()){
            etBPBpm.setError("Enter a value.");
            etBPBpm.requestFocus(); return;
        }else{
            btnBPSave.setEnabled(false);
            btnBPSave.setProgress(2);
            storeBPData(sys, dias, hRate, notes);
        }
        
    }

    private void storeBPData(String sys, String dias, String hRate, String notes) {

        dataBundle = new ContentValues();
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());


        dataBundle.put("UID", id);
        dataBundle.put("SYSTOLIC", Double.parseDouble(sys));
        dataBundle.put("DIASTOLIC", Double.parseDouble(dias));
        dataBundle.put("HEART_BEAT", Double.parseDouble(hRate));
        dataBundle.put("NOTES", notes);
        dataBundle.put("LAST_MODIFIED", timeStamp);

        resCode = (int) dbHelper.insert(dataBundle, "bp");
        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable(){
            public void run() {

                if(resCode != -1){

                    btnBPSave.setProgress(100);
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    btnBPSave.setProgress(-1);
                }


            }}, 750);

    }

    private void processAndSaveBMIData() {

        String weight = etBMIWeight.getText().toString();
        String height = etBMIHeight.getText().toString();
        String notes = "";
        notes = etBMInotes.getText().toString();

        if(weight.isEmpty()){
            etBMIWeight.setError("Enter your Weight.");
            etBMIWeight.requestFocus(); return;
        }else if(height.isEmpty()){
            etBMIHeight.setError("Enter your Height.");
            etBMIHeight.requestFocus(); return;

         }else if((Double.parseDouble(weight) == 0.0) ||(Double.parseDouble(height) == 0.0)) {

            Snackbar.make(this.findViewById(android.R.id.content), "Height or Weight value cannot be 0", Snackbar.LENGTH_SHORT).show();
            return;
        }else{
            btnBMISave.setEnabled(false);
            btnBMISave.setProgress(2);
            calculateBMIandInsert(height, weight, notes);

        }
    }

    private void calculateBMIandInsert(String height, String weight, String notes) {
        dataBundle = new ContentValues();
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());

        double heightDouble = Double.parseDouble(height);
        double weightDouble = Double.parseDouble(weight) ;
        double BMI = ((weightDouble *  0.453) / Math.pow((heightDouble), 2));
        dataBundle.put("UID", id);
        dataBundle.put("HEIGHT", heightDouble);
        dataBundle.put("WEIGHT", weightDouble);
        dataBundle.put("BMI", BMI);
        dataBundle.put("NOTES", notes);
        dataBundle.put("LAST_MODIFIED", timeStamp);

        resCode = (int) dbHelper.insert(dataBundle, "bmi");
        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable(){
            public void run() {

                if(resCode != -1){

                    btnBMISave.setProgress(100);
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    btnBMISave.setProgress(-1);
                }


            }}, 750);




    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}

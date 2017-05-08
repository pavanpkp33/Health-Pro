package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.VitalInformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VitalRecordDetails extends AppCompatActivity implements View.OnClickListener {

    String tag, header;
    Button btnBMIdelete, btnBPdelete, btnBGCdelete;
    VitalInformation vitalInfo;
    DataHelper dbHelper;
    LinearLayout bmiLin, bpLin, bgcLin;
    TextView tvBMIHeight, tvBMIWeight, tvBMIValue, tvBMINotes, tvBMILastUpdated
            , tvBGCValue, tvBGVLastUpdated, tvBGCNotes, tvBPSys, tvBPDias, tvBPbpm, tvBPLastIpdated
            , tvBPNotes;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DataHelper(this);
        Bundle bdl = getIntent().getExtras();
        tag = bdl.getString("tag");
        header = bdl.getString("header");
        vitalInfo = (VitalInformation) bdl.getSerializable("record");
        setContentView(R.layout.activity_vital_record_details);
        bmiLin = (LinearLayout) findViewById(R.id.bmi_lin_layout);
        bpLin = (LinearLayout) findViewById(R.id.bp_lin_layout);
        bgcLin = (LinearLayout) findViewById(R.id.bgc_lin_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.vitalRowDetailsToolbar);
        setSupportActionBar(toolbar);
        if(tag.equalsIgnoreCase("BMI")){
            bmiLin.setVisibility(View.VISIBLE);
            initBMIElements();

        }else if(tag.equalsIgnoreCase("BGC")){
            bgcLin.setVisibility(View.VISIBLE);
            initBGCElements();
        }else{
            bpLin.setVisibility(View.VISIBLE);
            initBPElements();
        }
        getSupportActionBar().setTitle(header);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEditVitalRecord);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private void initBPElements() {
        tvBPSys = (TextView) findViewById(R.id.tvBPVitalSystolic);
        tvBPDias = (TextView) findViewById(R.id.tvBPVitalDiastolic);
        tvBPbpm = (TextView) findViewById(R.id.tvBPVitalBpm);
        tvBPLastIpdated = (TextView) findViewById(R.id.tvBPVitalAdded);
        tvBPNotes = (TextView)findViewById(R.id.tvBPVitalNotes);

        btnBPdelete = (Button) findViewById(R.id.btnBPVitalDelete);
        btnBPdelete.setOnClickListener(this);

        tvBPSys.setText(vitalInfo.getSystolic());
        tvBPDias.setText(vitalInfo.getDiastolic());
        tvBPbpm.setText(vitalInfo.getBpm());
        tvBPNotes.setText(vitalInfo.getNotes());
        tvBPLastIpdated.setText(vitalInfo.getVitalLastUpdated());
    }

    private void initBGCElements() {
        tvBGCValue = (TextView) findViewById(R.id.tvBGCVitalValue);
        tvBGVLastUpdated = (TextView) findViewById(R.id.tvBGCVitalAdded);
        tvBGCNotes = (TextView) findViewById(R.id.tvBGCVitalNotes);
        btnBGCdelete = (Button) findViewById(R.id.btnBGCVitalDelete);
        btnBGCdelete.setOnClickListener(this);

        tvBGCValue.setText(vitalInfo.getVitalValue());
        tvBGVLastUpdated.setText(vitalInfo.getVitalLastUpdated());
        tvBGCNotes.setText(vitalInfo.getNotes());
    }

    private void initBMIElements() {
        tvBMIHeight = (TextView) findViewById(R.id.tvBMIVitalHeight);
        tvBMIWeight = (TextView) findViewById(R.id.tvBMIVitalWeight);
        tvBMILastUpdated = (TextView) findViewById(R.id.tvBMIVitalAdded);
        tvBMIValue = (TextView) findViewById(R.id.tvBMIVitalBMI);
        tvBMINotes = (TextView) findViewById(R.id.tvBMIVitalNotes);
        btnBMIdelete = (Button) findViewById(R.id.btnBMIVitalDelete);
        btnBMIdelete.setOnClickListener(this);

        tvBMIHeight.setText(vitalInfo.getHeight());
        tvBMIWeight.setText(vitalInfo.getWeight());
        tvBMIValue.setText(vitalInfo.getVitalValue());
        tvBMILastUpdated.setText(vitalInfo.getVitalLastUpdated());
        tvBMINotes.setText(vitalInfo.getNotes());
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("id", vitalInfo.getUid());
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("id", vitalInfo.getUid());
                setResult(RESULT_OK, intent);
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnBMIVitalDelete:
                new AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to delete this record?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //delete record
                                String query = "DELETE FROM bmi WHERE ID="+vitalInfo.getId();
                                try {
                                    dbHelper.execSQL(query);
                                    Intent intent = new Intent();
                                    intent.putExtra("id", vitalInfo.getUid());
                                    setResult(RESULT_CANCELED, intent);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                break;

            case R.id.btnBGCVitalDelete:
                new AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to delete this record?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //delete record
                                String query = "DELETE FROM bgc WHERE ID="+vitalInfo.getId();
                                try {
                                    dbHelper.execSQL(query);
                                    Intent intent = new Intent();
                                    intent.putExtra("id", vitalInfo.getUid());
                                    setResult(RESULT_CANCELED, intent);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                break;

            case R.id.btnBPVitalDelete:
                new AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to delete this record?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //delete record
                                String query = "DELETE FROM bp WHERE ID="+vitalInfo.getId();
                                try {
                                    dbHelper.execSQL(query);
                                    Intent intent = new Intent();
                                    intent.putExtra("id", vitalInfo.getUid());
                                    setResult(RESULT_CANCELED, intent);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                break;
        }
    }


}

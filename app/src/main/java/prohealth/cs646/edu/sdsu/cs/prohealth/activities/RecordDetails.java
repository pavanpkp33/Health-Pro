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

import com.dd.processbutton.iml.ActionProcessButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.Ailment;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.MedicalVisit;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.Vaccination;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.VitalInformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RecordDetails extends AppCompatActivity implements View.OnClickListener {

    String tag;
    Button btnVacDelete, btnMedDelete, btnAilDelete;
    int id = 0;
    DataHelper dbHelper;
    Vaccination vacInfo;
    MedicalVisit medInfo;
    Ailment ailInfo;
    MaterialEditText etMedicalFname, etMedicalLName, etMedicalDov, etMedicalDept, etMedicalPurpose, etMedicalDrDiag,
            etMedicalDrMedicines, etMedicalDrNotes, etVaccinationName, etVacDateAdministered, etVacNotes,
            etAilmentName, etAilmentMed, etAilmentFirstSeen, etAilmentNotes;

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
        if(tag.equalsIgnoreCase("VAC")){
            setContentView(R.layout.details_vaccination);
            vacInfo = (Vaccination) bdl.getSerializable("record");
            initVACElements();

        }else if(tag.equalsIgnoreCase("MED")){
            setContentView(R.layout.details_medical);
            medInfo = (MedicalVisit) bdl.getSerializable("record");
            initMEDElements();
        }else{
            setContentView(R.layout.details_ailment);
            ailInfo = (Ailment) bdl.getSerializable("record");
            initAILElements();
        }



    }

    private void initAILElements() {
        id = medInfo.getUid();
        etAilmentName = (MaterialEditText) findViewById(R.id.disAilmentName);
        etAilmentMed = (MaterialEditText) findViewById(R.id.disAilmentMedication);
        etAilmentFirstSeen = (MaterialEditText) findViewById(R.id.disDateEncountered);
        etAilmentNotes = (MaterialEditText) findViewById(R.id.disAilmentNotes);
        btnAilDelete = (Button) findViewById(R.id.btnDisDelAilment);
        btnAilDelete.setOnClickListener(this);

        etAilmentName.setText(ailInfo.getAilment());
        etAilmentMed.setText(ailInfo.getMedication());
        etAilmentFirstSeen.setText(ailInfo.getEncounteredDate());
        etAilmentNotes.setText(ailInfo.getNotes());

    }

    private void initMEDElements() {
        id = medInfo.getUid();
        etMedicalFname = (MaterialEditText) findViewById(R.id.disDrFirstName);
        etMedicalLName = (MaterialEditText) findViewById(R.id.disDrLastName);
        etMedicalDov = (MaterialEditText) findViewById(R.id.disDateOfVisit);
        etMedicalDept = (MaterialEditText) findViewById(R.id.disDepartment);
        etMedicalPurpose = (MaterialEditText) findViewById(R.id.disPurposeOfVisit);
        etMedicalDrDiag = (MaterialEditText) findViewById(R.id.disDoctorDiagnosis);
        etMedicalDrMedicines = (MaterialEditText) findViewById(R.id.disMedications);
        etMedicalDrNotes = (MaterialEditText) findViewById(R.id.disMedicalNotes);
        btnMedDelete = (Button) findViewById(R.id.btnDisDelMedical);

        btnMedDelete.setOnClickListener(this);

        etMedicalFname.setText(medInfo.getDrFirstName());
        etMedicalLName.setText(medInfo.getDrLastName());
        etMedicalDov.setText(medInfo.getDateOfVisit());
        etMedicalDept.setText(medInfo.getDepartment());
        etMedicalPurpose.setText(medInfo.getPurposeOfVisit());
        etMedicalDrDiag.setText(medInfo.getDiagnosis());
        etMedicalDrMedicines.setText(medInfo.getMedications());
        etMedicalDrNotes.setText(medInfo.getNotes());
    }

    private void initVACElements() {
        id = vacInfo.getUid();
        etVaccinationName = (MaterialEditText) findViewById(R.id.disVaccinationName);
        etVacDateAdministered = (MaterialEditText) findViewById(R.id.disDateAdministered);
        etVacNotes = (MaterialEditText) findViewById(R.id.disVaccinationNotes);

        btnVacDelete = (Button) findViewById(R.id.btnDisDelVaccination);
        btnVacDelete.setOnClickListener(this);
        etVaccinationName.setText(vacInfo.getVaccination());
        etVacDateAdministered.setText(vacInfo.getAdministeredDate());
        etVacNotes.setText(vacInfo.getNotes());

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnDisDelVaccination:
                new AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to delete this record?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //delete record
                                String query = "DELETE FROM VAC WHERE ID="+vacInfo.getId();
                                try {
                                    dbHelper.execSQL(query);
                                    Intent intent = new Intent();
                                    intent.putExtra("id", vacInfo.getUid());
                                    setResult(RESULT_CANCELED, intent);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                break;

            case R.id.btnDisDelMedical:
                new AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to delete this record?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //delete record
                                String query = "DELETE FROM VISIT WHERE ID="+medInfo.getId();
                                try {
                                    dbHelper.execSQL(query);
                                    Intent intent = new Intent();
                                    intent.putExtra("id", medInfo.getUid());
                                    setResult(RESULT_CANCELED, intent);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                break;

            case R.id.btnDisDelAilment:
                new AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to delete this record?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //delete record
                                String query = "DELETE FROM AIL WHERE ID="+ailInfo.getId();
                                try {
                                    dbHelper.execSQL(query);
                                    Intent intent = new Intent();
                                    intent.putExtra("id", ailInfo.getUid());
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

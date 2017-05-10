package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.adapters.MedicalVisitAdapter;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.Constants;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.MedicalVisit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MedicalVisitList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    int id =0;
    ListView medListView;
    MedicalVisitAdapter medAdapter;
    DataHelper dbHelper;
    ArrayList<MedicalVisit> medList;
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
        setContentView(R.layout.activity_medical_visit_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.medToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Medical Visits");

        medListView = (ListView) findViewById(R.id.medicalListView);
        medList = new ArrayList<>();
        medAdapter = new MedicalVisitAdapter(this, medList);
        medListView.setDivider(null);
        medListView.setAdapter(medAdapter);
        medListView.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicalVisitList.this, AddRecords.class);
                intent.putExtra("id", id);
                intent.putExtra("tag", "medvisit");
                startActivityForResult(intent, 1);
            }
        });
        getData();
    }

    public void getData() {
        medList.clear();
        MedicalVisit infoObj;
        Cursor dbCursor;
        String arr [] = {String.valueOf(id)};
        dbCursor = dbHelper.select(Constants.GET_MED_DATA, arr);

        if(dbCursor.getCount() > 0){
            while(dbCursor.moveToNext()){
                infoObj = new MedicalVisit();

                infoObj.setId(dbCursor.getInt(0));
                infoObj.setUid(dbCursor.getInt(1));
                infoObj.setDrFirstName(dbCursor.getString(2));
                infoObj.setDrLastName(dbCursor.getString(3));
                infoObj.setPurposeOfVisit(dbCursor.getString(4));
                infoObj.setDateOfVisit(dbCursor.getString(5));
                infoObj.setDiagnosis(dbCursor.getString(6));
                infoObj.setMedications(dbCursor.getString(7));
                infoObj.setNotes(dbCursor.getString(8));
                infoObj.setDepartment(dbCursor.getString(9));
                medList.add(infoObj);
            }
            dbCursor.close();
        }
        medAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Record has been deleted", Toast.LENGTH_SHORT).show();
        }
        id = data.getIntExtra("id", 0);
        getData();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent detailsIntent = new Intent(this, RecordDetails.class);
        detailsIntent.putExtra("record", medList.get(position));
        detailsIntent.putExtra("tag", "MED");

        startActivityForResult(detailsIntent, 11);


    }

}

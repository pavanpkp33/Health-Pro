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
import prohealth.cs646.edu.sdsu.cs.prohealth.adapters.VaccinationAdapter;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.Constants;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.Vaccination;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VaccinationList extends AppCompatActivity implements AdapterView.OnItemClickListener{
    int id =0;
    ListView vacListView;
    VaccinationAdapter vacAdapter;
    DataHelper dbHelper;
    ArrayList<Vaccination> vacList;
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
        setContentView(R.layout.activity_vaccination_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.vaccinationToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Vaccinations");

        vacListView = (ListView) findViewById(R.id.vaccinationListView);
        vacList = new ArrayList<>();
        vacAdapter = new VaccinationAdapter(this, vacList);
        vacListView.setDivider(null);
        vacListView.setAdapter(vacAdapter);
        vacListView.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VaccinationList.this, AddRecords.class);
                intent.putExtra("id", id);
                intent.putExtra("tag", "vaccination");
                startActivityForResult(intent, 1);
            }
        });
        getData();
    }

    public void getData() {
        vacList.clear();
        Vaccination infoObj;
        Cursor dbCursor;
        String arr [] = {String.valueOf(id)};
        dbCursor = dbHelper.select(Constants.GET_VAC_DATA, arr);

        if(dbCursor.getCount() > 0){
            while(dbCursor.moveToNext()){
                infoObj = new Vaccination();

                infoObj.setId(dbCursor.getInt(0));
                infoObj.setUid(dbCursor.getInt(1));
                infoObj.setVaccination(dbCursor.getString(2));
                infoObj.setAdministeredDate(dbCursor.getString(3));
                infoObj.setNotes(dbCursor.getString(4));

                vacList.add(infoObj);
            }
            dbCursor.close();
        }
        vacAdapter.notifyDataSetChanged();

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
        detailsIntent.putExtra("record", vacList.get(position));
        detailsIntent.putExtra("tag", "VAC");
        startActivityForResult(detailsIntent, 11);


    }

}

package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.adapters.VitalAdapter;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.Constants;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.VitalInformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BP extends AppCompatActivity implements AdapterView.OnItemClickListener{
    int id = 0;
    ListView bpListView;
    VitalAdapter vAdapter;
    DataHelper dbHelper;
    ArrayList<VitalInformation> vitalList;
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
        setContentView(R.layout.activity_bp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.bpToolbar);
        setSupportActionBar(toolbar);

        bpListView = (ListView) findViewById(R.id.bpListView);
        vitalList = new ArrayList<>();
        vAdapter = new VitalAdapter(this, vitalList, "BP");
        bpListView.setDivider(null);
        bpListView.setAdapter(vAdapter);
        bpListView.setOnItemClickListener(this);

        getSupportActionBar().setTitle("Blood Pressure");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBP);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BP.this, VitalDetails.class);
                intent.putExtra("id", id);
                intent.putExtra("tag", "BP");
                intent.putExtra("action", "add");
                startActivityForResult(intent, 3);
            }
        });
        getListData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("id", id);
                setResult(RESULT_OK, intent);
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3){
            id = data.getIntExtra("id",0);
            //reload list
            getListData();
        }else{
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Record has been deleted", Toast.LENGTH_SHORT).show();
            }
            id = data.getIntExtra("id",0);
            //reload list
            getListData();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent detailsIntent = new Intent(this, VitalRecordDetails.class);
        detailsIntent.putExtra("record", vitalList.get(position));
        detailsIntent.putExtra("tag", "BP");
        detailsIntent.putExtra("header", "Blood Pressure details");
        startActivityForResult(detailsIntent, 11);

    }

    private void getListData() {
        vitalList.clear();
        VitalInformation infoObj;
        Cursor dbCursor;
        String arr [] = {String.valueOf(id)};
        dbCursor = dbHelper.select(Constants.GET_BP_DATA, arr);

        if(dbCursor.getCount() > 0){
            while(dbCursor.moveToNext()){
                infoObj = new VitalInformation();
                infoObj.setVitalHeader("Blood Pressure");
                infoObj.setId(dbCursor.getInt(0));
                infoObj.setUid(dbCursor.getInt(1));
                infoObj.setSystolic(dbCursor.getString(2));
                infoObj.setDiastolic(dbCursor.getString(3));
                infoObj.setBpm(dbCursor.getString(4));
                String BP = dbCursor.getString(2)+"/"+dbCursor.getString(3)+" "+dbCursor.getString(4);
                infoObj.setVitalValue(BP);
                infoObj.setNotes(dbCursor.getString(5));
                infoObj.setVitalLastUpdated(dbCursor.getString(6));

                vitalList.add(infoObj);
            }
            dbCursor.close();
        }
        vAdapter.notifyDataSetChanged();

    }
}

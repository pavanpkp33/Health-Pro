package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.adapters.VitalAdapter;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.Constants;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.VitalInformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BMI extends AppCompatActivity implements AdapterView.OnItemClickListener{

    int id = 0;
    ListView bmiListView;
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
        setContentView(R.layout.activity_bmi);
        Toolbar bmiToolbar = (Toolbar) findViewById(R.id.bmiToolbar);
        setSupportActionBar(bmiToolbar);

        bmiListView = (ListView) findViewById(R.id.bmiListView);
        vitalList = new ArrayList<>();
        vAdapter = new VitalAdapter(this, vitalList, "BMI");
        bmiListView.setDivider(null);
        bmiListView.setAdapter(vAdapter);
        bmiListView.setOnItemClickListener(this);

        getSupportActionBar().setTitle("Body mass index");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBMI);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BMI.this, VitalDetails.class);
                intent.putExtra("id", id);
                intent.putExtra("tag", "BMI");
                startActivityForResult(intent, 1);
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
        if(requestCode == 1){
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

    }

    private void getListData() {
        vitalList.clear();
        VitalInformation infoObj;
        Cursor dbCursor;
        String arr [] = {String.valueOf(id)};
        dbCursor = dbHelper.select(Constants.GET_BMI_DATA, arr);

        if(dbCursor.getCount() > 0){
            while(dbCursor.moveToNext()){
                infoObj = new VitalInformation();
                infoObj.setVitalHeader("Body mass index");
                infoObj.setId(dbCursor.getInt(0));
                infoObj.setUid(dbCursor.getInt(1));
                infoObj.setVitalValue(dbCursor.getString(4));
                infoObj.setNotes(dbCursor.getString(5));
                infoObj.setVitalLastUpdated(dbCursor.getString(6));

                vitalList.add(infoObj);
            }
            dbCursor.close();
        }
        vAdapter.notifyDataSetChanged();

    }
}

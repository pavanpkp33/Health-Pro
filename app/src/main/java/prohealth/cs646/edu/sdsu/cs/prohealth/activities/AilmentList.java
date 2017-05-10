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
import prohealth.cs646.edu.sdsu.cs.prohealth.adapters.AilmentAdapter;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.Constants;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import prohealth.cs646.edu.sdsu.cs.prohealth.model.Ailment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AilmentList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    int id =0;
    ListView ailListView;
    AilmentAdapter aAdapter;
    DataHelper dbHelper;
    ArrayList<Ailment> ailList;
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
        setContentView(R.layout.activity_ailment_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ailToolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Ailment List");


        ailListView = (ListView) findViewById(R.id.ailmentListView);
        ailList = new ArrayList<>();
        aAdapter = new AilmentAdapter(this, ailList);
        ailListView.setDivider(null);
        ailListView.setAdapter(aAdapter);
        ailListView.setOnItemClickListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AilmentList.this, AddRecords.class);
                intent.putExtra("id", id);
                intent.putExtra("tag", "ailment");
                startActivityForResult(intent, 1);
            }
        });
        getData();
    }

    public void getData() {
        ailList.clear();
        Ailment infoObj;
        Cursor dbCursor;
        String arr [] = {String.valueOf(id)};
        dbCursor = dbHelper.select(Constants.GET_AIL_DATA, arr);

        if(dbCursor.getCount() > 0){
            while(dbCursor.moveToNext()){
                infoObj = new Ailment();

                infoObj.setId(dbCursor.getInt(0));
                infoObj.setUid(dbCursor.getInt(1));
                infoObj.setAilment(dbCursor.getString(2));
                infoObj.setEncounteredDate(dbCursor.getString(3));
                infoObj.setMedication(dbCursor.getString(4));
                infoObj.setNotes(dbCursor.getString(5));

                ailList.add(infoObj);
            }
            dbCursor.close();
        }
        aAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent detailsIntent = new Intent(this, RecordDetails.class);
        detailsIntent.putExtra("record", ailList.get(position));
        detailsIntent.putExtra("tag", "AIL");
        startActivityForResult(detailsIntent, 2);


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
}

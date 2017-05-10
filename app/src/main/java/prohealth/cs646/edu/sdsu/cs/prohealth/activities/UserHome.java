package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.Constants;
import prohealth.cs646.edu.sdsu.cs.prohealth.helpers.DataHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserHome extends AppCompatActivity  implements OnBoomListener, View.OnClickListener{

    DataHelper dbHelper;
    TextView tvUserName, tvDob, tvGender, tvBMI, tvBMIDate, tvGlucose, tvGlucoseDate, tvBP, tvBPDate, tvUnit, tvBPUnit;
    CardView bmiView, bgcView, bpView;
    BoomMenuButton bmb;
    int userId = 0;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle bdl = getIntent().getExtras();
        if(bdl != null){
            userId = bdl.getInt("id");

        }
        dbHelper = new DataHelper(this);
        String [] buttonNames = {"Ailment", "Medical Visit", "Vaccinations", "Profile"};
        int [] resources = { R.drawable.ic_disease, R.drawable.ic_doctor, R.drawable.ic_syringe, R.drawable.ic_profile };
        setContentView(R.layout.activity_user_home);
        Toolbar tbUser = (Toolbar) findViewById(R.id.tbUserHome);
        this.setSupportActionBar(tbUser);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initUIElements();
        getUserData(userId);
        getVitalInfo(userId);
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .normalImageRes(resources[i])
                    .normalText(buttonNames[i]);
            bmb.addBuilder(builder);

        }
        bmb.setOnBoomListener(this);

    }

    private void getVitalInfo(int userId) {
        Cursor c;
        String [] arr = { String.valueOf(userId)};
        c = dbHelper.select(Constants.GET_BMI_DATA, arr);
        if(c.getCount() > 0){
            c.moveToFirst();
            double BMI = c.getDouble(4);
            tvBMI.setText(String.format("%.2f",BMI));
            tvBMIDate.setText(c.getString(6));

        }else{
            tvBMI.setText("No Data");
            tvBMIDate.setText("No Data available");
        }

        c = dbHelper.select(Constants.GET_BGC_DATA, arr);
        if(c.getCount() > 0){
            c.moveToFirst();
            tvGlucose.setText(c.getString(2));
            tvGlucoseDate.setText(c.getString(4));
            tvUnit.setVisibility(View.VISIBLE);
        }else{
            tvGlucose.setText("No Data");
            tvGlucoseDate.setText("No Data available");
            tvUnit.setVisibility(View.GONE);
        }

        c = dbHelper.select(Constants.GET_BP_DATA, arr);
        if(c.getCount() > 0){
            c.moveToFirst();
            String BP = c.getString(2)+"/"+c.getString(3)+" "+c.getString(4);
            tvBP.setText(BP);
            tvBPDate.setText(c.getString(6));
            tvBPUnit.setVisibility(View.VISIBLE);

        }else{
            tvBP.setText("No Data");
            tvBPDate.setText("No Data available");
            tvBPUnit.setVisibility(View.GONE);

        }
        c.close();
    }

    private void getUserData(int userId) {
        Cursor dbCursor;
        String [] arr = { String.valueOf(userId)};
        dbCursor = dbHelper.select(Constants.GET_USER_DATA, arr);
        dbCursor.moveToFirst();
        if(dbCursor.getCount() > 0){

            tvUserName.setText(dbCursor.getString(1));
            tvDob.setText(dbCursor.getString(3));
            tvGender.setText(dbCursor.getString(4));


        }
        dbCursor.close();
    }

    private void initUIElements() {

        tvUserName = (TextView) findViewById(R.id.tvUserHomeName);
        tvGender = (TextView) findViewById(R.id.tvUserHomeGender);
        tvDob = (TextView) findViewById(R.id.tvUserHomeDob);
        tvBMI = (TextView) findViewById(R.id.tvBMIValue);
        tvBMIDate = (TextView) findViewById(R.id.tvBMILastUpdated);
        tvGlucose = (TextView) findViewById(R.id.tvBloodGlucoseVal);
        tvGlucoseDate = (TextView) findViewById(R.id.tvBGLastUpdated);
        tvBP = (TextView) findViewById(R.id.tvBPValue);
        tvBPDate = (TextView) findViewById(R.id.tvBPLastUpdated);
        tvUnit = (TextView) findViewById(R.id.tvMMOL);
        tvBPUnit = (TextView) findViewById(R.id.tvBpm);
        bmb= (BoomMenuButton) findViewById(R.id.bmb);
        bmiView = (CardView) findViewById(R.id.cardBMI);
        bgcView = (CardView) findViewById(R.id.cardGlucose);
        bpView = (CardView) findViewById(R.id.cardPressure);

        bmiView.setOnClickListener(this);
        bgcView.setOnClickListener(this);
        bpView.setOnClickListener(this);


    }



    @Override
    public void onClicked(int index, BoomButton boomButton) {
        switch (index){

            case 0: Intent ailIntent = new Intent(this, AilmentList.class);
                ailIntent.putExtra("id", userId);
                startActivity(ailIntent); break;

            case 1: Intent medIntent = new Intent(this, MedicalVisitList.class);
                    medIntent.putExtra("id", userId);
                    startActivity(medIntent); break;

            case 2:  Intent vacIntent = new Intent(this, VaccinationList.class);
                        vacIntent.putExtra("id", userId);
                        startActivity(vacIntent); break;

            case 3: Intent intent = new Intent(this, Profile.class);
                intent.putExtra("id", userId);
                startActivity(intent); break;


        }
        if(index == 3){

        }
    }

    @Override
    public void onBackgroundClick() {
        System.out.println("Not Implemented");
    }

    @Override
    public void onBoomWillHide() {
        System.out.println("Not Implemented");
    }

    @Override
    public void onBoomDidHide() {
        System.out.println("Not Implemented");
    }

    @Override
    public void onBoomWillShow() {
        System.out.println("Not Implemented");
    }

    @Override
    public void onBoomDidShow() {
        System.out.println("Not Implemented");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cardBMI:
                Intent intent = new Intent(this, BMI.class);
                intent.putExtra("id", userId);
                startActivityForResult(intent,1);
                break;

            case R.id.cardGlucose:
                Intent bgIntent = new Intent(this, BGC.class);
                bgIntent.putExtra("id", userId);
                startActivityForResult(bgIntent,2);
                break;

            case R.id.cardPressure:
                Intent bpIntent = new Intent(this, BP.class);
                bpIntent.putExtra("id", userId);
                startActivityForResult(bpIntent,3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            // BMI
            userId = data.getIntExtra("id",0);
            getUserData(userId);
            getVitalInfo(userId);
        }else if(requestCode == 2){
            userId = data.getIntExtra("id",0);
            getUserData(userId);
            getVitalInfo(userId);
        }else if(requestCode == 3){
            userId = data.getIntExtra("id",0);
            getUserData(userId);
            getVitalInfo(userId);

        }
    }
}

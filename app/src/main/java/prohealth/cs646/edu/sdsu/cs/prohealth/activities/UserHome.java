package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserHome extends AppCompatActivity  implements OnBoomListener{

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String [] buttonNames = {"Medications", "Diseases", "Medical Visit", "Vaccinations", "Other", "Profile"};
        int [] resources = {R.drawable.pill, R.drawable.ic_disease, R.drawable.ic_doctor, R.drawable.ic_syringe, R.drawable.ic_bulb, R.drawable.ic_profile };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Toolbar tbUser = (Toolbar) findViewById(R.id.tbUserHome);
        this.setSupportActionBar(tbUser);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .normalImageRes(resources[i])
                    .normalText(buttonNames[i]);
            bmb.addBuilder(builder);
        }
        bmb.setOnBoomListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_settings:
                Toast.makeText(this, "Skip selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onClicked(int index, BoomButton boomButton) {

        System.out.println(boomButton.getTextView().getText());
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
}

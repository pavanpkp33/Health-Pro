package prohealth.cs646.edu.sdsu.cs.prohealth.activities;

import android.app.Application;

import prohealth.cs646.edu.sdsu.cs.prohealth.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Pkp on 05/04/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}

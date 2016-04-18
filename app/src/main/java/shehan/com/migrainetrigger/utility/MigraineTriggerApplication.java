package shehan.com.migrainetrigger.utility;


import android.app.Application;
import android.content.Context;

import com.johnhiott.darkskyandroidlib.ForecastApi;

/**
 * This class is used to get the context of the application to the DatabaseHandler.DataBaseHelper inner class which extends from SQLiteOpenHelper
 * Created by Shehan on 4/13/2016.
 */
public class MigraineTriggerApplication extends Application {
    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ForecastApi.create("716be03d1038c9896a402c8cd50abf29");
    }
}
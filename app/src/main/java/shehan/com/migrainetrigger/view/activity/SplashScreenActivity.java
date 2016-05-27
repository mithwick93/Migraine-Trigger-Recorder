package shehan.com.migrainetrigger.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.MigraineTriggerApplication;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

/**
 * Created by Shehan on 24/05/2016.
 */
public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new InitializerTask().execute();
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    /**
     * Async task to initialize db and load Main activity
     */
    private class InitializerTask extends AsyncTask<Void, Void, Void> {
        Intent intent;

        @Override
        protected Void doInBackground(Void... params) {
            //First initialization
            DatabaseHandler.testDatabase();
            intent = new Intent(SplashScreenActivity.this, MainActivity.class);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            //enable suggestions
            boolean senMail = PreferenceManager.getDefaultSharedPreferences(MigraineTriggerApplication.getAppContext()).getBoolean("pref_send", false);
            if (senMail) {
                performSendViaEmail();
            }
            startActivity(intent);
            finish();
        }

        /**
         * send record data via email
         */
        private void performSendViaEmail() {
            Log.d("InitializerTask", "performSendViaEmail");
            File data = Environment.getDataDirectory();

            String currentDBPath = "//data//" + "shehan.com.migrainetrigger"
                    + "//databases//" + "MigraineTrigger";
            File currentDB = new File(data, currentDBPath);//get database file

            if (!currentDB.exists()) {
                Log.e("InitializerTask", "database does not exist");
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            String currentTime = dateFormat.format(date);//get current time

            BackgroundMail.newBuilder(SplashScreenActivity.this)
                    .withUsername("mithwick93@gmail.com")
                    .withPassword("home0382294116")
                    .withMailto("shehanwick93@gmail.com")
                    .withSubject("Migraine Trigger record data")
                    .withBody(String.format("Sample data as of : %s", currentTime))
                    .withAttachments(currentDB.getPath())
                    .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                        @Override
                        public void onSuccess() {
                            //do some magic
                            AppUtil.showToast(SplashScreenActivity.this, "Send Success");
                            Log.d("InitializerTask", "Send mail success");
                        }
                    })
                    .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                        @Override
                        public void onFail() {
                            //do some magic
                            AppUtil.showToast(SplashScreenActivity.this, "Send Failed");
                            Log.e("InitializerTask", "Send mail failed");
                        }
                    });
            //.send();  -DEBUG
        }
    }
}

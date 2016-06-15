package shehan.com.migrainetrigger.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import shehan.com.migrainetrigger.R;
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
    private class InitializerTask extends AsyncTask<Void, Void, Boolean> {
        Intent intent;

        @Override
        protected Boolean doInBackground(Void... params) {
            //First initialization

            try {
                DatabaseHandler.testDatabase();
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //enable suggestions
            if (result && intent != null) {
                startActivity(intent);
                finish();
            } else {
                Log.e("MigraineTrigger", "Invalid database detected. Migraine trigger will exit");
                android.os.Process.killProcess(android.os.Process.myPid());
                SplashScreenActivity.super.onDestroy();
            }
        }

    }
}

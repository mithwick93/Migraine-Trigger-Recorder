package shehan.com.migrainetrigger.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

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

            startActivity(intent);
            finish();
        }

    }
}

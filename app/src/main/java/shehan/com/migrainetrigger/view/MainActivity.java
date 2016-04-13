package shehan.com.migrainetrigger.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import shehan.com.migrainetrigger.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shehan.com.migrainetrigger.utility.database.DatabaseHandler.getReadableDatabase();
    }
}

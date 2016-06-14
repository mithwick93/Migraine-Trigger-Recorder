package shehan.com.migrainetrigger.view.fragment.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.csv.CSVWriter;

/**
 * Created by Shehan on 12/05/2016.
 */
public class SettingsFragment extends PreferenceFragment {

    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE_BACKUP = 10;
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE_RESTORE = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        Preference prefSendEmail = findPreference("pref_send_email");
        prefSendEmail.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                initiateSendEmail();
                return true;
            }
        });

        ListPreference themePreference = (ListPreference) findPreference("pref_appTheme");
        themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                AppUtil.showToast(getActivity(), "Theme will be applied when you close the settings window");

                return true;
            }
        });

        ListPreference colorSchemePreference = (ListPreference) findPreference("pref_appColor");
        colorSchemePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                AppUtil.showToast(getActivity(), "Color scheme will be applied when you close the settings window");

                return true;
            }
        });

        Preference prefBackup = findPreference("pref_key_backup");
        prefBackup.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                initiateBackup();
                return true;
            }
        });

        Preference prefRestore = findPreference("pref_key_restore");
        prefRestore.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                initiateRestore();
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_WRITE_EXTERNAL_STORAGE_BACKUP:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    new BackupDatabaseTask().execute();
                } else {
                    // Permission Denied
                    AppUtil.showToast(getActivity(), "SD card access Denied");
                }
                break;
            case PERMISSION_WRITE_EXTERNAL_STORAGE_RESTORE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    new RestoreDatabaseTask().execute();
                } else {
                    // Permission Denied
                    AppUtil.showToast(getActivity(), "SD card access Denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initiateBackup() {
        new MaterialDialog.Builder(getActivity())
                .title("Backup conformation")
                .content("If backup exists it will be overwritten")
                .positiveText("Ok")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (isStoragePermissionGranted(PERMISSION_WRITE_EXTERNAL_STORAGE_BACKUP)) {
                            new BackupDatabaseTask().execute();
                        }
                    }
                })
                .show();
    }

    private void initiateRestore() {
        new MaterialDialog.Builder(getActivity())
                .title("Restore conformation")
                .content("Application database will be overwritten")
                .positiveText("Ok")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (isStoragePermissionGranted(PERMISSION_WRITE_EXTERNAL_STORAGE_RESTORE)) {
                            new RestoreDatabaseTask().execute();
                        }
                    }
                })
                .show();
    }

    private void initiateSendEmail() {
        if (isStoragePermissionGranted(PERMISSION_WRITE_EXTERNAL_STORAGE_BACKUP)) {
            new SendEmailTask().execute();
        }
    }

    private boolean isStoragePermissionGranted(final int request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("SettingsFragment", "Permission is granted");
                return true;
            } else {
                Log.v("SettingsFragment", "Permission is revoked");
                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new MaterialDialog.Builder(getContext())
                            .content("This app wants to access your SD card.")
                            .positiveText("Agree")
                            .negativeText("Disagree")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {

                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, request);
                                }
                            })
                            .show();
                    return false;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, request);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("SettingsFragment", "Permission is granted");
            return true;
        }
    }

    /**
     * Async task to backup database
     */
    private class BackupDatabaseTask extends AsyncTask<String, Void, String> {

        private ProgressDialog nDialog;

        /* Checks if external storage is available for read and write */
        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED.equals(state);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("BackupDatabaseTask", "doInBackground");

            String result = performBackup();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         * Backup application data
         */
        private String performBackup() {
            try {
                if (!isExternalStorageWritable()) {
                    throw new Exception("Cannot access sd card");
                }
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();
                if (sd.canWrite()) {

                    sd = new File(Environment.getExternalStorageDirectory() + "/MigraineTrigger");
                    //Create app directory if not exists
                    boolean result = false;
                    if (!sd.exists()) {
                        result = sd.mkdir();
                    }

                    if (sd.exists() || result) {
                        String currentDBPath = "//data//" + "shehan.com.migrainetrigger"
                                + "//databases//" + "MigraineTrigger";

                        String backupDBPath = "MigraineTriggerBackup"; // From SD directory.
                        File currentDB = new File(data, currentDBPath);
                        File backupDB = new File(sd, backupDBPath);

                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                        return "TRUE";
                    } else {
                        throw new Exception("Cannot create app folder");
                    }

                } else {
                    throw new Exception("Cannot access sdcard");
                }

            } catch (FileNotFoundException e) {
                Log.e("SettingsFragment", "File Not Found exception");
                e.printStackTrace();
                return "file not found";
            } catch (Exception e) {
                Log.e("SettingsFragment", "Backup exception");
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Performing backup...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }


        @Override
        protected void onPostExecute(String result) {
            Log.d("BackupDatabaseTask", " onPostExecute - show status");

            if (nDialog != null) {
                nDialog.dismiss();
            }
            if (result.equals("TRUE")) {
                AppUtil.showToast(getActivity(), "Backup Successful!");
            } else {
                AppUtil.showToast(getActivity(), "Backup Failed : " + result);
            }
        }
    }

    /**
     * Async task to restore database
     */
    private class RestoreDatabaseTask extends AsyncTask<String, Void, String> {

        private ProgressDialog nDialog;

        /* Checks if external storage is available for read and write */
        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED.equals(state);
        }

        /**
         * Restore application data
         */
        private String performRestore() {
            try {
                if (!isExternalStorageWritable()) {
                    throw new Exception("Cannot access sd card");
                }
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                if (sd.canWrite()) {

                    sd = new File(Environment.getExternalStorageDirectory() + "/MigraineTrigger");
                    //Create app directory if not exists
                    boolean result = false;
                    if (!sd.exists()) {
                        result = sd.mkdir();
                    }
                    if (sd.exists() || result) {
                        String currentDBPath = "//data//" + "shehan.com.migrainetrigger"
                                + "//databases//" + "MigraineTrigger";
                        String backupDBPath = "MigraineTriggerBackup"; // From SD directory.
                        File backupDB = new File(data, currentDBPath);
                        File currentDB = new File(sd, backupDBPath);

                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();

                        return "TRUE";
                    } else {
                        throw new Exception("Cannot create app folder");
                    }
                } else {
                    throw new Exception("Cannot access sdcard");
                }
            } catch (FileNotFoundException e) {
                Log.e("SettingsFragment", "File Not Found exception");
                e.printStackTrace();
                return "file not found";
            } catch (Exception e) {
                Log.e("SettingsFragment", "Restore exception");
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Performing restore...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("RestoreDatabaseTask", "doInBackground");

            String result = performRestore();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("RestoreDatabaseTask", " onPostExecute - show status");

            if (nDialog != null) {
                nDialog.dismiss();
            }
            if (result.equals("TRUE")) {
                AppUtil.showToast(getActivity(), "Restore Successful!");
            } else {
                AppUtil.showToast(getActivity(), "Restore Failed : " + result);
            }
        }
    }

    /**
     * Async task to create temporary db on external and open email
     */
    private class SendEmailTask extends AsyncTask<String, Void, File> {

        private ProgressDialog nDialog;

        /* Checks if external storage is available for read and write */
        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED.equals(state);
        }

        /**
         * Create copy of records to csv file
         */
        @Nullable
        private File performCSVCopy() {
            try {

                ArrayList<String[]> recordLst = RecordController.getAllRecordsOrderByDateRAW();

                if (recordLst.size() < 1) {
                    throw new Exception("No records found to send");
                }

                if (!isExternalStorageWritable()) {
                    throw new Exception("Cannot access sd card");
                }

                File sd = Environment.getExternalStorageDirectory();
                if (sd.canWrite()) {

                    sd = new File(Environment.getExternalStorageDirectory() + "/tmp");
                    //Create app directory if not exists
                    boolean result = false;
                    if (!sd.exists()) {
                        result = sd.mkdir();
                    }

                    if (sd.exists() || result) {

                        String csvName = "MigraineTrigger.csv"; // From SD directory.
                        File csvFile = new File(sd, csvName);

                        final boolean createResult = csvFile.createNewFile();

                        CSVWriter csvWrite = new CSVWriter(new FileWriter(csvFile));

                        csvWrite.writeNext(new String[]{"Record No", "Start", "End", "Intensity"});

                        for (String[] strArray : recordLst) {
                            csvWrite.writeNext(strArray);
                        }

                        csvWrite.close();

                        if (csvFile.exists()) {
                            return csvFile;
                        }

                        throw new Exception("Tmp file does not exist");
                    } else {
                        throw new Exception("Cannot create app folder");
                    }

                } else {
                    throw new Exception("Cannot access sdcard");
                }

            } catch (FileNotFoundException e) {
                Log.e("SettingsFragment", "File Not Found exception");
                e.printStackTrace();
                return null;

            } catch (Exception e) {
                Log.e("SettingsFragment", "Create temp exception");
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Create copy of application data
         */
        @Nullable
        private File performTempCopy() {
            try {
                if (!isExternalStorageWritable()) {
                    throw new Exception("Cannot access sd card");
                }
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();
                if (sd.canWrite()) {

                    sd = new File(Environment.getExternalStorageDirectory() + "/tmp");
                    //Create app directory if not exists
                    boolean result = false;
                    if (!sd.exists()) {
                        result = sd.mkdir();
                    }

                    if (sd.exists() || result) {
                        String currentDBPath = "//data//" + "shehan.com.migrainetrigger"
                                + "//databases//" + "MigraineTrigger";

                        String tempDBPath = "MigraineTrigger"; // From SD directory.
                        File currentDB = new File(data, currentDBPath);
                        File tmpDB = new File(sd, tempDBPath);

                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(tmpDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();

                        if (tmpDB.exists()) {
                            return tmpDB;
                        }
                        throw new Exception("Tmp file does not exist");
                    } else {
                        throw new Exception("Cannot create app folder");
                    }

                } else {
                    throw new Exception("Cannot access sdcard");
                }

            } catch (FileNotFoundException e) {
                Log.e("SettingsFragment", "File Not Found exception");
                e.printStackTrace();
                return null;

            } catch (Exception e) {
                Log.e("SettingsFragment", "create temp exception");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected File doInBackground(String... params) {
            Log.d("SendEmailTask", "doInBackground");

            File tmp = performTempCopy();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return tmp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Preparing records ...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected void onPostExecute(File tmp) {
            Log.d("SendEmailTask", " onPostExecute - show email intent");
            if (nDialog != null) {
                nDialog.dismiss();
            }
            if (tmp != null) {

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm a", Locale.getDefault());
                Date date = new Date();
                String currentTime = dateFormat.format(date);//get current time

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Migraine Trigger data");
                intent.putExtra(Intent.EXTRA_TEXT, String.format("Record database as of : %s", currentTime));
                intent.setType("application/octet-stream");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tmp));
                startActivity(Intent.createChooser(intent, "Send via e-mail"));

            } else {
                Log.e("SettingsFragment", "null temp file");
                AppUtil.showToast(SettingsFragment.this.getActivity(), "Something went wrong");
            }
        }
    }
}
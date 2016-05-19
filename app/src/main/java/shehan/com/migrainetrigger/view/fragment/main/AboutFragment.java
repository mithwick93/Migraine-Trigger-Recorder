package shehan.com.migrainetrigger.view.fragment.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import shehan.com.migrainetrigger.R;

public class AboutFragment extends Fragment {

    private TextView viewTxtLicense;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        viewTxtLicense = (TextView) view.findViewById(R.id.txt_about_licenses);

        viewTxtLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLicensesAlertDialog();
            }
        });
        return view;
    }

    private void displayLicensesAlertDialog() {
        WebView view = (WebView) LayoutInflater.from(getContext()).inflate(R.layout.dialog_licenses, null);
        view.loadUrl("file:///android_asset/open_source_licenses.html");
        new AlertDialog.Builder(getContext())
                .setTitle("Open source licenses")
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

}

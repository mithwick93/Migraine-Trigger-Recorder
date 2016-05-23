package shehan.com.migrainetrigger.view.fragment.faq;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.AppUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreventionFragment extends Fragment {


    public PreventionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq_web_view, container, false);
        WebView myWebView = (WebView) view.findViewById(R.id.webView);
        myWebView.loadUrl("http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/prevention/con-20026358");
        AppUtil.showToast(getContext(), "Redirecting to www.mayoclinic.org");
        return view;
    }

}

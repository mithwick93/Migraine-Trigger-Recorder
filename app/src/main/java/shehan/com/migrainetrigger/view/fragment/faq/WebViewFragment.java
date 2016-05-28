package shehan.com.migrainetrigger.view.fragment.faq;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.AppUtil;

public class WebViewFragment extends Fragment {
    private static final String ARG_FAQ_SECTION = "faqSection";
    private volatile String faqSection;

    public WebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Static method to get new instance of this fragment
     *
     * @param faqSection faqSection
     * @return WebViewFragment
     */
    public static WebViewFragment newInstance(String faqSection) {

        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FAQ_SECTION, faqSection);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            faqSection = getArguments().getString(ARG_FAQ_SECTION);
        } else {
            Log.e("WebViewFragment", "Invalid FAQ Section");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq_web_view, container, false);
        loadPage(view);
        return view;
    }


    private void loadPage(View view) {
        final WebView webView = (WebView) view.findViewById(R.id.webView);
        String strUrl = "NULL";
        switch (faqSection) {
            case "Definition":
                strUrl = "http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/definition/con-20026358";
                break;
            case "Symptoms":
                strUrl = "http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/symptoms/con-20026358";
                break;
            case "Causes":
                strUrl = "http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/causes/con-20026358";
                break;
            case "Risk":
                strUrl = "http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/risk-factors/con-20026358";
                break;
            case "Complications":
                strUrl = "http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/complications/con-20026358";
                break;
            case "Diagnosis":
                strUrl = "http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/tests-diagnosis/con-20026358";
                break;
            case "Treatments":
                strUrl = "http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/treatment/con-20026358";
                break;
            case "Remedies":
                strUrl = "http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/lifestyle-home-remedies/con-20026358";
                break;
            case "Alternative":
                strUrl = "http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/alternative-medicine/con-20026358";
                break;
            case "Prevention":
                strUrl = "http://www.mayoclinic.org/diseases-conditions/migraine-headache/basics/prevention/con-20026358";
                break;
        }
        if (!strUrl.equals("NULL")) {
            webView.setClickable(false);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setBackgroundColor(0);


            final Activity activity = getActivity();
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return true;
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    String content = "<html>\n" +
                            "<head>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<h1 style=\"text-align:center;color:#F44336;padding:15px;\">Something Went Wrong</h1>\n" +
                            "<p style=\"text-align:center;color:#F44336;\">Could not load " + faqSection + " page &#9785;</p>\n" +
                            "</body>\n" +
                            "</html>";
                    webView.loadData(content, "text/html", "utf-8");
                }

            });

            webView.loadUrl(strUrl);
            AppUtil.showToast(getContext(), "Redirecting to www.mayoclinic.org");
        }

    }
}

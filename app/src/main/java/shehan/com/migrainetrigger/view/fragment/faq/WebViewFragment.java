package shehan.com.migrainetrigger.view.fragment.faq;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import shehan.com.migrainetrigger.R;

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
        String htmlContent = "NULL";
        switch (faqSection) {
            case "Definition":
                htmlContent = getHTML("definition");
                break;
            case "Symptoms":
                htmlContent = getHTML("symptoms");
                break;
            case "Causes":
                htmlContent = getHTML("causes");
                break;
            case "Risk":
                htmlContent = getHTML("risk");
                break;
            case "Complications":
                htmlContent = getHTML("complications");
                break;
            case "Diagnosis":
                htmlContent = getHTML("diagnosis");
                break;
            case "Treatments":
                htmlContent = getHTML("treatments");
                break;
            case "Remedies":
                htmlContent = getHTML("remedies");
                break;
            case "Alternative":
                htmlContent = getHTML("alternative");
                break;
            case "Prevention":
                htmlContent = getHTML("prevention");
                break;
        }
        if (!htmlContent.equals("NULL")) {
            webView.setClickable(false);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            //webView.setBackgroundColor(0);

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

                public void onReceivedHttpError(
                        WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
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

            webView.loadData(composeWebPage(htmlContent), "text/html", "utf-8");
        }

    }

    /**
     * Method to get html file contents from a file
     *
     * @param htmlFile html file name
     * @return returns html file content
     */
    private String getHTML(String htmlFile) {
        try {
            AssetManager assetManager = getContext().getAssets();
            InputStream is = assetManager.open("html/faq/" + htmlFile + ".html");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            return new String(buffer);
        } catch (IOException e) {
            Log.e("WebViewFragment", e.getMessage());
        }
        return "NULL";
    }

    /**
     * compose html web page
     *
     * @param htmlBody html body to render
     * @return full html page
     */
    private String composeWebPage(String htmlBody) {
        String theme = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_appTheme", "Light");
        String htmlContent;

        if (Objects.equals(theme, "Dark")) {
            htmlContent = "<html>"
                    + "<head>"
                    + "<style type=\"text/css\">"
                    + "body{font-family: Arial,sans-serif; margin: 10px; color: #e2e2e2; background-color: #212121;}"
                    + "</style>"
                    + "</head>"
                    + "<body>";
        } else {
            htmlContent = "<html>"
                    + "<head>"
                    + "<style type=\"text/css\">"
                    + "body{font-family: Arial,sans-serif; margin: 10px; color: #3c3c3c; background-color: #ededed;}"
                    + "</style>"
                    + "</head>"
                    + "<body>";
        }

        htmlContent += htmlBody + "</body></html>";

        return htmlContent;
    }
}

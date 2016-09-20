package com.gz.android_utils.analytic;

import android.app.Activity;

import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.gz.android_utils.GZApplication;

/**
 * created by Zhao Yue, at 19/9/16 - 11:46 AM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZGoogleAnalytic {

    /**
     * This class provide the fundamental class research summary for 'how to user google analytic'
     * */

    /**
     * Config for campaign: when jumping to google play store, the url carries the 'referrer info' and it will be
     * broadcast to app during the installation process, following the example below:
     * For further detail , refers to https://developers.google.com/analytics/devguides/collection/android/v4/campaigns
     */

    public static final String exampleRedirectURL =
            "https://play.google.com/store/apps/details?id=com.example.application\n" +
                    "&referrer=utm_source%3Dgoogle\n" +
                    "%26utm_medium%3Dcpc\n" +
                    "%26utm_term%3Drunning%252Bshoes\n" +
                    "%26utm_content%3Dlogolink\n" +
                    "%26utm_campaign%3Dspring_sale";

    /**
     * Jump to app by redirect from campaign url, the campaign url can be get from intent which init the app
     */

    public static void openAppByCampain(Activity activity) {
        // Get tracker.
        Tracker t = ((GZApplication) activity.getApplication()).getDefaultTracker();

        // Set screen name.
        t.setScreenName("screen name");

        // In this example, campaign information is set using
        // a url string with Google Analytics campaign parameters.
        // Note: This is for illustrative purposes. In most cases campaign
        //       information would come from an incoming Intent.
        String campaignData = "http://examplepetstore.com/index.html?" +
                "utm_source=email&utm_medium=email_marketing&utm_campaign=summer" +
                "&utm_content=email_variation_1";

        // Campaign data sent with this hit.
        t.send(new HitBuilders.ScreenViewBuilder()
                .setCampaignParamsFromUrl(campaignData)
                .build()
        );
    }

    /**
     * Catch exceptions and errors, GZ provides caught & uncaught exception handling
     */

    public static void trackExceptionAndCrash(Activity activity) {
        Tracker t = ((GZApplication) activity.getApplication()).getDefaultTracker();

        // 1. catch exception and track
        // Build and send exception.
        t.send(new HitBuilders.ExceptionBuilder()
                .setDescription("getExceptionMethod()" + ":" + "getExceptionLocation()")
                .setFatal(true)
                .build());

        // 2. track uncaought exception in app
        Thread.UncaughtExceptionHandler myHandler = new ExceptionReporter(
                t,
                Thread.getDefaultUncaughtExceptionHandler(),
                activity.getApplicationContext());

        // 3. make myHandler the new default uncaught exception handler.
        Thread.setDefaultUncaughtExceptionHandler(myHandler);
    }


    /**
     * Work with dimensions and metrics
     *
     * In the most basic reports, you usually want to get one or more metrics for one dimension.
     * A dimension can be measured in several metrics, but not the other way around.
     * That's why the metrics are the columns, the dimensions are the rows in every report table.
     * A metric is usually something you can count: 10 or 100 Visits, 1,000 or 10,000 Pageviews, 400 Total Events.
     * A dimension is what you are applying the metric to: the Page Title, the Page Path, the Event Label.
     * So for example, the dimension 'Page Title' can be analyzed via the metrics 'Pageviews', 'Unique Pageviews', 'Time on Page', 'Exit Rate' and so on.
     * So a metric is a way to look at a dimension.
     * */

    public static void customizeDimensionAndMetrics() {

    }

    /**
     * Enable automatic activity measurement
     *
     * https://developers.google.com/analytics/devguides/collection/android/v4/screens
     * */
    public static void automaticScreen() {

    }
}

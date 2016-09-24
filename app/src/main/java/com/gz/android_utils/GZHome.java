package com.gz.android_utils;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.gz.android_utils.config.GZConsts;
import com.gz.android_utils.demo.GZDemoListViewItem;
import com.gz.android_utils.ui.listview.GZListView;
import com.gz.android_utils.ui.listview.GZListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class GZHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /* List view items for each feature */
    private List<GZDemoListViewItem> hardWareControlFeatures;
    private List<GZDemoListViewItem> cacheFeatures;


    private GZListViewAdapter adapter = new GZListViewAdapter();


    /* Initialization block */
    {
        hardWareControlFeatures = new ArrayList<>();
        hardWareControlFeatures.add(new GZDemoListViewItem(new GZDemoListViewItem.GZDemoListViewItemData("Battery","Measure the battery status")));
        hardWareControlFeatures.add(new GZDemoListViewItem(new GZDemoListViewItem.GZDemoListViewItemData("System Info","Measure the system information")));

        cacheFeatures = new ArrayList<>();
        cacheFeatures.add(new GZDemoListViewItem(new GZDemoListViewItem.GZDemoListViewItemData("Image Cache","Cache of current user")));
        cacheFeatures.add(new GZDemoListViewItem(new GZDemoListViewItem.GZDemoListViewItemData("Regular Cache","Regular LRU Cache")));
        cacheFeatures.add(new GZDemoListViewItem(new GZDemoListViewItem.GZDemoListViewItemData("User Preference","Preference based on user identity")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GZListView listView = (GZListView)findViewById(R.id.home_list_view);
        adapter.updateData(hardWareControlFeatures);
        listView.setAdapter(this.adapter);


        this.buildFloatAction();
        this.buildDrawerBehaviour();
        this.updateGAEvents();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.demo_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_hardware) {
            adapter.updateData(hardWareControlFeatures);
        } else if (id == R.id.nav_cache) {
            adapter.updateData(cacheFeatures);
        } else if (id == R.id.nav_concurrency) {
            adapter.clearData();
        } else if (id == R.id.nav_manage) {
            adapter.clearData();
        } else if (id == R.id.nav_cross_app) {
            adapter.clearData();
        } else if (id == R.id.nav_send) {
            adapter.clearData();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * View Customization
     */

    private void buildFloatAction() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "For any problem, create a issue on git:SamuelZhaoY/Android-CommonUtils", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void buildDrawerBehaviour() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * GA event update
     */
    private void updateGAEvents() {
        /** Check about local analytic tool */
        Log.i(GZConsts.ApplicationTag, "Home app initialization");
        GZApplication application = (GZApplication) getApplication();
        Tracker tracker = application.getDefaultTracker();
        tracker.setScreenName(this.getLocalClassName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }
}

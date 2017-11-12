package com.example.ckmj.interactivemap;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.Locale;

// Top-level. Includes the Navigation Drawer
            public class MainDrawer extends AppCompatActivity
                    implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener
            {
                public static Bitmap hotspots;
                public static DrawerLayout drawer;
                private Toast mCurrentToast;
                private FragmentManager fragmentManager;
                private Locale locale;
                private Configuration config;
                private Context newContext;
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);

                    SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    String localeX= mSharedPreferences.getString("language_list","en");

                    locale= new Locale(localeX);
                    config= getResources().getConfiguration();
                    config.setLocale(locale);
                    newContext = createConfigurationContext(config);

                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);

                    setContentView(R.layout.activity_main_drawer);

                    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                    drawer.setDrawerListener(toggle);
                    toggle.syncState();


                    BitmapFactory.Options o= new BitmapFactory.Options();
                    o.inScaled=false;
                    hotspots = BitmapFactory.decodeResource(getResources(),R.drawable.vnmapover,o);

                    Fragment fragment = new MainFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.content_main_drawer, fragment).addToBackStack("1")
                            .commit();

                    fragmentManager.executePendingTransactions();


                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(this);

                    Log.d("locale", localeX);


                }


                @Override
                protected void attachBaseContext(Context newBase) {
                    SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase);
                    String localeX= mSharedPreferences.getString("language_list","en");
                    Log.d("locale", localeX);
                    locale= new Locale(localeX);

                    final Resources res = newBase.getResources();
                    final Configuration config = res.getConfiguration();
                    config.setLocale(locale); // getLocale() should return a Locale
                    final Context newContext = newBase.createConfigurationContext(config);
                    super.attachBaseContext(newContext);
                }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() == 0)
        {
            Log.d("fasd", ""+getSupportFragmentManager().getBackStackEntryCount());
            super.onBackPressed();
        }

        else {
            Fragment frag= new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main_drawer,frag).commit();
            navigationView.setCheckedItem(R.id.nav_mapView);
    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings= new Intent(this, AppSettings.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
      int id = item.getItemId();
        fragmentManager= getSupportFragmentManager();

        Fragment fragment = null;

        if (id == R.id.nav_mapView) {
            Log.d("dasd", "Clicked Map");
            fragment= new MainFragment();
        }

        else if (id == R.id.nav_listView) {
          Log.d("dD", "Clicked List");
          fragment= new com.example.ckmj.interactivemap.ListFragment();
     }

        else if (id == R.id.nav_settings) {
        fragment= new SettingsFragment();
        }
        /*
        else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        fragmentManager.beginTransaction().replace(R.id.content_main_drawer, fragment).commit();
        fragmentManager.executePendingTransactions();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

                @Override
                public void onFragmentInteraction(Uri uri) {

                }

            }

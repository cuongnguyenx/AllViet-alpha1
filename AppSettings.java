package com.example.ckmj.interactivemap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Locale;

import static android.R.attr.key;

public class AppSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new thisPreferenceFragment())
                .commit();
        this.setTitle(getString(R.string.settings_title));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

       SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if(key.equals("language_list")) {
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    System.exit(0);
                }
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String localeX= mSharedPreferences.getString("language_list","en");
        Log.d("locale", localeX);
        Locale locale= new Locale(localeX);

        final Resources res = newBase.getResources();
        final Configuration config = res.getConfiguration();
        config.setLocale(locale); // getLocale() should return a Locale
        final Context newContext = newBase.createConfigurationContext(config);
        super.attachBaseContext(newContext);
    }

    public static class thisPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(this,MainDrawer.class);
        startActivity(i);
    }


}




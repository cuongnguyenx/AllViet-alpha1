package com.example.ckmj.interactivemap;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Provides static methods to create Context with other Configuration.
 */
public class ConfigurationWrapper {
    private ConfigurationWrapper() {
    }

    //Creates a Context with updated Configuration.
    public static Context wrapConfiguration(final Context context,
                                            final Configuration config) {
        return context.createConfigurationContext(config);
    }

    // Creates a Context with updated Locale.
    public static Context wrapLocale(final Context context,
                                     final Locale locale) {
        final Resources res = context.getResources();
        final Configuration config = res.getConfiguration();
        config.setLocale(locale);
        return wrapConfiguration(context, config);
    }}
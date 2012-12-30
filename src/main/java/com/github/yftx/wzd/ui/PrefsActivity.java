package com.github.yftx.wzd.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.github.yftx.wzd.R;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-28
 */
public class PrefsActivity extends SherlockPreferenceActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
   	protected void onResume() {
   		super.onResume();

   		// Set up a listener whenever a key changes
   		getPreferenceScreen().getSharedPreferences()
   				.registerOnSharedPreferenceChangeListener(this);
   	}

   	@Override
   	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
   			Preference preference) {
   		return super.onPreferenceTreeClick(preferenceScreen, preference);
   	}

    @Override
   	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
   			String key) {

//   		if (key.equalsIgnoreCase(Preferences.NETWORK_TYPE)) {
//   			HttpClient httpClient = TwitterApplication.mApi.getHttpClient();
//   			String type = sharedPreferences.getString(Preferences.NETWORK_TYPE,
//   					"");
//
//   			if (type.equalsIgnoreCase(getString(R.string.pref_network_type_cmwap))) {
//   				Log.d("LDS", "Set proxy for cmwap mode.");
//   				httpClient.setProxy("10.0.0.172", 80, "http");
//   			} else {
//   				Log.d("LDS", "No proxy.");
//   				httpClient.removeProxy();
//   			}
//   		} else if (key.equalsIgnoreCase(Preferences.UI_FONT_SIZE)) {
//   			MyTextView.setFontSizeChanged(true);
//   		}

   	}
}

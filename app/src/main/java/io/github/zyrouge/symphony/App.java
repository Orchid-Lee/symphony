package io.github.zyrouge.symphony;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import io.github.zyrouge.symphony.services.subsonic.Subsonic;
import io.github.zyrouge.symphony.services.subsonic.SubsonicPreferences;
import io.github.zyrouge.symphony.utils.Preferences;

public class App extends Application {
    private static App instance;
    private static Context context;
    private static Subsonic subsonic;
    private static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String themePref = sharedPreferences.getString(Preferences.THEME, ThemeHelper.DEFAULT_MODE);
//        ThemeHelper.applyTheme(themePref);

        instance = this;
        context = getApplicationContext();
//        Log.d("Lee4", "onCreate: " + context);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        Log.d("getInstance", "getInstance: " + instance);
        return instance;
    }

    public static Context getContext() {
        if (context == null) {
            context = getInstance();
        }

        return context;
    }

    public static Subsonic getSubsonicClientInstance(boolean override) {
        if (subsonic == null || override) {
            subsonic = getSubsonicClient();
        }
        return subsonic;
    }

    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return preferences;
    }

    private static Subsonic getSubsonicClient() {
        //服务器
        String server = Preferences.getServer();
        //用户名
        String username = Preferences.getUser();
        //密码
        String password = Preferences.getPassword();
        //token
        String token = Preferences.getToken();
        //salt
        String salt = Preferences.getSalt();
        Log.d("Sonic:", "SV:" + server + ", US:" + username + ", TK" + token + ", salt:" + salt);
        boolean isLowSecurity = Preferences.isLowScurity();

        SubsonicPreferences preferences = new SubsonicPreferences();
        preferences.setServerUrl(server);
        preferences.setUsername(username);
        preferences.setAuthentication(password, token, salt, isLowSecurity);

        if (preferences.getAuthentication() != null) {
            if (preferences.getAuthentication().getPassword() != null)
                Preferences.setPassword(preferences.getAuthentication().getPassword());
            if (preferences.getAuthentication().getToken() != null)
                Preferences.setToken(preferences.getAuthentication().getToken());
            if (preferences.getAuthentication().getSalt() != null)
                Preferences.setSalt(preferences.getAuthentication().getSalt());
        }

        return new Subsonic(preferences);
    }
}
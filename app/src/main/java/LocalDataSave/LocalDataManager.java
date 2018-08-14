package LocalDataSave;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import DataBase.User;

public class LocalDataManager {

    public static final String PREFERENCES_USER = "PREFERENCES_USER";

    public void saveUserObject(User user, Context context){
        SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("User", json);
        prefsEditor.commit();
    }

    public User getUserObject(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_USER,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("User", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public void saveAfterGetStart(boolean isFirstTime, Context context){
        SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putBoolean("isFirstTime", isFirstTime).apply();
        prefsEditor.commit();
    }

    public boolean isCheckFistTime(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_USER,Context.MODE_PRIVATE);
        Boolean isFirstTime = mPrefs.getBoolean("isFirstTime", false);
        return isFirstTime;
    }

}

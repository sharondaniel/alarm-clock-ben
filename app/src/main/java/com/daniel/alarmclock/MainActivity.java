package com.daniel.alarmclock;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.daniel.alarmclock.activities.AlarmDetailsActivity;
import com.daniel.alarmclock.activities.AlarmsListActivity;
import com.daniel.alarmclock.utils.ContextUtils;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // loading the saved language from the phone memory.
        String prefDB = newBase.getString(R.string.PREF_DB);
        SharedPreferences pref = newBase.getSharedPreferences(prefDB, MODE_PRIVATE); // 0 - for private mode
        String app_language = pref.getString(
                newBase.getString(R.string.PREF_APP_LANG),
                newBase.getString(R.string.LANG_DEFAULT)
        );

        // changing the app locale to the selected language
        Locale localeToSwitchTo = new Locale(app_language);
        ContextWrapper localeUpdatedContext = ContextUtils.updateLocale(newBase, localeToSwitchTo);
        super.attachBaseContext(localeUpdatedContext);
    }

    public void onStartApplication(View view) {
        Intent alarmListIntent = new Intent(this, AlarmsListActivity.class);
        startActivity(alarmListIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idMENUEnglish:
                setLocale(getString(R.string.LANG_ENGLISH));
                break;
            case R.id.idMENUHebrew:
                setLocale(getString(R.string.LANG_HEBREW));
                break;
            default:
                setLocale(getString(R.string.LANG_DEFAULT));
                break;
        }
        return true;
    }

    private void setLocale(String loc) {
        // Saving the language selection for the next time the app starts
        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.PREF_DB), 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(getString(R.string.PREF_APP_LANG), loc);
        editor.commit();

        // Start the same intent again so it will reuse the new language.

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }


//    public void onInsertEvent(View view) {
//        AlarmsDBAdapter dbAdapter = new AlarmsDBAdapter(this);
//        Alarm a = new Alarm(10, 30, "Sharon", false, false, false, false, false, false, true, false);
//        dbAdapter.insert(a.getContentValues());
//
//        Log.i("**Info**", "Test Message");
//    }
//
//    public void onClickEvent(View view) {
//        AlarmsDBAdapter dbAdapter = new AlarmsDBAdapter(this);
//        List<Alarm> alarmsList = dbAdapter.getAlarmsList();
//
//        Log.i("**Info**", "Test Message");
//    }
}
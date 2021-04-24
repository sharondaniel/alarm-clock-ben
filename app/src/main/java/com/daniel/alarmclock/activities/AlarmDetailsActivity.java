package com.daniel.alarmclock.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.daniel.alarmclock.R;
import com.daniel.alarmclock.data.AlarmsDBAdapter;
import com.daniel.alarmclock.models.Alarm;
import com.daniel.alarmclock.models.Time;

public class AlarmDetailsActivity extends AppCompatActivity {


    EditText alarmTimeEdit;
    private boolean doneOnce = false;
    Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_details_activity);

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if(source.length() > 1 && doneOnce == false){
                    source = source.subSequence(source.length()-1, source.length());
                    if(source.charAt(0)  >= '0' && source.charAt(0) <= '2'){
                        doneOnce = true;
                        return source;
                    }else{
                        return "";
                    }
                }


                if (source.length() == 0) {
                    return null;// deleting, keep original editing
                }
                String result = "";
                result += dest.toString().substring(0, dstart);
                result += source.toString().substring(start, end);
                result += dest.toString().substring(dend, dest.length());

                if (result.length() > 5) {
                    return "";// do not allow this edit
                }
                boolean allowEdit = true;
                char c;
                if (result.length() > 0) {
                    c = result.charAt(0);
                    allowEdit &= (c >= '0' && c <= '2');
                }
                if (result.length() > 1) {
                    c = result.charAt(1);
                    if(result.charAt(0) == '0' || result.charAt(0) == '1')
                        allowEdit &= (c >= '0' && c <= '9');
                    else
                        allowEdit &= (c >= '0' && c <= '3');
                }
                if (result.length() > 2) {
                    c = result.charAt(2);
                    allowEdit &= (c == ':');
                }
                if (result.length() > 3) {
                    c = result.charAt(3);
                    allowEdit &= (c >= '0' && c <= '5');
                }
                if (result.length() > 4) {
                    c = result.charAt(4);
                    allowEdit &= (c >= '0' && c <= '9');
                }
                return allowEdit ? null : "";
            }
        };
        alarmTimeEdit = (EditText) findViewById(R.id.idADTime);
        alarmTimeEdit.setFilters(new InputFilter[] { filter });

        Bundle data = getIntent().getExtras();
        alarm = (Alarm) data.getParcelable("ALARM");
        Log.i("ALARM CODE", alarm.getTitle());
    }

    public void onSaveClick(View view) {
        boolean error = false;
        String alarmTitle = ((EditText) findViewById(R.id.idADAlarmTitle)).getText().toString();
        String alarmTime = ((EditText) findViewById(R.id.idADTime)).getText().toString();
        Time time = new Time(alarmTime);
//        EditText alarmHourEdit = (EditText) findViewById(R.id.idADHour);
//        EditText alarmMinuteEdit = (EditText) findViewById(R.id.idADMinute);
//
//        String alarmHourStr = alarmHourEdit.getText().toString();
//        String alarmMinuteStr = alarmMinuteEdit.getText().toString();
//        int alarmHour = Integer.parseInt(alarmHourStr);
//        int alarmMinute = Integer.parseInt(alarmMinuteStr);
//
//        if (alarmHourStr.length() == 0) {
//            error = true;
//            alarmHourEdit.requestFocus();
//            alarmHourEdit.setError("FIELD CANNOT BE EMPTY");
//        }
//
//        if (!error && alarmMinuteStr.length() == 0) {
//            error = true;
//            alarmMinuteEdit.requestFocus();
//            alarmMinuteEdit.setError("FIELD CANNOT BE EMPTY");
//        }
//
//        if (!error && (alarmHour < 0 || alarmHour > 23)) {
//            error = true;
//            alarmHourEdit.requestFocus();
//            alarmHourEdit.setError("HOUR Must be between 0 and 23");
//        }
//
//        if (!error && (alarmMinute < 0 || alarmMinute > 59)) {
//            error = true;
//            alarmMinuteEdit.requestFocus();
//            alarmMinuteEdit.setError("MINUTE Must be between 0 and 59");
//        }
//
        if (!error) {
            boolean active = ((Switch) findViewById(R.id.idADActive)).isChecked();
            boolean repeat = ((CheckBox) findViewById(R.id.idADRepeat)).isChecked();
            boolean sunday = ((CheckBox) findViewById(R.id.idADSunday)).isChecked();
            boolean monday = ((CheckBox) findViewById(R.id.idADMonday)).isChecked();
            boolean tuesday = ((CheckBox) findViewById(R.id.idADTuesday)).isChecked();
            boolean wednesday = ((CheckBox) findViewById(R.id.idADWednesday)).isChecked();
            boolean thursday = ((CheckBox) findViewById(R.id.idADThursday)).isChecked();
            boolean friday = ((CheckBox) findViewById(R.id.idADFriday)).isChecked();
            boolean saturday = ((CheckBox) findViewById(R.id.idADSaturday)).isChecked();
            alarm = new Alarm(
                    time.getHour(),
                    time.getMinutes(),
                    alarmTitle,
                    repeat,
                    sunday,
                    monday,
                    tuesday,
                    wednesday,
                    thursday,
                    friday,
                    saturday
            );

            AlarmsDBAdapter dbAdapter = new AlarmsDBAdapter(this);
            dbAdapter.insert(alarm.getContentValues());

            Intent alarmListIntent = new Intent(this, AlarmsListActivity.class);
            startActivity(alarmListIntent);
        }
    }
}
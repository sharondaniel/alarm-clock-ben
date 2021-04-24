package com.daniel.alarmclock.models;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable {

    private int alarmId;
    private int hour, minute;
    private String title;
    private boolean active;
    private boolean repeat;
    private boolean sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    public Alarm(int hour, int minute, String title, boolean repeat, boolean sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday) {
        this.hour = hour;
        this.minute = minute;
        this.active = true;

        this.repeat = repeat;

        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;

        this.title = title;
    }

    public Alarm(Parcel in) {
        this.title = in.readString();
        this.active = in.readInt() == 1;
        this.hour = in.readInt();
        this.minute = in.readInt();
        this.repeat = in.readInt() == 1;
        this.sunday = in.readInt() == 1;
        this.monday = in.readInt() == 1;
        this.tuesday = in.readInt() == 1;
        this.wednesday = in.readInt() == 1;
        this.thursday = in.readInt() == 1;
        this.friday = in.readInt() == 1;
        this.saturday = in.readInt() == 1;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isActive() {
        return active;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public boolean isSunday() {
        return sunday;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isMonday() {
        return monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

/*
    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(RECURRING, recurring);
        intent.putExtra(MONDAY, monday);
        intent.putExtra(TUESDAY, tuesday);
        intent.putExtra(WEDNESDAY, wednesday);
        intent.putExtra(THURSDAY, thursday);
        intent.putExtra(FRIDAY, friday);
        intent.putExtra(SATURDAY, saturday);
        intent.putExtra(SUNDAY, sunday);

        intent.putExtra(TITLE, title);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        if (!recurring) {
            String toastText = null;
            try {
                toastText = String.format("One Time Alarm %s scheduled for %s at %02d:%02d", title, DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK)), hour, minute, alarmId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );
        } else {
            String toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", title, getRecurringDaysText(), hour, minute, alarmId);
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            final long RUN_DAILY = 24 * 60 * 60 * 1000;
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent
            );
        }

        this.started = true;
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        this.started = false;

        String toastText = String.format("Alarm cancelled for %02d:%02d with id %d", hour, minute, alarmId);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        Log.i("cancel", toastText);
    }
*/

    public String getRecurringDaysText() {
        if (!repeat) {
            return null;
        }

        String days = "";
        if (sunday) {
            days += "1";
        }
        if (monday) {
            days += "2";
        }
        if (tuesday) {
            days += "3";
        }
        if (wednesday) {
            days += "4";
        }
        if (thursday) {
            days += "5";
        }
        if (friday) {
            days += "6";
        }
        if (saturday) {
            days += "7";
        }

        return days;
    }

    public String getTitle() {
        return title;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ContentValues getContentValues() {

        ContentValues cv = new ContentValues();
        cv.put("HOUR", hour);
        cv.put("MINUTE", minute);
        cv.put("TITLE", title);
        cv.put("ACTIVE", active);
        cv.put("REPEAT", repeat);
        cv.put("SUNDAY", sunday);
        cv.put("MONDAY", monday);
        cv.put("TUESDAY", tuesday);
        cv.put("WEDNESDAY", wednesday);
        cv.put("THURSDAY", thursday);
        cv.put("FRIDAY", friday);
        cv.put("SATURDAY", saturday);
        return cv;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.active ? 1 : 0);
        dest.writeInt(this.hour);
        dest.writeInt(this.minute);
        dest.writeInt(this.repeat ? 1 : 0);
        dest.writeInt(this.sunday ? 1 : 0);
        dest.writeInt(this.monday ? 1 : 0);
        dest.writeInt(this.tuesday ? 1 : 0);
        dest.writeInt(this.wednesday ? 1 : 0);
        dest.writeInt(this.thursday ? 1 : 0);
        dest.writeInt(this.friday ? 1 : 0);
        dest.writeInt(this.saturday ? 1 : 0);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

}

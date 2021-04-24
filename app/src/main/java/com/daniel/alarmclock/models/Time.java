package com.daniel.alarmclock.models;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class Time {

    private int hour, minutes;

    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public Time(String time) {
        // 10:12
        this.hour = Integer.parseInt(time.substring(0,2));
        this.minutes = Integer.parseInt(time.substring(3,5));
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setHour(int hour) {
        hour = hour;
    }

    public void setMinutes(int minutes) {
        minutes = minutes;
    }

    public String getStringTime() {
        return hour + ":" + minutes;
    }

    @NonNull
    @Override
    public String toString() {
        return getStringTime();
    }

    public long getAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }
}

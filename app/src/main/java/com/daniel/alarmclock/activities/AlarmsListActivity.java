package com.daniel.alarmclock.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.daniel.alarmclock.R;
import com.daniel.alarmclock.adapters.RecyclerAlarmListAdapter;
import com.daniel.alarmclock.data.AlarmsDBAdapter;
import com.daniel.alarmclock.models.Alarm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AlarmsListActivity extends AppCompatActivity implements RecyclerAlarmListAdapter.ItemClickListener {

    RecyclerAlarmListAdapter adapter;
    List<Alarm> alarmsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_list);

        FloatingActionButton fab = findViewById(R.id.idALPlusButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmDetailsIntent = new Intent(AlarmsListActivity.this, AlarmDetailsActivity.class);
                startActivity(alarmDetailsIntent);
            }
        });

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.idALAlarmsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateAlarmsList();

        adapter = new RecyclerAlarmListAdapter(this, alarmsList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent alarmDetailsIntent = new Intent(AlarmsListActivity.this, AlarmDetailsActivity.class);
        alarmDetailsIntent.putExtra("ALARM", adapter.getItem(position));
        startActivity(alarmDetailsIntent);
        Toast.makeText(this, "You clicked " + adapter.getItem(position).getTitle() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    private void updateAlarmsList() {
        AlarmsDBAdapter dbAdapter = new AlarmsDBAdapter(this);
        alarmsList = dbAdapter.getAlarmsList();
    }

}
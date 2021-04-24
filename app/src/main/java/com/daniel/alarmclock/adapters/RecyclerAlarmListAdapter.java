package com.daniel.alarmclock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daniel.alarmclock.R;
import com.daniel.alarmclock.models.Alarm;
import com.daniel.alarmclock.models.Time;

import java.util.List;

public class RecyclerAlarmListAdapter extends RecyclerView.Adapter<RecyclerAlarmListAdapter.ViewHolder> {


    private List<Alarm> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecyclerAlarmListAdapter(Context context, List<Alarm> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_alarm_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alarm alarm = mData.get(position);
        holder.txtAlarmText.setText(alarm.getTitle());
        Time time = new Time(alarm.getHour(), alarm.getMinute());
        holder.txtAlarmTime.setText(time.toString());
        if( alarm.isActive() ) {
            holder.swAlarmActive.setText(R.string.ral_active);
            holder.swAlarmActive.setChecked(true);

        } else {
            holder.txtAlarmTime.setEnabled(false);
            holder.txtAlarmText.setEnabled(false);
            holder.swAlarmActive.setChecked(false);
            holder.swAlarmActive.setText(R.string.ral_not_active);
        }

        holder.swAlarmActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.txtAlarmTime.setEnabled(isChecked);
                holder.txtAlarmText.setEnabled(isChecked);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtAlarmText;
        TextView txtAlarmTime;
        Switch swAlarmActive;


        ViewHolder(View itemView) {
            super(itemView);
            txtAlarmText = itemView.findViewById(R.id.txtAlarmText);
            txtAlarmTime = itemView.findViewById(R.id.txtAlarmTime);
            swAlarmActive = itemView.findViewById(R.id.swActive);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Alarm getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}

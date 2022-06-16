package com.example.alarmclock.alarm;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarmclock.R;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class alarm extends Fragment {

    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String currentTime;
    String stringMinute;
    String stringHour;
    String stringCHour;
    String stringCMinute;
    String amPm;
    String cAmPm;
    Integer hour, minute;
    String aTime;
    Ringtone chosenr;
    Timer timer = new Timer();


    public alarm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri mCurrentSelectedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                chosenr = RingtoneManager.getRingtone(getActivity(), mCurrentSelectedUri);
            }
            else if(requestCode == 10){
                Uri uri = data.getData();
                chosenr = RingtoneManager.getRingtone(getActivity(),uri);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        @SuppressLint("InflateParams") View root= inflater.inflate(R.layout.fragment_alarm,null);
        TextView  alarmtime = root.findViewById(R.id.alarmtime);
        Button selectedTime = root.findViewById(R.id.timepicker);
        Button setTime = root.findViewById(R.id.set);
        Button cancel = root.findViewById(R.id.cancel);
        Button choosetone = root.findViewById(R.id.choosetone);
        Button customtone = root.findViewById(R.id.customtone);


        selectedTime.setOnClickListener(view -> {
            if(chosenr != null)
                chosenr.stop();

            calendar = Calendar.getInstance();
            currentHour=calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute=calendar.get(Calendar.MINUTE);
            if(currentHour>=12){
                if(currentHour>12)
                    currentHour -= 12;
                cAmPm="PM";
            }else{
                cAmPm="AM";
            }
            if(currentHour<10){
                stringCHour="0";
                stringCHour=stringCHour.concat(Integer.toString(currentHour));
            }else{
                stringCHour=Integer.toString(currentHour);
            }if(currentMinute<10){
                stringCMinute="0";
                stringCMinute=stringCMinute.concat(Integer.toString(currentMinute));
            }else{
                stringCMinute=Integer.toString(currentMinute);
            }
            currentTime=stringCHour+":"+stringCMinute+" "+cAmPm;

            calendar = Calendar.getInstance();
            currentHour=calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute=calendar.get(Calendar.MINUTE);
            timePickerDialog=new TimePickerDialog(getActivity(), (timePicker, hourOfDay, minutes) -> {
                if(hourOfDay >= 12){
                    if(hourOfDay>12)
                        hourOfDay -= 12;
                    amPm="PM";
                }else{
                    amPm="AM";
                }
                if(hourOfDay<10){
                    stringHour="0";
                    stringHour=stringHour.concat(Integer.toString(hourOfDay));
                }else{
                    stringHour=Integer.toString(hourOfDay);
                }
                if(minutes<10){
                    stringMinute="0";
                    stringMinute=stringMinute.concat(Integer.toString(minutes));
                }else{
                    stringMinute=Integer.toString(minutes);
                }
                alarmtime.setText(stringHour+":"+stringMinute+" "+amPm);
            },currentHour,currentMinute,false);

            timePickerDialog.show();
        });


        choosetone.setOnClickListener(view -> {
            if(chosenr != null)
                chosenr.stop();

            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select alarm tone:");
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, true);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, true);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_INCLUDE_DRM,true);
            startActivityForResult(intent,1);
        });

        customtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chosenr != null)
                    chosenr.stop();
                Intent ctone = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(ctone,10);
            }
        });


        setTime.setOnClickListener(view -> {
            if(alarmtime.getText().toString().equals(currentTime)){
                if(chosenr != null){
                    chosenr.play();
                    Toast.makeText(getActivity(),"Alarm Set Successfully :)",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Choose Ringtone for Alarm",Toast.LENGTH_SHORT).show();
                }
            }else if(alarmtime.getText().toString().equals("Alarm : Time")) {
                Toast.makeText(getActivity(),"Select Alarm Time to Set Alarm ;)",Toast.LENGTH_SHORT).show();
            }else{
                if(chosenr != null){
                    Toast.makeText(getActivity(),"Alarm Set Successfully!!",Toast.LENGTH_SHORT).show();
                    chosenr.stop();
                    otheralarm();
                }else{
                    Toast.makeText(getActivity(),"Choose Ringtone for Alarm",Toast.LENGTH_SHORT).show();
                }
            }
        });


        cancel.setOnClickListener(view -> {
            timer.cancel();
            if(chosenr != null){
                if(chosenr.isPlaying()) {
                    chosenr.stop();
                    Toast.makeText(getActivity(), "Alarm Cancelled", Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(getActivity(),"No Alarm Exists to be Cancelled :(",Toast.LENGTH_SHORT).show();
            if (alarmtime.getText().toString().equals("Alarm : Time"))
                Toast.makeText(getActivity(),"No Alarm Exists to be Cancelled :(",Toast.LENGTH_SHORT).show();
            alarmtime.setText("Alarm : Time");
        });
        return root;
    }

    private void otheralarm() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                calendar = Calendar.getInstance();
                currentHour=calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute=calendar.get(Calendar.MINUTE);
                minute=Integer.parseInt(stringMinute);
                hour=Integer.parseInt(stringHour);
                if(currentHour>12)
                    currentHour -= 12;
                if(hour>12)
                    hour -= 12;
                currentTime=Integer.toString(currentHour)+ currentMinute;
                aTime=Integer.toString(hour)+ minute;
                if(currentTime.equals(aTime)){
                    chosenr.play();
                }
            }
        },0,1000);
    }
}
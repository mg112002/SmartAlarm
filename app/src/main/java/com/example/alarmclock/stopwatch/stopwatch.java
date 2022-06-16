package com.example.alarmclock.stopwatch;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.example.alarmclock.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link stopwatch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class stopwatch extends Fragment {

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public stopwatch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment stopwatch.
     */
    // TODO: Rename and change types and number of parameters
    public static stopwatch newInstance(String param1, String param2) {
        stopwatch fragment = new stopwatch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = (ViewGroup) inflater.inflate(R.layout.fragment_stopwatch, null);
        chronometer = root.findViewById(R.id.chronometer);
        Button startbtn = root.findViewById(R.id.startbtn);
        Button pausebtn = root.findViewById(R.id.pausebtn);
        Button resetbtn = root.findViewById(R.id.resetbtn);
        final Ringtone r= RingtoneManager.getRingtone(getActivity(),RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 600000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    r.play();
                    Toast.makeText(getActivity(), "Bing!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                }
            }
        });

        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running) {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                chronometer.stop();

                running=false;
            }
        });
        return root;
    }
}



package com.example.alarmclock.study;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alarmclock.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link study#newInstance} factory method to
 * create an instance of this fragment.
 */
public class study extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public study() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment study.
     */
    // TODO: Rename and change types and number of parameters
    public static study newInstance(String param1, String param2) {
        study fragment = new study();
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
        View root = (ViewGroup) inflater.inflate(R.layout.fragment_study, null);
        TextView htmltxt=root.findViewById(R.id.html);
        htmltxt.setMovementMethod(LinkMovementMethod.getInstance());
        TextView csstxt=root.findViewById(R.id.css);
        csstxt.setMovementMethod(LinkMovementMethod.getInstance());
        TextView pythontxt=root.findViewById(R.id.python);
        pythontxt.setMovementMethod(LinkMovementMethod.getInstance());
        TextView javatxt=root.findViewById(R.id.java);
        javatxt.setMovementMethod(LinkMovementMethod.getInstance());
        TextView exittxt=root.findViewById(R.id.exit);
        exittxt.setMovementMethod(LinkMovementMethod.getInstance());
        return root;
    }
}
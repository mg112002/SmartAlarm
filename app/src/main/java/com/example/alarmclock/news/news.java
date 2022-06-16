package com.example.alarmclock.news;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.alarmclock.R;
import com.example.alarmclock.news.Models.NewsApiResponse;
import com.example.alarmclock.news.Models.NewsHeadlines;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link news#newInstance} factory method to
 * create an instance of this fragment.
 */
public class news extends Fragment{
    ProgressDialog dialog;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    SearchView searchView;
    NewsHeadlines headlines;
    View root;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public news() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment news.
     */
    // TODO: Rename and change types and number of parameters
    public static news newInstance(String param1, String param2) {
        news fragment = new news();
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
        root = inflater.inflate(R.layout.fragment_news, null);
        Button business= root.findViewById(R.id.btn_1);
        Button entertainment= root.findViewById(R.id.btn_2);
        Button general= root.findViewById(R.id.btn_3);
        Button health= root.findViewById(R.id.btn_4);
        Button science= root.findViewById(R.id.btn_5);
        Button sports= root.findViewById(R.id.btn_6);
        Button technology= root.findViewById(R.id.btn_7);

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Fetching news articles of Business");
                dialog.show();
                RequestManager manager= new RequestManager(getActivity());
                manager.getNewsHeadlines(listener,"business",null);
            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Fetching news articles of Entertainment");
                dialog.show();
                RequestManager manager= new RequestManager(getActivity());
                manager.getNewsHeadlines(listener,"entertainment",null);
            }
        });

        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Fetching General news articles");
                dialog.show();
                RequestManager manager= new RequestManager(getActivity());
                manager.getNewsHeadlines(listener,"general",null);
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Fetching news articles of Health");
                dialog.show();
                RequestManager manager= new RequestManager(getActivity());
                manager.getNewsHeadlines(listener,"health",null);
            }
        });

        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Fetching news articles of Science");
                dialog.show();
                RequestManager manager= new RequestManager(getActivity());
                manager.getNewsHeadlines(listener,"science",null);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Fetching news articles of Sports");
                dialog.show();
                RequestManager manager= new RequestManager(getActivity());
                manager.getNewsHeadlines(listener,"sports",null);
            }
        });

        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Fetching news articles of Technology");
                dialog.show();
                RequestManager manager= new RequestManager(getActivity());
                manager.getNewsHeadlines(listener,"technology",null);
            }
        });


        dialog = new ProgressDialog(getContext());

        RequestManager manager = new RequestManager(getContext());
        manager.getNewsHeadlines(listener, "general", null);

        return root;
    }


    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {

        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if (list.isEmpty()) {
                Toast.makeText(getActivity(), "No data found!!!", Toast.LENGTH_SHORT).show();
            }
            else {
                showNews(list);
                dialog.dismiss();
            }
        }
        @Override
        public void onError(String Message) {
            Toast.makeText(getActivity(), "An Error Occurred!!!", Toast.LENGTH_SHORT).show();

        }

        private void showNews(List<NewsHeadlines> list) {
            recyclerView = root.findViewById(R.id.recycler_main);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            adapter = new CustomAdapter(getContext(),list);
            recyclerView.setAdapter(adapter);
        }
    };
}
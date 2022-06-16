package com.example.alarmclock.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import com.example.alarmclock.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link weather#newInstance} factory method to
 * create an instance of this fragment.
 */
public class weather extends Fragment {

    EditText etCity, etCountry;
    TextView tvResult;
    Button btnGet;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "e53301e27efa0b66d05045d91b2742d3";
    DecimalFormat df = new DecimalFormat("#.##");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public weather() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment weather.
     */
    // TODO: Rename and change types and number of parameters
    public static weather newInstance(String param1, String param2) {
        weather fragment = new weather();
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
        View root=(ViewGroup) inflater.inflate(R.layout.fragment_weather,null);

        etCity = root.findViewById(R.id.etCity);
        etCountry = root.findViewById(R.id.etCountry);
        tvResult = root.findViewById(R.id.tvResult);
        btnGet =root.findViewById(R.id.btnGet);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempUrl = "";
                String city = etCity.getText().toString().trim();
                String country = etCountry.getText().toString().trim();
                if(city.equals("")){
                    tvResult.setText("City field can not be empty!");
                }else{
                    if(!country.equals("")){
                        tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
                    }else{
                        tempUrl = url + "?q=" + city + "&appid=" + appid;
                    }
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String output = "";
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                                String description = jsonObjectWeather.getString("description");
                                JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                                double temp = jsonObjectMain.getDouble("temp") - 273.15;
                                double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                                float pressure = jsonObjectMain.getInt("pressure");
                                int humidity = jsonObjectMain.getInt("humidity");
                                JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                                String wind = jsonObjectWind.getString("speed");
                                JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                                String clouds = jsonObjectClouds.getString("all");
                                JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                                String countryName = jsonObjectSys.getString("country");
                                String cityName = jsonResponse.getString("name");
                                tvResult.setTextColor(Color.rgb(68,134,199));
                                output += "Current weather of " + cityName + " (" + countryName + ")"
                                        + "\n Temp: " + df.format(temp) + " °C"
                                        + "\n Feels Like: " + df.format(feelsLike) + " °C"
                                        + "\n Humidity: " + humidity + "%"
                                        + "\n Description: " + description
                                        + "\n Wind Speed: " + wind + "m/s (meters per second)"
                                        + "\n Cloudiness: " + clouds + "%"
                                        + "\n Pressure: " + pressure + " hPa";
                                tvResult.setText(output);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);
                }
            }
        });

        return root;
    }
}
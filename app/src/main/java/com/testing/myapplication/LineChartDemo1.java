package com.testing.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class LineChartDemo1 extends AppCompatActivity {
    ProgressBar progressBar;

    LineChart mpLineChart;
    Button Home;
    Button Temperature;
    Button Motion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_demo1);
        progressBar = findViewById(R.id.progress);
        Home = findViewById(R.id.Home);
        Temperature = findViewById(R.id.btnLineChart1);
        Motion= findViewById(R.id.btnLineChart2);

        mpLineChart = (LineChart) findViewById(R.id.line_chart1);
        LineDataSet lineDataSet1 = new LineDataSet(FetchTimeTemp(),"Data Set 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

        //Home button function
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start ProgressBar first (Set visibility VISIBLE)
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"LineChartTest",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        //End Write and Read data with URL
                    }
                });
            }
        });
        //temperature button function
        Temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start ProgressBar first (Set visibility VISIBLE)
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"LineChartTest",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LineChartDemo1.class);
                        startActivity(intent);
                        finish();
                        //End Write and Read data with URL
                    }
                });
            }
        });

        //Sleep Motion button function
        Motion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start ProgressBar first (Set visibility VISIBLE)
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"LineChartTest",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LineChartDemo2.class);
                        startActivity(intent);
                        finish();
                        //End Write and Read data with URL
                    }
                });
            }
        });

    }
    private ArrayList<Entry> dataValues1()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();

        for (int i = 900000000; i <900004000 ; i++) {
            dataVals.add(new Entry(i,i));
        }
        return  dataVals;

    }
    private ArrayList<Entry> FetchTimeTemp ()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        FetchData fetchData = new FetchData("http://112.14.72.115/FetchData/FetchData.php");
//        FetchData fetchData = new FetchData("http://122.239.216.214/FetchData/FetchData.php");
        if (fetchData.startFetch()) {
            //progressBar.setVisibility(View.VISIBLE);
            if (fetchData.onComplete()) {
                String result = fetchData.getResult();
                try {
                    JSONArray arr = new JSONArray(result);

                    for(int i = 0; i < arr.length(); i++){

                        if (arr.getJSONObject(i).getString("time").equals("null")||
                                arr.getJSONObject(i).getString("temp").equals("null"))
                        {
                            continue;
                        }
                        float time_v = 0f;
                        float temp_v = 0f;
                        time_v = Float.parseFloat(arr.getJSONObject(i).getString("id"));
                        temp_v = Float.parseFloat(arr.getJSONObject(i).getString("temp"));
                        dataVals.add(new Entry(time_v,temp_v));
                    }
                    Toast.makeText(getApplicationContext(),"Fetch Success",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"JSON Error",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //TODO:No Use
//                                    Intent intent = new Intent(getApplicationContext(),TestResult.class);
//                                    startActivity(intent);
//                                    finish();
            } else {
                Toast.makeText(getApplicationContext(),"fetchData.onComplete() Error",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"fetchData.startFetch() Error",Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.GONE);
        return  dataVals;
    }
    private void FetchTest()
    {
        FetchData fetchData = new FetchData("http://122.239.216.61/FetchData/FetchData.php");
        if (fetchData.startFetch()) {
            progressBar.setVisibility(View.VISIBLE);
            if (fetchData.onComplete()) {
                progressBar.setVisibility(View.GONE);
                String result = fetchData.getResult();
                try {
                    JSONArray arr = new JSONArray(result);
                    for(int i = 0; i < arr.length(); i++){
                        float time_v = 0f;
                        if (arr.getJSONObject(i).getString("time").equals("null")||
                        arr.getJSONObject(i).getString("temp").equals("null"))
                        {
                            continue;
                        }
                        time_v = Float.parseFloat(arr.getJSONObject(i).getString("time"));
                        float temp_v = 0f;
                        temp_v = Float.parseFloat(arr.getJSONObject(i).getString("temp"));

                        System.out.println(time_v+" "+temp_v);
                        Toast.makeText(getApplicationContext(),"Fetch Success",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"JSON Error",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //TODO:No Use
//                                    Intent intent = new Intent(getApplicationContext(),TestResult.class);
//                                    startActivity(intent);
//                                    finish();
            } else {
                Toast.makeText(getApplicationContext(),"fetchData.onComplete() Error",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"fetchData.startFetch() Error",Toast.LENGTH_SHORT).show();
        }
    }
}
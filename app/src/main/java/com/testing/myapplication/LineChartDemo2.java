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

public class LineChartDemo2 extends AppCompatActivity {
    ProgressBar progressBar;

    LineChart mpLineChart;
    Button Home;
    Button Temperature;
    Button Motion;

    TextView DeepSleep,REM;
    public Float DeepSleepf=0f,REMf=0f;
    public int DSrate,REMrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_demo2);

        progressBar = findViewById(R.id.progress);
        Home = findViewById(R.id.Home);
        Temperature = findViewById(R.id.btnLineChart1);
        Motion= findViewById(R.id.btnLineChart2);

        //display DeepSleep REM
        DeepSleep=(TextView)findViewById(R.id.DeepSleep);
        REM=(TextView)findViewById(R.id.REM);


        mpLineChart = (LineChart) findViewById(R.id.line_chart2);
        LineDataSet lineDataSet1 = new LineDataSet(FetchMotion(),"Data Set 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

        DSrate = (int)((DeepSleepf/(DeepSleepf+REMf))*100);
        REMrate = (int)((REMf/(DeepSleepf+REMf))*100);
        System.out.println(DSrate);
        System.out.println(REMrate);
        DeepSleep.setText("DeepSleep: "+String.valueOf(DSrate)+"%");
        REM.setText("REM: "+String.valueOf(REMrate)+"%");

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
    private ArrayList<Entry> FetchMotion ()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        FetchData fetchData = new FetchData("http://112.14.72.115/FetchData/FetchData.php");
        // dong zi bro's ip: 122.239.216.214
        if (fetchData.startFetch()) {
            //progressBar.setVisibility(View.VISIBLE);
            if (fetchData.onComplete()) {
                String result = fetchData.getResult();
                try {
                    JSONArray arr = new JSONArray(result);
                    Float DeepLeep,REM;
                    for(int i = 0; i < arr.length(); i++){

                        if (arr.getJSONObject(i).getString("id").equals("null")||arr.getJSONObject(i).getString("accel_x").equals("null"))
                        {
                            continue;
                        }
                        float id_v = 0f;
                        double a_x= 0f,a_y= 0f,a_z= 0f,g_x= 0f,g_y= 0f,g_z = 0f,temp_mean = 0f;
                        float temp_sum = 0f, temp_A = 0f;
                        id_v = Float.parseFloat(arr.getJSONObject(i).getString("id"));
//                        a_x = Float.parseFloat(arr.getJSONObject(i).getString("accel_x"));
//                        a_y = Float.parseFloat(arr.getJSONObject(i).getString("accel_y"));
//                        a_z = Float.parseFloat(arr.getJSONObject(i).getString("accel_z"));
//                        g_x = Float.parseFloat(arr.getJSONObject(i).getString("gyro_x"));
//                        g_y = Float.parseFloat(arr.getJSONObject(i).getString("gyro_y"));
//                        g_z = Float.parseFloat(arr.getJSONObject(i).getString("gyro_z"));
//                        temp_mean = Math.abs((a_x+a_y+a_z+g_x+g_y+g_z)/6);
                        a_x = Double.parseDouble(arr.getJSONObject(i).getString("accel_x"));
                        a_y = Double.parseDouble(arr.getJSONObject(i).getString("accel_y"));
                        a_z = Double.parseDouble(arr.getJSONObject(i).getString("accel_z"));

                        temp_sum = (float)(Math.pow(a_x,2)+ Math.pow(a_y,2)+ Math.pow(a_z,2));
                        temp_A = (float)Math.pow(temp_sum,0.5);
                        //判断是否是深度睡眠 e.g. 实例：合加速度>11。1为深度睡眠
                        if (temp_A>11.1)
                        {
                            DeepSleepf++;
                        }
                        else
                        {
                            REMf++;
                        }
                        dataVals.add(new Entry(id_v,temp_A));
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
}
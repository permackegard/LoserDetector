package looserapp.looserdetector;
//package de.vogella.android.sensor.compass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static SensorManager sensorService;
    private MyCompassView compassView;
    private Sensor sensor;

    private int compassValueButton = 0;
    private int compassValue;
    private boolean hasButtonBeenPressed = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //compassView = new MyCompassView(this);
        //setContentView(compassView);

        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        if (sensor != null) {
            sensorService.registerListener(mySensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");

        } else {
            Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(this, "ORIENTATION Sensor not found",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void startCompass()
    {
        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        if (sensor != null) {
            sensorService.registerListener(mySensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");

        } else {
            Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(this, "ORIENTATION Sensor not found",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch(keycode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:{
                compassValueButton = compassValue;
                hasButtonBeenPressed = true;
                return true;
            }
        }

        return super.onKeyDown(keycode, e);
    }

    private SensorEventListener mySensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // angle between the magnetic north direction
            // 0=North, 90=East, 180=South, 270=West
            //float azimuth = event.values[0];
            compassValue = (int)event.values[0];

            if( ((compassValue < (compassValueButton + 10)) && ( compassValue > (compassValueButton - 10) )) && hasButtonBeenPressed )
            {
               Button p1_button = (Button)findViewById(R.id.button);
               p1_button.setText("Loser Detected!");

               // startActivity(nextScreen);
                //startActivity(new Intent(MainActivity.this , Second.class));

            }
            else
            {
                Button p1_button = (Button)findViewById(R.id.button);
                p1_button.setText("Searching!");
            }

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensor != null) {
            sensorService.unregisterListener(mySensorEventListener);
        }
    }

/*    public void buttonOnClick(View v){
       // Button button = (Button) v;
        //((Button) v).setText("clicked");

        //startCompass();

        compassValueButton = compassValue;

        //startActivity(new Intent(MainActivity.this , Second.class));


*//*        Intent nextScreen = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(nextScreen);*//*
    }*/

}
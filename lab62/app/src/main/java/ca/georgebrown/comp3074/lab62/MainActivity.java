package ca.georgebrown.comp3074.lab62;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    private SensorManager sensorManager;
    private View background;
    private long lastUpdate;
    private boolean colorChange = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        background = findViewById(R.id.background);
        background.setBackgroundColor(Color.GRAY);
        Button btn = findViewById(R.id.btnCompass);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CompassActivity.class);
                startActivity(i);
            }
        });

    }
    public static final float SHAKE_VALUE = 1.6f;

    private void getAccelerometer(SensorEvent event){
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accSqRoot = (x*x + y*y + z*z)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH);

        long time = event.timestamp;

        if(time - lastUpdate <200){
            return;
        }
        Log.d("SHAKE-TEST", ""+accSqRoot);
        if(accSqRoot >= SHAKE_VALUE){
            lastUpdate = time;
            Log.d("SHAKE-TEST","Device shaken");
            if(colorChange){
                background.setBackgroundColor(Color.GREEN);
            }else{
                background.setBackgroundColor(Color.RED);
            }
            colorChange = !colorChange;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer((sensorEvent));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
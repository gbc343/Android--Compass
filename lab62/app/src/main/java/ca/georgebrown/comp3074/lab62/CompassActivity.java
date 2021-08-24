package ca.georgebrown.comp3074.lab62;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class CompassActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    Sensor sensor;

    private MyCompassView compas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compas = new MyCompassView(this);

        setContentView(compas);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float azimuth = sensorEvent.values[0];
            compas.updateData(azimuth);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener((sensorEventListener));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensor != null){
            sensorManager.registerListener(sensorEventListener,
                    sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this,"Orientations sse not found",
                    Toast.LENGTH_LONG).show();
        }
    }
}
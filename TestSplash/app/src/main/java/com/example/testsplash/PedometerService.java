package com.example.testsplash;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PedometerService extends Service implements SensorEventListener {
    SensorManager sm;
    Sensor sensor_step_detector;
    TextView tv_sensor;
    private pedometerLockScreen pls;

    private IBinder mIBinder = (IBinder) new pedometerLockScreen();


    public class LocalBinder extends Binder {
        public PedometerService getService(){
            return PedometerService.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);  //센서 매니저 생성
        sensor_step_detector = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);  //스텝 감지 센서 등록

        pls = (pedometerLockScreen) getApplicationContext();
//        tv_sensor = findViewById(R.id.sensor);
//        tv_sensor.setText("0");  //걸음 수 초기화 및 출력
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {  //센서값이 변할 때
        switch(sensorEvent.sensor.getType()){  //센서 유형이 스텝 감지 센서인 경우 걸음수를 1증가하여 출력력
            case Sensor.TYPE_STEP_DETECTOR:
                tv_sensor.setText(""+ (++pls.steps));
                break;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    //호출할 수 있는 메서드
    public int show(){
        return pls.steps;
    }
}

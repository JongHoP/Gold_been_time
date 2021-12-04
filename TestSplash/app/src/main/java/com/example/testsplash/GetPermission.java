package com.example.testsplash;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class GetPermission extends AppCompatActivity {
    final static int PERMISSION_REQUEST_CODE = 1000;
//    private String TAG = GetPermission.class.getSimpleName();
//    private Context context = GetPermission.this;
//    private Activity activity;
//    public String[] packageNameList;
//    public String p1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedometer);
        permissionCheck();
        checkAccessibilityPermissions();
        setAccessibilityPermissions();


//        PackageManager packageName = activity.getPackageManager();
//        List<PackageInfo> installList = packageName.getInstalledPackages(0);
//
//        for(int i = 0 ; i < installList.size() ; i++ ){
//            Log.d("PackageManager", installList.get(i).packageName);
//            packageNameList[i] = installList.get(i).packageName;
//
//        }
//        Toast.makeText(GetPermission.this, "패키지명 : "
//                + packageNameList[0], Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent(this, LockApp.class);
//        intent.putExtra("p_value", packageNameList);


    }

    private void permissionCheck() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            ArrayList<String> arrayPermission = new ArrayList<String>();

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                arrayPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                arrayPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (arrayPermission.size() > 0) {
                String strArray[] = new String[arrayPermission.size()];
                strArray = arrayPermission.toArray(strArray);
                ActivityCompat.requestPermissions(this, strArray, PERMISSION_REQUEST_CODE);
            } else {
                // Initialize 코드
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length < 1) {
                    Toast.makeText(this, "Failed get permission", Toast.LENGTH_SHORT).show();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    return ;
                }

                for (int i=0; i<grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission is denied : " + permissions[i], Toast.LENGTH_SHORT).show();
                        finish();
                        return ;
                    }
                }

                Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
                // Initialize 코드
            }
            break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean checkAccessibilityPermissions(){
        AccessibilityManager accessibilityManager =
                (AccessibilityManager)getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> list =
                accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);

        Log.d("service_test", "size : " + list.size());
        for(int i = 0; i < list.size(); i++){
            AccessibilityServiceInfo info = list.get(i);
            if(info.getResolveInfo().serviceInfo.packageName.equals(getApplication().getPackageName())){
                return true;
            }
        }
        return false;
    }

    public void setAccessibilityPermissions(){
        AlertDialog.Builder permissionDialog = new AlertDialog.Builder(this);
        permissionDialog.setTitle("접근성 권한 설정");
        permissionDialog.setMessage("앱을 사용하기 위해 접근성 권한이 필요합니다.");
        permissionDialog.setPositiveButton("허용", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                return;
            }
        }).create().show();
    }
}

package com.example.testsplash;

import static android.content.ContentValues.TAG;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;
import com.example.testsplash.GetPermission;

public class LockApp extends AccessibilityService {
    private String packagename1 = "com.instagram.android";
    private String packagename2 = "com.google.android.youtube";
    private String packagename3 = "com.netflix.mediaclient";
    private String MyAppPkgName = "com.example.testsplash";
    private String CallPkgName = "com.samsung.android.dialer";
    private String MsgPkgName = "com.samsung.android.messaging";
    private String pkName;
    private boolean isMyApp = false;


//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Toast.makeText(getApplicationContext(), "Service Created", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            pkName = (String) event.getPackageName();
            if (pkName.equals(event.getPackageName()) && isMyApp == false) {
                Toast.makeText(this.getApplicationContext(), event.getPackageName() + "앱이 거부되었습니다", Toast.LENGTH_LONG);
                if (!pkName.equals(MyAppPkgName) && !pkName.equals(CallPkgName) && !pkName.equals(MsgPkgName)) {
                    gotoHome();
                } else if (pkName.equals(pkName.equals(MyAppPkgName) || pkName.equals(CallPkgName) || pkName.equals(MsgPkgName))) {
                    isMyApp = true;
                }
            }else if (pkName.equals(event.getPackageName()) && isMyApp == true) {
                isMyApp = false;
            }
//            if (packagename1.equals(event.getPackageName())) {
//                Toast.makeText(this.getApplicationContext(), event.getPackageName() + "앱이 거부되었습니다", Toast.LENGTH_LONG);
//                gotoHome();
//            } else if (packagename2.equals(event.getPackageName())) {
//                Toast.makeText(this.getApplicationContext(), event.getPackageName() + "앱이 거부되었습니다", Toast.LENGTH_LONG);
//                gotoHome();
//            } else if (packagename3.equals(event.getPackageName())) {
//                Toast.makeText(this.getApplicationContext(), event.getPackageName() + "앱이 거부되었습니다", Toast.LENGTH_LONG);
//                gotoHome();
//            }
            Log.e(TAG, "Catch Event Package Name : " + event.getPackageName());
            Log.e(TAG, "Catch Event TEXT : " + event.getText());
            Log.e(TAG, "Catch Event ContentDescription : " + event.getContentDescription());
            Log.e(TAG, "Catch Event getSource : " + event.getSource());
            Log.e(TAG, "=========================================================================");
        }
    }
    private void gotoHome(){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                | Intent.FLAG_ACTIVITY_FORWARD_RESULT
                | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
    }

    @Override
    public void onInterrupt() {
    }

//    @Override
//    public boolean onUnbind(Intent intent) {
//
//        return super.onUnbind(intent);
//    }
}

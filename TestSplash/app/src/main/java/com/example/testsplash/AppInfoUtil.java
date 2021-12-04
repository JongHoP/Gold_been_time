package com.example.testsplash;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

public class AppInfoUtil {

    /**
     * app id 가져오기
     * @param context context
     * @return appId
     */
    public static String getAppId(Context context) {
        String appId = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo i = pm.getPackageInfo(context.getPackageName(), 0);
            appId = i.applicationInfo.loadDescription(pm) + "";
        } catch(PackageManager.NameNotFoundException e) { }
        return appId;
    }

    /**
     * app name 가져오기
     * @param context context
     * @return appName
     */
    public static String getAppName(Context context) {
        String appName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo i = pm.getPackageInfo(context.getPackageName(), 0);
            appName = i.applicationInfo.loadLabel(pm) + "";
        } catch(PackageManager.NameNotFoundException e) { }
        return appName;
    }

    /**
     * package name 가져오기
     * @param context
     * @return packageName
     */
    public static String getPackageName(Context context) {
        String packageName = ""; // 패키지명 예시 데이터
        try {
            PackageManager packagemanager = context.getPackageManager();
            ApplicationInfo appinfo = packagemanager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            packageName = packagemanager.getApplicationLabel(appinfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    /**
     * app version 가져오기
     * @param context context
     * @return versionName
     */
    public static String getVersion(Context context) {
        String versionName = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName + "";
        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * app version code 가져오기
     * @param context context
     * @return versionCode
     */
    public static int getVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = i.versionCode;
        } catch(PackageManager.NameNotFoundException e) { }
        return versionCode;
    }

}
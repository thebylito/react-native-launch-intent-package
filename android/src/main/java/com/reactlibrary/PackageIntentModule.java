package com.reactlibrary;


import android.content.Intent;
import android.content.pm.PackageManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

public class PackageIntentModule extends ReactContextBaseJavaModule {
    private static final String E_PACKAGE_NOT_FOUND = "E_PACKAGE_NOT_FOUND";
    private static final String E_ERR_ON_OPENING = "E_ERR_ON_OPENING";

    public PackageIntentModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void startIntent(ReadableMap data, Promise promise) {
        String packageName = data.getString("packageName");
        Intent intent = getIntent(packageName);
        if (intent != null) {
            getReactApplicationContext().startActivity(intent);
            WritableMap map = Arguments.createMap();
            map.putBoolean("packageOpened",true);
            promise.resolve(map);
            return;
        }
        promise.reject(E_ERR_ON_OPENING, "Error on opening package.");
    }

    @ReactMethod
    public void canStartIntent(ReadableMap data, Promise promise) {
        String packageName = data.getString("packageName");
        Intent intent = getIntent(packageName);
        if(intent != null) {
            WritableMap map = Arguments.createMap();
            map.putBoolean("packageExists", true);
            map.putString("packageName", packageName);
            promise.resolve(map);
            return;
        }
        promise.reject(E_PACKAGE_NOT_FOUND, "Package not found");
    }

    private Intent getIntent(String packageName) {
        PackageManager pm = getReactApplicationContext().getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        return intent;
    }

    @Override
    public String getName() {
        return "PackageIntentAndroid";
    }
}

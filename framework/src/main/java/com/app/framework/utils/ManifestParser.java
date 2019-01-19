package com.app.framework.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lowen on 14/08/2017 21:42
 */
public final class ManifestParser<T> {

    private final Context context;
    private String moduleName;

    public ManifestParser(Context context) {
        this.context = context;
    }

    public T parse(String moduleName) {
        this.moduleName = moduleName;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            String packageName = appInfo.metaData.getString(moduleName);
            if (TextUtils.isEmpty(packageName)) {
                throw new RuntimeException("Unable to find metadata name:" + moduleName + ".");
            }
            return parseModule(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse " + moduleName, e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Expected instanceof ," + moduleName + ".");
        }
    }

    private T parseModule(String className) throws ClassCastException {
        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find ConfigModule implementation", e);
        }

        Object module;
        try {
            module = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate " + moduleName + " implementation for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate " + moduleName + " implementation for " + clazz, e);
        }
        return (T) module;
    }
}
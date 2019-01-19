package com.app.framework.common;

import android.app.Activity;

import com.app.framework.rxlifecycle.RxLifecycleManager;

/**
 * Created by lowen on 14/08/2017 21:42
 */
public interface AppManager {

    /**
     * 返回RxLifecycleManager实例<br/>
     * 为了保证对象唯一性和使用正确性，请在整个APP生命周期中一直使用这个实例
     *
     * @return
     */
    RxLifecycleManager rxLifecycleManager();

    /**
     * 删除对应Class的所有Activity
     *
     * @param activityClass Activity Class
     */
    void killActivity(Class<? extends Activity> activityClass);

    /**
     * 检测Activity对象是否已经Finish
     *
     * @param activity
     * @return Finish 返回false，否则返回true
     */
    boolean activityInstanceIsLive(Activity activity);

    /**
     * 检测Activity中是否有对应Class的Activity实例
     *
     * @param activityClass Activity Class
     * @return
     */
    boolean activityClassIsLive(Class<? extends Activity> activityClass);

    /**
     * 关闭所有Activity
     */
    void killAll();

    /**
     * 退出APP
     */
    void appExit();
}

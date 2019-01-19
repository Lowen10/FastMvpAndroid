package com.app.framework.mvp.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by lowen on 29/08/2017.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface AppScope {
}

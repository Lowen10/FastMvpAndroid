package com.app.framework.configuration;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.victoralbertos.jolyglot.JolyglotGenerics;
import io.victoralbertos.jolyglot.Types;

/**
 * Created by lowen on 14/08/2017 21:42
 */

public class GenericGsonSpeaker implements JolyglotGenerics {

    private final Gson gson;

    public GenericGsonSpeaker(Gson gson) {
        this.gson = gson;
    }

    public GenericGsonSpeaker() {
        this.gson = new Gson();
    }

    @Override
    public String toJson(Object src) {
        return gson.toJson(src);
    }

    @Override
    public String toJson(Object src, Type typeOfSrc) {
        return gson.toJson(src, typeOfSrc);
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) throws RuntimeException {
        Type genType = classOfT.getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return gson.fromJson(json, params[0]);
    }

    @Override
    public <T> T fromJson(String json, Type typeOfT) throws RuntimeException {
        Type[] params = ((ParameterizedType) typeOfT).getActualTypeArguments();
        return gson.fromJson(json, params[0]);
    }

    @Override
    public <T> T fromJson(File file, Class<T> classOfT) throws RuntimeException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            Type genType = classOfT.getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            T object = gson.fromJson(reader, params[0]);
            reader.close();
            return object;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException i) {
                }
            }
        }
    }

    @Override
    public <T> T fromJson(File file, Type typeOfT) throws RuntimeException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            Type[] params = ((ParameterizedType) typeOfT).getActualTypeArguments();
            T object = gson.fromJson(reader, params[0]);
            reader.close();
            return object;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException i) {
                }
            }
        }
    }

    @Override
    public GenericArrayType arrayOf(Type componentType) {
        return Types.arrayOf(componentType);
    }

    @Override
    public ParameterizedType newParameterizedType(Type rawType, Type... typeArguments) {
        return Types.newParameterizedType(rawType, typeArguments);
    }
}
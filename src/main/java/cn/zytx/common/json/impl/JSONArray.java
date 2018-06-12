package cn.zytx.common.json.impl;

import cn.zytx.common.json.Json;
import cn.zytx.common.json.JsonArray;
import cn.zytx.common.json.JsonObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author xiongshiyan at 2018/6/11
 */
public class JSONArray extends BaseJson<JSONArray> implements JsonArray {
    private org.json.JSONArray jsonArray;

    public JSONArray(org.json.JSONArray jsonArray){
        this.jsonArray = jsonArray;
    }
    public JSONArray(){
        this.jsonArray = new org.json.JSONArray();
    }
    public JSONArray(String arrayString){
        this.jsonArray = new org.json.JSONArray(arrayString);
    }

    @Override
    public org.json.JSONArray unwrap() {
        return jsonArray;
    }

    @Override
    public JsonArray parse(String arrayString) {
        jsonArray = new org.json.JSONArray(arrayString);
        return this;
        //return new JSONArray(arrayString);
    }

    @Override
    public int size() {
        return jsonArray.length();
    }

    @Override
    public Object get(int index) {
        assertIndex(index , size());
        Object opt = jsonArray.opt(index);
        return checkNullValue(index,opt);
    }

    @Override
    public String getString(int index) {
        assertIndex(index , size());
        return jsonArray.optString(index);
    }

    @Override
    public Boolean getBoolean(int index) {
        assertIndex(index , size());
        return jsonArray.optBoolean(index);
    }

    @Override
    public Integer getInteger(int index) {
        assertIndex(index , size());
        return jsonArray.optInt(index);
    }

    @Override
    public Long getLong(int index) {
        assertIndex(index , size());
        return jsonArray.optLong(index);
    }

    @Override
    public Double getDouble(int index) {
        assertIndex(index , size());
        return jsonArray.optDouble(index);
    }

    @Override
    public Float getFloat(int index) {
        assertIndex(index , size());
        return jsonArray.optFloat(index);
    }

    @Override
    public BigInteger getBigInteger(int index) {
        assertIndex(index , size());
        return jsonArray.optBigInteger(index , new BigInteger("0"));
    }

    @Override
    public BigDecimal getBigDecimal(int index) {
        assertIndex(index , size());
        return jsonArray.optBigDecimal(index , new BigDecimal("0"));
    }

    @Override
    public JsonObject getJsonObject(int index) {
        assertIndex(index , size());
        Object opt = jsonArray.opt(index);
        if(opt instanceof org.json.JSONObject){
            return new JSONObject((org.json.JSONObject)opt);
        }
        return (JsonObject) opt;
    }

    @Override
    public JsonArray getJsonArray(int index) {
        assertIndex(index , size());
        Object opt = jsonArray.opt(index);
        if(opt instanceof org.json.JSONArray){
            return new JSONArray((org.json.JSONArray)opt);
        }
        return (JsonArray) opt;
    }

    @Override
    public JsonArray remove(int index) {
        assertIndex(index , size());
        Object remove = jsonArray.remove(index);
        return this;
    }

    @Override
    public JsonArray clear() {
        int length = jsonArray.length();
        for(int i=0; i<length; i++){
            jsonArray.remove(i);
        }
        return this;
    }

    @Override
    public JsonArray put(Object o) {
        jsonArray.put(o);
        return this;
    }

    @Override
    public JsonArray put(int index, Object o) {
        jsonArray.put(index , o);
        return this;
    }

    @Override
    public JsonArray putAll(Collection<?> os) {
        os.forEach(v->jsonArray.put(v));
        return this;
    }

    @Override
    public String toString() {
        //需要针对JsonObject/JsonArray处理
        Map<Integer , Json> map = new HashMap<>();
        int size = size();
        for (int i = 0; i < size; i++) {
            Object o = jsonArray.get(i);
            if(o instanceof JsonObject || o instanceof JsonArray){
                map.put(i , (Json) o);
            }
        }
        map.forEach((k,v)->jsonArray.put(k , v.unwrap()));

        return jsonArray.toString();
    }

    @Override
    public int hashCode() {
        return jsonArray.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return jsonArray.equals(obj);
    }
}

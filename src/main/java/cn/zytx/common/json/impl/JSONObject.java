package cn.zytx.common.json.impl;

import cn.zytx.common.json.Json;
import cn.zytx.common.json.JsonArray;
import cn.zytx.common.json.JsonException;
import cn.zytx.common.json.JsonObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xiongshiyan at 2018/6/10
 */
public class JSONObject extends BaseJson<JSONObject> implements JsonObject {

    private org.json.JSONObject jsonObject;

    public JSONObject(org.json.JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }
    public JSONObject(Map<String , Object> map){
        this.jsonObject = new org.json.JSONObject(map);
    }
    public JSONObject(){
        this.jsonObject = new org.json.JSONObject();
    }
    public JSONObject(String jsonString){
        this.jsonObject = new org.json.JSONObject(jsonString);
    }

    @Override
    public org.json.JSONObject unwrap() {
        return jsonObject;
    }

    @Override
    public Object get(String key) {
        assertKey(key);
        return checkNullValue(key , jsonObject.opt(key));
    }

    @Override
    public Object get(String key, Object defaultObject) {
        assertKey(key);
        Object temp = jsonObject.opt(key);
        return null == temp ? defaultObject : temp;
    }

    @Override
    public JsonObject getJsonObject(String key) {
        assertKey(key);
        //这里不能使用getJSONObject，因为每一种Json实现不一样，给出的JsonObject类型是不一致的。
        //这里就是各种JsonObject不能混用的原因
        Object temp = this.jsonObject.opt(key);
        Object t = checkNullValue(key, temp);

        if(t instanceof org.json.JSONObject){
            return new JSONObject((org.json.JSONObject) t);
        }
        if(t instanceof Map){
            return new JSONObject((Map<String, Object>) t);
        }
        return (JsonObject) t;
    }

    @Override
    public JsonArray getJsonArray(String key) {
        assertKey(key);
        //这里不能使用getJSONObject，因为每一种Json实现不一样，给出的JsonObject类型是不一致的。
        //这里就是各种JsonObject不能混用的原因
        Object temp = this.jsonObject.opt(key);
        Object t = checkNullValue(key, temp);

        if(t instanceof org.json.JSONArray){
            return new JSONArray((org.json.JSONArray)t);
        }
        if(t instanceof List){
            return new JSONArray((List)t);
        }
        return (JsonArray) t;
    }

    @Override
    public String getString(String key) {
        return getString(key , null);
    }

    @Override
    public String getString(String key, String defaultValue) {
        assertKey(key);
        boolean has = jsonObject.has(key);
        if(!has){
            return checkNullValue(key , defaultValue);
        }
        String temp;
        if(isTolerant()){
            temp = ValueCompatible.compatibleValue(this.jsonObject.get(key) , String.class);
        }else {
            temp = this.jsonObject.getString(key);
        }
        return temp;
    }

    @Override
    public Boolean getBoolean(String key) {
        return getBoolean(key , null);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        assertKey(key);
        boolean has = jsonObject.has(key);
        if(!has){
            return checkNullValue(key , defaultValue);
        }
        Boolean temp;
        if(isTolerant()){
            temp = ValueCompatible.compatibleValue(this.jsonObject.get(key) , Boolean.class);
        }else {
            temp = this.jsonObject.getBoolean(key);
        }
        return temp;
    }

    @Override
    public Integer getInteger(String key) {
        return getInteger(key , null);
    }

    @Override
    public Integer getInteger(String key, Integer defaultValue) {
        assertKey(key);
        boolean has = jsonObject.has(key);
        if(!has){
            return checkNullValue(key , defaultValue);
        }
        Integer temp;
        if(isTolerant()){
            temp = ValueCompatible.compatibleValue(this.jsonObject.get(key) , Integer.class);
        }else {
            temp = this.jsonObject.getInt(key);
        }
        return temp;
    }

    @Override
    public Long getLong(String key) {
        return getLong(key , null);
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        assertKey(key);
        boolean has = jsonObject.has(key);
        if(!has){
            return checkNullValue(key , defaultValue);
        }
        Long temp;
        if(isTolerant()){
            temp = ValueCompatible.compatibleValue(this.jsonObject.get(key) , Long.class);
        }else {
            temp = this.jsonObject.getLong(key);
        }
        return temp;
    }

    @Override
    public Float getFloat(String key) {
        return getFloat(key , null);
    }

    @Override
    public Float getFloat(String key, Float defaultValue) {
        assertKey(key);
        boolean has = jsonObject.has(key);
        if(!has){
            return checkNullValue(key , defaultValue);
        }
        Float temp;
        if(isTolerant()){
            temp = ValueCompatible.compatibleValue(this.jsonObject.get(key) , Float.class);
        }else {
            temp = this.jsonObject.getFloat(key);
        }
        return temp;
    }

    @Override
    public Double getDouble(String key) {
        return getDouble(key , null);
    }

    @Override
    public Double getDouble(String key, Double defaultValue) {
        assertKey(key);
        boolean has = jsonObject.has(key);
        if(!has){
            return checkNullValue(key , defaultValue);
        }
        Double temp;
        if(isTolerant()){
            temp = ValueCompatible.compatibleValue(this.jsonObject.get(key) , Double.class);
        }else {
            temp = this.jsonObject.getDouble(key);
        }
        return temp;
    }

    @Override
    public BigInteger getBigInteger(String key) {
        return getBigInteger(key , null);
    }

    @Override
    public BigInteger getBigInteger(String key, BigInteger defaultValue) {
        assertKey(key);
        boolean has = jsonObject.has(key);
        if(!has){
            return checkNullValue(key , defaultValue);
        }
        BigInteger temp;
        if(isTolerant()){
            temp = ValueCompatible.compatibleValue(this.jsonObject.get(key) , BigInteger.class);
        }else {
            temp = this.jsonObject.getBigInteger(key);
        }
        return temp;
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key , null);
    }

    @Override
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        assertKey(key);
        boolean has = jsonObject.has(key);
        if(!has){
            return checkNullValue(key , defaultValue);
        }
        BigDecimal temp;
        if(isTolerant()){
            temp = ValueCompatible.compatibleValue(this.jsonObject.get(key) , BigDecimal.class);
        }else {
            temp = this.jsonObject.getBigDecimal(key);
        }
        return temp;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        assertKey(key);
        boolean has = jsonObject.has(key);
        if(!has){
            return checkNullValue(key , null);
        }
        T temp = ValueCompatible.compatibleValue(this.jsonObject.get(key) , clazz);
        return checkNullValue(key, temp);
    }

    @Override
    public Set<String> keySet() {
        return jsonObject.keySet();
    }

    @Override
    public int size() {
        return jsonObject.length();
    }

    @Override
    public boolean isEmpty() {
        return jsonObject.length() == 0;
    }

    @Override
    public boolean containsKey(String key) {
        return jsonObject.has(key);
    }

    @Override
    public boolean containsValue(Object value) {
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            Object o = jsonObject.get(key);
            if(null == o){
                continue;
            }
            if(o.equals(value)){
                return true;
            }
        }
        return false;
    }

    @Override
    public JsonObject put(String key, Object value) {
        jsonObject.put(key, value);
        return this;
    }

    @Override
    public JsonObject putAll(Map<? extends String, ?> m) {
        m.forEach(jsonObject::put);
        return this;
    }

    @Override
    public void clear() {
        Set<String> keySet = jsonObject.keySet();
        for (Object key : keySet.toArray()) {
            jsonObject.remove(key.toString());
        }
    }

    @Override
    public Object remove(String key) {
        return jsonObject.remove(key);
    }

    @Override
    public JsonObject parse(String jsonString) {
        jsonObject = new org.json.JSONObject(jsonString);
        return this;
        //return new JSONObject(jsonString);
    }

    @Override
    public JsonObject fromMap(Map<String, Object> map) {
        this.jsonObject = new org.json.JSONObject(map);
        return new JSONObject(map);
    }

    @Override
    public String serialize(Object javaBean) {
        throw new JsonException(new UnsupportedOperationException());
    }

    @Override
    public <T> T deserialize(String jsonString, Class<T> clazz) {
        throw new JsonException(new UnsupportedOperationException());
    }

    @Override
    public String toString() {
        //需要针对JsonObject/JsonArray处理
        Map<String , Json> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            Object o = jsonObject.get(key);
            if(o instanceof JsonObject || o instanceof JsonArray){
                map.put(key , (Json) o);
            }
        }
        map.forEach((k , v)-> jsonObject.put(k , v.unwrap()));

        return jsonObject.toString();
    }

    @Override
    public int hashCode() {
        return jsonObject.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return jsonObject.equals(obj);
    }

}

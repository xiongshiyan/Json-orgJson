package cn.zytx.common.json.impl;

import cn.zytx.common.json.JsonException;

/**
 * @author xiongshiyan at 2018/6/11
 */
public abstract class JsonBase {
    private boolean isStrict;

    public boolean isStrict() {
        return isStrict;
    }

    public void setStrict(boolean strict) {
        isStrict = strict;
    }

    protected void assertKey(String key){
        if(null == key){
            throw new IllegalArgumentException("key 不能为空");
        }
    }
    protected  <T> T checkNullValue(String key , T o) {
        if(null == o){
            if(isStrict()){
                throw new JsonException("不存在key->" + key);
            }else {
                return null;
            }
        }
        return o;
    }
    protected  <T> T checkNullValue(int index , T o) {
        if(null == o){
            if(isStrict()){
                throw new JsonException("不存在->[ " + index + " ]");
            }else {
                return null;
            }
        }
        return o;
    }

    protected void assertIndex(int index , int size){
        if(index< 0 || index >= size){
            throw new JsonException(new IllegalArgumentException("index must between 0 and " + size));
        }
    }
}

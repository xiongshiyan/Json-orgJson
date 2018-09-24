package top.jfunc.json.util;

import java.lang.reflect.Field;
import java.util.*;

import static top.jfunc.json.impl.ValueCompatible.compatibleValue;

/**
 * 将查询结果 map 封装成对应的javaBean，支持级联 ，但是属性不能重复
 * 对应的javaBean的属性名必须以小驼峰形式命名，否则无法填充数据
 * @author zenghl
 * @author xiongshiyan
 */
public class Map2Bean {

    private Map2Bean(){
    }

    /**
     * 将 map 数据封装成javaBean
     * @param map Map类型数据
     * @param clazz 需要转换的JavaBean
     * @param <T> 泛型
     * @return JavaBean
     */
    public static  <T> T convert(Map<String,Object> map,Class<T> clazz){
        try {
            final T instance = clazz.newInstance();
            //Field[] fields = clazz.getDeclaredFields();
            List<Field> fields = new ArrayList<>();
            parseAllFields(clazz , fields);

            for (Field field : fields) {
                String fieldName = field.getName();
                Class<?> type = field.getType();
                if(!canSetValueDirectly(type)){
                    setValue(instance, field, convert(map, type));
                }else {
                    Object value = map.get(fieldName);
                    if(null == value){
                        //null没得必要设置
                        continue;
                    }
                    setValue(instance, field, value);
                    /*map.forEach((k,v)->{
                        String columnToField = StrKit._2Camel(k);
                        if(fieldName.equals(columnToField)) {
                            ObjectKit.setValue(instance, field, v);
                        }
                    });*/
                }
            }

            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 递归获取某个类的所有的属性
     * getDeclaredFields 获取某个类的所有的字段，包括私有的，但是不包括父类的
     * getFields 获得某个类的所有的公共（public）的字段，包括父类中的字段
     */
    private static void parseAllFields(Class<?> clazz , List<Field> list){
        if(clazz != Object.class){
            Field[] fields = clazz.getDeclaredFields();
            list.addAll(Arrays.asList(fields));
            parseAllFields(clazz.getSuperclass() , list);
        }
    }

    public static boolean canSetValueDirectly(Class<?> clazz){
        return clazz.isPrimitive() // 基本类型
                || clazz == String.class  //String
                || clazz == Character.class //Character
                || clazz == Boolean.class  //Boolean
                || Number.class.isAssignableFrom(clazz) //Double Float Long Integer Short Byte BigInteger BigDecimal
                || java.util.Date.class.isAssignableFrom(clazz) // java.sql.Date,Time,TimeStamp
                ;
    }

    public static <T> void setValue(T instance, Field field , Object v) {
        field.setAccessible(true);
        try {
            v = compatibleValue(v , field.getType());
            field.set(instance,v);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

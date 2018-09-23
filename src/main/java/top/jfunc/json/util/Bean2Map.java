package top.jfunc.json.util;

import org.json.JSONObject;
import top.jfunc.json.annotation.JsonField;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Bean2Map {
    /**
     * @see JSONObject#populateMap(Object)
     * JavaBean转换为map
     * @param javaBean Javabean
     * @param nullHold null值是否保留
     * @return Map<String ,Object>
     */
    public static Map<String ,Object> convert(Object javaBean , boolean nullHold , String... ignoreFields) {
        Class<?> klass = javaBean.getClass();

        boolean includeSuperClass = klass.getClassLoader() != null;

        Method[] methods = includeSuperClass ? klass.getMethods() : klass
                .getDeclaredMethods();
        if (null == methods || 0 == methods.length) {
            return new HashMap<>(0);
        }

        Map<String, Object> map = new HashMap<>(methods.length);

        for (final Method method : methods) {
            //添加JsonField判断
            JsonField annotation = method.getAnnotation(JsonField.class);
            //不需要序列化
            if(null != annotation && !annotation.serialize()){
                continue;
            }

            final int modifiers = method.getModifiers();
            if (!Modifier.isPublic(modifiers) || Modifier.isStatic(modifiers) ||
                    method.getParameterTypes().length != 0 || method.isBridge() || method.getReturnType() == Void.TYPE) {
                continue;
            }
            final String name = method.getName();
            String key;
            if (name.startsWith("get")) {
                if ("getClass".equals(name) || "getDeclaringClass".equals(name)) {
                    continue;
                }
                key = name.substring(3);
            } else if (name.startsWith("is")) {
                key = name.substring(2);
            } else {
                continue;
            }
            if (key.length() > 0
                    && Character.isUpperCase(key.charAt(0))) {
                if (key.length() == 1) {
                    key = key.toLowerCase(Locale.ROOT);
                } else if (!Character.isUpperCase(key.charAt(1))) {
                    key = key.substring(0, 1).toLowerCase(Locale.ROOT)
                            + key.substring(1);
                }


                if(shouldIgnore(ignoreFields , key)){
                    continue;
                }


                //根据JsonField修改field的名字
                if(null != annotation && !"".equals(annotation.value())){
                    key = annotation.value();
                }

                try {
                    final Object result = method.invoke(javaBean);
                    if (nullHold || result != null) {
                        map.put(key, JSONObject.wrap(result));
                        // we don't use the result anywhere outside of wrap
                        // if it's a resource we should be sure to close it after calling toString
                        if (result instanceof Closeable) {
                            try {
                                ((Closeable) result).close();
                            } catch (IOException ignore) {
                            }
                        }
                    }
                } catch (Exception ignore) {
                }
            }
        }
        return map;
    }

    private static boolean shouldIgnore(String[] ignoreFields , String fieldName){
        if(ignoreFields == null || ignoreFields.length == 0){
            return false;
        }

        for (int i = 0; i < ignoreFields.length; i++) {
            if(fieldName.equals(ignoreFields[i])){
                return true;
            }
        }
        return false;
    }
}

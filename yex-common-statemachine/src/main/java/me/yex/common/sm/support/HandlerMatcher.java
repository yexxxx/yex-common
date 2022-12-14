package me.yex.common.sm.support;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HandlerMatcher {

    private LazyMap<Class, Map<String, Object>> handlerPool = new LazyMap<Class, Map<String, Object>>() {
        protected Map<String, Object> load(Class key) {
            try {
                return HandlerMatcher.this.getHandlers(key);
            } catch (Exception var3) {
                throw new RuntimeException(var3);
            }
        }
    };
    @Resource
    private ApplicationContext applicationContext;

    public HandlerMatcher() {
    }

    public HandlerMatcher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public <T> T match(Class<T> targetClass, String key) {
        return this.match(targetClass, key, null);
    }

    public <T> T match(Class<T> targetClass, String key, T defaultBean) {
        Map<String, Object> map = this.handlerPool.get(targetClass);
        if (MapUtil.isEmpty(map)) {
            return null;
        } else {
            Object result = map.get(key);
            return (T) result;
        }
    }

    private <T> Map<String, T> getHandlers(Class<T> contract) {
        HashMap handlerMap = Maps.newHashMap();
        Map beanMap = this.applicationContext.getBeansOfType(contract);
        Iterator beanIt = beanMap.entrySet().iterator();
        while (beanIt.hasNext()) {
            Entry entry = (Entry) beanIt.next();
            Object handler = entry.getValue();
            handlerMap.put(entry.getKey(), handler);
        }

        return handlerMap;
    }
}
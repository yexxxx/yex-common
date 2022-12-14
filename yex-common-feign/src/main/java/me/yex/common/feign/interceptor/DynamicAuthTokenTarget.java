package me.yex.common.feign.interceptor;

import feign.Request;
import feign.RequestTemplate;
import feign.Target;
import feign.auth.BasicAuthRequestInterceptor;

import static feign.Util.checkNotNull;
import static feign.Util.emptyToNull;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.feign.target
 */
public class DynamicAuthTokenTarget<T> implements Target<T> {

    private final Class<T> type;
    private final String name;
    private final String url;

    public DynamicAuthTokenTarget(Class<T> type, String url) {
        this(type, url, url);
    }

    public DynamicAuthTokenTarget(Class<T> type, String name, String url) {
        this.type = checkNotNull(type, "type");
        this.name = checkNotNull(emptyToNull(name), "name");
        this.url = checkNotNull(emptyToNull(url), "url");
    }


    @Override
    public Request apply(RequestTemplate input) {
        return null;
    }

    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String url() {
        return url;
    }


}

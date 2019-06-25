package com.htkj.check.g6.workshop;

/**
 * 元消息
 *
 * @author jineral
 * @param <T>
 * @date 2018-12-13
 */
public final class MetaMsg<T> {

    int order;
    T data;

    // 常用 order
    public static final int DOWN_LNGLAT = 20101;
    public static final int DOWN_LOGIN = 20102;
    public static final int DOWN_LOGOUT = 20103;

    // 国六 order
    public static final int DOWN_G6_OBD = 20201;
    public static final int DOWN_G6_ENG = 20202;

    public MetaMsg() {
    }

    public MetaMsg(int order) {
        this.order = order;
    }

    public MetaMsg(int order, T data) {
        this.order = order;
        this.data = data;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

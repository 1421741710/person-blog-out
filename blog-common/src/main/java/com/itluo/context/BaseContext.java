package com.itluo.context;

/**
 * 获取id
 * @author Administrator
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static ThreadLocal<Long> threadLocalRole = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static void setCurrentId(Long id,Long role) {
        threadLocal.set(id);
        threadLocalRole.set(role);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static Long getCurrentRole() {
        return threadLocalRole.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }
    public static void removeCurrentRole() {
        threadLocalRole.remove();
    }
}

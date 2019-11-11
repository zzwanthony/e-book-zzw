package com.zzw.monitor;


import java.util.Observable;

/**
 *
 * 被监控对象需要实现该接口才能被监控
 *
 * @author 11723
 */
public abstract class BeMonitorObj extends Observable {

    /**
     * 将被检测对象加入到一个监控对象中
     * @param monitor 监视器
     * @return 返回添加是否成功
     * */
    protected void addMonitor(Monitor monitor) {
        monitor.addBeMonitorObj(this);
    }


    /**
     * 设置被监控对象是否开始执行任务
     * */
    protected void start() {
        setChanged();
        notifyObservers(true);
    }

    /**
     * 被监控对象需要执行的任务
     * */
    public abstract void run();


    /**
     * 断开与监控方的联系
     * */
    public abstract void close();

}

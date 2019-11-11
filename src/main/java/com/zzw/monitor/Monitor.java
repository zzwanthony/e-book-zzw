package com.zzw.monitor;

import java.util.Observer;

/**
 * @author 11723
 *
 * 监控对象接口
 * 需要作为监控方时需要实现该接口
 *
 * */
public interface Monitor extends Observer{

    /**
     * 添加一个被监控对象到监控器中
     * @param obj 被监控的对象
     * @return 返回添加是否成功
     * */
    boolean addBeMonitorObj(BeMonitorObj obj);
}

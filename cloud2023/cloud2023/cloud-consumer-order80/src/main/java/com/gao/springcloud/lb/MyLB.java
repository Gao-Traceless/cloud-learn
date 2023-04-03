package com.gao.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer{

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    //获取访问次数
    public final int getAndIncrement(){
        int current;
        int next;
        //第一个参数是期望值，第二个参数是修改值是
        do{
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        }while(!this.atomicInteger.compareAndSet(current,next));
        System.out.println("*********第几次访问，次数next: " + next);
        return next;
    }

    //负载均衡算法：rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标，每次服务重启后rest接口计数从1开始
    //得到机器的列表
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        //对次数进行取余    //得到服务器的下标位置
        int index = getAndIncrement() % serviceInstances.size();

        return serviceInstances.get(index);
    }


}

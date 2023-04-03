package com.gao.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {

    //获取活着的服务总数
    ServiceInstance instances(List<ServiceInstance> serviceInstances);

    //

}

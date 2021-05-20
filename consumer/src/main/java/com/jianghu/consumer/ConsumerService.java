package com.jianghu.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jianghu.api.ProviderService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: OF3848
 * @create: 2021-05-14 11:00
 */
@Service
public class ConsumerService {

    /**
     * check：启动时是否检查，false不检查，调用时提供者不存在报错
     * retries：重试次数，不算第一次调用后，重试的次数
     * loadbalance：负载均衡策略，roundrobin随机
     * version：多版本，*不区分版本
     */
    @Reference(check = false, retries = 4, loadbalance = "roundrobin" , version = "*")
    private ProviderService providerService;

    public String initDubbo(String world){
        return providerService.sayHello(world);
    }
}

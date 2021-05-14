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

    @Reference
    private ProviderService providerService;

    public String initDubbo(String world){
        return providerService.sayHello(world);
    }
}

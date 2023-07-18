package com.jianghu.api;

import java.time.ZonedDateTime;

/**
 * @description: 本地伪装，提供者不可用时，调用本地服务
 * @author: OF3848
 * @create: 2021-05-21 13:59
 */
public class ProviderServiceMock implements ProviderService {
    @Override
    public String sayHello(String world) {
        return "服务不可用啊！！！";
    }

    @Override
    public ZonedDateTime sayTime(ZonedDateTime time) {
        return null;
    }

    @Override
    public Person sayTime(Person person) {
        return null;
    }
}

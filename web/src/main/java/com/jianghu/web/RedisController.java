package com.jianghu.web;

import com.jianghu.consumer.ConsumerService;
import com.jianghu.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: OF3848
 * @create: 2021-05-14 11:09
 */
@RestController
public class RedisController {

    @Autowired
    private ConsumerService consumerService;

    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public void testRedis(){
        RedisUtil.set("k1", "v1");
    }
}

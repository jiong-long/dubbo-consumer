package com.jianghu.web;

import com.jianghu.mq.active.ActiveMqService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: OF3848
 * @create: 2021-08-17 23:05
 */
@RestController
@RequestMapping("/activemq")
public class ActiveMqController {

    @Autowired
    private ActiveMqService activeMqService;

    @GetMapping("/sendMsg")
    @ApiOperation("与SpringBoot集成---Queue")
    public String sendMessage(){
        activeMqService.sendMessageQueue();
        return "success!";
    }


    @GetMapping("/sendMsg2")
    @ApiOperation("与SpringBoot集成---Topic")
    public String sendMessage2(){
        activeMqService.sendMessageTopic();
        return "success!";
    }
}

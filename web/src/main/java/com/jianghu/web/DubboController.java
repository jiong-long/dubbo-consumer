package com.jianghu.web;

import com.jianghu.api.Person;
import com.jianghu.consumer.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

/**
 * @description:
 * @author: OF3848
 * @create: 2021-05-14 11:09
 */
@RestController
public class DubboController {

    @Autowired
    private ConsumerService consumerService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String initDubbo(){
        return consumerService.initDubbo("什么是快乐星球？？？？？");
    }

    @GetMapping(value = "/time")
    public Person sayTime(){
        Person person = new Person();
        person.setBirthday(ZonedDateTime.now());
        return consumerService.sayTime(person);
    }
}

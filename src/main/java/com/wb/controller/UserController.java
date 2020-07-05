package com.wb.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.wb.feignclient.BaiduFeignClient;
import com.wb.feignclient.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    // feign接口
    @Autowired
    private UserClient userClient;

    @GetMapping("getUser")
    public String getUser() {
        // 直接用服务提供者的spring.application.name的值，可以调用
        String url = "http://spring-cloud-provider";
        String forObject = restTemplate.getForObject(url + "/getUser", String.class, (Object) null);
        return forObject;
    }

    //通过feign调用
    @GetMapping("getUser1")
    public String getUser1(){
        System.out.println("aaa");
        return userClient.getUser1();
    }

    @GetMapping("getUser2")
    public String getUser2(@RequestParam("name") String name){
        String resource = "doSomeThing";
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        // 被保护的资源,每个资源比必须和SphO.entry(resource)只用的参数保持一致
        rule.setResource(resource);
        // 限流阈值类型 QPS模式或并发线程数模式
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 限流阈值
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);

        /*
        Sentinel只对BlockException和其子类异常做监控
        为了支持其他异常，可以用Tracer.trace(e);
        表示监控e的抛出次数，达到监控的目的
         */
        try(Entry entry = SphU.entry(resource)){
            if(name == null || name.length()==0){
                throw new IllegalArgumentException("name is null");
            }
            return "业务逻辑执行...";
        } catch (BlockException e) {
            return "BlockException 被限流";
        }catch (IllegalArgumentException e){
            Tracer.trace(e);
            return "IllegalArgumentException 被限流";
        }
    }

    // 用SentinelResource方式
    @GetMapping("getUser3")
    @SentinelResource(value = "getUser3",blockHandler = "block",fallback = "fallback")
    public String getUser3(@RequestParam(required = false) String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("name is null");
        }
        return "success";
    }

    public String block(String name, BlockException e) {
        System.out.println("限流了");
        return "被限流";
    }

    public String fallback(String name) {
        System.out.println("降级了");
        return "被降级";
    }

    @Autowired
    private BaiduFeignClient baiduFeignClient;
    @GetMapping("baidu")
    public String index(){
        return baiduFeignClient.index();
    }
}

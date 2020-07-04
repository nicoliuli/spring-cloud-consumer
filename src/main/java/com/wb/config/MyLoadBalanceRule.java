package com.wb.config;

/*import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 扩展Ribbon，自定义负载均衡规则
 * 然后修改spring-cloud-provider.ribbon.NFLoadBalancerRuleClassName
 *
@Configuration
public class MyLoadBalanceRule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        ILoadBalancer lb = getLoadBalancer();
        // 获取所有服务List
        List<Server> allServers = lb.getAllServers();
        // 获取可用的服务list
        List<Server> reachableServers = lb.getReachableServers();
        // 可以根据一些负载均衡规则，自定义
        System.out.println("allServers:"+allServers.size());
        System.out.println("reachableServers:"+reachableServers.size());
        return allServers.get(0);
    }
}*/

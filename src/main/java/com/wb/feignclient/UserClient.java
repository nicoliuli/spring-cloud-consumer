package com.wb.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//服务提供者id
@FeignClient(name="spring-cloud-provider")
public interface UserClient {

    // 这个路径要和provider端，对应的接口路径保持一致
    @GetMapping("/getUser1")
    public String getUser1();
}

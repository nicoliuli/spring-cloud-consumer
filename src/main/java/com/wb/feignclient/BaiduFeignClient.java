package com.wb.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 脱离Ribbon使用Feign
 * name随便起
 * 疑问：是不是可以把这种方法，定义为一个HTTP组件，不用手写HTTP组件？
 * 和其他htto组件相比，feign性能怎样？有池化技术吗？怎么添加http header等信息
 */
@FeignClient(name="baidu",url = "https://www.taobao.com")
public interface BaiduFeignClient {

    @GetMapping("")
    String index();
}

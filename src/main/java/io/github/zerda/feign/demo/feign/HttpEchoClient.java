package io.github.zerda.feign.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="http-echo", url = "${feign.client.http-echo.url}")
public interface HttpEchoClient {
    @GetMapping("api/v1/cats")
    String echo(@RequestParam(name = "delay") int delay);
}

package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

// 明白远程调用时怎么使用的
@FeignClient("user-service")
public interface UserClient extends UserApi {
}

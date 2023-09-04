package com.yh.weatherpush.manager.api;


import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author yangh
 */
@FeignClient(name = "qywx-api", url = "${qywx.base-url}")
public interface QywxApiClient extends com.yh.api.client.QywxApiClient {

}

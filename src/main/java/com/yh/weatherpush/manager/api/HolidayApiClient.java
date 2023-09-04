package com.yh.weatherpush.manager.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 *
 */
@FeignClient(name = "holiday-api", url = "${holiday.url}")
public interface HolidayApiClient extends com.yh.api.client.HolidayApiClient {

}

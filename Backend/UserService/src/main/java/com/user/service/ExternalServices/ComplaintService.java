package com.user.service.ExternalServices;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="ComplaintService")
public interface ComplaintService {
}

package com.driden.job_app_company_service.feign;

import com.driden.job_app_company_service.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="user-service")
public interface UserFeign {
    @PostMapping("/user-service/users/get-users-by-ids")
    public ResponseEntity<List<User>> getUsersByIds(@RequestBody List<Long> userIds);
}

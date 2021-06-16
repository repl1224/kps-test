package com.kpsec.test.contoller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kpsec.test.model.AccountResult;
import com.kpsec.test.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Sample")
@RestController
@RequestMapping("/test")
public class SampleController {
    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "sample")
    @GetMapping(value = "/acount")
    public List<AccountResult> getAccountInfo(String branchCode) {
        return accountService.getAccountByBranchCode(branchCode);
    }
    
    @ApiOperation(value = "sampleApi1")
    @GetMapping(value = "/sampleApi1")
    public List<Map<String, Object>> getSampleApi1() {
        return accountService.sampleApi1();
    }
    
    @ApiOperation(value = "sampleApi2")
    @GetMapping(value = "/sampleApi2")
    public List<Map<String, Object>> getSampleApi2() {
        return accountService.sampleApi2();
    }
    
    @ApiOperation(value = "sampleApi3")
    @GetMapping(value = "/sampleApi3")
    public List<Map<String, Object>> getSampleApi3() {
        return accountService.sampleApi3();
    }
    
    @ApiOperation(value = "sampleApi4")
    @GetMapping(value = "/sampleApi4")
    public Map<String, Object> getSampleApi4(@RequestParam MultiValueMap<String, String> param) {
    	
    	String brName = param.getFirst("brName");
        return accountService.sampleApi4(brName);
    }
}

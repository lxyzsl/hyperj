package com.hyperj.app.controller;

import com.hyperj.framework.config.HyperjConfig;
import com.hyperj.framework.web.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
@Api("foo接口")
public class FooController {

    @Autowired
    private HyperjConfig hyperjConfig;

    @GetMapping("/get")
    @ApiOperation("foo")
    public R foo()  {
        return R.success(hyperjConfig.getName());
    }
}

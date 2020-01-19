package com.hovvyoung.mall.service.impl;

import com.hovvyoung.mall.MallApplicationTests;
import com.hovvyoung.mall.enums.ResponseEnum;
import com.hovvyoung.mall.service.ICategoryService;
import com.hovvyoung.mall.vo.CategoryVo;
import com.hovvyoung.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImplTest extends MallApplicationTests {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVo<List<CategoryVo>> listResponseVo = categoryService.selectAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), listResponseVo.getStatus());
    }
}
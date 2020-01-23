package com.hovvyoung.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.hovvyoung.mall.MallApplicationTests;
import com.hovvyoung.mall.enums.ResponseEnum;
import com.hovvyoung.mall.service.IProductService;
import com.hovvyoung.mall.vo.ProductDetailVo;
import com.hovvyoung.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImplTest extends MallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        ResponseVo<PageInfo> responseVo = productService.list(null, 2, 2);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void detail() {
        ResponseVo<ProductDetailVo> responseVo = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}
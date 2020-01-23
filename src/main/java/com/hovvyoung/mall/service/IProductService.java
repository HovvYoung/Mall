package com.hovvyoung.mall.service;

import com.github.pagehelper.PageInfo;
import com.hovvyoung.mall.vo.ProductDetailVo;
import com.hovvyoung.mall.vo.ResponseVo;

public interface IProductService {

    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> detail(Integer productId);
}

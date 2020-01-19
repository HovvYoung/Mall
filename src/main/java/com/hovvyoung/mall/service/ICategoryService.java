package com.hovvyoung.mall.service;

import com.hovvyoung.mall.vo.CategoryVo;
import com.hovvyoung.mall.vo.ResponseVo;

import java.util.List;

public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAll();
}

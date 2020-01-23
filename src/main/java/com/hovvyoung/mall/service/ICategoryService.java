package com.hovvyoung.mall.service;

import com.hovvyoung.mall.vo.CategoryVo;
import com.hovvyoung.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAll();

    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}

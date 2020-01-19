package com.hovvyoung.mall.service.impl;

import com.hovvyoung.mall.dao.CategoryMapper;
import com.hovvyoung.mall.pojo.Category;
import com.hovvyoung.mall.service.ICategoryService;
import com.hovvyoung.mall.vo.CategoryVo;
import com.hovvyoung.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.hovvyoung.mall.consts.MallConst.ROOT_PARENT_ID;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<Category> categories = categoryMapper.selectAll();
        List<CategoryVo> categoryVoList = new ArrayList<>();

        for (Category category : categories) {
            if (category.getParentId().equals(ROOT_PARENT_ID)) {
                categoryVoList.add(category2CategoryVo(category));
            }
        }
        // set sort_order not Null first;
        categoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
        findSubCategory(categories, categoryVoList);

        return ResponseVo.success(categoryVoList);
    }

    private void findSubCategory(List<Category> categories, List<CategoryVo> categoryVoList) {
        for (CategoryVo categoryVo : categoryVoList) {

            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                if (categoryVo.getId().equals(category.getParentId())) {
                    subCategoryVoList.add(category2CategoryVo(category));
                }


                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
                categoryVo.setSubCategories(subCategoryVoList);

                findSubCategory(categories, subCategoryVoList);
            }
        }
    }

    private CategoryVo category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}

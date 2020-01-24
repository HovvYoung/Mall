package com.hovvyoung.mall.service;

import com.hovvyoung.mall.form.CartAddForm;
import com.hovvyoung.mall.form.CartUpdateForm;
import com.hovvyoung.mall.vo.CartVo;
import com.hovvyoung.mall.vo.ResponseVo;

public interface ICartService {

    ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form);

    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);
}

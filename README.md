# 通用+秒杀电商系统+支付系统

电商系统由两个模块组成：通用型电商模块 和 秒杀模块  
支付系统接入微信native支付和支付宝电脑网页支付

###通用性电商模块
#####Service:  
`CartService` `CategoryService` `OrderService` `ProductService` `ShippingService` `UserService` 
#####数据库表关系
```
user -- 1-N -- order -- 1-N -- order_item  
　|　　　　　　　 |  
shipping　　pay_info

catogary -- 1-N -- product (TODO: 库存分离出一张单独的stock表)

promote -- 1-N -- product  (实际可以是N-N)
```
#####索引优化：
查表要么用order_id查订单通用信息(user,shipping, time)，要么同时用order_id 和 user_id查订单商品详情;  
在order表中，创建UNIQUE_KEY 'order_no_index' ('order_no) USING BTREE　唯一索引  
在order_item表中，创建了KEY order_no_index单索引和KEY order_no_user_id_index组合索引

#####购物车
购物车商品信息保存在redis中，用hash结构存储。  
为什么不用list？因为list查找商品要O(n), 而hash是O(1);  
看看selectAll方法
```java
    //<cart_uid, product_id, item_object>
    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }
```

前端先电商模块发起下单请求 createOrder -> OrderService 创建订单, 删减购物车，扣减库存 -> 向前端返回oderId  
前端带着orderId请求支付模块的支付接口，当向支付平台支付成功后，支付平台会向指定的notifyUrl发送通知，
支付模块修改payInfo成功状态后向mq消息队列发送支付成功消息，电商模块取到支付成功信息，修改订单表对应订单状态为已支付并记录支付时间。

# 
###秒杀模块
***先给ProductVo增加一个属性 PromoVo(聚合模型)，如果不为空，则表示该商品正在活动中***  
前端发起请求调用promoService，根据itemid获取即将进行的或者正在进行的秒杀活动   
`PromoModel是DO往上在抽象出的一层，可以一个Model可以聚合多个DO, ItemModel 聚合了 itemDO 和 promoDO(PromoDO对应Promo)`
```java
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        //dataobject->model
        PromoModel promoModel = convertFromDataObject(promoDO);
        if(promoModel == null){
            return null;
        }

        //判断当前时间是否秒杀活动即将开始或正在进行
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }
```
#####活动发布
```java
    @Override
    public void publishPromo(Integer promoId) {
        //通过活动id获取活动
        PromoDO promoDO = promoDOMapper.selectByPrimaryKey(promoId);
        if(promoDO.getItemId() == null || promoDO.getItemId().intValue() == 0){
            return;
        }
        ItemModel itemModel = itemService.getItemById(promoDO.getItemId());

        //将库存同步到redis内
        redisTemplate.opsForValue().set("promo_item_stock_"+itemModel.getId(), itemModel.getStock());

        //将秒杀大闸的限制数字设到redis内
        redisTemplate.opsForValue().set("promo_door_count_"+promoId,itemModel.getStock().intValue() * 5);

    }
```

##分布式扩展和优化
#####交易优化
```
    高效验证：缓存，索引
    缓存库存
    缓存令牌，库存售罄标志防击穿
    交易异步化
    RocketMQ事务型消息，消息反查，库存流水 
```

#####防刷限流
```
    验证码
    令牌桶限流， rateLimiter
```

#####分布式会话
```
    cookie, session, token
```

#####多级缓存
```
    redis缓存
    本地缓存: guava cache
    热点缓存： 如何判定热， 如何保持热， 如何淘汰热
```

# 支付系统
接入微信native支付和支付宝电脑网页支付。
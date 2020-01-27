package com.hovvyoung.mall.listener;

import com.google.gson.Gson;
import com.hovvyoung.mall.pojo.PayInfo;
import com.hovvyoung.mall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * As for PayInfo, In general, Payment project should provide client.jar, mall project includes it.；
 */
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {

    @Autowired
    private IOrderService orderService;

    @RabbitHandler
    public void process(String msg) {
        log.info("【接收到消息】=> {}", msg);

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        if (payInfo.getPlatformStatus().equals("SUCCESS")) {
//            modify order status
            orderService.paid(payInfo.getOrderNo());
        }
    }
}

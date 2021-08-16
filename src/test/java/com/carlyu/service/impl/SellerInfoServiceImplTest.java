package com.carlyu.service.impl;

import com.carlyu.entity.SellerInfo;
import com.carlyu.service.SellerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 卖家端用户信息Service实现类 的测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerInfoServiceImplTest {
    private static final String openid = "liyanxxxopenid";

    /**
     * 卖家端信息Service
     */
    @Autowired
    private SellerInfoService sellerInfoService;

    /**
     * 通过openid查询卖家端信息 方法测试
     */
    @Test
    public void findSellerInfoByOpenid() throws Exception {
        SellerInfo sellerInfo = sellerInfoService.findSellerInfoByOpenid(openid);
        Assert.assertEquals(openid, sellerInfo.getOpenid());
    }
}

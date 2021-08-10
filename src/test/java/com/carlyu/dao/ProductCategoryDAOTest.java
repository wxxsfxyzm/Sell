package com.carlyu.dao;

import com.carlyu.SellApplication;
import com.carlyu.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SellApplication.class)
public class ProductCategoryDAOTest {
    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    /**
     * 根据商品类目 ID 查询商品类目信息
     */
    @Test
    public void findOneTest() {
        // https://blog.csdn.net/TMaskBoy/article/details/91489433 SpringBoot1.x和SpringBoot2.x中所有不同
        ProductCategory productCategory =
                productCategoryDAO.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }

    /**
     * 新增商品类目
     * Table '项目名称..hibernate_sequence' doesn't exist 的解决方法: * https://blog.csdn.net/Interesting_Talent/article/details/81454104
     */
    @Test
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(3);
        productCategoryDAO.save(productCategory);
    }

    /**
     * 修改商品类目
     * 更新/修改 还是调用 save 方法 只不过要更新主键 categoryId
     */
    @Test
    @Transactional // 回滚数据(测试完数据就回滚)
    public void saveTest2() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(4);
        productCategoryDAO.save(productCategory);
    }

    /**
     * 一次性 根据商品类目查询 测试
     */
    @Test
    public void queryCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(1, 2);
        List<ProductCategory> result = productCategoryDAO.queryByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());
    }
}
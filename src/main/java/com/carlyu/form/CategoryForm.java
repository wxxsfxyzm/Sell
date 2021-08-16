package com.carlyu.form;

import lombok.Data;

/**
 * 封装 修改商品类目、新增商品类目 方法 传递的数据
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /**
     * 类目名字.
     */
    private String categoryName;

    /**
     * 类目编号.
     */
    private Integer categoryType;
}

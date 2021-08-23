<html>
    <#-- (1) 引入头文件 -->
    <#include  "../common/header.ftl">

    <body>
        <!-- (2)自己写的div start -->
        <div id="wrapper" class="toggled">
            <#-- (3)边栏sidebar-->
            <#include  "../common/nav.ftl">

            <!-- (4) 主要内容content start -->
            <div class="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <!--  复制 从 http://www.ibootstrap.cn 里面下载的内容 start -->
                        <div class="col-md-12 column">
                            <form role="form" method="post" action="/sell/seller/product/save">
                                <div class="form-group">
                                    <label>商品名称</label>
                                    <span class="errSpan error hide" id="emptyProductName">商品名称不能为空！</span>
                                    <input type="text" class="form-control" id="productName"
                                           name="productName" value="${(product.productName)!''}"/>
                                </div>
                                <div class="form-group">
                                    <label>商品价格</label>
                                    <span class="errSpan error hide" id="emptyProductPrice">商品价格不能为空！</span>
                                    <input type="text" class="form-control" id="productPrice"
                                           name="productPrice" value="${(product.productPrice)!''}"/>
                                </div>
                                <div class="form-group">
                                    <label>商品库存</label><span class="errSpan error hide"
                                                             id="emptyProductStock">商品库存不能为空！</span>
                                    <input type="number" class="form-control" id="productStock"
                                           name="productStock" value="${(product.productStock)!''}"/>

                                </div>
                                <div class="form-group">
                                    <label>商品描述</label>
                                    <span class="errSpan error hide" id="emptyProductDescription">商品描述不能为空！</span>
                                    <input type="text" class="form-control" id="productDescription"
                                           name="productDescription"
                                           value="${(product.productDescription)!''}"/>
                                </div>
                                <div class="form-group">
                                    <label>商品图片</label>
                                    <img height="100px;" width="100px;" src="${(product.productIcon)!''}"
                                         alt="该图片不存在!"/> <br/>
                                    <input id="productIcon" name="productIcon" type="text"
                                           value="${(product.productIcon)!''}" width="50%"/>
                                    <span class="errSpan error hide" id="emptyProductImage">商品图片不能为空！</span>
                                </div>
                                <div class="form-group">
                                    <label>商品类目</label>
                                    <select name="categoryType" id="categoryType" class="form-control">
                                        <#list categoryList as category>
                                            <option value="${category.categoryType}" <#if (product.categoryType)?? && product.categoryType == category.categoryType> selected </#if> >
                                                ${category.categoryName}
                                            </option>
                                        </#list>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-default">保存修改</button>
                                <input type="hidden" name="productId" value="${(product.productId)!''}">
                            </form>
                        </div>
                        <!--  复制 从 http://www.ibootstrap.cn 里面下载的内容 end -->
                    </div>
                </div>
            </div>
            <!-- (4) 主要内容content end -->
        </div>
        <!-- (2)自己写的div end -->
        <script type="text/javascript" src="${path}/javascript/productEmptyCheck.js">
        </script>
    </body>
</html>

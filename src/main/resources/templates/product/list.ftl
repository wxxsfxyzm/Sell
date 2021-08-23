<#--
<h1>${orderDTOPageList.getTotalPages()}</h1>
<h1>${orderDTOPageList.totalPages}</h1>
-->

<#--<#list orderDTOPageList.content as orderDTO>
    ${orderDTO.orderId} <br/>
</#list>-->
<html>
    <#-- (1) 引入头文件 -->
    <#include  "../common/header.ftl">


    <body>
        <!-- (2)复制 从 https://www.ibootstrap.cn 里面下载的内容 -->
        <!-- 自己写的div start -->
        <div id="wrapper" class="toggled">
            <#-- (3)边栏sidebar-->
            <#include  "../common/nav.ftl">

            <!-- (4) 主要内容content start -->
            <div class="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <!-- 表格内容 start  -->
                        <div class="col-md-12 column">
                            <table class="table table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>商品ID</th>
                                    <th>名称</th>
                                    <th>图片</th>
                                    <th>单价</th>
                                    <th>库存</th>
                                    <th>描述</th>
                                    <th>类目</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>

                                <tbody>
                                <#if productInfoPageList.content??>
                                    <#list productInfoPageList.content as productInfo>
                                        <tr>
                                            <td>${productInfo_index + 1}</td>
                                            <td>${productInfo.productId}</td>
                                            <td>${productInfo.productName}</td>
                                            <td><img height="100" width="100" src="${productInfo.productIcon}"
                                                     alt="该图片不存在!"></td>
                                            <td>${productInfo.productPrice}</td>
                                            <td>${productInfo.productStock}</td>
                                            <td>${productInfo.productDescription}</td>
                                            <td>${productInfo.categoryType}</td>
                                            <td>${productInfo.createTime}</td>
                                            <td>${productInfo.updateTime}</td>
                                            <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a>
                                            </td>
                                            <td>
                                                <#if productInfo.getProductStatusEnum().message == "上架">
                                                    <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                                                <#else>
                                                    <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                                                </#if>
                                            </td>
                                        </tr>
                                    </#list>
                                </#if>
                                </tbody>
                                <#--主要内容content end -->
                            </table>
                        </div>
                        <!-- 表格内容 start  -->

                        <!-- 分页 start -->
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right"> <!-- 让分页居右-->
                                                               <!-- 上一页处理  start-->
                                <#if currentPage lte 1>
                                    <li class="disabled">
                                        <a href="#">上一页</a>
                                    </li>
                                <#else>
                                    <li>
                                        <a href="/sell/seller/order/list?page=${currentPage - 1}&size=${pageSize}">上一页</a>
                                    </li>
                                </#if>
                                                               <!-- 上一页处理  end-->
                                                               <!-- 代循环遍历(根据DB中查询出来的 带分页查询所有的订单列表) start-->
                                <#list 1..productInfoPageList.getTotalPages() as index>
                                    <!--  让当前页置灰  -->
                                    <#if currentPage == index>
                                        <li class="disabled">
                                            <a href="/sell/seller/product/list?page=${index}&size=${pageSize}">${index}</a>
                                        </li>
                                    <#else>
                                        <li>
                                            <a href="/sell/seller/product/list?page=${index}&size=${pageSize}">${index}</a>
                                        </li>
                                    </#if>
                                </#list>

                                                               <!-- 代循环遍历(根据DB中查询出来的 带分页查询所有的订单列表) start-->
                                                               <!-- 下一页处理  start-->
                                <#if currentPage gte productInfoPageList.getTotalPages()>
                                    <li class="disabled">
                                        <a href="#">下一页</a>
                                    </li>
                                <#else>
                                    <li>
                                        <a href="/sell/seller/product/list?page=${currentPage + 1}&size=${pageSize}">上一页</a>
                                    </li>
                                </#if>
                                                               <!-- 下一页处理  end-->
                            </ul>
                            <div>
                                <!-- 分页 end -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- (4) 主要内容content end -->
        </div>
        <!-- (2)自己写的div end -->
    </body>
</html>

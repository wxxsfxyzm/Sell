<html>
    <#include "../common/header.ftl">

    <body>
        <div id="wrapper" class="toggled">
            <#-- 边栏sidebar -->
            <#include "../common/nav.ftl">

            <#-- 主要内容content start -->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <form role="form" method="post" action="/sell/seller/category/save">
                                <div class="form-group">
                                    <label>商品类目名</label>
                                    <span id="emptyName" class="error hide">类目名不能为空！</span>
                                    <br/>
                                    <input id="categoryName" name="categoryName" type="text" class="form-control"
                                           value="${(category.categoryName)!''}"/>
                                </div>
                                <div class="form-group">
                                    <label>type</label>
                                    <span id="emptyType" class="errType error hide">Type不能为空！</span>
                                    <span id="duplicateType" class="errType error hide">Type已存在！</span>
                                    <br/>
                                    <input id="categoryType" name="categoryType" type="number" class="form-control"
                                           value="${(category.categoryType)!''}"/>
                                </div>
                                <input hidden type="text" name="categoryId" value="${(category.categoryId)!''}">
                                <button id="submit" type="submit" class="btn btn-default">提交</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <#-- 主要内容content end -->
        </div>
        <script type="text/javascript" src="${path}/javascript/categoryEmptyCheck.js"></script>
    </body>
</html>

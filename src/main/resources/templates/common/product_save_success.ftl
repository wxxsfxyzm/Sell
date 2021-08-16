<html>
    <head>
        <meta charset="utf-8">
        <title>成功提示</title>
        <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.css" rel="stylesheet">
        <#assign  path="${springMacroRequestContext.getContextPath()}">
        <script src="${path}/javascript/jquery-1.4.js" type="text/javascript"></script>
        <script src="${path}/javascript/time.js" type="text/javascript"></script>
    </head>

    <body>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <#--<div class="alert alert-dismissable alert-danger">-->
                    <div class="alert alert-dismissable alert-success">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <h4>
                            修改操作成功!
                        </h4>
                        <span class="alert-link">3</span>
                        <strong><span>秒后自动跳转</span></strong><br/>
                        <span>如果没有跳转，请<a href="${url}">点击这里</a>跳转！</span>
                        <input type="hidden" value="${url}" id="hiddenUrl">
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

function onDocumentReady() {
    var valid = false;
    $("#categoryName").blur(function () {
        valid = true;
        $('#emptyName').addClass('hide');
        const name = $(':input[name="categoryName"]').val().trim();
        if (!name.length) {
            valid = false;
            $("#emptyName").removeClass('hide');
        } else
            $("#emptyName").addClass('hide');
    });
    $("#categoryType").blur(function () {
        valid = true;
        $('.errType').addClass('hide');
        const name = $(':input[name="categoryType"]').val().trim();
        if (!name.length) {
            valid = false;
            $("#emptyType").removeClass('hide');
        } else {
            $("#emptyType").addClass('hide');
            $.ajax(
                {
                    "url": "newCategoryTypeDuplicateCheck",
                    "data": {
                        "categoryType": $("#categoryType").val(),
                    },
                    "dataType": "text",
                    "type": "post",
                    "success": function (result) {
                        console.log(result);
                        var jsonData = JSON.parse(result);
                        if (!jsonData.code) {// 请求成功
                            console.log(jsonData.msg);
                            valid = true;
                            $("#duplicateType").addClass("hide");
                        } else {
                            console.log(jsonData.msg);
                            valid = false;
                            $("#duplicateType").removeClass("hide");
                        }
                    }
                }
            );
        }
    });
    $('form').submit(function (e) {
        if (valid)
            $('form').submit();
        else
            alert("请检查输入！")
        e.preventDefault();
    });

}

$(onDocumentReady);
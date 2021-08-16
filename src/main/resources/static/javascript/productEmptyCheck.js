function onDocumentReady() {
    var valid = false;
    $("#productName").blur(function () {
        valid = true;
        $('emptyName').addClass('hide');
        const name = $(':input[name="productName"]').val().trim();
        if (!name.length) {
            valid = false;
            $("#emptyProductName").removeClass('hide');
        } else
            $("#emptyProductName").addClass('hide');
    });

    $("#productPrice").blur(function () {
        valid = true;
        $('#emptyProductPrice').addClass('hide');
        const name = $(':input[name="productPrice"]').val().trim();
        if (!name.length) {
            valid = false;
            $("#emptyProductPrice").removeClass('hide');
        } else
            $("#emptyProductPrice").addClass('hide');
    });

    $("#productStock").blur(function () {
        valid = true;
        $('#emptyProductStock').addClass('hide');
        const name = $(':input[name="productStock"]').val().trim();
        if (!name.length) {
            valid = false;
            $("#emptyProductStock").removeClass('hide');
        } else
            $("#emptyProductStock").addClass('hide');
    });

    $("#productDescription").blur(function () {
        valid = true;
        $('#emptyProductDescription').addClass('hide');
        const name = $(':input[name="productDescription"]').val().trim();
        if (!name.length) {
            valid = false;
            $("#emptyProductDescription").removeClass('hide');
        } else
            $("#emptyProductDescription").addClass('hide');
    });

    $("#productIcon").blur(function () {
        valid = true;
        $('#emptyProductImage').addClass('hide');
        const name = $(':input[name="productIcon"]').val().trim();
        if (!name.length) {
            valid = false;
            $("#emptyProductImage").removeClass('hide');
        } else
            $("#emptyProductImage").addClass('hide');
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
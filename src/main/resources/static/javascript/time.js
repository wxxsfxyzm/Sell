$(function () {
    // 使用匿名函数方法
    // 1秒调用一次
    setInterval(function () {
        // 获取到id为time标签中的内容，现进行判断
        if ($(".alert-link").text() == 1) {
            ;
            // 等于0 时清除计时
            location.href = $('#hiddenUrl').val();
        } else {
            $(".alert-link").text($(".alert-link").text() - 1);
        }
    }, 1000);
});

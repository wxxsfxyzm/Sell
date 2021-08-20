//老师的
/*$(function () {
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
});*/
//我的
$(document).ready(function () {
    function jump(count) {
        window.setTimeout(function () {
            count--;
            if (count > 0) {
                $('.alert-link').html(count);
                jump(count);
            } else {
                location.href = $('#hiddenUrl').val();
                ;
            }
        }, 1000);
    }

    jump(3);
});


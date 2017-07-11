$(document).ready(function() {
    // 加载完成后初始化星星评分控件
    $('.star').raty({
        readOnly : true,
        score : function() {
            return $(this).attr('data-rating');
        },
        path : "/Housekeeping/mobile/images"
    });
});

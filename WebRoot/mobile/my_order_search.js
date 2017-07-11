function setTab(name, cursel, n) {
    for ( i = 1; i <= n; i++) {
        var con = document.getElementById(name + i);
        con.className = i == cursel ? "check" : "check1";
    }
}


$(document).ready(function() {
    // 加载完成后初始化星星评分控件
    $('.star').raty({
        readOnly : false,
        path : "/Housekeeping/mobile/images"
    });
    $("#a_button_ok,#a_button_closed").click(function() {
        $('#filter').css("display", "none");
        $('#pageone').css("display", "none");
    });
    $("#a_button_open").click(function() {
        $('#filter').css("display", "block");
        $('#pageone').css("display", "block");
    });
});

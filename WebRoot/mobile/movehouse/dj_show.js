$(document).ready(function() {
    $('.star').raty({
        readOnly : true,
        score : function() {
            return $(this).attr('data-rating');
        },
        path : "/Housekeeping/mobile/images"
    });
});

function setTab(name, cursel, n) {
    for ( i = 1; i <= n; i++) {
        var menu = document.getElementById(name + i);
        var con = document.getElementById("con_" + name + "_" + i);
        menu.className = i == cursel ? "active" : "";
        con.style.display = i == cursel ? "block" : "none";
    }

}
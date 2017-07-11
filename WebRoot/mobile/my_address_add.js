$(document).ready(function() {

});

function addAddress() {
    var address = $("#address").val().trim();
    var city = $("#city").val().trim();
    if (city == "") {
        alert("请选择城市");
        return false;
    }
    if (address == "") {
        alert("请输入地址");
        return false;
    }
    $.postRequest({
        url : "/Housekeeping/AddressServlet.do",
        data : {
            header : {
                className : "",
                method : "addAddress"
            },
            parameter : {
                address : city + address
            }
        },
        success : function(data) {
            window.history.back();
        },
        error : function(message) {
            alert(message);
        }
    });
    return false;
}

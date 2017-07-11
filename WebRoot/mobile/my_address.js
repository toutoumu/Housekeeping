$(document).ready(function() {

});

/**
 * 将地址设置为默认地址
 * @param {Object} id
 */
function setDefault(id) {
    $.postRequest({
        url : "/Housekeeping/AddressServlet.do",
        data : {
            header : {
                className : "",
                method : "setDefault"
            },
            parameter : {
                id : id
            }
        },
        success : function(data, parameter) {
            location.reload(true);
        }
    });
    return false;
}

/**
 * 表单转换为Object对象
 */
$.fn.formToObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 将字符串转换为Json对象,支持复杂对象
 * @param {Object} serializedParams
 * 参数形式类似"prop1=value1&prop2=value2"
 * 或者'prop.subprop=value'
 */
$.fn.formToObjectExtend = function() {
    var serializedParams = this.serialize();
    var obj = {};
    function evalThem(str) {
        var attributeName = str.split("=")[0];
        var attributeValue = str.split("=")[1];
        if (!attributeValue) {
            return;
        }

        var array = attributeName.split(".");
        for (var i = 1; i < array.length; i++) {
            var tmpArray = Array();
            tmpArray.push("obj");
            for (var j = 0; j < i; j++) {
                tmpArray.push(array[j]);
            };
            var evalString = tmpArray.join(".");
            // alert(evalString);
            if (!eval(evalString)) {
                eval(evalString + "={};");
            }
        };
        eval("obj." + attributeName + "='" + attributeValue + "';");
    };

    var properties = serializedParams.split("&");
    for (var i = 0; i < properties.length; i++) {
        evalThem(properties[i]);
    };
    return obj;
};

/**
 * 表单转换为json字符串
 */
$.fn.formToJson = function() {
    var serializedParams = this.serialize();
    var obj = paramString2obj(serializedParams);
    //调用谷歌的json库进行处理
    return $.toJSON(obj);
    //return JSON.stringify(obj);
};

/** @serializedParams looks like "prop1=value1&prop2=value2".
 Nested property like 'prop.subprop=value' is also supported **/
/**
 *将字符串转换为Json对象
 * @param {Object} serializedParams
 * 参数形式类似"prop1=value1&prop2=value2"
 * 或者'prop.subprop=value'
 */
function paramString2obj(serializedParams) {

    var obj = {};
    function evalThem(str) {
        var attributeName = str.split("=")[0];
        var attributeValue = str.split("=")[1];
        if (!attributeValue) {
            return;
        }

        var array = attributeName.split(".");
        for (var i = 1; i < array.length; i++) {
            var tmpArray = Array();
            tmpArray.push("obj");
            for (var j = 0; j < i; j++) {
                tmpArray.push(array[j]);
            };
            var evalString = tmpArray.join(".");
            // alert(evalString);
            if (!eval(evalString)) {
                eval(evalString + "={};");
            }
        };

        eval("obj." + attributeName + "='" + attributeValue + "';");

    };

    var properties = serializedParams.split("&");
    for (var i = 0; i < properties.length; i++) {
        evalThem(properties[i]);
    };
    return obj;
}

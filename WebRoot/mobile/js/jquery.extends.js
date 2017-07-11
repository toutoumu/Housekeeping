/**
 * 对jQuery的扩展函数都写在此处,调用方式$.postRequest()....
 */
( function($) {
        /**
         * post方式请求数据
         */
        $.mobile.loader.prototype.options.theme = "b";
        //$mobile.loadingMessageTheme = "a";
        $.mobile.loadingMessageTextVisible = true;
        $.postRequest = function(para) {
            // 弹出等待框
            var theme = $.mobile.loader.prototype.options.theme;
            $.mobile.loading("show", {
                text : "loding...",
                textVisible : true,
                theme : theme,
                textonly : false
            });
            $.ajax({
                type : "POST",
                url : para.url,
                data : $.toJSON(para.data),
                dataType : "json",
                success : function(data) {
                    $.mobile.loading("hide");
                    if (data.header.isSuccess) {
                        if (para.success) {
                            para.success(data.data, data.parameter);
                        }
                    } else {
                        if (para.error) {
                            para.error(data.header.message);
                        } else {
                            alert(data.header.message);
                        }
                    }
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    $.mobile.loading("hide");
                    // 通常情况下textStatus和errorThrown只有其中一个包含信息
                    alert(textStatus + errorThrown);
                }
            });
        };
    }(jQuery));

// 下面是针对表单的一些扩展
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
 *
 * @param {Object}
 *            serializedParams 参数形式类似"prop1=value1&prop2=value2"
 *            或者'prop.subprop=value'
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
            }
            ;
            var evalString = tmpArray.join(".");
            // alert(evalString);
            if (!eval(evalString)) {
                eval(evalString + "={};");
            }
        }
        ;
        eval("obj." + attributeName + "='" + attributeValue + "';");
    }

    ;

    var properties = serializedParams.split("&");
    for (var i = 0; i < properties.length; i++) {
        evalThem(properties[i]);
    }
    ;
    return obj;
};

/**
 * 表单转换为json字符串
 */
$.fn.formToJson = function() {
    var serializedParams = this.serialize();
    var obj = paramString2obj(serializedParams);
    // 调用谷歌的json库进行处理
    return $.toJSON(obj);
    // return JSON.stringify(obj);
};

/*******************************************************************************
 * @serializedParams looks like "prop1=value1&prop2=value2". Nested property
 *                   like 'prop.subprop=value' is also supported
 ******************************************************************************/
/**
 * 将字符串转换为Json对象
 *
 * @param {Object}
 *            serializedParams 参数形式类似"prop1=value1&prop2=value2"
 *            或者'prop.subprop=value'
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
            }
            ;
            var evalString = tmpArray.join(".");
            // alert(evalString);
            if (!eval(evalString)) {
                eval(evalString + "={};");
            }
        }
        ;

        eval("obj." + attributeName + "='" + attributeValue + "';");

    }

    ;

    var properties = serializedParams.split("&");
    for (var i = 0; i < properties.length; i++) {
        evalThem(properties[i]);
    }
    ;
    return obj;
}

String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
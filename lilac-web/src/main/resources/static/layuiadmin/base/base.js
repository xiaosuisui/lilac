$.micAjax = function(option){
    if(option.url.indexOf("?") > -1){
        option.url += "&v="+new Date().getTime();
    }else{
        option.url += "?v="+new Date().getTime();
    }
    $.ajax(option);
};
var config = {};
// 服务端域名
config.domain = window.location.protocol + "//" + window.location.hostname + (window.location.port == "" ? window.location.port : ":" + window.location.port) + "/";
// 调试模式
config.debug = false;
// 服务端API
config.api = {
    splider: {
        getCollegeInfo: config.domain + "splider/college"
    }
};

var postType = "POST";
var getType = "GET";
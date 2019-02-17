/**
 *
 *
 * @param img
 */
/*店铺注册 验证码*/
function changeVerifyCode(img) {
    img.src = "../Kaptche?" + Math.floor(Math.random()*100);
}
//解析URL中的shopId
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}

$(function () {

    $.getJSON("/product/getProductList",function (data) {
        console.log(data);
    })
});
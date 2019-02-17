$(function () {

    var shopId = getQueryString("shopId");

    getProductList();

    function getProductList() {
        $.ajax({
            url:"/shop/getProductList/"+shopId,
            type:"GET",
            success:function (data) {
                alert(data);
            }
        })
    }
});
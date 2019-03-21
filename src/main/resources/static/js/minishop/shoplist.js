$(function () {
    var shopCategoryId = getQueryString("shopCategoryId");
    if(shopCategoryId>0){
        initWithShopCategory(shopCategoryId);
    }

    function initWithShopCategory(id) {
        $.getJSON("/frontend/initWithShopCategory/"+id,function (data) {
            console.log(data);
        })
    }

});
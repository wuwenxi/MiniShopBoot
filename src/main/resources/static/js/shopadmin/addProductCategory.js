$(function () {
    getInitInfo();

    function getInitInfo() {
        $.getJSON("/shop/getshoplist",function (data) {
            $("#productFromShop").empty();
            var shop0 = "<option value='0'>—— 店铺 ——</option>";
            $("#productFromShop").append(shop0);
            $.each(data.extend.map.shops,function () {
                var shop = $("<option></option>").append(this.shopName).attr("value",this.shopId);
                $("#productFromShop").append(shop);
            })

        });
    }

    $("#submit").click(function () {

        var productCatogory={};

        productCatogory.productCategoryName = $("#productCategoryName").val();
        productCatogory.shopId = $("#productFromShop option:selected").val();

        var formdata = new FormData();

        formdata.append("productCategory",JSON.stringify(productCatogory));

        $.ajax({
            url:"/product/addProductCategory",
            type:"POST",
            data:formdata,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                console.log(data);
            }
        })
    })
});
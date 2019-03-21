/*取消点击出现下拉菜单，改为点击跳转页面*/
$(document).ready(function(){
    $(document).off('click.bs.dropdown.data-api');
});

$(function(){

    initShopCategory();

    function initShopCategory(){
    	$.ajax({
			url:"/frontend/initShopCategory",
			type:"GET",
            contentType:false,
            processData:false,
            cache:false,
			success:function (data) {
                $.each(data.extend.map.categoryList, function(index, item) {
					for (var i=1;i<9;i++){
                        if(item.parent.shopCategoryId === i){
                            var html = $("<li>" +
                                "<a href='/frontend/shopList?shopCategoryId="+item.shopCategoryId+"' target='_blank'>"
                                +item.shopCategoryName+"</a>" +
                                "</li>");
                            $(".index"+i).append(html);
                        }
                    }
                });
            }
		})
	}

	$("#search").click(function(){
		alert("正在检索...")
	});

});

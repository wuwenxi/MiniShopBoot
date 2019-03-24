$(function () {

    var shopId = getQueryString("shopId");

    if(shopId>0){
        initShopDetail(shopId);
        //initProductList(shopId);
        to_page(1);
    }

    function initShopDetail(id) {
        $.getJSON("/frontend/getShop/"+id,function (data) {
            var shop = data.extend.map.shop;
            var html = $("<img src='"+shop.shopImg+"' title='"+shop.shopName+"' style='height:300px;width:100%;display: block'>");
            $(".shop-img").html(html);
            html = $("<li>店铺：<span>"+shop.shopName+"</span></li>" +
                "<li>店铺地址：<span>"+shop.shopAddress+"</span></li>"+
                "<li>详细地址：<span>"+shop.shopAddress+"</span></li>" +
                "<li>联系电话：<span>"+shop.phone+"</span></li>" +
                "<li>创建时间：<span>"+fmtDate(shop.createTime)+"</span></li>" +
                "<li>店铺情况：<p>&nbsp;&nbsp;&nbsp;&nbsp;"+shop.shopDesc+"</p></li>"
            );
            $(".shop-detail").html(html);
        })
    }

    function to_page(pn) {
        initProductList(pn)
    }

    var currentPage,theLastPages;

    function initProductList(pn) {
        $.ajax({
            url:"/frontend/getProductList/"+shopId,
            data:"pn="+pn,
            type:"GET",
            cache:false,
            processData:false,
            contentData:false,
            success:function (data) {
                if(data.code===100){
                    console.log(data);

                    var result = data.extend.map;

                    currentPage = result.pageInfo.pageNum;

                    theLastPages = result.pageInfo.pages;

                    build_shop_list(result);

                    build_page_info(result);

                    build_page_nav(result);
                }else {
                    $("#modal_hint").text("当前店铺商品已下架");
                    $("#modal").modal({
                        backdrop:'static'
                    });
                }
            }
        })
    }

    function build_shop_list(result) {
        var list = result.pageInfo.list;
        $.each(list, function (index, item) {
            var html = $("<div class='col-sm-6 col-md-3 shop-list'>"
                +"<a href='/frontend/productDetail?product="+item.productId+"' " +
                "title='"+item.shop.shopName+" "+item.productCategory.productCategoryName
                +" "+item.productName+"'>"
                +"<div class='thumbnail'>"
                + "<img src='"+item.imgAddress+"' style='width: 100%;display: block' >"
                +"<div class='caption'>"
                +"<h4>"+item.shop.shopName+" "+item.productCategory.productCategoryName
                +" "+item.productName+"</h4>"
                +"</div>"
                +"</div>"
                + "</a>"
                + "</div>");

            $(".product-list").append(html);
        })
    }

    function build_page_info(result) {
        $("#page_info").empty();

        var pages = result.pageInfo.pages;//总共的页码
        var pageNum = result.pageInfo.pageNum;//当前页码
        var total = result.pageInfo.total;//总记录数

        $("#page_info").append("当前第"+pageNum+"页，共"+pages+"页，共"+total+"条记录");
    }

    function build_page_nav(result) {
        $("#page_nav").empty();

        var list = result.pageInfo.navigatepageNums;//导航页码

        var ul = $("<ul></ul>").addClass("pagination");
        var firstPage = $("<li></li>").append($("<a></a>").append("首页"));
        var nextPage = $("<li></li>").append($("<a></a>").append("&raquo;"));


        var Previous = $("<li></li>").append($("<a></a>").append("&laquo;"));
        var lastPage = $("<li></li>").append($("<a></a>").append("末页"));

        if(result.pageInfo.hasPreviousPage === false){
            firstPage.addClass("disabled");
            Previous.addClass("disabled");
        }else {
            firstPage.click(function () {
                to_page(1);
            });
            Previous.click(function () {
                to_page(currentPage-1);
            });
        }

        if(result.pageInfo.hasNextPage === false){
            nextPage.addClass("disabled");
            lastPage.addClass("disabled");
        }else {
            nextPage.click(function () {
                to_page(currentPage+1);
            });
            lastPage.click(function () {
                to_page(theLastPages);
            })
        }

        ul.append(firstPage)
            .append(Previous)
            .appendTo("#page_nav");

        $.each(list,function (index,item) {
            var num = $("<li></li>").append($("<a></a>").append(item));

            if(currentPage === item){
                num.addClass("active");
            }

            num.click(function () {
                to_page(item);
            });

            ul.append(num).appendTo("#page_nav");
        });


        ul.append(nextPage)
            .append(lastPage);

        $("<nav></nav>").append(ul).appendTo("#page_nav");
    }


    function fmtDate(time){
        var data = new Date(time);
        var year = data.getFullYear();  //获取年
        var month = data.getMonth() + 1;    //获取月
        var day = data.getDate(); //获取日

        time = year + "年" + month + "月" + day + "日";
        return time;
    }
});
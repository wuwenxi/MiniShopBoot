/*取消点击出现下拉菜单，改为点击跳转页面*/
$(document).ready(function(){
    $(document).off('click.bs.dropdown.data-api');
});

$(function(){
	$("#search").click(function(){
		alert("正在检索...")
	})
});

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <title>${siteName!""}</title>
    <link media="all" href="/home/css/index.css" type="text/css" rel="stylesheet">
    <link href="/admin/css/bootstrap.min.css" rel="stylesheet">
    <link href="/admin/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="/admin/css/style.min.css" rel="stylesheet">
</head>
<body>
<#include "../common/top_header.ftl"/>
<#include "../common/left_menu.ftl"/>
<div class="container">
    <div class="main center clearfix">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>图片</th>
                                    <th>名称</th>
                                    <th>卖家QQ</th>
                                    <th>卖家电话</th>
                                    <th>售价</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#if pageBean.content?size gt 0>
                                    <#list pageBean.content as goods>
                                        <tr>
                                            <td style="vertical-align:middle;">
                                                <a href="/home/goods/detail?id=${goods.getGoods().getId()}">
                                                    <img src="/photo/view?filename=${goods.photo}" width="60px" height="60px">
                                                </a>
                                            </td>
                                            <td style="vertical-align:middle;">
                                                <a href="/home/goods/detail?id=${goods.getGoods().getId()}">${goods.name}</a>
                                            </td>
                                            <td style="vertical-align:middle;">
                                                ${goods.student.qq}
                                            </td>
                                            <td style="vertical-align:middle;">
                                                ${goods.student.mobile}
                                            </td>
                                            <td style="vertical-align:middle;">
                                                ${goods.sellPrice}元
                                            </td>
                                            <td style="vertical-align:middle;">
                                                <#if goods.status == 1>正在出售
                                                <#elseif goods.status == 2>下架
                                                <#else>已出售
                                                </#if>
                                            </td>
                                            <td style="vertical-align:middle;">
                                                <a href="javascript:void(0)" onClick="delWish(${goods.id})" target="_top">
                                                    <span class="enshrine_it  make_edition" style="margin-right:30px; color: red">删除</span>
                                                </a>
                                            </td>
                                        </tr>
                                    </#list>
                                <#else>
                                    <tr align="center"><td colspan="11">这里空空如也！</td></tr>
                                </#if>
                                </tbody>
                            </table>
                        </div>
                        <#if pageBean.total gt 0>
                            <ul class="pagination ">
                                <#if pageBean.currentPage == 1>
                                    <li class="disabled"><span>«</span></li>
                                <#else>
                                    <li><a href="list?currentPage=1">«</a></li>
                                </#if>
                                <#list pageBean.currentShowPage as showPage>
                                    <#if pageBean.currentPage == showPage>
                                        <li class="active"><span>${showPage}</span></li>
                                    <#else>
                                        <li><a href="list?currentPage=${showPage}">${showPage}</a></li>
                                    </#if>
                                </#list>
                                <#if pageBean.currentPage == pageBean.totalPage>
                                    <li class="disabled"><span>»</span></li>
                                <#else>
                                    <li><a href="list?currentPage=${pageBean.totalPage}">»</a></li>
                                </#if>
                                <li><span>共${pageBean.totalPage}页,${pageBean.total}条数据</span></li>
                            </ul>
                        </#if>
                    </div>
                </div>
        </div>
    </div>
    </div>
</div>
<div class="return-to-top"><a href="#"></a></div><!--返回顶部-->
<#include "../common/right_menu.ftl"/>
<#include "../common/bottom_footer.ftl"/>
<script  src="/home/js/jquery-3.1.1.min.js"></script>
<script src="/home/js/common.js"></script>
<script src="/home/js/user_center.js"></script>
<script src="/home/js/add.js"></script>
<script>
    function delWish(id){
        if (!confirm('删除后不可恢复，确定要删除？')) {
            return;
        }
        jaxRequest('delete','post',{id:id},function(){
            alert('删除成功');
            location.reload();
        });
    }

</script>
</body>
</html>
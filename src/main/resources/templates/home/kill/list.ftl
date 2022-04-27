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
        <!-- 页面显示部分-->
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <div class="card-body">
                        <div class="panel panel-default">
                            <div class="table-responsive">
                                <table class="table table-bordered">
                            <div class="panel-heading text-center">
                                <h1>秒杀活动</h1>
                            </div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <td>图片</td>
                            <td>名称</td>
                            <td>开始时间</td>
                            <td>结束时间</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#if killProducts.content?size gt 0>
                            <#list killProducts.content as kill_product>

                                <tr>
                                    <td style="vertical-align:middle;">
                                        <#if kill_product.goodsPhoto??>
                                            <#if kill_product.goodsPhoto?length gt 0>
                                                <img src="/photo/view?filename=${kill_product.goodsPhoto}" width="80px" height="80px">
                                            </#if>
                                        </#if>
                                    </td>
                                    <td style="vertical-align:middle;">${kill_product.getGoodsName()}</td>
                                    <td style="vertical-align:middle;" style="vertical-align:middle;"><font class="text-success">${kill_product.startTime}</td>
                                    <td style="vertical-align:middle;" style="vertical-align:middle;"><font class="text-success">${kill_product.endTime}</td>
                                    <td>
                                        <a  onClick="kill(${kill_product.id})" target="_top">
                                            <span class="btn btn-info">秒杀</span>
                                        </a>
                                    </td>
                                </tr>
                            </#list>
                        <#else>
                            <tr align="center"><td colspan="9">这里空空如也！</td></tr>
                        </#if>
                        </tbody>
                    </table>
                </div>

                                </table>
                            </div>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="return-to-top"><a href="#"></a></div><!--返回顶部-->
<#include "../common/bottom_footer.ftl"/>
<script  src="/home/js/jquery-3.1.1.min.js"></script>
<script src="/home/js/common.js" type=""></script>
<script type="text/javascript">
function kill(id){
    <#if !ylrc_student?? >
    alert("请您先登录");
    window.location.href="/home/index/login;
    </#if>
    $.ajax({
        url: '/home/kill/kill_product',
        method: 'post',
        data:{id:id},
        dataType:'json',
        success:function (data){
            if (data.code == 0){
                alert(' 秒杀成功！ ');
            }else {
                alert(data.msg);
            }
        },
        error:function (){
            alert('网络出现错误！');
        }
    });
}
</script>
</body>
</html>
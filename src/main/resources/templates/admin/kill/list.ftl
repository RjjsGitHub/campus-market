<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|秒杀管理-${title!""}</title>
<#include "../common/header.ftl"/>
<style>
td{
	vertical-align:middle;
}
</style>
</head>
  
<body>
<div class="lyear-layout-web">
  <div class="lyear-layout-container">
    <!--左侧导航-->
    <aside class="lyear-layout-sidebar">
      
      <!-- logo -->
      <div id="logo" class="sidebar-header">
        <a href="index.html"><img src="/admin/images/sxlogo.png" title="${siteName!""}" alt="${siteName!""}" /></a>
      </div>
      <div class="lyear-layout-sidebar-scroll"> 
        <#include "../common/left-menu.ftl"/>
      </div>
      
    </aside>
    <!--End 左侧导航-->
    
    <#include "../common/header-menu.ftl"/>
    
    <!--页面主要内容-->
    <main class="lyear-layout-content">


      <div class="container-fluid">

        <div class="row">
          <div class="col-lg-12">
            <div class="card">
              <div class="card-toolbar clearfix">
                <form class="pull-right search-bar" method="get" action="list" role="form">
                  <div class="input-group">
                    <div class="input-group-btn">
                      <button class="btn btn-default dropdown-toggle" id="search-btn" data-toggle="dropdown" type="button" aria-haspopup="true" aria-expanded="false">
                        名称 <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">名称</a> </li>
                      </ul>
                    </div>
                    <input type="text" class="form-control" value="${name!""}" name="name" placeholder="请输入名称">
                    <span class="input-group-btn">
                      <button class="btn btn-primary" type="submit">搜索</button>
                    </span>
                  </div>
                </form>
                <#include "../common/third-menu.ftl"/>
              </div>
              <div class="card-body">
                <div class="table-responsive">
                  <table class="table table-bordered">
                    <thead>
                    <tr>
                      <th>
                        <#--                          <label class="lyear-checkbox checkbox-primary">-->
                        <#--                            <input type="checkbox" id="check-all"><span></span>-->
                        <#--                          </label>-->
                      </th>
                      <th>物品</th>
                      <th>名称</th>
                      <th>用户</th>
                      <th>状态</th>
                      <th>开始时间</th>
                      <th>结束时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if pageBean.content?size gt 0>
                      <#list pageBean.content as killproduct>
                        <tr>
                          <td style="vertical-align:middle;">
                            <label class="lyear-checkbox checkbox-primary">
                              <input type="checkbox" name="ids[]" value="${killproduct.id}"><span></span>
                            </label>
                          </td>
                          <td style="vertical-align:middle;">
                            <#if killproduct.icon??>
                              <#if killproduct.icon?length gt 0>
                                <img src="/photo/view?filename=${killproduct.}" width="30px" height="30px">
                              <#else>
                                <img src="/admin/images/default-category-icon.png" width="30px" height="30px">
                              </#if>
                            </#if>
                          </td>
                          <td style="vertical-align:middle;">${killproduct.name}</td>
                          <td style="vertical-align:middle;">
                            <#if killproduct.parent??>
                              ${killproduct.parent.name}
                            </#if>
                          </td>
                          <td style="vertical-align:middle;" align="center">${killproduct.sort}</td>
                          <td style="vertical-align:middle;" style="vertical-align:middle;"><font class="text-success">${killproduct.createTime}</font></td>
                        </tr>
                      </#list>
                    <#else>
                      <tr align="center"><td colspan="6">这里空空如也！</td></tr>
                    </#if>
                    </tbody>
                  </table>
                </div>
                <#if pageBean.total gt 0>
                  <ul class="pagination ">
                    <#if pageBean.currentPage == 1>
                      <li class="disabled"><span>«</span></li>
                    <#else>
                      <li><a href="list?name=${name!""}&currentPage=1">«</a></li>
                    </#if>
                    <#list pageBean.currentShowPage as showPage>
                      <#if pageBean.currentPage == showPage>
                        <li class="active"><span>${showPage}</span></li>
                      <#else>
                        <li><a href="list?name=${name!""}&currentPage=${showPage}">${showPage}</a></li>
                      </#if>
                    </#list>
                    <#if pageBean.currentPage == pageBean.totalPage>
                      <li class="disabled"><span>»</span></li>
                    <#else>
                      <li><a href="list?name=${name!""}&currentPage=${pageBean.totalPage}">»</a></li>
                    </#if>
                    <li><span>共${pageBean.totalPage}页,${pageBean.total}条数据</span></li>
                  </ul>
                </#if>
              </div>
            </div>
          </div>

        </div>

      </div>
      
    </main>
    <!--End 页面主要内容-->
  </div>
</div>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
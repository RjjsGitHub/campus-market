<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|秒杀发布-</title>
<#include "../common/header.ftl"/>

</head>
  
<body>
<div class="lyear-layout-web">
  <div class="lyear-layout-container">
    <!--左侧导航-->
    <aside class="lyear-layout-sidebar">
      
      <!-- logo -->
      <div id="logo" class="sidebar-header">
        <a href="index.html"><img src="/static/admin/images/sxlogo.png" title="${siteName!""}" alt="${siteName!""}" /></a>
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
              <div class="card-header"><h4>添加秒杀</h4></div>
              <div class="card-body">
                <form action="add_kill.ftl" id="kill-add-form" method="post" class="row">
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">秒杀物品</span>
                    <input type="text"  class="form-control" id="goods" name="goods_name" value="${goods.name}"/>
                    <li class="col-xs-4 col-sm-3 col-md-2">
                      <figure>
                          <img src="/photo/view?filename=${goods.photo}" id="goods_photo" name="goods_photo">
                      </figure>
                    </li>
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">开始时间</span>
                    <input type="datetime-local" class="form-control required" id="start_time" name="start_time" value=""/>`
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">结束时间</span>
                    <input type="datetime-local" class="form-control" id="end_time" name="end_time" value="" />
                  </div>
                  <div class="form-group col-md-12">
                    <button type="submit" class="btn btn-primary ajax-post" id="add-form-submit-btn">确 定</button>
                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                  </div>
                </form>
       
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
<script type="text/javascript" src="/static/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/static/admin/js/main.min.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
    //提交按钮监听事件
    $("#add-form-submit-btn").click(function(){
      if(!checkForm("kill-add-form")){
        return;
      }
      var data = $("#kill-add-form").serialize();
      $.ajax({
        url:'add_kill',
        type:'POST',
        data:data,
        dataType:'json',
        success:function(data){
          if(data.code == 0){
            showSuccessMsg('发布成功!',function(){
              window.location.href = 'list';
            })
          }else{
            showErrorMsg(data.msg);
          }
        },
        error:function(data){
          alert('网络错误!');
        }
      });
    });
  });
$("#goods").attr("disabled","disabled");
</script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
<script type="text/javascript">

	$(function(){
		$("#xp").click(function () {
			$("input[name=xz]").prop("checked",this.checked);
		});
		$("#customerBody").on("click",$("input[name=xz]"),function () {
			$("#xp").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);
		})
        $(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});
		$(".time1").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "top-left"
		});
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });

		$("#addBut").click(function () {
			$.ajax({
				url:"workbench/customer/getUserList.do",
				type:"get",
				dataType:"json",
				success:function (data) {
					var html='<option></option>';
					$.each(data,function (i,n) {
						html+='<option value="'+n.id+'">'+n.name+'</option>';
					});
					$("#create-owner").html(html);
					$("#create-owner").val('${user.id}');
                    $("#createCustomerModal").modal("show");
				}
			});

		});
		$("#saveBtn").click(function () {
            $.ajax({
                url:"workbench/customer/save.do",
                data:{
                    "owner":$.trim($("#create-owner").val()),
                    "banquetDate":$.trim($("#create-banquetDate").val()),
                    "banquetVenue":$.trim($("#create-banquetVenue").val()),
                    "nature":$.trim($("#create-nature").val()),
                    "name":$.trim($("#create-name").val()),
                    "phone":$.trim($("#create-phone").val()),
                    "childrenName":$.trim($("#create-childrenName").val()),
                    "nPeopleName":$.trim($("#create-nPeopleName").val()),
                    "nPeoplePhone":$.trim($("#create-nPeoplePhone").val()),
                    "website":$.trim($("#create-website").val()),
                    "childrenAddress":$.trim($("#create-childrenAddress").val()),
                    "childrenPhone":$.trim($("#create-childrenPhone").val()),
                    "description":$.trim($("#create-description").val()),
                    "contactSummary":$.trim($("#create-contactSummary").val()),
                    "nextContactTime":$.trim($("#create-nextContactTime").val()),
					"money":$.trim($("#create-money").val()),
					"deposit":$.trim($("#create-deposit").val()),
                    "address":$.trim($("#create-address1").val())
                },
                type:"post",
                dataType:"json",
                success:function (data) {
                    if(data.success){
                        $("#createCustomerModal").modal("hide");
                        $("#createModal")[0].reset();
						pageList(1,$("#customerPage").bs_pagination('getOption', 'rowsPerPage'));

                    }else{
                        alert("添加失败,数据库中已有该数据。请到修改页通过手机号查询并修改该信息！");
                        $("#createCustomerModal").modal("hide");
                    }
                }
            });
        });
		pageList(1,2);
		$("#searchBtn").click(function () {
			$("#hidden-name").val($.trim($("#search-name").val()));
			$("#hidden-owner").val($.trim($("#search-owner").val()));
			$("#hidden-phone").val($.trim($("#search-phone").val()));
			$("#hidden-startDate").val($.trim($("#search-startDate").val()));
			$("#hidden-endDate").val($.trim($("#search-endDate").val()));
			pageList(1,$("#customerPage").bs_pagination('getOption', 'rowsPerPage'));
		});
		$("#editBtn").click(function () {
			if($("input[name=xz]:checked").length==0){
				alert("请选中要修改的记录");
			}else if($("input[name=xz]:checked").length>1){
				alert("不可选中多条记录");
			}else{
				$.ajax({
					url:"workbench/customer/getUserListAndCustomer.do",
					data:{
						"id":$("input[name=xz]:checked").val()
					},
					type:"get",
					dataType:"json",
					success:function (data) {
						var html='<option></option>';
						$.each(data.userList,function (i,n) {
							html+='<option value="'+n.id+'">'+n.name+'</option>';
						});
						$("#edit-owner").html(html);
						$("#hidden-userId").val(data.cus.id);
						$("#edit-owner").val("${user.id}");
						$("#edit-name").val(data.cus.name);
						$("#edit-website").val(data.cus.website);
						$("#edit-phone").val(data.cus.phone);
						$("#edit-description").val(data.cus.description);
						$("#edit-contactSummary").val(data.cus.contactSummary);
						$("#edit-nextContactTime").val(data.cus.nextContactTime);
						$("#edit-address").val(data.cus.address);
						$("#editCustomerModal").modal("show");

					}
				});
			}


		});
		$("#updateBtn").click(function () {
			$.ajax({
				url:"workbench/customer/update.do",
				data:{
			"id":$.trim($("#hidden-userId").val()),
			"owner":$.trim($("#edit-owner").val()),
			"name":$.trim($("#edit-name").val()),
			"website":$.trim($("#edit-website").val()),
			"phone":$.trim($("#edit-phone").val()),
			"description":$.trim($("#edit-description").val()),
			"contactSummary":$.trim($("#edit-contactSummary").val()),
			"nextContactTime":$.trim($("#edit-nextContactTime").val()),
			"address":$.trim($("#edit-address").val())
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					if(data.success){
						$("#editCustomerModal").modal("hide");
						$("#updateModal")[0].reset();
						pageList($("#customerPage").bs_pagination('getOption', 'currentPage')
								,$("#customerPage").bs_pagination('getOption', 'rowsPerPage'));
					}else{
						alert("更新失败");
						$("#editCustomerModal").modal("hide");
					}
				}
			});
		});
		$("#deleteBtn").click(function () {
			alert("客户信息禁止删除，如有疑问请联系管理员");
		});
	});
	function pageList(pageNo,pageSize) {
		$("#xp").prop("checked",false);
		$("#search-name").val($.trim($("#hidden-name").val()));
		$("#search-owner").val($.trim($("#hidden-owner").val()));
		$("#search-phone").val($.trim($("#hidden-phone").val()));
		$("#search-startDate").val($.trim($("#hidden-startDate").val()));
		$("#search-endDate").val($.trim($("#hidden-endDate").val()));
		$.ajax({
			url:"workbench/customer/pageList.do",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"name":$.trim($("#search-name").val()),
				"owner":$.trim($("#search-owner").val()),
				"phone":$.trim($("#search-phone").val()),
				"startDate":$.trim($("#search-startDate").val()),
				"endDate":$.trim($("#search-endDate").val())
			},
			type:"get",
			dataType:"json",
			success:function (data) {
				var html='';
				$.each(data.dataList,function (i,n) {
				html+='<tr class="active">';
				html+='<td><input name="xz" type="checkbox" value="'+n.id+'" /></td>';
				html+='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/customer/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
				html+='<td>'+n.owner+'</td>';
				html+='<td>'+n.phone+'</td>';
				html+='<td>'+n.banquetDate+'</td>';
				html+='<td>'+n.banquetVenue+'</td>';
				html+='</tr>';
				});
				$("#customerBody").html(html);
				var totalPages=data.total%pageSize==0?data.total/pageSize:(parseInt(data.total/pageSize))+1;
				$("#customerPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});
			}
		});
	}
</script>
</head>
<body>
	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-phone"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>
	<input type="hidden" id="hidden-userId"/>
	<!-- 创建客户的模态窗口 -->
	<div class="modal fade" id="createCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建客户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="createModal" role="form">
					
						<div class="form-group">
							<label for="create-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
								  <%--<option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>--%>
								</select>
							</div>
							<label for="create-customerName" class="col-sm-2 control-label">宴会日期<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-banquetDate">
							</div>
                        </div>
                            <div class="form-group">
                                <label for="create-customerName" class="col-sm-2 control-label">宴会地点<span style="font-size: 15px; color: red;">*</span></label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="create-banquetVenue">
                                </div>
                            <label for="create-customerIdentity" class="col-sm-2 control-label">宴会性质<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="create-nature">
                                    <option value="hy">婚宴</option>
                                    <option value="hm">回门</option>
                                    <option value="zs">周岁</option>
                                    <option value="se">十二</option>
                                </select>
                            </div>

						</div>
						
						<div class="form-group">
                            <label for="create-website" class="col-sm-2 control-label">主办人姓名</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
							<label for="create-phone" class="col-sm-2 control-label">主办人电话</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
						</div>
						<div class="form-group">
							<label for="create-website" class="col-sm-2 control-label">宴会金额</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-money">
							</div>
							<label for="create-phone" class="col-sm-2 control-label">宴会订金</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-deposit">
							</div>
						</div>
                        <div class="form-group">
                            <label for="create-website" class="col-sm-2 control-label">子/女姓名</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-childrenName">
                            </div>
                            <label for="create-phone" class="col-sm-2 control-label">子/女电话</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-childrenPhone">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="create-website" class="col-sm-2 control-label">新郎/娘姓名</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-nPeopleName">
                            </div>
                            <label for="create-phone" class="col-sm-2 control-label">新郎/娘电话</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-nPeoplePhone">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="create-website" class="col-sm-2 control-label">电子邮箱</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-website">
                            </div>
                            <label for="create-phone" class="col-sm-2 control-label">子女工作城市</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-childrenAddress">
                            </div>
                        </div>

						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control  time1" id="create-nextContactTime">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="create-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address1"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改客户的模态窗口 -->
	<div class="modal fade" id="editCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改客户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="updateModal" role="form">
					
						<div class="form-group">

							<label for="edit-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
								  <%--<option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>--%>
								</select>
							</div>
							<label for="edit-customerName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-name">
							</div>
						</div>
						<div class="form-group">
                            <label for="edit-website" class="col-sm-2 control-label">个人邮箱</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-website">
                            </div>
							<label for="edit-phone" class="col-sm-2 control-label">电话号码</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="create-contactSummary1" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="edit-contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="create-nextContactTime2" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="edit-nextContactTime">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>客户列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" id="search-name" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" id="search-owner" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">电话号码</div>
				      <input class="form-control" id="search-phone" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
				      <input class="form-control" id="search-startDate" type="text">
				    </div>
				  </div>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">结束日期</div>
							<input class="form-control" id="search-endDate" type="text">
						</div>
					</div>
				  
				  <button type="button" id="searchBtn" class="btn btn-default">查询</button>
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBut"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input id="xp" type="checkbox" /></td>
							<td>名称</td>
							<td>所有者</td>
							<td>电话号码</td>
							<td>宴会时间</td>
							<td>宴会地点</td>
						</tr>
					</thead>
					<tbody id="customerBody">
						<%--<tr>
							<td><input type="checkbox"/></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/customer/detail.jsp';">动力节点</a></td>
							<td>zhangsan</td>
							<td>010-84846003</td>
							<td>http://www.bjpowernode.com</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/customer/detail.jsp';">动力节点</a></td>
                            <td>zhangsan</td>
                            <td>010-84846003</td>
                            <td>http://www.bjpowernode.com</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="customerPage"></div>
				<%--<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>--%>
			</div>
			
		</div>
		
	</div>
</body>
</html>
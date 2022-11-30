<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>SeoDocmall | QR코드 생성</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	
  <!--Admin LTE CSS 파일들.  -->
  <%@ include file="/WEB-INF/views/admin/include/plugin1.jsp" %>
  
</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

<!--Header 부분 include  -->
<%@ include file="/WEB-INF/views/admin/include/header.jsp" %>

<!--aside 사이드바 부분 include 사이드바 --> 
<%@ include file="/WEB-INF/views/admin/include/aside.jsp" %>

 <!--MAIN  -->
 
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        	QR코드 생성
        <small>QR코드 생성</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="/admin/admain"><i class="fa fa-dashboard"></i>메인</a></li>
        <li class="active">QR코드 생성</li>
      </ol>
    </section>
    <!-- Main list -->
    <section class="content container-fluid">
    	<div id="container_box">
    		<h2>QR코드 생성</h2>
 		
		<form id="QRcreate">
		<!-- 	<li>FileName : <input type="text" name="name"></li>-->	
				<h4>QR 코드를 생성할 URL을 입력하세요 : <input type="text" name="content"></h4>
				<!-- button에 function 불러오기 -->
				<button type="button" name="qrcreate" class="btn btn-primary">QR코드생성</button>		
		</form>
 			
		<img id="change_img" style="width: 200px; height:200px; ">
		${qrsrc}
 		  
    	</div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

 <!-- Main Footer 부분 include -->
<%@ include file="/WEB-INF/views/admin/include/footer.jsp" %>

  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
  immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 3 javaScript 문법추가 부분 include -->
<%@ include file="/WEB-INF/views/admin/include/plugin2.jsp" %>

<script>
	$(document).ready(function() {
		$("button[name='qrcreate']").on("click", function (){ 
			console.log("QR코드생성 버튼 클릭됨.");
			let formData = $("#QRcreate").serialize(); //formData 한번에 받기
			alert(formData);

			let fileName = "";

			$.ajax({
				async: true,
				url: "/admin/qrcode/qrcreate",
				type: "POST",
				data: formData, 
				success: function(data){
						alert("QR코드가 생성되었습니다.");
						console.log(data);
						fileName = data;
						/* 이미지를 출력하는 부분 img src */
					//$("#qrimg").attr("src", "http://localhost:9090/admin/qrcode/qrimg?fileName="+ fileName );
					},
				error: function(xhr, textStatus, errorThrown){
					alert("QR코드가 생성되었습니다.");
				}
			});
		});
		
		// 이미지 미리보기
		$("button[name='qrcreate']").on("change", function(e) {
			let file = e.target.files[0];

			let reader = new FileReader();

			reader.onload = function (e) {
				$("#change_img").attr("src", e.target.result);
			}

			reader.readAsDataURL(file);

		});
		
		
		
	});


</script>

</body>
</html>
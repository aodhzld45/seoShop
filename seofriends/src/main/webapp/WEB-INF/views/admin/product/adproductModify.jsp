<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
    
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Docmall | 상품수정</title>
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
        Page Header
        <small>Optional description</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
        <li class="active">Here</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">
    
    <div class="row">
    	<div class="col-md-12">
    		<div class="box box-primary">
    			<div class="box-header">
    				MODIFY  PRODUCT
    			</div>
    			 <form id="productModifyForm"  action="AdProductModify" method="post" enctype="multipart/form-data">
    			<div class="box-body">
    			<!-- 상품 등록 폼 구성 -->
    				
					  <div class="form-group row">
					  
					  <!--페이징 및 검색 정보 (Criteria클래스의 필드)? 원래상태의 리스트에서 페이징 정보 -->
					  <input type="hidden" name="pageNum" value="${cri.pageNum}"  >
					  <input type="hidden" name="amount" value="${cri.amount}"  >
					  <input type="hidden" name="type" value="${cri.type}"  >
					  <input type="hidden" name="keyword" value="${cri.keyword}"  >
					  
					  <label for="pdt_name" class="col-sm-2 col-form-label">카테고리</label>
					  	<div class="col-sm-3">
					  		<select name="catecoderef"  id="firstCategory" class="form-control">
					  			<option value="">1차 카테고리 선택</option>
					  			<c:forEach items="${cateList}" var="categoryVO" >
					  				<option value="${categoryVO.catecode}" ${categoryVO.catecode == productVO.catecoderef ? 'selected':'' }>${categoryVO.catename}</option>
					  			</c:forEach>
					  		</select>
					  	</div>
					  	
					  	<div class="col-sm-3">
					  		<select name="catecode" id="SecondCategory" class="form-control">
					  			<option value="">2차 카테고리 선택</option>
					  				<c:forEach items="${SubCateList}" var="subCategoryVO" >
					  				<option value="${subCategoryVO.catecode}" ${subCategoryVO.catecode == productVO.catecode ? 'selected':'' }>${subCategoryVO.catename}</option>
					  			</c:forEach>
					  		</select>
					  	</div>
				  	</div>
			
					  <!-- 1 -->
					  <div class="form-group row">
					    <label for="pdt_name" class="col-sm-2 col-form-label">상품명</label>
					    <div class="col-sm-4">
					      <input type="hidden" id="pdt_num" name="pdt_num" value="${productVO.pdt_num }" >	
					      <input type="text" class="form-control" id="pdt_name" name="pdt_name" value="${productVO.pdt_name }">
					    </div>
					    
					    <label for="pdt_price" class="col-sm-2 col-form-label">상품가격</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pdt_price" name="pdt_price" value="${productVO.pdt_price }">
					    </div>
					  </div>
					  <!-- 2 -->
					  <div class="form-group row">
					    <label for="pdt_discount" class="col-sm-2 col-form-label">할인율</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pdt_discount" name="pdt_discount" value="${productVO.pdt_discount }">
					    </div>
					    
					    <label for="pdt_company" class="col-sm-2 col-form-label">제조사</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pdt_company" name="pdt_company" value="${productVO.pdt_company }">
					    </div>
					  </div>
				
					<!--상품 이미지 업로드  -->
					  <div class="form-group row">
					    <label for="uploadFile" class="col-sm-2 col-form-label">상품 이미지</label>
					    <div class="col-sm-10"> <!-- name="pdt_img" 존재하지 않음 실제 업로드한 이미지 이름이 들어감. -->
					      <input type="file"  class="form-control" id="uploadFile" name="uploadFile"  >
					      
					      <!--날짜 폴더, 상품 이미지 파일명.   -->
					      <input type="hidden" id="pdt_img_folder" name="pdt_img_folder" value="${productVO.pdt_img_folder }">
					      <input type="hidden" id="pdt_img" name="pdt_img" value="${productVO.pdt_img }">   
					    </div>
					  </div>
					  
					  <!-- 상품 이미지 보기  -->
					  <div class="form-group row">
					    <label for="pdt_img" class="col-sm-2 col-form-label">현재 이미지</label>
					    <div class="col-sm-4"> <!-- name="pdt_img" 존재하지 않음 실제 업로드한 이미지 이름이 들어감. -->
					    	<img src="/admin/product/dlsplayFile?folderName=${productVO.pdt_img_folder}&fileName=${productVO.pdt_img}" alt="이미지준비" id="cur_img">
					  	</div>
					 
					    <label for="pdt_img" class="col-sm-2 col-form-label">변경 이미지</label>
					  <div class="col-sm-4"> <!-- name="pdt_img" 존재하지 않음 실제 업로드한 이미지 이름이 들어감. -->
					    	<img id="change_img" style="width: 200px; height: 200px; ">
					  </div>
					 </div> 
					  
					  <!--상품 설명  -->
					  <div class="form-group row">
					    <label for="pdt_detail" class="col-sm-2 col-form-label">상품 설명</label>
					    <div class="col-sm-10">
							<textarea class="form-control" id="pdt_detail" name="pdt_detail" rows="3" >${productVO.pdt_detail}</textarea>
					    </div>
					  </div>
					  <!-- 3 -->
					<div class="form-group row">
					    <label for="pdt_amount" class="col-sm-2 col-form-label">수량</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pdt_amount" name="pdt_amount" value="${productVO.pdt_amount }">
					    </div>
					    
					    <label for="pdt_buy" class="col-sm-2 col-form-label">판매여부</label>
					    <div class="col-sm-4">
					      <select  id="pdt_buy" name="pdt_buy" >
					      	<option value="Y" ${productVO.pdt_buy eq 'Y' ? 'selected':'' }>판매가능</option>
					      	<option value="N" ${productVO.pdt_buy eq 'N' ? 'selected':'' }>판매불가능</option>
					      </select>
					    </div>
					  </div>
					  
    			</div>
    			<div class="box-footer">
    				<div class="form-group">
    					<ul class="uploadedList"></ul>
    				</div>
    			  	<div class="from-group row">
    					<div class="col-sm12 text-center">
    						<button type="submit" class="btn btn-primary" id="btnModify">상품수정</button>
    						<button type="button" class="btn btn-info" id="btnCancel">취소</button>
    					</div>
    				</div>
    		
    			</div>
    			</form>
    		</div>
    	
    	</div> 
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

<!-- jQuery 3 javaScript 부분 include -->
<%@ include file="/WEB-INF/views/admin/include/plugin2.jsp" %>

<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
<script>
	$(document).ready(function() {

		// ckeditor 환경설정.
		var ckeditor_config = {
			resize_enabled : false,
			enterMode : CKEDITOR.ENTER_BR,
			shiftEnterMode : CKEDITOR.ENTER_P,
			toolbarCanCollapse : true,
			removePlugins : "elementspath", 
			filebrowserUploadUrl: '/admin/product/imageUpload' //업로드 탭기능추가 속성
		}

		CKEDITOR.replace("pdt_detail", ckeditor_config);

/////////////////////////////////////////////////////////////////////////////////////////////////

		// 1차카테고리 선택시
		$("#firstCategory").on("change", function() {

			let firstCategoryCode = $(this).val();

			//console.log("1차 카테고리 코드 : " + firstCategoryCode);

			let url = "/admin/product/subCategoryList/" + firstCategoryCode;

			$.getJSON(url, function(subCategoryList) {

				// console.log(subCategoryList.length);
				// console.log("첫번째 데이터: " + subCategoryList[0].catecode);
				// console.log("첫번째 데이터: " + subCategoryList[0].catrcoderef);
				// console.log("첫번째 데이터: " + subCategoryList[0].catrname);
				console.log("로그로그")
				

				// 2차 카테고리 태그를 참조.
				let optionStr = "";
				let SecondCategory = $("#SecondCategory");

				//기존 카테고리에 의하여 출력되는 요소를 제거.
				SecondCategory.find("option").remove();
				SecondCategory.append("<option>2차 카테고리 선택</option>");
				

				for(let i = 0; i<subCategoryList.length; i++){
					optionStr += "<option value='" + subCategoryList[i].catecode + "'>" + subCategoryList[i].catename + "</option>";
				}

				SecondCategory.append(optionStr);

			});
		});

///////////////////////////////////////////////////////////////////////////////////////////////
		// 취소버튼시 상품목록으로 이동.
		$("#btnCancel").on("click", function() {
			location.href= "/admin/product/productlist";
		});

		// 이미지 미리보기
		$("#uploadFile").on("change", function(e) {
			let file = e.target.files[0];

			let reader = new FileReader();

			reader.onload = function (e) {
				$("#change_img").attr("src", e.target.result);
			}

			// 첨부하고자 하는 파일이 업로드가 성공적으로 동작이되면 
			// 파일을 읽어들인다.
			reader.readAsDataURL(file); 

		});

		
	});


</script>


<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. -->
</body>
</html>
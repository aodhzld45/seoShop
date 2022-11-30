<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
    
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>SeoDocmall | 상품목록</title>
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
        	상품 목록
        <small>관리자 상품목록</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="/admin/admain"><i class="fa fa-dashboard"></i>메인</a></li>
        <li class="active">상품목록</li>
      </ol>
    </section>
    <!-- Main list -->
    <section class="content container-fluid">
    	<div id="container_box">
    		<h2>상품 목록</h2>
	<form id="searchForm" action="/admin/product/productlist" method="get">
	  <select name="type">
		  <option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }" />>--</option>
		  <option value="N" <c:out value="${pageMaker.cri.type eq 'N' ? 'selected' : '' }" />>상품명</option><!-- PDT_NAME  -->
		  <option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : '' }" />>제조사</option><!-- PDT_COMPANY  -->
		  <option value="NC" <c:out value="${pageMaker.cri.type eq 'NC' ? 'selected' : '' }" />>상품명 or 제조사</option><!-- PDT_NAME or PDT_COMPANY -->
	  </select>
	  <input type="text" name="keyword" value="${pageMaker.cri.keyword }">
	  <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
	  <input type="hidden" name="amount" value="${pageMaker.cri.amount }">
	  <button type="button" id="btnSearch" class="btn btn-link">검색</button>
  </form>	
  
   <table class="table table-hover">
 	<!--제목행.  -->
  <thead class="thead-dark">
    <tr>
      <th scope="col">상품번호</th>
      <th scope="col">상품이미지</th>
      <th scope="col">상품명</th>
      <th scope="col">가격</th>
      <th scope="col">등록일</th>
      <th scope="col">판매여부</th>
      <th scope="col">수정</th>
      <th scope="col">삭제</th>
    </tr>
  </thead>	
    
    <tbody>
	  <c:forEach items="${productList}" var="productVO">
	  <!--productVO클래스의 필드명으로 코딩했지만, 호출은 getter메서드가 사용됨.   -->
	    <tr>
	      <td scope="row"><c:out value="${productVO.pdt_num}" /> </td>
	      
	      <td>
	      	<img src="/admin/product/dlsplayFile?folderName=${productVO.pdt_img_folder}&fileName=s_${productVO.pdt_img}" alt="이미지준비" onerror="this.onerror=null; this.src='/image/no_img.jpg'" />
	      </td> 
	   
	      <td>
	      	<a class="move" href="${productVO.pdt_num} "><c:out value="${productVO.pdt_name}" escapeXml="true" /></a>
	      </td>
	      
	      <td><c:out value="${productVO.pdt_price}" /></td>
	      
	      <td><fmt:formatDate value="${productVO.pdt_date_sub}" pattern="yyyy-MM-dd hh:mm" /></td>

	      <td><c:out value="${productVO.pdt_buy}" /></td>
	      
	       <!-- 태그 data-이름="값." -->
	      <td><button type="button" name="btnProductEdit" data-pdt_num="${productVO.pdt_num}" class="btn btn-primary">수정</button></td>
	      <td>
			<input type="hidden" name="pdt_img_folder" value="${productVO.pdt_img_folder}">
			<input type="hidden" name="pdt_img" value="${productVO.pdt_img}">
			<button type="button" name="btnProductDelete" data-pdt_num="${productVO.pdt_num}" class="btn btn-danger">삭제</button>
		 </td>
	    </tr>
	  </c:forEach>
	   </tbody>
	</table><br>  

		<button type="button" id="btn_productInsert" class="btn btn-info">상품 등록</button>

		<!-- 페이징 추가 부분.-->
		<nav aria-label="...">
		<ul class="pagination">
		  
			<!-- 이전 버튼 표시 -->
			<c:if test="${pageMaker.prev }">
				<li class="page-item"><a class="page-link" href="${pageMaker.startPage - 1}"><b>이전</b></a></li>
			</c:if> 
		  
		    <!-- 페이지 번호 표시. 1 2 3 4 5  -->
		    <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="num">
		    <li class='page-item ${pageMaker.cri.pageNum == num ? "active": ""}'><a class="page-link" href="${num}">${num}</a></li>
		    </c:forEach>
		    		    
		    <!-- 다음 버튼 표시  -->
		   <c:if test="${pageMaker.next }">
		    <li class="page-item"><a class="page-link" href="${pageMaker.endPage + 1}" ><b>다음</b></a></li>
		  </c:if> 
		    
		</ul>
		  
		  <!--1)페이지 번호 클릭시 /admin/product/productlist 주소로 보낼 파라미터 작업  2)상품 수정 버튼 클릭시 : 수정주소 3)상품 삭제 버튼 클릭시 : 삭제주소-->
		  <form id="productactionForm" name="productactionForm" action="/admin/product/productlist" method="get">
		  			<%-- <input type="hidden" name="pdt_num" value="${productVO.pdt_num}"> --%>
					<input type="hidden" name="pageNum" value="${cri.pageNum}">
					<input type="hidden" name="amount" value="${cri.amount}">
					<input type="hidden" name="type" value="${cri.type}">
					<input type="hidden" name="keyword" value="${cri.keyword}">
		 </form>
		 
		</nav>

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

	// productactionForm 참조 
	// 1)상품수정 2)상품삭제 3)페이지번호
	let productactionForm = $("#productactionForm"); 

	// 상품 등록 폼 이동
    $("#btn_productInsert").on("click", function() {
    	console.log("상품 등록 버튼 클릭");
      	location.href= "/admin/product/adproductInsert";    
    });
////////////////////////////////////////////////////////////////////////////////////////
	
	// 상품 수정 버튼 클릭
	$("button[name='btnProductEdit']").on("click", function() {

		console.log("상품 코드" + $(this).data("pdt_num"));
		//$(this).data("pdt_num");

	// 상품코드를 자식으로 추가
	    productactionForm.append("<input type='hidden' name='pdt_num' value='" + $(this).data("pdt_num")   + "'>");
		productactionForm.attr("method", "get");
		productactionForm.attr("action", "/admin/product/adproductModify");
		productactionForm.submit();

	});

	// 상품 삭제 버튼 클릭
	$("button[name='btnProductDelete']").on("click", function() {
		//$(this).data("pdt_num");
		
	    if (!confirm( $(this).data("pdt_num") + "번 상품을 삭제하시겠습니까?")) return;
// 		상품 코드 추가
		productactionForm.append("<input type='hidden' name='pdt_num' value='" + $(this).data("pdt_num")   + "'>");
// 		날짜 폴더 추가
// 		delete 버튼 ->td->자식선택자 찾기
		let pdt_img_folder = $(this).parent().children("input[name='pdt_img_folder']").val();
		productactionForm.append("<input type='hidden' name='pdt_img_folder' value='" + $(this).data("pdt_img_folder")   + "'>");
		console.log("pdt_img_folder: " + pdt_img_folder);
	
		let pdt_img = $(this).parent().children("input[name='pdt_img']").val();
		productactionForm.append("<input type='hidden' name='pdt_img' value='" + $(this).data("pdt_img")   + "'>");
		console.log("pdt_img: " + pdt_img);

	    productactionForm.attr("action");
		productactionForm.attr("method", "get");
		productactionForm.attr("action", "/admin/product/AdDeleteProduct");
		  
		productactionForm.submit();
	});
	

	// 페이지 번호 클릭시 동작.  이전	1	2	3	4	5  다음
	$("li.page-item a.page-link").on("click", function(e) {
// 		pageNum 필드는 actionForm에 수동으로 작업되어 있어, 추가하는 것이 아니라, 참조하여 값을 변경한다.
		let pageNum = $(this).attr("href");

		console.log("페이지 번호: " + pageNum);
		e.preventDefault(); // 태그의 기본특성을 제거. <a>태그의 링크기능을 제거.

		productactionForm.find("input[name='pageNum']").val(pageNum);
		productactionForm.attr("action", "/admin/product/adproductlist");
		productactionForm.submit();

		//현재 선택한 페이지번호 변경작업 <input type="hidden" name="pageNum" value="값">
		// let url = "productlist?pageNum=" + $(this).attr("href") + "&amount=10";
		// location.href = url;
		
	});	

/* 	// 목록에서 제목을 클릭시 동작.(페이징 파라미터, 검색 파라미터, 글번호)
	$("a.move").on("click", function(e) {
	// $(this) : $("a.move") 선택자중 클릭된 a 태그
	e.preventDefault();
	let bno = $(this).attr("href");

	actionForm.find("input[name='pdt_num']").remove();

	//<form>태그의 자식으로 추가됨.
	actionForm.append("<input type='hidden' name='pdt_num' value='" + pdt_num + "'>");
	actionForm.attr("action", "/admin/product/productInsert");
	//form 전송 -> submit();
	actionForm.submit();
	}); */

	// 검색 페이지 이동 value값 1 설정
	$("#btnSearch").on("click", function() {
		let searchForm =  $("#searchForm");
		searchForm.find("input[name='pageNum']").val(1); //해당 페이지의 value값을 1로 변경
		searchForm.submit();
	});


});

</script>
<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. -->
</body>
</html>
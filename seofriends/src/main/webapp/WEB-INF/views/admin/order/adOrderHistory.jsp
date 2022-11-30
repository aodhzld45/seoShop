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
  <title>SeoDocmall | 주문 변경 목록</title>
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
        	주문변경목록
        <small>관리자 주문변경목록</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="/admin/admain"><i class="fa fa-dashboard"></i>메인</a></li>
        <li class="active">주문변경목록</li>
      </ol>
    </section>
    <!-- Main list -->
    <section class="content container-fluid">
    	<div id="container_box">
    		<h2>주문 변경 목록</h2>
 <%-- 	<form id="searchForm" action="/admin/order/adOrderList" method="get">
	  <select name="type">
		  <option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }" />>--</option>
		  <option value="O" <c:out value="${pageMaker.cri.type eq 'O' ? 'selected' : '' }" />>주문번호</option><!-- ODR_CODE  -->
		  <option value="M" <c:out value="${pageMaker.cri.type eq 'M' ? 'selected' : '' }" />>주문자ID</option><!-- MEM_ID  -->
		  <option value="OM" <c:out value="${pageMaker.cri.type eq 'OM' ? 'selected' : '' }" />>주문번호 or 주문자ID</option><!-- ODR_CODE or MEM_ID -->
	  </select>
	  <input type="text" name="keyword" value="${pageMaker.cri.keyword }">
	  <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
	  <input type="hidden" name="amount" value="${pageMaker.cri.amount }">
	  
	  주문일자 <input type="date" name="startDate" value="${startDate}"> ~ <input type="date" name="endDate" value="${endDate}"> 
	  <button type="button" id="btnSearch" class="btn btn-primary">검색</button>
	  
  </form> --%>
  <button type="button" name="btnCheckedOrderDelete" class="btn btn-danger">선택삭제</button>


	 
  <table class="table" style="border: 1px solid #444444;">
 	<!-- 제목행. -->
  <thead class="thead-dark">
    <tr class="bg-primary">

      <th scope="col"><input type="checkbox" id="checkAll" name="checkAll" ></th>
      <th scope="col">주문번호</th>
      <th scope="col">주문날짜</th>
      <th scope="col">주문자ID/수령인</th>
      <th scope="col">주소/상세주소</th>
      <th scope="col">총 주문금액</th>   
      <th scope="col">연락처</th>
      <th scope="col">주문상태</th>
      <th scope="col">주문변경날짜</th>
      <th scope="col">변경내용</th>
      
    
    </tr>
  </thead>	
    
    <tbody>
	  <c:forEach items="${adOrderHistory}" var="adOrderHistory">
	  <!--productVO클래스의 필드명으로 코딩했지만, 호출은 getter메서드가 사용됨.   -->

	    <tr>  
	      <td scope="row"><input type="checkbox" class="check" value="${adOrderHistory.odr_code}"> </td>
	      
	    <%--  
	    	 <td>
	      		<img src="/admin/product/dlsplayFile?folderName=${productVO.pdt_img_folder}&fileName=s_${productVO.pdt_img}" alt="이미지준비" onerror="this.onerror=null; this.src='/image/no_img.jpg'" />
	     	 </td>
	    --%>
	    
	    <!-- href="/admin/order/adOrderDetailView" -->
	      <td>
	      	<a class="move" data-odr_code="${adOrderHistory.odr_code}"><c:out value="${adOrderHistory.odr_code}" escapeXml="true" /></a>
	      </td>

	      <td>
	      	<fmt:formatDate value="${adOrderHistory.odr_date}" pattern="yyyy-MM-dd hh:mm" />
	      </td>
	      
 		  <td><c:out value="${adOrderHistory.mem_id }" /> / <c:out value="${adOrderHistory.odr_name }" /></td>	                
 		  
 		  <td><c:out value="${adOrderHistory.odr_addr }" /> / <c:out value="${adOrderHistory.odr_addr_d }" /></td>
	                
	      <td><c:out value="${adOrderHistory.odr_total_price}" /></td>
	
	      <td><c:out value="${adOrderHistory.odr_phone}" /></td>
	      
		  <td><c:out value="${adOrderHistory.odr_status}" /></td>

		   <td>
	      	<fmt:formatDate value="${adOrderHistory.odr_event_date}" pattern="yyyy-MM-dd hh:mm" />
	      </td>	
		  
		  <td><c:out value="${adOrderHistory.event_name}" /></td>
		  
		 
	    </tr>
	  </c:forEach>
	   </tbody>
	</table><br>  
	  <!-- 엑셀다운로드 Form -->
	  <form action="/admin/order/excelDown" id="excelDownForm" name="excelDownForm" method="post" enctype="multipart/form-data">
		<button type="submit" name="btnExcel" class="btn btn-primary" style="float: right;">엑셀다운로드</button>	 
	  </form>

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

		$("a.move").on("click", function (e) {

			e.preventDefault(); // 태그의 기본특성을 제거. <a>태그의 링크기능을 제거.
			let odr_code = $(this).data("odr_code");	
			console.log("주분번호 = " + odr_code);	
			// orderactionForm.find("input[name='pageNum']").val(pageNum);
			// orderactionForm.attr("action", "/admin/order/adOrderDetailView");
			// orderactionForm.submit();

			 let url = "/admin/order/adOrderDetailView?odr_code=" + odr_code;
			 location.href =  url;
		});


		//let excelDownState = false;
		//엑셀 다운로드
	// 	$("button[name='btnExcel']").on("click", function () {
	// 		console.log("엑셀 다운로드 버튼 클릭됨.");
		
	// 		// excelDownForm.attr("action", "/admin/order/excelDown");
	// 		// excelDownForm.submit();

	// 	// 	$.ajax({
	// 	// 		url : '',
	// 	// 		type : '',
	// 	// 		data : {odr_code : odr_code, odr_status : odr_status},
	// 	// 		dataType : 'text',
	// 	// 		success : function (result) {
	// 	// 			if (result == "success") {
					

	// 	// 			}
	// 	// 	}

	// 	// });
	// });

	// 배송상태 정보
	$("button[name='btnChangeOrderStatus']").on("click", function () {

		//주문번호, 선택한 배송상태값.
		let odr_code = $(this).data("odr_code");
		let odr_status = $(this).parent().find("select[name='odr_status'] option:selected").val();
		console.log("주문번호 : " + odr_code );
		console.log("주문상태 : " + odr_status);

			$.ajax({
			url : '/admin/order/orderStatusChange',
			type : 'get',
			data : {odr_code : odr_code, odr_status : odr_status},
			dataType : 'text',
			success : function (result) {
				if (result == "success") {
				alert("배송상태가 변경되었습니다.");

					}
				}

			});
    });

//체크박스 선택. 제목행 체크박스 클릭
let isCheck = true;
    $("#checkAll").on("click", function(){
      $(".check").prop("checked", this.checked); // attr()메서드 사용하면 체크처리 안됨.

      isCheck = this.checked;
    });

    // 데이터행 체크박스 클릭
    $(".check").on("click", function(){
      
      //제목행의 전체선택 체크박스
      $("#checkAll").prop("checked", this.checked);

      //데이터 행의 체크박스의 선택자 해당하는 만큼 동작하는 구문
      $(".check").each(function(){
        if(!$(this).is(":checked")) {
          $("#checkAll").prop("checked", false);
        }
      });
    });

	 //선택삭제
	$("button[name='btnCheckedOrderDelete']").on("click", function(){

		if($(".check:checked").length == 0) {
		alert("삭제할 주문데이타를 체크하세요.");
		return;
		}

		let isOrderDel = confirm("주문상품을 삭제하시겠습니까?");
		if(!isOrderDel) return;

		// 자바스크립트 배열문법
		// 삭제할 주문번호 배열.
		let ordCodeArr = [];

		$(".check:checked").each(function(){
		ordCodeArr.push($(this).val());
		});

		console.log("선택된 주문번호: " + ordCodeArr);

		$.ajax({
		url : '/admin/order/orderCheckedDelete',
		type: 'post',
		dataType : 'text',
		data : {
			ordCodeArr : ordCodeArr
		},
		success : function(result) {
			if(result == "success") {
			alert("선택한 주문정보가 삭제되었습니다.");

			location.href= "/admin/order/adOrderHistory";
			}
		}
	});
});


	// 주문 삭제 버튼 클릭
	$("button[name='btnOdrDelete']").on("click", function() {
		
		let odr_code = $(this).data("odr_code");
		console.log("주문번호 : " +  odr_code);

        if (!confirm( $(this).data("odr_code") + "번 주문내역을 삭제하시겠습니까?")) return;

		let url = "/admin/order/adOrderDelete?odr_code=" + odr_code;
        location.href =  url;
		
	}); 

});

</script>
</body>
</html>
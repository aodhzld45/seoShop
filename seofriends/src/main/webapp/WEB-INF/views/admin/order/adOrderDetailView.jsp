<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
    
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>SeoDocmall | 주문상세목록</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<style>
    
    
 	   
    </style>
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
        	주문상세목록
        <small>관리자 주문상세목록</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="/admin/admain"><i class="fa fa-dashboard"></i>메인</a></li>
        <li class="active">주문상세목록</li>
      </ol>
    </section>
    <!-- Main list -->
    <section class="content container-fluid">
    
  <div id="container_box">
     
    <h2>주문정보</h2>	

	   <table class="table" style="border: 1px solid #444444;">
	  <thead class="thead-dark">
	    <tr class="bg-primary">
	      <th scope="col">주문번호</th>
	      <td scope="col">${orderVO.odr_code }</td>
	    </tr>
	  </thead>
	  <tbody>
	  
  		<tr>
	      <th scope="col">주문일자</th>
	      <td scope="col"><fmt:formatDate value="${orderVO.odr_date}" pattern="yyyy-MM-dd hh:mm" /></td>
	    </tr>
	    
  		<tr>
	     <th scope="col">주문자</th>
	      <td scope="col">${orderVO.odr_name }</td>
	    </tr>
	    
	    <tr>
	     <th scope="col">주소</th>
	      <td scope="col">(${orderVO.odr_zipcode}) ${orderVO.odr_addr} [${orderVO.odr_addr_d}]</td>
	    </tr>
	    
	    <tr>
	     <th scope="col">결제상태</th>
	      <td scope="col">${orderVO.payment_status }</td>
	    </tr>
	    
  		<tr>
	     <th scope="col">주문처리상태</th>
	      <td scope="col">${orderVO.odr_status }</td>
	    </tr>
	    
	    
	  </tbody>
	</table>
	
	 <h2>결제정보</h2>		
	   <table class="table" style="border: 1px solid #444444;">
	  <thead>
	    <tr class="bg-primary">
	      <th scope="col">총 주문금액</th>
	      <td scope="col">${orderVO.odr_total_price }</td>
	    </tr>
	  </thead>
	  <tbody>
	  
  		<tr>
	      <th scope="col">총 할인결제</th>
	      <td scope="col">0</td>
	    </tr>
	    
  		<tr>
	     <th scope="col">총 결제금액</th>
	      <td scope="col">${paymentVO.pay_tot_price }</td>
	    </tr>
	    
  		<tr>
	     <th scope="col">결제수단</th>
	      <td scope="col">${paymentVO.pay_method }</td>
	    </tr>
	    
	  </tbody>
	</table>

	<hr>
	
	<h5>주문상품정보</h5>
	   <table class="table" id="orderlistresult"style="border: 1px solid #444444;">
	  <thead>
	   	<tr class="bg-primary">

	      <th scope="col">상품이미지</th>
	      <th scope="col">주문번호</th>
	      <th scope="col">상품명</th>
	      <th scope="col">수량</th>
	      <th scope="col">상품구매금액</th>
	      <th scope="col">주문처리상태</th>
	      <th scope="col">취소/교환/반품</th>
	      <th scope="col">개별상품삭제</th>

	      
	    </tr>
	  </thead>
	   <tbody>
		  <c:forEach items="${orderProductMap }" var="orderProduct">
		    <tr> <!-- MAP의 키를 대문자 사용해야 함. -->		    
		     <td>
		     <!-- <resultMap> 사용시    -->
		     <!--이미지 // -> \\ 변환작업 에서 p_imgfolder의 값을 mapper 파일의 property값을 명시해줘야함   -->
	      		<img src="/admin/product/dlsplayFile?folderName=${orderProduct.p_imgfolder}&fileName=s_${orderProduct.p_img}" alt="이미지준비" onerror="this.onerror=null; this.src='/image/no_img.jpg'" />
	     	 </td>
	     	 	
	     	  <td scope="col">${orderProduct.ODR_CODE }</td>	
		      <td scope="col">${orderProduct.p_name }</td>
		      <td scope="col">
		            <input type="hidden" name="pdt_price" value='<c:out value="${orderProduct.ODR_PRICE}" />'> 
		      		<input type="number" class="w-25" name="odr_amount" value='<c:out value="${orderProduct.ODR_AMOUNT }" />'> 
		      	    <button type="button" name="btnOrderDetailAmountChange" data-odr_code="${orderProduct.ODR_CODE  }" class="btn btn-primary">수량변경Ajax</button>
		      </td>
		      
		      <td scope="col">
		      	<span class="unitprice"><fmt:formatNumber type="number" maxFractionDigits="3">${orderProduct.ODR_PRICE}</fmt:formatNumber></span>
		      </td>
		      
		      <td scope="col">${orderProduct.ODR_STATUS }</td>
		      <td scope="col">
				<select name="cs_status">
					<option value="상품취소" ${orderProduct.cs_status eq '상품취소'? 'selected':''}>상품취소</option>
					<option value="상품교환" ${orderProduct.cs_status eq '상품교환'? 'selected':''}>상품교환</option>
					<option value="상품반품" ${orderProduct.cs_status eq '상품반품'? 'selected':''}>상품반품</option>
				</select>
				<button type="button" name="btnChangeCs_status" data-odr_code="${orderProduct.ODR_CODE}" class="btn btn-primary">변경</button>
			  </td>
			  
			   <td>
			   		<button type="button" name="btnUnitCancle" data-odr_code="${orderProduct.ODR_CODE}" data-pdt_num="${orderProduct.PDT_NUM}"  data-odr_price="${orderProduct.ODR_PRICE}" class="btn btn-danger">상품삭제</button>
			   </td>
			  
		    </tr>
		   </c:forEach> 
	  </tbody>
	</table>
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

// cs상태 정보변경
	$("button[name='btnChangeCs_status']").on("click", function () {

	//주문번호, 선택한 배송상태값.
	let odr_code = $(this).data("odr_code");
	let cs_status = $(this).parent().find("select[name='cs_status'] option:selected").val();
	console.log("주문번호 : " + odr_code );
	console.log("CS상태 : " + cs_status);

		$.ajax({
		url : '/admin/order/orderCsStatusChange',
		type : 'get',
		data : {odr_code : odr_code, cs_status : cs_status},
		dataType : 'text',
		success : function (result) {
			if (result == "success") {
			alert("cs상태가 변경되었습니다.");

			}
		}

	});
});

// 개별 상품 삭제
	$("button[name='btnUnitCancle']").on("click", function () {
	
	if (!confirm($(this).data("odr_code") + "번 개별 상품을 삭제하시겠습니까?")) {
		return;
	}
	
	
	let odr_code = $(this).data("odr_code");
	let pdt_num = $(this).data("pdt_num"); 
	let odr_price = $(this).data("odr_price");
	
	
	console.log("주문번호 : " + odr_code );
	console.log("상품번호 : " + pdt_num);
	console.log("개별상품가격 : " + odr_price);
	
	location.href = "/admin/order/orderUnitProductCancel?odr_code=" + odr_code + "&pdt_num=" + pdt_num + "&odr_price=" + odr_price;
	
	});

	 // 수량 변경 버튼클릭1 Ajax 사용. - 주문 코드, 변경수량.
    $("button[name='btnOrderDetailAmountChange']").on("click", function() {

      let btnOrderDetailAmountChange = $(this);

      let odr_code = $(this).data("odr_code");
      //$(this).prev();
      let odr_amount = $(this).parent().find("input[name='odr_amount']").val();

      console.log("주문 코드 : " +  odr_code);
      console.log("변경 수량 : " +  odr_amount);
 
      $.ajax({
          url : '/admin/order/adOrderUnitChange',
          type : 'get',
          data : {odr_code : odr_code, odr_amount : odr_amount},
          dataType : 'text',
          success : function (result) {
            if (result == "success") {
              alert("수량 변경이 되었습니다.")

              //개별 상품 금액, 총 금액 변경작업 
              let pdt_price = btnOrderDetailAmountChange.parent().find("input[name='pdt_price']").val();
              // 단위 가격 변경
              btnOrderDetailAmountChange.parent().parent().find("span.unitprice").html($.numberWithCommas(pdt_price * odr_amount));
              // 총구매 가격변경
              let total_price = 0;
              $("table#orderlistresult span.unitprice").each(function(index, item) {
               // console.log("단위가격:" + $(item).html());
               total_price += parseInt ($.withoutCommas($(item).text()));
               $("table#orderlistresult span#cartTotalPrice").text($.numberWithCommas(total_price));

              });

            }
          }


    });
      
  });


});
	// 숫자 값을 3자리 마다 ,(콤마) 찍기
	$.numberWithCommas = function(x) {
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		}
	// 세자리 마다 ,(콤마)를 제거하기
	  $.withoutCommas = function (x) {
		return x.toString().replace(",", '');
	}
</script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SeoDocMall- 주문 내역</title>
	<meta name="theme-color" content="#563d7c">
	<style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
      
      .orderInfo { border:5px solid #eee; padding:20px; display: none; }
	  .orderInfo .inputArea { margin:10px 0; }
	  .orderInfo .inputArea label { display:inline-block; width:120px; margin-right:10px; }
	  .orderInfo .inputArea input { font-size:14px; padding:5px; }
	  .orderInfo .inputArea:last-child { margin-top:30px; }
	  .orderInfo .inputArea button { font-size:20px; border:2px solid #ccc; padding:5px 10px; background:#fff; margin-right:20px;}
      
       section#content ul li { list-style:none; border:5px solid #eee; padding:10px 20px; margin-bottom:20px; }
 	   section#content .orderHistory span { text-align:center; font-size:20px; font-weight:bold; display:inline-block; width:120px; margin-right:10px; }
 	   
    </style>
   <!--common.jsp - 공통 스타일 시트  -->
<%@ include file="/WEB-INF/views/include/common.jsp" %>
    

</head>
<body>
<!-- header 부분 include -->
<%@ include file="/WEB-INF/views/user/include/header.jsp" %>

<!-- userCategoryMenu 부분 include  -->
<%@ include file="/WEB-INF/views/user/include/userCategoryMenu.jsp" %>

<!-- 메인 글  -->
	<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
	  <h1 class="display-4"><b>주문내역</b></h1>
	</div>

<!-- content 부분  -->

  <div class="container">
   <section id="content" >
	<div id="container_box">
    
		 <ul class="orderHistory">
		  <c:forEach items="${orderHistory}" var="orderHistory">
		  <li>
			  <div>
			   <p><span>주문번호</span> ${orderHistory.odr_code}</p>
			   <p><span>주문날짜</span><fmt:formatDate value="${orderHistory.odr_date}" pattern="yyyy-MM-dd hh:mm" /></p>
			   <p><span>수령인</span>${orderHistory.odr_name}</p>
			   <p><span>주소</span>(${orderHistory.odr_zipcode}) ${orderHistory.odr_addr} [${orderHistory.odr_addr_d}]</p>	 
			   <p><span>결제상태</span>${orderHistory.payment_status}</p>	 
			   <p><span>주문상태</span>${orderHistory.odr_status}</p>	 
			   <p><span>주문 총 가격</span><b><fmt:formatNumber pattern="###,###,###" value="${orderHistory.odr_total_price}" /> 원</b></p>
			  </div>
		  </li>
		  </c:forEach>
		 </ul>


		</div>
	</section>
	
	<div class="box-footer text-center">
		<button type="button" id="btnOrderCancel" class="btn btn-primary">주문취소</button><!--장바구니 목록 이동  -->
	</div>

	<script>
	$(document).ready(function() {
		$("#btnOrderCancel").on("click", function() {
			alert("주문을 취소하시려면 관리자에게 문의바랍니다.");
			alert("문의번호 000-0000-0000");
		});



	
	}); 
	</script>


  <!--footer 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/footer.jsp" %>  
</div>



</body>
</html>
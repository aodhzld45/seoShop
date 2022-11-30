<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>SeoDocMall - 장바구니 목록</title>
    
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
      

    </style>
   <!--common.jsp - 공통 스타일 시트  -->
<%@ include file="/WEB-INF/views/include/common.jsp" %>
    
    
  </head>
  <script>
  	if('${msg}' == 'logout'){
  		alert("로그아웃 되었습니다.");
  	}
  </script>
  
  <body>
   

<!-- header 부분 include -->
<%@ include file="/WEB-INF/views/user/include/header.jsp" %>

<!-- userCategoryMenu 부분 include  -->
<%@ include file="/WEB-INF/views/user/include/userCategoryMenu.jsp" %>

<!-- 메인 글  -->
	<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
	  <h1 class="display-4">장바구니</h1>
	  <p class="lead"></p>
	</div>


<!-- content 부분  -->
  
  <div class="container">
	<div id="container_box">
    		<h2>상품 목록</h2>
  
   <table class="table table-hover" id="cartlistresult" >
 	<!--제목행.  -->
  <thead class="thead-dark">
    <tr>
     
      <th scope="col">상품이미지</th>
      <th scope="col">상품명</th>
      <th scope="col">수량</th>
      <th scope="col">적립</th>
      <th scope="col">배송비</th>
      <th scope="col">가격</th>
      <th scope="col">제거</th>
      
    </tr>
  </thead>	
    
    <tbody>

	  <c:forEach items="${cartlist}" var="cartVO">
	  <c:set var="price" value="${cartVO.cart_amount * cartVO.pdt_price}"></c:set>
	  <!--cartVO클래스의 필드명으로 코딩했지만, 호출은 getter메서드가 사용됨.   -->
	    <tr>
	   
	      <td scope="row">
	   	     <a class="move" href="${productVO.pdt_num }">
	      		<img src="/user/product/displayFile?folderName=${cartVO.pdt_img_folder }&fileName=s_${cartVO.pdt_img }" 
	      		alt="" style="width: 80px;height: 80px;" onerror="this.onerror=null; this.src='/image/no_images.png'">
	      	</a>
	      </td>
	      
	      <td>	
	     	 <c:out value="${cartVO.pdt_name }"/>
	      </td>
	      
	      <td>
          <input type="hidden" name="pdt_price" value='<c:out value="${cartVO.pdt_price }" />'> 
	      	<input type="number" class="w-25" name="cart_amount" value='<c:out value="${cartVO.cart_amount }" />'> 
	      	<button type="button" name="btnCartAmountChange1" data-cart_code="${cartVO.cart_code }" class="btn btn-primary">수량변경Ajax</button>
          	<button type="button" name="btnCartAmountChange2" data-cart_code="${cartVO.cart_code }" class="btn btn-primary">수량변경2</button>

	      </td> 
	   
	      <td><c:out value="${cartVO.mem_point }" /></td>
	      <td>[기본배송]</td>
	      <td><span class="unitprice"><fmt:formatNumber type="number" maxFractionDigits="3">${price }</fmt:formatNumber></span></td>
	      <td>
          <button type="button" name="btnCartDelete" data-cart_code="${cartVO.cart_code}" class="btn btn-danger">삭제</button>
          <input type="hidden" name="cart_amount" value='<c:out value="${cartVO.cart_amount }" />'> 
        </td>
	    </tr>
	    	  <c:set var="sum" value="${sum + price }" ></c:set>
	  </c:forEach>
	   </tbody>
	   <tfoot>
	   	<tr>
	   	<%-- 	<c:if test="${!empty cartlist }"> <!--데이터가 존재 할 경우 true  -->
	   			<td colspan="6" style="text-align: right;">총 구매 금액:<span id="cartTotalPrice"><fmt:formatNumber type="number" maxFractionDigits="3" value="${sum }"></fmt:formatNumber></span> </td>
	   		</c:if> --%>
	   		<c:if test="${empty cartlist }">  <!--데이터가 존재 하지 않을 경우 false  -->
	   			<td colspan="6" style="text-align: center;">장바구니에 상품이 존재 하지 않습니다.</td>
	   		</c:if>
	   	</tr>
	   </tfoot>
	   
	</table><br> 
	
	<div class="box-footer text-center">
		<button type="button" id="btnEmpty" class="btn btn-primary">장바구니 비우기</button>
		<button type="button" class="btn btn-primary">계속 쇼핑하기</button>
		<button type="button" id="btnOrder" class="btn btn-primary">주문하기</button>
	</div><br>
	
</div>

    
  <!--footer 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/footer.jsp" %>  
</div>

<!-- 상품 상세 보기  -->

<script>
  $(document).ready(function() {
    // 수량 변경 버튼클릭1 Ajax 사용. - 장바구니 코드, 변경수량.
    $("button[name='btnCartAmountChange1']").on("click", function() {

      let btnCartAmountChange = $(this);

      let cart_code = $(this).data("cart_code");
      //$(this).prev();
      let cart_amount = $(this).parent().find("input[name='cart_amount']").val();

      console.log("장바구니 코드 : " +  cart_code);
      console.log("변경 수량 : " +  cart_amount);

      $.ajax({
          url : '/user/cart/cart_amount_update1',
          type : 'get',
          data : {cart_code : cart_code, cart_amount : cart_amount},
          dataType : 'text',
          success : function (result) {
            if (result == "success") {
              alert("수량 변경이 되었습니다.")

              //개별 상품 금액, 총 금액 변경작업 
              let pdt_price = btnCartAmountChange.parent().find("input[name='pdt_price']").val();
              // 단위 가격 변경
              btnCartAmountChange.parent().parent().find("span.unitprice").html($.numberWithCommas(pdt_price * cart_amount));
              // 총구매 가격변경
              let total_price = 0;
              $("table#cartlistresult span.unitprice").each(function(index, item) {
               // console.log("단위가격:" + $(item).html());
               total_price += parseInt ($.withoutCommas($(item).text()));
               $("table#cartlistresult span#cartTotalPrice").text($.numberWithCommas(total_price));

              });

            }
          }


    });
  });

      // 수량 변경 버튼클릭2 Ajax 사용 X - 장바구니 코드, 변경수량.
      $("button[name='btnCartAmountChange2']").on("click", function() {

        let cart_code = $(this).data("cart_code");
        //$(this).prev();
        let cart_amount = $(this).parent().find("input[name='cart_amount']").val();

        console.log("장바구니 코드 : " +  cart_code);
        console.log("변경 수량 : " +  cart_amount);

        location.href = "/user/cart/cart_amount_update2?cart_code=" + cart_code +"&cart_amount=" + cart_amount;
});


      //삭제 버튼 클릭시 동작
      $("button[name='btnCartDelete']").on("click", function() {
        
        if (!confirm( $(this).data("cart_code") + "번 상품을 삭제하시겠습니까?")) return;

        let cart_code = $(this).data("cart_code");

        let cart_amount = $(this).parent().find("input[name='cart_amount']").val();

        console.log("장바구니 코드 : " +  cart_code);
        console.log("변경 수량 : " +  cart_amount);

        location.href = "/user/cart/cart_delete?cart_code=" + cart_code;

});

    //장바구니 비우기
    $("#btnEmpty").on("click", function () {

      location.href = "/user/cart/cart_empty";
      
    });
    
    //주문하기 버튼클릭
    $("#btnOrder").click(function(){
    	let type = "cartOrder";
    	location.href = "/user/order/orderListInfo?type=" + type;
   	 
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

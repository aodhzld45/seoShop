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
    <title>SeoDocMall - 상품목록</title>
    
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
   
<div class="container">
<!-- header 부분 include -->
<%@ include file="/WEB-INF/views/user/include/header.jsp" %>

	<!-- userCategoryMenu 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/userCategoryMenu.jsp" %>

<!-- 메인 글  -->
	<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
	  <h1 class="display-4"><c:out value="${catename }"></c:out> </h1>
	</div>
	
	   		<h2>상품 목록</h2>
	<form id="searchForm" action="/user/product/productlist" method="get">
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


<!-- content 부분  -->
  
  <div class="container">

      <div class="row">
      	<c:forEach items="${productList }" var="productVO"   >
        <div class="col-md-4">
          <div class="card mb-4 shadow-sm">
<!--             <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"></rect><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg>
 -->            
            <!-- 상품이미지 -->
            <a class="move" href="${productVO.pdt_num }">
            <img src="/user/product/displayFile?folderName=${productVO.pdt_img_folder }&fileName=s_${productVO.pdt_img }" 
	      		alt="" class="bd-placeholder-img card-img-top" width="100%" height="225" onerror="this.onerror=null; this.src='/image/no_images.png'">
            </a>
            <div class="card-body">
              <p class="card-text">
              	${productVO.pdt_name }<br>
              	${productVO.pdt_company }<br>
       		 <fmt:setLocale value="ko_KR"/><fmt:formatNumber type="number" maxFractionDigits="3" value="${productVO.pdt_price }"></fmt:formatNumber>
	              <br>
	          </p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                
                  <button type="button" class="btn btn-sm btn-outline-secondary" data-pdt_num="${productVO.pdt_num}" id="btnBuyCart" name="btnBuyCart" >Buy & Cart</button>
                </div>              
              </div>
            </div>
          </div>
        </div>
        </c:forEach>  
      </div>
      
      <div class="row">
      	<div class="col-12 text-center">
      			<!-- 페이징 추가 부분.-->
		<nav aria-label="...">
		  <ul class="pagination">
			    <!-- 이전표시 -->
			    <c:if test="${pageMaker.prev }">
				    <li class="page-item">
				      <a class="page-link" href="${pageMaker.startPage - 1 }">이전</a>
				    </li>
			    </c:if>
			    
			    <!-- 페이지번호 표시.  1  2  3  4  5 -->
			    
			    <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="num" >
			    	<li class='page-item ${pageMaker.cri.pageNum == num ? "active": "" }'><a class="page-link" href="${num}">${num}</a></li>
			    </c:forEach>
			    <!-- 
			    <li class="page-item active" aria-current="page">
			      <span class="page-link">2</span>
			    </li>
			    <li class="page-item"><a class="page-link" href="#">3</a></li>
			     -->
			    <!-- 다음표시 -->
			    <c:if test="${pageMaker.next }">
				    <li class="page-item">
				      <a class="page-link" href="${pageMaker.endPage + 1 }">다음</a>
				    </li>
			    </c:if>
				
			  </ul>
		  
		  <!--1)페이지 번호 클릭시 /admin/product/productlist 주소로 보낼 파라미터 작업  2)상품 수정 버튼 클릭시 : 수정주소 3)상품 삭제 버튼 클릭시 : 삭제주소-->
		  <form id="productactionForm" name="productactionForm" action="/admin/product/productList" method="get">
		  			<%-- <input type="hidden" name="pdt_num" value="${productVO.pdt_num}"> --%>
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
					<input type="hidden" name="type" value="${pageMaker.cri.type}">
					<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
					<input type="hidden" name="catecode" value="${catecode}">
					<input type="hidden" name="catename" value="${catename}">
					
		  </form>
		 
			</nav>
      	</div>
      </div>
      
   
   </div>
  
  
  <!--footer 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/footer.jsp" %>  
</div>

<!-- 상품 상세 보기  -->

<!--  Modal 대화상자 : 상품상세보기 -->
	<div class="modal fade" id="modal_productDetail" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">상품 상세 정보</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">

	        <div class="row">
            <div class="col-md-6">
              <img src="" id="modal_detail_image" 
	      		alt="" class="bd-placeholder-img card-img-top" width="100%" height="225" onerror="this.onerror=null; this.src='/image/no_images.png'">
            </div>
            <div class="col-md-6">
              <form>
                <div class="form-group row">
                  <label for="pdt_name" class="col-form-label col-3">상품이름</label>
                  <div class="col=9">
                  	<input type="text" class="form-control" id="pdt_name" readonly>
                  	<input type="hidden" class="form-control" id="pdt_num"> 
                  </div>
                </div>
                <div class="form-group row">
                  <label for="pdt_price" class="col-form-label col-3">판매가격</label>
                  <div class="col=9">
                  	<input type="text" class="form-control" id="pdt_price" readonly>
                  </div>
                </div>
                <div class="form-group row">
                  <label for="pdt_company" class="col-form-label col-3">제조사</label>
                  <div class="col=9">
                  	<input type="text" class="form-control" id="pdt_company" readonly>
                  </div>
                </div> 
                <div class="form-group row">
                  <label for="pdt_amount" class="col-form-label col-3">수량</label>
                  <div class="col=9">
                  	<input type="number" class="form-control" id="pdt_amount" min="1" value="1">
                  </div>
                </div>              
              </form>
            </div>
          </div>
          
    </div>

    <div class="modal-footer">
      <!-- 로그인 이전 상태  -->
   <%--   <c:if test="${sessionScope.loginStatus == null}">
      <!-- <label for="LoginCheck">로그인 후에 주문 가능합니다.</label> -->
      <script>
        alert("로그인 후에 이용가능합니다.");
        location.href='/member/login';
      </script>
     <!-- <button type="button" name="loginCheck"></button> -->
       </c:if> --%>
       
         <!-- 로그인 이후 상태  -->  
    <%--  <c:if test="${sessionScope.loginStatus != null}"> --%>
         <button type="button" name="btnModalBuy" class="btn btn-primary">바로 구매하기</button>
         <button type="button" name="btnModalCart" class="btn btn-primary">장바구니에 담기</button>
    <%--  </c:if>  --%>
     </div>

	    </div>
	  </div>
 
</div>
<script>
  $(document).ready(function() {

    $("button[name='btnBuyCart']").on('click', function(){

      $("#modal_productDetail").modal('show');
    
      let url = "/user/product/productDetail/" + $(this).data("pdt_num");
		
      $.getJSON(url, function(result){

        //모달 대화상자에서 상품 상세정보 표시.
        //console.log("상품상세정보" + result.pdt_num);

        //상품코드
        $("div#modal_productDetail input#pdt_num").val(result.pdt_num);
        // 상품 이름
        $("div#modal_productDetail input#pdt_name").val(result.pdt_name);
        // 판매가격
        $("div#modal_productDetail input#pdt_price").val(result.pdt_price);
        // 제조사
        $("div#modal_productDetail input#pdt_company").val(result.pdt_company);
        // 상품 이미지
        let url = "/user/product/displayFile?folderName=" + result.pdt_img_folder + "&" + "fileName=" + result.pdt_img;
        $("div#modal_productDetail img#modal_detail_image").attr("src", url);
     });
    });

    // 장바구니 불러오기.
    $("button[name='btnModalCart']").on("click", function (){

      $.ajax({
        url : '/user/cart/cart_add',
        data : {pdt_num: $("div#modal_productDetail input#pdt_num").val(), cart_amount: $("div#modal_productDetail input#pdt_amount").val()},
        dataType : 'text',
        beforeSend : function(xmlHttpRequest) {
			console.log("ajax xmlHttpRequest check");
			xmlHttpRequest.setRequestHeader("AJAX", "true");
		},
        success: function(result) {
          if (result == "success") {
            alert("장바구니에 추가되었습니다.");
            if (confirm("장바구니로 이동하시겠습니까?")){
              location.href= "/user/cart/cart_List";
            }
                   
          }
        },
        error : function(xhr, status, error) {
        
        if (xhr.status == 400) {
              console.log(status);
              alert("로그인 후 이용해주세요.");
              location.href = "/member/login";
            
          }
	
		}
		
      });
    });


    //btnModalBuy//직접 구매 클릭시
    $("button[name='btnModalBuy']").on("click", function (){ 
      let type = "direct";
      let pdt_num = $("div#modal_productDetail input#pdt_num").val(); //구매 상품코드
      let odr_amount = $("div#modal_productDetail input#pdt_amount").val(); // 구매수량

      let url = "/user/order/orderListInfo?pdt_num="+ pdt_num + "&odr_amount=" + odr_amount + "&type="+ type;

      console.log("직접구매 주소: " + url);

      location.href = url;

    });


    let productactionForm = $("#productactionForm");
	// 페이지 번호 클릭시 동작.  이전	1	2	3	4	5  다음
	$("ul.pagination li a.page-link").on("click", function(e) {
// 		pageNum 필드는 actionForm에 수동으로 작업되어 있어, 추가하는 것이 아니라, 참조하여 값을 변경한다.
		let pageNum = $(this).attr("href");

		console.log("페이지 번호: " + pageNum);
		e.preventDefault(); // 태그의 기본특성을 제거. <a>태그의 링크기능을 제거.

		productactionForm.find("input[name='pageNum']").val(pageNum);
		
		let url = "/user/product/productList/${catecode}/" + encodeURIComponent("${catename}");
			
		
		productactionForm.attr("action", url);
		productactionForm.submit();

		//현재 선택한 페이지번호 변경작업 <input type="hidden" name="pageNum" value="값">
		// let url = "productlist?pageNum=" + $(this).attr("href") + "&amount=10";
		// location.href = url;
		
	});	


	// 검색 페이지 이동 value값 1 설정
	$("#btnSearch").on("click", function() {
		let searchForm =  $("#searchForm");
		searchForm.find("input[name='pageNum']").val(1); //해당 페이지의 value값을 1로 변경
		searchForm.submit();
	});

  //상품 이미지, 상품 제목 클릭
  $("div.container a.move").on("click", function(e) {
    e.preventDefault();
    
    let pdt_num = $(this).attr("href");
    
    let url = "/user/product/productDetail"
    
    productactionForm.attr("method", "get");
    productactionForm.attr("action", url);

    //뒤로가기시 pdt_num 초기화 작업
    productactionForm.find("input[name='pdt_num']").remove();

    productactionForm.append("<input type-'hidden' name='pdt_num' value='" + pdt_num + "'>");
    productactionForm.submit();

  });


});

</script>

 
  </body>
</html>

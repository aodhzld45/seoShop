<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>SeoDocMall - 주문목록 정보</title>
    
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
	  <h1 class="display-4">주문하기</h1>
	</div>


<!-- content 부분  -->
  
  <div class="container">
	<div id="container_box">
    <h2>주문 목록</h2>
  
   <table class="table table-hover" id="cartlistresult" >
 	<!--제목행.  -->
  <thead class="thead-dark">
    <tr>
      <th scope="col">상품</th>
      <th scope="col">수량</th>
      <th scope="col">적립</th>
      <th scope="col">주문금액</th>
    </tr>
  </thead>	
    
    <tbody>

	  <c:forEach items="${cartOrderList}" var="cartorderinfo">
	  <c:set var="price" value="${cartorderinfo.cart_amount * cartorderinfo.pdt_price}"></c:set>
	  <!--cartVO클래스의 필드명으로 코딩했지만, 호출은 getter메서드가 사용됨.   -->
	    <tr>
	      <td scope="row">
	   	     <a class="move" href="${productVO.pdt_num }">
	      		<img src="/user/order/displayFile?folderName=${cartorderinfo.pdt_img_folder }&fileName=s_${cartorderinfo.pdt_img }" 
	      		alt="" style="width: 80px;height: 80px;" onerror="this.onerror=null; this.src='/image/no_images.png'">
	      		<c:out value="${cartorderinfo.pdt_name }"/>
	      	</a>
	   
	      </td>
	      
	      <td>
          <input type="hidden" name="pdt_price" value='<c:out value="${cartorderinfo.pdt_price }" />'> 
	      	<input type="number" readonly class="w-25" name="cart_amount" value='<c:out value="${cartorderinfo.cart_amount }" />'> 
	      </td> 
	   
	      <td><c:out value="${sessionScope.loginStatus.mem_point }" /></td>
	    
	      <td><span class="unitprice"><fmt:formatNumber type="number" maxFractionDigits="3">${price }</fmt:formatNumber></span></td>
	  
	    </tr>
	    	  <c:set var="sum" value="${sum + price }" ></c:set>
	  </c:forEach>
	   </tbody>
	    <tfoot>
	   	<tr>
	   		<c:if test="${empty cartOrderList }">  <!--데이터가 존재 하지 않을 경우 false  -->
	   			<td colspan="4" style="text-align: center;">주문 내역 상품이 존재 하지 않습니다.</td>
	   		</c:if>
	   	</tr>
	   </tfoot>
	 
	   
	</table><br> 
	
	<!--주문 정보 입력 폼  -->
	<div class="orderInfo">
		<form name="orderForm" id="orderForm" method="post" >
		
		<c:if test="${!empty cartOrderList }"> <!--데이터가 존재 할 경우 true  -->
	   		<h2>총 구매 금액 : <span id="cartTotalPrice"><fmt:formatNumber type="number" maxFractionDigits="3" value="${sum }"></fmt:formatNumber></span></h2>
	   		<br>
	   	</c:if>
	   	
		<h5>주문자 정보</h5>
		<hr>
		  <div class="form-group row">
		    <label for="staticEmail" class="col-sm-2 col-form-label">이름</label>
		    <div class="col-sm-10">
		      <input type="text"  class="form-control" id="s_mem_name" name="s_mem_name" value="${sessionScope.loginStatus.mem_name}" readonly>
		    </div>
		  </div>
		 
		  <div class="form-group row">
		    <label for="staticEmail" class="col-sm-2 col-form-label">이메일</label>
		    <div class="col-sm-10">
		      <input type="text"  class="form-control" id="s_mem_email" name="s_mem_email" value="${sessionScope.loginStatus.mem_email}" readonly >
		    </div>
		  </div>
		  
			  
		  <div class="form-group row">
		    <label for="staticEmail" class="col-sm-2 col-form-label">휴대폰</label>
		    <div class="col-sm-10">
		      <input type="text"  class="form-control" id="s_mem_phone" name="s_mem_phone" value="${sessionScope.loginStatus.mem_phone}" readonly >
		      <input type="hidden"  class="form-control" id="s_mem_zipcode" name="s_mem_zipcode" value="${sessionScope.loginStatus.mem_zipcode}" >
		      <input type="hidden"  class="form-control" id="s_mem_addr" name="s_mem_addr" value="${sessionScope.loginStatus.mem_addr}" >
		      <input type="hidden"  class="form-control" id="s_mem_addr_d" name="s_mem_addr_d" value="${sessionScope.loginStatus.mem_addr_d}" >
		      
		    </div>
		  </div>
		  
		  <input type="checkbox" id="same"><label for="same">위 정보와 같음</label>
		  <hr>
		  <h5>배송 정보</h5>
		   <hr>
		  <div class="form-group row">
		    <label for="staticEmail" class="col-sm-2 col-form-label">이름</label>
		    <div class="col-sm-10">
		      <input type="text"  class="form-control" id="mem_name" name="odr_name">
		    </div>
		  </div> 
		  
		  <div class="form-group row">
		    <label for="staticEmail" class="col-sm-2 col-form-label">연락처</label>
		    <div class="col-sm-10">
		      <input type="text"  class="form-control" id="mem_phone" name="odr_phone" >
		    </div>
		  </div>
		  
		  <div class="form-group row">
		    <label for="staticEmail" class="col-sm-2 col-form-label">배송지 선택</label>
		    <div class="col-sm-10">
     		      <div class="form-check form-check-inline">
					  <input class="form-check-input" type="radio" name="receiveAddr" id="receiveAddr1" value="1" checked>
					  <label class="form-check-label" for="inlineRadio1">자택</label>
					</div>
		     	
		    <div class="form-check form-check-inline">
					  <input class="form-check-input" type="radio" name="receiveAddr" id="receiveAddr2" value="2">
					  <label class="form-check-label" for="inlineRadio2">타지역</label>
				  </div>
		    	</div>		  
		  	</div>
		 
		  <div class="form-group row">
		    <label for="staticEmail" class="col-sm-2 col-form-label">우편번호</label>
		    <div class="col-sm-10">
		      	<input type="text"  class="form-control" id="sample2_postcode" name="odr_zipcode" value="${sessionScope.loginStatus.mem_zipcode}">
		    </div>	
		     	 <input type="button" onclick="sample2_execDaumPostcode()" value="우편번호 찾기"><br>		 
		  </div>
		  
		  <div class="form-group row">
		    <label for="staticEmail" class="col-sm-2 col-form-label">주소</label>
		    <div class="col-sm-10">
		      <input type="text"  class="form-control" id="sample2_address" name="odr_addr" value="${sessionScope.loginStatus.mem_addr}">
		    </div>
		  </div>
		  
		  <div class="form-group row">
		    <label for="staticEmail" class="col-sm-2 col-form-label">상세주소</label>
		    <div class="col-sm-10">
		      <input type="text"  class="form-control" id="sample2_detailAddress" name="odr_addr_d" value="${sessionScope.loginStatus.mem_addr_d}">
		      <input type="hidden" id="sample2_extraAddress" placeholder="참고항목">
		    </div>
		  </div>
		  
		  <div class="form-group row">
		    <label for="odr_req_message" class="col-sm-2 col-form-label">배송시 메세지(100자 이내)</label>
		    <div class="col-sm-10">
		     	<textarea rows="5" class="form-control" id="odr_req_message" name="odr_req_message" ></textarea>
		     	<input type="hidden" name="odr_total_price" value="${sum }">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="pay_method">결제방법</label>
		    <select name="pay_method" id="pay_method" class="form-control">
		      <option value="">결제 방법을 선택하세요</option>
		      <option value="무통장입금">무통장입금</option>
		      <option value="카카오페이">카카오페이</option>
		      <option value="휴대폰">휴대폰</option>
		      <option value="신용카드">신용카드</option>
		      <option value="페이코">페이코</option>
		    </select>
		       <select name="bank" id="bank" class="form-control">
		      <option value="">입금은행을 선택하세요</option>
		      <option value="0111111111">국민은행(0111111)</option>
		      <option value="0222222222">하나은행(0222222)</option>
		      <option value="0333333333">신한은행(0333333)</option>
		      <option value="0444444444">우리은행(0444444)</option>
		    </select>
		    <input type="hidden" name="pay_nobank" id="pay_nobank" value="" >
		   	<input type="hidden" name="pay_nobank_price" id="pay_nobank_price" value="${sum}" >
		    
		  </div>
		  
	  		<div class="form-group row">
				<label for="mem_name" class="col-sm-2 col-form-label">입금자명</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="pay_nobank_user" name="pay_nobank_user">
				</div>
			</div>
		 
		<div class="inputArea">
	   		<button type="button" id="btnOrder"  class="btnOrder">주문</button>
	   		<button type="button" id="btnCancel" class="cancel_btn">취소</button> 
	   		<img id="kakao_pay" alt="kakao_pay" src="/image/payment_icon_yellow_medium.png" style="display: none;">  		
	  	</div>
	  
	  	
	  	
		</form>
		
	</div>
	
	
	<div class="box-footer text-center">
		<button type="button" class="orderOpne_bnt btn btn-primary">주문 정보 입력</button>
		<button type="button" class="btn btn-primary">주문취소</button><!--장바구니 목록 이동  -->
	</div>

</div>

    
  <!--footer 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/footer.jsp" %>  
</div>

<!-- 상품 상세 보기  -->

<script>
  $(document).ready(function() {
    
    //주문 정보 버튼 슬라이드
    $(".orderOpne_bnt").on("click", function () {
    	  $(".orderInfo").slideDown();
    	  $(".orderOpne_bnt").slideUp();
        
      });
    //취소 버튼  슬라이드
    $(".cancel_btn").click(function(){
    	 $(".orderInfo").slideUp();
    	 $(".orderOpne_bnt").slideDown();
    	}); 
      
    //위 정보와 같을때 체크박스 이벤트
    $("#same").on("click", function () {

      if ($("#same").is(":checked") == true) {
        $("#mem_name").val($('#s_mem_name').val());
        $("#mem_phone").val($('#s_mem_phone').val());
      }else{
        $("#mem_name").val("");
        $("#mem_phone").val("");

      }

    });

    //자택 또는 타지역 라디오 버튼 선택 : 선택자 : 작업하는 태그를 선택. 선택자 방법 : id, class , tag
    $("input[name='receiveAddr").on("click", function () {

      if ($("input[name='receiveAddr']:checked").val() == 1) { //자택
        $("#sample2_postcode").val($("#s_mem_zipcode").val());
        $("#sample2_address").val($("#s_mem_addr").val());
        $("#sample2_detailAddress").val($("#s_mem_addr_d").val());
  
      }else if ($("input[name='receiveAddr']:checked").val() == 2){ //타지역
        
        $("#sample2_postcode").val("");
        $("#sample2_address").val("");
        $("#sample2_detailAddress").val("");
      }
    });

	//결제 방법 선택
	let pay_method;
	$("#pay_method").on("change", function() {
		
		if ($("#pay_method option:selected").val() == "") {
			alert("결제 방법을 선택해주세요.");
			return;
		} 

		if ($("#pay_method option:selected").val() == "무통장입금") {
			alert("입금은행을 선택해주세요.")

			
		}

		if ($("#pay_method option:selected").val() == "카카오페이") {
			alert("카카오페이 이미지를 클릭하여 결제를 진행해주세요.");
			$("#btnOrder").attr("disabled", true);
			$("img#kakao_pay").css("display", "inline");
			$("select#bank").css("visibility","hidden");
			return;
		} 

	});

	//카카오페이 버튼클릭. (ajax구문 사용해야함.)
	$("img#kakao_pay").on("click",function () {
		
		//Kakao pay에서 요청하는 필수 입력값 작업.
		//주문자
		
		let odr_name = $("input[name='odr_name']").val();	
		//연락처
		let odr_phone = $("input[name='odr_phone']").val();
		//전자우편
		let odr_email  = $("#s_mem_email").val();
		//전체금액
		let odr_total_price = $("input[name='odr_total_price']").val();
		
		//단위금액
		//할인이 적용된 금액
		//적립금
		//쿠폰

		$.ajax({
			url: '/user/order/orderPay',
			type: 'get',
			data: {
				totalAmount : odr_total_price, 

				odr_name : $("input[name='odr_name']").val(),
				odr_zipcode : $("input[name='odr_zipcode']").val(),
				odr_addr : $("input[name='odr_addr']").val(),
				odr_addr_d : $("input[name='odr_addr_d']").val(),
				odr_phone : $("input[name='odr_phone']").val(),
				odr_total_price : odr_total_price,
				odr_req_message : $("textarea[name='odr_req_message']").val(),
				payment_status : '카카오페이 결제완료',

				pay_method : $("#pay_method option:selected").val(),
				pay_tot_price : odr_total_price,

			},
			success: function (response) {
				
				location.href = response.next_redirect_pc_url;
			}

		});


	});

	
//무통장 입금시 은행선택
	$("#bank option:selected").on("change", function(){

		if($("#bank").val() == "") {
			alert("입금은행을 선택하세요.");
			return;
		} 

	$("#pay_nobank").val($("#bank option:selected").text().substring(0, 4));

	});


    //주문하기 버튼클릭
    $("#btnOrder").on("click", function() {
		let type = "무통장입금";
      // 유효성 검사
	
	  //무통장 입금 유효성 검사
	  	if ($("#pay_method option:selected").val() == "") {
			alert("결제 방법을 선택해주세요.");
			return;
		}
      	if($("#bank").val() == "") {
			alert("입금은행을 선택하세요.");
			return;
		} 

	   //


      $("#orderForm").attr("action","/user/order/orderSave?type=" + type);
      $("#orderForm").submit();

      
    });



    
  
//////////////////////////
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


<!-- 다음 주소 API Script-->
<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 화면을 넣을 element
    var element_layer = document.getElementById('layer'); //<div id="layer"> 태그가 현재 실행코드보다 앞에 작성되어 있어야 한다.

    function closeDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        // 태그참조변수.style.css속성명령어 = '값' 
        element_layer.style.display = 'none';
    }

    function sample2_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample2_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample2_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample2_postcode').value = data.zonecode;
                document.getElementById("sample2_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample2_detailAddress").focus();

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_layer.style.display = 'none';
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(element_layer);

        // iframe을 넣은 element를 보이게 한다.
        element_layer.style.display = 'block';

        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
        initLayerPosition();
    }

    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    function initLayerPosition(){
        var width = 300; //우편번호서비스가 들어갈 element의 width
        var height = 400; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 5; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }

</script>

 
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
	<meta name="theme-color" content="#563d7c">
	
	<title>DocMall - LOGIN</title>
    <!-- Custom styles for this template -->
    <link href="pricing.css" rel="stylesheet">
    
    <script>
    	let msg = "${msg}";
    	if (msg == "idFailure") {
			alert("아이디를 확인해주세요.");
		}else if (msg == "passwdFailure") {
			alert("비밀번호를 확인해주세요.");
		}
    </script>
    
    
    
  </head>
<body>
   
<div class="container">
<!-- header 부분 include -->
<%@ include file="/WEB-INF/views/user/include/header.jsp" %>
	
	<!-- 로그인 부분  -->
	<div class="mb-3 text-center">
		<br>
		<h3><b>로그인</b></h3>
		<hr>
		<form id="loginForm"  action=loginPost method="post">
		  <div class="form-group row">
		    <label for="staticEmail" class="col-sm-2 col-form-label">아이디</label>
			    <div class="col-sm-5">
			      <input type="text"  class="form-control" id="mem_id" name="mem_id" placeholder="아이디를  8~15자 이내로 입력해주세요.">
		    	</div>
		  </div>
		  
		  <div class="form-group row ">
		    <label for="inputPassword" class="col-sm-2 col-form-label">비밀번호</label>
		    <div class="col-sm-5">
		      <input type="password" class="form-control" id="mem_pw" name="mem_pw">
		    </div>
		  </div>
		<div class="form-group row">
			<div class="col-sm-8 text-center">
				<button type="submit" class="btn btn-primary" id="btnlogin">로그인</button>
				<button type="button" class="btn btn-primary" id="btnSearchIdPw">아이디 및 비밀번호 찾기</button>
			</div>
		</div>
		</form>
		
  </div>
  

  	<!--footer 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/footer.jsp" %>  
</div>

	<!--common.jsp - 공통 스타일 시트  -->
	<%@ include file="/WEB-INF/views/include/common.jsp" %> 

<!--========================================================================================================================  -->

<!-- <script type="text/javascript" src="/js/member/join.js"></script> -->


<script>

//html문서와 내용을 브라우저가 읽고 난 이후에 동작되는 특징.
$(document).ready(function(){

	let loginForm = $("#loginForm");

	// 로그인 정보전송. <button type="submit" id="btnlogin"> 클릭하면, 전송이벤트가 동작된다. <form>태그의 이벤트 설정.
	$("#loginForm").on("submit", function() {
		console.log("로그인 버튼 클릭");
		// 유효성 검사 작업
		if ($("#mem_id").val() == "") {
			alert("아이디를 입력해주세요.");
			$("#mem_id").focus();
			return false;
		}

		if ($("#mem_pw").val() == "") {
			alert("비밀번호를 입력해주세요.");
			$("#mem_pw").focus();
			return false;
		}

		return true;
	});

	$("#btnSearchIdPw").on("click", function () {
		location.href = "/member/usersearch"
	});




});
</script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>DocMall - UserSearch</title>

<meta name="theme-color" content="#563d7c">

    <!-- Custom styles for this template -->
    <link href="pricing.css" rel="stylesheet">
	<script>
		if ('${msg}' == 'noId') {
			alert("내용과 일치하는 회원정보가 없습니다.");
		}
		
		
	</script>
  </head>
  <body>
    
<div class="container">

<%@ include file="/WEB-INF/views/user/include/header.jsp" %>

<hr>

  <div class="mb-3 text-center row">
	 <!-- 아이디찾기 -->
	 <div class="col-sm-6">
	 	<h5>아이디 찾기</h5>
	 	<form id="IdsearchForm" action="searchid" method="post">
		  <div class="form-group row">
		    <label for="mem_id" class="col-sm-4 col-form-label">이름</label>
		    <div class="col-sm-8">
		      <input type="text" class="form-control" id="mem_name" name="mem_name" placeholder="이름을 입력해주세요.">
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="mem_pw" class="col-sm-4 col-form-label">이메일</label>
		    <div class="col-sm-8">
		      <input type="text" class="form-control" id="mem_email" name="mem_email" placeholder="이메일을 입력해주세요.">
		    </div>
		  </div>
		  <div class="form-group row">
			  <div class="col-sm-9 text-center">
			  	<button type="submit" class="btn btn-primary" id="btnLogin">아이디 찾기</button>
			  	<button type="button" class="btn btn-primary" id="btnSearchIdPw">로그인</button>
			  </div>			
		  </div>
	 	</form>
	 </div>
	 <!-- 임시비밀번호 발급 -->
	 <div class="col-sm-6">
	 <h5>임시 비밀번호 발급</h5>
	 	<form id="loginForm" action="searchImsiPw" method="post">
		  <div class="form-group row">
		    <label for="mem_id" class="col-sm-4 col-form-label">아이디</label>
		    <div class="col-sm-8">
		      <input type="text" class="form-control" id="mem_id" name="mem_id" placeholder="아이디를 입력해주세요.">
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="mem_pw" class="col-sm-4 col-form-label">이메일</label>
		    <div class="col-sm-8">
		      <input type="text" class="form-control" id="mem_email" name="mem_email" placeholder="이메일을 입력해주세요.">
		    </div>
		  </div>
		  <div class="form-group row">
			  <div class="col-sm-12 text-center">
			  	<button type="submit" class="btn btn-primary" id="btnLogin">임시 비밀번호 발급</button>
			  	<button type="button" class="btn btn-primary" id="btnSearchIdPw">로그인</button>
			  </div>			
		  </div>
	 	</form>
	 </div>
  </div>


  <!--  footer.jsp -->
	<%@ include file="/WEB-INF/views/user/include/footer.jsp" %>  
</div>

<%@include file="/WEB-INF/views/include/common.jsp" %>
   
   <!--<script type="text/javascript" src="/js/member/join.js"></script>-->

	<script>

		/* 로그인 */
	//html문서와 내용을 브라우저가 읽고 난 이후에 동작되는 특징
	$(document).ready(function(){

		

		//메일 인증코드 요청
		$("#btnAuthcode").on("click", function(){

			if($("#mem_email").val() == "") {
				alert("메일주소를 입력하세요.");
				return;
			}

			$.ajax({
				url : '/email/send',
				type : 'get',
				dataType : 'text',
				data : {receiveMail : $("#mem_email").val()},
				success : function(result) {
					if(result == "success"){
						alert("메일이 발송되어, 인증코드를 확인바랍니다.");
					}else{
						alert("메일발송이 실패되어, 메일주소 확인 또는 관리자에게 문의바랍니다.");
					}
				}
			});
		});

	});


	</script>

  </body>
</html>
    
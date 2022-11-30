<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    

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

  </head>
  <body>
    
<div class="container">

<%@ include file="/WEB-INF/views/user/include/header.jsp" %>

<hr>

<h3>아이디 및 비밀번호 찾기</h3>

<hr>

  <div class="mb-3 text-center row">
  
  <c:if test="${mem_id != null }">
	  <p>아이디는 : ${fn:substring(mem_id, 0, 4) }***** 입니다.</p><br>
		전체 아이디는 고객센터로 문의해 주세요.
  </c:if>	
  
   <c:if test="${mail != null }">
	  <p>임시 비밀번호가 발급되었습니다. 메일을 확인해주세요.</p><br>
  </c:if>	
  
  
	
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

		



	});


	</script>

  </body>
</html>
    
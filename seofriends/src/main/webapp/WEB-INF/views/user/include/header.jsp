<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
	<header>
	  <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
	    <a class="navbar-brand" href="/">SeoFriends</a>
	    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    
	   <div class="collapse navbar-collapse" id="navbarCollapse">
	    	<c:if test="${sessionScope.loginStatus.mem_verify == 9}">    
	     		<ul class="navbar-nav mr-auto">
		  			<a class="btn btn-outline-primary" href="/admin/adLogin">관리자</a> 
	     		</ul>
	      	</c:if>

	   </div>
	   
	     <form class="form-inline mt-2 mt-md-0">	      	 
		<!-- 로그인 이전 상태  -->
		  <c:if test="${sessionScope.loginStatus == null}">
		    <a class="p-2 text-white" href="/member/login">로그인&nbsp;&nbsp;</a>
		    <a class="p-2 text-white" href="/member/join">회원가입&nbsp;&nbsp;</a> 
		  </c:if> 
		  
		   
		   <!-- 로그인 이후 상태  -->  
		 <c:if test="${sessionScope.loginStatus != null}">
		    <a class="p-2 text-white" href="/member/logout">Logout[${sessionScope.loginStatus.mem_id}]</a>
		  	<span style="color: red;">Point : [${sessionScope.loginStatus.mem_point}]</span>
		    <a class="p-2 text-white" href="/user/cart/cart_List">장바구니</a>
		    <a class="p-2 text-white" href="/user/order/orderHistory">주문내역</a>
		  </c:if> 
		  
		   <a class="p-2 text-white" href="/board/list">고객센터&nbsp;&nbsp;</a>	
		   	  
	      </form>
	    
	    <form class="form-inline">
		    <input class="form-control mr-sm-2" type="search" placeholder="검색어를 입력해주세요" aria-label="Search">
		    <button class="btn btn-primary my-2 my-sm-0" type="submit">검색</button>
	  	</form>
	    

	  </nav>
	</header>
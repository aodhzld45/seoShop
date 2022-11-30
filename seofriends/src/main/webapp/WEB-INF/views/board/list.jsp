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
    <title>SeoDocMall - 고객센터</title>
    
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
	  <h1 class="display-4">고객센터 </h1>
	</div>
	
	   		<h2>질문있어요!</h2>
    <form id="searchForm" action="/board/list" method="get">
	  <select name="type">
		  <option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }" />>--</option>
		  <option value="T" <c:out value="${pageMaker.cri.type eq 'T' ? 'selected' : '' }" />>제목</option><!-- Title -->
		  <option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : '' }" />>내용</option><!-- Content -->
		  <option value="I" <c:out value="${pageMaker.cri.type eq 'I' ? 'selected' : '' }" />>작성자</option><!-- Writer -->
		  <option value="TC" <c:out value="${pageMaker.cri.type eq 'TC' ? 'selected' : '' }" />>제목 or 내용</option><!-- Title or Content -->
		  <option value="TI" <c:out value="${pageMaker.cri.type eq 'TI' ? 'selected' : '' }" />>제목 or 작성자</option><!-- Title or Writer -->
		  <option value="TIC" <c:out value="${pageMaker.cri.type eq 'TIC' ? 'selected' : '' }" />>제목 or 작성자 or 내용</option><!-- Title or Writer or Content-->
	  </select>
	  <input type="text" name="keyword" value="${pageMaker.cri.keyword }">
	  <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
	  <input type="hidden" name="amount" value="${pageMaker.cri.amount }">
	 
	  <button type="button" id="btnSearch" class="btn btn-primary">검색</button>
  </form>


<!-- content 부분  -->
  
  <div class="container">

      <div class="row">
  <table class="table">
 <!--제목행.  -->
  <thead class="thead-dark">
    <tr>
      <th scope="col">글번호</th>
      <th scope="col">제목</th>
      <th scope="col">작성자</th>
      <th scope="col">첨부파일</th>
      <th scope="col">등록일</th>
    </tr>
  </thead>
	  <tbody>
	  
		  <c:forEach items="${list}" var="list">
		  <!--BoardVO클래스의 필드명으로 코딩했지만, 호출은 getter메서드가 사용됨.   -->
			    <tr>
			      <th scope="row"><c:out value="${list.brd_num}" /></th>
			      <td><a href="/board/get?brd_num=${list.brd_num} "><c:out value="${list.brd_title}" escapeXml="true" /></a>[${list.replycnt}]</td>
			      <td><c:out value="${list.mem_id}" /></td>
			     
			      <td><img src="/resources/image/attach.png" style="width: 50px; height: 50px; "></td>
			      
			     <td><fmt:formatDate value="${list.brd_date_reg}" pattern="yyyy-MM-dd hh:mm" />  </td>
			    </tr>
		  </c:forEach>
	   </tbody>
	</table><br>

</div>

	<hr>
		<button type="button" id="btn_write" style="float: right;" class="btn btn-primary">글 작성</button>
   
      <hr>
  <div class="row">
      <!-- 페이징 추가 부분.-->
      
  <nav aria-label="...">
  <ul class="pagination">
  <!-- 이전 버튼 표시 -->
  <c:if test="${pageMaker.prev }">
    <li class="page-item"><a class="page-link" href="${pageMaker.startPage - 1}"><b>이전</b></a></li>
  </c:if> 
  
    <!-- 페이지 번호 표시. 1 2 3 4 5  -->
    <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="num">
    <li class='page-item ${pageMaker.cri.pageNum == num ? "active": ""}'><a class="page-link" href="${num}">${num}</a></li>
    </c:forEach>
    
  <!--   <li class="page-item active" aria-current="page">
      <span class="page-link">2</span>
    </li> -->
    
    <!-- 다음 버튼 표시  -->
   <c:if test="${pageMaker.next }">
    <li class="page-item"><a class="page-link" href="${pageMaker.endPage + 1}" ><b>다음</b></a></li>
  </c:if>   
  </ul>
 
  
  <!--페이지 번호 클릭시 list주소로 보낼 파라미터 작업  -->
  <form id="actionForm" action="/board/list" method="get">
			<input type="hidden" name="pageNum" value="${cri.pageNum}">
			<input type="hidden" name="amount" value="${cri.amount}">
			<input type="hidden" name="type" value="${cri.type}">
			<input type="hidden" name="keyword" value="${cri.keyword}">
 </form>
</nav>
      
      	
      </div>
      
   
   </div>
  
  
  <!--footer 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/footer.jsp" %>  
</div>

  <script>
    $(document).ready(function(){
    	
    	let actionForm = $("#actionForm"); // <form id="actionForm"> 참조
    	
		// 페이지 번호 클릭시 동작.  이전	1	2	3	4	5  다음
		$("li.page-item a.page-link").on("click", function(e) {
			e.preventDefault(); // 태그의 기본특성을 제거. <a>태그의 링크기능을 제거.
			
			/* 검색기능추가하여 아래구문은 사용안함.
			let url = "list?pageNum=" + $(this).attr("href") + "&amount=10";
			location.href = url;
			*/

			//현재 선택한 페이지번호 변경작업.   <input type="hidden" name="pageNum" value="값">
			actionForm.find("input[name='pageNum']").val($(this).attr("href"));

      actionForm.attr("action", "/board/list");
			actionForm.submit();
		});
    	
		// 목록에서 제목을 클릭시 동작.(페이징 파라미터, 검색 파라미터, 글번호)
		$("a.move").on("click", function(e) {
			// $(this) : $("a.move") 선택자중 클릭된 a 태그
			e.preventDefault();
			let bno = $(this).attr("href");

			actionForm.find("input[name='brd_num']").remove();

			//<form>태그의 자식으로 추가됨.
			actionForm.append("<input type='hidden' name='brd_num' value='" + brd_num + "'>");
			actionForm.attr("action", "/board/get");
			//form 전송 -> submit();
			actionForm.submit();

		});
    	
      //  글작성 클릭시 동작 구문
          $("#btn_write").on("click", function(){
            console.log("글작성 버튼클릭");
            location.href= "/board/write"
          });

      //  페이지 클릭시 동작 구문
          $("li.page-item a.page-link").on("click", function(e){
        	console.log("페이지 클릭");
            e.preventDefault(); //태그의 기본 특성을 제거.
            let url = "list?pageNum=" + $(this).attr("href") + "&amount=10";
            location.href = url;
          });
      // 검색 페이지 이동 value값 1 설정
          $("#btnSearch").on("click", function() {
      		let searchForm =  $("#searchForm");
      		searchForm.find("input[name='pageNum']").val(1); //해당 페이지의 value값을 1로 변경
      		searchForm.submit();
      	});
      
        });
    </script>

 
  </body>
</html>

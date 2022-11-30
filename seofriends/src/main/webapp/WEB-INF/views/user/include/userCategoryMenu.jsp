<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
<!--1차 카테고리 메뉴  -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand">&nbsp;&nbsp;&nbsp;</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
<ul class="navbar-nav mr-auto">
<!--  
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
-->  

<c:forEach items="${mainCategoryList }" var="categoryVO" > 
  <!-- 1차 카테고리 -->
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="${categoryVO.catecode }" role="button" data-toggle="dropdown" aria-expanded="false" style="display: inline;">
          ${categoryVO.catename}
        </a>

        <!--2차 카테고리. 1차 카테고리가 선택된 next() -->
        <div class="dropdown-menu subCategory"></div>
      </li>
    </c:forEach>
     <!--  
      <li class="nav-item">
        <a class="nav-link disabled">Disabled</a>
      </li>
     -->
    </ul>
   
  </div>
</nav>

<script>
// $(document).ready(function () {});
$(function () {
  //1차 카테고리 클릭.
  $("ul.navbar-nav li.nav-item a").on("click", function() {

    // ajax작업 구문 사용시 이전에 필요한 선택자의정보를 변수에 미리 저장해서 사용해야 한다.
    let selectedCategory = $(this);
    let url = "/user/product/subUserCategoryList/" + $(this).attr("href"); //catecode를 받아옴.
   // console.log("2차 카테고리 : " + url) ;

    /* result : 2차 카테고리 정보  */
     $.getJSON(url, function (result) {
    	 console.log("2차 카테고리 정보 : " + selectedCategory.attr("href"));

      // 2차 카테고리 추가 작업.
      let subCategoryList = selectedCategory.next();
          //기존 카테고리에 의하여 자식 태그 출력되는 요소 여기선 <a>태그 모두제거.
          subCategoryList.children("a").remove();

      let subCategoryStr = "";

  		for(let i = 0; i<result.length; i++){
        // 1) 직접 주소 작업.
        //subCategoryStr += "<a class='dropdown-item' href='/user/product/productList/" + result[i].catecode + "'>" + result[i].catrname + "</a>";
        // 2) JQuery 문법을 사용하여, 이벤트 적용을 통한 주소 요청 작업 
        subCategoryStr += "<a class='dropdown-item' href='" + result[i].catecode + "'>" + result[i].catename +  "</a>";
		}
  		
        subCategoryList.append(subCategoryStr);
     }); 
    });

  //2차 카테고리 클릭.
  $("ul.navbar-nav li.nav-item div.subCategory").on("click", "a", function(e){
    e.preventDefault();  // <a href=""></a> 링크기능제거, <input type="submit"> 전송기능 제거

    let catecode = $(this).attr("href");
    let catename = $(this).html();
    // get방식으로 특수문자 데이터가 서버로 보내질때 문제되는 경우 : 인코딩할 것.  검색어 : mdn url 인코딩
    location.href = "/user/product/productList/" + catecode + "/" +  encodeURIComponent(catename);
    productList
    
  });


});

</script>





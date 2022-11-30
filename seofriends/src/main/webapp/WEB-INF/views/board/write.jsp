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
    <title>SeoDocMall - 질문있어요! 글작성</title>
    
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
      
      .bigPictureWrapper {
		  position: absolute;
		  display: none;
		  justify-content: center;
		  align-items: center;
		  top:0%;
		  width:100%;
		  height:100%;
		  background-color: gray; 
		  z-index: 100;
		}
		
		.bigPicture {
		  position: relative;
		  display:flex;
		  justify-content: center;
		  align-items: center;
		}
	</style>
	
	<style>
	.uploadResult {
		width: 100%;
		background-color: gray;
	}
	
	.uploadResult ul {
		display: flex;
		flex-flow: row;
		justify-content: center;
		align-items: center;
	}
	
	.uploadResult ul li {
		list-style: none;
		padding: 10px;
	}
	
	.uploadResult ul li img {
		width: 100px;
	}
	</style>
      
    
   <!--common.jsp - 공통 스타일 시트  -->
<%@ include file="/WEB-INF/views/include/common.jsp" %>
 
  </head>
<body>
   
<div class="container">
<!-- header 부분 include -->
<%@ include file="/WEB-INF/views/user/include/header.jsp" %>

	<!-- userCategoryMenu 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/userCategoryMenu.jsp" %>

<!-- 메인 글  -->
	<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
	  <h1 class="display-4">질문있어요! </h1>
	</div>
	  <h3>문의 내용을 작성해주세요.</h3>
   
	<!-- content 부분  -->
  
  <div class="container">
     <div class="row">
    <div class="col-12">
   
      <form id="boardForm" name="boardForm" method="post" action="/board/write" enctype="multipart/form-data">
        <div class="form-group">
        <!-- name에는 BoardVO클래스 필드명을 적어준다. -->
          <label for="title">제목</label>
          <input type="text" class="form-control" id="brd_title" name="brd_title">
        </div>
        <div class="form-group">
          <label for="content">내용</label>
          <textarea class="form-control" id="brd_content" name="brd_content" rows="3"></textarea>
        </div>
        
        <div class="form-group">
        <!-- name에는 BoardVO클래스 필드명을 적어준다. -->
          <label for="title">작성자</label>
          <input type="text" class="form-control" id="mem_id" name="mem_id">
        </div>
        
        <div>
	            <h2>첨부할 파일을 등록해주세요.</h2>
	    </div>
	    
    	 
			    	  <table>
						<tr>
							<td id="fileIndex">
							
							</td>
						</tr>
						<tr>
							<td>	
								<hr>									
								<button id="fileAdd_btn" class="btn btn-primary" type="button">파일추가</button>	
								<hr>
							</td>
						</tr>	
					</table>
				
		<div class="form-group row"> 
			    <label for="uploadFile" class="col-sm-2 col-form-label">이미지파일</label>
			    <div class="col-sm-4"> <!-- name="" 존재하지 않음 실제 업로드한 이미지 이름이 들어감. -->
			      <input type="file"  class="form-control" id="uploadFile" name="uploadFile" multiple="multiple"  >
			    </div>
			    			    			    
			   	<label for="pdt_img" class="col-sm-2 col-form-label">미리보기 이미지</label>
			  	<div class="col-sm-4"> <!-- name="" 존재하지 않음 실제 업로드한 이미지 이름이 들어감. -->
			    	<img id="change_img" style="width: 200px; height: 200px; ">
			  	</div>
		 </div>
		
		 
    <hr>
   			
  		<div class="from-group row">
			<div class="col-sm12 text-center">
				 <button type="submit" class="btn btn-primary" name="btnwrite" id="btnwrite">등록하기</button>
        		 <button type="button" id="btn_list" class="btn btn-info">취소</button>
			</div>
    	</div>
    
      </form>
      
	  </div>
	</div>
	
	<hr>
	
  </div>
  
  <!--footer 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/footer.jsp" %>  
</div>

<script type="text/javascript">
		$(document).ready(function(){
			let formObj = $("form[name='boardForm']");
			$(".btnwrite").on("click", function(){
				if(fn_valiChk()){
					return false;
				}
				formObj.attr("action", "/board/write");
				formObj.attr("method", "post");
				formObj.submit();
			});
			fn_addFile();
		})
		function fn_valiChk(){
			var regForm = $("form[name='boardForm'] .chk").length;
			for(var i = 0; i<regForm; i++){
				if($(".chk").eq(i).val() == "" || $(".chk").eq(i).val() == null){
					alert($(".chk").eq(i).attr("title"));
					return true;
				}
			}
		}
		function fn_addFile(){
			var fileIndex = 1;
			//$("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"<button type='button' style='float:right;' id='fileAddBtn'>"+"추가"+"</button></div>");
			$("#fileAdd_btn").on("click", function(){
				$("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"</button>"+"<button type='button' style='float:right;' id='fileDelBtn' class='btn btn-danger'>"+"삭제"+"</button></div>");
			});
			$(document).on("click","#fileDelBtn", function(){
				$(this).parent().remove();
				
			});
		}
	</script>

<script>
	$(document).ready(function() {
/////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
		// 이미지 미리보기
		$("#uploadFile").on("change", function(e) {
			let file = e.target.files[0];

			let reader = new FileReader();

			reader.onload = function (e) {
				$("#change_img").attr("src", e.target.result);
			}

			reader.readAsDataURL(file);

		});
		
		//목록 이동
		$("#btn_list").on("click", function() {
			location.href = "/board/list";
		});



	});


</script>

  

 
  </body>
</html>

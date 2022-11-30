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
    <title>SeoDocMall - 질문있어요! 글수정</title>
    
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
	  <h3>수정할 내용을 작성해주세요.</h3>
   
	<!-- content 부분  -->
  
  <div class="container">
     <div class="row">
    <div class="col-12">
  
      <form id="modifyForm" method="post" action="/board/modify" enctype="multipart/form-data">
      	<input type="hidden" name="brd_num" value="${board.brd_num}">
		<input type="hidden" name="pageNum" value="${cri.pageNum}">
		<input type="hidden" name="amount" value="${cri.amount}">
		<input type="hidden" name="type" value="${cri.type}">
		<input type="hidden" name="keyword" value="${cri.keyword}"> 
      	<input type="hidden" id="fileNoDel" name="fileNoDel[]" value=""> 
		<input type="hidden" id="fileNameDel" name="fileNameDel[]" value=""> 
      
       <div class="form-group">
        <!-- name에는 BoardVO클래스 필드명을 적어준다. -->
          <label for="brd_num">글번호</label>
          <input type="text" class="form-control" id="brd_num" name="brd_num" readonly value="${board.brd_num}">
        </div>
      
        <div class="form-group">
        <!-- name에는 BoardVO클래스 필드명을 적어준다. -->
          <label for="brd_title">제목</label>
          <input type="text" class="form-control" id="brd_title" name="brd_title" value="${board.brd_title}">
        </div>
        <div class="form-group">
          <label for="brd_content">내용</label>
          <textarea class="form-control" id="brd_content" name="brd_content" rows="3" >${board.brd_content}</textarea>
        </div>
        
        <div class="form-group">
        <!-- name에는 BoardVO클래스 필드명을 적어준다. -->
          <label for="mem_id">작성자</label>
          <input type="text" class="form-control" id="mem_id" name="mem_id" value="${board.mem_id}" readonly>
        </div><br>
        
        <div class="form-group">
        <!-- name에는 BoardVO클래스 필드명을 적어준다. -->
          <label for="mem_id"><strong>등록일 : </strong></label>          
			<strong><fmt:formatDate value="${board.brd_date_reg}" pattern="yyyy-MM-dd hh:mm"/></strong>					
        </div>
        
        <div>
	            <h3>첨부할 파일을 수정해주세요.</h3>
	    </div>
	    
	      	  <table>
				<tr>
					<td id="fileIndex">
					<c:forEach var="file" items="${file}" varStatus="var">
						<div>
							<input type="hidden" id="FILE_NO" name="FILE_NO_${var.index}" value="${file.FILE_NO }">
							<input type="hidden" id="FILE_NAME" name="FILE_NAME" value="FILE_NO_${var.index}">
							<a href="#" id="fileName" onclick="return false;">${file.ORG_FILE_NAME}</a>(${file.FILE_SIZE}kb)
							<button id="fileDel" onclick="fn_del('${file.FILE_NO}','FILE_NO_${var.index}');" type="button">삭제</button><br>
						</div>
					</c:forEach>
					
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
			    <div class="col-sm-4"> 
			      <input type="file"  class="form-control" id="uploadFile" name="uploadFile" multiple="multiple"  >
			    </div>
			    
			   	<label for="s_img" class="col-sm-2 col-form-label">미리보기 이미지</label>
			  	<div class="col-sm-4"> 
			    	<img id="change_img" style="width: 200px; height: 200px; ">
			  	</div>
		 </div> 
		 
    <hr>
  		<div class="from-group row">
			<div class="col-sm12 text-center">
				 <button type="submit" class="btn btn-primary" name="btn_modify" id="btn_modify">수정하기</button>
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

<script>
		$(document).ready(function(){
			let formObj = $("form[name='modifyForm']");
			
			$(document).on("click","#fileDel", function(){
				$(this).parent().remove();
			});
			
			fn_addFile();
			
			$("#btn_list").on("click", function(){
				event.preventDefault();
				location.href = "/board/get?brd_num=${board.brd_num}"
					   + "&pageNum=${cri.pageNum}"
					   + "&amount=${cri.amount}"
					   + "&type=${cri.type}"
					   + "&keyword=${cri.keyword}";
			});
			
			$(".btn_modify").on("click", function(){
				if(fn_valiChk()){
					return false;
				}
				formObj.attr("action", "/board/modify");
				formObj.attr("method", "post");
				formObj.submit();
			});
		});
		
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
			
		function fn_valiChk(){
			let updateForm = $("form[name='modifyForm'] .chk").length;
			for(var i = 0; i<updateForm; i++){
				if($(".chk").eq(i).val() == "" || $(".chk").eq(i).val() == null){
					alert($(".chk").eq(i).attr("title"));
					return true;
				}
			}
		}
		
			function fn_addFile(){
				let fileIndex = 1;
			//$("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"<button type='button' style='float:right;' id='fileAddBtn'>"+"추가"+"</button></div>");
			$("#fileAdd_btn").on("click", function(){
				$("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"</button>"+"<button type='button' style='float:right;' id='fileDelBtn' class='btn btn-danger'>"+"삭제"+"</button></div>");
			});
			$(document).on("click","#fileDelBtn", function(){
				$(this).parent().remove();
				
			});
		}
			let fileNoArry = new Array();
			let fileNameArry = new Array();
			function fn_del(value, name){
				
				fileNoArry.push(value);
				fileNameArry.push(name);
				$("#fileNoDel").attr("value", fileNoArry);
				$("#fileNameDel").attr("value", fileNameArry);
			}
</script>
	

  </body>
</html>

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
	
      
    
   <!--common.jsp - 공통 스타일 시트  -->
<%@ include file="/WEB-INF/views/include/common.jsp" %>
    <!-- 핸들바 작업1 -->
<script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>
  </head>
<body>
   
<div class="container">
<!-- header 부분 include -->
	<%@ include file="/WEB-INF/views/user/include/header.jsp" %>

	<!-- userCategoryMenu 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/userCategoryMenu.jsp" %>

<!-- 메인 글  -->
	<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
	  <h1 class="display-4"> 문의 내용 </h1>
	</div>
	  <h3>문의 내용입니다.</h3>
	<!-- content 부분  -->
 
  <div class="container">
     <div class="row">
    <div class="col-12">
    
       <div class="form-group">
	    <label for="title">번호</label>
	    <input type="text" class="form-control" id="brd_num" name="brd_num" readonly value="${board.brd_num }">
	  </div>
	  <div class="form-group">
	    <label for="title">제목</label>
	    <input type="text" class="form-control" id="brd_title" name="brd_title" readonly value="${board.brd_title }">
	  </div>
	  <div class="form-group">
	    <label for="content">내용</label>
	    <textarea class="form-control" id="brd_content" rows="3" name="brd_content" readonly>${board.brd_content }</textarea>
  	  </div>
	  <div class="form-group">
	    <label for="writer">작성자</label>
	    <input type="text" class="form-control" id="mem_id" name="mem_id" readonly value="${board.mem_id }">
	  </div>
	  	  
	  <div class="form-group">
	    <label for="writer">등록일</label>
	    <input type="text" class="form-control" id="brd_date_reg" name="brd_date_reg" readonly value="<fmt:formatDate value="${board.brd_date_reg }" pattern="yyyy-MM-dd hh:ss"/>">
	  </div>
	  
	 	<hr>
			<span>파일 목록</span>
			<div class="form-group" style="border: 1px solid #dbdbdb;">
				<c:forEach var="file" items="${file}">
					<a href="#" onclick="fn_fileDown('${file.FILE_NO}'); return false;">${file.ORG_FILE_NAME}</a>(${file.FILE_SIZE}kb)<br>
				</c:forEach>
			</div>
		<hr>
	<button type="button" id="btn_modify" class="btn btn-info">수정하기</button>
	<button type="button" id="btn_delete" class="btn btn-danger">삭제하기</button><br><br>
	<button type="button" id="btn_list" class="btn btn-primary">목록으로</button>
    	
       
     <form id="operForm" role="form" method="get">
		<input type="hidden" name="brd_num" value="${board.brd_num}">
		<input type="hidden" name="pageNum" value="${cri.pageNum}">
		<input type="hidden" name="amount" value="${cri.amount}">
		<input type="hidden" name="type" value="${cri.type}">
		<input type="hidden" name="keyword" value="${cri.keyword}">
		<input type="hidden" id="FILE_NO" name="FILE_NO" value=""> 	
	</form>
	
	<hr>
	
	<div class="row">
	    <div class="col-12">
	      <button type="button" id="btn_replyWrite" class="btn btn-primary">답글 작성하기</button>
	    </div>
	 </div>

	<hr>
	
	  </div>
	</div>	
  </div>
  
    <!--댓글 목록-->
  <div class="row">
    <div class="col-12">
      <div id="replyList">

      </div>
      <div id="replyPaging">
        
      </div>
    </div>
  </div>
  
  <!--footer 부분 include  -->
	<%@ include file="/WEB-INF/views/user/include/footer.jsp" %>  
</div>

<!--Modal Dialog-->
<div class="modal fade" id="replyModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="replyTitle">새 답글 작성하기</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="replyer" class="col-form-label">작성자:</label>
            <input type="hidden" class="form-control" id="rno" name="rno">
            <input type="text" class="form-control" id="replyer" name="replyer">
          </div>
          <div class="form-group">
            <label for="reply" class="col-form-label">답글:</label>
            <textarea class="form-control" id="reply" name="reply"></textarea>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
        <button type="button" id="btn_replySave" class="btn btn-primary btnModal">등록하기</button>
        <button type="button" id="btn_replyModify" class="btn btn-primary btnModal">수정하기</button>
        <button type="button" id="btn_replyDelete" class="btn btn-primary btnModal">삭제하기</button>
      </div>
    </div>
  </div>
</div>

<script>
	

	//let formObj = $("form[name='operForm']");

	
	function fn_fileDown(fileNo){
		alert("파일 다운로드는 관리자만 가능합니다.");
		let operForm = $("#operForm");
		operForm.find("input[name='FILE_NO']");

	 	console.log(fileNo);
		$("#FILE_NO").attr("value", fileNo);
        operForm.attr("action", "/board/fileDown");
        operForm.submit(); 
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
	/* 1.게시판 작업  */
	$(document).ready(function() {
		// 이미지 미리보기
		$("#uploadFile").on("change", function(e) {
			let file = e.target.files[0];

			let reader = new FileReader();

			reader.onload = function (e) {
				$("#change_img").attr("src", e.target.result);
			}
			reader.readAsDataURL(file);
		});
		
		 let operForm = $("#operForm");
		//목록 이동
		$("#btn_list").on("click", function() {
			 operForm.find("input[name='brd_num']").remove();
	         operForm.attr("action", "/board/list");
	         operForm.submit();
			//location.href = "/board/list";
		});
		        
        //수정버튼 클릭시 동작구문. 	<button type="button" id="btn_modify" class="btn btn-primary">Modify</button>
          $("#btn_modify").on("click", function(){
            //console.log("수정버튼 클릭"); //이클립스에서 해당파일을 읽어주어야 한다.
            // 수정폼 주소
            let brd_num = $("#brd_num").val();  // 입력양식 태그의 값을 읽어옴.
            location.href = "/board/modify?brd_num=" + brd_num;

            //operForm.submit();
          });

        //제거버튼 클릭시 동작구문. <button type="button" id="btn_remove" class="btn btn-danger">Remove</button>
          $("#btn_delete").on("click", function(){
            //console.log("제거버튼 클릭");
            //제거주소
            let brd_num = $("#brd_num").val();
            if(!confirm(brd_num + "번 글을 삭제하겠습니까?")) return;

            location.href = "/board/delete?brd_num=" + brd_num;
          });
          
      
		
		
		
	});
</script>

<script>
      //2.댓글작업
      $(document).ready(function(){
    	  //댓글페이지번호 클릭. 동적인 태그를 이벤트 설정 할경우에 
        $("#replyPaging").on("click", "ul.pagination li.page-item a", function(e){
            e.preventDefault();

            //console.log("클릭");
            replyPage = $(this).attr("href");  //  1  2 3 4 5
            let url = "/replies/pages/" + brd_num + "/" + replyPage + ".json"; // 댓글목록및 페이징정보 요청주소

            getPage(url);
        }); 

		 //모달대화상자에서 수정버튼 클릭
		 $("#btn_replyModify").on("click", function(){

			let rno = $("#rno").val(); //댓글번호
			let reply = $("#reply").val(); // 댓글내용

			//자바스크립트 Object구문
			let replyObj = { rno:rno, reply:reply };
			// 서버측에 보낼 데이타를 JSON문법구조의 문자열로 변환작업. 
			let replyStr = JSON.stringify(replyObj);

			console.log(replyStr);
			//웹브라우저는 get, post방식만 지원한다. 스프링에서 put요청으로 인식되기 위하여, 헤더요청을 변경한다.
			$.ajax({
				type: 'put',
				url: '/replies/modify/' + rno,
				headers: {
				"Content-Type" : "application/json", "X-HTTP-Method-Override" : "PUT"
				},
				dataType: 'text', // 매핑주소의 리턴값 타입
				data: replyStr, //
				success: function(result) {
				if(result == "success"){
					alert("댓글 데이타 수정 성공");

					//Modal Dialog의 내용을 초기화
					$("#replyTitle").html("New Reply");
					$("#rno").val("");
					$("#replyer").val("");
					$("#reply").val("");

					$(".btnModal").hide(); // 모달대화상자의 3개버튼을 모두 보이지않게한다.
					$("#btn_replySave").show();

					$("#replyModal").modal('hide');

					let url = "/replies/pages/" + brd_num + "/" + replyPage + ".json"; // 댓글목록및 페이징정보 요청주소

					getPage(url);
					}
				}
			});
		});
  
		      //모달대화상자에서 삭제버튼 클릭
			$("#btn_replyDelete").on("click", function(){

				let rno = $("#rno").val(); //댓글번호

				//웹브라우저는 get, post방식만 지원한다. 스프링에서 delete요청으로 인식되기 위하여, 헤더요청을 변경한다.
				$.ajax({
					type: 'delete',
					url: '/replies/delete/' + rno,
					headers: {
					"Content-Type" : "application/json", "X-HTTP-Method-Override" : "DELETE"
					},
					dataType: 'text', // 매핑주소의 리턴값 타입
					//data: replyStr, //
					success: function(result) {
					if(result == "success"){
						alert("댓글 데이타 삭제 성공");

						//Modal Dialog의 내용을 초기화
						$("#replyTitle").html("New Reply");
						$("#rno").val("");
						$("#replyer").val("");
						$("#reply").val("");

						$(".btnModal").hide(); // 모달대화상자의 3개버튼을 모두 보이지않게한다.
						$("#btn_replySave").show();

						$("#replyModal").modal('hide');

						let url = "/replies/pages/" + brd_num + "/" + replyPage + ".json"; // 댓글목록및 페이징정보 요청주소

						getPage(url);
					}
					}
				});
			});	


			//댓글목록에서 수정버튼 클릭시
			$("#replyList").on("click", "button[name='btnModalModify']", function(){
				//console.log("댓글수정");

				//현재 선택된 댓글내용을 읽어와서, 모달대화상자에 보여준다.
				let rno = $(this).parents("div.box-body").find("input[name='rno']").val();
				let replyer = $(this).parents("div.box-body").find("input[name='replyer']").val();;
				let reply = $(this).parents("div.box-body").find("textarea[name='reply']").val();;
				let replydate = $(this).parents("div.box-body").find("span").html();;

				/*
				console.log(rno);
				console.log(replyer);
				console.log(reply);
				console.log(replydate);
				*/

				//Modal Dialog의 내용을 수정
				$("#replyTitle").html("Modify Reply");
				$("#rno").val(rno);
				$("#replyer").val(replyer);
		        $("#replyer").attr("readonly", true);

				$("#reply").val(reply);

				$(".btnModal").hide(); // 모달대화상자의 3개버튼을 모두 보이지않게한다.
				$("#btn_replyModify").show();

				$("#replyModal").modal('show');
      });
			
		    //댓글목록에서 삭제버튼 클릭
		      $("#replyList").on("click", "button[name='btnModalDelete']", function(){
		        console.log("댓글삭제");

		        //현재 선택된 댓글내용을 읽어와서, 모달대화상자에 보여준다.
		        let rno = $(this).parents("div.box-body").find("input[name='rno']").val();
		        let replyer = $(this).parents("div.box-body").find("input[name='replyer']").val();;
		        let reply = $(this).parents("div.box-body").find("textarea[name='reply']").val();;
		        let replydate = $(this).parents("div.box-body").find("span").html();;

		        /*
		        console.log(rno);
		        console.log(replyer);
		        console.log(reply);
		        console.log(replydate);
		        */

		        //Modal Dialog의 내용을 보여줌.
		        $("#replyTitle").html("Delete Reply");
		        $("#rno").val(rno);
		        $("#replyer").val(replyer);
		        $("#replyer").attr("readonly", true);
		        $("#reply").val(reply);

		        $(".btnModal").hide(); // 모달대화상자의 3개버튼을 모두 보이지않게한다.
		        $("#btn_replyDelete").show();

		        $("#replyModal").modal('show');
		      });
			
			
		//댓글 작성하기 띄우기
		  $("#btn_replyWrite").on("click", function(){
			$("#replyTitle").html("New Reply");
			$("#rno").val("");
			$("#replyer").val("");
	        $("#replyer").attr("readonly", false);
			$("#reply").val("");
		
			$(".btnModal").hide(); // 모달대화상자의 3개버튼을 모두 보이지않게한다.
			$("#btn_replySave").show(); // Save 보여주기
			$("#replyModal").modal('show'); // 모달창 화면에 보이기
			
		
        });
		
	      

		//댓글저장하기
        $("#btn_replySave").on("click", function(){

			let replyer = $("#replyer").val(); //작성자
			let reply = $("#reply").val(); // 댓글내용

			//자바스크립트 Object구문
			let replyObj = { brd_num:${board.brd_num }, replyer:replyer, reply:reply };
			// 서버측에 보낼 데이타를 JSON문법구조의 문자열로 변환작업.  {"bno":5120,"replyer":"user01","reply":"댓글테스트"}
			let replyStr = JSON.stringify(replyObj);

			console.log(replyStr);

			$.ajax({
			type: 'post',
			url: '/replies/new',
			headers: {
				"Content-Type" : "application/json", "X-HTTP-Method-Override" : "POST"
			},
			dataType: 'text', // 매핑주소의 리턴값 타입
			data: replyStr, // 전송데이타 {"bno":5120,"replyer":"user01","reply":"댓글테스트"}
			success: function(result) {
				if(result == "success"){
				alert("댓글 데이타 삽입 성공");
				}
			}
		});
	});
});
</script>

  <!-- 핸들바작업2. handlebar template : 목록작업-->
  <script id="reply-template" type="text/x-handlebars-template">
    {{#each .}}
	
    <div class="box-body">

      <div class="form-group row">
        <label for="replyer" class="col-3 col-form-label">작성자</label><br>

        <div class="col-5">		  
          <input type="text" class="form-control" name="rno" value="{{rno}}" readonly>
          <input type="text" class="form-control" name="replyer" value="{{replyer}}" readonly>
        </div>
        <label for="reply" class="col-4 col-form-label">등록일: <span>{{prettifyDate replydate}}</span></label>
      </div>
      <div class="form-group row">
        <div class="col-8">
          <textarea class="form-control" name="reply" rows="3" readonly>{{reply}}</textarea>
        </div>
        <div class="col-4">
          <button type="button" name="btnModalModify" class="btn btn-primary value="수정">수정</button><br>
          <button type="button" name="btnModalDelete" class="btn btn-primary value="삭제">삭제</button>
        </div>
      </div>
  </div>
    <hr>
    {{/each}}
  </script>
  <script>
    //핸들바 작업3.(선택) 사용자정의 Helper. 용도: 댓글 작성일 밀리세컨드 데이타를 날짜포맷을 변환(2022/06/27)
    Handlebars.registerHelper("prettifyDate", function(timeValue){

      const date = new Date(timeValue);
      return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();
    });
  </script>
  
   <script>
      // 댓글목록 요청작업(댓글+페이징기능)
      let brd_num = ${board.brd_num};  //본문 글번호
      let replyPage = 1;

      let url = "/replies/pages/" + brd_num + "/" + replyPage + ".json"; // 댓글목록및 페이징정보 요청주소

      getPage(url);

      //1)댓글목록출력작업
      //파라미터 설명
      /*
      replyData : 댓글목록 데이타
      target : 댓글이 삽입될 태그위치
      templateObj : 핸들바 템플릿 참조객체
      */
      let printData = function(replyData, target, templateObj) {
        // 핸들바 작업4.
        let template = Handlebars.compile(templateObj.html());
        let html = template(replyData);
        target.empty(); //댓글이 삽입되는 태그의 내용을 제거
        target.append(html); // 댓글이 삽입되는 위치에 결과를 자식으로 추가함.
      }

      //2)댓글페이징출력작업
      // pageMaker : 페이징정보, target : 출력될 위치.
      let printPaging = function(pageMaker, target){
        /*
          <nav aria-label="Page navigation example">
          <ul class="pagination">
            <li class="page-item"><a class="page-link" href="#">Previous</a></li>
            <li class="page-item"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">Next</a></li>
          </ul>
        </nav>
        */
       let pagingStr = '<nav aria-label="Page navigation example">';
           pagingStr += '<ul class="pagination">';

          //이전표시작업
          if(pageMaker.prev) {
            pagingStr += '<li class="page-item"><a class="page-link" href="' + (pageMaker.startPage - 1) ;
            pagingStr += '">이전</a></li>';
          }

          //페이지번호 표시작업.  
          for(let i=pageMaker.startPage; i<=pageMaker.endPage; i++) {
            let strClass = (pageMaker.cri.pageNum == i) ? 'active': ''; //페이지번호중 선택된 현재페이지 style  
            pagingStr += '<li class="page-item ' + strClass + '"><a class="page-link" href="' + i + '">' + i + '</a></li>';
          }

          //다음표시작업
          if(pageMaker.next) {
            pagingStr += '<li class="page-item"><a class="page-link" href="' + (pageMaker.endPage + 1) ;
            pagingStr += '">다음</a></li>';
          }


           pagingStr += '</ul>';
           pagingStr += '</nav>';

		   console.log(pagingStr);
           // target변수가 가리키는 위치에 pagingStr변수의 내용을 삽입한다.
           target.html(pagingStr);
      }




      //작업1>ajax구문으로 요청하는 작업. 댓글목록을 요청하는 메인 시작함수.
      function getPage(url){
		    console.log(url);
        $.getJSON(url, function(data){

          //console.log(data);
          /*
            1)data.list : 댓글목록데이타, 2)data.pageMaker : 댓글페이징정보
          */

           //1)댓글목록 출력기능 함수
          printData(data.list, $("#replyList"), $("#reply-template"));

          //2)댓글페이징출력기능 함수
          printPaging(data.pageMaker, $("#replyPaging"));
        });
      }


    </script>
  </body>
</html>

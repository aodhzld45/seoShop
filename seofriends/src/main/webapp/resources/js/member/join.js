/* 회원가입 */

//html문서와 내용을 브라우저가 읽고 난 이후에 동작되는 특징.
$(document).ready(function(){

	// 회원가입 저장하기.
	$("joinSend").on("click", function() {
		let joinForm = $("#");

		console.log("회원정보 저장하기");

		// 유효성 검사 작업.

		joinForm.submit();
	});

	// 아이디 중복체크
	$("#btnIdCheck").on("click", function() {

		console.log("중복체크 클릭")

		if ($("#mem_id").val() == "") {
			alert("아이디를 입력해주세요.");
			$("#mem_id").focus();
			return;
		}

		


	});

});
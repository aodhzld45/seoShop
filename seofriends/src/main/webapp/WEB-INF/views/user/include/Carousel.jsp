<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <link rel="stylesheet" href=/css/slideshow.css >
    
 <script>
 $(document).ready(function(){
	 $(".slide_div").slick({
			dots: true,
			autoplay : true,
			autoplaySpeed: 5000
		});
	});
 </script>
 
 <div class="content_area">
 	<div class="slide_div_wrap">
				<div class="slide_div">
					<div>
						<a>
							<img src="../resources/image/main1.jpg">
						</a>
					</div>
					<div>
						<a>
							<img src="../resources/image/main2.jpg">
						</a>
					</div>
					<div>
						<a>
							<img src="../resources/image/main3.jpg">
						</a>
					</div>				
				</div>	
			</div>
 </div>


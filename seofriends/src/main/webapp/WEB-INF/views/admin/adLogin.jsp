<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>  
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>SEO Friends | Admin Login</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">  
  	<%@ include file="/WEB-INF/views/admin/include/plugin1.jsp" %>
	<link rel="stylesheet" href="/css/adLogin.css">

</head>
<body>
<div class="main">
    <div class="container">
	
		<div class="middle">
     <div id="login">
       <form action="/admin/adLoginPost" method="post">
          <fieldset class="clearfix">
           <p><span class="fa fa-user"></span>
           <input type="text" id="admin_id" name="admin_id" Placeholder="Username" required></p>
            <!-- JS because of IE support; better: placeholder="Username" -->
			<p><span class="fa fa-lock"></span>
			<input type="password" id="admin_pw" name="admin_pw"  Placeholder="Password" required></p> 
			
		<!-- JS because of IE support; better: placeholder="Password" -->
			<div>
				<span style="width:48%; text-align:left;  display: inline-block;">
				<a class="small-text" href="#">Forgot password?</a></span>
	         	<span style="width:50%; text-align:right;  display: inline-block;">
	         	<input type="submit" value="로그인"></span>
	        </div>
          </fieldset>
<div class="clearfix"></div>
        </form>

        <div class="clearfix"></div>
        
      </div>
      
      		 <!-- end login -->
      		<div class="logo">SEO&nbsp;Friends&nbsp;Admin&nbsp;LOGIN  
         		<div class="clearfix"></div>
      		</div>
      	</div>
	</div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- ================================================================================================ --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%-- ================================================================================================ --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<link rel="stylesheet" href="<c:url value='/public/bootstrap/css/bootstrap.css' />">
<link rel="stylesheet" href="<c:url value='/public/bootstrap/css/bootstrap-responsive.css' />">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="<c:url value='/public/bootstrap/js/bootstrap.min.js' />"></script>
<style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

</style>

</head>
<body>
	
	<div class="container">
		
       	<div class="alert alert-error" style="width:300px; margin: 0 auto 20px;">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
	  		<strong>Error!</strong> Wrong login information!
		</div>
        <form name="loginForm" id="loginForm" action="<c:url value="/auth.action?login=1" />" class="form-signin" method="post">
            <h2 class="form-signin-heading">Please sign in</h2>
            <input type="text" name="account" class="input-block-level" placeholder="account" required>
            <input type="password" name="password" class="input-block-level" placeholder="Password" required>
            <label class="checkbox">
                <input type="checkbox" value="remember-me">
                Remember me
            </label>
            <button class="btn btn-large btn-primary" type="submit">Login</button>
            <a href="#" class="btn btn-link" >Forgot Password?</a>
        </form>

    </div>
</body>
</html>
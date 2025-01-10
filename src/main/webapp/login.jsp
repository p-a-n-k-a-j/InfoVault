<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    HttpSession emailSession = request.getSession(false);
    if(emailSession !=null && emailSession.getAttribute("userEmail")!=null){
    	response.sendRedirect("home.jsp");
    }
    %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Data Manager</title>
<link rel="Stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<main>
<section class="wrapper">
<section class="image"><h2>Go to DashBoard.</h2></section>
<section class="form-container">
<h2 class="form-heading">Login here.</h2>


<form action="./login" method="post">
<section class="group">
<label class="label-style">Username</label><br>
<input id="email" class="input-style" type="email" name="email" required="required" placeholder="Email addres..">
<%if(request.getAttribute("wrong-email") !=null) {%>
<span id="emailError" class="error"><%=request.getAttribute("wrong-email") %></span>
<%} %>
</section>

<section class="group">

<label class="label-style">Password</label>
<img  src="./images/hide.png" class="eye1" id="hide">
<img  src="./images/view.png" class="eye1" id="view" style="display:none;">
<input  id="pass" class="input-style" type="password" name="password" placeholder="Password..">
<%if(request.getAttribute("wrong-pass") !=null) {%>
<span id="passError" class="error"><%=request.getAttribute("wrong-pass") %></span>
<%} %>
</section>

<section class="group">
<button type="submit" onclick="validate()" id="submit" class="submit"> Log in</button>

<p class="info"> Don't have an account? <a href="index.jsp">click here</a></p>
</section>

</form>
</section>

</section>

</main>
<script>
const passError = document.getElementById('passError');
const emailError = document.getElementById('emailError');
if(passError !== null){
	document.getElementById('pass').style.borderBottomColor = 'red';
}
if(emailError !== null){
	document.getElementById('email').style.borderBottomColor = 'red';
}
</script>
<script src="./jsfile/login.js"></script>
</body>
</html>

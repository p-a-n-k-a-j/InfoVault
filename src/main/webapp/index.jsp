<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Data Manager</title>
<link rel="Stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<main>
<section class="wrapper">
<section class="image"><h2>Create your Account.</h2></section>
<section class="form-container">
<h2 class="form-heading">Register</h2>

<%if (request.getAttribute("exist")!=null){ %>
<p class="error-after-submit"><%= request.getAttribute("exist")%></p>
<%} %>
<form action="./register" method="post">

<section class="group">

<label class="label-style1">First Name:</label>
<input   class="input-style1" id="first" type="text" name="fname" placeholder="First Name..">
<label class="label-style2">Last Name:</label>
<input class="input-style2"  id="last" type="text" name="lname" placeholder="Last Name..">
</section>

<section class="group">
<label class="label-style1">Email</label><br>
<input id="email" class="input-style1" type="email" name="email" required="required" placeholder="Email addres..">
<span id="emailError" class="email-error"></span>
</section>

<section class="group">

<label class="label-style1">Password</label>
<img  src="./images/hide.png" class="eye1" id="hide">
<img  src="./images/view.png" class="eye1" id="view" style="display:none;">
<input  id="pass" class="input-style1" type="password" name="password" placeholder="Password..">

<label class="label-style2">Repeate Password</label>
<img  src="./images/hide.png" class="eye2" id="hide2">
<img  src="./images/view.png" class="eye2" id="view2" style="display:none;">
<input id="pass2" class="input-style2" type="password" name="confi-pass" placeholder="Confirm pass..">
<span id="pass-error" class="pass-error"></span>
</section>

<section class="group">
<button type="submit" onclick="validate()" id="submit" class="submit"> Register</button>
<button type="reset" class="clean" id="clean"> Reset</button>
<p class="info"> Already have an account? <a href="login.jsp">click here</a></p>
</section>

</form>
</section>

</section>

</main>

<script src="./jsfile/main.js"></script>
</body>
</html>


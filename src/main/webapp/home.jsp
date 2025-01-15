<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
HttpSession emailSession = request.getSession(false);
if (emailSession == null || emailSession.getAttribute("userEmail") == null) {
    response.sendRedirect("login.jsp");
}
%>
<!DOCTYPE html> 
<html>
<head>
    <meta charset="UTF-8">
    <title>Home page</title>
   <link rel="Stylesheet" href="./css/home.css">
</head>
<body>

<div class="navigation">
<div class="logo">DATA<span class="manager">MANAGER</span></div>
<input type="search" name="search" id="searchqueary" class="searchqueary" placeholder="Search by name or mobile..">
<img src="./images/search.png" class="searchicon" id="searchicon">

<img src="./images/light.png" class="modes" id="light" title="dark mode">
<img src="./images/dark.png" class="modes" id="dark" style="display:none;" title="light mode">
<button id="show" class="btn active">Home</button>
<button id="showtrash" class="btn">Trash</button>
<button id="add" class="btn">+</button>
<a href="./SessionDelete" id="logout" class="logout-link"><button class="logout-btn" >Log Out</button></a>


</div>

<div class="wrapper">
    <!-- Form Container -->
    <div class="container">

        <form  id="insertForm" method="post">
           
              <div id="close" class="close" title="close">X</div>
                <div  id="dynamicField" class="field" >
                    
                    <input type="number" id="idData" hidden="hidden" name="id"  >
                </div>
                <div id="nameField" class="field">
                    <label>Name:</label>
                    <input type="text" id="namedata"   name="name" placeholder="Enter your name">
                </div>
                <div id="mobileField" class="field">
                    <label>Mobile:</label>
                    <input type="number" id="mobiledata"  name="mobile" placeholder="Enter your mobile">
                </div>
                <div class="buttonField">
                <button id="insert" type="submit" class="form-btn">Insert</button>
                <button id="update" type="submit"  class="form-btn" style="display:none;" >Update</button>
          		</div>
        </form>
    </div>

<div class="table-container">

    <table id="employeeTable" border="1">
        <thead>
            <tr >
                <th>Name</th>
                <th>Mobile No</th>
                <th>Actions</th>
                <th id="updatefield">Update</th>
                <th id="restoreaction" style="display:none">Restore</th>
            </tr>
        </thead>
        <tbody>
            <!-- Data will be inserted here by JavaScript -->
        </tbody>
    </table>
</div>
</div>



<script src="./jsfile/forField.js"></script>

</body>
</html>

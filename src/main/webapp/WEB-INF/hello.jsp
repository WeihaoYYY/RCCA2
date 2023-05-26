<%--
  Created by IntelliJ IDEA.
  User: Weihao
  Date: 11/05/2023
  Time: 4:35 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="jump" method="post" enctype="multipart/form-data">
<input type="file" name="file" />

<input type="submit" value="Save">

</form>
</body>
</html>

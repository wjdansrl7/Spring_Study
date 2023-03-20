<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
<%--    jsp 접근법: property 접근법--%>
    <li>id=${member.id}</li>
    <li>id=${member.username}</li>
    <li>id=${member.age}</li>
<%--    <li>id=<%=((Member)request.getAttribute("member")).getId()%></li>--%>
<%--    <li>id=<%=((Member)request.getAttribute("member")).getUsername()%></li>--%>
<%--    <li>id=<%=((Member)request.getAttribute("member")).getAge()%></li>--%>
</ul>
<a href="/index.html">메인</a>
</body>
</html>

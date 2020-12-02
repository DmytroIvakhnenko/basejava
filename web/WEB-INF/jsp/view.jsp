<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
        <b>${contactEntry.key.title}</b>:&nbsp${contactEntry.key.getHtml(contactEntry.value)}<br>
        </c:forEach>
    <p>
    <p>
        <c:forEach var="sectionType" items="${resume.sections}">
            <c:choose>
                <c:when test="${sectionType.key eq 'ACHIEVEMENT' || sectionType.key eq 'QUALIFICATIONS'}">
                    <b>${sectionType.key.title}:</b><br>
                    <c:forEach var="listEntry" items="${sectionType.value.items}">
                        - ${listEntry}<br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <b>${sectionType.key.title}:</b>&nbsp${sectionType.value}<br>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

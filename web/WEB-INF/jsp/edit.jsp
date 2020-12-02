<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="action" value="${param.action}">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Full Name:</dt>
            <dd><input type="text" name="fullName" size=40 value="${resume.fullName}"></dd>
        </dl>
        <h3>Contacts:</h3>
        <c:forEach var="type" items="${ContactType.values()}">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.contacts.get(type)}"></dd>
            </dl>
        </c:forEach>
        <!-- Sections -->
        <c:forEach var="sectionType" items="${SectionType.values()}">
            <c:choose>
                <c:when test="${sectionType eq 'EDUCATION' || sectionType eq 'EXPERIENCE'}">
                    <b>${sectionType.title}:</b><br>
                </c:when>
                <c:when test="${sectionType eq 'ACHIEVEMENT' || sectionType eq 'QUALIFICATIONS'}">
                    <dl id="${sectionType}">
                        <dt><b>${sectionType.title}:</b></dt>
                        <br>
                        <c:forEach var="listEntry" items="${resume.sections.get(sectionType).items}" varStatus="count">
                            <dd><input type="text" id="${sectionType}${count.index}" name="${sectionType}"
                                       size=100 value="${listEntry}">
                                <a href="javascript:void(0)" title="Delete field"
                                   onclick="remove('${sectionType}${count.index}')"><img
                                        src="img/delete.png"></a>
                            </dd>
                        </c:forEach>
                    </dl>
                    <a href="javascript:void(0)" title="Add field" onclick="add('${sectionType}')"><img
                            src="img/add.png"></a>
                    <br>
                </c:when>
                <c:otherwise>
                    <dl>
                        <dt><b>${sectionType.title}:</b></dt>
                        <dd><input type="text" name="${sectionType}" size=100
                                   value="${resume.sections.get(sectionType)}"></dd>
                    </dl>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Save changes</button>
        <button onclick="window.history.back()">Discard</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
<script>
    function add(elementId) {
        var parent = document.getElementById(elementId);
        var count = parent.getElementsByTagName("dd").length
        var index = count;
        //find free to use index if possible
        for (var i = 0; i < count; i++) {
            if (document.getElementById(elementId + i) == null) {
                index = i;
            }
        }
        //saving values in inputs
        var elems = parent.getElementsByTagName("input");
        for (var i = 0; i < elems.length; i++) {
            // set attribute to property value
            elems[i].setAttribute("value", elems[i].value);
        }
        //add new input
        insertValue = "<dd>\n" +
            "<input type=\"text\" id=\"" + elementId + index + "\" name=\"" + elementId + "\" size=100 value=\"\">\n" +
            "<a href=\"javascript:void(0)\" title=\"Delete field\"" + "onclick=\"remove('" + elementId + index + "')\"><img src=\"img/delete.png\"></a>\n" +
            "</dd>";
        parent.innerHTML += insertValue;
    }

    function remove(elementId) {
        var element = document.getElementById(elementId);
        element.parentElement.remove();
    }
</script>
</body>
</html>

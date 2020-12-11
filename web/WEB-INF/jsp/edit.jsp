<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
        <jsp:useBean id="position" class="ru.javawebinar.basejava.model.Experience" scope="request"/>
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
                <!-- Contacts -->
                <h3>Контакты:</h3>
                <c:forEach var="type" items="${ContactType.values()}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type.name()}" size=30 value="${resume.contacts.get(type)}"></dd>
                    </dl>
                </c:forEach>
                <!-- Sections -->
                <c:forEach var="sectionType" items="${SectionType.values()}">
                    <h3>${sectionType.title}:</h3>
                    <c:choose>
                        <c:when test="${sectionType eq 'EDUCATION' || sectionType eq 'EXPERIENCE'}">
                            <a href="javascript:void(0)" title="Add experience" onclick="addExperience('${sectionType}')"><img alt="Add" src="img/add.png"></a>
                            <dl id ="${sectionType}">
                            <c:forEach var="experience" items="${resume.sections.get(sectionType).experienceList}" varStatus="exp">
                                <c:set var = "pfx" scope="request" value="${sectionType}${exp.index}"/>
                                <dl id="${pfx}">
                                    <dt><b>Organization: <a href="javascript:void(0)" title="Delete place"
                                                           onclick="remove('${pfx}')"><img alt="Delete" src="img/delete.png"></a></b></dt>
                                    <dd><input type="text" id="${pfx}.place" name="${sectionType}.place" size=50
                                               value="${experience.place.name}" required></dd>
                                    <dt><b>Homepage:</b></dt>
                                    <dd><input type="text" id="${pfx}.homepage" name="${sectionType}.homepage" size=50
                                               value="${experience.place.homepage}"></dd>
                                    <br>
                                    <a href="javascript:void(0)" title="Add position" onclick="addPosition('${pfx}')"><img
                                            src="img/add.png"></a>
                                    <c:forEach var="position" items="${experience.positions}" varStatus="pos">
                                        <dl id="${pfx}.positions${pos.index}">
                                            <dt>Position: <a href="javascript:void(0)" title="Delete position"
                                                             onclick="removePosition('${pfx}.positions${pos.index}')"><img alt="Delete" src="img/delete.png"></a></dt>
                                            <dd><input type="text" id="${pfx}.position${pos.index}" name="${pfx}.position" size=50
                                                       value="${position.position}" required></dd>
                                            <dt>Description:</dt>
                                            <dd><input type="text" id="${pfx}.description${pos.index}" name="${pfx}.description" size=50
                                                       value="${position.description}"></dd>
                                            <br>
                                            <dt>Start date:</dt>
                                            <dd><input type="text" id="${pfx}.startDate${pos.index}" name="${pfx}.startDate" size=10
                                                       value="${position.startDate}" placeholder="YYYY-MM" required></dd>
                                            <dt>End date:</dt>
                                            <dd><input type="text" id="${pfx}.endDate${pos.index}" name="${pfx}.endDate" size=10
                                                       value="${position.endDate}" placeholder="YYYY-MM" required></dd>
                                        </dl>
                                    </c:forEach>
                                </dl>
                            </c:forEach>
                            </dl>
                        </c:when>
                        <c:when test="${sectionType eq 'ACHIEVEMENT' || sectionType eq 'QUALIFICATIONS'}">
                            <a href="javascript:void(0)" title="Add field" onclick="addField('${sectionType}')"><img alt="Add" src="img/add.png"></a>
                            <dl id="${sectionType}">
                                <c:forEach var="listEntry" items="${resume.sections.get(sectionType).items}"
                                           varStatus="count">
                                    <dd id="${sectionType}${count.index}"><input type="text" id="${sectionType}${count.index}" name="${sectionType}"
                                               size=100 value="${listEntry}">
                                        <a href="javascript:void(0)" title="Delete field"
                                           onclick="remove('${sectionType}${count.index}')"><img alt="Delete" src="img/delete.png"></a>
                                    </dd>
                                </c:forEach>
                            </dl>
                        </c:when>
                        <c:otherwise>
                            <dl>
                                <dd><input type="text" name="${sectionType}" size=100
                                           value="${resume.sections.get(sectionType)}"></dd>
                            </dl>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <hr>
                <button type="submit">Save changes</button>
                <button type="button" onclick="window.history.back()">Discard</button>
                <button type="reset">Reset</button>
            </form>
        </section>
        <jsp:include page="fragments/footer.jsp"/>
        <script src="js/listsHandler.js" type="text/javascript"></script>
    </body>
</html>

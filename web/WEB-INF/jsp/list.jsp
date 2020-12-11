<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <title>List of resumes:</title>
    </head>
    <body>
        <jsp:include page="fragments/header.jsp"/>
        <section>
            <br>
            <table>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach var="resume" items="${allResumes}">
                    <tr>
                        <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                        <td>${ContactType.EMAIL.getHtml(resume.contacts.get(ContactType.EMAIL))}</td>
                        <td class="td-icons"><a href="resume?uuid=${resume.uuid}&action=delete"><img alt="Delete" src="img/delete.png"></a>
                        </td>
                        <td class="td-icons"><a href="resume?uuid=${resume.uuid}&action=edit"><img alt="Edit" src="img/pencil.png"></a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td><a href="resume?action=add"><img alt="Add" src="img/add.png" title="Add resume"></a></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </section>
        <jsp:include page="fragments/footer.jsp"/>
    </body>
</html>

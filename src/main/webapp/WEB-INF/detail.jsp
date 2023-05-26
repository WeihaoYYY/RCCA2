<%--
  Created by IntelliJ IDEA.
  User: Weihao
  Date: 12/05/2023
  Time: 10:48 am
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Shiqi
  Date: 2023/5/11
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Details</title>
    <link href="/static/css/style.css" rel="stylesheet" />
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
    />

    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

    <script type= text/javascript>
        function openFile(url) {
            window.open(url, 'newwindow', 'width=5000,height=3000,screenX=300,screenY=100');
        }
    </script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a href="${pageContext.request.contextPath}/item/index">
            <img
                src="/static/asset/newLogo.png"
                class="logo"
            />
        </a>
        <button
                class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent"
                aria-expanded="false"
                aria-label="Toggle navigation"
        >
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse" id="navbarSupportedContent" style="display: flex; justify-content: flex-end;">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item" style="">
                    <!-- all the category will in one upload page, they have the same form, I will create later. -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/item/ut1">Upload</a>
                </li>
                <li class="nav-item">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/index">Admin Panel</a>
                </li>

                <!-- <li class="nav-item dropdown">
                  <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Upload
                  </a>
                  <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="#">Advertisements</a></li>
                    <li><a class="dropdown-item" href="#">Photos</a></li>
                    <li><a class="dropdown-item" href="#">News Articles</a></li>
                    <li><a class="dropdown-item" href="#">Sales Records</a></li>
                    <li><a class="dropdown-item" href="#">Sales Brochures</a></li>
                  </ul>
                </li> -->

                <!-- <li class="nav-item">
                  <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                </li> -->
            </ul>
            <!-- <form class="d-flex">
              <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
              <button class="btn btn-outline-success" type="submit">Search</button>
            </form> -->
        </div>
    </div>
</nav>

<section>
    <div style="margin:20px;">
        <table class="content">
                <tr class="body">
                    <td class="pic">
                        <c:set var="jspFormat" value="jsp" />
                        <c:set var="pngFormat" value="png" />
                        <c:set var="fileFormat" value="${ad.file_format}" />
                        <c:set var="fileFormatLc" value="${fn:toLowerCase(fileFormat)}" />

                        <c:choose>
                            <c:when test="${fileFormatLc eq 'jpg' or fileFormatLc eq 'png'}">
                                <a onclick="openFile('${ad.file_path}'); return false;"><img src="${ad.file_path}" title="Click to view"></a>
                            </c:when>
                            <c:otherwise>
                                <a onclick="openFile('${ad.file_path}'); return false;"><img src="/static/asset/documentImage.png" class="notImage" title="Click to view"></a>
                            </c:otherwise>
                        </c:choose>


                        <div class="d-flex justify-content-center">
                            <button class="btn btn-secondary" onclick="openFile('${ad.file_path}'); return false;">View</button>
                        </div>
                    </td>

                    <td class="detail">
                        <div class="heading">${ad.title}</div>
                        <div>
                            <table>
                                <tr>
                                    <td class="label">Description:</td>
                                    <td colspan="3">${ad.description}</td>
                                </tr>
                                <tr>
                                    <td class="label">Publisher:</td>
                                    <td>${ad.publisher}</td>
                                    <td class="label">Publish Date:</td>
                                    <td>${ad.publish_date}</td>
                                </tr>
                                <tr>
                                    <td class="label">Subject:</td>
                                    <td>${ad.subject}</td>
                                    <td class="label">Car model:</td>
                                    <td>${ad.car_model}</td>
                                </tr>
                                <tr>
                                    <td class="label">Language:</td>
                                    <td>${ad.language}</td>
                                    <td class="label">Identifier:</td>
                                    <td>${ad.identifier}</td>
                                </tr>
                                <tr>
                                    <td class="label">Creator:</td>
                                    <td>${ad.creator}</td>
                                    <td class="label">Contributor email:</td>
                                    <td>${ad.creator_email}</td>
                                </tr>
                                <tr>
                                    <td class="label">Contributor:</td>
                                    <td>${ad.contributor}</td>
                                    <td class="label">Rights:</td>
                                    <td>${ad.rights}</td>
                                </tr>
                                <tr>
                                    <td class="label">Source:</td>
                                    <td>${ad.source}</td>
                                    <td class="label">Relation:</td>
                                    <td>${ad.relation}</td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
        </table>
    </div>

    <hr />
</section>


<!-- interest part -->
<h5>You may also be interested in</h5>
<!-- <hr /> -->
<div class="interest_container">
    <!-- item1 -->
    <div class="col d-flex justify-content-center interest_item">
        <div class="card mb-3 mt-3">
            <div class="row g-0">
                <div class="col-md-4">
                    <img
                            src="http://rovercarclubaust.asn.au/wp-content/uploads/2015/01/rccabadge.jpg"
                            class="img-fluid rounded-start"
                            alt="..."
                            width="20px"
                            height="20px"
                    />
                </div>
                <div class="col-md-12">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="result1.html" class="titlelink"
                            >"Rover One Of Britains Fine Cars"</a
                            >
                        </h5>
                        <p class="card-text">Author: Herald</p>
                        <p class="card-text">
                            <small class="text-muted">24/06/1949</small>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- item2 -->
    <div class="col d-flex justify-content-center interest_item">
        <div class="card mb-3 mt-3">
            <div class="row g-0">
                <div class="col-md-4">
                    <img
                            src="http://rovercarclubaust.asn.au/wp-content/uploads/2015/01/rccabadge.jpg"
                            class="img-fluid rounded-start"
                            alt="..."
                            width="20px"
                            height="20px"
                    />
                </div>
                <div class="col-md-12">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="result1.html" class="titlelink"
                            >"Rover One Of Britains Fine Cars"</a
                            >
                        </h5>
                        <p class="card-text">Author: Herald</p>
                        <p class="card-text">
                            <small class="text-muted">24/06/1949</small>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- item3 -->
    <div class="col d-flex justify-content-center interest_item">
        <div class="card mb-3 mt-3">
            <div class="row g-0">
                <div class="col-md-4">
                    <img
                            src="http://rovercarclubaust.asn.au/wp-content/uploads/2015/01/rccabadge.jpg"
                            class="img-fluid rounded-start"
                            alt="..."
                            width="20px"
                            height="20px"
                    />
                </div>
                <div class="col-md-12">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="result1.html" class="titlelink"
                            >"Rover One Of Britains Fine Cars"</a
                            >
                        </h5>
                        <p class="card-text">Author: Herald</p>
                        <p class="card-text">
                            <small class="text-muted">24/06/1949</small>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
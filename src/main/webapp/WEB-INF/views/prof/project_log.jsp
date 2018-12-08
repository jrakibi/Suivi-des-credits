<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE>
<html>
<head>
	<title>Admin/Profil</title>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href='<c:url value="/resources/css/bootstrap-3.3.7.css" />' >
    <link rel="stylesheet" href='<c:url value="/resources/css/style.css" />' >
</head>
<body data-ng-app="my-app"  data-ng-controller="Ctrl">

        <div class="wrapper">
            <!-- Page Content Holder -->
            <div id="content" class="container">
                <nav class="navbar navbar-default">
                    <div class="container-fluid">
                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav navbar-right">
                                <li><a href="#">Page</a></li>
                                <li><a href="#">Page</a></li>
                                <li><a href="#">Page</a></li>
                                <li><a href="#">Page</a></li>
                            </ul>
                        </div>
                    </div>
                </nav>				
				<div class="row">
					<div class="col-md-11 col-md-offset-1">
						<c:if test="${!empty logs}">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Professor</th>
										<th>Rubric</th>
										<th>ChildRubric</th>
										<th>Contribution</th>
										<th>Date Contribution</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${logs}" var="log">
										<tr>
											<td> ${log.professor} </td>
											<td> ${log.rubric} </td>
											<td> ${log.childRubric} </td>
											<td> ${log.contribution} </td>
											<td> ${log.date_contribution} </td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						
						<c:if test="${empty logs}">
							<p>Data not found !</p>
						</c:if>
					</div>
					<div class="col-md-12 col-md-offset-1">
						<a href='<c:url value="/prof/profil" />' class="col-md-3 btn btn-default" >Go Back</a>
					</div>
				</div> 					
            </div>
        </div>
		
    
    <!-- /#wrapper -->
    <script src='<c:url value="/resources/js/jquery-3.3.1.js" />' ></script>
    <script src='<c:url value="/resources/js/bootstrap-3.3.7.js" />'></script>
    
	<!--angularJs -->
    <script src='<c:url value="/resources/js/angular.min.js" />' ></script>
    
    <script>
	    $('#sidebarCollapse').click(function (e) {
	        $('#sidebar').toggleClass('active');
        });
        
		var app = angular.module("my-app", []); // angular call
		app.controller("Ctrl", function($scope, $http) {

		});
    </script>
</body>
</html>
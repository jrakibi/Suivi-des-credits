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
            <!-- Sidebar Holder -->
            <nav id="sidebar">
                <div class="sidebar-header">
                    <h3>SideBar</h3>
                </div>

                <ul class="list-unstyled components">
                    <p>Credits Flow</p>
                    <li class="active">
                        <a href='<c:url value="/admin/profil" />'>DASHBOARD</a>
                    </li>
                    <li>
                        <a href='<c:url value="/admin/mng_profs" />'>Professors management</a>
                    </li>
                    <li>
                        <a href='<c:url value="/admin/mng_teams" />'>Teams management</a>
                    </li>
                    <li>
                        <a href='<c:url value="/admin/mng_projects" />'>Project management</a>
                    </li>
                </ul>

                <ul class="list-unstyled CTAs">
                    <li><a href="#" class="download">Log out</a></li>
                </ul>
            </nav>

            <!-- Page Content Holder -->
            <div id="content" class="container">
                <nav class="navbar navbar-default">
                    <div class="container-fluid">

                        <div class="navbar-header">
                            <button type="button" id="sidebarCollapse" class="btn btn-info navbar-btn">
                                <i class="glyphicon glyphicon-align-left"></i>
                                <span>Toggle Sidebar</span>
                            </button>
                        </div>

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
					<div class="col-md-12">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Project</th>
									<th>Team</th>
									<th>Amount</th>
									<th>Year</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${dashboard}" var="d">
									<tr>
										<td> <a href='<c:url value="/admin/detail_project?id_project=${d.id_projet}" />' style="color: green" target='_blank'  >${d.project}</a> </td>
										<td> ${d.team} </td>
										<td> ${d.amount} </td>
										<td> ${d.year} </td>
										<td> <a class="btn btn-danger" href='<c:url value="/admin/log_project?id_project=${d.id_projet}" />' >View Log</a> </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div> 					
            </div>
        </div>
		
    
    <!-- /#wrapper -->
    <script src='<c:url value="/resources/js/jquery-3.2.1.js" />' ></script>
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
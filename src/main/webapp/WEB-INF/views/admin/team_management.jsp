<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE>
<html>
<head>
	<title>Admin/Manage Teams</title>
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
                    <li>
                        <a href='<c:url value="/admin/profil" />'>DASHBOARD</a>
                    </li>
                    <li>
                        <a href='<c:url value="/admin/mng_profs" />'>Professors management</a>
                    </li>
                    <li class="active">
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
					<form name="TeamForm" class="col-md-12" action='<c:url value="/admin/mng_teams" />' method="post" novalidate="novalidate">
						<div class="col-md-9 col-md-offset-1">
							<c:if test="${!empty result}">
								<c:choose>
									<c:when test="${result == 'done' }">
										<div class="alert alert-success alert-dismissible">
										  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
										  <strong>Well Done!</strong> Team saved
										</div>
									</c:when>
									<c:otherwise>
										<div class="alert alert-danger alert-dismissible">
										  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
										  <strong>Error!</strong>${result}.
										</div>
									</c:otherwise>
								</c:choose>
							</c:if>
							<div class="form-group">
								<legend>Teams management</legend>
						    	<label for="name">Team name</label>
								<input type="text" name="name" class="form-control" data-ng-model="name" data-ng-required="true" />	
							    <div data-ng-if="TeamForm.name.$touched && TeamForm.name.$invalid">
									<small style="color: red;" class="form-text" >Enter a valid Name Please !</small>
								</div>								
							</div>
							<div class="form-group">
						    	<label for="code">Team Code(Unique)</label>
								<input type="text" name="code" class="form-control" data-ng-model="code" data-ng-required="true" />	
							    <div data-ng-if="TeamForm.code.$touched && TeamForm.code.$invalid">
									<small style="color: red;" class="form-text" >Enter a valid Code Please !</small>
								</div>									
							</div>
							<div class="form-group">
								<label for="members">Team members</label>
								<select multiple class="form-control" data-ng-model="members" name="members" data-ng-required="true">
									<c:forEach items="${profs}" var="p" >
										<option value="${p.name}" > ${p.name} ${p.lastName} </option>
									</c:forEach>
								</select>
								{{members.length}}
							    <div data-ng-if="TeamForm.members.$touched && TeamForm.members.$invalid">
									<small style="color: red;" class="form-text" >A team must have at least one or more member !</small>
								</div>									
								<strong> <p>Members: {{members}} </p> </strong>
							</div>
							<div class="form-groupe">
								 <button type="submit" class="btn btn-primary col-md-3" data-ng-disabled="TeamForm.$invalid">GO</button>
							</div>
						</div>
					</form>
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
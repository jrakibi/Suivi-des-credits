<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE>
<html>
<head>
	<title>Admin/Manage projects</title>
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
                    <li>
                        <a href='<c:url value="/admin/mng_teams" />'>Teams management</a>
                    </li>
                    <li class="active">
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
					<form class="row" name="Projectform" action='<c:url value="/admin/mng_projects" />' method="post">
						<div class="col-md-9 col-md-offset-1">
							<c:if test="${!empty result}">
								<c:choose>
									<c:when test="${result == 'done' }">
										<div class="alert alert-success alert-dismissible">
											 <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
										 <strong>Well Done!</strong> Project successfully saved
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
								<legend>Projects management</legend>
						    	<label for="name">Label</label>
								<input type="text" name="name" class="form-control" data-ng-required="true" data-ng-model="name" />					
							    <div data-ng-if="Projectform.name.$touched && Projectform.name.$invalid">
									<small style="color: red;" class="form-text" >Enter a valid Label Please !</small>
								</div>
							</div>	
							<div class="form-group">
						    	<label for="desc">Description</label>
								<textarea rows="3" name="desc" class="form-control" data-ng-required="true" data-ng-model="desc"></textarea>					
							    <div data-ng-if="Projectform.desc.$touched && Projectform.desc.$invalid">
									<small style="color: red;" class="form-text" >A project must have a description text</small>
								</div>							
							</div>	
							<div class="form-group">
						    	<label for="duration">Duration</label>
								<input type="text" name="duration" class="form-control" data-ng-required="true" data-ng-model="duration" />					
							    <div data-ng-if="Projectform.duration.$touched && Projectform.duration.$invalid">
									<small style="color: red;" class="form-text" >Enter the project duration Please !</small>
								</div>							
							</div>		
							<div class="form-group">
						    	<label for="year">Year</label>
								<input type="text" name="year" class="form-control" data-ng-required="true" data-ng-model="year" />					
							    <div data-ng-if="Projectform.year.$touched && Projectform.year.$invalid">
									<small style="color: red;" class="form-text" >Project developpement year !</small>
								</div>								
							</div>
							<div class="form-group">
						    	<label for="team">Team</label>
								<select name="team" data-ng-model="team" class="form-control" data-ng-required="true">
									<c:forEach items="${teams}" var="team">
										<option value="${team.id}" > ${team.name} </option>
									</c:forEach>
								</select>
							    <div data-ng-if="Projectform.team.$touched && Projectform.team.$invalid">
									<small style="color: red;" class="form-text" >A project must have a team</small>
								</div>									
							</div>	
							<div class="form-group">
								<legend>Rubrics</legend>
						    	<label for="name">Label</label>
								<input type="text" name="rubric" class="form-control" data-ng-required="true" data-ng-model="rubric" />												
							    <div data-ng-if="Projectform.rubric.$touched && Projectform.rubric.$invalid">
									<small style="color: red;" class="form-text" >A project must have at less one rubric</small>
								</div>							
							</div>
							<div class="form-group">
								<legend>Child Rubric</legend>
						    	<label for="name">Child Rubric</label>
								<input type="text" name="childrubric" class="form-control" data-ng-required="true" data-ng-model="childrubric" />					
							    <div data-ng-if="Projectform.childrubric.$touched && Projectform.childrubric.$invalid">
									<small style="color: red;" class="form-text" >A rubric must have at less one childRubric</small>
								</div>	
							</div>			
							<div class="form-group">
						    	<label for="name">Amount</label>
								<input type="text" name="money" class="form-control" data-ng-required="true" data-ng-model="money" />					
							    <div data-ng-if="Projectform.money.$touched && Projectform.money.$invalid">
									<small style="color: red;" class="form-text" >A rubric must have a price !</small>
								</div>	
							</div>	
							<div class="form-groupe">
								 <button type="submit" class="btn btn-primary col-md-3" data-ng-disabled="Projectform.$invalid" >ADD</button>
								 <a href='<c:url value="/admin/mng_rubrics" />' class="col-md-offset-6 col-md-3 btn btn-default" >Manage Rubrics</a>
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
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
                    <p>${prof.name} ${prof.lastName}</p>
                    <li>
                        <a href='<c:url value="/prof/profil" />'>DASHBOARD</a>
                    </li>
                    <li>
                        <a href='<c:url value="/prof/mng_teams" />'>Teams</a>
                    </li>
                    <li class="active">
                        <a href='<c:url value="/prof/fact" />'>Contribute</a>
                    </li>
                </ul>

                <ul class="list-unstyled CTAs">
                    <li><a href='<c:url value="/prof/logout" />' class="download">Log out</a></li>
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
					<form class="col-md-12" name="Cntform" action='<c:url value="/prof/contribution" />' method="post">
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
								<legend>Contribute</legend>
						    	<label for="name">Projects</label>
								<select name="project" class="form-control" data-ng-model="project" data-ng-change="getRubrics()">
									<c:forEach items="${projects}" var="p">
										<option value="${p.id}" > ${p.label} </option>
									</c:forEach>
								</select>
							    <div data-ng-if="Cntform.project.$touched && Cntform.project.$invalid">
									<small style="color: red;" class="form-text" >You must chose a project first !</small>
								</div>
							</div>	
							<div class="form-group">
						    	<label for="name">Project's rubrics</label>
								<select name="rubric" class="form-control" data-ng-model="rubric" data-ng-change="getChilds()" >
									<option data-ng-repeat="rubric in rubrics" value="{{rubric.id}}" > {{rubric.name}} </option>
								</select>
							    <div data-ng-if="Cntform.rubric.$touched && Cntform.rubric.$invalid">
									<small style="color: red;" class="form-text" >Select a rubric please !</small>
								</div>							
							</div>	
							<div class="form-group">
						    	<label for="child">Childs</label>
								<select name="child" class="form-control" data-ng-model="child" data-ng-change="update()" >
									<option data-ng-repeat="child in childs" value="{{child.id}}" > {{child.name}} </option>
								</select>
							    <div data-ng-if="Cntform.child.$touched && Cntform.child.$invalid">
									<small style="color: red;" class="form-text" >Select a child rubric please !</small>
								</div>	
								<p data-ng-if="credit.length > 0" >Price: {{credit}}</p>							
							</div>		
							<div class="form-group">
						    	<label for="contibution">Contribute</label>
								<input type="text" name="contribution" class="form-control" data-ng-required="true" data-ng-model="contibution" />					
							    <div data-ng-if="Cntform.contibution.$touched && Cntform.contibution.$invalid">
									<small style="color: red;" class="form-text" >Enter your contribution part !</small>
								</div>								
							</div>
							<div class="form-group" data-ng-if="credit.length > 0 && contibution.length >0">
						    	<label>Rest is: {{credit - contibution}} </label>		
							    <div data-ng-if=" (credit - contibution)<0 ">
									<small style="color: red;" class="form-text" >Enter a valid number less than child's credit {{credit}} </small>
								</div>						    						
							</div>
							<div class="form-groupe">
								 <button type="submit" class="btn btn-primary col-md-3" data-ng-disabled="Cntform.$invalid || (credit - contibution)<0" >ADD</button>
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
			$scope.rubrics = [];
			$scope.childs = [];
			$scope.credit = 0.0;
			
			$scope.getRubrics = function(){	
					var project = {"project_id" : $scope.project};
					var response = $http.post('loadRubrics', project);
					
					response.then(function(response) {
						$scope.rubrics = []; // set to empty
						$scope.rubrics = response.data;
						$scope.credit = 0.0;
					}, function(error) {
						alert('cannot get data ! '+error);
					}, function(value) {
						
					});	
			};

			$scope.getChilds = function(){	
				var project = {"rubric_id" : $scope.rubric};
				var response = $http.post('loadChilds', project);
				
				response.then(function(response) {
					$scope.childs = []; // set to empty
					$scope.childs = response.data;
					$scope.credit = 0.0;
				}, function(error) {
					alert('cannot get data ! '+error);
				}, function(value) {
					
				});	
			};

			$scope.update = function(){
				var i;
				for(i=0; i<$scope.childs.length; i++){
					if($scope.childs[i].id == $scope.child){
						 $scope.credit = $scope.childs[i].credit;
						 return;
					}
				}
			}
			
		});
    </script>
</body>
</html>
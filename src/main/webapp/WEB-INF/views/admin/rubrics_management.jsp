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
					<form class="col-md-12" name="Projectform" action='<c:url value="/admin/mng_rubrics" />' method="post">
						<div class="col-md-9 col-md-offset-2">
							<c:if test="${!empty result}">
								<c:choose>
									<c:when test="${result == 'done' }">
										<div class="alert alert-success alert-dismissible">
											 <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
										 <strong>Well Done!</strong> Rubric successfully saved
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
								<legend>Rubrics management</legend>
						    	<label for="project">Projects list</label>
								<select name="project" class="form-control" data-ng-model="project" >
									<c:forEach items="${projects}" var="p">
										<option value="${p.id}" > ${p.label} </option>
									</c:forEach>
								</select>
							    <div data-ng-if="Projectform.project.$touched && Projectform.project.$invalid">
									<small style="color: red;" class="form-text" >You must chose a project first !</small>
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
								 <button type="submit" class="btn btn-primary col-md-3">ADD</button>
								 <a href='<c:url value="/admin/mng_projects" />' class="col-md-offset-1 col-md-2 btn btn-info" >Go Back</a>
								 <a href='<c:url value="/admin/mng_childs" />' class="col-md-offset-4 col-md-2 btn btn-default" >Manage Childs</a>
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
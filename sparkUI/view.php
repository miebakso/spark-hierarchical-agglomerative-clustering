<!DOCTYPE html>
<html>
<head>
	<?php  include 'head.php' ?>
	<title>Spark Reduce Data App UI</title>
	<style type="text/css">
	</style>
</head>

<body>
	<?php include 'nav.php' ?>
	<div class="row">
	<div class="col m6 offset-m3">
		<div class="row">
		    <div class="col m12" style="margin-top: 200px;">
		      	<div class="card teal lighten-5">
		        	<div class="card-content black-text">
		          	<span class="card-title">Explore HDFS Directory</span>
		          	<div class="row">
				    <form id="spark-data" class="col m12" action="list.php" method="post">
				      	<div class="row">
				        	<div class="input-field col m12 black-text">
				          		<input id="data_path" type="text" name="data_path" class="validate">
				          		<label for="data_path" >HDFS data path</label>
				        	</div>
				        </div>
				  
				        <button class="btn waves-effect waves-light" type="submit" name="action">Submit
    						<i class="material-icons right"></i>
  						</button>
				    </form>
		      	</div>
		    </div>
		</div>   
	</div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function(){

		$( "#spark-data" ).submit(function( event ) {
  			window.open('localhost:50070/explorer.html#'+$('#data_path').val(), '_blank');
		});
	});
</script>

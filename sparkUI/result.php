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
	<div class="col m4 offset-m4">
		<div class="row">
		    <div class="col m12" style="margin-top: 30px;">
		      	<div class="card teal lighten-5">
		        	<div class="card-content black-text">
		          	<span class="card-title">Submit Successful</span>
		          	<div class="row">
				 	<?php
				 		$jar = $_POST['jar_path'];
				 		$input =  $_POST['input_path'];
				 		$output = $_POST['output_path'];
				 		$executor_number = $_POST['executor_number'];
				 		$executor_memory = $_POST['executor_memory'];
				 		$partition = $_POST['number'];
				 		$max_obj = $_POST['max_obj'];
				 		$type = $_POST['type'];
				 		$cutoff = $_POST['cut_off'];
				 		
				 		$output = shell_exec('cd $SPARK_HOME && ./bin/spark-submit --class main.scala.Main --master yarn /home/miebakso/IdeaProjects/BigData/target/scala-2.11/bigdata_2.11-0.1.jar');
						//echo "<pre>$output</pre>";
				 	?>
				 	</div>
		      	</div>
		    </div>
		</div>   
	</div>
	</div>
</body>
</html>
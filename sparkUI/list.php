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
	<div class="col m3 offset-m1">
		<div class="row">
		    <div class="col m12" style="margin-top: 20px;">
		      	<div class="card teal lighten-5">
		        	<div class="card-content black-text">
				      	<?php 
				      		$output = shell_exec('cd /home/miebakso/hadoop-2.7.3 && ./bin/hadoop fs -ls '.$_POST['data_path']);
				      		$arr = explode("\n", $output);
				      		$len = count($arr)-2;
				      		$i=0;
				      		while($i<$len){
								echo "<a href='data.php?part=".$i."&path=".$_POST['data_path']."'>part-".$i."</a><br>";
								$i=$i+1;
							}
				      	?>
				    </div>
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


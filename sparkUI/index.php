<!DOCTYPE html>
<html>
<head>
	<?php  include 'head.php'; ?>
	<title>Spark Reduce Data App UI</title>
	<style type="text/css">
		.input-field label {
			color: #212121;
		}
		/* label focus color */
		.input-field input[type=text]:focus + label {
			color: #212121;
		}
		/* label underline focus color */
		.input-field input[type=text]:focus {
			border-bottom: 1px solid #212121;
			box-shadow: 0 1px 0 0 #212121;
		}
		/* icon prefix focus color */
		.input-field .prefix.active {
			color: #212121;
		}	
	</style>
</head>

<body>
	<?php include 'nav.php' ?>
	<div class="row">
	<div class="col m4 offset-m4">
		<div class="row">
			<div class="col m12" style="margin-top: 40px;">
				<div class="card teal lighten-5">
					<div class="card-content black-text">
					<span class="card-title">Jalankan Aplikasi Spark</span>
					<div class="row">
					<?php include 'form.php'; ?>
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
		
		$('select').formSelect();
		
		$( "#spark-form" ).submit(function( event ) {
  			window.open('master:8080/cluster', '_blank');
		});
	});
</script>

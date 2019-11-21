<!DOCTYPE html>
<html>
<head>
	<?php include 'head.php' ?>
	<title>Spark Reduce Data App UI</title>
	<style type="text/css">
	</style>
</head>

<body>
	<?php include 'nav.php' ?>
	<div class="row">
	<div class="col">
		<div class="row">
			<div class="col m12" style="margin-top:10px;">
				
				<?php 
					$temp = "00000";
					$sub = substr($temp, strlen($_GET['part']));
					//echo $sub;
					//echo $_GET['part'];
					$format = "%.3f";
					$output = shell_exec('cd /home/miebakso/hadoop-2.7.3 && ./bin/hadoop fs -cat '.$_GET['path'].'/part-'.$sub.$_GET['part']);
					$arr = explode("\n", $output);
					$i = 1;
					$len = count(explode(',',$arr[1]));
					foreach ($arr as $value) {
						if($i==1){
							echo '
							<div class="card teal lighten-4">
							<div class="card-content black-text">
							<table>
								<thead>
									<tr>'
										
							$file = fopen("collum.txt", "r") or die("Unable to open file!");
							$attributes = fgets($file);
							$attarr = explode(",", $attributes);

							echo '<th colspan="'.count($attarr).'">Total Objek = '.$value.' </th>
									</tr>
								</thead>

								<tbody>';

							echo '<tr>
									<td></td>';
									$x=0;
									while($x<=$len){
										echo '<th>'.$attarr[0].'</th>';
										$x++;
									}
							echo '</tr>';
							
							
								
						} else if($i==2){
							$arr2 = explode(",", $value);
							echo '<tr><td>Minimum</td>';
							foreach ($arr2 as $val) {
								echo '<td>'.sprintf($format,$val).'</td>';		        
							}
							echo '</tr>';
						} else if($i==3){
							$arr2 = explode(",", $value);
							echo '<tr><td>Maksimum</td>';
							foreach ($arr2 as $val) {
								echo '<td>'.sprintf($format,$val).'</td>';		        
							}
							echo '</tr>';
						} else if($i==4){
							$arr2 = explode(",", $value);
							echo '<tr><td>Rata-rata</td>';
							foreach ($arr2 as $val) {
								echo '<td>'.sprintf($format,$val).'</td>';		        
							}
							echo '</tr>';
						}else {
							$arr2 = explode(",", $value);
							echo '<tr><td>Standar Deviasi</td>';
							foreach ($arr2 as $val) {
								echo '<td>'.sprintf($format,$val).'</td>';		        
							}
							echo '</tr>

								</tbody>
							</table>
							</div>
							</div>
							';
							$i=0;
						}
						$i+=1;
					}
				?>
			
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


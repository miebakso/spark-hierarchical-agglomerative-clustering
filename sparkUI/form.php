<form id="spark-form" class="col m12" method="post" action="result.php" style="font-size: 20px;">
	<div class="row">
		<div class="input-field col m12 s12 black-text">
			<input id="jar_path" type="text" name="jar_path" class="validate">
			<label for="jar_path" >Spark JAR Path</label>
		</div>
	</div>
	<div class="row">
		<div class="input-field col m12 s12 black-text">
			<input id="input_path" type="text" name="input_path" class="black-text">
			<label for="input_path" >Input Path</label>
		</div>
	</div>
	<div class="row">
		<div class="input-field col m12 s12 black-text">
			<input id="output_path" type="text" name="output_path"class="black-text">
			<label for="output_path" >Output Path</label>
		</div>
	</div>

	<div class="row">
		<div class="input-field col m4 s12 black-text">
			<input id="number_of_executor" type="number" name="executor_number" class="black-text" value="1" min="1" step="1" max="100">
			<label for="number_of_executor" >Number of Executor</label>
		</div>
		<div class="input-field col m4 s12 black-text">
			<input id="executor_memory" type="number" name="executor_memory" class="black-text" value="1000" min="1000" step="100">
			<label for="executor_memory" >Executor Memory in mb</label>
		</div>
		<div class="input-field col m4 s12 black-text">
			<input id="number_of_partition" type="number" name="number" class="black-text" value="1" min="1" step="1" max="200">
			<label for="number_of_partition" >Number of Partition</label>
		</div>
	</div>
	<div class="row">
		<div class="input-field col m4 s12 black-text">
			<input id="max_obj" type="number" name="max_obj" class="black-text" value="1" min="1" step="1" max="100">
			<label for="max_obj" >Max Object</label>
		</div>
		<div class="input-field col m4 s12 black-text">
			<select name="type">
				<option value="0" >Single Linkage</option>
				<option value="1" >Complete Linkage</option>
				<option value="2" >Centroid Linkage</option>
			</select>
		</div>
		<div class="input-field col m4 s12 black-text">
			<input id="cut_off" type="number" name="cut_off" class="black-text" value="0.1" min="0.1" step="0.1" max="1">
			<label for="cut_off" >Cut Off Distance</label>
		</div>
	</div>

	<button class="btn waves-effect waves-light" type="submit" name="action">Submit
		<i class="material-icons right"></i>
	</button>
</form>
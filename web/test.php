<?php
	session_start();
	if ($_SERVER['REQUEST_METHOD'] == 'POST') {
		$_SESSION['id'] = $_POST['id'];
	}
?>
<html>
	<head>
	</head>
	<body>
		<form action="" method="POST">
			<label for="id">Id:</label>
			<input type="text" name="id" id="id" value="<?php echo isset($_POST['id']) ? $_POST['id'] : null ?>" />
			<input type="submit"/>
		</form>
	</body>
</html>
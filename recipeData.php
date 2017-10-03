<?php
require "recipeConnection.php";
$query = $_POST["query"];
$mysql_qry = $query;
$result = mysqli_query($conn, $mysql_qry);
if (!$result) {
    die(mysqli_error());
}
else{
	$i = -1;
	if(mysqli_num_rows($result) == 0) {
		echo "none found";
	}
	while ($row = mysqli_fetch_assoc($result)) {
	$i = $i+1;
    // Do stuff with $row
	$c='@';
	if($i<10){
		echo $row['Name'],$c, $row['Steps'],$c, $row['Ingredients'],$c, $row['Nutrition'],$c, $row['IMG_URL'],$c;
	}
}
}


$conn->close();
?>
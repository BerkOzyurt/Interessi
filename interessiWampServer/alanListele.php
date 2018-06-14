<?php
$con = mysql_connect('localhost', 'root', '');
mysql_select_db('interessi', $con);
 
$r = mysql_query('SELECT * FROM alanlar WHERE 1');
 
while($row = mysql_fetch_array($r))
{
	$out[] = $row;
}
 
print(json_encode($out));
mysql_close($con);
?>
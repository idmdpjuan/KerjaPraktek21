<?php

include 'connection.php';

$connection = mysqli_connect($host, $user ,$pass, $dbname);

if(!$connection) {
    die("Connection failed: " . mysqli_connect_error());
}
$result["errorcode"]="0";
$sql = "SELECT * FROM barangmasuk ORDER BY kodebarang";
$res = mysqli_query($connection, $sql);
$items = array();
if (mysqli_num_rows($res) > 0){
    while($row = mysqli_fetch_object($res)){
        array_push($items, $row);
    }

    $result["errorcode"] = "1";
    $result["data"] = $items;
}else {
    $result["errormsg"] = "Tidak ada data";
}
echo json_encode($result);
mysqli_close($connection);
?>
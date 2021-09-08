<?php
require("connection.php");

$connection = mysqli_connect($host, $user ,$pass, $dbname);

$tglAwal = $_POST['tglAwal'];
$tglAkhir = $_POST['tglAkhir'];
$strKeyword = $_POST['txtKeyword'];

$perintah = "SELECT * FROM stokbarang where name like '%$strKeyword%' and tanggal between '$tglAwal' and '$tglAkhir'";
$eksekusi = mysqli_query($connection, $perintah);
$intNumField = mysqli_num_fields($eksekusi);

$response = array();
while($result = mysql_fetch_array($eksekusi)){
    $arrCol = array();
    for($i=0;$i<$intNumField;$i++){
        $arrCol[mysql_field_name($eksekusim,$i)] = $result[$i];
    }
}
array_push($response,$arrCol);
mysqli_close($connection);

?>
?>
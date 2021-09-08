<?php
require("connection.php");

$Idp = $_POST['idp'];
$KodeBarang = $_POST['kodebarang'];
$NamaItem = $_POST['namaitem'];
$NoBarcode = $_POST['nobarcode'];
$JenisItem = $_POST['jenisitem'];
$NoFaktur = $_POST['nofaktur'];
$Tanggal = $_POST['tanggal'];
$Jumlah = $_POST['jumlah'];
$SuratJalan = $_POST['suratjalan'];
$Keterangan = $_POST['keterangan'];

$connection = mysqli_connect($host, $user, $pass, $db);

if(!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$query = "INSERT INTO barangmasuk (nofaktur, nobarcode, kodebarang, tanggal, namaitem, jumlah, suratjalan, keterangan) values('".$Idp."','".$KodeBarang."','".
$NoBarcode. "','".$NoFaktur. "','".$JenisItem. "','".$Tanggal. "','".$Jumlah. "','".$SuratJalan. "','".$Keterangan."')";

if($Idp != "0"){
    $query = "UPDATE barangmasuk set kodebarang ='".$KodeBarang. "',nobarcode = '".$NoBarcode."',namaitem = '".$NamaItem."',jumlah = '".$Jumlah."',
    tanggal = '".$Tanggal."',suratjalan = '".$SuratJalan."',keterangan = '".$Keterangan."' where idp = ".$Idp;
}
$response = mysqli_query($conn, $query) or die('Error query:    '.$query);
$lid = mysqli_insert_id($conn);
$result["errormsg"]="Data Berhasil";
$result["lid"]="$lid";
if($response == 1){
    echo json_encode($result);
}else {
    $result["errormsg"]="Gagal";
    echo json_encode($result);
}

?>
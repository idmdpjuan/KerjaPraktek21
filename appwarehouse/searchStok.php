<?php
require("connection.php");

$connection = mysqli_connect($host, $user ,$pass, $dbname);

$tglAwal = $_POST['tanggal'];
$tglAkhir = $_POST['tanggal'];

if(isset($_GET['tanggal']) ){

    $tanggal = $_GET['tanggal'];
    $sql = "SELECT * FROM stokbarang where name like '%$tanggal%' and tanggal between '$tglAwal' and '$tglAkhir'";
    $res = mysqli_query($connection, $sql);
        $response = array();
        while($row = mysqli_fetch_assoc($res)){
            array_push($response,
            array(
                'nobarcode'=>$row['nobarcode'],
                'kodebarang'=>$row['kodebarang'],
                'namaitem'=>$row['namaitem'],
                'jenisitem'=>$row['jenisitem'],
                'jumlah'=>$row['jumlah'],
                'warna'=>$row['warna'],
                'tanggal'=>$row['tanggal'])
            );
        }
        echo json_encode($response);

}else {
    $tanggal = $_GET['tanggal'];
    $sql = "SELECT * FROM stokbarang";
    $res = mysqli_query($connection, $sql);
        $response = array();
        while($row = mysqli_fetch_assoc($res) ){
            array_push($response,
            array(
                'kodebarang'=>$row['kodebarang'],
                'namaitem'=>$row['namaitem'],
                'jenisitem'=>$row['jenisitem'],
                'jumlah'=>$row['jumlah'],
                'warna'=>$row['warna'],
                'tanggal'=>$row['tanggal'])
            );
        }
        echo json_encode($response);
}
// $items = array();
// $hasilitems = array();
// while ($row = mysqli_fetch_assoc($res)){
//     array_push($hasilitems,$items);
// }
// while($rest = $res->fetch_object()){
//     $items = $rest;
//     array_push($hasilitems,$items);
// }
mysqli_close($connection);
?>
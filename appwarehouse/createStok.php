<?php

require("connect.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $NoBarcode = $_POST["nobarcode"];
    $KodeBarang = $_POST["kodebarang"];
    $NamaItem = $_POST["namaitem"];
    $JenisItem = $_POST["jenisitem"];
    $Jumlah = $_POST["jumlah"];
    $Warna = $_POST["warna"];
    $Tanggal = $_POST["tanggal"];

    $perintah = "INSERT INTO stokbarang (nobarcode, kodebarang, namaitem, jenisitem, jumlah, warna, tanggal) 
    VALUES('$NoBarcode','$KodeBarang','$NamaItem','$JenisItem','$Jumlah','$Tanggal')";
    $eksekusi = mysqli_query($connection, $perintah);
    $cek      = mysqli_affected_rows($connection);


    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Simpan Data Berhasil";
    }else {
        $response["kode"] = 0;
        $response["pesan"] = "Gagal Menyimpan Data";
    }
}else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($connection);
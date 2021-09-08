<?php

require("connection.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $NoFaktur = $_POST["nofaktur"];
    $NoBarcode = $_POST["nobarcode"];
    $KodeBarang = $_POST["kodebarang"];
    $NamaItem = $_POST["namaitem"];
    $JenisItem = $_POST["jenisitem"];
    $Tanggal = $_POST["tanggal"];
    $Jumlah = $_POST["jumlah"];
    $Warna = $_POST["warma"];
    $Keterangan = $_POST["keterangan"];


    $perintah = "INSERT INTO databarang (nofaktur, nobarcode, kodebarang, namaitem, jenisitem, tanggal, jumlah, warna, keterangan) 
    VALUES('$NoFaktur','$NoBarcode','$KodeBarang','$NamaItem','$JenisItem','$Tanggal','$Jumlah','$Warna','$Keterangan')";
    $eksekusi = mysqli_query($connection, $perintah);
    $cek      = mysqli_affected_rows($connection);

    // $next_id = ($NoBarcode+1);
    // $prefix = "TRAS";
    // $nofa = $sprintf.sprintf('%06d', $next_id);

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
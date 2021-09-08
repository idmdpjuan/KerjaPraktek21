<?php
require("connection.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $NoBarcode = $_POST["nobarcode"];
    $KodeBarang = $_POST["kodebarang"];
    $NamaItem = $_POST["namaitem"];
    $JenisItem = $_POST["jenisitem"];
    $Tanggal = $_POST["tanggal"];
    $Jumlah = $_POST["jumlah"];
    $Warna = $_POST["warna"];

    $perintah = "UPDATE stokbarang SET nofaktur = '$NoFaktur', nobarcode = '$NoBarcode', kodebarang = '$KodeBarang', namaitem = '$NamaItem', jenisitem = '$JenisItem', 
    tanggal = '$Tanggal', jumlah = '$Jumlah', warna = '$Warna', keterangan = '$Keterangan' WHERE kodeBarang = '$Nobarcode'";
    $eksekusi = mysqli_query($connection, $perintah);
    $cek      = mysqli_affected_rows($connection);

    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil Diubah";
    }else {
        $response["kode"] = 0;
        $response["pesan"] = "Data Gagal Diubah";
    }
}else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($connection);

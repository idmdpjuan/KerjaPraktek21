<?php
require("connection.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id = $_POST["kodebarang"];

    $perintah = "SELECT * FROM stokbarang WHERE kodebarang = '$kodebarang'";
    $eksekusi = mysqli_query($connection, $perintah);
    $cek      = mysqli_affected_rows($connection);

    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Tersedia";
        $response["data"] = array();

        while ($ambil = mysqli_fetch_object($eksekusi)) {
            $F["kodebarang"] = $ambildata->kodebarang;
            $F["namaitem"] = $ambildata->namaitem;
            $F["jenisitem"] = $ambildata->jenisitem;
            $F["jumlah"] = $ambildata->jumlah;
            $F["warna"] = $ambildata->warna;
            $F["tanggal"] = $ambildata->tanggal;

            array_push($response["data"], $F);
        }
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Data Tidak Tersedia";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($connection);

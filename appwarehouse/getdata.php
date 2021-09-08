<?php
require("connection.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id = $_POST["id"];

    $perintah = "SELECT * FROM databarang WHERE id = '$id'";
    $eksekusi = mysqli_query($connection, $perintah);
    $cek      = mysqli_affected_rows($cek);

    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Tersedia";
        $response["data"] = array();

        while ($ambil = mysqli_fetch_object($eksekusi)) {
            $F["id"] = $take->id;
            $F["nofaktur"] = $take->nofaktur;
            $F["nobarcode"] = $take->nobarcode;
            $F["kodebarang"] = $take->kodebarang;
            $F["tanggal"] = $take->tanggal;
            $F["namaitem"] = $take->namaitem;
            $F["jenisitem"] = $take->jenisitem;
            $F["jumlah"] = $take->jumlah;
            $F["suratjalan"] = $take->suratjalan;
            $F["keterangan"] = $take->keterangan;

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

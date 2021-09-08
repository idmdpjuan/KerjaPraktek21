<?php
require("connection.php");

$perintah = "SELECT * FROM databarang";
$eksekusi = mysqli_query($connection, $perintah);
$cek = mysqli_affected_rows($connection);

if($cek > 0){
    $response["kode"] = 1;
    $response["pesan"] = "Data Tersedia";
    $response["data"] = array();

    while($ambildata = mysqli_fetch_object($eksekusi)){
        $F["id"] = $ambildata->id;
        $F["nofaktur"] = $ambildata->nofaktur;
        $F["nobarcode"] = $ambildata->nobarcode;
        $F["kodebarang"] = $ambildata->kodebarang;
        $F["tanggal"] = $ambildata->tanggal;
        $F["namaitem"] = $ambildata->namaitem;
        $F["jenisitem"] = $ambildata->jenisitem;
        $F["jumlah"] = $ambildata->jumlah;
        $F["warna"] = $ambildata->warna;
        $F["keterangan"] = $ambildata->keterangan;

        array_push($response["data"], $F);
    }
}else {
    $response["kode"] = 0;
    $response["pesan"] = "Data Tidak Tersedia";
}

echo json_encode($response);
mysqli_close($connection);
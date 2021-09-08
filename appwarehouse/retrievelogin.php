<?php
require("connection.php");
$perintah = "SELECT * FROM users";
$eksekusi = mysqli_query($connection, $perintah);
$cek = mysqli_affected_rows($connection);

if($cek > 0){
    $response["kode"] = 1;
    $response["pesan"] = "Data Tersedia";
    $response["data"] = array();

    while($ambil = mysqli_fetch_object($eksekusi)){
        $F["id"] = $ambil->id;
        $F["idpegawai"] = $ambil->idpegawai;
        $F["password"] = $ambil->password;
        $F["email"] = $ambil->email;

        array_push($response["data"], $F);
    }
}else {
    $response["kode"] = 0;
    $response["pesan"] = "Data Tidak Tersedia";
}

echo json_encode($response);
mysqli_close($connection);
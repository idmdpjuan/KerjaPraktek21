<?php

include 'connection.php';

$connection = mysqli_connect($host, $user, $pass, $dbname);

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $idpegawai = $_POST['idpegawai'];
    $password = $_POST['password'];

    $sql = "SELECT * FROM users WHERE idpegawai = '$idpegawai' AND password = '$password'";

    $result = mysqli_query($connection,$sql);

    $check = mysqli_fetch_array($result);

    if(isset($check)){
        echo "Data Berhasil Terhubung";
    }else {
        echo "Data Tidak Terhubung";
    }
    mysqli_close($connection);
}
   ?>
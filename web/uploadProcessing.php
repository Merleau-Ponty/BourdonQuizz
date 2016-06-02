<?php
header('Content-Type: text/plain; charset=utf-8');

try 
{
    if (!isset($_FILES['upfile']['error']) || is_array($_FILES['upfile']['error'])) 
    {
        throw new RuntimeException('Paramètres invalides');
    }

    switch ($_FILES['upfile']['error']) 
    {
        case UPLOAD_ERR_OK:
            break;
        case UPLOAD_ERR_NO_FILE:
            throw new RuntimeException('Aucun fichier envoyé');
        case UPLOAD_ERR_INI_SIZE:
        case UPLOAD_ERR_FORM_SIZE:
            throw new RuntimeException('Taille de fichier maximale dépassée');
        default:
            throw new RuntimeException('Erreur inconnue');
    }

    if ($_FILES['upfile']['size'] > 1000000) 
    {
        throw new RuntimeException('Fichier trop volumineux');
    }

    $finfo = new finfo(FILEINFO_MIME_TYPE);
    if (false === $ext = array_search(
        $finfo->file($_FILES['upfile']['tmp_name']),
        array(
            'jpg' => 'image/jpg',
            'png' => 'image/png',
            'jpeg' => 'image/jpeg',
        ),
        true
    )) 
    {
        throw new RuntimeException('Format du fichier invalide');
    }

    if(!move_uploaded_file($_FILES['upfile']['tmp_name'], sprintf('./img/%s.%s', $_GET['q_id'], $ext))) 
    {
        throw new RuntimeException('Impossible de bouger le fichier uploadé');
    }
    echo 'Le fichier a été uploadé avec succès';
} 
catch (RuntimeException $e) 
{
    echo $e->getMessage();
}
?>
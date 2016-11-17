  <?php


class uploads
{
    // Datos de la tabla "usuario"
    const PHOTOURL = "photourl";


    const ESTADO_EXITO = 1;
    const ESTADO_CREACION_EXITOSA = 1;
    const ESTADO_CREACION_FALLIDA = 2;
    const ESTADO_ERROR_BD = 3;
    const ESTADO_AUSENCIA_CLAVE_API = 4;
    const ESTADO_CLAVE_NO_AUTORIZADA = 5;
    const ESTADO_URL_INCORRECTA = 6;
    const ESTADO_FALLA_DESCONOCIDA = 7;
    const ESTADO_PARAMETROS_INCORRECTOS = 8;

    const ESTADO_NO_ENCONTRADO = 5;




public static function put($peticion)
{
        if ($peticion[0] == 'uploadImage') {
            return self::uploadImage();
        } else {
            throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
        }
}

    private static function uploadImage()
    {

        $cuerpo = file_get_contents("php://input");
        parse_str(file_get_contents("php://input"),$post_vars);

        //echo $cuerpo;
        echo $post_vars['tipo']."\n";
        echo $post_vars['ente']."\n";
        echo $post_vars['dato']."\n";

        $imagen = json_decode($cuerpo);
        

        if (self::updatePhoto($imagen) > 0) {
                http_response_code(200);
                return [
                    "estado" => self::CODIGO_EXITO,
                    "mensaje" => "Registro actualizado correctamente"
                ];
            } else {
                throw new ExcepcionApi(self::ESTADO_NO_ENCONTRADO,
                    "El contacto al que intentas acceder no existe", 404);
            }
    }

    private static function updatePhoto($imagen)
    {

        try {

            $upload_path = 'images/';

            //Getting the server ip 
            $server_ip = gethostbyname(gethostname());
             
            //creating the upload url 
            $upload_url = 'http://'.$server_ip.'/walkappservices/v1/'.$upload_path; 


            $tipo = $imagen->tipo;
            $ente = $imagen->ente;
            $dato = $imagen->dato;
            $fileinfo = pathinfo($_FILES['image']['name']);
                     //getting the file extension 
            $extension = $fileinfo['extension'];
                     //file url to store in the database 
             $file_url = $upload_url .$tipo.'s/'. $dato . '.'. $extension;
             
            //file path to upload in the server 
            $file_path = $upload_path .$tipo. 's/'. $dato . '.'. $extension;
            try{
                 //saving the file 
                 move_uploaded_file($_FILES['image']['tmp_name'],$file_path);

                         // Creando consulta UPDATE
                $consulta = "UPDATE " .$tipo.
                " SET " .self::PHOTOURL. "=? " .
                " WHERE " . $ente. " =? ";
                        // Preparar la sentencia
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($consulta);

                $sentencia->bindParam(1, $file_url);
                $sentencia->bindParam(1, $dato);
                       // Ejecutar la sentencia
                $sentencia->execute();
                return $sentencia->rowCount();
                 
             //if some error occurred 
            }catch(Exception $e){
            }
        } catch (PDOException $e) {
            throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
        }
    }
}
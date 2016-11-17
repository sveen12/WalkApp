<?php


class favorites
{
    // Datos de la tabla "usuario"
    const NOMBRE_TABLA = "favorites";
    const IDROUTE = "idrouteFav";
    const USERNAME = "usernameFav";


    const ESTADO_EXITO = 1;
    const ESTADO_CREACION_EXITOSA = 1;
    const ESTADO_CREACION_FALLIDA = 2;
    const ESTADO_ERROR_BD = 3;
    const ESTADO_AUSENCIA_CLAVE_API = 4;
    const ESTADO_CLAVE_NO_AUTORIZADA = 5;
    const ESTADO_URL_INCORRECTA = 6;
    const ESTADO_FALLA_DESCONOCIDA = 7;
    const ESTADO_PARAMETROS_INCORRECTOS = 8;




    public static function get($peticion)
    {
            if ($peticion[0] == 'getFavoriteRoutes') {
                $usernameAux = $_GET['username'];

                if($usernameAux){
                    return self::getFavoriteRoutes($usernameAux);
                }else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
                }     
                
            } else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
            }
    }

    public static function post($peticion)
    {
        if ($peticion[0] == 'addFavoriteRoute') {
            return self::addFavoriteRoute();
        } /*else if ($peticion[0] == 'login') {
            return self::loguear();
        } */else {
            throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
        }
    }



    public static function addFavoriteRoute()
    {
        $cuerpo = file_get_contents('php://input');
        $favoriteRoute = json_decode($cuerpo);

        $resultado = self::crear($favoriteRoute);

        switch ($resultado) {
            case self::ESTADO_CREACION_EXITOSA:
                http_response_code(200);
                return
                    [
                        "estado" => self::ESTADO_CREACION_EXITOSA,
                        "mensaje" => utf8_encode("¡Registro con éxito!")
                    ];
                break;
            case self::ESTADO_CREACION_FALLIDA:
                throw new ExcepcionApi(self::ESTADO_CREACION_FALLIDA, "Ha ocurrido un error");
                break;
            default:
                throw new ExcepcionApi(self::ESTADO_FALLA_DESCONOCIDA, "Falla desconocida", 400);
        }
    }


    private static function crear($favoriteRoute)
    {
        $idRoute = $favoriteRoute->idRoute;
        $username = $favoriteRoute->username;

        try {

            $pdo = ConexionBD::obtenerInstancia()->obtenerBD();

            // Sentencia INSERT
            $comando = "INSERT INTO " . self::NOMBRE_TABLA . " ( " .
                self::IDROUTE . "," .
                self::USERNAME . ")" .
                " VALUES(?,?)";

            $sentencia = $pdo->prepare($comando);

            $sentencia->bindParam(1, $idRoute);
            $sentencia->bindParam(2, $username);

            $resultado = $sentencia->execute();

            if ($resultado) {
                return self::ESTADO_CREACION_EXITOSA;
            } else {
                return self::ESTADO_CREACION_FALLIDA;
            }
        } catch (PDOException $e) {
            throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
        }

    }

    public static function getFavoriteRoutes($username)
    {
        try {
            if ($username) {
                $comando = 

                "SELECT username, 
                        nombre, photourl, description, 
                        difficulty, weather, howarrive".

                " FROM ".self::NOMBRE_TABLA.
                " INNER JOIN route ".
                " ON ".self::IDROUTE." = idroute".
                " WHERE ".self::USERNAME."=?";

                // Preparar sentencia
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                // Ligar idUsuario
                $sentencia->bindParam(1, $username);
            }

            // Ejecutar sentencia preparada
            if ($sentencia->execute()) {
                http_response_code(200);
                return
                    [
                        "estado" => self::ESTADO_EXITO,
                        "datos" => $sentencia->fetchAll(PDO::FETCH_ASSOC)
                    ];
            } else
                throw new ExcepcionApi(self::ESTADO_ERROR, "Se ha producido un error");

        } catch (PDOException $e) {
            throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
        }
    }
}


<?php


class ratings
{
    // Datos de la tabla "usuario"
    const NOMBRE_TABLA = "rating";
    const IDRATING = "idRating";
    const IDROUTE = "idRoute";
    const USERNAME = "username";
    const STARS = "stars";    
    const COMENTARIO = "comentario";
    const SEGURIDAD = "seguridad";


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
            if ($peticion[0] == 'getRatings') {
                $idRouteAux = $_GET['idRoute'];

                if($idRouteAux){
                    return self::getRatings($idRouteAux);
                }else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
                }     
                
            } else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
            }
    }

    public static function post($peticion)
    {
        if ($peticion[0] == 'addRating') {
            return self::addRating();
        } /*else if ($peticion[0] == 'login') {
            return self::loguear();
        } */else {
            throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
        }
    }



    public static function addRating(){
        $cuerpo = file_get_contents('php://input');
        $rating = json_decode($cuerpo);

        $resultado = self::crear($rating);

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


    private static function crear($rating)
    {
        $idRating = $rating->idRating;
        $idRoute = $rating->idRoute;
        $username = $rating->username;
        $stars = $rating->stars;
        $comentario = $rating->comentario;
        $seguridad = $rating->seguridad;


        try {

            $pdo = ConexionBD::obtenerInstancia()->obtenerBD();

            // Sentencia INSERT
            $comando = "INSERT INTO " . self::NOMBRE_TABLA . " ( " .
                self::IDRATING . "," .
                self::IDROUTE . "," .
                self::USERNAME . "," .
                self::STARS . "," .
                self::COMENTARIO . "," .
                self::SEGURIDAD . ")" .
                " VALUES(?,?,?,?,?,?)";

            $sentencia = $pdo->prepare($comando);

            $sentencia->bindParam(1, $idRating);
            $sentencia->bindParam(2, $idRoute);
            $sentencia->bindParam(3, $username);
            $sentencia->bindParam(4, $stars);
            $sentencia->bindParam(5, $comentario);
            $sentencia->bindParam(6, $seguridad);

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

    public static function getRatings($idRoute){
        try {
            if ($idRoute && $tipo) {
                $comando = "SELECT * FROM " . self::NOMBRE_TABLA .
                    " WHERE " . self::IDROUTE . "=?";

                // Preparar sentencia
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                // Ligar idUsuario
                $sentencia->bindParam(1, $idRoute);
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


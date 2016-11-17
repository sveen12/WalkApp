<?php


class points
{
    // Datos de la tabla "usuario"
    const NOMBRE_TABLA = "points";
    const IDROUTE = "idRoute";
    const ORDER = "orden";
    const LONGITUD = "longitud";    
    const LATITUD = "latitud";
    const TIPO = "tipo";


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
            if ($peticion[0] == 'getPoints') {
                $idRouteAux = $_GET['idRoute'];
                $tipoAux = $_GET['tipo'];

                if($idRouteAux && $tipoAux){
                    return self::getPoints($idRouteAux, $tipoAux);
                }else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
                }     
                
            } else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
            }
    }

    public static function post($peticion)
    {
        if ($peticion[0] == 'addPoints') {
            return self::addPoints();
        } /*else if ($peticion[0] == 'login') {
            return self::loguear();
        } */else {
            throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
        }
    }



    public static function addPoints(){
        $cuerpo = file_get_contents('php://input');
        $points = json_decode($cuerpo, true);

        $resultado = self::crear($points);

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


    private static function crear($puntos)
    {
        try {

            

            $pdo = ConexionBD::obtenerInstancia()->obtenerBD();

            foreach ($puntos as $k=>$v){
                foreach ($v as $i=>$puntos){
                    $idRoute = $puntos[self::IDROUTE];
                    $order = $puntos[self::ORDER];
                    $longitud = $puntos[self::LONGITUD];
                    $latitud = $puntos[self::LATITUD];
                    $tipo = $puntos[self::TIPO];
                    
                    echo "Id: ".$idRoute;
                    echo "\n";
                    echo "Order: ".$order;
                    echo "\n";
                    echo "longitud: ".$longitud;
                    echo "\n";
                    echo "latitud: ".$latitud;
                    echo "\n";
                    echo "tipo: ".$tipo;
                    echo "\n";

                    // Sentencia INSERT
                    $comando = "INSERT INTO " . self::NOMBRE_TABLA . " ( " .
                        self::IDROUTE . "," .
                        self::ORDER . "," .
                        self::LONGITUD . "," .
                        self::LATITUD . "," .
                        self::TIPO . ") " .
                        " VALUES(?,?,?,?,?)";

                    $sentencia = $pdo->prepare($comando);

                    $sentencia->bindParam(1, $idRoute);
                    $sentencia->bindParam(2, $order);
                    $sentencia->bindParam(3, $longitud);
                    $sentencia->bindParam(4, $latitud);
                    $sentencia->bindParam(5, $tipo);

                    $resultado = $sentencia->execute();
 
                }
            }
                                if ($resultado) {
                        return self::ESTADO_CREACION_EXITOSA;
                    } else {
                        return self::ESTADO_CREACION_FALLIDA;
                    }  


                    /* // Sentencia INSERT
                    $comando = "INSERT INTO " . self::NOMBRE_TABLA . " ( " .
                        self::IDROUTE . "," .
                        self::ORDER . "," .
                        self::LONGITUD . "," .
                        self::LATITUD . "," .
                        self::TIPO . " ) " .
                        " VALUES(?,?,?,?,?)";

                    $sentencia = $pdo->prepare($comando);

                    $sentencia->bindParam(1, $idRoute);
                    $sentencia->bindParam(2, $order);
                    $sentencia->bindParam(3, $longitud);
                    $sentencia->bindParam(4, $latitud);
                    $sentencia->bindParam(5, $tipo);

                    $resultado = $sentencia->execute();
                } 

            }
                        if ($resultado) {
                return self::ESTADO_CREACION_EXITOSA;
            } else {
                return self::ESTADO_CREACION_FALLIDA;
            }*/
            /*
                 $idRoute=$puntos[$i]->{'idRoute'};              , ,,                                                                                                                                                     mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmvhxxxxxx            
                $order = $puntos[$i]->{'order'};
                $longitud = $puntos[$i]->{'longitud'};
                $latitud = $puntos[$i]->{'latitud'};
                $tipo = $puntos[$i]->{'tipo'};
            
                // Sentencia INSERT
                $comando = "INSERT INTO " . self::NOMBRE_TABLA . " ( " .
                    self::IDROUTE . "," .
                    self::ORDER . "," .
                    self::LONGITUD . "," .
                    self::LATITUD . "," .
                    self::TIPO . ")" .
                    " VALUES(?,?,?,?,?)";

                $sentencia = $pdo->prepare($comando);

                $sentencia->bindParam(1, $idRoute);
                $sentencia->bindParam(2, $order);
                $sentencia->bindParam(3, $longitud);
                $sentencia->bindParam(4, $latitud);
                $sentencia->bindParam(5, $tipo);

                $resultado = $sentencia->execute();

            }


            if ($resultado) {
                return self::ESTADO_CREACION_EXITOSA;
            } else {
                return self::ESTADO_CREACION_FALLIDA;
            }*/
                //echo "<pre>";
               // print_r($puntos);
        } catch (PDOException $e) {
            throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
        }

    }

    public static function getPoints($idRoute, $tipo){
        try {
            if ($idRoute && $tipo) {
                $comando = "SELECT * FROM " . self::NOMBRE_TABLA .
                    " WHERE " . self::IDROUTE . "=?". " AND " . self::TIPO . "=?";

                // Preparar sentencia
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                // Ligar idUsuario
                $sentencia->bindParam(1, $idRoute);
                $sentencia->bindParam(2, $tipo);

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


?>
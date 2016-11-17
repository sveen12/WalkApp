<?php


class routes
{
    // Datos de la tabla "usuario"
    const NOMBRE_TABLA = "route";
    const ID = "idroute";
    const USERNAME = "username";
    const NAME = "nombre";    
    const PHOTO = "photourl";
    const DESCRIPTION = "description";
    const DIFFICULTY = "difficulty";
    const WEATHER = "weather";
    const HOWARRIVE = "howarrive";


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
            if ($peticion[0] == 'validateRoute') {
                $nameAux = $_GET['name'];

                if($nameAux){
                    return self::validateRoute($nameAux);
                }else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
                }
                
            } else if($peticion[0] == "getMyRoutes"){
                $userNameAux = $_GET['username'];
                if($userNameAux){
                    return self::getMyRoutes($userNameAux);
                }else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
                }
            } else if($peticion[0] == "searchRoutes"){
                $wordAux = $_GET['word'];
                if($wordAux){
                    return self::searchRoutes($wordAux);
                }else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
                }
            } else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
            }
    }

    public static function post($peticion)
    {
        if ($peticion[0] == 'addRoute') {
            return self::addRoute();
        } /*else if ($peticion[0] == 'login') {
            return self::loguear();
        } */else {
            throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
        }
    }


    // Valida si el usuario ya esta registrado
    private static function validateRoute($name)
    {


        $cadena = str_replace("%20"," ",$name);
        echo $cadena;
        $name = $cadena;

        try {
            if ($name) {
                $comando = "SELECT * FROM " . self::NOMBRE_TABLA .
                    " WHERE " . self::NAME . "=?";

                // Preparar sentencia
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                // Ligar idUsuario
                $sentencia->bindParam(1, $name);

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


    /**
     * Crea un nuevo usuario en la base de datos
     */
    private static function addRoute()
    {
        $cuerpo = file_get_contents('php://input');
        $route = json_decode($cuerpo);

        $resultado = self::crear($route);

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

    /**
     * Crea un nuevo usuario en la tabla "usuario"
     * @param mixed $datosUsuario columnas del registro
     * @return int codigo para determinar si la inserción fue exitosa
     */
    private static function crear($datosRoute)
    {
        $username = $datosRoute->username;
        $name = $datosRoute->name;
        $photo = $datosRoute->photo;
        $description = $datosRoute->description;
        $difficulty = $datosRoute->difficulty;
        $weather = $datosRoute->weather;
        $howarrive = $datosRoute->howarrive;

        try {

            $pdo = ConexionBD::obtenerInstancia()->obtenerBD();

            // Sentencia INSERT
            $comando = "INSERT INTO " . self::NOMBRE_TABLA . " ( " .
                self::USERNAME . "," .
                self::NAME . "," .
                self::PHOTO . "," .
                self::DESCRIPTION . "," .
                self::DIFFICULTY . "," .
                self::WEATHER . "," .
                self::HOWARRIVE . ")" .
                " VALUES(?,?,?,?,?,?,?)";

            $sentencia = $pdo->prepare($comando);

            $sentencia->bindParam(1, $username);
            $sentencia->bindParam(2, $name);
            $sentencia->bindParam(3, $photo);
            $sentencia->bindParam(4, $description);
            $sentencia->bindParam(5, $difficulty);
            $sentencia->bindParam(6, $weather);
            $sentencia->bindParam(7, $howarrive);

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

    // Valida si el usuario ya esta registrado
    private static function getMyRoutes($username)
    {
        try {
            if ($username) {
                $comando = "SELECT * FROM " . self::NOMBRE_TABLA .
                    " WHERE " . self::USERNAME . "=?";

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

    private static function searchRoutes($word)
    {
        try {
            if ($word) {
                $comando = "SELECT * FROM " . self::NOMBRE_TABLA .
                    " WHERE " . self::NAME ." LIKE '%$word%' OR ". self::DESCRIPTION." LIKE '%$word%'"  ;

                // Preparar sentencia
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                // Ligar idUsuario
                $sentencia->bindParam(1, $word);
                $sentencia->bindParam(2, $word);

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


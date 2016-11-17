-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-11-2016 a las 23:44:25
-- Versión del servidor: 5.7.14
-- Versión de PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `walkapp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `favorites`
--

CREATE TABLE `favorites` (
  `idrouteFav` int(11) NOT NULL,
  `usernameFav` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `favorites`
--

INSERT INTO `favorites` (`idrouteFav`, `usernameFav`) VALUES
(1, 'root'),
(2, 'root'),
(1, 'root');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `points`
--

CREATE TABLE `points` (
  `idRoute` int(11) NOT NULL,
  `orden` int(11) NOT NULL,
  `longitud` double NOT NULL,
  `latitud` double NOT NULL,
  `tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `points`
--

INSERT INTO `points` (`idRoute`, `orden`, `longitud`, `latitud`, `tipo`) VALUES
(1, 1, 90, 5, 'Principal'),
(1, 1, 90, 5, 'Principal'),
(1, 2, 90, 5, 'Principal'),
(1, 1, 90, 5, 'Principal'),
(1, 2, 90, 5, 'Principal'),
(1, 1, 90, 5, 'Principal'),
(1, 2, 90, 5, 'Principal'),
(1, 1, 90, 5, 'Principal'),
(1, 2, 90, 5, 'Principal');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rating`
--

CREATE TABLE `rating` (
  `idRating` int(11) NOT NULL,
  `idRoute` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `stars` int(11) NOT NULL,
  `comentario` varchar(45) DEFAULT NULL,
  `seguridad` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `route`
--

CREATE TABLE `route` (
  `idroute` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `photourl` varchar(45) NOT NULL,
  `description` varchar(100) NOT NULL,
  `difficulty` varchar(20) NOT NULL,
  `weather` varchar(20) NOT NULL,
  `howarrive` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `route`
--

INSERT INTO `route` (`idroute`, `username`, `nombre`, `photourl`, `description`, `difficulty`, `weather`, `howarrive`) VALUES
(1, 'root', 'San Felix', 'sanfe.jpg', 'Muy buena la verdad', '3', 'Humedo', 'Coger bus desde Medellin'),
(2, 'root', 'Charco Verde', 'charco.jpg', 'Excelente para charcos', '5', 'Caliente', 'Coger bus desde Bello');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `photourl` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`username`, `password`, `email`, `age`, `photourl`) VALUES
('gg', 'gg', 'gg', 55, 'foto.jpg'),
('root', 'root', 'root@gmail.com', 34, NULL),
('sveen12', '2214325', 'sveen12@gmail.com', 21, 'foto.jpg'),
('test', 'test', 'test', 21, 'foto.jpg'),
('testo', 'te', 'te', 85, 'foto.jpg'),
('tos', 'test', 'test', 21, 'foto.jpg'),
('tt', 'tt', 'tt', 55, 'foto.jpg');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `favorites`
--
ALTER TABLE `favorites`
  ADD KEY `username` (`usernameFav`),
  ADD KEY `favorites_ibfk_1_idx` (`idrouteFav`);

--
-- Indices de la tabla `points`
--
ALTER TABLE `points`
  ADD KEY `fkrouteid_idx` (`idRoute`);

--
-- Indices de la tabla `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`idRating`),
  ADD KEY `username` (`username`),
  ADD KEY `rating_ibfk_2_idx` (`idRoute`);

--
-- Indices de la tabla `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`idroute`),
  ADD UNIQUE KEY `idroute_UNIQUE` (`idroute`),
  ADD KEY `username` (`username`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `route`
--
ALTER TABLE `route`
  MODIFY `idroute` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `favorites`
--
ALTER TABLE `favorites`
  ADD CONSTRAINT `favorites_ibfk_1` FOREIGN KEY (`idrouteFav`) REFERENCES `route` (`idroute`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `favorites_ibfk_2` FOREIGN KEY (`usernameFav`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `points`
--
ALTER TABLE `points`
  ADD CONSTRAINT `fkrouteid` FOREIGN KEY (`idRoute`) REFERENCES `route` (`idroute`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `rating`
--
ALTER TABLE `rating`
  ADD CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`idRoute`) REFERENCES `route` (`idroute`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `route`
--
ALTER TABLE `route`
  ADD CONSTRAINT `route_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

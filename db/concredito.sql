SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE concredito;

USE concredito;

CREATE TABLE `prospectos` (
  `id` bigint(20) NOT NULL,
  `calle` varchar(25) COLLATE utf8_spanish2_ci NOT NULL,
  `codigo_postal` varchar(255) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `colonia` varchar(25) COLLATE utf8_spanish2_ci NOT NULL,
  `nombre` varchar(25) COLLATE utf8_spanish2_ci NOT NULL,
  `numero` int(11) NOT NULL,
  `observacion` varchar(255) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `primer_apellido` varchar(25) COLLATE utf8_spanish2_ci NOT NULL,
  `rfc` varchar(255) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `ruta_docs` varchar(255) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `segundo_apellido` varchar(255) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `telefono` varchar(255) COLLATE utf8_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;


ALTER TABLE `prospectos`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

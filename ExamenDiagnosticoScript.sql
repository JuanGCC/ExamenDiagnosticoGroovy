CREATE DATABASE IF NOT EXISTS examendiagnostico;
CREATE TABLE `examendiagnostico`.`clientes`(
`id` bigint AUTO_INCREMENT PRIMARY KEY,
`nombre` varchar(50),
`direccion` varchar(50),
`telefono` long,
`correo_electronico` varchar(50)
)ENGINE=InnoDB DEFAULT CHARSET= utf8mb4;

CREATE TABLE `examendiagnostico`.`productos`(
`id` bigint AUTO_INCREMENT PRIMARY KEY,
`nombre` varchar(50),
`clave` varchar(50),
`cantidad` long,
`estatus` varchar(50)
)ENGINE=InnoDB DEFAULT CHARSET= utf8mb4;
CREATE TABLE `examendiagnostico`.`ventas`(
`id` bigint primary KEY,
`cliente_id` bigint,
`fecha_venta` date,
CONSTRAINT `id` FOREIGN KEY (`cliente_id`) REFERENCES `examendiagnostico`.`clientes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE= InnoDB DEFAULT CHARSET = utf8mb4;
CREATE TABLE `examendiagnostico`.`detalle_ventas`(
`id` bigint AUTO_INCREMENT PRIMARY KEY,
`id_venta` bigint,
`id_producto` bigint,
`cantidad` int,
CONSTRAINT `id_venta` FOREIGN KEY (`id_venta`) REFERENCES `examendiagnostico`.`ventas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT `id_producto` FOREIGN KEY (`id_producto`) REFERENCES `examendiagnostico`.`productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE InnoDB DEFAULT CHARSET = utf8mb4;


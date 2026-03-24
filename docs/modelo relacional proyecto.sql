CREATE TABLE `usuario` (
  `id_usuario` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(300) NOT NULL,
  `apellido` varchar(300) NOT NULL,
  `correo` varchar(300) UNIQUE NOT NULL,
  `fecha_registro` date NOT NULL,
  `salario_mensual` int NOT NULL,
  `estado` boolean NOT NULL,
  `creador_user` varchar(300) NOT NULL,
  `modificado_por_user` varchar(300),
  `creado_en` timestamp NOT NULL,
  `modificado_en` timestamp NOT NULL
);

CREATE TABLE `presupuesto` (
  `id_presupuesto` int PRIMARY KEY AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `nombre` varchar(300) NOT NULL,
  `year_inicio` int NOT NULL,
  `mes_inicio` int NOT NULL,
  `year_fin` int NOT NULL,
  `mes_fin` int NOT NULL,
  `total_ingresos` decimal(12,2) NOT NULL,
  `total_gastos` decimal(12,2) NOT NULL,
  `total_ahorro` decimal(12,2) NOT NULL,
  `fecha_hora_creacion` timestamp NOT NULL,
  `estado` boolean NOT NULL,
  `creado_user` varchar(300) NOT NULL,
  `modificado_user` varchar(300),
  `creado_fecha` timestamp NOT NULL,
  `modificado_fecha` timestamp NOT NULL
);

CREATE TABLE `categoria` (
  `id_categoria` int PRIMARY KEY AUTO_INCREMENT,
  `id_usuario_propetario` int NOT NULL,
  `nombre` varchar(300) NOT NULL,
  `descripcion` varchar(1000),
  `tipo` varchar(300) NOT NULL,
  `creado_user` varchar(300) NOT NULL,
  `modificado_user` varchar(300),
  `creado_fecha` timestamp NOT NULL,
  `modificado_fecha` timestamp NOT NULL
);

CREATE TABLE `subcategoria` (
  `id_subcategoria` int PRIMARY KEY AUTO_INCREMENT,
  `id_categoria` int NOT NULL,
  `nombre` varchar(300) NOT NULL,
  `descripcion` varchar(300),
  `indicador_activa` boolean NOT NULL,
  `es_defecto` boolean NOT NULL,
  `creado_user` varchar(300) NOT NULL,
  `modificado_user` varchar(300),
  `creado_fecha` timestamp NOT NULL,
  `modificado_fecha` timestamp NOT NULL
);

CREATE TABLE `presupuesto_detalle` (
  `id_presupuesto_detalle` int PRIMARY KEY AUTO_INCREMENT,
  `id_presupuesto` int NOT NULL,
  `id_subcategoria` int NOT NULL,
  `monto_mensual` decimal(12,2) NOT NULL,
  `observaciones` varchar(1000),
  `creado_user` varchar(300) NOT NULL,
  `modificado_user` varchar(300),
  `creado_fecha` timestamp NOT NULL,
  `modificado_fecha` timestamp NOT NULL
);

CREATE TABLE `obligacion_fija` (
  `id_obligacion` int PRIMARY KEY AUTO_INCREMENT,
  `id_subcategoria` int NOT NULL,
  `id_usuario` int NOT NULL,
  `nombre` varchar(300) NOT NULL,
  `descripcion` varchar(500),
  `monto_mensual` decimal(12,2) NOT NULL,
  `dia_vencimiento` int NOT NULL,
  `vigente` boolean NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_finalizacion` date,
  `creado_user` varchar(300) NOT NULL,
  `modificado_user` varchar(300),
  `creado_fecha` timestamp NOT NULL,
  `modificado_fecha` timestamp NOT NULL
);

CREATE TABLE `transaccion` (
  `id_transaccion` int PRIMARY KEY AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_presupuesto` int NOT NULL,
  `anio` int NOT NULL,
  `mes` int NOT NULL,
  `id_subcategoria` int NOT NULL,
  `id_obligacion` int,
  `tipo` varchar(300) NOT NULL,
  `descripcion` varchar(1000),
  `monto` decimal(12,2) NOT NULL,
  `fecha` date NOT NULL,
  `metodo_pago` varchar(300),
  `num_factura` varchar(300),
  `observaciones` varchar(1000),
  `creado_user` varchar(300) NOT NULL,
  `modificado_user` varchar(300),
  `creado_fecha` timestamp NOT NULL,
  `modificado_fecha` timestamp NOT NULL
);

CREATE TABLE `obligacionfija_transaccion` (
  `id_obligacion` int NOT NULL,
  `id_transaccion` int NOT NULL,
  PRIMARY KEY (`id_obligacion`, `id_transaccion`)
);

ALTER TABLE `presupuesto` ADD FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

ALTER TABLE `categoria` ADD FOREIGN KEY (`id_usuario_propetario`) REFERENCES `usuario` (`id_usuario`);

ALTER TABLE `subcategoria` ADD FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`);

ALTER TABLE `presupuesto_detalle` ADD FOREIGN KEY (`id_presupuesto`) REFERENCES `presupuesto` (`id_presupuesto`);

ALTER TABLE `presupuesto_detalle` ADD FOREIGN KEY (`id_subcategoria`) REFERENCES `subcategoria` (`id_subcategoria`);

ALTER TABLE `obligacion_fija` ADD FOREIGN KEY (`id_subcategoria`) REFERENCES `subcategoria` (`id_subcategoria`);

ALTER TABLE `obligacion_fija` ADD FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

ALTER TABLE `transaccion` ADD FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

ALTER TABLE `transaccion` ADD FOREIGN KEY (`id_presupuesto`) REFERENCES `presupuesto` (`id_presupuesto`);

ALTER TABLE `transaccion` ADD FOREIGN KEY (`id_subcategoria`) REFERENCES `subcategoria` (`id_subcategoria`);

ALTER TABLE `transaccion` ADD FOREIGN KEY (`id_obligacion`) REFERENCES `obligacion_fija` (`id_obligacion`);

ALTER TABLE `obligacionfija_transaccion` ADD FOREIGN KEY (`id_obligacion`) REFERENCES `obligacion_fija` (`id_obligacion`);

ALTER TABLE `obligacionfija_transaccion` ADD FOREIGN KEY (`id_transaccion`) REFERENCES `transaccion` (`id_transaccion`);

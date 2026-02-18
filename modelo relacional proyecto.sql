CREATE TABLE `usuario` (
  `id_usuario` int PRIMARY KEY,
  `nombre` varchar(300),
  `apellido` varchar(300),
  `correo` varchar(300),
  `fecha_registro` date,
  `salario_mensual` double,
  `estado` bool,
  `creado_user` varchar(300),
  `modificado_user` varchar(300),
  `creado_fecha` timestamp,
  `modificado_fecha` timestamp
);

CREATE TABLE `presupuesto` (
  `id_presupuesto` int PRIMARY KEY,
  `id_usuario` int,
  `nombre` varchar(300),
  `year_inicio` int,
  `mes_inicio` int,
  `year_fin` int,
  `mes_fin` int,
  `total_ingresos` double,
  `total_gastos` double,
  `total_ahorro` double,
  `fecha_hora_creacion` date,
  `estado` bool,
  `creado_user` varchar(300),
  `modificado_user` varchar(300),
  `creado_fecha` timestamp,
  `modificado_fecha` timestamp
);

CREATE TABLE `categoria` (
  `id_categoria` int PRIMARY KEY,
  `id_usuario` int,
  `nombre` varchar(300),
  `descripcion` varchar(300),
  `tipo` varchar(300),
  `creado_user` varchar(300),
  `modificado_user` varchar(300),
  `creado_fecha` timestamp,
  `modificado_fecha` timestamp
);

CREATE TABLE `subcategoria` (
  `id_subcategoria` int PRIMARY KEY,
  `id_categoria` int,
  `nombre` varchar(300),
  `descripcion` varchar(300),
  `indicador_activa` bool,
  `es_defecto` bool,
  `creado_user` varchar(300),
  `modificado_user` varchar(300),
  `creado_fecha` timestamp,
  `modificado_fecha` timestamp
);

CREATE TABLE `presupuesto_detalle` (
  `id_presupuesto_detalle` int PRIMARY KEY,
  `id_presupuesto` int,
  `id_subcategoria` int,
  `monto_mensual` double,
  `observaciones` varchar(500),
  `creado_user` varchar(300),
  `modificado_user` varchar(300),
  `creado_fecha` timestamp,
  `modificado_fecha` timestamp
);

CREATE TABLE `obligacion_fija` (
  `id_obligacion` int PRIMARY KEY,
  `id_usuario` int,
  `id_subcategoria` int,
  `nombre` varchar(300),
  `descripcion` varchar(500),
  `monto_mensual` double,
  `dia_vencimiento` int,
  `vigente` bool,
  `fecha_inicio` date,
  `fecha_finalizacion` date,
  `creado_user` varchar(300),
  `modificado_user` varchar(300),
  `creado_fecha` timestamp,
  `modificado_fecha` timestamp
);

CREATE TABLE `transaccion` (
  `id_transaccion` int PRIMARY KEY,
  `id_usuario` int,
  `id_presupuesto` int,
  `year` int,
  `mes` int,
  `id_subcategoria` int,
  `id_obligacion` int,
  `tipo_transaccion` varchar(300),
  `descripcion` varchar(1000),
  `monto` double,
  `fecha` date,
  `metodo_pago` varchar(300),
  `num_factura` varchar(300),
  `observaciones` varchar(1000),
  `creado_user` varchar(300),
  `modificado_user` varchar(300),
  `creado_fecha` timestamp,
  `modificado_fecha` timestamp
);

ALTER TABLE `presupuesto` ADD FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

ALTER TABLE `categoria` ADD FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

ALTER TABLE `subcategoria` ADD FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`);

ALTER TABLE `presupuesto_detalle` ADD FOREIGN KEY (`id_presupuesto`) REFERENCES `presupuesto` (`id_presupuesto`);

ALTER TABLE `presupuesto_detalle` ADD FOREIGN KEY (`id_subcategoria`) REFERENCES `subcategoria` (`id_subcategoria`);

ALTER TABLE `obligacion_fija` ADD FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

ALTER TABLE `obligacion_fija` ADD FOREIGN KEY (`id_subcategoria`) REFERENCES `subcategoria` (`id_subcategoria`);

ALTER TABLE `transaccion` ADD FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

ALTER TABLE `transaccion` ADD FOREIGN KEY (`id_presupuesto`) REFERENCES `presupuesto` (`id_presupuesto`);

ALTER TABLE `transaccion` ADD FOREIGN KEY (`id_subcategoria`) REFERENCES `subcategoria` (`id_subcategoria`);

ALTER TABLE `transaccion` ADD FOREIGN KEY (`id_obligacion`) REFERENCES `obligacion_fija` (`id_obligacion`);

use presupuesto_personal_proyecto;

call sp_insertar_usuario(
    'esdras',
    'carranza',
    'esdras.presupuesto@gmail.com',
    18000.00,
    'admin'
);

select id_usuario into @id_usuario
from usuario
where correo = 'esdras.presupuesto@gmail.com';

call sp_insertar_categoria('ingresos', 'fuentes de ingreso mensual', 'ingreso', 'admin', @id_usuario);
call sp_insertar_categoria('alimentacion', 'gastos de comida y consumo', 'gasto', 'admin', @id_usuario);
call sp_insertar_categoria('transporte', 'movilidad diaria', 'gasto', 'admin', @id_usuario);
call sp_insertar_categoria('servicios', 'pagos de servicios del hogar', 'gasto', 'admin', @id_usuario);
call sp_insertar_categoria('entretenimiento', 'gastos recreativos', 'gasto', 'admin', @id_usuario);
call sp_insertar_categoria('ahorro', 'apartados para metas y reserva', 'ahorro', 'admin', @id_usuario);

select id_categoria into @cat_ingresos
from categoria
where nombre = 'ingresos' and id_usuario_propetario = @id_usuario;

select id_categoria into @cat_alimentacion
from categoria
where nombre = 'alimentacion' and id_usuario_propetario = @id_usuario;

select id_categoria into @cat_transporte
from categoria
where nombre = 'transporte' and id_usuario_propetario = @id_usuario;

select id_categoria into @cat_servicios
from categoria
where nombre = 'servicios' and id_usuario_propetario = @id_usuario;

select id_categoria into @cat_entretenimiento
from categoria
where nombre = 'entretenimiento' and id_usuario_propetario = @id_usuario;

select id_categoria into @cat_ahorro
from categoria
where nombre = 'ahorro' and id_usuario_propetario = @id_usuario;

call sp_insertar_subcategoria(@cat_ingresos, 'salario mensual', 'ingreso principal del usuario', 0, 'admin');

call sp_insertar_subcategoria(@cat_alimentacion, 'supermercado', 'compras de comida del hogar', 0, 'admin');
call sp_insertar_subcategoria(@cat_alimentacion, 'restaurante', 'comidas fuera de casa', 0, 'admin');

call sp_insertar_subcategoria(@cat_transporte, 'bus', 'transporte publico', 0, 'admin');
call sp_insertar_subcategoria(@cat_transporte, 'uber', 'transporte por aplicacion', 0, 'admin');

call sp_insertar_subcategoria(@cat_servicios, 'internet', 'servicio mensual de internet', 0, 'admin');
call sp_insertar_subcategoria(@cat_servicios, 'energia electrica', 'pago de energia del hogar', 0, 'admin');
call sp_insertar_subcategoria(@cat_servicios, 'renta', 'alquiler de vivienda', 0, 'admin');

call sp_insertar_subcategoria(@cat_entretenimiento, 'salidas', 'ocio y recreacion', 0, 'admin');
call sp_insertar_subcategoria(@cat_entretenimiento, 'streaming', 'suscripciones digitales', 0, 'admin');

call sp_insertar_subcategoria(@cat_ahorro, 'fondo de emergencia', 'ahorro mensual programado', 0, 'admin');

select id_subcategoria into @sub_salario
from subcategoria
where id_categoria = @cat_ingresos and nombre = 'salario mensual';

select id_subcategoria into @sub_supermercado
from subcategoria
where id_categoria = @cat_alimentacion and nombre = 'supermercado';

select id_subcategoria into @sub_restaurante
from subcategoria
where id_categoria = @cat_alimentacion and nombre = 'restaurante';

select id_subcategoria into @sub_bus
from subcategoria
where id_categoria = @cat_transporte and nombre = 'bus';

select id_subcategoria into @sub_uber
from subcategoria
where id_categoria = @cat_transporte and nombre = 'uber';

select id_subcategoria into @sub_internet
from subcategoria
where id_categoria = @cat_servicios and nombre = 'internet';

select id_subcategoria into @sub_energia
from subcategoria
where id_categoria = @cat_servicios and nombre = 'energia electrica';

select id_subcategoria into @sub_renta
from subcategoria
where id_categoria = @cat_servicios and nombre = 'renta';

select id_subcategoria into @sub_salidas
from subcategoria
where id_categoria = @cat_entretenimiento and nombre = 'salidas';

select id_subcategoria into @sub_streaming
from subcategoria
where id_categoria = @cat_entretenimiento and nombre = 'streaming';

select id_subcategoria into @sub_ahorro
from subcategoria
where id_categoria = @cat_ahorro and nombre = 'fondo de emergencia';

call sp_ingresar_presupuesto(
    @id_usuario,
    'presupuesto enero-febrero 2026',
    2026,
    1,
    2026,
    2,
    'admin'
);

select id_presupuesto into @id_presupuesto
from presupuesto
where id_usuario = @id_usuario
  and nombre = 'presupuesto enero-febrero 2026';

call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_salario, 18000.00, 'ingreso mensual esperado', 'admin');

call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_supermercado, 3200.00, 'compras mensuales de supermercado', 'admin');
call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_restaurante, 900.00, 'comidas fuera de casa', 'admin');

call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_bus, 500.00, 'transporte publico', 'admin');
call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_uber, 800.00, 'viajes esporadicos en uber', 'admin');

call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_internet, 850.00, 'servicio fijo mensual', 'admin');
call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_energia, 1400.00, 'recibo mensual variable', 'admin');
call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_renta, 5000.00, 'alquiler mensual', 'admin');

call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_salidas, 700.00, 'ocio del mes', 'admin');
call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_streaming, 250.00, 'suscripciones', 'admin');

call sp_insertar_presupuesto_detalle(@id_presupuesto, @sub_ahorro, 2000.00, 'aporte mensual al ahorro', 'admin');


call sp_insertar_obligacion_fija(
    @sub_renta,
    'renta apartamento',
    'pago mensual de alquiler',
    5000.00,
    5,
    '2026-01-01',
    null,
    'admin'
);

call sp_insertar_obligacion_fija(
    @sub_internet,
    'internet hogar',
    'servicio residencial de internet',
    850.00,
    15,
    '2026-01-01',
    null,
    'admin'
);

call sp_insertar_obligacion_fija(
    @sub_energia,
    'energia electrica hogar',
    'consumo mensual de energia',
    1350.00,
    20,
    '2026-01-01',
    null,
    'admin'
);

call sp_insertar_obligacion_fija(
    @sub_streaming,
    'suscripcion streaming',
    'plataforma de entretenimiento',
    219.00,
    22,
    '2026-01-01',
    null,
    'admin'
);

select id_obligacion into @obl_renta
from obligacion_fija
where id_subcategoria = @sub_renta and nombre = 'renta apartamento';

select id_obligacion into @obl_internet
from obligacion_fija
where id_subcategoria = @sub_internet and nombre = 'internet hogar';

select id_obligacion into @obl_energia
from obligacion_fija
where id_subcategoria = @sub_energia and nombre = 'energia electrica hogar';

select id_obligacion into @obl_streaming
from obligacion_fija
where id_subcategoria = @sub_streaming and nombre = 'suscripcion streaming';

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_supermercado, null, 'gasto',
'compra quincenal de supermercado', 920.50, '2026-01-03', 'tarjeta_debito', 'FAC-001', 'compra de inicio de mes', 'admin');


call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_renta, @obl_renta, 'gasto',
'pago de renta de enero', 5000.00, '2026-01-05', 'transferencia', 'REC-REN-001', 'pagado en fecha de vencimiento', 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_bus, null, 'gasto',
'transporte publico semanal', 80.00, '2026-01-06', 'efectivo', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_uber, null, 'gasto',
'viaje en uber al trabajo', 145.00, '2026-01-09', 'tarjeta_credito', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_restaurante, null, 'gasto',
'almuerzo con compañeros', 210.00, '2026-01-11', 'tarjeta_debito', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_internet, @obl_internet, 'gasto',
'pago de internet enero', 850.00, '2026-01-14', 'transferencia', 'FAC-INT-001', 'pagado un dia antes del vencimiento', 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_supermercado, null, 'gasto',
'compra de supermercado de mitad de mes', 1185.75, '2026-01-16', 'tarjeta_debito', 'FAC-002', null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_energia, @obl_energia, 'gasto',
'pago de energia electrica enero', 1325.40, '2026-01-19', 'efectivo', 'FAC-ENE-001', 'pagado cerca del vencimiento', 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_streaming, @obl_streaming, 'gasto',
'pago mensual de streaming', 219.00, '2026-01-22', 'tarjeta_credito', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_bus, null, 'gasto',
'transporte publico segunda quincena', 95.00, '2026-01-23', 'efectivo', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_salidas, null, 'gasto',
'salida de fin de semana', 340.00, '2026-01-25', 'tarjeta_debito', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_supermercado, null, 'gasto',
'compra de cierre de mes', 760.20, '2026-01-27', 'tarjeta_debito', 'FAC-003', null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_salario, null, 'ingreso',
'deposito de salario enero', 18000.00, '2026-01-30', 'transferencia', 'PAY-ENE-001', 'pago de planilla', 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 1, @sub_ahorro, null, 'ahorro',
'aporte a fondo de emergencia enero', 1800.00, '2026-01-31', 'transferencia', null, 'ahorro menor a lo presupuestado', 'admin');

-- xd
call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_supermercado, null, 'gasto',
'compra de supermercado inicio de febrero', 870.30, '2026-02-02', 'tarjeta_debito', 'FAC-004', null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_renta, @obl_renta, 'gasto',
'pago de renta de febrero', 5000.00, '2026-02-05', 'transferencia', 'REC-REN-002', 'pagado puntual', 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_bus, null, 'gasto',
'transporte publico primera semana', 90.00, '2026-02-06', 'efectivo', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_uber, null, 'gasto',
'viaje en uber por lluvia', 165.00, '2026-02-08', 'tarjeta_credito', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_restaurante, null, 'gasto',
'cena de fin de semana', 260.00, '2026-02-10', 'tarjeta_debito', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_internet, @obl_internet, 'gasto',
'pago de internet febrero', 850.00, '2026-02-15', 'transferencia', 'FAC-INT-002', 'pagado el mismo dia de vencimiento', 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_supermercado, null, 'gasto',
'compra de supermercado mitad de febrero', 1240.80, '2026-02-17', 'tarjeta_debito', 'FAC-005', null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_energia, @obl_energia, 'gasto',
'pago de energia electrica febrero', 1188.60, '2026-02-20', 'efectivo', 'FAC-ENE-002', 'menor consumo que en enero', 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_streaming, @obl_streaming, 'gasto',
'pago mensual de streaming febrero', 219.00, '2026-02-22', 'tarjeta_credito', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_salidas, null, 'gasto',
'salida especial de febrero', 460.00, '2026-02-24', 'tarjeta_debito', null, 'gasto de ocio mayor que enero', 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_bus, null, 'gasto',
'transporte publico segunda quincena', 85.00, '2026-02-25', 'efectivo', null, null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_supermercado, null, 'gasto',
'compra final de supermercado febrero', 910.45, '2026-02-26', 'tarjeta_debito', 'FAC-006', null, 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_salario, null, 'ingreso',
'deposito de salario febrero', 18000.00, '2026-02-28', 'transferencia', 'PAY-FEB-001', 'pago de planilla mensual', 'admin');

call sp_insertar_transaccion(@id_usuario, @id_presupuesto, 2026, 2, @sub_ahorro, null, 'ahorro',
'aporte a fondo de emergencia febrero', 2200.00, '2026-02-28', 'transferencia', null, 'ahorro mayor que enero', 'admin');
























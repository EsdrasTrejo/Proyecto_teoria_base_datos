ALTER TABLE transaccion AUTO_INCREMENT = 7001;

delimiter $$
drop procedure if exists sp_insertar_transaccion $$
create procedure sp_insertar_transaccion( p_id_usuario int, p_id_presupuesto int, p_anio int, p_mes int, p_id_subcategoria int, p_id_obligacion int, p_tipo varchar(300),
p_descripcion varchar(1000), p_monto decimal(12,2), p_fecha date, p_metodo_pago varchar(300), p_num_factura varchar(300), p_observaciones varchar(1000), p_creado_por varchar(300))
begin
    if p_id_usuario is null or p_id_usuario <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id del usuario es invalido';
    end if;

    if not exists ( select 1 from usuario where id_usuario = p_id_usuario) then
    signal sqlstate '45000'
    set message_text = 'el usuario no existe';
    end if;

    if p_id_presupuesto is null or p_id_presupuesto <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id del presupuesto es invalido';
    end if;


    if not exists (select 1 from presupuesto where id_presupuesto = p_id_presupuesto) then
    signal sqlstate '45000'
    set message_text = 'el presupuesto no existe';
    end if;

    if p_id_subcategoria is null or p_id_subcategoria <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id de la subcategoria es invalido';
    end if;

    if not exists ( select 1 from subcategoria where id_subcategoria = p_id_subcategoria) then
    
    signal sqlstate '45000'
    set message_text = 'la subcategoria no existe';
    end if;
    
    
    if p_id_obligacion is not null and p_id_obligacion <> 0 then
    if not exists (select 1 from obligacion_fija
    where id_obligacion = p_id_obligacion) then
    signal sqlstate '45000'
    set message_text = 'la obligacion fija no existe';
    end if;
    end if;


    if p_anio is null or p_anio <= 0 then
    signal sqlstate '45000'
    set message_text = 'el año es invalido';
    end if;
    
    if p_mes is null or p_mes not in (1,2,3,4,5,6,7,8,9,10,11,12) then
    signal sqlstate '45000'
    set message_text = 'el mes debe estar entre 1 y 12';
    end if;

    if p_tipo is null or trim(p_tipo) = '' then
    signal sqlstate '45000'
    set message_text = 'el tipo no puede ser nulo o vacio';
    end if;

    if p_monto is null then
    signal sqlstate '45000'
    set message_text = 'el monto no puede ser nulo';
    end if;

    if p_monto < 0 then
    signal sqlstate '45000'
    set message_text = 'el monto no puede ser negativo';
    end if;

    if p_fecha is null then
    signal sqlstate '45000'
    set message_text = 'la fecha no puede ser nula';
    end if;

    if p_metodo_pago is null or trim(p_metodo_pago) = '' then
    signal sqlstate '45000'
    set message_text = 'el metodo de pago no puede ser nulo o vacio';
    end if;

    if p_creado_por is null or trim(p_creado_por) = '' then
    signal sqlstate '45000'
     set message_text = 'el usuario creador no puede ser nulo o vacio';
    end if;

    insert into transaccion(id_usuario, id_presupuesto, anio, mes, id_subcategoria, id_obligacion, tipo, descripcion, monto, fecha, metodo_pago,num_factura, observaciones,
    creado_user,creado_fecha)
    values(p_id_usuario,p_id_presupuesto,p_anio, p_mes, p_id_subcategoria, case when p_id_obligacion is null or p_id_obligacion = 0 then null else p_id_obligacion end
    , trim(p_tipo), case when p_descripcion is null then null else trim(p_descripcion) end,
    p_monto,p_fecha,trim(p_metodo_pago),case when p_num_factura is null then null else trim(p_num_factura) end,case when p_observaciones is null then null else trim(p_observaciones) end,
    trim(p_creado_por),current_timestamp);
end $$

delimiter ;

call sp_insertar_transaccion(
    1, 1, 2026, 3, 1,
    0,  -- 👈 ESTE ES EL CASO CLAVE
    'gasto',
    'test sin obligacion',
    100,
    '2026-03-22',
    'efectivo',
    null,
    null,
    'test'
);

show create procedure sp_insertar_transaccion;
describe transaccion;

delimiter $$ 
drop procedure if exists sp_actualizar_transaccion $$
create procedure sp_actualizar_transaccion( p_id_transaccion int, p_anio int, p_mes int, p_descripcion varchar(300), p_monto decimal(12,2), p_fecha date, p_metodo_pago varchar(300),
p_num_factura varchar(300), p_observaciones varchar(300),p_modificado_por varchar(300))
begin 
    if p_id_transaccion is null or p_id_transaccion <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id de la transaccion es invalido';
    end if;

    if not exists (select 1 from transaccion
    where id_transaccion = p_id_transaccion) then
    signal sqlstate '45000'
    set message_text = 'la transaccion no existe';
    end if;
    
    if p_anio is null or p_anio <= 0 then
    signal sqlstate '45000'
    set message_text = 'el año es invalido';
    end if;

    if p_mes is null or p_mes not in (1,2,3,4,5,6,7,8,9,10,11,12) then
    signal sqlstate '45000'
    set message_text = 'el mes debe estar entre 1 y 12';
    end if;

    if p_monto is null then
    signal sqlstate '45000'
    set message_text = 'el monto no puede ser nulo';
    end if;

    if p_monto < 0 then
    signal sqlstate '45000'
    set message_text = 'el monto no puede ser negativo';
    end if;

    if p_fecha is null then
    signal sqlstate '45000'
     set message_text = 'la fecha no puede ser nula';
    end if;

    if p_metodo_pago is null or trim(p_metodo_pago) = '' then
    signal sqlstate '45000'
    set message_text = 'el metodo de pago no puede ser nulo o vacio';
    end if;

    if p_modificado_por is null or trim(p_modificado_por) = '' then
    signal sqlstate '45000'
    set message_text = 'el usuario modificador no puede ser nulo o vacio';
    end if;

    update transaccion set anio = p_anio, mes = p_mes,descripcion = case when p_descripcion is null then null else trim(p_descripcion)end,monto = p_monto,
    fecha = p_fecha,metodo_pago = trim(p_metodo_pago),num_factura = case when p_num_factura is null then null else trim(p_num_factura)end,
    observaciones = case when p_observaciones is null then null else trim(p_observaciones) end,modificado_user = trim(p_modificado_por), modificado_fecha = current_timestamp
    where id_transaccion = p_id_transaccion;
end $$ 
delimiter ; 

delimiter $$ 
drop procedure if exists sp_eliminar_transaccion $$
create procedure sp_eliminar_transaccion(p_id_transaccion int)
begin 
    if p_id_transaccion is null or p_id_transaccion <= 0 then
    signal sqlstate '45000'
     set message_text = 'el id de la transaccion es invalido';
    end if;

    if not exists ( select 1 from transaccion
    where id_transaccion = p_id_transaccion) then
    signal sqlstate '45000'
    set message_text = 'la transaccion no existe';
    end if;
  
    delete from transaccion
    where id_transaccion = p_id_transaccion;

end $$ 
delimiter ;

delimiter $$
drop procedure if exists sp_consultar_transaccion $$ 
create procedure sp_consultar_transaccion(p_id_transaccion int )
begin  
	select * from transaccion where id_transaccion = p_id_transaccion;
end $$ 
delimiter ; 

delimiter $$
drop procedure if exists sp_listar_transaccion $$
create procedure sp_listar_transaccion(p_id_presupuesto int )
begin 
	select id_transaccion, descripcion from transaccion  where id_presupuesto = p_id_presupuesto;
end $$ 
delimiter ; 


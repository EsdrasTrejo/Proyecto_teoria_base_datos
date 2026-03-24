ALTER TABLE obligacion_fija AUTO_INCREMENT = 6001;

delimiter $$

drop procedure if exists sp_insertar_obligacion_fija $$

create procedure sp_insertar_obligacion_fija( p_id_subcategoria int,p_nombre varchar(300),p_descripcion varchar(500),p_monto decimal(12,2),p_dia_vencimiento int,
p_fecha_inicio date,p_fecha_fin date,p_creado_por varchar(300),p_id_usuario int)
begin 
    if p_id_subcategoria is null or p_id_subcategoria <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id de la subcategoria es invalido';
    end if;

    if not exists (select 1 from subcategoria
    where id_subcategoria = p_id_subcategoria) then
    signal sqlstate '45000'
    set message_text = 'la subcategoria no existe';
    end if;

    if p_nombre is null or trim(p_nombre) = '' then
    signal sqlstate '45000'
    set message_text = 'el nombre no puede ser nulo o vacio';
    end if;

    if p_monto is null then
    signal sqlstate '45000'
    set message_text = 'el monto no puede ser nulo';
    end if;

    if p_monto < 0 then
    signal sqlstate '45000'
    set message_text = 'el monto no puede ser negativo';
    end if;

    if p_dia_vencimiento is null or p_dia_vencimiento < 1 or p_dia_vencimiento > 31 then
    signal sqlstate '45000'
    set message_text = 'el dia de vencimiento debe estar entre 1 y 31';
    end if;

    if p_fecha_inicio is null then
    signal sqlstate '45000'
    set message_text = 'la fecha de inicio no puede ser nula';
    end if;

    if p_fecha_fin is not null and p_fecha_fin < p_fecha_inicio then
    signal sqlstate '45000'
    set message_text = 'la fecha fin no puede ser menor que la fecha inicio';
    end if;

    if p_creado_por is null or trim(p_creado_por) = '' then
    signal sqlstate '45000'
    set message_text = 'el usuario creador no puede ser nulo o vacio';
    end if;

    if exists (select 1 from obligacion_fija
    where id_subcategoria = p_id_subcategoria and lower(trim(nombre)) = lower(trim(p_nombre))
    and vigente = 1 ) then
    signal sqlstate '45000'
    set message_text = 'ya existe una obligacion fija activa con ese nombre en la subcategoria';
    end if;
    
     if p_id_usuario is null or p_id_usuario <= 0 then
     signal sqlstate '45000'
     set message_text = 'el id del usuario es invalido';
    end if;
    if not exists ( select 1 from usuario
    where id_usuario = p_id_usuario ) then
    signal sqlstate '45000'
    set message_text = 'el usuario no existe';
    end if;


    insert into obligacion_fija( id_subcategoria, nombre, descripcion, monto_mensual, dia_vencimiento, vigente, fecha_inicio, fecha_finalizacion, creado_user,
    creado_fecha,id_usuario)
    values(p_id_subcategoria,trim(p_nombre),case when p_descripcion is null then null else trim(p_descripcion) end,p_monto,p_dia_vencimiento,
    1,p_fecha_inicio, p_fecha_fin, trim(p_creado_por), current_timestamp,p_id_usuario);
end $$
delimiter ;

call sp_insertar_obligacion_fija(
    20,
    'pago libros',
    'pago de libros de universidad',
    1023.00,
    12,
    '2026-09-01',
    '2026-11-30',
    'esdras'
);

delimiter $$ 
drop procedure if exists sp_actualizar_obligacion_fija $$
create procedure sp_actualizar_obligacion_fija( p_id_obligacion int, p_nombre varchar(300), p_descripcion varchar(500), p_monto decimal(12,2), p_dia_vencimiento int,
p_fecha_fin date, p_activo tinyint,p_modificado_por varchar(300))
begin 
	
    declare v_id_subcategoria int;
    declare v_fecha_inicio date;

    if p_id_obligacion is null or p_id_obligacion <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id de la obligacion es invalido';
    end if;

    if not exists ( select 1 from obligacion_fija
    where id_obligacion = p_id_obligacion) then
    signal sqlstate '45000'
    set message_text = 'la obligacion fija no existe';
    end if;
    
    if p_nombre is null or trim(p_nombre) = '' then
    signal sqlstate '45000'
    set message_text = 'el nombre no puede ser nulo o vacio';
    end if;

    if p_monto is null then
    signal sqlstate '45000'
    set message_text = 'el monto no puede ser nulo';
    end if;

    if p_monto < 0 then
    signal sqlstate '45000'
    set message_text = 'el monto no puede ser negativo';
    end if;

    if p_dia_vencimiento is null or p_dia_vencimiento < 1 or p_dia_vencimiento > 31 then
    signal sqlstate '45000'
    set message_text = 'el dia de vencimiento debe estar entre 1 y 31';
    end if;

    if p_activo is null or p_activo not in (0,1) then
    signal sqlstate '45000'
    set message_text = 'el estado vigente solo puede ser 0 o 1';
    end if;

    if p_modificado_por is null or trim(p_modificado_por) = '' then
    signal sqlstate '45000'
    set message_text = 'el usuario modificador no puede ser nulo o vacio';
    end if;

    select id_subcategoria, fecha_inicio
    into v_id_subcategoria, v_fecha_inicio
    from obligacion_fija
    where id_obligacion = p_id_obligacion;

    if p_fecha_fin is not null and p_fecha_fin < v_fecha_inicio then
    signal sqlstate '45000'
    set message_text = 'la fecha fin no puede ser menor que la fecha inicio';
    end if;

    if exists ( select 1 from obligacion_fija
    where id_subcategoria = v_id_subcategoria and lower(trim(nombre)) = lower(trim(p_nombre))
    and id_obligacion <> p_id_obligacion and vigente = 1) then
    signal sqlstate '45000'
   set message_text = 'ya existe otra obligacion fija activa con ese nombre en la subcategoria';
    end if;

    update obligacion_fija set nombre = trim(p_nombre), descripcion = case when p_descripcion is null then null else trim(p_descripcion) end, monto_mensual = p_monto,
    dia_vencimiento = p_dia_vencimiento, vigente = p_activo, fecha_finalizacion = p_fecha_fin, modificado_user = trim(p_modificado_por), modificado_fecha = current_timestamp
    where id_obligacion = p_id_obligacion;
end $$ 
delimiter ;

delimiter $$ 
drop procedure if exists sp_eliminar_obligacion $$
create procedure sp_eliminar_obligacion(p_id_obligacion int, p_modificado_por varchar(300))
begin 
    if p_id_obligacion is null or p_id_obligacion <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id de la obligacion es invalido';
    end if;

    if not exists ( select 1 from obligacion_fija
    where id_obligacion = p_id_obligacion) then
    signal sqlstate '45000'
    set message_text = 'la obligacion no existe';
    end if;

    if p_modificado_por is null or trim(p_modificado_por) = '' then
    signal sqlstate '45000'
    set message_text = 'el usuario modificador no puede ser nulo o vacio';
    end if;

    if exists ( select 1 from obligacion_fija
    where id_obligacion = p_id_obligacion and vigente = 0) then
    signal sqlstate '45000'
    set message_text = 'la obligacion ya se encuentra inactiva';
    end if;

    update obligacion_fija set vigente = 0, modificado_user = trim(p_modificado_por), modificado_fecha = current_timestamp
    where id_obligacion = p_id_obligacion;
end $$ 
delimiter ;

delimiter $$
drop procedure if exists sp_consultar_obligacion_fija $$ 
create procedure sp_consultar_obligacion_fija(p_id_obligacion int )
begin  
	select * from obligacion_fija where id_obligacion = p_id_obligacion;
end $$ 
delimiter ; 


delimiter $$
drop procedure if exists sp_listar_obligacion_fija $$
create procedure sp_listar_obligacion_fija(p_id_usuario int )
begin 
	select id_usuario,id_obligacion, nombre from obligacion_fija where id_usuario = p_id_usuario;
end $$ 
delimiter ; 
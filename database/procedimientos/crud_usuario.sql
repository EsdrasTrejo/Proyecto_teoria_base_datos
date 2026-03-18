delimiter $$

drop procedure if exists sp_insertar_usuario $$

create procedure sp_insertar_usuario( p_nombre varchar(300), p_apellido varchar(300), p_correo varchar(300), p_salario_mensual decimal(12,2),
    p_creado_por varchar(300))
begin
    if p_nombre is null or trim(p_nombre) = '' then
        signal sqlstate '45000'
        set message_text = 'el nombre no puede ser nulo o vacio';
    end if;

    if p_apellido is null or trim(p_apellido) = '' then
        signal sqlstate '45000'
        set message_text = 'el apellido no puede ser nulo o vacio';
    end if;

    if p_correo is null or trim(p_correo) = '' then
        signal sqlstate '45000'
        set message_text = 'el correo no puede ser nulo o vacio';
    end if;

    if p_salario_mensual is null then
        signal sqlstate '45000'
        set message_text = 'el salario mensual no puede ser nulo';
    end if;

    if p_salario_mensual < 0 then
        signal sqlstate '45000'
        set message_text = 'el salario mensual no puede ser negativo';
    end if;

    if p_creado_por is null or trim(p_creado_por) = '' then
        signal sqlstate '45000'
        set message_text = 'el usuario creador no puede ser nulo o vacio';
    end if;

    if exists (
        select 1
        from usuario
        where lower(trim(correo)) = lower(trim(p_correo))
    ) then
        signal sqlstate '45000'
        set message_text = 'ya existe un usuario con ese correo';
    end if;

    insert into usuario(
        nombre,
        apellido,
        correo,
        fecha_registro,
        salario_mensual,
        estado,
        creador_user
    )
    values(
        trim(p_nombre),
        trim(p_apellido),
        trim(p_correo),
        curdate(),
        p_salario_mensual,
        1,
        trim(p_creado_por)
    );
end $$

delimiter ;
call sp_insertar_usuario('esdras','carranza','esdrastrejo@gmail.com',1000,'admin1');



delimiter $$
drop procedure if exists sp_eliminar_usuario $$

create procedure sp_eliminar_usuario(in p_id_usuario int )
begin 
	update usuario set estado = 0,modificado_en = current_timestamp where id_usuario = p_id_usuario;
end $$

delimiter ;

delimiter $$ 
drop procedure if exists sp_consultar_usuario $$ 
create procedure sp_consultar_usuario(in p_id_usuario int)
begin 
	select id_usuario, nombre, apellido, correo, fecha_registro, salario_mensual, estado, 
	creador_user, modificado_por_user, creado_en, modificado_en from usuario where id_usuario=p_id_usuario;
end $$ 
delimiter ;
delimiter $$

drop procedure if exists sp_actualizar_usuario $$

create procedure sp_actualizar_usuario( p_id_usuario int, p_nombre varchar(300), p_apellido varchar(300), p_correo varchar(300), p_salario_mensual decimal(12,2),
 p_estado tinyint, p_modificado_por varchar(300))
begin
    if p_id_usuario is null or p_id_usuario <= 0 then
        signal sqlstate '45000'
        set message_text = 'el id del usuario es invalido';
    end if;

    if not exists (
        select 1
        from usuario
        where id_usuario = p_id_usuario
    ) then
        signal sqlstate '45000'
        set message_text = 'el usuario no existe';
    end if;

    if p_nombre is null or trim(p_nombre) = '' then
        signal sqlstate '45000'
        set message_text = 'el nombre no puede ser nulo o vacio';
    end if;

    if p_apellido is null or trim(p_apellido) = '' then
        signal sqlstate '45000'
        set message_text = 'el apellido no puede ser nulo o vacio';
    end if;

    if p_correo is null or trim(p_correo) = '' then
        signal sqlstate '45000'
        set message_text = 'el correo no puede ser nulo o vacio';
    end if;

    if p_salario_mensual is null then
        signal sqlstate '45000'
        set message_text = 'el salario mensual no puede ser nulo';
    end if;

    if p_salario_mensual < 0 then
        signal sqlstate '45000'
        set message_text = 'el salario mensual no puede ser negativo';
    end if;

    if p_estado is null or p_estado not in (0,1) then
        signal sqlstate '45000'
        set message_text = 'el estado solo puede ser 0 o 1';
    end if;

    if p_modificado_por is null or trim(p_modificado_por) = '' then
        signal sqlstate '45000'
        set message_text = 'el usuario modificador no puede ser nulo o vacio';
    end if;

    if exists (
        select 1
        from usuario
        where lower(trim(correo)) = lower(trim(p_correo))
          and id_usuario <> p_id_usuario
    ) then
        signal sqlstate '45000'
        set message_text = 'ya existe otro usuario con ese correo';
    end if;

    update usuario
    set nombre = trim(p_nombre),
        apellido = trim(p_apellido),
        correo = trim(p_correo),
        salario_mensual = p_salario_mensual,
        estado = p_estado,
        modificado_user = trim(p_modificado_por),
        modificado_fecha = current_timestamp
    where id_usuario = p_id_usuario;
end $$

delimiter ;

delimiter $$
drop procedure if exists sp_listar_usuario $$
create procedure sp_listar_usuario() 
begin 
	select id_usuario,estado from usuario;
end $$ 
delimiter ;
















  
  
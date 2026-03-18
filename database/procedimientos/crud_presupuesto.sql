ALTER TABLE categoria AUTO_INCREMENT = 4001;
USE presupuesto_personal_proyecto;
delimiter $$
drop procedure if exists sp_ingresar_presupuesto $$
create procedure sp_ingresar_presupuesto( p_id_usuario int, p_nombre varchar(300), anio_periodo_inicio int, mes_periodo_inicio int,
   anio_periodo_fin int, mes_periodo_fin int, p_creado_por varchar(300))
begin
    if p_id_usuario is null or p_id_usuario <= 0 then
        signal sqlstate '45000'
        set message_text = 'el id del usuario es invalido';
    end if;
    if not exists ( select 1 from usuario
        where id_usuario = p_id_usuario
    ) then
        signal sqlstate '45000'
        set message_text = 'el usuario no existe';
    end if;
    if p_nombre is null or trim(p_nombre) = '' then
        signal sqlstate '45000'
        set message_text = 'el nombre del presupuesto no puede ser nulo o vacio';
    end if;
    if anio_periodo_inicio is null or anio_periodo_inicio <= 0 then
        signal sqlstate '45000'
        set message_text = 'el año de inicio es invalido';
    end if;
    if mes_periodo_inicio is null or mes_periodo_inicio not in (1,2,3,4,5,6,7,8,9,10,11,12) then
        signal sqlstate '45000'
        set message_text = 'el mes de inicio debe estar entre 1 y 12';
    end if;
    if anio_periodo_fin is null or anio_periodo_fin <= 0 then
        signal sqlstate '45000'
        set message_text = 'el año de fin es invalido';
    end if;
    if mes_periodo_fin is null or mes_periodo_fin not in (1,2,3,4,5,6,7,8,9,10,11,12) then
        signal sqlstate '45000'
        set message_text = 'el mes de fin debe estar entre 1 y 12';
    end if;
    if p_creado_por is null or trim(p_creado_por) = '' then
        signal sqlstate '45000'
        set message_text = 'el usuario creador no puede ser nulo o vacio';
    end if;

    if (anio_periodo_fin < anio_periodo_inicio)
       or (anio_periodo_fin = anio_periodo_inicio and mes_periodo_fin < mes_periodo_inicio) then
        signal sqlstate '45000'
        set message_text = 'el periodo final no puede ser menor al periodo inicial';
    end if;
    if exists ( select 1 from presupuesto
      where id_usuario = p_id_usuario and lower(trim(nombre)) = lower(trim(p_nombre)) and year_inicio = anio_periodo_inicio
      and mes_inicio = mes_periodo_inicio and year_fin = anio_periodo_fin and mes_fin = mes_periodo_fin
    ) then
        signal sqlstate '45000'
        set message_text = 'ya existe un presupuesto con ese nombre y periodo para este usuario';
    end if;

    insert into presupuesto(
        id_usuario, nombre, year_inicio, mes_inicio, year_fin, mes_fin, estado, creado_user, creado_fecha)
    values(p_id_usuario,trim(p_nombre),anio_periodo_inicio,mes_periodo_inicio,anio_periodo_fin,mes_periodo_fin,1,trim(p_creado_por),
  current_timestamp);
end $$
delimiter ;

 call sp_ingresar_presupuesto(1,'universitario',2025,12,2026,4,'esdras');
 
 delimiter $$

drop procedure if exists sp_actualizar_presupuesto $$

create procedure sp_actualizar_presupuesto( p_id_presupuesto int, p_nombre varchar(300), anio_periodo_inicio int, mes_periodo_inicio int,
    in anio_periodo_fin int, mes_periodo_fin int, p_modificado_por varchar(300))
begin
    declare v_id_usuario int;
    if p_id_presupuesto is null or p_id_presupuesto <= 0 then
        signal sqlstate '45000'
        set message_text = 'el id del presupuesto es invalido';
    end if;
    if not exists ( select 1 from presupuesto
        where id_presupuesto = p_id_presupuesto
    ) then
        signal sqlstate '45000'
        set message_text = 'el presupuesto no existe';
    end if;
    if p_nombre is null or trim(p_nombre) = '' then
        signal sqlstate '45000'
        set message_text = 'el nombre del presupuesto no puede ser nulo o vacio';
    end if;
    if anio_periodo_inicio is null or anio_periodo_inicio <= 0 then
        signal sqlstate '45000'
        set message_text = 'el año de inicio es invalido';
    end if;
    if mes_periodo_inicio is null or mes_periodo_inicio not in (1,2,3,4,5,6,7,8,9,10,11,12) then
        signal sqlstate '45000'
        set message_text = 'el mes de inicio debe estar entre 1 y 12';
    end if;
    if anio_periodo_fin is null or anio_periodo_fin <= 0 then
        signal sqlstate '45000'
        set message_text = 'el año de fin es invalido';
    end if;
    if mes_periodo_fin is null or mes_periodo_fin not in (1,2,3,4,5,6,7,8,9,10,11,12) then
        signal sqlstate '45000'
        set message_text = 'el mes de fin debe estar entre 1 y 12';
    end if;
    if (anio_periodo_fin < anio_periodo_inicio)
       or (anio_periodo_fin = anio_periodo_inicio and mes_periodo_fin < mes_periodo_inicio) then
    signal sqlstate '45000'
    set message_text = 'el periodo final no puede ser menor al periodo inicial';
    end if;
    if p_modificado_por is null or trim(p_modificado_por) = '' then
    signal sqlstate '45000'
    set message_text = 'el usuario modificador no puede ser nulo o vacio';
    end if;
    select id_usuario into v_id_usuario from presupuesto
    where id_presupuesto = p_id_presupuesto;
    if exists ( select 1 from presupuesto
        where id_usuario = v_id_usuario and lower(trim(nombre)) = lower(trim(p_nombre)) and year_inicio = anio_periodo_inicio
        and mes_inicio = mes_periodo_inicio and year_fin = anio_periodo_fin and mes_fin = mes_periodo_fin
        and id_presupuesto <> p_id_presupuesto
    ) then  
    signal sqlstate '45000'
    set message_text = 'ya existe otro presupuesto con ese nombre y periodo para este usuario';
    end if;
    update presupuesto
    set nombre = trim(p_nombre), year_inicio = anio_periodo_inicio, mes_inicio = mes_periodo_inicio, year_fin = anio_periodo_fin,
        mes_fin = mes_periodo_fin, modificado_user = trim(p_modificado_por), modificado_fecha = current_timestamp
    where id_presupuesto = p_id_presupuesto;
end $$
delimiter ;
 
delimiter $$
drop procedure if exists sp_consultar_presupuesto $$ 
create procedure sp_consultar_presupuesto(p_id_presupuesto int)
 begin 
 	select * from presupuesto where id_presupuesto = p_id_presupuesto;
 end $$ 
delimiter ;

delimiter $$

drop procedure if exists sp_eliminar_presupuesto $$

create procedure sp_eliminar_presupuesto( p_id_presupuesto int)
begin
    if p_id_presupuesto is null or p_id_presupuesto <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id del presupuesto es invalido';
    end if;
    if not exists ( select 1 from presupuesto
        where id_presupuesto = p_id_presupuesto
    ) then
        signal sqlstate '45000'
        set message_text = 'el presupuesto no existe';
    end if;
    if exists ( select 1 from transaccion
        where id_presupuesto = p_id_presupuesto
    ) then
        signal sqlstate '45000'
        set message_text = 'no se puede eliminar el presupuesto porque tiene transacciones asociadas';
    end if;
    delete from presupuesto
    where id_presupuesto = p_id_presupuesto;
end $$
delimiter ;

delimiter $$
drop procedure if exists sp_listar_presupuesto $$
create procedure sp_listar_presupuesto(p_id_usuario int )
begin 
	select id_presupuesto, nombre from presupuesto where id_usuario = p_id_usuario;
end $$ 
delimiter ; 
 
 
 
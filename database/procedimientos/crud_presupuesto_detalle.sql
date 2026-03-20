USE presupuesto_personal_proyecto;
ALTER TABLE presupuesto_detalle AUTO_INCREMENT = 5001;

delimiter $$
drop procedure if exists sp_insertar_presupuesto_detalle $$
create procedure sp_insertar_presupuesto_detalle(  p_id_presupuesto int, p_id_subcategoria int, p_monto_mensual decimal(12,2), p_observaciones varchar(1000),
 p_creado_por varchar(300))
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
    if p_id_subcategoria is null or p_id_subcategoria <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id de la subcategoria es invalido';
    end if;
    if not exists (select 1 from subcategoria
    where id_subcategoria = p_id_subcategoria) then
   signal sqlstate '45000'
    set message_text = 'la subcategoria no existe';
    end if;
    if p_monto_mensual is null then
    signal sqlstate '45000'
    set message_text = 'el monto mensual no puede ser nulo';
    end if;
    if p_monto_mensual < 0 then
    signal sqlstate '45000'
    set message_text = 'el monto mensual no puede ser negativo';
    end if;
    if p_creado_por is null or trim(p_creado_por) = '' then
    signal sqlstate '45000'
    set message_text = 'el usuario creador no puede ser nulo o vacio';
    end if;
    if exists ( select 1 from presupuesto_detalle
     where id_presupuesto = p_id_presupuesto and id_subcategoria = p_id_subcategoria
    ) then
    signal sqlstate '45000'
    set message_text = 'ya existe un detalle para esa subcategoria en ese presupuesto';
    end if;
    insert into presupuesto_detalle( id_presupuesto, id_subcategoria, monto_mensual, observaciones, creado_user, creado_fecha)
    values( p_id_presupuesto,p_id_subcategoria, p_monto_mensual,case when p_observaciones is null then null else trim(p_observaciones) end, 
    trim(p_creado_por),current_timestamp);
end $$
delimiter ;

delimiter $$ 
drop procedure if exists sp_actualizar_presupuesto_detalle $$
create procedure sp_actualizar_presupuesto_detalle( p_id_detalle int, p_monto_mensual decimal(12,2), p_observaciones varchar(1000),  p_modificado_por varchar(300))
begin 
	
    if p_id_detalle is null or p_id_detalle <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id del detalle es invalido';
    end if;

    if not exists ( select 1  from presupuesto_detalle
        where id_presupuesto_detalle = p_id_detalle
    ) then
    
    signal sqlstate '45000'
    set message_text = 'el detalle del presupuesto no existe';
    end if;
    if p_monto_mensual is null then
    signal sqlstate '45000'
    set message_text = 'el monto mensual no puede ser nulo';
    end if;
    if p_monto_mensual < 0 then
    signal sqlstate '45000'
    set message_text = 'el monto mensual no puede ser negativo';
    end if;
    if p_modificado_por is null or trim(p_modificado_por) = '' then
    signal sqlstate '45000'
    set message_text = 'el usuario modificador no puede ser nulo o vacio';
    end if;
    update presupuesto_detalle 
    set monto_mensual = p_monto_mensual, observaciones = case when p_observaciones is null then null else trim(p_observaciones)end, modificado_user = trim(p_modificado_por),
    modificado_fecha = current_timestamp
    where id_presupuesto_detalle = p_id_detalle;
end $$ 
delimiter ;

delimiter $$ 
drop procedure if exists sp_eliminar_presupuesto_detalle $$
create procedure sp_eliminar_presupuesto_detalle(p_id_detalle int)
begin 
    if p_id_detalle is null or p_id_detalle <= 0 then
    signal sqlstate '45000'
     set message_text = 'el id del detalle es invalido';
    end if;
    if not exists ( select 1 from presupuesto_detalle
    where id_presupuesto_detalle = p_id_detalle) then
    signal sqlstate '45000'
    set message_text = 'el detalle del presupuesto no existe';
    end if;
    if exists ( select 1 from transaccion
    where id_presupuesto_detalle = p_id_detalle) then
    signal sqlstate '45000'
    set message_text = 'no se puede eliminar el detalle porque tiene transacciones asociadas';
    end if;
    delete from presupuesto_detalle
    where id_presupuesto_detalle = p_id_detalle;
end $$ 
delimiter ;

delimiter $$
drop procedure if exists sp_consultar_presupuesto_detalle $$ 
create procedure sp_consultar_presupuesto_detalle(p_id_presupuesto_detalle int )
begin  
	select * from presupuesto_detalle where id_presupuesto_detalle = p_id_presupuesto_detalle;
end $$ 
delimiter ; 

delimiter $$
drop procedure if exists sp_listar_presupuesto_detalle $$
create procedure sp_listar_presupuesto_detalle(p_id_presupuesto int )
begin 
	select id_presupuesto_detalle from presupuesto_detalle where id_presupuesto = p_id_presupuesto;
end $$ 
delimiter ; 
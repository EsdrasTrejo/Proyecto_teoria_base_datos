USE presupuesto_personal_proyecto;
ALTER TABLE presupuesto_detalle AUTO_INCREMENT = 5001;

delimiter $$
drop procedure if exists sp_insertar_presupuesto_detalle $$
create procedure sp_insertar_presupuesto_detalle(p_id_presupuesto int, p_id_subcategoria int, p_monto_mensual decimal(12,2), 
p_observaciones varchar(1000) , p_creado_por varchar(300))
begin 
	insert into presupuesto_detalle(id_presupuesto,id_subcategoria,monto_mensual,observaciones, creado_user,creado_fecha)
	values(p_id_presupuesto,p_id_subcategoria,p_monto_mensual,p_observaciones,p_creado_por,current_timestamp);
end $$
delimiter ;

delimiter $$ 
drop procedure if exists sp_actualizar_presupuesto_detalle $$
create procedure sp_actualizar_presupuesto_detalle(p_id_detalle int, p_monto_mensual decimal(12,2), p_observaciones varchar(1000), p_modificado_por varchar(300))
begin 
	update presupuesto_detalle set  monto_mensual= p_monto_mensual, observaciones = p_observaciones, modificado_user =p_modificado_por,modificado_fecha = current_timestamp where id_presupuesto_detalle = p_id_detalle;

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
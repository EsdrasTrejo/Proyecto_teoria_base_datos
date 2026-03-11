ALTER TABLE obligacion_fija AUTO_INCREMENT = 6001;

delimiter $$
drop procedure if exists sp_insertar_obligacion_fija $$
create procedure sp_insertar_obligacion_fija(p_id_usuario int , p_id_subcategoria int, p_nombre varchar(300)
, p_descripcion varchar(500), p_monto decimal(12,2), p_dia_vencimiento int, p_fecha_inicio date, p_fecha_fin date, p_creado_por varchar(300))
begin 
	insert into obligacion_fija(id_usuario,id_subcategoria,nombre,descripcion,monto,dia_vencimiento,vigente,fecha_inicio,
	fecha_finalizacion,creado_user,creado_fecha)
	values(p_id_usuario,p_id_subcategoria,p_nombre,p_descripcion,p_monto,p_dia_vencimiento,1,p_fecha_inicio,p_fecha_fin,
p_creado_por,current_timestamp);
end $$
delimiter ;

delimiter $$ 
drop procedure if exists sp_actualizar_obligacion_fija $$
create procedure sp_actualizar_obligacion_fija(p_id_obligacion int, p_nombre varchar(300), p_descripcion varchar(500), p_monto decimal(12,2),
p_dia_vencimiento int, p_fecha_fin date, p_activo tinyint, p_modificado_por varchar(300))
begin 
	update obligacion_fija set nombre = p_nombre, descripcion = p_descripcion,monto_mensual=p_monto,dia_vencimiento=p_dia_vencimiento 
,vigente = p_activo, fecha_finalizacion = p_fecha_fin, modificado_user= p_modificado_por, modificado_fecha = current_timestamp where id_obligacion = p_id_obligacion;

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
	select id_usuario, nombre from obligacion_fija where id_usuario = p_id_usuario;
end $$ 
delimiter ; 
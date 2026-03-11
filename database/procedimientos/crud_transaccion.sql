ALTER TABLE transaccion AUTO_INCREMENT = 7001;

delimiter $$
drop procedure if exists sp_insertar_transaccion $$
create procedure sp_insertar_transaccion(p_id_usuario int, p_id_presupuesto int, p_anio int , p_mes int, p_id_subcategoria int, p_id_obligacion int, p_tipo varchar(300),
p_descripcion varchar(1000), p_monto decimal(12,2),p_fecha date, p_metodo_pago varchar(300),
p_num_factura varchar(300), p_observaciones varchar(1000), p_creado_por varchar(300))
begin 
	insert into transaccion(id_categoria,nombre,descripcion, es_defecto, creado_user, creado_fecha)
	values(p_id_categoria,p_nombre,p_descripcion,p_es_defecto,p_creado_por,current_timestamp);
end $$
delimiter ;

delimiter $$ 
drop procedure if exists sp_actualizar_transaccion $$
create procedure sp_actualizar_transaccion(p_id_transaccion int, p_anio int, p_mes int, p_descripcion varchar(300),
p_monto decimal(12,2), p_fecha date, p_metodo_pago varchar(300), p_num_factura varchar(300), p_observaciones varchar(300), p_modificado_por varchar(300))
begin 
	update transaccion set year=p_anio,mes = p_mes, descripcion = p_descripcion,monto=p_monto,fecha = p_fecha,
metodo_pago=p_metodo_pago,num_factura=p_num_factura,observaciones=p_observaciones,modificado_user= p_modificado_por, modificado_fecha = current_timestamp where id_transaccion = p_id_transaccion;

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
	select id_transaccion, nombre from transaccion  where id_presupuesto = p_id_presupuesto;
end $$ 
delimiter ; 


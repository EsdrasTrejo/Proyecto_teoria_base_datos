ALTER TABLE categoria AUTO_INCREMENT = 4001;
USE presupuesto_personal_proyecto;
delimiter $$ 
drop procedure if exists sp_ingresar_presupuesto $$ 
 create procedure sp_ingresar_presupuesto(p_id_usuario int, p_nombre varchar (300),anio_periodo_inicio int,mes_periodo_inicio int,anio_periodo_fin int,
 mes_periodo_fin int,p_creado_por varchar(300))
 begin 
 	insert into presupuesto (id_usuario,nombre,year_inicio,mes_inicio,year_fin, mes_fin,
 	creado_user,creado_fecha) values (p_id_usuario,p_nombre,anio_periodo_inicio,mes_periodo_inicio, anio_periodo_fin,
 	mes_periodo_fin,p_creado_por,current_timestamp);
 end $$
 
delimiter ;

 call sp_ingresar_presupuesto(1,'universitario',2025,12,2026,4,'esdras');
 
 
delimiter $$ 
drop procedure if exists sp_actualizar_presupuesto $$
create procedure sp_actualizar_presupuesto(p_id_presupuesto int, p_nombre varchar (300),anio_periodo_inicio int,mes_periodo_inicio int,anio_periodo_fin int,
 mes_periodo_fin int,p_creado_por varchar(300))
begin 
	update subcategoria set nombre = p_nombre,year_inicio = anio_periodo_inicio,mes_inicio = mes_periodo_inicio,year_fin=anio_periodo_fin,mes_fin=mes_periodo_fin, modificado_user= p_creado_por, modificado_fecha = current_timestamp where id_presupuesto = p_id_presupuesto;

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
drop procedure if exists sp_listar_presupuesto $$
create procedure sp_listar_presupuesto(p_id_usuario int )
begin 
	select id_presupuesto, nombre from presupuesto where id_usuario = p_id_usuario;
end $$ 
delimiter ; 
 
 
 
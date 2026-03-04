use presupuesto_personal_proyecto;
ALTER TABLE categoria AUTO_INCREMENT = 2001;
delimiter $$

drop procedure if exists sp_insertar_categoria $$ 
create procedure sp_insertar_categoria(in p_nombre varchar(300), in p_descripcion varchar(1000), 
in p_tipo varchar (300),in p_creado_por varchar (300),in p_id_usuario int)
begin 
	insert into categoria ( nombre, descripcion, tipo, creado_user, creado_fecha, id_usuario_propetario)
	values (p_nombre,p_descripcion,p_tipo,p_creado_por,CURRENT_TIMESTAMP,p_id_usuario );
end $$

delimiter ; 

call sp_insertar_categoria('universidad', 'estos gastos era unicamente para mis estudios','gasto','esdras',1);
call sp_insertar_categoria('comida', 'estos gastos era unicamente para mi comida','gasto','esdraas',1);
call sp_insertar_categoria('trabajo', 'recibir bono extra del trabajo','ingreso','esdraas',1);

delimiter $$ 
drop procedure if exists sp_actualizar_categoria $$
create procedure sp_actualizar_categoria(in p_id_categoria int, in p_nombre varchar(300), in p_descripcion varchar(1000), in p_modificado_por varchar(300))
begin  
	update categoria set  nombre = p_nombre, descripcion = p_descripcion, modificado_user = p_modificado_por, modificado_fecha=current_timestamp where id_categoria = p_id_categoria;

end $$ 
delimiter ; 

call sp_actualizar_categoria(2003,'comida','ahora se va usar unicamente para alimentos','tio chepe');


delimiter $$ 
drop procedure if exists sp_consultar_categoria $$ 
create procedure sp_consultar_categoria(in p_id_categoria int )
begin 
	select * from categoria where id_categoria = p_id_categoria;
end $$ 
delimiter ; 

call sp_consultar_categoria(2001);

delimiter $$ 
drop procedure if exists sp_listar_categoria $$ 
create procedure sp_listar_categoria(in p_id_usuario int, in p_tipo varchar(300))
begin 
	select id_categoria, tipo from categoria where tipo = p_tipo and id_usuario_propetario=p_id_usuario;
end $$ 
delimiter ; 

call sp_listar_categoria(1,"gasto");













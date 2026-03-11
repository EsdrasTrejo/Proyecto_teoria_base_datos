ALTER TABLE usuario AUTO_INCREMENT = 3001;

delimiter $$
drop procedure if exists sp_insertar_subcategoria $$
create procedure sp_insertar_subcategoria(p_id_categoria int, p_nombre varchar(300), p_descripcion varchar(1000), p_es_defecto tinyint,p_creado_por varchar(300))
begin 
	insert into subcategoria(id_categoria,nombre,descripcion, es_defecto, creado_user, creado_fecha)
	values(p_id_categoria,p_nombre,p_descripcion,p_es_defecto,p_creado_por,current_timestamp);
end $$
delimiter ;

call sp_insertar_subcategoria(2001,'libros','libros texto que necesito',1,'esdras');

delimiter $$ 
drop procedure if exists sp_actualizar_subcategoria $$
create procedure sp_actualizar_subcategoria(p_id_subcategoria int, p_nombre varchar(300), p_descripcion varchar(1000), p_modificado varchar(300))
begin 
	update subcategoria set nombre = p_nombre, descripcion = p_descripcion, modificado_user= p_modificado, modificado_fecha = current_timestamp where id_subcategoria = p_id_subcategoria;

end $$ 
delimiter ; 
call sp_actualizar_subcategoria(1,'cuadernos','cuadernos que necesito','marco');

delimiter $$
drop procedure if exists sp_consultar_subcategoria $$ 
create procedure sp_consultar_subcategoria(p_id_subcategoria int )
begin  
	select * from subcategoria where id_subcategoria = p_id_subcategoria;
end $$ 
delimiter ; 

call sp_consultar_subcategoria(1);


delimiter $$
drop procedure if exists sp_listar_subcategoria $$
create procedure sp_listar_subcategoria(p_id_categoria int )
begin 
	select id_subcategoria, nombre from subcategoria where id_categoria = p_id_categoria;
end $$ 
delimiter ; 

call  sp_listar_subcategoria(2001);








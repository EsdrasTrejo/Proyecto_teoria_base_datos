use presupuesto_personal_proyecto;
ALTER TABLE categoria AUTO_INCREMENT = 2001;
delimiter $$

delimiter $$

drop procedure if exists sp_insertar_categoria $$
create procedure sp_insertar_categoria( p_nombre varchar(300), p_descripcion varchar(1000), p_tipo varchar(300),
p_creado_por varchar(300), p_id_usuario int)
begin
	
    if p_nombre is null or trim(p_nombre) = '' then
    signal sqlstate '45000'
     set message_text = 'el nombre de la categoria no puede ser nulo o vacio';
    end if;

    if p_descripcion is null or trim(p_descripcion) = '' then
    signal sqlstate '45000'
    set message_text = 'la descripcion no puede ser nula o vacia';
    end if;

    if p_tipo is null or trim(p_tipo) = '' then
    signal sqlstate '45000'
    set message_text = 'el tipo no puede ser nulo o vacio';
    end if;

    if p_creado_por is null or trim(p_creado_por) = '' then
    signal sqlstate '45000'
    set message_text = 'el usuario creador no puede ser nulo o vacio';
    end if;

    if p_id_usuario is null or p_id_usuario <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id del usuario es invalido';
    end if;

    if not exists ( select 1 from usuario
    where id_usuario = p_id_usuario) then
    signal sqlstate '45000'
    set message_text = 'el usuario no existe';
    end if;

    if exists ( select 1 from categoria
    where lower(trim(nombre)) = lower(trim(p_nombre)) and lower(trim(tipo)) = lower(trim(p_tipo))
    and id_usuario_propetario = p_id_usuario ) then
    signal sqlstate '45000'
    set message_text = 'ya existe una categoria con ese nombre y tipo para este usuario';
    end if;

    insert into categoria( nombre, descripcion, tipo, creado_user, creado_fecha, id_usuario_propetario)
    values( trim(p_nombre), trim(p_descripcion),trim(p_tipo), trim(p_creado_por), current_timestamp, p_id_usuario);
end $$
delimiter ;

call sp_insertar_categoria('universidad', 'estos gastos era unicamente para mis estudios','gasto','esdras',1);
call sp_insertar_categoria('comida', 'estos gastos era unicamente para mi comida','gasto','esdraas',1);
call sp_insertar_categoria('trabajo', 'recibir bono extra del trabajo','ingreso','esdraas',1);

delimiter $$
drop procedure if exists sp_actualizar_categoria $$
create procedure sp_actualizar_categoria( p_id_categoria int, p_nombre varchar(300), p_descripcion varchar(1000),
p_modificado_por varchar(300))
begin
    declare v_id_usuario int;
    declare v_tipo varchar(300);

    if p_id_categoria is null or p_id_categoria <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id de la categoria es invalido';
    end if;

    if not exists ( select 1 from categoria
    where id_categoria = p_id_categoria) then
    signal sqlstate '45000'
    set message_text = 'la categoria no existe';
    end if;

    if p_nombre is null or trim(p_nombre) = '' then
    signal sqlstate '45000'
    set message_text = 'el nombre no puede ser nulo o vacio';
    end if;

    if p_descripcion is null or trim(p_descripcion) = '' then
    signal sqlstate '45000'
    set message_text = 'la descripcion no puede ser nula o vacia';
    end if;

    if p_modificado_por is null or trim(p_modificado_por) = '' then
        signal sqlstate '45000'
        set message_text = 'el usuario modificador no puede ser nulo o vacio';
    end if;

    select id_usuario_propetario, tipo into v_id_usuario, v_tipo from categoria
    where id_categoria = p_id_categoria;
   
    if exists ( select 1 from categoria
    where lower(trim(nombre)) = lower(trim(p_nombre)) 
    and lower(trim(tipo)) = lower(trim(v_tipo)) and id_usuario_propetario = v_id_usuario and id_categoria <> p_id_categoria) then
    signal sqlstate '45000'
    set message_text = 'ya existe otra categoria con ese nombre para este usuario';
    end if;
    update categoria set nombre = trim(p_nombre), descripcion = trim(p_descripcion), modificado_user = trim(p_modificado_por), modificado_fecha = current_timestamp
    where id_categoria = p_id_categoria;
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

drop procedure if exists sp_eliminar_categoria $$

create procedure sp_eliminar_categoria( p_id_categoria int)
begin
    if p_id_categoria is null or p_id_categoria <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id de la categoria es invalido';
    end if;
    if not exists (select 1 from categoria
    where id_categoria = p_id_categoria) then
    signal sqlstate '45000'
    set message_text = 'la categoria no existe';
    end if;
    if exists ( select 1from subcategoria
    where id_categoria = p_id_categoria
    and indicador_activa = 1) then
    signal sqlstate '45000'
    set message_text = 'no se puede eliminar la categoria porque tiene subcategorias activas';
    end if;
    delete from categoria
    where id_categoria = p_id_categoria;
end $$

delimiter ;


delimiter $$ 
drop procedure if exists sp_listar_categoria $$ 
create procedure sp_listar_categoria(in p_id_usuario int, in p_tipo varchar(300))
begin 
	select id_categoria, tipo from categoria where tipo = p_tipo and id_usuario_propetario=p_id_usuario;
end $$ 
delimiter ; 

call sp_listar_categoria(1,"gasto");













ALTER TABLE usuario AUTO_INCREMENT = 3001;

delimiter $$

drop procedure if exists sp_insertar_subcategoria $$

create procedure sp_insertar_subcategoria( p_id_categoria int, p_nombre varchar(300), p_descripcion varchar(1000), p_es_defecto tinyint,
 p_creado_por varchar(300))
begin
    if p_id_categoria is null or p_id_categoria <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id de la categoria es invalido';
    end if;
    if not exists (select 1 from categoria
        where id_categoria = p_id_categoria
    ) then
    
    
    
    signal sqlstate '45000'
    set message_text = 'la categoria no existe';
    end if;
    
    
    if p_nombre is null or trim(p_nombre) = '' then
    signal sqlstate '45000'
    set message_text = 'el nombre de la subcategoria no puede ser nulo o vacio';
    end if;
    if p_descripcion is null or trim(p_descripcion) = '' then
    signal sqlstate '45000'
    set message_text = 'la descripcion no puede ser nula o vacia';
    end if;
    if p_es_defecto is null or p_es_defecto not in (0,1) then
    signal sqlstate '45000'
    set message_text = 'el valor de es_defecto solo puede ser 0 o 1';
    end if;
    if p_creado_por is null or trim(p_creado_por) = '' then
    signal sqlstate '45000'
    set message_text = 'el usuario creador no puede ser nulo o vacio';
    end if;

    if exists ( select 1 from subcategoria
        where id_categoria = p_id_categoria and lower(trim(nombre)) = lower(trim(p_nombre))
    ) then
    
    signal sqlstate '45000'
    set message_text = 'ya existe una subcategoria con ese nombre en la categoria';
    end if;
    if p_es_defecto = 1 and exists ( select 1 from subcategoria
        where id_categoria = p_id_categoria and es_defecto = 1 and indicador_activa = 1) then
        signal sqlstate '45000'
        set message_text = 'ya existe una subcategoria por defecto activa para esta categoria';
    end if;

    insert into subcategoria(
        id_categoria,
        nombre,
        descripcion,
        es_defecto,
        creado_user,
        creado_fecha
    )
    values(
        p_id_categoria,
        trim(p_nombre),
        trim(p_descripcion),
        p_es_defecto,
        trim(p_creado_por),
        current_timestamp
    );
end $$

delimiter ;

call sp_insertar_subcategoria(2001,'libros','libros texto que necesito',1,'esdras');

delimiter $$
drop procedure if exists sp_actualizar_subcategoria $$
create procedure sp_actualizar_subcategoria( p_id_subcategoria int, p_nombre varchar(300), p_descripcion varchar(1000),
 p_modificado varchar(300))
begin
    declare v_id_categoria int;
    if p_id_subcategoria is null or p_id_subcategoria <= 0 then
        signal sqlstate '45000'
        set message_text = 'el id de la subcategoria es invalido';
    end if;
    if not exists ( select 1 from subcategoria
    where id_subcategoria = p_id_subcategoria) then
        signal sqlstate '45000'
        set message_text = 'la subcategoria no existe';
    end if;
    if p_nombre is null or trim(p_nombre) = '' then
      signal sqlstate '45000'
     set message_text = 'el nombre de la subcategoria no puede ser nulo o vacio';
    end if;
    if p_descripcion is null or trim(p_descripcion) = '' then
    signal sqlstate '45000'
      set message_text = 'la descripcion no puede ser nula o vacia';
    end if;

    if p_modificado is null or trim(p_modificado) = '' then
    signal sqlstate '45000'
    set message_text = 'el usuario modificador no puede ser nulo o vacio';
    end if;

    select id_categoria into v_id_categoria
    from subcategoria
     where id_subcategoria = p_id_subcategoria;
    if exists ( select 1 from subcategoria
        where id_categoria = v_id_categoria and lower(trim(nombre)) = lower(trim(p_nombre)) and id_subcategoria <> p_id_subcategoria
    ) then
        signal sqlstate '45000'
        set message_text = 'ya existe otra subcategoria con ese nombre en la categoria';
    end if;
    update subcategoria
    set nombre = trim(p_nombre), descripcion = trim(p_descripcion), modificado_user = trim(p_modificado), modificado_fecha = current_timestamp
    where id_subcategoria = p_id_subcategoria;
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

drop procedure if exists sp_eliminar_subcategoria $$

create procedure sp_eliminar_subcategoria( p_id_subcategoria int)
begin
    if p_id_subcategoria is null or p_id_subcategoria <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id de la subcategoria es invalido';
    end if;
    if not exists ( select 1 from subcategoria
    where id_subcategoria = p_id_subcategoria
    ) then
    
    signal sqlstate '45000'
    set message_text = 'la subcategoria no existe';
    end if;
    if exists ( select 1 from presupuesto_detalle
        where id_subcategoria = p_id_subcategoria
    ) then
   signal sqlstate '45000'
   set message_text = 'no se puede eliminar la subcategoria porque esta en uso en presupuestos';
    end if;
    if exists ( select 1 from transaccion
        where id_subcategoria = p_id_subcategoria
    ) then
    signal sqlstate '45000'
    set message_text = 'no se puede eliminar la subcategoria porque esta en uso en transacciones';
    end if;
    delete from subcategoria
    where id_subcategoria = p_id_subcategoria;
end $$
delimiter ;


delimiter $$
drop procedure if exists sp_listar_subcategoria $$
create procedure sp_listar_subcategoria(p_id_categoria int )
begin 
	select id_subcategoria, nombre from subcategoria where id_categoria = p_id_categoria;
end $$ 
delimiter ; 

call  sp_listar_subcategoria(2001);








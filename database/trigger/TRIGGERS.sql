USE presupuesto_personal_proyecto;

delimiter $$

drop trigger if exists tr_categoria_crear_subcategoria_defecto $$

create trigger tr_categoria_crear_subcategoria_defecto
after insert on categoria
for each row
begin
    insert into subcategoria(
        id_categoria,
        nombre,
        descripcion,
        indicador_activa,
        es_defecto,
        creado_user,
        modificado_user,
        creado_fecha,
        modificado_fecha
    )
    values(
        new.id_categoria,
        new.nombre,
        concat('subcategoria por defecto de la categoria ', new.nombre),
        1,
        1,
        new.creado_user,
        new.creado_user,
        current_timestamp,
        current_timestamp
    );
end $$

delimiter ;
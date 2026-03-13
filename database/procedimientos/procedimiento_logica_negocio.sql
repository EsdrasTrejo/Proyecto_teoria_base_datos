USE presupuesto_personal_proyecto;

delimiter $$
drop procedure if exists sp_crear_presupuesto_completo $$
create procedure sp_crear_presupuesto_completo( p_id_usuario int, p_nombre varchar(300), p_descripcion varchar(1000), p_year_inicio int, p_mes_inicio int, p_year_fin int, p_mes_fin int,
     p_lista_subcategorias_json json, p_creado_por varchar(300)
)
begin

    declare v_id_presupuesto int;
    declare v_total_ingresos decimal(12,2) default 0.00;
    declare v_total_gastos decimal(12,2) default 0.00;
    declare v_total_ahorro decimal(12,2) default 0.00;

    declare exit handler for sqlexception
    begin
        rollback;
        resignal;
    end;

    if p_mes_inicio < 1 or p_mes_inicio > 12 then
        signal sqlstate '45000'
        set message_text = 'mes inicio invalido';
    end if;

    if p_mes_fin < 1 or p_mes_fin > 12 then
        signal sqlstate '45000'
        set message_text = 'mes fin invalido';
    end if;

    if p_year_fin < p_year_inicio 
    or (p_year_fin = p_year_inicio and p_mes_fin < p_mes_inicio) then
        signal sqlstate '45000'
        set message_text = 'periodo fin menor que periodo inicio';
    end if;

    if p_lista_subcategorias_json is null 
    or json_length(p_lista_subcategorias_json) = 0 then
        signal sqlstate '45000'
        set message_text = 'json de subcategorias vacio';
    end if;

    start transaction;

    insert into presupuesto(
        id_usuario,
        nombre,
        descripcion,
        year_inicio,
        mes_inicio,
        year_fin,
        mes_fin,
        total_ingresos,
        total_gastos,
        total_ahorro,
        fecha_hora_creacion,
        estado,
        creado_user,
        creado_fecha
    )
    values(
        p_id_usuario,
        p_nombre,
        p_descripcion,
        p_year_inicio,
        p_mes_inicio,
        p_year_fin,
        p_mes_fin,
        0.00,
        0.00,
        0.00,
        current_timestamp(),
        1,
        p_creado_por,
        current_timestamp()
    );

    set v_id_presupuesto = last_insert_id();

    insert into presupuesto_detalle(
        id_presupuesto,
        id_subcategoria,
        monto_mensual,
        observaciones,
        creado_user,
        creado_fecha
    )
    select
        v_id_presupuesto,
        jt.id_subcategoria,
        jt.monto_mensual,
        null,
        p_creado_por,
        current_timestamp()
    from json_table(
        p_lista_subcategorias_json,
        '$[*]' columns (
            id_subcategoria int path '$.id_subcategoria',
            monto_mensual decimal(12,2) path '$.monto_mensual'
        )
    ) jt;

    select
        ifnull(sum(case when c.tipo='ingreso' then pd.monto_mensual else 0 end),0),
        ifnull(sum(case when c.tipo='gasto' then pd.monto_mensual else 0 end),0),
        ifnull(sum(case when c.tipo='ahorro' then pd.monto_mensual else 0 end),0)
    into
        v_total_ingresos,
        v_total_gastos,
        v_total_ahorro
    from presupuesto_detalle pd
    join subcategoria s
        on s.id_subcategoria = pd.id_subcategoria
    join categoria c
        on c.id_categoria = s.id_categoria
    where pd.id_presupuesto = v_id_presupuesto;

    update presupuesto
    set total_ingresos = v_total_ingresos,
        total_gastos = v_total_gastos,
        total_ahorro = v_total_ahorro,
        modificado_user = p_creado_por,
        modificado_fecha = current_timestamp()
    where id_presupuesto = v_id_presupuesto;

    commit;

    select
        v_id_presupuesto as id_presupuesto_creado,
        v_total_ingresos as total_ingresos,
        v_total_gastos as total_gastos,
        v_total_ahorro as total_ahorro;

end $$

delimiter ;

call sp_crear_presupuesto_completo(
1,
'presupuesto ganado trabajo-mesada',
'primer trimestre',
2026,
1,
2026,
3,
'[
 {"id_subcategoria":3,"monto_mensual":20000},
 {"id_subcategoria":4,"monto_mensual":1000}
]',
'esdras'
);


delimiter $$
drop procedure if exists sp_registrar_transaccion_completa $$
create procedure sp_registrar_transaccion_completa(p_id_usuario int,p_id_presupuesto int,p_anio int,p_mes int,
p_id_subcategoria int, p_tipo varchar(300), p_descripcion varchar(1000), p_monto decimal(12,2), p_fecha date, p_metodo_pago varchar(300),
p_creado_por varchar(300)
)
begin
    declare v_year_inicio int;
    declare v_mes_inicio int;
    declare v_year_fin int;
    declare v_mes_fin int;
    declare v_id_presupuesto_detalle int;
    declare v_tipo_categoria varchar(300);
    declare v_existe_presupuesto int default 0;

    declare exit handler for sqlexception
    begin
     rollback;
     resignal;
    end;

    if p_mes < 1 or p_mes > 12 then
    signal sqlstate '45000'
    set message_text = 'mes invalido';
    end if;

    if p_monto <= 0 then
    signal sqlstate '45000'
    set message_text = 'el monto debe ser mayor que cero';
    end if;

    select count(*)
    into v_existe_presupuesto
    from presupuesto
    where id_presupuesto = p_id_presupuesto
   and id_usuario = p_id_usuario;

    if v_existe_presupuesto = 0 then
    signal sqlstate '45000'
    set message_text = 'el presupuesto no existe o no pertenece al usuario';
    end if;

    select year_inicio, mes_inicio, year_fin, mes_fin
    into v_year_inicio, v_mes_inicio, v_year_fin, v_mes_fin
    from presupuesto
    where id_presupuesto = p_id_presupuesto;

    if (p_anio < v_year_inicio)
    or (p_anio = v_year_inicio and p_mes < v_mes_inicio)
    or (p_anio > v_year_fin)
    or (p_anio = v_year_fin and p_mes > v_mes_fin) then
    signal sqlstate '45000'
    set message_text = 'el anio y mes no estan dentro de la vigencia del presupuesto';
    end if;

    select c.tipo
    into v_tipo_categoria
    from subcategoria s
    inner join categoria c on c.id_categoria = s.id_categoria
    where s.id_subcategoria = p_id_subcategoria;

    if v_tipo_categoria is null then
        signal sqlstate '45000'
        set message_text = 'la subcategoria no existe';
    end if;

    if lower(v_tipo_categoria) <> lower(p_tipo) then
    signal sqlstate '45000'
    set message_text = 'el tipo de transaccion no coincide con el tipo de la categoria';
    end if;

    select pd.id_presupuesto_detalle
    into v_id_presupuesto_detalle
    from presupuesto_detalle pd
    where pd.id_presupuesto = p_id_presupuesto
    and pd.id_subcategoria = p_id_subcategoria;

    if v_id_presupuesto_detalle is null then
    signal sqlstate '45000'
    set message_text = 'la subcategoria no esta asociada al presupuesto';
    end if;

    start transaction;

    insert into transaccion(
    id_presupuesto_detalle,
    year,
    mes,
    tipo_transaccion,
    descripcion,
    monto,
    fecha,
    metodo_pago,
    creado_user,
    creado_fecha
    )
    values(
    v_id_presupuesto_detalle,
    p_anio,
    p_mes,
    p_tipo,
    p_descripcion,
    p_monto,
    p_fecha,
    p_metodo_pago,
    p_creado_por,
        current_timestamp()
    );

    commit;

    select last_insert_id() as id_transaccion_creada;
end $$

delimiter ;

call sp_registrar_transaccion_completa(
    1,
    4003,
    2026,
    1,
    1,
    'gasto',
    'canje de loteria',
    850.00,
    '2026-01-15',
    'efectivo',
    'esdras'
);






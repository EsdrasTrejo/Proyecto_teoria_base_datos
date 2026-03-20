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

    insert into presupuesto( d_usuario,nombre,descripcion,year_inicio,mes_inicio, year_fin, mes_fin, total_ingresos, total_gastos, total_ahorro, fecha_hora_creacion,
    estado, creado_user, creado_fecha)
    values(p_id_usuario, p_nombre, p_descripcion, p_year_inicio, p_mes_inicio, p_year_fin, p_mes_fin, 0.00, 0.00, 0.00, current_timestamp(), 1, p_creado_por, current_timestamp());

    set v_id_presupuesto = last_insert_id();

    insert into presupuesto_detalle( id_presupuesto, id_subcategoria, monto_mensual, observaciones, creado_user, creado_fecha )
    select
        v_id_presupuesto,jt.id_subcategoria,jt.monto_mensual,null, p_creado_por, current_timestamp()
    from json_table(
        p_lista_subcategorias_json,
        '$[*]' columns (
            id_subcategoria int path '$.id_subcategoria',
            monto_mensual decimal(12,2) path '$.monto_mensual'
        )
    ) jt;

    select ifnull(sum(case when c.tipo='ingreso' then pd.monto_mensual else 0 end),0), ifnull(sum(case when c.tipo='gasto' then pd.monto_mensual else 0 end),0),
        ifnull(sum(case when c.tipo='ahorro' then pd.monto_mensual else 0 end),0)
    into v_total_ingresos, v_total_gastos, v_total_ahorro
    from presupuesto_detalle pd
    inner join subcategoria s
        on s.id_subcategoria = pd.id_subcategoria
    inner join categoria c
        on c.id_categoria = s.id_categoria
    where pd.id_presupuesto = v_id_presupuesto;

    update presupuesto
    set total_ingresos = v_total_ingresos,total_gastos = v_total_gastos, total_ahorro = v_total_ahorro, modificado_user = p_creado_por, modificado_fecha = current_timestamp()
    where id_presupuesto = v_id_presupuesto;
    commit;
    select v_id_presupuesto as id_presupuesto_creado, v_total_ingresos as total_ingresos, v_total_gastos as total_gastos, v_total_ahorro as total_ahorro;
end $$
delimiter ;
call sp_crear_presupuesto_completo(1,'presupuesto ganado trabajo-mesada','primer trimestre',2026,1,2026,3,
'[ {"id_subcategoria":3,"monto_mensual":20000}, {"id_subcategoria":4,"monto_mensual":1000}]','esdras');

delimiter $$
drop procedure if exists sp_registrar_transaccion_completa $$
create procedure sp_registrar_transaccion_completa(p_id_usuario int,p_id_presupuesto int,p_anio int,p_mes int,
p_id_subcategoria int, p_tipo varchar(300), p_descripcion varchar(1000), p_monto decimal(12,2), p_fecha date, p_metodo_pago varchar(300),
p_creado_por varchar(300))
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

    insert into transaccion(id_presupuesto_detalle,  year, mes, tipo_transaccion, descripcion, monto, fecha, metodo_pago, creado_user,
    creado_fecha )
    values( v_id_presupuesto_detalle, p_anio, p_mes, p_tipo, p_descripcion, p_monto, p_fecha, p_metodo_pago, p_creado_por, current_timestamp());
    commit;
    select last_insert_id() as id_transaccion_creada;
end $$

delimiter ;

call sp_registrar_transaccion_completa( 1, 4003, 2026, 1, 1, 'gasto', 'canje de loteria', 850.00, '2026-01-15', 'efectivo', 'esdras');

delimiter $$
drop procedure if exists sp_procesar_obligaciones_mes $$
create procedure sp_procesar_obligaciones_mes(p_id_usuario int, p_anio int, p_mes int, p_id_presupuesto int)
begin
    declare v_existe_presupuesto int default 0;
    declare v_fecha_inicio_mes date;
    declare v_fecha_fin_mes date;
    declare v_hoy date;
    set v_hoy = curdate();
    if p_mes < 1 or p_mes > 12 then
    signal sqlstate '45000'
    set message_text = 'mes invalido';
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

     set v_fecha_inicio_mes = str_to_date(concat(p_anio, '-', lpad(p_mes, 2, '0'), '-01'), '%Y-%m-%d');
    set v_fecha_fin_mes = last_day(v_fecha_inicio_mes);

    select distinct
    o.id_obligacion,
    o.nombre,
    o.descripcion,
    c.nombre as categoria,
     s.nombre as subcategoria,
    o.monto_mensual,
     o.dia_vencimiento,
    date_add(v_fecha_inicio_mes, interval o.dia_vencimiento - 1 day) as fecha_vencimiento_mes,
      case
    when o.dia_vencimiento < 1 or o.dia_vencimiento > day(v_fecha_fin_mes) then 'dia_vencimiento_invalido'
    when date_add(v_fecha_inicio_mes, interval o.dia_vencimiento - 1 day) < v_hoy then 'vencida'
     when datediff(date_add(v_fecha_inicio_mes, interval o.dia_vencimiento - 1 day), v_hoy) between 0 and 3 then 'por_vencer'
     else 'pendiente'
     end as estado_alerta,
    case
     when o.dia_vencimiento < 1 or o.dia_vencimiento > day(v_fecha_fin_mes) then null
            else datediff(date_add(v_fecha_inicio_mes, interval o.dia_vencimiento - 1 day), v_hoy)
        end as dias_restantes
    from obligacion_fija o
    inner join subcategoria s
    on s.id_subcategoria = o.id_subcategoria inner join categoria c
    on c.id_categoria = s.id_categoria
    inner join presupuesto_detalle pd
     on pd.id_subcategoria = s.id_subcategoria
    inner join presupuesto p
    on p.id_presupuesto = pd.id_presupuesto
    where p.id_presupuesto = p_id_presupuesto
    and p.id_usuario = p_id_usuario
    and o.vigente = 1
    and (o.fecha_inicio is null or o.fecha_inicio <= v_fecha_fin_mes)
    and (o.fecha_finalizacion is null or o.fecha_finalizacion >= v_fecha_inicio_mes)
    order by
        fecha_vencimiento_mes,o.nombre;

end $$

delimiter ;

call sp_procesar_obligaciones_mes(1, 2026, 10, 4003);

delimiter $$

drop procedure if exists sp_calcular_balance_mensual $$
create procedure sp_calcular_balance_mensual( p_id_usuario int, p_id_presupuesto int, p_anio int, p_mes int,out p_total_ingresos decimal(12,2),
out p_total_gastos decimal(12,2), out p_total_ahorros decimal(12,2), out p_balance_final decimal(12,2))
begin
	
    declare v_existe_presupuesto int default 0;

    if p_mes < 1 or p_mes > 12 then
    signal sqlstate '45000'
    set message_text = 'mes invalido';
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

    select
    ifnull(sum(case when lower(t.tipo_transaccion) = 'ingreso' then t.monto else 0 end), 0.00),
    ifnull(sum(case when lower(t.tipo_transaccion) = 'gasto' then t.monto else 0 end), 0.00),
    ifnull(sum(case when lower(t.tipo_transaccion) = 'ahorro' then t.monto else 0 end), 0.00)
    into p_total_ingresos, p_total_gastos, p_total_ahorros
    from transaccion t
    inner join presupuesto_detalle pd
    on pd.id_presupuesto_detalle = t.id_presupuesto_detalle
    inner join presupuesto p
    on p.id_presupuesto = pd.id_presupuesto
    where p.id_presupuesto = p_id_presupuesto
     and p.id_usuario = p_id_usuario
      and t.year = p_anio
      and t.mes = p_mes;

    set p_balance_final = p_total_ingresos - p_total_gastos - p_total_ahorros;
end $$

delimiter ;

call sp_calcular_balance_mensual(1, 4001, 2026, 10, @ingresos, @gastos, @ahorros, @balance);

select @ingresos as total_ingresos,
       @gastos as total_gastos,
       @ahorros as total_ahorros,
       @balance as balance_final;

delimiter $$

drop procedure if exists sp_calcular_monto_ejecutado_mes $$
create procedure sp_calcular_monto_ejecutado_mes( p_id_subcategoria int, p_id_presupuesto int, p_anio int, p_mes int,out p_monto_ejecutado decimal(12,2))
begin
	
    declare v_existe_detalle int default 0;

    if p_mes < 1 or p_mes > 12 then
     signal sqlstate '45000'
    set message_text = 'mes invalido';
    end if;

    select count(*)
    into v_existe_detalle
    from presupuesto_detalle
    where id_presupuesto = p_id_presupuesto
    and id_subcategoria = p_id_subcategoria;

    if v_existe_detalle = 0 then
    signal sqlstate '45000'
    set message_text = 'la subcategoria no esta asociada al presupuesto';
    end if;

    select ifnull(sum(t.monto), 0.00)
    into p_monto_ejecutado
    from transaccion t
    inner join presupuesto_detalle pd
     on pd.id_presupuesto_detalle = t.id_presupuesto_detalle
    where pd.id_presupuesto = p_id_presupuesto
    and pd.id_subcategoria = p_id_subcategoria
    and t.year = p_anio
    and t.mes = p_mes;
end $$

delimiter ;

call sp_calcular_monto_ejecutado_mes(1, 4001, 2026, 12, @monto);

select @monto as monto_ejecutado;

-- falta probar

delimiter $$

drop procedure if exists sp_calcular_porcentaje_ejecucion_mes $$
create procedure sp_calcular_porcentaje_ejecucion_mes( p_id_subcategoria int, p_id_presupuesto int, p_anio int, p_mes int,out p_porcentaje decimal(12,2))
begin
	
    declare v_existe_detalle int default 0;
    
    declare v_monto_presupuestado decimal(12,2) default 0.00;
    declare v_monto_ejecutado decimal(12,2) default 0.00;

    if p_mes < 1 or p_mes > 12 then
     signal sqlstate '45000'
      set message_text = 'mes invalido';
    end if;

    select count(*)
    into v_existe_detalle
    from presupuesto_detalle
    where id_presupuesto = p_id_presupuesto
    and id_subcategoria = p_id_subcategoria;

    if v_existe_detalle = 0 then
     signal sqlstate '45000'
    set message_text = 'la subcategoria no esta asociada al presupuesto';
    end if;

    select monto_mensual
    into v_monto_presupuestado
    from presupuesto_detalle
    where id_presupuesto = p_id_presupuesto
      and id_subcategoria = p_id_subcategoria
    limit 1;

    select ifnull(sum(t.monto), 0.00)
    into v_monto_ejecutado
    from transaccion t
    inner join presupuesto_detalle pd
    on pd.id_presupuesto_detalle = t.id_presupuesto_detalle
    where pd.id_presupuesto = p_id_presupuesto
    and pd.id_subcategoria = p_id_subcategoria
    and t.year = p_anio
    and t.mes = p_mes;

    if v_monto_presupuestado = 0 then
     set p_porcentaje = 0.00;
    else
    set p_porcentaje = (v_monto_ejecutado / v_monto_presupuestado) * 100;
    end if;
end $$

delimiter ;

-- falta probar

delimiter $$


drop procedure if exists sp_calcular_porcentaje_ejecucion_mes $$
create procedure sp_calcular_porcentaje_ejecucion_mes(
    in p_id_subcategoria int,
    in p_id_presupuesto int,
    in p_anio int,
    in p_mes int,
    out p_porcentaje decimal(12,2)
)
begin
    declare v_existe_detalle int default 0;
    declare v_monto_presupuestado decimal(12,2) default 0.00;
    declare v_monto_ejecutado decimal(12,2) default 0.00;

    if p_mes < 1 or p_mes > 12 then
        signal sqlstate '45000'
        set message_text = 'mes invalido';
    end if;

    select count(*)
    into v_existe_detalle
    from presupuesto_detalle
    where id_presupuesto = p_id_presupuesto
      and id_subcategoria = p_id_subcategoria;

    if v_existe_detalle = 0 then
        signal sqlstate '45000'
        set message_text = 'la subcategoria no esta asociada al presupuesto';
    end if;

    select monto_mensual
    into v_monto_presupuestado
    from presupuesto_detalle
    where id_presupuesto = p_id_presupuesto
      and id_subcategoria = p_id_subcategoria
    limit 1;

    select ifnull(sum(t.monto), 0.00)
    into v_monto_ejecutado
    from transaccion t
    inner join presupuesto_detalle pd
        on pd.id_presupuesto_detalle = t.id_presupuesto_detalle
    where pd.id_presupuesto = p_id_presupuesto
      and pd.id_subcategoria = p_id_subcategoria
      and t.year = p_anio
      and t.mes = p_mes;

    if v_monto_presupuestado = 0 then
        set p_porcentaje = 0.00;
    else
        set p_porcentaje = (v_monto_ejecutado / v_monto_presupuestado) * 100;
    end if;
end $$

delimiter ;

-- falta probar 

delimiter $$

drop procedure if exists sp_cerrar_presupuesto $$
create procedure sp_cerrar_presupuesto( p_id_presupuesto int, p_modificado_por varchar(300))
begin
    declare v_existe_presupuesto int default 0;
    declare v_year_fin int;
    declare v_mes_fin int;
    declare v_fecha_fin_presupuesto date;
    declare v_total_ingresos decimal(12,2) default 0.00;
    declare v_total_gastos decimal(12,2) default 0.00;
    declare v_total_ahorros decimal(12,2) default 0.00;
    declare v_balance_final decimal(12,2) default 0.00;

    declare exit handler for sqlexception
    begin
        rollback;
        resignal;
    end;

    select count(*) into v_existe_presupuesto from presupuesto
    where id_presupuesto = p_id_presupuesto;

    if v_existe_presupuesto = 0 then
    signal sqlstate '45000'
    set message_text = 'el presupuesto no existe';
    end if;

    select year_fin, mes_fin into v_year_fin, v_mes_fin from presupuesto
    where id_presupuesto = p_id_presupuesto;

    set v_fecha_fin_presupuesto = last_day(
    str_to_date(concat(v_year_fin, '-', lpad(v_mes_fin, 2, '0'), '-01'), '%Y-%m-%d'));

    if curdate() <= v_fecha_fin_presupuesto then
    signal sqlstate '45000'
    set message_text = 'no se puede cerrar el presupuesto porque su fecha de fin aun no ha pasado';
    end if;

    start transaction;

    update presupuesto set estado = 0, modificado_user = p_modificado_por, modificado_fecha = current_timestamp()
    where id_presupuesto = p_id_presupuesto;

    select ifnull(sum(case when lower(t.tipo_transaccion) = 'ingreso' then t.monto else 0 end), 0.00), 
    ifnull(sum(case when lower(t.tipo_transaccion) = 'gasto' then t.monto else 0 end), 0.00),
    ifnull(sum(case when lower(t.tipo_transaccion) = 'ahorro' then t.monto else 0 end), 0.00)
    into v_total_ingresos,v_total_gastos,v_total_ahorros from transaccion t
    inner join presupuesto_detalle pd
    on pd.id_presupuesto_detalle = t.id_presupuesto_detalle
    where pd.id_presupuesto = p_id_presupuesto;

    set v_balance_final = v_total_ingresos - v_total_gastos - v_total_ahorros;

    commit;

    select p_id_presupuesto as id_presupuesto, v_fecha_fin_presupuesto as fecha_fin_presupuesto, v_total_ingresos as total_ingresos_ejecutados,
    v_total_gastos as total_gastos_ejecutados, v_total_ahorros as total_ahorros_ejecutados, v_balance_final as balance_final, 'presupuesto cerrado correctamente' as mensaje;
end $$

delimiter ;

-- falta probar 
delimiter $$

drop procedure if exists sp_obtener_resumen_categoria_mes $$
create procedure sp_obtener_resumen_categoria_mes( p_id_categoria int, p_id_presupuesto int, p_anio int, p_mes int, out p_monto_presupuestado decimal(12,2),
out p_monto_ejecutado decimal(12,2), out p_porcentaje decimal(12,2))
begin
    declare v_existe_categoria int default 0;
    declare v_existe_presupuesto int default 0;

    if p_mes < 1 or p_mes > 12 then
     signal sqlstate '45000'
    set message_text = 'mes invalido';
    end if;

    select count(*) into v_existe_categoria from categoria
    where id_categoria = p_id_categoria;
    if v_existe_categoria = 0 then
    signal sqlstate '45000'
    set message_text = 'la categoria no existe';
    end if;
    select count(*) into v_existe_presupuesto from presupuesto
    where id_presupuesto = p_id_presupuesto;

    
    
    if v_existe_presupuesto = 0 then
    signal sqlstate '45000'
     set message_text = 'el presupuesto no existe';
    end if;

    select ifnull(sum(pd.monto_mensual), 0.00)
    into p_monto_presupuestado
    from presupuesto_detalle pd
    inner join subcategoria s
    on s.id_subcategoria = pd.id_subcategoria
    where pd.id_presupuesto = p_id_presupuesto
      and s.id_categoria = p_id_categoria;

    select ifnull(sum(t.monto), 0.00)
    into p_monto_ejecutado
    from transaccion t
    inner join presupuesto_detalle pd
    on pd.id_presupuesto_detalle = t.id_presupuesto_detalle
    inner join subcategoria s
    on s.id_subcategoria = pd.id_subcategoria
    where pd.id_presupuesto = p_id_presupuesto
    and s.id_categoria = p_id_categoria
    and t.year = p_anio
    and t.mes = p_mes;

    if p_monto_presupuestado = 0 then
    set p_porcentaje = 0.00;
    else
    set p_porcentaje = (p_monto_ejecutado / p_monto_presupuestado) * 100;
    end if;
end $$

delimiter ;



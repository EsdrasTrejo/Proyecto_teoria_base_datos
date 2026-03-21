USE presupuesto_personal_proyecto;
-- no probado 
delimiter $$
drop function if exists fn_calcular_monto_ejecutado $$
create function fn_calcular_monto_ejecutado( p_id_subcategoria int, p_anio int, p_mes int)
returns decimal(12,2)
deterministic
reads sql data
begin
    declare v_monto_ejecutado decimal(12,2);
    select ifnull(sum(monto), 0.00)
    into v_monto_ejecutado
    from transaccion
    where id_subcategoria = p_id_subcategoria
    and anio = p_anio
    and mes = p_mes;
    return v_monto_ejecutado;
end $$
delimiter ;

delimiter $$
drop function if exists fn_calcular_porcentaje_ejecutado $$
create function fn_calcular_porcentaje_ejecutado(p_id_subcategoria int, p_id_presupuesto int, p_anio int,p_mes int)
returns decimal(10,2)
deterministic
reads sql data
begin
    declare v_monto_ejecutado decimal(12,2);
    declare v_monto_presupuestado decimal(12,2);
    declare v_porcentaje decimal(10,2);

    select ifnull(sum(monto),0)
    into v_monto_ejecutado
    from transaccion
    where id_subcategoria = p_id_subcategoria
      and anio = p_anio
      and mes = p_mes;
    
    select monto_mensual
    into v_monto_presupuestado
    from presupuesto_detalle
    where id_presupuesto = p_id_presupuesto
      and id_subcategoria = p_id_subcategoria;
    
    if v_monto_presupuestado is null or v_monto_presupuestado = 0 then
        return 0;
    end if;
    set v_porcentaje = (v_monto_ejecutado / v_monto_presupuestado) * 100;
    return v_porcentaje;
end $$
delimiter ;

delimiter $$
drop function if exists fn_obtener_balance_subcategoria $$
create function fn_obtener_balance_subcategoria( p_id_presupuesto int,p_id_subcategoria int,p_anio int, p_mes int)
returns decimal(12,2)
deterministic
reads sql data
begin
    declare v_monto_presupuestado decimal(12,2);
    declare v_monto_ejecutado decimal(12,2);
    declare v_balance decimal(12,2);
   
    select ifnull(monto_mensual,0)
    into v_monto_presupuestado
    from presupuesto_detalle
    where id_presupuesto = p_id_presupuesto
    and id_subcategoria = p_id_subcategoria;
    select ifnull(sum(monto),0)
    into v_monto_ejecutado
    from transaccion
    where id_subcategoria = p_id_subcategoria
    and anio = p_anio
    and mes = p_mes;
    set v_balance = v_monto_presupuestado - v_monto_ejecutado;
    return v_balance;
end $$
delimiter ;

delimiter $$
drop function if exists fn_obtener_total_categoria_mes $$
create function fn_obtener_total_categoria_mes(p_id_categoria int, p_id_presupuesto int, p_anio int, p_mes int)
returns decimal(12,2)
deterministic
reads sql data
begin
    declare v_total decimal(12,2);
    select ifnull(sum(pd.monto_mensual),0)
    into v_total
    from presupuesto_detalle pd
    inner join subcategoria s on pd.id_subcategoria = s.id_subcategoria
    where s.id_categoria = p_id_categoria
    and pd.id_presupuesto = p_id_presupuesto;
    return v_total;
end $$
delimiter ;

delimiter $$

drop function if exists fn_obtener_total_ejecutado_categoria_mes $$
create function fn_obtener_total_ejecutado_categoria_mes( p_id_categoria int, p_anio int, p_mes int)
returns decimal(12,2)
deterministic
reads sql data
begin
    declare v_total_ejecutado decimal(12,2);
    select ifnull(sum(t.monto), 0.00)
    into v_total_ejecutado
    from transaccion t
    inner join subcategoria s
    on t.id_subcategoria = s.id_subcategoria
    where s.id_categoria = p_id_categoria and t.anio = p_anio
      and t.mes = p_mes;
    return v_total_ejecutado;
end $$
delimiter ;

delimiter $$
drop function if exists fn_dias_hasta_vencimiento $$
create function fn_dias_hasta_vencimiento( p_id_obligacion int)
returns int
deterministic
reads sql data
begin
    declare v_fecha_vencimiento date;
    declare v_dias_restantes int;
    select fecha_vencimiento
    into v_fecha_vencimiento
    from obligacion_fija
    where id_obligacion = p_id_obligacion;
    set v_dias_restantes = datediff(v_fecha_vencimiento, curdate());
    return v_dias_restantes;
end $$
delimiter ;

delimiter $$
drop function if exists fn_validar_vigencia_presupuesto $$
create function fn_validar_vigencia_presupuesto(p_fecha date,p_id_presupuesto int)
returns tinyint
deterministic
reads sql data
begin
    declare v_fecha_inicio date;
    declare v_fecha_fin date;
    select 
    str_to_date(concat(year_inicio,'-',mes_inicio,'-01'),'%Y-%m-%d'),
    last_day(str_to_date(concat(year_fin,'-',mes_fin,'-01'),'%Y-%m-%d'))
    into v_fecha_inicio, v_fecha_fin
    from presupuesto
    where id_presupuesto = p_id_presupuesto;
    if p_fecha between v_fecha_inicio and v_fecha_fin then
    return 1;
    else
    return 0;
    end if;
end $$
delimiter ;

delimiter $$
drop function if exists fn_obtener_categoria_por_subcategoria $$
create function fn_obtener_categoria_por_subcategoria( p_id_subcategoria int)
returns int
deterministic
reads sql data
begin
    declare v_id_categoria int;
    select id_categoria
    into v_id_categoria
    from subcategoria
    where id_subcategoria = p_id_subcategoria;
    return v_id_categoria;
end $$
delimiter ;

delimiter $$
drop function if exists fn_calcular_proyeccion_gasto_mensual $$
create function fn_calcular_proyeccion_gasto_mensual(p_id_subcategoria int,p_anio int,p_mes int)
returns decimal(12,2)
deterministic
reads sql data
begin
    declare v_monto_ejecutado decimal(12,2);
    declare v_dias_transcurridos int;
    declare v_dias_totales int;
    declare v_fecha_actual date;
    declare v_primer_dia_mes date;
    declare v_proyeccion decimal(12,2);
    set v_fecha_actual = curdate();
    set v_primer_dia_mes = str_to_date(concat(p_anio, '-', lpad(p_mes, 2, '0'), '-01'), '%Y-%m-%d');
    set v_dias_totales = day(last_day(v_primer_dia_mes));
    select ifnull(sum(monto), 0.00)
    into v_monto_ejecutado
    from transaccion
    where id_subcategoria = p_id_subcategoria
    and anio = p_anio
    and mes = p_mes;
    if year(v_fecha_actual) = p_anio and month(v_fecha_actual) = p_mes then
    set v_dias_transcurridos = day(v_fecha_actual);
    else
    set v_dias_transcurridos = v_dias_totales;
    end if;
   if v_dias_transcurridos <= 0 then
    return 0.00;
    end if;
    set v_proyeccion = (v_monto_ejecutado / v_dias_transcurridos) * v_dias_totales;
    return round(v_proyeccion, 2);
end $$
delimiter ;

delimiter $$

drop function if exists fn_obtener_promedio_gasto_subcategoria $$
create function fn_obtener_promedio_gasto_subcategoria(p_id_usuario int,p_id_subcategoria int,p_cantidad_meses int)
returns decimal(12,2)
deterministic
reads sql data
begin
    declare v_fecha_inicio date;
    declare v_fecha_fin date;
    declare v_total_gastado decimal(12,2);
    declare v_promedio decimal(12,2);
    if p_cantidad_meses is null or p_cantidad_meses <= 0 then
    return 0.00;
    end if;
    set v_fecha_fin = last_day(curdate());
    set v_fecha_inicio = str_to_date(concat(year(date_sub(curdate(), interval (p_cantidad_meses - 1) month)),'-',
    lpad(month(date_sub(curdate(), interval (p_cantidad_meses - 1) month)), 2, '0'),'-01' ),'%Y-%m-%d' );
    select ifnull(sum(t.monto), 0.00)
    into v_total_gastado
    from transaccion t
    where t.id_usuario = p_id_usuario
      and t.id_subcategoria = p_id_subcategoria
      and str_to_date(concat(t.anio, '-', lpad(t.mes, 2, '0'), '-01'), '%Y-%m-%d')
      between v_fecha_inicio and v_fecha_fin;
    set v_promedio = v_total_gastado / p_cantidad_meses;
    return round(v_promedio, 2);
end $$
delimiter ;
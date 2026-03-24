USE presupuesto_personal_proyecto;
-- reporte1
delimiter $$
drop procedure if exists sp_reporte_resumen_mensual $$
create procedure sp_reporte_resumen_mensual( p_id_usuario int, p_anio_desde int, p_mes_desde int, p_anio_hasta int,
p_mes_hasta int)
begin
if p_id_usuario is null or p_id_usuario <= 0 then
signal sqlstate '45000'
set message_text = 'el id del usuario es invalido';
end if;
if not exists ( select 1 from usuario
where id_usuario = p_id_usuario) then
signal sqlstate '45000'
set message_text = 'el usuario no existe';
end if;

if p_anio_desde is null or p_mes_desde is null
or p_anio_hasta is null or p_mes_hasta is null then
signal sqlstate '45000'
set message_text = 'el rango de fechas es obligatorio';
end if;

if p_mes_desde < 1 or p_mes_desde > 12 then
signal sqlstate '45000'
set message_text = 'el mes desde es invalido';
end if;

if p_mes_hasta < 1 or p_mes_hasta > 12 then
signal sqlstate '45000'
set message_text = 'el mes hasta es invalido';
end if;
 if (p_anio_desde * 100 + p_mes_desde) > (p_anio_hasta * 100 + p_mes_hasta) then
 signal sqlstate '45000'
 set message_text = 'el rango de fechas es invalido';
 end if;
 select t.anio,t.mes,sum(case when lower(t.tipo) = 'ingreso' then t.monto else 0 end) as total_ingresos,
sum(case when lower(t.tipo) = 'gasto' then t.monto else 0 end) as total_gastos, sum(case when lower(t.tipo) = 'ahorro' then t.monto else 0 end) as total_ahorros,
sum(case when lower(t.tipo) = 'ingreso' then t.monto else 0 end)
- sum(case when lower(t.tipo) = 'gasto' then t.monto else 0 end)
- sum(case when lower(t.tipo) = 'ahorro' then t.monto else 0 end) as balance_final
from transaccion t
where t.id_usuario = p_id_usuario
 and (t.anio * 100 + t.mes) between (p_anio_desde * 100 + p_mes_desde)  and (p_anio_hasta * 100 + p_mes_hasta)
group by t.anio, t.mes
 order by t.anio, t.mes;

end $$

delimiter ;

call sp_reporte_resumen_mensual(1,2025,02,2026,02);

delimiter $$

drop procedure if exists sp_reporte_distribucion_gastos_categoria $$

create procedure sp_reporte_distribucion_gastos_categoria(p_id_usuario int,p_anio int,p_mes int)
begin
	
    declare v_total_gastos decimal(14,2) default 0.00;
    if p_id_usuario is null or p_id_usuario <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id del usuario es invalido';
    end if;

    if not exists ( select 1 from usuario where id_usuario = p_id_usuario) then
    signal sqlstate '45000'
    set message_text = 'el usuario no existe';
    end if;

    if p_anio is null or p_anio <= 0 then
    signal sqlstate '45000'
    set message_text = 'el año es invalido';
    end if;

    if p_mes is null or p_mes < 1 or p_mes > 12 then
    signal sqlstate '45000'
     set message_text = 'el mes es invalido';
    end if;

    select ifnull(sum(t.monto), 0)
    into v_total_gastos
    from transaccion t
    where t.id_usuario = p_id_usuario and t.anio = p_anio
    and t.mes = p_mes
    and lower(t.tipo) = 'gasto';

    select
    c.id_categoria,
    c.nombre as nombre_categoria,
    sum(t.monto) as total_gastado,
    case
	    when v_total_gastos = 0 then 0
        else round((sum(t.monto) / v_total_gastos) * 100, 2)
        end as porcentaje_total_gastos,
        count(t.id_transaccion) as cantidad_transacciones
       
    from transaccion t
    inner join subcategoria s
    on t.id_subcategoria = s.id_subcategoria
    inner join categoria c
    on s.id_categoria = c.id_categoria
    where t.id_usuario = p_id_usuario
    and t.anio = p_anio
    and t.mes = p_mes
    and lower(t.tipo) = 'gasto'
    group by c.id_categoria, c.nombre
    order by total_gastado desc;

end $$

delimiter ;

call sp_reporte_distribucion_gastos_categoria(1, 2026, 2);

delimiter $$
drop procedure if exists sp_reporte_cumplimiento_presupuesto_categoria_subcategoria $$
create procedure sp_reporte_cumplimiento_presupuesto_categoria_subcategoria( p_id_presupuesto int, p_anio int, p_mes int)
begin
    if p_id_presupuesto is null or p_id_presupuesto <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id del presupuesto es invalido';
    end if;

    if not exists ( select 1from presupuesto
    where id_presupuesto = p_id_presupuesto) then
    signal sqlstate '45000'
        set message_text = 'el presupuesto no existe';
    end if;

    if p_anio is null or p_anio <= 0 then
    signal sqlstate '45000'
    set message_text = 'el año es invalido';
    end if;

    if p_mes is null or p_mes < 1 or p_mes > 12 then
    signal sqlstate '45000'
    set message_text = 'el mes es invalido';
    end if;

    select
        c.id_categoria,
        c.nombre as nombre_categoria,
        s.id_subcategoria,
        s.nombre as nombre_subcategoria,
        pd.monto_mensual as monto_presupuestado,

        ifnull(( select sum(t.monto)from transaccion t
        where t.id_presupuesto = pd.id_presupuesto
         and t.id_subcategoria = pd.id_subcategoria
         and t.anio = p_anio
         and t.mes = p_mes
         and lower(t.tipo) = 'gasto'
        ), 0) as monto_ejecutado,

        pd.monto_mensual - ifnull((
            select sum(t.monto)
            from transaccion t
            where t.id_presupuesto = pd.id_presupuesto
             and t.id_subcategoria = pd.id_subcategoria
             and t.anio = p_anio
             and t.mes = p_mes
             and lower(t.tipo) = 'gasto'
        ), 0) as diferencia,

        case
	        
            when pd.monto_mensual = 0 then 0
            else round((
            ifnull(( select sum(t.monto) from transaccion t
            where t.id_presupuesto = pd.id_presupuesto
            and t.id_subcategoria = pd.id_subcategoria
             and t.anio = p_anio
             and t.mes = p_mes
             and lower(t.tipo) = 'gasto'
              ), 0) / pd.monto_mensual) * 100, 2)
        end as porcentaje_ejecucion,
        case
            when pd.monto_mensual = 0 then 'verde'
            when (
            ifnull((select sum(t.monto) from transaccion t
            where t.id_presupuesto = pd.id_presupuesto
            and t.id_subcategoria = pd.id_subcategoria
            and t.anio = p_anio
            and t.mes = p_mes
            and lower(t.tipo) = 'gasto' ), 0) / pd.monto_mensual
            ) * 100 < 80 then 'verde'
            when (ifnull((select sum(t.monto) from transaccion t
            where t.id_presupuesto = pd.id_presupuesto
            and t.id_subcategoria = pd.id_subcategoria
             and t.anio = p_anio
            and t.mes = p_mes
             and lower(t.tipo) = 'gasto'), 0) / pd.monto_mensual) * 100 <= 100 then 'amarillo'
            else 'rojo'
        end as indicador_visual

    from presupuesto_detalle pd
    inner join subcategoria s
    on pd.id_subcategoria = s.id_subcategoria
    inner join categoria c
    on s.id_categoria = c.id_categoria
    where pd.id_presupuesto = p_id_presupuesto
    order by c.nombre, s.nombre;

end $$

delimiter ;

call sp_reporte_cumplimiento_presupuesto_categoria_subcategoria(1, 2026, 2);

delimiter $$

drop procedure if exists sp_reporte_tendencia_gastos_categoria $$

create procedure sp_reporte_tendencia_gastos_categoria(p_id_usuario int, p_anio_desde int, p_mes_desde int, p_anio_hasta int,
p_mes_hasta int)
begin
    if p_id_usuario is null or p_id_usuario <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id del usuario es invalido';
    end if;

    if not exists ( select 1 from usuario
    where id_usuario = p_id_usuario) then
    signal sqlstate '45000'
    set message_text = 'el usuario no existe';
    end if;
    if p_mes_desde < 1 or p_mes_desde > 12 then
    signal sqlstate '45000'
     set message_text = 'el mes desde es invalido';
    end if;

    if p_mes_hasta < 1 or p_mes_hasta > 12 then
    signal sqlstate '45000'
    set message_text = 'el mes hasta es invalido';
    end if;

    if (p_anio_desde * 100 + p_mes_desde) > (p_anio_hasta * 100 + p_mes_hasta) then
    signal sqlstate '45000'
    set message_text = 'el rango de fechas es invalido';
    end if;
    select c.id_categoria, c.nombre as nombre_categoria,  t.anio,
    t.mes,
    sum(t.monto) as total_gastado
    from transaccion t
    inner join subcategoria s
    on t.id_subcategoria = s.id_subcategoria
    inner join categoria c
    on s.id_categoria = c.id_categoria
    where t.id_usuario = p_id_usuario
     and lower(t.tipo) = 'gasto'
      and (t.anio * 100 + t.mes) between (p_anio_desde * 100 + p_mes_desde) and (p_anio_hasta * 100 + p_mes_hasta)
    group by c.id_categoria, c.nombre, t.anio, t.mes
    order by t.anio, t.mes, c.nombre;

end $$

delimiter ;

call  sp_reporte_tendencia_gastos_categoria(1,2025,02,2026,02);

delimiter $$

drop procedure if exists sp_reporte_estado_obligaciones_fijas $$

create procedure sp_reporte_estado_obligaciones_fijas(p_id_usuario int, p_anio int,  p_mes int)
begin
    if p_id_usuario is null or p_id_usuario <= 0 then
    signal sqlstate '45000'
    set message_text = 'el id del usuario es invalido';
    end if;

    if not exists ( select 1 from usuario
    where id_usuario = p_id_usuario) then
    signal sqlstate '45000'
    set message_text = 'el usuario no existe';
    end if;

    if p_anio is null or p_anio <= 0 then
    signal sqlstate '45000'
    set message_text = 'el año es invalido';
    end if;

    if p_mes is null or p_mes < 1 or p_mes > 12 then
    signal sqlstate '45000'
    set message_text = 'el mes es invalido';
    end if;

    select o.id_obligacion,o.nombre as nombre_obligacion,c.nombre as nombre_categoria,o.monto_mensual as monto_mensual,
    str_to_date(concat(p_anio, '-', lpad(p_mes, 2, '0'), '-', lpad(o.dia_vencimiento, 2, '0')), '%Y-%m-%d') as fecha_vencimiento,
    ifnull((select sum(t.monto) from transaccion t  where t.id_usuario = p_id_usuario
    and t.id_subcategoria = o.id_subcategoria and t.anio = p_anio
    and t.mes = p_mes
    and lower(t.tipo) = 'gasto'), 0) as total_pagado_mes,

    (select max(str_to_date(concat(t.anio, '-', lpad(t.mes, 2, '0'), '-01'), '%Y-%m-%d')) from transaccion t
     where t.id_usuario = p_id_usuario and t.id_subcategoria = o.id_subcategoria and t.anio = p_anio and t.mes = p_mes
     and lower(t.tipo) = 'gasto') as fecha_ultimo_pago,
      case
      when ifnull((
      select sum(t.monto)
      from transaccion t
      where t.id_usuario = p_id_usuario
      and t.id_subcategoria = o.id_subcategoria
      and t.anio = p_anio
      and t.mes = p_mes
      and lower(t.tipo) = 'gasto'), 0) >= o.monto_mensual then 'pagada'
      when datediff( str_to_date(concat(p_anio, '-', lpad(p_mes, 2, '0'), '-', lpad(o.dia_vencimiento, 2, '0')), '%Y-%m-%d'), curdate()) < 0 then 'vencida'
      when datediff(
       str_to_date(concat(p_anio, '-', lpad(p_mes, 2, '0'), '-', lpad(o.dia_vencimiento, 2, '0')), '%Y-%m-%d'), curdate()) < 3 then 'por vencer'
       else 'pendiente'
        end as estado_pago,
        case
        when ifnull((
        select sum(t.monto)
        from transaccion t
        where t.id_usuario = p_id_usuario
        and t.id_subcategoria = o.id_subcategoria
         and t.anio = p_anio
         and t.mes = p_mes
         and lower(t.tipo) = 'gasto'), 0) >= o.monto_mensual then 0
          else datediff(
          str_to_date(concat(p_anio, '-', lpad(p_mes, 2, '0'), '-', lpad(o.dia_vencimiento, 2, '0')), '%Y-%m-%d'), curdate() )
        end as dias_referencia

    from obligacion_fija o
    inner join subcategoria s
    on o.id_subcategoria = s.id_subcategoria
    inner join categoria c
    on s.id_categoria = c.id_categoria
    where o.vigente = 1
    order by fecha_vencimiento, o.nombre;
end $$
delimiter ;
call sp_reporte_estado_obligaciones_fijas(1,2025,02);
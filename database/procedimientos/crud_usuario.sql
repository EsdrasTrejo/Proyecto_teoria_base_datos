DELIMITER $$

DROP PROCEDURE IF EXISTS sp_insertar_usuario $$

CREATE PROCEDURE sp_insertar_usuario(
    IN p_nombre VARCHAR(300),
    IN p_apellido VARCHAR(300),
    IN p_correo VARCHAR(300),
    IN p_salario_mensual DECIMAL(12,2),
    IN p_creado_por VARCHAR(300)
)
BEGIN
    INSERT INTO usuario(
        nombre,
        apellido,
        correo,
        fecha_registro,
        salario_mensual,
        estado,
        creador_user
    )
    VALUES(
        p_nombre,
        p_apellido,
        p_correo,
        CURDATE(),
        p_salario_mensual,
        1,
        p_creado_por
    );

   
END $$

DELIMITER ;


DELIMITER $$
call sp_insertar_usuario('esdras','carranza','esdrastrejo@gmail.com',1000,'admin1');

DROP PROCEDURE IF EXISTS sp_actualizar_usuario $$

CREATE PROCEDURE sp_actualizar_usuario(
  IN p_id_usuario INT,
  IN p_nombre VARCHAR(300),
  IN p_apellido VARCHAR(300),
  IN p_salario_mensual DECIMAL(12,2),
  IN p_modificado_user VARCHAR(300)
)
BEGIN
  UPDATE usuario
  SET nombre = p_nombre,
      apellido = p_apellido,
      salario_mensual = p_salario_mensual,
      modificado_por_user = p_modificado_user,
      modificado_en = CURRENT_TIMESTAMP
  WHERE id_usuario = p_id_usuario;

END $$

DELIMITER ;



delimiter $$
drop procedure if exists sp_eliminar_usuario $$

create procedure sp_eliminar_usuario(in p_id_usuario int )
begin 
	update usuario set estado = 0,modificado_en = current_timestamp where id_usuario = p_id_usuario;
end $$

delimiter ;

delimiter $$ 
drop procedure if exists sp_consultar_usuario $$ 
create procedure sp_consultar_usuario(in p_id_usuario int)
begin 
	select id_usuario, nombre, apellido, correo, fecha_registro, salario_mensual, estado, 
	creador_user, modificado_por_user, creado_en, modificado_en from usuario where id_usuario=p_id_usuario;
end $$ 
delimiter ;


delimiter $$
drop procedure if exists sp_listar_usuario $$
create procedure sp_listar_usuario() 
begin 
	select id_usuario,estado from usuario;
end $$ 
delimiter ;
















  
  
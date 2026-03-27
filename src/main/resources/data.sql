-- Carga inicial de candidatos
-- Solo inserta si no existe el registro (evita duplicados en cada reinicio)

INSERT INTO candidatos (nombre, apellido, correo, telefono, fecha_creacion)
SELECT 'Ana', 'García', 'ana.garcia@email.com', '5512345601', NOW()
WHERE NOT EXISTS (SELECT 1 FROM candidatos WHERE correo = 'ana.garcia@email.com');

INSERT INTO candidatos (nombre, apellido, correo, telefono, fecha_creacion)
SELECT 'Carlos', 'Martínez', 'carlos.martinez@email.com', '5512345602', NOW()
WHERE NOT EXISTS (SELECT 1 FROM candidatos WHERE correo = 'carlos.martinez@email.com');

INSERT INTO candidatos (nombre, apellido, correo, telefono, fecha_creacion)
SELECT 'Laura', 'López', 'laura.lopez@email.com', '5512345603', NOW()
WHERE NOT EXISTS (SELECT 1 FROM candidatos WHERE correo = 'laura.lopez@email.com');

INSERT INTO candidatos (nombre, apellido, correo, telefono, fecha_creacion)
SELECT 'Miguel', 'Hernández', 'miguel.hernandez@email.com', '5512345604', NOW()
WHERE NOT EXISTS (SELECT 1 FROM candidatos WHERE correo = 'miguel.hernandez@email.com');

INSERT INTO candidatos (nombre, apellido, correo, telefono, fecha_creacion)
SELECT 'Sofía', 'Ramírez', 'sofia.ramirez@email.com', '5512345605', NOW()
WHERE NOT EXISTS (SELECT 1 FROM candidatos WHERE correo = 'sofia.ramirez@email.com');

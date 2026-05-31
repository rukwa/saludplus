START TRANSACTION;

-- Intentamos la operación
ALTER TABLE usuario ADD status VARCHAR(20);

-- Si llegamos aquí sin error, confirmamos
COMMIT;

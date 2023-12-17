CREATE PROCEDURE `InsertErrorStatus`(
    IN id_config INT,
    IN `status` VARCHAR(255),
		IN note VARCHAR(255),
		IN date VARCHAR(255)
)
BEGIN
    DECLARE formatted_date VARCHAR(255);
		
		SELECT created_at INTO formatted_date
    FROM data_files
    WHERE df_config_id = id_config
    ORDER BY created_at DESC
    LIMIT 1;
		
		SET formatted_date = DATE_FORMAT(date, '%d_%m_%Y');
		SET @df_name = CONCAT('XSKT_', formatted_date);
		
    INSERT INTO data_files(df_config_id, name, `status`, note)
    VALUES (id_config, @df_name, `status`, note);
END
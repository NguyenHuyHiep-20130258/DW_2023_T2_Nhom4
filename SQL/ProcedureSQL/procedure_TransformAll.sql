CREATE DEFINER=`root`@`localhost` PROCEDURE `CopyDataFromStagingToWarehouse`()
BEGIN
		TRUNCATE TABLE warehouse.lottery_result;
    -- Sao chép dữ liệu từ bảng staging sang bảng warehouse
    INSERT INTO warehouse.lottery_result (region, province, date, prize, winning_number)
    SELECT region, province, date, prize, winning_number
    FROM staging.lottery_result_staging;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `AddRegionDim`()
BEGIN
    -- Thêm các giá trị mới vào bảng region_dim nếu chưa tồn tại
    INSERT IGNORE INTO warehouse.region_dim (name, dt_changed, dt_expired)
    SELECT DISTINCT
        lr.region,
		 NOW(),  -- Thêm ngày hiện tại vào trường created_at
         '9999-12-31 23:59:59'   -- Thêm giá trị 99999 vào trường updated_at
    FROM
        warehouse.lottery_result lr
    WHERE
        lr.region NOT IN (SELECT name FROM warehouse.region_dim);
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `AddProvinceDim`()
BEGIN
 INSERT IGNORE INTO warehouse.province_dim (name, dt_changed, dt_expired)
    SELECT DISTINCT
        lr.province, 
		 NOW(),  -- Thêm ngày hiện tại vào trường created_at
         '9999-12-31 23:59:59'   -- Thêm giá trị 99999 vào trường updated_at
    FROM
        warehouse.lottery_result lr
				
    WHERE
        lr.province NOT IN (SELECT name FROM warehouse.province_dim);
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `AddPrizeDim`()
BEGIN
 INSERT IGNORE INTO warehouse.prize_dim (name, dt_changed, dt_expired)
    SELECT DISTINCT
        lr.prize,
		NOW(),  -- Thêm ngày hiện tại vào trường created_at
        '9999-12-31 23:59:59'   -- Thêm giá trị 99999 vào trường updated_at
    FROM
        warehouse.lottery_result lr
				
    WHERE
        lr.prize NOT IN (SELECT name FROM warehouse.prize_dim);
END

CREATE DEFINER = CURRENT_USER PROCEDURE `TransformAll`()
BEGIN
	CALL PROCEDURE CopyDataFromStagingToWarehouse();
	CALL PROCEDURE AddRegionDim();
	CALL PROCEDURE AddProvinceDim();
	CALL PROCEDURE AddPrizeDim();
	CALL PROCEDURE Transform();
END;
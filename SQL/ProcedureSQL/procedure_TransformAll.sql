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

CREATE PROCEDURE Transform()
BEGIN
-- (Transform) 9.6. Xóa bảng temp_fact nếu nó tồn tại trong db
 DROP TEMPORARY TABLE IF EXISTS temp_fact;
-- (Transform)  9.7. Tạo bảng tạm temp_fact
CREATE TEMPORARY TABLE temp_fact AS
-- (Transform)  9.8. Bảng lottery_result left join trường region, province, prize, date với bảng region_dim, province_dim, prize_dim, date_dim tương ứng của db warehouse, lấy ra các key và insert nó vào bảng tạm temp_fact
SELECT
    rd.region_key,
    pd.province_key,
    dd.date_key,
    prd.prize_key,
    lr.winning_number,
    COALESCE(dd.date_key, CURRENT_DATE) AS created_at,
    99999 AS updated_at
FROM
    warehouse.lottery_result lr
        LEFT JOIN
    warehouse.region_dim rd ON lr.region = rd.name
        LEFT JOIN
    warehouse.province_dim pd ON lr.province = pd.name
        LEFT JOIN
    warehouse.date_dim dd ON lr.date = dd.full_date
        LEFT JOIN
    warehouse.prize_dim prd ON lr.prize = prd.name;
END

CREATE DEFINER = CURRENT_USER PROCEDURE `TransformAll`()
BEGIN
	CALL PROCEDURE CopyDataFromStagingToWarehouse();
	CALL PROCEDURE AddRegionDim();
	CALL PROCEDURE AddProvinceDim();
	CALL PROCEDURE AddPrizeDim();
	CALL PROCEDURE Transform();
END;
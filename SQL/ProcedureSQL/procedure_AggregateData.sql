
CREATE PROCEDURE AggregateData()
BEGIN
    --(Aggregate) 12.2. Truncate bảng lottery_result_aggregate trong db data_aggregate
		TRUNCATE TABLE data_aggregate.lottery_result_aggregate;
    --(Aggregate) 12.3. Tạo bảng tạm temp_lottery_result_fact
    CREATE TEMPORARY TABLE temp_lottery_result_fact AS
    SELECT
        lr.id,
        rd.name AS region,
        pd.name AS province,
        dd.full_date AS date,
        prize.name AS prize,
        lr.winning_number
    --(Aggregate) 12.4. Bảng lottery_result_fact left join trường region_key , province_key, prize_key, date_key với bảng region_dim, province_dim, prize_dim, date_dim để lấy các giá trị tương ứng của db warehouse, giới hạn dữ liệu ngày (date) < 30, insert các giá trị vào bảng tạm temp_lottery_result_fact
    FROM
        warehouse.lottery_result_fact lr
    JOIN warehouse.region_dim rd ON lr.region_key = rd.region_key
    JOIN warehouse.province_dim pd ON lr.province_key = pd.province_key
    JOIN warehouse.prize_dim prize ON lr.prize_key = prize.prize_key
		JOIN warehouse.date_dim dd ON lr.date_key = dd.date_key
		WHERE DATEDIFF(CURDATE(), dd.full_date) <= 30;
    --(Aggregate) 12.5. Insert tất cả dữ liệu từ bảng tạm temp_lottery_result_fact sang bảng lottery_result_aggregate
		INSERT INTO data_aggregate.lottery_result_aggregate (id, region, province, date, prize, winning_number)
		SELECT id, region, province, date, prize, winning_number FROM temp_lottery_result_fact;

    --(Aggregate) 12.6. Xóa bảng tạm temp_lottery_result_fact
    DROP TEMPORARY TABLE IF EXISTS temp_lottery_result;
END 

CREATE PROCEDURE AggregateData()
BEGIN
		TRUNCATE TABLE data_aggregate.lottery_result_aggregate;
    -- Tạo bảng tạm thời để lưu các giá trị biến đổi
    CREATE TEMPORARY TABLE temp_lottery_result_fact AS
    SELECT
        lr.id,
        rd.name AS region,
        pd.name AS province,
        dd.full_date AS date,
        prize.name AS prize,
        lr.winning_number
    FROM
        warehouse.lottery_result_fact lr
    JOIN warehouse.region_dim rd ON lr.region_key = rd.region_key
    JOIN warehouse.province_dim pd ON lr.province_key = pd.province_key
    JOIN warehouse.prize_dim prize ON lr.prize_key = prize.prize_key
		JOIN warehouse.date_dim dd ON lr.date_key = dd.date_key
		WHERE DATEDIFF(CURDATE(), dd.full_date) <= 30;
    -- Chèn dữ liệu mới từ bảng tạm thời vào bảng lottery_result_mart trong database data_mart
		INSERT INTO data_aggregate.lottery_result_aggregate (id, region, province, date, prize, winning_number)
		SELECT id, region, province, date, prize, winning_number FROM temp_lottery_result_fact;

    -- Xóa bảng tạm thời
    DROP TEMPORARY TABLE IF EXISTS temp_lottery_result;
END 

CREATE PROCEDURE LoadToMart()
BEGIN
        --(LoadToMart) 12.2. Truncate bảng datamart.lottery_result_mart
		TRUNCATE TABLE datamart.lottery_result_mart;
        --(LoadToMart) 12.3. Insert tất cả dữ liệu từ bảng data_aggregate.lottery_result_aggregate;
		INSERT INTO datamart.lottery_result_mart (id, region, province, date, prize, winning_number)
		SELECT id, region, province, date, prize, winning_number FROM data_aggregate.lottery_result_aggregate;

END 

CREATE PROCEDURE LoadToMart()
BEGIN
		TRUNCATE TABLE datamart.lottery_result_mart;
		INSERT INTO datamart.lottery_result_mart (id, region, province, date, prize, winning_number)
		SELECT id, region, province, date, prize, winning_number FROM data_aggregate.lottery_result_aggregate;

END 
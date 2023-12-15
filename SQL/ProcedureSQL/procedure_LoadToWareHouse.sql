CREATE PROCEDURE LoadToWareHouse()
BEGIN
		INSERT INTO warehouse.lottery_result_fact (region_key, province_key, date_key, prize_key, winning_number, created_at, updated_at)
		SELECT region_key, province_key, date_key, prize_key, winning_number, created_at, updated_at from control.temp_fact;
END 
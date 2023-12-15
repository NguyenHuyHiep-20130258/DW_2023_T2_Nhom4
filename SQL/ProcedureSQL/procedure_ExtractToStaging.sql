CREATE PROCEDURE ExtractToStaging(
    in_region VARCHAR(255),
    in_province VARCHAR(255),
    in_date DATE,
    in_prize VARCHAR(255),
    in_winning_number VARCHAR(255)
)
BEGIN
    INSERT INTO staging.lottery_result_staging (region, province, date, prize, winning_number)
    VALUES (in_region, in_province, in_date, in_prize, in_winning_number);
END 
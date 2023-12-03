

INSERT INTO rentals.public.rentals (rental_uid, username, payment_uid, car_uid, date_from, date_to, status)
VALUES
    (gen_random_uuid(), 'username1', gen_random_uuid(), gen_random_uuid(), '2021-01-01 00:00:00', '2021-01-02 00:00:00', 'IN_PROGRESS'),
    (gen_random_uuid(), 'username2', gen_random_uuid(), gen_random_uuid(), '2021-02-01 00:00:00', '2021-02-02 00:00:00', 'FINISHED'),
    (gen_random_uuid(), 'username3', gen_random_uuid(), gen_random_uuid(), '2021-03-01 00:00:00', '2021-03-02 00:00:00', 'CANCELED'),
    (gen_random_uuid(), 'username4', gen_random_uuid(), gen_random_uuid(), '2021-04-01 00:00:00', '2021-04-02 00:00:00', 'IN_PROGRESS'),
    (gen_random_uuid(), 'username5', gen_random_uuid(), gen_random_uuid(), '2021-05-01 00:00:00', '2021-05-02 00:00:00', 'FINISHED'),
    (gen_random_uuid(), 'username6', gen_random_uuid(), gen_random_uuid(), '2021-06-01 00:00:00', '2021-06-02 00:00:00', 'IN_PROGRESS'),
    (gen_random_uuid(), 'username7', gen_random_uuid(), gen_random_uuid(), '2021-07-01 00:00:00', '2021-07-02 00:00:00', 'FINISHED'),
    (gen_random_uuid(), 'username8', gen_random_uuid(), gen_random_uuid(), '2021-08-01 00:00:00', '2021-08-02 00:00:00', 'CANCELED'),
    (gen_random_uuid(), 'username9', gen_random_uuid(), gen_random_uuid(), '2021-09-01 00:00:00', '2021-08-02 00:00:00', 'CANCELED');
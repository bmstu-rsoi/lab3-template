INSERT INTO cars.public.cars(car_uid, brand, model, registration_number, power, price, type, availability)
VALUES
       (gen_random_uuid(), 'Honda', 'S3', 'AM777R', 180, 1800000, 'SUV', false),
       (gen_random_uuid(), 'Mazda', 'CX-5', 'RU235X', 200, 2400000, 'ROADSTER', true),
       (gen_random_uuid(), 'Nissan', 'Almera', 'KA832A', 325, 5600000, 'MINIVAN', false),
       (gen_random_uuid(), 'BMW', 'M3', 'ZU532K', 123, 6100000, 'ROADSTER', true),
       (gen_random_uuid(), 'Volkswagen', 'PU994Z', 'RU182KL', 621, 1236321, 'SUV', false),
       (gen_random_uuid(), 'Audi', 'A8', 'QK835L', 220, 1000000, 'SEDAN', true),
       (gen_random_uuid(), 'Mercedes', 'Maybach', 'XU773C', 233, 845022, 'ROADSTER', true),
       (gen_random_uuid(), 'Ferrari', 'F8', 'MF231L', 643, 5829402, 'SEDAN', true),
       (gen_random_uuid(), 'Porsche', '911', 'XN888L', 125, 8593022, 'SUV', false),
       (gen_random_uuid(), 'BMW', 'M5', 'RU182KL', 324, 1294032, 'ROADSTER', true),
       (gen_random_uuid(), 'Geely', 'Monjaro', 'PS830Q', 673, 8502358, 'SEDAN', true),
       (gen_random_uuid(), 'Toyota', 'Camri', 'SU887L', 134, 9502423, 'MINIVAN', true),
       (gen_random_uuid(), 'Subaru', 'P3_1', 'AR222R', 280, 1100588, 'MINIVAN', true);

INSERT INTO cars.public.cars(car_uid, brand, model, registration_number, power, price, type, availability)
VALUES
    ('109b42f3-198d-4c89-9276-a7520a7120ab', 'Mercedes Benz', 'GLA 250', 'ЛО777Х799', 249, 3500, 'SEDAN', true)
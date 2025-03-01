TRUNCATE TABLE currencies RESTART IDENTITY CASCADE;

INSERT INTO currencies (code, full_name, sign) VALUES
                                                       (2, 'RUB', 'Ruble', 'P'),
                                                       (3, 'USD', 'United States Dollar', '$'),
                                                       (4, 'EUR', 'Euro', '€'),
                                                       (5, 'CAD', 'Canadian Dollar', '$'),
                                                       (7, 'JPY', 'Japanese Yen', '¥');
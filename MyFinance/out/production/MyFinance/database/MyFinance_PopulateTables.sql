/* MyFinance sample data */ 

insert into business_categories values ('Bank', 'Financial establishments that invest, administer loans, exchange currency.');
insert into business_categories values ('Housing', 'Property managers, landlords.');
insert into business_categories values ('Restaurant', null);
insert into business_categories values ('Grocery', null);
insert into business_categories values ('Gas', 'Gas stations including any associated business, such as a convenience store.');

insert into postal_codes values ('500 Broadway Ave.', 'Vancouver', 'BC', 'V5Z1M2'); 
insert into postal_codes values ('350 Hallmark Ln.', 'Vancouver', 'BC', 'V7X2V3');
insert into postal_codes values ('800 Alberta St.', 'Vancouver', 'BC', 'V4Z1V2');
insert into postal_codes values ('250 55th St.', 'Red Deer', 'AB', 'T4N3V6');
insert into postal_codes values ('Wellington St.', 'Ottawa', 'ON', 'K1A0P9');

insert into corporations values ('100', 'Bank', 'TD Canada Trust'); 
insert into corporations values ('101', 'Housing', 'Strata'); 
insert into corporations values ('102', 'Restaurant', 'Starbucks'); 
insert into corporations values ('103', 'Grocery', 'Whole Foods'); 
insert into corporations values ('104', 'Restaurant', 'Whole Foods');  
insert into corporations values ('105', 'Bank', 'CIBC');  

insert into my_finance_users values ('bpasula', 'password', 'Brett', 'Pasula', '500 Broadway Ave.', 'Vancouver', 'BC'); 
insert into my_finance_users values ('jdoe', 'J0hnDo3', 'John', 'Doe', '350 Hallmark Ln.', 'Vancouver', 'BC'); 

insert into accounts values ('bpasula', '100', 'Chequing', '500');  
insert into accounts values ('bpasula', '105', 'Savings', '0'); 
insert into accounts values ('jdoe', '105', 'Chequing', '3000'); 
insert into accounts values ('jdoe', '100', 'Savings', '35000');  

insert into administrators values ('bpasula', 'Brett', 'Pasula');  
insert into administrators values ('jdoe', 'John', 'Doe');   

insert into brokerage_fees values ('100', 10); 
insert into brokerage_fees values ('105', 11.50); 
 
insert into budgets values ('01', '2020', 'bpasula', 2000); 
insert into budgets values ('02', '2020', 'bpasula', 2500);  
insert into budgets values ('03', '2020', 'bpasula', 2500); 
insert into budgets values ('02', '2020', 'jdoe', 4000); 
insert into budgets values ('03', '2020', 'jdoe', 5000);   

insert into budget_categories values ('100', 'Transportation', '02', '2020', 100, 'bpasula');  
insert into budget_categories values ('101', 'Grocery', '02', '2020', 200, 'bpasula'); 
insert into budget_categories values ('102', 'Rent', '02', '2020', 1000, 'bpasula'); 
insert into budget_categories values ('103', 'Restaurants', '03', '2020', 300, 'jdoe'); 
insert into budget_categories values ('104', 'Rent', '03', '2020', 1500, 'jdoe');  

insert into cash_transactions values ('28-02-20', 10.50, '104', 'jdoe', '103'); 
insert into cash_transactions values ('28-02-20', 9.25, '104', 'bpasula', '101'); 
insert into cash_transactions values ('27-02-20', 80.50, '103', 'bpasula', '101'); 
insert into cash_transactions values ('27-02-20', 60, '103', 'bpasula', '101'); 
insert into cash_transactions values ('26-02-20', 8.50, '102', 'jdoe', '103'); 

insert into credit_cards values ('123456789123', 'MasterCard', 0, 1000, 1000, '100', 'bpasula'); 
insert into credit_cards values ('221144336655', 'Visa', 150, 1000, 500, '105', 'bpasula');
insert into credit_cards values ('123498761234', 'MasterCard', 900, 5000, 4950, '105', 'jdoe');
insert into credit_cards values ('987654321234', 'Visa', 99.75, 900, 800.25, '100', 'jdoe');

insert into credit_transactions values ('28-02-20', 10.50, '102', 'jdoe', '102', '123498761234'); 
insert into credit_transactions values ('27-02-20', 40.85, '103', 'bpasula', '101', '123498761234');
insert into credit_transactions values ('14-02-20', 17.89, '102', 'bpasula', '102', '123498761234');
insert into credit_transactions values ('13-02-20', 218.50, '103', 'bpasula', '102', '123498761234');
insert into credit_transactions values ('09-02-20', 30.50, '104', 'jdoe', '104', '123498761234');

insert into investments values ('bpasula', '103', '100', '28-02-20', 500, 5000, 4560);
insert into investments values ('bpasula', '100', '105', '15-11-08', 700, 7000, 7500);
insert into investments values ('jdoe', '105', '100', '08-06-12', 1000, 500, 600);
insert into investments values ('jdoe', '100', '100', '20-03-01', 800, 800, 780);

insert into physical_items values ('bpasula', 'Pen', 2.50);
insert into physical_items values ('jdoe', 'Car', 85000);

insert into bills values ('28-02-20', 'bpasula', '100', null, '123456789123', 500);
insert into bills values ('03-01-20', 'bpasula', '101', '102', null, 2000);
insert into bills values ('27-03-20', 'bpasula', '105', null, '221144336655', 1000);
insert into bills values ('03-02-20', 'jdoe', '101', '104', null, 1500);
insert into bills values ('31-03-20', 'jdoe', '100', null, '987654321234', 800);













drop table bills; 
drop table physical_items; 
drop table investments; 
drop table credit_transactions; 
drop table credit_cards; 
drop table cash_transactions;  
drop table budget_categories;  
drop table budgets; 
drop table brokerage_fees; 
drop table administrators; 
drop table accounts; 
drop table my_finance_users; 
drop table corporations; 
drop table postal_codes; 
drop table business_categories; 

CREATE TABLE business_categories ( 
    category_name   VARCHAR(50) PRIMARY KEY, 
    description     VARCHAR(255)
); 

grant select on business_categories to public; 

CREATE TABLE postal_codes ( 
    street_address  VARCHAR(50) NOT NULL, 
    city            VARCHAR(50) NOT NULL, 
    province        CHAR(2)     NOT NULL,
    postal_code     CHAR(6)     NOT NULL, 
    PRIMARY KEY (street_address, city, province) 
);  

grant select on postal_codes to public;
    
CREATE TABLE corporations ( 
    corporation_id          CHAR(3)    PRIMARY KEY,
    category_name           VARCHAR(50)  NOT NULL, 
    corporation_name        VARCHAR(50)  NOT NULL,  
    FOREIGN KEY (category_name) REFERENCES business_categories  
);   

grant select on corporations to public; 

CREATE TABLE my_finance_users ( 
    username        VARCHAR(50) PRIMARY KEY, 
    user_password   VARCHAR(50) NOT NULL, 
    first_name      VARCHAR(50) NOT NULL, 
    last_name       VARCHAR(50) NOT NULL, 
    street_address  VARCHAR(50) NOT NULL, 
    city            VARCHAR(50) NOT NULL, 
    province        CHAR(2)     NOT NULL,
    FOREIGN KEY (street_address, city, province) REFERENCES postal_codes    
);  

grant select on my_finance_users to public;  

CREATE TABLE accounts (  
    username        VARCHAR(50)     NOT NULL,   
    corporation_id  CHAR(3)         NOT NULL, 
    account_type    VARCHAR(50)     NOT NULL, 
    account_value   NUMBER          NOT NULL, 
    PRIMARY KEY (username, corporation_id, account_type),
    FOREIGN KEY (username) REFERENCES my_finance_users   
        ON DELETE CASCADE, 
    FOREIGN KEY (corporation_id) REFERENCES corporations  
        ON DELETE SET NULL
);   

grant select on accounts to public;
    
CREATE TABLE administrators (  
    username      VARCHAR(50) PRIMARY KEY, 
    first_name    VARCHAR(50) NOT NULL, 
    last_name     VARCHAR(50) NOT NULL, 
    FOREIGN KEY (username) REFERENCES my_finance_users
        ON DELETE CASCADE
);   

grant select on administrators to public; 

CREATE TABLE brokerage_fees ( 
    corporation_id     CHAR(3)   PRIMARY KEY, 
    fee                NUMBER    NOT NULL,
    FOREIGN KEY (corporation_id) REFERENCES corporations 
        ON DELETE CASCADE  
);   

grant select on brokerage_fees to public;  

CREATE TABLE budgets ( 
    budget_month    CHAR(2)         NOT NULL, 
    budget_year     CHAR(4)         NOT NULL, 
    username        VARCHAR(50)     NOT NULL, 
    amount          NUMBER          NOT NULL,  
    PRIMARY KEY (budget_month, budget_year, username),
    FOREIGN KEY (username) REFERENCES my_finance_users 
        ON DELETE CASCADE
); 

grant select on budgets to public; 

CREATE TABLE budget_categories ( 
    budget_category_id  CHAR(3)         PRIMARY KEY, 
    budget_name         VARCHAR(50)     NOT NULL, 
    budget_month        CHAR(2)         NOT NULL, 
    budget_year         CHAR(4)         NOT NULL, 
    amount              NUMBER          NOT NULL, 
    username            VARCHAR(50)     NOT NULL, 
    FOREIGN KEY (budget_month, budget_year, username) REFERENCES budgets,
    FOREIGN KEY (username) REFERENCES my_finance_users 
        ON DELETE CASCADE 
);   

grant select on budget_categories to public; 

CREATE TABLE cash_transactions ( 
    transaction_date    DATE        NOT NULL, 
    amount              NUMBER      NOT NULL,
    corporation_id      CHAR(3)     DEFAULT '999', 
    username            VARCHAR(50) NOT NULL, 
    budget_category_id  CHAR(3), 
    PRIMARY KEY (transaction_date, amount, corporation_id, username), 
    FOREIGN KEY (corporation_id) REFERENCES corporations,
    FOREIGN KEY (username) REFERENCES my_finance_users
        ON DELETE CASCADE, 
    FOREIGN KEY (budget_category_id) REFERENCES budget_categories       
);   

grant select on cash_transactions to public;

CREATE TABLE credit_cards ( 
    credit_card_number  CHAR(12)        PRIMARY KEY, 
    credit_card_name    VARCHAR(50)     NOT NULL, 
    balance             NUMBER          NOT NULL, 
    credit_limit        NUMBER          NOT NULL, 
    credit_available    NUMBER          NOT NULL, 
    corporation_id      CHAR(3), 
    username            VARCHAR(50)     NOT NULL, 
    FOREIGN KEY (corporation_id) REFERENCES corporations 
        ON DELETE SET NULL, 
    FOREIGN KEY (username) REFERENCES my_finance_users 
        ON DELETE CASCADE
); 

grant select on credit_cards to public;

CREATE TABLE credit_transactions ( 
    transaction_date    DATE            NOT NULL, 
    amount              NUMBER          NOT NULL, 
    corporation_id      CHAR(3)         DEFAULT '999', 
    username            VARCHAR(50)     NOT NULL, 
    budget_category_id  CHAR(3), 
    credit_card_number  CHAR(12), 
    PRIMARY KEY (transaction_date, amount, corporation_id, username), 
    FOREIGN KEY (corporation_id) REFERENCES corporations,
    FOREIGN KEY (username) REFERENCES my_finance_users
        ON DELETE CASCADE, 
    FOREIGN KEY(budget_category_id) REFERENCES budget_categories 
        ON DELETE SET NULL, 
    FOREIGN KEY (credit_card_number) REFERENCES credit_cards
        ON DELETE SET NULL
);    

grant select on credit_transactions to public;

CREATE TABLE investments ( 
    username            VARCHAR(50)     NOT NULL, 
    corporation_id      CHAR(3)         NOT NULL, 
    bank_id             CHAR(3)         NOT NULL, 
    purchase_date       DATE            NOT NULL, 
    quantity            NUMBER          NOT NULL, 
    investment_cost     NUMBER          NOT NULL,  
    investment_value    NUMBER          NOT NULL,
    PRIMARY KEY (username, corporation_id, bank_id, purchase_date, quantity), 
    FOREIGN KEY (username) REFERENCES my_finance_users
        ON DELETE CASCADE, 
    FOREIGN KEY (corporation_id) REFERENCES corporations
        ON DELETE CASCADE, 
    FOREIGN KEY (bank_id) REFERENCES corporations(corporation_id) 
        ON DELETE CASCADE
); 

grant select on investments to public;

CREATE TABLE physical_items ( 
    username    VARCHAR(50)     NOT NULL, 
    item_name   VARCHAR(50)     NOT NULL, 
    item_value  NUMBER          NOT NULL, 
    PRIMARY KEY (username, item_name), 
    FOREIGN KEY (username) REFERENCES my_finance_users
        ON DELETE CASCADE
);  

grant select on physical_items to public;
 
CREATE TABLE bills ( 
    due_date            DATE            NOT NULL, 
    username            VARCHAR(50)     NOT NULL, 
    corporation_id      CHAR(3)         NOT NULL,  
    budget_category_id  CHAR(3), 
    credit_card_number  CHAR(12), 
    amount_due          NUMBER, 
    PRIMARY KEY (due_date, username, corporation_id), 
    FOREIGN KEY (username) REFERENCES my_finance_users 
        ON DELETE CASCADE, 
    FOREIGN KEY (corporation_id) REFERENCES corporations
        ON DELETE CASCADE, 
    FOREIGN KEY (budget_category_id) REFERENCES budget_categories
        ON DELETE SET NULL, 
    FOREIGN KEY (credit_card_number) REFERENCES credit_cards 
        ON DELETE CASCADE     
);   

grant select on bills to public;

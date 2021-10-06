create schema if not exists CURRENCY_EXCHANGE character set 'utf8mb4';

use CURRENCY_EXCHANGE;

-- drop table if exists USERS;

create table USERS (
	USER_ID bigint auto_increment primary key,
    USER_LOGIN varchar(16) not null unique,
    USER_PASS varchar(255) not null,
    USER_ROLE varchar(16) not null,
    USER_F_NAME varchar(50) not null,
    USER_L_NAME varchar(50) not null
);

create index USER_LOGIN_INDEX on USERS(USER_LOGIN);

-- --------------------------------------------------

-- drop table if exists EXCHANGE_RATES;

create table EXCHANGE_RATES (
	EXCH_RATE_ID int auto_increment primary key,
    RATE_DT date not null,
    CCY char(3) not null,
    BASE_CCY char(3) not null,
    BUY numeric(6,3) unsigned not null,
    SALE numeric(6,3) unsigned not null
);

create index RATE_DT_INDEX on EXCHANGE_RATES(RATE_DT);

-- --------------------------------------------------

-- drop table if exists ORDERS;

create table ORDERS (
	ORDER_ID bigint auto_increment primary key,
	ORDER_NUM varchar(20) not null,
    USER_ID bigint not null,
	CL_PASS_INFO varchar(8) not null,
    ORDER_DT date not null,
    RATE_DT date not null,
    CCY char(3) not null,
    BASE_CCY char(3) not null,
    ORDER_OPER char(4) not null,
	EXCH_RATE numeric(6,3) unsigned not null,
    CCY_AMT numeric(15,2) unsigned not null,
	BASE_CCY_AMT numeric(15,2) unsigned not null,
    ORDER_STATUS char(3) not null default 'ACT',
    constraint FK_ORDERS_USERS foreign key(USER_ID) references USERS(USER_ID)
);

create index ORDER_NUM_INDEX on ORDERS(ORDER_NUM);

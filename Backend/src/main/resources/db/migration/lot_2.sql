-- public.auction_table_auction_actions definition

    -- Drop table

    -- DROP TABLE public.auction_table_auction_actions;

    CREATE TABLE public.auction_table_auction_actions (
    auction_event_id int8 NOT NULL,
    auction_actions_id int4 NOT NULL,
    CONSTRAINT auction_table_auction_actions_pkey PRIMARY KEY (auction_event_id, auction_actions_id),
    CONSTRAINT uk_4jr4w5rmkmak8le3d7k4eo3kx UNIQUE (auction_actions_id)
    );


    -- public.users definition

    -- Drop table

    -- DROP TABLE public.users;

    CREATE TABLE public.users (
    id bigserial NOT NULL,
    email varchar(255) NULL,
    first_name varchar(255) NULL,
    last_name varchar(255) NULL,
    login varchar(255) NULL,
    "password" varchar(255) NULL,
    "role" varchar(255) NULL,
    user_rating float8 NULL,
    CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
    CONSTRAINT ukow0gan20590jrb00upg3va2fn UNIQUE (login),
    CONSTRAINT users_pkey PRIMARY KEY (id)
    );


    -- public.users_role definition

    -- Drop table

    -- DROP TABLE public.users_role;

    CREATE TABLE public.users_role (
    id serial NOT NULL,
    user_role int4 NULL,
    CONSTRAINT users_role_pkey PRIMARY KEY (id)
    );


    -- public.auction_table definition

    -- Drop table

    -- DROP TABLE public.auction_table;

    CREATE TABLE public.auction_table (
    id bigserial NOT NULL,
    "type" varchar(255) NULL,
    description text NULL,
    finish_date timestamp NULL,
    finish_price float8 NULL,
    gen_date timestamp NULL,
    start_date timestamp NULL,
    start_price float8 NULL,
    status varchar(255) NULL,
    title varchar(255) NULL,
    gen_user_id int8 NOT NULL,
    CONSTRAINT auction_table_pkey PRIMARY KEY (id),
    CONSTRAINT fkaww0d8j91a82lecat6dhyqhjj FOREIGN KEY (gen_user_id) REFERENCES public.users(id)
    );


    -- public.auction_winner definition

    -- Drop table

    -- DROP TABLE public.auction_winner;

    CREATE TABLE public.auction_winner (
    id bigserial NOT NULL,
    price float8 NULL,
    auction_id int8 NOT NULL,
    user_id int8 NOT NULL,
    CONSTRAINT auction_winner_pkey PRIMARY KEY (id),
    CONSTRAINT fk7xtbxihi0nc4ynv5rjhpqgw7r FOREIGN KEY (user_id) REFERENCES public.users(id),
    CONSTRAINT fki5gc3x8ptcn4rhchscwwvw471 FOREIGN KEY (auction_id) REFERENCES public.auction_table(id)
    );


    -- public.reset_password definition

    -- Drop table

    -- DROP TABLE public.reset_password;

    CREATE TABLE public.reset_password (
    id serial NOT NULL,
    code varchar(255) NULL,
    enabled bool NULL,
    user_id int8 NOT NULL,
    CONSTRAINT reset_password_pkey PRIMARY KEY (id),
    CONSTRAINT fknn31vah8aab9hd92x70pgdsbh FOREIGN KEY (user_id) REFERENCES public.users(id)
    );


    -- public.auction_action definition

    -- Drop table

    -- DROP TABLE public.auction_action;

    CREATE TABLE public.auction_action (
    id bigserial NOT NULL,
    bet float8 NULL,
    get_date timestamp NULL,
    auction_id int8 NOT NULL,
    user_id int8 NOT NULL,
    CONSTRAINT auction_action_pkey PRIMARY KEY (id),
    CONSTRAINT fk76c4y08hgspbf4unbg1pdabmt FOREIGN KEY (user_id) REFERENCES public.users(id),
    CONSTRAINT fka2v3to4h0yqm8ml9nsdgneu2t FOREIGN KEY (auction_id) REFERENCES public.auction_table(id)
    );


    -- public.auction_charity definition

    -- Drop table

    -- DROP TABLE public.auction_charity;

    CREATE TABLE public.auction_charity (
    id bigserial NOT NULL,
    "percent" float8 NULL,
    auction_id int8 NOT NULL,
    CONSTRAINT auction_charity_pkey PRIMARY KEY (id),
    CONSTRAINT fkjrplkdtr4yppwu0rxplg521wk FOREIGN KEY (auction_id) REFERENCES public.auction_table(id)
    );


    -- public.auction_sort definition

    -- Drop table

    -- DROP TABLE public.auction_sort;

    CREATE TABLE public.auction_sort (
    id bigserial NOT NULL,
    sort_rating int8 NULL,
    auction_id int8 NOT NULL,
    CONSTRAINT auction_sort_pkey PRIMARY KEY (id),
    CONSTRAINT fk1ovrjxrtxj3i3j39ywngcdsgn FOREIGN KEY (auction_id) REFERENCES public.auction_table(id)
    );
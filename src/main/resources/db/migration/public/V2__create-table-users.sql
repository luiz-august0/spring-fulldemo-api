CREATE TABLE IF NOT EXISTS public.users (
    id INT4 NOT NULL,
    login varchar(255),
    password varchar(255) NOT NULL,
    role varchar(30) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    photo varchar(255),
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_user
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.users ADD CONSTRAINT pk_users PRIMARY KEY (id);

ALTER TABLE public.users ADD CONSTRAINT users_login_key UNIQUE (login);

CREATE TRIGGER tr_set_schema_users BEFORE INSERT ON users FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
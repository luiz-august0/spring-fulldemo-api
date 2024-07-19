CREATE TABLE IF NOT EXISTS users () INHERITS (public.users);

ALTER TABLE users ADD CONSTRAINT pk_users PRIMARY KEY (id);

ALTER TABLE users ADD CONSTRAINT users_login_key UNIQUE (login);

CREATE TRIGGER tr_set_schema_users BEFORE INSERT ON users FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
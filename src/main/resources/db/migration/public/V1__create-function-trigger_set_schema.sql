CREATE OR REPLACE FUNCTION trigger_set_schema()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.schema := current_schema();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
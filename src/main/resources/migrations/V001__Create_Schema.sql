-- Tables

CREATE TABLE IF NOT EXISTS paradero (
    id character varying(50) NOT NULL,
    nombre character varying(100) NOT NULL,
    latitud numeric(9,6) NOT NULL,
    longitud numeric(9,6) NOT NULL
);

CREATE TABLE IF NOT EXISTS paraderoruta (
    id bigint NOT NULL,
    sentido_ida boolean NOT NULL,
    id_paradero_id character varying(50) NOT NULL,
    id_ruta_id character varying(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS recarga (
    id uuid NOT NULL,
    fecha_hora timestamp with time zone NOT NULL,
    monto_recargado float8 NOT NULL,
    medio_pago character varying(50) NOT NULL,
    codigo_tarjeta_id character varying(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS ruta (
    id character varying(50) NOT NULL,
    nombre character varying(100) NOT NULL,
    servicio character varying(100) NOT NULL,
    inicio character varying(100) NOT NULL,
    final character varying(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS tarifa (
    id character varying(50) NOT NULL,
    precio_base double precision NOT NULL,
    id_ruta_id character varying(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS tarjeta (
    codigo character varying(50) NOT NULL,
    saldo double precision NOT NULL,
    tipo integer NOT NULL,
    limite double precision,
    fecha_vencimiento date,
    id_usuario_id uuid NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    id uuid NOT NULL,
    dni character varying(50) NOT NULL,
    nombres character varying(100) NOT NULL,
    apellidos character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(100) NOT NULL,
    recovery_token character varying(100)
);

CREATE TABLE IF NOT EXISTS viaje (
    id uuid NOT NULL,
    fecha_hora timestamp with time zone NOT NULL,
    precio_final double precision NOT NULL,
    codigo_tarjeta_id character varying(50) NOT NULL,
    id_tarifa_id character varying(50) NOT NULL
);

-- Constraints

ALTER TABLE paraderoruta ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME paraderoruta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );

ALTER TABLE ONLY paradero
    ADD CONSTRAINT paradero_pkey PRIMARY KEY (id);

ALTER TABLE ONLY paraderoruta
    ADD CONSTRAINT paraderoruta_id_ruta_id_id_paradero_id_696ed98a_uniq UNIQUE (id_ruta_id, id_paradero_id);

ALTER TABLE ONLY paraderoruta
    ADD CONSTRAINT paraderoruta_pkey PRIMARY KEY (id);

ALTER TABLE ONLY recarga
    ADD CONSTRAINT recarga_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ruta
    ADD CONSTRAINT ruta_pkey PRIMARY KEY (id);

ALTER TABLE ONLY tarifa
    ADD CONSTRAINT tarifa_pkey PRIMARY KEY (id);

ALTER TABLE ONLY tarjeta
    ADD CONSTRAINT tarjeta_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_dni_key UNIQUE (dni);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);

ALTER TABLE ONLY viaje
    ADD CONSTRAINT viaje_pkey PRIMARY KEY (id);


-- Indexes

CREATE INDEX elimapass_paradero_id_1d86fa70_like ON paradero USING btree (id varchar_pattern_ops);

CREATE INDEX elimapass_paraderoruta_id_paradero_id_a4d16901 ON paraderoruta USING btree (id_paradero_id);

CREATE INDEX elimapass_paraderoruta_id_paradero_id_a4d16901_like ON paraderoruta USING btree (id_paradero_id varchar_pattern_ops);

CREATE INDEX elimapass_paraderoruta_id_ruta_id_6296911a ON paraderoruta USING btree (id_ruta_id);

CREATE INDEX elimapass_paraderoruta_id_ruta_id_6296911a_like ON paraderoruta USING btree (id_ruta_id varchar_pattern_ops);

CREATE INDEX elimapass_recarga_codigo_tarjeta_id_61fa0927 ON recarga USING btree (codigo_tarjeta_id);

CREATE INDEX elimapass_recarga_codigo_tarjeta_id_61fa0927_like ON recarga USING btree (codigo_tarjeta_id varchar_pattern_ops);

CREATE INDEX elimapass_ruta_id_72991e98_like ON ruta USING btree (id varchar_pattern_ops);

CREATE INDEX elimapass_tarifa_id_075607a3_like ON tarifa USING btree (id varchar_pattern_ops);

CREATE INDEX elimapass_tarifa_id_ruta_id_b78d4930 ON tarifa USING btree (id_ruta_id);

CREATE INDEX elimapass_tarifa_id_ruta_id_b78d4930_like ON tarifa USING btree (id_ruta_id varchar_pattern_ops);

CREATE INDEX elimapass_tarjeta_codigo_9336ee71_like ON tarjeta USING btree (codigo varchar_pattern_ops);

CREATE INDEX elimapass_tarjeta_id_usuario_id_c3e6af18 ON tarjeta USING btree (id_usuario_id);

CREATE INDEX elimapass_usuario_dni_32cf89d1_like ON usuario USING btree (dni varchar_pattern_ops);

CREATE INDEX elimapass_usuario_email_683e7fde_like ON usuario USING btree (email varchar_pattern_ops);

CREATE INDEX elimapass_viaje_codigo_tarjeta_id_3505d609 ON viaje USING btree (codigo_tarjeta_id);

CREATE INDEX elimapass_viaje_codigo_tarjeta_id_3505d609_like ON viaje USING btree (codigo_tarjeta_id varchar_pattern_ops);

CREATE INDEX elimapass_viaje_id_tarifa_id_4a17028b ON viaje USING btree (id_tarifa_id);

CREATE INDEX elimapass_viaje_id_tarifa_id_4a17028b_like ON viaje USING btree (id_tarifa_id varchar_pattern_ops);

-- Foreign Keys

ALTER TABLE ONLY paraderoruta
    ADD CONSTRAINT elimapass_paraderoru_id_paradero_id_a4d16901_fk_elimapass FOREIGN KEY (id_paradero_id) REFERENCES paradero(id) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE ONLY paraderoruta
    ADD CONSTRAINT elimapass_paraderoruta_id_ruta_id_6296911a_fk_elimapass_ruta_id FOREIGN KEY (id_ruta_id) REFERENCES ruta(id) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE ONLY recarga
    ADD CONSTRAINT elimapass_recarga_codigo_tarjeta_id_61fa0927_fk_elimapass FOREIGN KEY (codigo_tarjeta_id) REFERENCES tarjeta(codigo) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE ONLY tarifa
    ADD CONSTRAINT elimapass_tarifa_id_ruta_id_b78d4930_fk_elimapass_ruta_id FOREIGN KEY (id_ruta_id) REFERENCES ruta(id) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE ONLY tarjeta
    ADD CONSTRAINT elimapass_tarjeta_id_usuario_id_c3e6af18_fk_elimapass FOREIGN KEY (id_usuario_id) REFERENCES usuario(id) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE ONLY viaje
    ADD CONSTRAINT elimapass_viaje_codigo_tarjeta_id_3505d609_fk_elimapass FOREIGN KEY (codigo_tarjeta_id) REFERENCES tarjeta(codigo) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE ONLY viaje
    ADD CONSTRAINT elimapass_viaje_id_tarifa_id_4a17028b_fk_elimapass_tarifa_id FOREIGN KEY (id_tarifa_id) REFERENCES tarifa(id) DEFERRABLE INITIALLY DEFERRED;
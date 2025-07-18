-- Test schema for Component Reservation Shred

-- Drop tables if they exist to ensure a clean state
DROP TABLE IF EXISTS component_reservation;
DROP TABLE IF EXISTS stock_level;
DROP TABLE IF EXISTS device_registry;

-- Create device_registry table for device validation tests
CREATE TABLE device_registry (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    imei VARCHAR(15) UNIQUE NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL, -- 'active', 'inactive', 'blocked'
    last_communication_timestamp TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Create stock_level table
-- This table is shared and holds the inventory information.
CREATE TABLE stock_level (
    item_id UUID NOT NULL,
    location_id UUID NOT NULL,
    quantity_on_hand NUMERIC(19, 4) NOT NULL,
    reserved_qty NUMERIC(19, 4) NOT NULL DEFAULT 0,
    last_updated TIMESTAMP WITH TIME ZONE,
    version BIGINT,
    PRIMARY KEY (item_id, location_id)
);

-- Create component_reservation table
-- This table stores the details of each reservation.
CREATE TABLE component_reservation (
    reservation_id UUID PRIMARY KEY,
    item_id UUID NOT NULL,
    location_id UUID NOT NULL,
    production_run_id UUID NOT NULL,
    quantity_reserved NUMERIC(19, 4) NOT NULL,
    quantity_unit VARCHAR(50) NOT NULL,
    reserved_by UUID NOT NULL,
    reserved_at TIMESTAMP WITH TIME ZONE NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(20) NOT NULL,
    version BIGINT
);

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_reservation_production_run_id ON component_reservation(production_run_id);
CREATE INDEX IF NOT EXISTS idx_reservation_item_location ON component_reservation(item_id, location_id);
CREATE INDEX IF NOT EXISTS idx_reservation_status ON component_reservation(status);
CREATE INDEX IF NOT EXISTS idx_device_registry_imei ON device_registry(imei);
CREATE INDEX IF NOT EXISTS idx_device_registry_status ON device_registry(status);

-- Insert sample device registry data for testing
INSERT INTO device_registry (imei, manufacturer, model, status, last_communication_timestamp) VALUES
('123456789012346', 'TestManufacturer', 'TestModel', 'active', NOW()),
('123456789012347', 'TestManufacturer', 'TestModel', 'blocked', NOW() - INTERVAL '1 day'),
('123456789012348', 'TestManufacturer', 'TestModel', 'inactive', NOW() - INTERVAL '2 hours');

-- Insert sample stock level data for testing
INSERT INTO stock_level (item_id, location_id, quantity_on_hand, reserved_qty, last_updated, version) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 100.0000, 10.0000, NOW(), 1),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 50.0000, 0.0000, NOW(), 1),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 200.0000, 50.0000, NOW(), 1);

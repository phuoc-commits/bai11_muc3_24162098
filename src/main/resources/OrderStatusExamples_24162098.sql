USE WebDB_24162098;

-- Set the order ID you want to inspect.
SET @order_id = 8;

-- Change this value to one of:
-- NEW, CONFIRMED, PREPARING, SHIPPING, DELIVERING, DELIVERED, CANCELLED, RETURNED
SET @new_status = 'CANCELLED';

UPDATE CustomerOrders
SET Status = @new_status
WHERE OrderId = @order_id;

SELECT OrderId, Username, OrderDate, TotalAmount, PaymentMethod, Status
FROM CustomerOrders
WHERE OrderId = @order_id;

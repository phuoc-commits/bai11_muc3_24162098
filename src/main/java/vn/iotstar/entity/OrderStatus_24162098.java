package vn.iotstar.entity;

public enum OrderStatus_24162098 {
    NEW("Đơn hàng mới"),
    CONFIRMED("Đã xác nhận"),
    PREPARING("Chuẩn bị hàng"),
    SHIPPING("Vận chuyển"),
    DELIVERING("Đang giao hàng"),
    DELIVERED("Đã giao"),
    CANCELLED("Đơn hàng hủy"),
    RETURNED("Đơn hàng hoàn");

    private final String label;

    OrderStatus_24162098(String label) { this.label = label; }

    public String getLabel() { return label; }

    public static OrderStatus_24162098 fromCode(String code) {
        if (code == null) return null;
        try { return valueOf(code.trim().toUpperCase()); }
        catch (IllegalArgumentException e) { return null; }
    }
}

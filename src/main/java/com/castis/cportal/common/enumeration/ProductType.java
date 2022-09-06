package com.castis.cportal.common.enumeration;

public enum ProductType {
    UNKNOWN("UNKNOWN"),
    PREMIER("PREMIER"),
    NORMAL("NORMAL"),
    ETC("ETC"),
    PLATINUM("PLATINUM");

    private final String value;

    private ProductType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static ProductType valueof(String value) {
        ProductType[] list = ProductType.values();
        for (ProductType type : list) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return ProductType.UNKNOWN;
    }


}

package com.castis.cportal.common.enumeration;

public enum CompanyProductType {
    UNKNOWN("UNKNOWN"),
    PREMIER("PREMIER"),
    NORMAL("NORMAL"),
    ETC("ETC");

    private final String value;

    private CompanyProductType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static CompanyProductType valueof(String value) {
        CompanyProductType[] list = CompanyProductType.values();
        for (CompanyProductType type : list) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return CompanyProductType.UNKNOWN;
    }


}

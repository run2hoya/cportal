package com.castis.cportal.common.enumeration;

public enum UserType {
    UNKNOWN("UNKNOWN"),
    INTERNAL_PERSON("INTERNAL_PERSON"),
    COMPANY("COMPANY"),
    EXTERNAL("EXTERNAL"),
    ETC("ETC");

    private final String value;

    private UserType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static UserType valueof(String value) {
        UserType[] list = UserType.values();
        for (UserType type : list) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return UserType.UNKNOWN;
    }


}

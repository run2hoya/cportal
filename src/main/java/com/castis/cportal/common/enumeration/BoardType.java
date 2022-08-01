package com.castis.cportal.common.enumeration;

public enum BoardType {
    UNKNOWN("UNKNOWN"),
    CPORTAL_NOTI("CPORTAL_NOTI"),
    DO_NOTI("DO_NOTI");

    private final String value;

    private BoardType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static BoardType valueof(String value) {
        BoardType[] list = BoardType.values();
        for (BoardType type : list) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return BoardType.UNKNOWN;
    }


}

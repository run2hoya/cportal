package com.castis.cportal.common.enumeration;

public enum BookingState {
    UNKNOWN("UNKNOWN"),
    BOOKING("BOOKING"),
    CONFLICT("CONFLICT");

    private final String value;

    private BookingState(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static BookingState valueof(String value) {
        BookingState[] list = BookingState.values();
        for (BookingState type : list) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return BookingState.UNKNOWN;
    }


}

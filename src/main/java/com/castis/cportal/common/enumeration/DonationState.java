package com.castis.cportal.common.enumeration;

public enum DonationState {
    UNKNOWN("UNKNOWN"),
    PUBLISH("PUBLISH"),
    DONATION("DONATION"),
    COMPLETE("COMPLETE");

    private final String value;

    private DonationState(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static DonationState valueof(String value) {
        DonationState[] list = DonationState.values();
        for (DonationState type : list) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return DonationState.UNKNOWN;
    }


}

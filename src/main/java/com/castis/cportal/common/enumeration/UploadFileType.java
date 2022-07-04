package com.castis.cportal.common.enumeration;

public enum UploadFileType {
    UNKNOWN("UNKNOWN"),
    COMPANY114("COMPANY114"),
    USER_IMG("USER_IMG");

    private final String value;

    private UploadFileType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static UploadFileType valueof(String value) {
        UploadFileType[] list = UploadFileType.values();
        for (UploadFileType type : list) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return UploadFileType.UNKNOWN;
    }


}

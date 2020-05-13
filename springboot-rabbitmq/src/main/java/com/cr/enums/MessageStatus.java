package com.cr.enums;

public enum MessageStatus {
    UNCONFIRM((byte) 0),
    CONFIRM((byte) 1),
    FAILURE((byte) 2),
    CONSUMED((byte) 3);

    private Byte status;

    MessageStatus(Byte status) {
        this.status = status;
    }

    public Byte getStatus() {
        return status;
    }
}

package it.interno.enumeration;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
public enum Status {
    TO_BE_ASSIGNED("TO_BE_ASSIGNED"),
    ASSIGNED("ASSIGNED");

    private String status;

    private Status(String status) {
        this.status = status;
    }

    public static Status fromValue(String number) {
        for (Status status : Status.values()) {
            if (status.getStatus().equals(number)) {
                return status;
            }
        }
        return null;
    }

    public String getStatus()
    {
        return this.status;
    }

}

package it.interno.enumeration;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
public enum Status {
    TO_WORK ("1"),
    STARTED ("2"),
    RUNNING("3"),
    COMPLETED("4"),
    FAILED("5"),
    STOPPED("6");

    private String status;

    private Status(String status) {
        this.status = status;
    }

    public static Status fromNumber(String number) {
        for (Status status : Status.values()) {
            if (status.name().equals(number)) {
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

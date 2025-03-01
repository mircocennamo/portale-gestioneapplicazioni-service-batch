package it.interno.enumeration;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
public enum Operation {
    DELETE_APP ("DELETE_APP"),DELETE_ALL_GROUPS("DELETE_ALL_GROUPS"),DELETE_ALL_REGOLE_SICUREZZA("DELETE_ALL_REGOLE_SICUREZZA"),
    DELETE_ALL_MOTIVAZIONI("DELETE_ALL_MOTIVAZIONI"),UPDATE_ALL_GROUPS("UPDATE_ALL_GROUPS"),UPDATE_ALL_REGOLE_SICUREZZA("UPDATE_ALL_REGOLE_SICUREZZA"),
    OTHER("OTHER");

    private String operation;

    private Operation(String operation) {
        this.operation = operation;
    }

    public static Operation fromValue(String value) {
        for (Operation operation : Operation.values()) {
            if (operation.getOperation().equals(value)) {
                return operation;
            }
        }
        return null;
    }

    public String getOperation()
    {
        return this.operation;
    }

}

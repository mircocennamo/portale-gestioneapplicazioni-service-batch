package it.interno.domain;

import lombok.*;

/**
 * @author mirco.cennamo on 09/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StepResponse {
    private long stepId;
    private String stepName;
    private String status;
    private long readCount;
    private long writeCount;
    private long commitCount;
    private long rollbackCount;
    private long readSkipCount;
    private long processSkipCount;
    private long writeSkipCount;
    private java.time.LocalDateTime startTime;
    private java.time.LocalDateTime createTime;
    private java.time.LocalDateTime endTime;
    private java.time.LocalDateTime lastUpdated;
    private String exitCode;
    private String exitDescription;

}

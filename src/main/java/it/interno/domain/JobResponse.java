package it.interno.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author mirco.cennamo on 08/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JobResponse implements Serializable {
    private Map<String,String> parameters;
    private String jobName;
    private Long jobId;
    private List<StepResponse> steps;
    private String status;
    private java.time.LocalDateTime startTime;
    private java.time.LocalDateTime createTime;
    private java.time.LocalDateTime endTime;
    private java.time.LocalDateTime lastUpdated;
    private String exitCode;
    private String exitDescription;
}

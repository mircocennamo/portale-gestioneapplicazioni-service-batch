package it.interno.controller;

/**
 * @author mirco.cennamo on 05/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.interno.domain.*;
import it.interno.entity.Request;
import it.interno.enumeration.Operation;
import it.interno.enumeration.Status;
import it.interno.service.JobService;
import jakarta.validation.Valid;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobExplorer jobExplorer;

    @PostMapping("/startJob")
    @io.swagger.v3.oas.annotations.Operation(summary = "start batch", description = "start batch", tags = { "Batch" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "started batch",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JobResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "failed start batch")
    })
    public ResponseEntity<ResponseDto<JobResponse>> startJob(@Valid @RequestBody JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, ParseException {

            if(Operation.DELETE_APP.equals(Operation.fromValue(jobParameters.getOperation()))){

                Long jobId = jobService.deleteApplicationJob(jobParameters);

                return ResponseEntity.ok(ResponseDto.<JobResponse>builder()
                        .code(HttpStatus.OK.value())
                        .body(JobResponse.builder()
                                .jobId(jobId).build()).build());
            }else if(Operation.DELETE_ALL_GROUPS.equals(Operation.fromValue(jobParameters.getOperation()))){
                Long jobId = jobService.deleteAllGroupsJob(jobParameters);

                return ResponseEntity.ok(ResponseDto.<JobResponse>builder()
                        .code(HttpStatus.OK.value())
                        .body(JobResponse.builder()
                                .jobId(jobId).build()).build());
            }else if(Operation.DELETE_ALL_REGOLE_SICUREZZA.equals(Operation.fromValue(jobParameters.getOperation()))){
                Long jobId = jobService.deleteAllRulesJob(jobParameters);

                return ResponseEntity.ok(ResponseDto.<JobResponse>builder()
                        .code(HttpStatus.OK.value())
                        .body(JobResponse.builder()
                                .jobId(jobId).build()).build());
            }else if(Operation.DELETE_ALL_MOTIVAZIONI.equals(Operation.fromValue(jobParameters.getOperation()))){
                Long jobId = jobService.deleteAllMotivazioniJob(jobParameters);
                return ResponseEntity.ok(ResponseDto.<JobResponse>builder()
                        .code(HttpStatus.OK.value())
                        .body(JobResponse.builder()
                                .jobId(jobId).build()).build());
            }
            else if(Operation.UPDATE_ALL_GROUPS.equals(Operation.fromValue(jobParameters.getOperation()))){
                Long jobId = jobService.updateAllGroupsJob(jobParameters);
                return ResponseEntity.ok(ResponseDto.<JobResponse>builder()
                        .code(HttpStatus.OK.value())
                        .body(JobResponse.builder()
                                .jobId(jobId).build()).build());
            }
            else if(Operation.UPDATE_ALL_REGOLE_SICUREZZA.equals(Operation.fromValue(jobParameters.getOperation()))){
                Long jobId = jobService.updateAllRegoleSicurezzaJob(jobParameters);
                return ResponseEntity.ok(ResponseDto.<JobResponse>builder()
                        .code(HttpStatus.OK.value())
                        .body(JobResponse.builder()
                                .jobId(jobId).build()).build());
            }

            return ResponseEntity.ok(ResponseDto.<JobResponse>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .body(JobResponse.builder()
                            .status("BAD REQUEST")
                            .build()).build());
    }





    @GetMapping("/getAvailableRequests")
    @io.swagger.v3.oas.annotations.Operation(summary = "Get Available Batch Requests", description = "Get Available Batch Requests", tags = { "Batch" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found available Batch Requests",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequestDto.class)) }),
            })
    public ResponseEntity<ResponseDto<List<RequestDto>>> getAvailableRequests(@RequestParam(required = false) String status,@RequestParam(required = false) String operation){
    Status statusEnum = status == null ? null :  Status.fromValue(status);
    Operation operationEnum = operation == null ? null : Operation.fromValue(operation);
        List<Request> requests = jobService.getAllJobs(statusEnum, operationEnum);
        return ResponseEntity.ok(ResponseDto.<List<RequestDto>>builder()
                .code(HttpStatus.OK.value())
                .body(convert(requests)).build());
    }

    private List<RequestDto>convert(List<Request> requests){
        return requests.stream().map(request -> RequestDto.builder()
                .idApplicazione(request.getIdApplicazione())
                .ufficioCancellazione(request.getUfficio())
                .utenteCancellazione(request.getUtente())
                .operation(request.getOperation())
                .status(request.getStatus())
                .jobId(request.getJobId())
                .build()).collect(Collectors.toList());
    }

    @GetMapping("/info")
    @io.swagger.v3.oas.annotations.Operation(summary = "Get batch details by its job Id", description = "Get batch details by its job Id", tags = { "Batch" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Founded details batch by its job Id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JobResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "batch details not Found")
    })
    public ResponseEntity<ResponseDto<List<JobResponse>>> info(@RequestParam Long jobId) throws ExecutionException, InterruptedException {
        List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobExplorer.getJobInstance(jobId));

        if(jobExecutions == null || jobExecutions.isEmpty()){
            return ResponseEntity.ok(ResponseDto.<List<JobResponse>>builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .body(Arrays.asList(JobResponse.builder()
                            .jobId(jobId)
                            .status("NOT FOUND")
                            .build())).build());
        }
        List<JobResponse> jobResponses = new ArrayList<>();
        jobExecutions.stream().forEach(jobExecution -> {
            JobResponse jobResponse =JobResponse.builder()
                    .jobId(jobExecution.getJobInstance().getId())
                    .jobName(jobExecution.getJobInstance().getJobName())
                    .parameters(convert(jobExecution.getJobParameters().getParameters()))
                    .status(jobExecution.getStatus().name())
                    .startTime(jobExecution.getStartTime())
                    .createTime(jobExecution.getCreateTime())
                    .endTime(jobExecution.getEndTime())
                    .lastUpdated(jobExecution.getLastUpdated())
                    .exitCode(jobExecution.getExitStatus().getExitCode())
                    .exitDescription(jobExecution.getExitStatus().getExitDescription())
                    .steps(jobExecution.getStepExecutions().stream().map(stepExecution -> StepResponse.builder()
                            .stepId(stepExecution.getId())
                            .stepName(stepExecution.getStepName())
                            .status(stepExecution.getStatus().name())
                            .readCount(stepExecution.getReadCount())
                            .writeCount(stepExecution.getWriteCount())
                            .commitCount(stepExecution.getCommitCount())
                            .rollbackCount(stepExecution.getRollbackCount())
                            .readSkipCount(stepExecution.getReadSkipCount())
                            .processSkipCount(stepExecution.getProcessSkipCount())
                            .writeSkipCount(stepExecution.getWriteSkipCount())
                            .startTime(stepExecution.getStartTime())
                            .createTime(stepExecution.getCreateTime())
                            .endTime(stepExecution.getEndTime())
                            .lastUpdated(stepExecution.getLastUpdated())
                            .exitCode(stepExecution.getExitStatus().getExitCode())
                            .exitDescription(stepExecution.getExitStatus().getExitDescription())
                            .build()).collect(Collectors.toList()))
                    .build();
            jobResponses.add(jobResponse);

        });

        return ResponseEntity.ok(ResponseDto.<List<JobResponse>>builder()
                .code(HttpStatus.OK.value())
                .body(jobResponses).build());


    }

    private Map<String,String> convert(Map<String, JobParameter<?>> parameters){
       Map<String,String> param = new HashMap<>();
        parameters.forEach((k,v) -> {
            param.put(k, v.getValue().toString());
       });

        return param;
    }


}

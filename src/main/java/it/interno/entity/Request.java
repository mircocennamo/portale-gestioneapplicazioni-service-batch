package it.interno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "REQUEST",schema = "SSD_SECURITY")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;


    @Column(name = "IDAPPLICAZIONE")
    private String idApplicazione;
    @Column(name = "UTENTECANCELLAZIONE")
    private String utenteCancellazione;
    @Column(name = "UFFICIOCANCELLAZIONE")
    private String ufficioCancellazione;
    @Column(name = "OPERATION")
    private String operation;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "JOB_ID")
    private Long jobId;



}

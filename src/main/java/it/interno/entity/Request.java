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
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;


    @Column(name = "IDAPPLICAZIONE", nullable = false)
    private String idApplicazione;
    @Column(name = "UTENTECANCELLAZIONE", nullable = false)
    private String utenteCancellazione;
    @Column(name = "UFFICIOCANCELLAZIONE", nullable = false)
    private String ufficioCancellazione;
    @Column(name = "OPERATION", nullable = false)
    private String operation;

    @Column(name = "STATUS", nullable = false)
    private String status;



}

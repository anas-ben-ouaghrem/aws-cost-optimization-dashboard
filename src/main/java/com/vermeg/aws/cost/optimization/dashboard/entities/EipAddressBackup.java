package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "eip_address_backup")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "eip_address_backup"
)
public class EipAddressBackup {

    @Id
    @SequenceGenerator(
            name = "eip_address_backup_id_seq",
            sequenceName = "eip_address_backup_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "eip_address_backup_id_seq"
    )
    private Long id;

    @Column(name = "allocation_id", nullable = false)
    private String allocationId;

    @Column(name = "association_id", nullable = false)
    private String associationId;

    @Column(name = "public_ip", nullable = true)
    private String publicIp;

    @Column(name = "private_ip", nullable = false)
    private String privateIp;

    @Column(name = "instance_id", nullable = true)
    private String instanceId;

    @Column(name = "associated_account", nullable = false)
    private String associatedAccount;
}

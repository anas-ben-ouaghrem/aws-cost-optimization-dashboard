package com.vermeg.aws.cost.optimization.dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "eip_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "eip_address",
        uniqueConstraints = @UniqueConstraint(columnNames = {"allocation_id"})
)
public class EIPAddress  {
    @Id
    @SequenceGenerator(
            name = "eip_address_id_seq",
            sequenceName = "eip_address_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "eip_address_id_seq"
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

    //Relations

    @OneToMany(mappedBy = "linkedIPAddress", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<EIPOptimizationSuggestion> linkedSuggestion;

    @ManyToOne
    @JoinColumn(
            name = "associated_account_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "eip_address_associated_account_id_fk"
            )
    )
    private AwsAccountCredentials associatedAccount;
}

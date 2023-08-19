package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwsCredentialsRepository extends JpaRepository<AwsAccountCredentials, Long> {
    AwsAccountCredentials findAwsAccountCredentialsByAccountName(String name);
    AwsAccountCredentials findAwsAccountCredentialsByAccountId(String accountID);

}

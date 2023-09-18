package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.redshift.model.BucketNotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.vermeg.aws.cost.optimization.dashboard.repositories.AwsCredentialsRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.StorageBucketRepository;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {S3Service.class})
@ExtendWith(SpringExtension.class)
class S3ServiceTest {
    @MockBean
    private AwsCredentialsRepository awsCredentialsRepository;

    @Autowired
    private S3Service s3Service;

    @MockBean
    private StorageBucketRepository storageBucketRepository;

    @MockBean
    private UtilityServices utilityServices;

    /**
     * Method under test: {@link S3Service#getAllBuckets()}
     */
    @Test
    void testGetAllBuckets() {
        when(utilityServices.getS3ClientsList()).thenReturn(new ArrayList<>());
        assertTrue(s3Service.getAllBuckets().isEmpty());
        verify(utilityServices).getS3ClientsList();
    }

    /**
     * Method under test: {@link S3Service#getAllBuckets()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllBuckets2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.s3.AmazonS3.listBuckets()" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.S3Service.lambda$getAllBuckets$2(S3Service.java:155)
        //       at java.base/java.util.stream.ReferencePipeline$7$1.accept(ReferencePipeline.java:273)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:960)
        //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:934)
        //       at java.base/java.util.stream.AbstractTask.compute(AbstractTask.java:327)
        //       at java.base/java.util.concurrent.CountedCompleter.exec(CountedCompleter.java:754)
        //       at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:373)
        //       at java.base/java.util.concurrent.ForkJoinTask.invoke(ForkJoinTask.java:686)
        //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateParallel(ReduceOps.java:927)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:233)
        //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.S3Service.getAllBuckets(S3Service.java:205)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonS3, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonS3, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        when(utilityServices.getS3ClientsList()).thenReturn(pairList);
        s3Service.getAllBuckets();
    }

    /**
     * Method under test: {@link S3Service#getAllBuckets()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllBuckets3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.s3.AmazonS3.listBuckets()" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.S3Service.lambda$getAllBuckets$2(S3Service.java:155)
        //       at java.base/java.util.stream.ReferencePipeline$7$1.accept(ReferencePipeline.java:273)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:960)
        //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:934)
        //       at java.base/java.util.stream.AbstractTask.compute(AbstractTask.java:327)
        //       at java.base/java.util.concurrent.CountedCompleter.exec(CountedCompleter.java:754)
        //       at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:373)
        //       at java.base/java.util.concurrent.ForkJoinTask.invoke(ForkJoinTask.java:686)
        //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateParallel(ReduceOps.java:927)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:233)
        //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.S3Service.getAllBuckets(S3Service.java:205)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonS3, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonS3, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        ImmutablePair<AmazonS3, String> nullPairResult2 = ImmutablePair.nullPair();
        pairList.add(nullPairResult2);
        when(utilityServices.getS3ClientsList()).thenReturn(pairList);
        s3Service.getAllBuckets();
    }

    /**
     * Method under test: {@link S3Service#getAllBuckets()}
     */
    @Test
    void testGetAllBuckets4() {
        when(utilityServices.getS3ClientsList()).thenThrow(new BucketNotFoundException("An error occurred"));
        assertThrows(BucketNotFoundException.class, () -> s3Service.getAllBuckets());
        verify(utilityServices).getS3ClientsList();
    }
}


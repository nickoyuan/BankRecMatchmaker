package com.xero.interview.bankrecmatchmaker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SubsetFinderUnitTest {
    @Test
    public void ableToGetSubSetFromTarget() {
        List<Integer> transactionAmount = Arrays.asList(1,2,3,4,5,6);
        int transactionSize = transactionAmount.size();
        int sumToFind = 10;

        ArrayList<Integer> positionInArray = new SubsetSumFinder().getSubset(
            transactionAmount,
            transactionSize,
            sumToFind
        );
        List<Integer> positionAnswer = Arrays.asList(4,3,2,1);

        assertEquals(positionInArray, positionAnswer);
    }

    @Test
    public void shouldNotReturnAnySubsetValues() {
        List<Integer> transactionAmount = Arrays.asList(1,2,3,4,5,6);
        int transactionSize = transactionAmount.size();
        int sumToFind = 100;

        ArrayList<Integer> positionInArray = new SubsetSumFinder().getSubset(
            transactionAmount,
            transactionSize,
            sumToFind
        );

        assertEquals(positionInArray, null);
    }

}

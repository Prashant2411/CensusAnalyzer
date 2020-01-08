package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INCORRECT_INDIA_CENSUS_CSV_FILE_PATH="./src/test/resources/IndiaStateCensusData";
    private static final String INCORRECT_FILE="./src/test/resources/IncorrectData.csv";
    private static final String INDIA_STATE_CODE = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_INDIA_STATE_CODE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INCORRECT_INDIA_STATE_CODE = "./src/test/resources/IndiaStateCode";
    private static final String INCORRECT_STATE_CODE_FILE="./src/test/resources/IncorrectStateCode.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidDelimiterInCSV_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INCORRECT_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenInvalidCSVFile_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INCORRECT_INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidHeaderInCSV_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INCORRECT_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenIndianStateCodeReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCode_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCode(WRONG_INDIA_STATE_CODE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidDelimiterInStateCode_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCode(INCORRECT_STATE_CODE_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenInvalidStateCodeFile_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCode(INCORRECT_INDIA_STATE_CODE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidHeaderInStateCode_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCode(INCORRECT_STATE_CODE_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenCSVCensus_shouldSort_returnsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            List list = censusAnalyser.getSortedCensus(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertTrue(list.get(28).toString().contains("West Bengal"));
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCSVCensus_shouldSort_returnsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            List list = censusAnalyser.getSortedCensus(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertTrue(list.get(0).toString().contains("Andhra Pradesh"));
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCSVState_shouldSort_returnsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            List list = censusAnalyser.getSortedState(INDIA_STATE_CODE);
            Assert.assertTrue(list.get(36).toString().contains("West Bengal"));
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCSVState_shouldSort_returnsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            List list = censusAnalyser.getSortedState(INDIA_STATE_CODE);
            Assert.assertTrue(list.get(0).toString().contains("Andaman and Nicobar Islands"));
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }




}
package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String list = censusAnalyser.getSortedIndiaCensusData();
            IndiaCensusCSV[] array = new Gson().fromJson(list,IndiaCensusCSV[].class);
            Assert.assertEquals("West Bengal",array[28].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenCSVCensus_shouldSort_returnsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String list = censusAnalyser.getSortedIndiaCensusData();
            IndiaCensusCSV[] array = new Gson().fromJson(list,IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh",array[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenCSVState_shouldSort_returnsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_STATE_CODE);
            String list = censusAnalyser.getSortedStateCodeData();
            CSVStates[] array = new Gson().fromJson(list,CSVStates[].class);
            Assert.assertEquals("West Bengal",array[36].stateName);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenCSVState_shouldSort_returnsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_STATE_CODE);
            String list = censusAnalyser.getSortedStateCodeData();
            CSVStates[] array = new Gson().fromJson(list,CSVStates[].class);
            Assert.assertEquals("Andhra Pradesh New",array[0].stateName);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileToSorting_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            censusAnalyser.loadIndiaCensusData(INCORRECT_INDIA_CENSUS_CSV_FILE_PATH);
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.getSortedIndiaCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidDelimiterInCSV_toSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INCORRECT_FILE);
            censusAnalyser.getSortedIndiaCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenInvalidCSVFile_toSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INCORRECT_INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.getSortedIndiaCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidHeaderInCSV_toSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INCORRECT_FILE);
            censusAnalyser.getSortedIndiaCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenIndiaStateCode_WithWrongFileToSorting_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INCORRECT_INDIA_STATE_CODE);
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.getSortedStateCodeData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidDelimiterInStateCode_ToSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INCORRECT_STATE_CODE_FILE);
            censusAnalyser.getSortedStateCodeData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenInvalidStateCodeFile_ToSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INCORRECT_INDIA_STATE_CODE);
            censusAnalyser.getSortedStateCodeData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidHeaderInStateCode_ToSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INCORRECT_STATE_CODE_FILE);
            censusAnalyser.getSortedStateCodeData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenGivenNull_ToStateCodeSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String list = censusAnalyser.getSortedStateCodeData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NULL_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenGivenNull_ToCensusDataSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String list = censusAnalyser.getSortedIndiaCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NULL_EXCEPTION,e.type);
        }

    }
}
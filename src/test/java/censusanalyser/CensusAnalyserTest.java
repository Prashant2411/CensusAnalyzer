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

    private static final String US_CENSUS_DATA = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
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
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidDelimiterInCSV_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void given37InvalidCSVFile_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidHeaderInCSV_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenIndianStateCodeReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

//    @Test
//    public void givenIndiaStateCode_WithWrongFile_ShouldThrowException() {
//        try {
//            CensusAnalyser censusAnalyser = new CensusAnalyser();
//            ExpectedException exceptionRule = ExpectedException.none();
//            exceptionRule.expect(CensusAnalyserException.class);
//            censusAnalyser.loadIndiaStateCode(WRONG_INDIA_STATE_CODE_PATH);
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
//        }
//    }
//
//    @Test
//    public void givenInvalidDelimiterInStateCode_shouldReturnsException() {
//        try {
//            CensusAnalyser censusAnalyser = new CensusAnalyser();
//            censusAnalyser.loadIndiaStateCode(INCORRECT_STATE_CODE_FILE);
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
//        }
//    }
//
    @Test
    public void givenInvalidStateCodeFile_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INCORRECT_INDIA_STATE_CODE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidHeaderInStateCode_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INCORRECT_STATE_CODE_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenCSVCensus_shouldSort_returnsLastElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
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
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
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
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
            String list = censusAnalyser.getSortedStateCodeData();
            System.out.println("List"+list);
            CensusDataDAO[] array = new Gson().fromJson(list,CensusDataDAO[].class);
            System.out.println("Array"+array);
            Assert.assertEquals("WB",array[28].State_Id);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCSVState_shouldSort_returnsFirstElement() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INCORRECT_INDIA_STATE_CODE);
            String list = censusAnalyser.getSortedStateCodeData();
            CensusDataDAO[] array = new Gson().fromJson(list,CensusDataDAO[].class);
            Assert.assertEquals("AP",array[0].State_Id);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileToSorting_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_INDIA_CENSUS_CSV_FILE_PATH);
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
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_FILE);
            censusAnalyser.getSortedIndiaCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

    @Test
    public void givenInvalidCSVFile_toSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.getSortedIndiaCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenInvalidHeaderInCSV_toSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_FILE);
            censusAnalyser.getSortedIndiaCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
        }
    }

//    @Test
//    public void givenIndiaStateCode_WithWrongFileToSorting_ShouldThrowException() {
//        try {
//            CensusAnalyser censusAnalyser = new CensusAnalyser();
//            ExpectedException exceptionRule = ExpectedException.none();
//            int numOfRecords = censusAnalyser.loadIndiaCensusData(INCORRECT_INDIA_STATE_CODE);
//            exceptionRule.expect(CensusAnalyserException.class);
//            censusAnalyser.getSortedStateCodeData();
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
//        }
//    }
//
//    @Test
//    public void givenInvalidDelimiterInStateCode_ToSorting_shouldReturnsException() {
//        try {
//            CensusAnalyser censusAnalyser = new CensusAnalyser();
//            int numOfRecords = censusAnalyser.loadIndiaCensusData(INCORRECT_STATE_CODE_FILE);
//            censusAnalyser.getSortedStateCodeData();
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
//        }
//    }
//
//    @Test
//    public void givenInvalidStateCodeFile_ToSorting_shouldReturnsException() {
//        try {
//            CensusAnalyser censusAnalyser = new CensusAnalyser();
//            int numOfRecords = censusAnalyser.loadIndiaCensusData(INCORRECT_INDIA_STATE_CODE);
//            censusAnalyser.getSortedStateCodeData();
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
//        }
//    }
//
//    @Test
//    public void givenInvalidHeaderInStateCode_ToSorting_shouldReturnsException() {
//        try {
//            CensusAnalyser censusAnalyser = new CensusAnalyser();
//            int numOfRecords = censusAnalyser.loadIndiaCensusData(INCORRECT_STATE_CODE_FILE);
//            censusAnalyser.getSortedStateCodeData();
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT,e.type);
//        }
//    }
//
//    @Test
//    public void givenGivenNull_ToStateCodeSorting_shouldReturnsException() {
//        try {
//            CensusAnalyser censusAnalyser = new CensusAnalyser();
//            String list = censusAnalyser.getSortedStateCodeData();
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.NULL_EXCEPTION,e.type);
//        }
//    }

    @Test
    public void givenGivenNull_ToCensusDataSorting_shouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String list = censusAnalyser.getSortedIndiaCensusData();
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NULL_EXCEPTION,e.type);
        }

    }

    @Test
    public void givenCensusCSV_returnMostPopulated() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String list = censusAnalyser.getMostPopulatedState();
            IndiaCensusCSV[] array = new Gson().fromJson(list,IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh",array[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCensusCSV_returnLeastPopulated() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String list = censusAnalyser.getMostPopulatedState();
            IndiaCensusCSV[] array = new Gson().fromJson(list,IndiaCensusCSV[].class);
            Assert.assertEquals("Sikkim",array[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCensusCSV_returnMostDenselyPopulatedState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String list = censusAnalyser.getDensityInSortedFormat();
            IndiaCensusCSV[] array = new Gson().fromJson(list,IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar",array[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCensusCSV_returnLeastDenselyPopulatedState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String list = censusAnalyser.getDensityInSortedFormat();
            IndiaCensusCSV[] array = new Gson().fromJson(list,IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh",array[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCensusCSV_returnLargestState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String list = censusAnalyser.getSortedAreaWise();
            IndiaCensusCSV[] array = new Gson().fromJson(list,IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan",array[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCensusCSV_returnSmallestState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String list = censusAnalyser.getSortedAreaWise();
            System.out.println(list);
            IndiaCensusCSV[] array = new Gson().fromJson(list,IndiaCensusCSV[].class);
            Assert.assertEquals("Goa",array[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_DATA);
            Assert.assertEquals(51,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
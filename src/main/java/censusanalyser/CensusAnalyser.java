package censusanalyser;

import com.google.gson.Gson;
import csvBuilder.CSVBuilderException;
import csvBuilder.CSVBuilderFactory;
import csvBuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Files.newBufferedReader;


public class CensusAnalyser {
    List<IndianCensusDAO> censusList = null; //For DAO-Data Access Object
    List<IndiaCensusCSV> censusCSVList = null;
    List<CSVStatesDAO> stateCodeList = null; //For DAO-Data Access Object
    List<CSVStates> stateCSVList = null;
    List<USCensusDAO> usCensusList = null; //For DAO-Data Access Object
    List<USCensus> usCensusCSVList = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndianCensusDAO>();
        this.stateCodeList = new ArrayList<CSVStatesDAO>();
        this.usCensusList = new ArrayList<USCensusDAO>();

    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileInList(reader, IndiaCensusCSV.class);
            for (int i=0;i<censusCSVList.size();i++)
                this.censusList.add(new IndianCensusDAO(censusCSVList.get(i)));
            return censusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCSVList = csvBuilder.getCSVFileInList(reader, CSVStates.class);
            for (int i=0;i<stateCSVList.size();i++)
                this.stateCodeList.add(new CSVStatesDAO(stateCSVList.get(i)));
            return stateCodeList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }

    }

    public String getSortedIndiaCensusData() throws CensusAnalyserException {
        try {
            if(censusList.size()==0 || censusList == null)
                throw new CensusAnalyserException("Invalid File",
                        CensusAnalyserException.ExceptionType.NULL_EXCEPTION);
            censusList = censusList.stream().sorted(Comparator.comparing(IndianCensusDAO::getStateDAO)).collect(Collectors.toList());
            String censusCodeJson = new Gson().toJson(this.censusList);
            return censusCodeJson;
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        }
    }

    public String getSortedStateCodeData() throws CensusAnalyserException {
        try {
            if(stateCodeList.size()==0 || stateCodeList == null)
                throw new CensusAnalyserException("Invalid File",
                        CensusAnalyserException.ExceptionType.NULL_EXCEPTION);
            stateCodeList = stateCodeList.stream().sorted(Comparator.comparing(CSVStatesDAO::getStateCodeDAO)).collect(Collectors.toList());
            String stateCodeJson = new Gson().toJson(this.stateCodeList);
            return stateCodeJson;
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        }
    }

    public String getMostPopulatedState() throws CensusAnalyserException {
        try {
            if(censusList.size()==0 || censusList == null)
                throw new CensusAnalyserException("Invalid File",
                        CensusAnalyserException.ExceptionType.NULL_EXCEPTION);
            censusList = censusList.stream().sorted(Comparator.comparing(IndianCensusDAO::getPopulationDAO).reversed()).collect(Collectors.toList());
            String censusJson = new Gson().toJson(this.censusList);
            return censusJson;
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        }
    }

    public String getDensityInSortedFormat() throws CensusAnalyserException {
        try {
            if(censusList.size()==0 || censusList == null)
                throw new CensusAnalyserException("Invalid File",
                        CensusAnalyserException.ExceptionType.NULL_EXCEPTION);
            censusList = censusList.stream().sorted(Comparator.comparing(IndianCensusDAO::getDensityPerSqKmDAO).reversed()).collect(Collectors.toList());
            String censusJson = new Gson().toJson(this.censusList);
            return censusJson;
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        }
    }

    public String getSortedAreaWise() throws CensusAnalyserException {
        try {
            if(censusList.size()==0 || censusList == null)
                throw new CensusAnalyserException("Invalid File",
                        CensusAnalyserException.ExceptionType.NULL_EXCEPTION);
            censusList = censusList.stream().sorted(Comparator.comparing(IndianCensusDAO::getAreaInSqkmDAO).reversed()).collect(Collectors.toList());
            String censusJson = new Gson().toJson(this.censusList);
            return censusJson;
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        }
    }

    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            usCensusCSVList = csvBuilder.getCSVFileInList(reader, USCensus.class);
            for (int i = 0; i < usCensusCSVList.size(); i++)
                this.usCensusList.add(new USCensusDAO(usCensusCSVList.get(i)));
            return usCensusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
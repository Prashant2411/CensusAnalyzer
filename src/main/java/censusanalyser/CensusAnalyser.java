package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Files.newBufferedReader;


public class CensusAnalyser {
    Map<String, CensusDataDAO> censusMap = new HashMap<>(); //For DAO-Data Access Object

    public enum Country{
        INDIA, US
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
        return censusMap.size();
    }

    public String getSortedIndiaCensusData() throws CensusAnalyserException {
        try {
            if(censusMap.size()==0 || censusMap == null)
                throw new CensusAnalyserException("Invalid File",
                        CensusAnalyserException.ExceptionType.NULL_EXCEPTION);
            List<CensusDataDAO> list = censusMap.values().stream().collect(Collectors.toList());
            list = list.stream().sorted(Comparator.comparing(CensusDataDAO::getStateDAO)).collect(Collectors.toList());
            String censusCodeJson = new Gson().toJson(list);
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
            if(censusMap.size()==0 || censusMap == null)
                throw new CensusAnalyserException("Invalid File",
                        CensusAnalyserException.ExceptionType.NULL_EXCEPTION);
            List<CensusDataDAO> list = censusMap.values().stream().collect(Collectors.toList());
            list = list.stream().sorted(Comparator.comparing(CensusDataDAO::getStateIDDAO)).collect(Collectors.toList());
            String stateCodeJson = new Gson().toJson(list);
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
            if(censusMap.size()==0 || censusMap == null)
                throw new CensusAnalyserException("Invalid File",
                        CensusAnalyserException.ExceptionType.NULL_EXCEPTION);
            List<CensusDataDAO> list = censusMap.values().stream().collect(Collectors.toList());
            list = list.stream().sorted(Comparator.comparing(CensusDataDAO::getPopulationDAO).reversed()).collect(Collectors.toList());
            System.out.println(list);
            String censusJson = new Gson().toJson(list);
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
            if(censusMap.size()==0 || censusMap == null)
                throw new CensusAnalyserException("Invalid File",
                        CensusAnalyserException.ExceptionType.NULL_EXCEPTION);
            List<CensusDataDAO> list = censusMap.values().stream().collect(Collectors.toList());
            list = list.stream().sorted(Comparator.comparing(CensusDataDAO::getDensityPerSqKmDAO).reversed()).collect(Collectors.toList());
            String censusJson = new Gson().toJson(list);
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
            if(censusMap.size()==0 || censusMap == null)
                throw new CensusAnalyserException("Invalid File",
                        CensusAnalyserException.ExceptionType.NULL_EXCEPTION);
            List<CensusDataDAO> list = censusMap.values().stream().collect(Collectors.toList());
            list = list.stream().sorted(Comparator.comparing(CensusDataDAO::getAreaInSqkmDAO).reversed()).collect(Collectors.toList());
            String censusJson = new Gson().toJson(list);
            return censusJson;
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        }
    }
}
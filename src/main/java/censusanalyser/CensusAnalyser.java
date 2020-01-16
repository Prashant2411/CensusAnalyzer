package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;


public class CensusAnalyser {
    Map<String, CensusDataDAO> censusMap = new HashMap<>(); //For DAO-Data Access Object

    public Country country;

    public enum Country{
        INDIA, US
    }

    public CensusAnalyser() {
    }

    public CensusAnalyser(Country country) {
        this.country = country;
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
            List list = censusMap.values().stream()
                    .sorted((data1,data2)-> data1.state.compareTo(data2.state))
                    .map(census -> census.getCensusDTO(country))
                    .collect(Collectors.toList());
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
//            List<CensusDataDAO> list = censusMap.values().stream().collect(Collectors.toList());
//            list = list.stream().sorted(Comparator.comparing(CensusDataDAO::getStateIDDAO)).collect(Collectors.toList());
            List list = censusMap.values().stream()
                    .sorted((data1,data2)-> data1.State_Id.compareTo(data2.State_Id))
                    .map(census -> census.getCensusDTO(country))
                    .collect(Collectors.toList());
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
            List list = censusMap.values().stream()
                    .sorted((census1, census2) -> census1.population - census2.population > 0 ? -1: 1)
                    .map(census -> census.getCensusDTO(country))
                    .collect(toCollection(ArrayList::new));
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
            List list = censusMap.values().stream()
                    .sorted((census1, census2) -> (int) census1.densityPerSqKm - census2.densityPerSqKm > 0 ? -1: 1)
                    .map(census -> census.getCensusDTO(country))
                    .collect(toCollection(ArrayList::new));
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
            List list = censusMap.values().stream()
                    .sorted((census1, census2) -> (int) census1.areaInSqkm - census2.areaInSqkm > 0 ? -1: 1)
                    .map(census -> census.getCensusDTO(country))
                    .collect(toCollection(ArrayList::new));
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
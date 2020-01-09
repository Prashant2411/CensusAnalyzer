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
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStates> censusCSVList = csvBuilder.getCSVFileInList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();
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
            List<CSVStates> stateCSVList = csvBuilder.getCSVFileInList(reader, CSVStates.class);
            return stateCSVList.size();
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

    public String getSortedIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> arrayList = csvBuilder.getCSVFileInList(reader, IndiaCensusCSV.class);
            List<IndiaCensusCSV> listSorted = arrayList.stream().sorted(Comparator.comparing(IndiaCensusCSV::getState)).collect(Collectors.toList());
            String censusCodeJson = new Gson().toJson(listSorted);
            return censusCodeJson;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        }
        return null;
    }

    public String getSortedStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStates> arrayList = csvBuilder.getCSVFileInList(reader, CSVStates.class);
            List<CSVStates> listSorted = arrayList.stream().sorted(Comparator.comparing(CSVStates::getStateCode)).collect(Collectors.toList());
            String stateCodeJson = new Gson().toJson(listSorted);
            return stateCodeJson;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        }
        return null;
    }
}
package censusanalyser;

import csvBuilder.CSVBuilderException;
import csvBuilder.CSVBuilderFactory;
import csvBuilder.ICSVBuilder;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public JSONArray getSortedIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> arrayList = csvBuilder.getCSVFileInList(reader, IndiaCensusCSV.class);
            List<IndiaCensusCSV> listSorted = arrayList.stream().sorted(Comparator.comparing(IndiaCensusCSV::getState)).collect(Collectors.toList());
            JSONArray json = new JSONArray();
            for (int i =0; i< listSorted.size();i++)
                json.put(listSorted.get(i));
            return json;
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

    public JSONArray getSortedStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStates> arrayList = csvBuilder.getCSVFileInList(reader, CSVStates.class);
            List<CSVStates> listSorted = arrayList.stream().sorted(Comparator.comparing(CSVStates::getStateCode)).collect(Collectors.toList());
            JSONArray json = new JSONArray();
            for (int i =0; i< listSorted.size();i++)
                json.put(listSorted.get(i));
            return json;
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

    public void mapCSV(String csvPath)     {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvPath));
            Map<String, List<String>> map = new TreeMap<>();
            String line;
            JSONArray jsonArray = new JSONArray();
            while ((line = reader.readLine()) != null) {
                String key = line.split(",")[3];
                List<String> list = map.get(key);
                if(list==null) {
                    list = new LinkedList();
                    list.add(line);
                    map.put(key, list);
                }
                jsonArray.put(map.get(key));
            }
            System.out.println(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
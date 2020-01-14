package censusanalyser;

import csvBuilder.CSVBuilderException;
import csvBuilder.CSVBuilderFactory;
import csvBuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public class IndiaCensusAdapter extends CensusAdapter{

    List<CSVStatesDAO> stateCodeList = new ArrayList<CSVStatesDAO>();
    List<CSVStates> stateCSVList = new ArrayList<>();
    Map<String, CSVStatesDAO> stateCodeMap = new HashMap<>();
    Map<String, CensusDataDAO> censusMap = new HashMap<>();

    @Override
    public <E> Map<String, CensusDataDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        return null;
    }

    public <E> Map<String,CensusDataDAO> loadCensusData(Class<E> csvClass, String... csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(String.valueOf(csvFilePath[0])))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<E> censusCSVDataList = csvBuilder.getCSVFileInList(reader, csvClass);
            if (csvClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(censusCSVDataList.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusData -> censusMap.put(censusData.state, new CensusDataDAO(censusData)));
            } else if (csvClass.getName().equals("censusanalyser.USCensus")) {
                StreamSupport.stream(censusCSVDataList.spliterator(), false)
                        .map(USCensus.class::cast)
                        .forEach(censusData -> censusMap.put(censusData.State, new CensusDataDAO(censusData)));
            }
            if (csvFilePath.length == 1) return censusMap;
            return this.loadIndiaStateCode(csvFilePath[1], CSVStates.class);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA_FORMAT);
        }
        return null;
    }

    public Map<String, CensusDataDAO> loadIndiaStateCode(String csvFilePath, Class csvStatesClass) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCSVList = csvBuilder.getCSVFileInList(reader, csvStatesClass);
            StreamSupport.stream(stateCSVList.spliterator(),false)
                    .filter(csvStates -> censusMap.get(csvStates.stateName) != null)
                    .forEach(csvStates -> censusMap.get(csvStates.stateName).State_Id = csvStates.stateCode);
            return censusMap;
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
}

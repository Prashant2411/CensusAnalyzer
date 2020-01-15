package censusanalyser;

import csvBuilder.CSVBuilderException;
import csvBuilder.CSVBuilderFactory;
import csvBuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public abstract class CensusAdapter {
    public abstract <E> Map<String, CensusDataDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException;

    Map<String, CensusDataDAO> censusMap = new HashMap<>();
    public <E> Map<String,CensusDataDAO> loadCensusData(Class csvClass, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(String.valueOf(csvFilePath)))) {
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
            return censusMap;
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
}

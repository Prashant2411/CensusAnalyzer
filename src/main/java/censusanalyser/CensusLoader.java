package censusanalyser;

import csvBuilder.CSVBuilderException;
import csvBuilder.CSVBuilderFactory;
import csvBuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.newBufferedReader;

public class CensusLoader {

    List<CensusDataDAO> censusList = new ArrayList<>();

    public <E> List<CensusDataDAO> loadCensusData(String csvFilePath, Class<E> csvClass) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<E> censusCSVDataList = csvBuilder.getCSVFileInList(reader, csvClass);
            if (csvClass.getName().equals("censusanalyser.IndiaCensusCSV"))
                censusCSVDataList.stream().filter(censusData -> censusList.add(new CensusDataDAO((IndiaCensusCSV) censusData))).collect(Collectors.toList());
            else if (csvClass.getName().equals("censusanalyser.USCensus"))
                censusCSVDataList.stream().filter(censusData -> censusList.add(new CensusDataDAO((USCensus) censusData))).collect(Collectors.toList());
            censusCSVDataList.stream().filter(censusData -> censusList.add(new CensusDataDAO(csvClass,censusData))).collect(Collectors.toList());
            return this.censusList;
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

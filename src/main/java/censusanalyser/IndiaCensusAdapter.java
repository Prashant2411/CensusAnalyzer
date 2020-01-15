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

    List<CSVStates> stateCSVList = new ArrayList<>();
    Map<String, CSVStatesDAO> stateCodeMap = new HashMap<>();
    Map<String, CensusDataDAO> censusMap = new HashMap<>();

    @Override
    public Map<String, CensusDataDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDataDAO> censusStateMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        this.loadIndiaStateCode(censusStateMap, csvFilePath[1]);
        return censusStateMap;
    }

    public Map<String, CensusDataDAO> loadIndiaStateCode(Map<String, CensusDataDAO> censusStateMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCSVList = csvBuilder.getCSVFileInList(reader, CSVStates.class);
            StreamSupport.stream(stateCSVList.spliterator(),false)
                    .filter(csvStates -> censusStateMap.get(csvStates.stateName) != null)
                    .forEach(csvStates -> censusStateMap.get(csvStates.stateName).State_Id = csvStates.stateCode);
            return censusStateMap;
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

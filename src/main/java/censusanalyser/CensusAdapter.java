package censusanalyser;

import java.util.Map;

public abstract class CensusAdapter {
    public abstract <E> Map<String, CensusDataDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException;

}

package censusanalyser;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter{

    @Override
    public Map<String, CensusDataDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDataDAO> censusStateMap = super.loadCensusData(USCensus.class, csvFilePath[0]);
        return censusStateMap;
    }
}

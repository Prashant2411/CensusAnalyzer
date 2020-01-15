package censusanalyser;

import java.util.Map;

public class CensusAdapterFactory {
    public static Map<String, CensusDataDAO> getCensusData(CensusAnalyser.Country country, String... csvFile) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFile);
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFile);
        throw new CensusAnalyserException("No Such Country in Database",CensusAnalyserException.ExceptionType.NO_SUCH_COUNTRY);
    }
}

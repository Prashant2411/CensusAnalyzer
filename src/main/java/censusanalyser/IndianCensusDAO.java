package censusanalyser;

public class IndianCensusDAO {
    private int areaInSqkm;
    private String state;
    private int population;
    private int densityPerSqKm;

    public IndianCensusDAO(IndiaCensusCSV indianCensusCSV) {
    state = indianCensusCSV.state;
    areaInSqkm = indianCensusCSV.areaInSqKm;
    population = indianCensusCSV.population;
    densityPerSqKm = indianCensusCSV.densityPerSqKm;
    }

    public String getStateDAO() {
        return state;
    }

    public int getPopulationDAO() {
        return population;
    }
}

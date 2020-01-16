package censusanalyser;

public class CensusDataDAO {
    public double areaInSqkm;
    public String state;
    public int population;
    public double densityPerSqKm;
    public double Housing_Density;
    public double Land_area;
    public double Water_area;
    public double Housing_units;
    public String State_Id;

    public CensusDataDAO(IndiaCensusCSV indianCensusCSV) {
        state = indianCensusCSV.state;
        areaInSqkm = indianCensusCSV.areaInSqKm;
        population = indianCensusCSV.population;
        densityPerSqKm = indianCensusCSV.densityPerSqKm;
    }

    public CensusDataDAO() {
    }

    public CensusDataDAO(USCensus usCensus) {
        state = usCensus.State;
        areaInSqkm = usCensus.Total_area;
        population = usCensus.Population;
        densityPerSqKm = usCensus.Population_Density;
        Housing_Density = usCensus.Housing_Density;
        Land_area = usCensus.Land_area;
        Water_area = usCensus.Water_area;
        Housing_units = usCensus.Housing_units;
        State_Id = usCensus.State_Id;
    }

    public String getStateDAO() {
        return state;
    }

    public int getPopulationDAO() {
        return population;
    }

    public int getDensityPerSqKmDAO() {
        return (int) densityPerSqKm;
    }

    public int getAreaInSqkmDAO() {
        return (int) areaInSqkm;
    }

    public String getStateIDDAO() {
        return State_Id;
    }

    public Object getCensusDTO(CensusAnalyser.Country country){
        if(country.equals(CensusAnalyser.Country.US))
            return new USCensus(state, State_Id, population, densityPerSqKm, areaInSqkm);
        return new IndiaCensusCSV(state, population, densityPerSqKm, areaInSqkm);
    }
}

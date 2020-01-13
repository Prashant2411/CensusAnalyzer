package censusanalyser;

import java.util.List;

public class CensusDataDAO {
    private int areaInSqkm;
    private String state;
    private int population;
    private int densityPerSqKm;
    public double Housing_Density;
    public double Land_area;
    public double Water_area;
    public int Housing_units;
    public String State_Id;

    public CensusDataDAO(IndiaCensusCSV indianCensusCSV) {
    state = indianCensusCSV.state;
    areaInSqkm = indianCensusCSV.areaInSqKm;
    population = indianCensusCSV.population;
    densityPerSqKm = indianCensusCSV.densityPerSqKm;
    }

    public CensusDataDAO(USCensus usCensus) {
        state = usCensus.State;
        areaInSqkm = (int) usCensus.Total_area;
        population = usCensus.Population;
        densityPerSqKm = (int) usCensus.Population_Density;
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
        return densityPerSqKm;
    }

    public int getAreaInSqkmDAO() {
        return areaInSqkm;
    }


}

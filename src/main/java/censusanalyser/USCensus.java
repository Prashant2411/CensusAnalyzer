package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensus {
    @CsvBindByName(column = "State_Id", required = true)
    public String State_Id;

    @CsvBindByName(column = "State", required = true)
    public String State;

    @CsvBindByName(column = "Population", required = true)
    public int Population;

    @CsvBindByName(column = "Housing_units", required = true)
    public int Housing_units;

    @CsvBindByName(column = "Total_area", required = true)
    public double Total_area;

    @CsvBindByName(column = "Water_area", required = true)
    public double Water_area;

    @CsvBindByName(column = "Land_area", required = true)
    public double Land_area;

    @CsvBindByName(column = "Population_Density", required = true)
    public double Population_Density;

    @CsvBindByName(column = "Housing_Density", required = true)
    public double Housing_Density;

    public USCensus(String state, String state_id, int population, double densityPerSqKm, double areaInSqkm) {
        this.State=state;
        this.State_Id=state_id;
        this.Population=population;
        this.Population_Density=densityPerSqKm;
        this.Total_area=areaInSqkm;
    }

    public USCensus() {
    }
}

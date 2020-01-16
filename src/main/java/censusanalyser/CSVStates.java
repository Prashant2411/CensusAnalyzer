package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStates {
    @CsvBindByName(column = "SrNo", required = true)
    public int srNo;
    @CsvBindByName(column = "StateName", required = true)
    public String stateName;
    @CsvBindByName(column = "TIN", required = true)
    public int tin;
    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;
    @Override
    public String toString() {
        return "{" +
                "SrNo='" + srNo + '\'' +
                ", StateName='" + stateName + '\'' +
                ", TIN='" + tin + '\'' +
                ", StateCode='" + stateCode + '\'' +
                '}';
    }

    public CSVStates() {
    }

    public String getStateCode() {
        return stateCode;
    }

}

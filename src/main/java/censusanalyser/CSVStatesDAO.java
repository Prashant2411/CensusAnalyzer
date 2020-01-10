package censusanalyser;

public class CSVStatesDAO {
    public int srNo;
    public String stateName;
    public int tin;
    public String stateCode;

    public CSVStatesDAO(CSVStates csvStates) {
        this.srNo = csvStates.srNo;
        this.stateName = csvStates.stateName;
        this.tin = csvStates.tin;
        this.stateCode = csvStates.stateCode;
    }

    public String getStateCodeDAO() {
        return stateCode;
    }
}

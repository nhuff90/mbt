package org.ta4j.core.analysis;

import java.util.List;

public class ResultsAnalysisWithRowNameList {
    List<ResultsAnalysisWithRowName> resultsAnalysisWithRowNameList;
    List<String> columnHeaders;

    public ResultsAnalysisWithRowNameList(List<String> columnHeaders, List<ResultsAnalysisWithRowName> resultsAnalysisWithRowNameList) {
        this.resultsAnalysisWithRowNameList = resultsAnalysisWithRowNameList;
        this.columnHeaders = columnHeaders;
    }

    public List<ResultsAnalysisWithRowName> getResultsAnalysisWithRowNameList() {
        return resultsAnalysisWithRowNameList;
    }

    public void setResultsAnalysisWithRowNameList(List<ResultsAnalysisWithRowName> resultsAnalysisWithRowNameList) {
        this.resultsAnalysisWithRowNameList = resultsAnalysisWithRowNameList;
    }

    public List<String> getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(List<String> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public void print() {
        // Print headers
        System.out.print("Test Name,");
        for (int i = 0; i < columnHeaders.size(); i++) {
            if (i == columnHeaders.size()-1) {
            System.out.println(columnHeaders.get(i));
            } else {
                System.out.print(columnHeaders.get(i) + ",");
            }
        }

        // Print data
        for (ResultsAnalysisWithRowName resultsAnalysisWithRowName : resultsAnalysisWithRowNameList) {
            resultsAnalysisWithRowName.printCommaSeparated();
        }
    }
}

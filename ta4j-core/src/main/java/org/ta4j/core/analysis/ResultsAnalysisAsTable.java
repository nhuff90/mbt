package org.ta4j.core.analysis;

import java.util.List;

public class ResultsAnalysisAsTable {

    String tableName;
    ResultsAnalysisWithRowNameList resultsAnalysisWithRowNameList;

    public ResultsAnalysisAsTable(String tableName, List<String> columnHeaders, List<ResultsAnalysisWithRowName> resultsAnalysisWithRowNames) {
        this.tableName = tableName;
        this.resultsAnalysisWithRowNameList = new ResultsAnalysisWithRowNameList(columnHeaders, resultsAnalysisWithRowNames);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ResultsAnalysisWithRowNameList getResultsAnalysisWithRowNameList() {
        return resultsAnalysisWithRowNameList;
    }

    public void setResultsAnalysisWithRowNameList(ResultsAnalysisWithRowNameList resultsAnalysisWithRowNameList) {
        this.resultsAnalysisWithRowNameList = resultsAnalysisWithRowNameList;
    }

    public void print() {
        System.out.println();
        System.out.println(tableName);
        resultsAnalysisWithRowNameList.print();
        System.out.println();
    }
}

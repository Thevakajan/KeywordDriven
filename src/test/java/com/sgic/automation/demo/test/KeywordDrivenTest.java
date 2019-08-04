package com.sgic.automation.demo.test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sgic.automation.demo.util.DataStore;
import com.test.qa.keywords.KeywordExecution;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class KeywordDrivenTest {
    public static ExtentReports keywordReport = new ExtentReports(System.getProperty("user.dir") + "/src/test/resources/reports/KeywordReportResults.html");
    public static ExtentTest keywordTest;

    @BeforeClass
    public void setUp() {
        DataStore.readExcelFile("/src/test/resources/data/KeywordDrivenData.xlsx");
        DataStore.loadData();
    }

    //TestCaseNo	TestStep	Action	ElementLocator	InputData
    @Test
    public void keywordTest() {
        String testCaseNumber = "TC_001";
        ArrayList<String> testCaseNos = DataStore.testData.get("TestCaseNo");
        ArrayList<Integer> steps = new ArrayList<Integer>();
        for (int i = 0; i < testCaseNos.size(); i++) {
            if (testCaseNos.get(i).equalsIgnoreCase(testCaseNumber)) {
                steps.add(i);
            }
        }
        KeywordExecution keywordExecution = new KeywordExecution();
        keywordTest = keywordReport.startTest("Admin Login Test");
        for (int j = steps.get(0); j < steps.size() + steps.get(0); j++) {
            String methodName = DataStore.testData.get("Action").get(j);
            List<Object> paramsList = new ArrayList<Object>();
            if (!DataStore.testData.get("ElementLocator").get(j).isEmpty()) {
                paramsList.add(DataStore.testData.get("ElementLocator").get(j));
            }
            if (!DataStore.testData.get("InputData").get(j).isEmpty()) {
                paramsList.add(DataStore.testData.get("InputData").get(j));
            }
            Object[] inputArgs = new Object[paramsList.size()];
            inputArgs = paramsList.toArray();
            try {
                keywordExecution.runReflectionMethod("com.test.qa.keywords.Keywords",
                        methodName, inputArgs);
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            keywordTest.log(LogStatus.PASS, DataStore.testData.get("TestStep").get(j));
        }
        keywordReport.endTest(keywordTest);
    }


    @AfterClass
    public void tearDown() {
        keywordReport.flush();
        DataStore.clearData();
    }
}

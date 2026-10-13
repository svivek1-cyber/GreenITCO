package generic_utility;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ListenerImplementation implements ISuiteListener, ITestListener {
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Map<ITestResult, ExtentTest> tests = new ConcurrentHashMap<>();
    private ExtentReports report;
    private BufferedWriter reportWriter;

    @Override
    public void onStart(ISuite suite) {
        Path reportsDirectory = Paths.get("Reports");
        Path textReportPath = reportsDirectory.resolve("Report.txt");
        try {
            Files.createDirectories(reportsDirectory);
            reportWriter = Files.newBufferedWriter(
                    textReportPath,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not create the text report at " + textReportPath, e);
        }

        ExtentSparkReporter spark = new ExtentSparkReporter(reportsDirectory.resolve("Report.html").toString());
        spark.config().setDocumentTitle("Test Execution Report");
        spark.config().setReportName("Regression Test Report");
        spark.config().setTheme(Theme.DARK);

        report = new ExtentReports();
        report.attachReporter(spark);
        report.setSystemInfo("Tester", "Saurabh Kushwaha");
        report.setSystemInfo("OS", "Windows 11");

        writeLine("Test Execution Report");
        writeLine("Suite: " + suite.getName());
        writeLine("Started: " + LocalDateTime.now().format(TIMESTAMP_FORMAT));
        writeLine("");
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        tests.put(result, report.createTest(testName));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logResult(result, Status.PASS, "PASS", "Test case passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = getTest(result);
        Throwable throwable = result.getThrowable();
        test.log(Status.FAIL, "Test case failed");
        if (throwable != null) {
            test.fail(throwable);
        }

        WebDriver driver = BaseClass.sdriver;
        if (driver instanceof TakesScreenshot) {
            try {
                String screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                test.addScreenCaptureFromBase64String(screenshot);
            } catch (WebDriverException e) {
                test.log(Status.WARNING, "Screenshot could not be captured: " + e.getMessage());
            }
        }
        writeResult(result, "FAIL");
        tests.remove(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logResult(result, Status.SKIP, "SKIP", "Test case skipped");
    }

    @Override
    public void onFinish(ISuite suite) {
        try {
            writeLine("");
            writeLine("Finished: " + LocalDateTime.now().format(TIMESTAMP_FORMAT));
        } finally {
            try {
                if (report != null) {
                    report.flush();
                }
            } finally {
                closeTextReport();
            }
        }
    }

    private void logResult(ITestResult result, Status status, String textStatus, String message) {
        getTest(result).log(status, message);
        writeResult(result, textStatus);
        tests.remove(result);
    }

    private ExtentTest getTest(ITestResult result) {
        ExtentTest test = tests.get(result);
        if (test == null) {
            throw new IllegalStateException("No report entry was created for "
                    + result.getMethod().getMethodName());
        }
        return test;
    }

    private void writeResult(ITestResult result, String status) {
        String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        long durationMillis = result.getEndMillis() - result.getStartMillis();
        writeLine("[" + status + "] " + testName + " (" + durationMillis + " ms)");

        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            writeLine("  Details: " + throwable);
        }
    }

    private synchronized void writeLine(String line) {
        if (reportWriter == null) {
            throw new IllegalStateException("The text report has not been initialized.");
        }
        try {
            reportWriter.write(line);
            reportWriter.newLine();
            reportWriter.flush();
        } catch (IOException e) {
            throw new UncheckedIOException("Could not write to the text report.", e);
        }
    }

    private synchronized void closeTextReport() {
        if (reportWriter == null) {
            return;
        }
        try {
            reportWriter.close();
        } catch (IOException e) {
            throw new UncheckedIOException("Could not close the text report.", e);
        } finally {
            reportWriter = null;
        }
    }
}

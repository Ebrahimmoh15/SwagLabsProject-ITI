// java
package CustomListeners;

import org.testng.*;
import utils.AllureUtlis;

public class TestNGListeners implements IInvokedMethodListener, IExecutionListener, ITestListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Before invoking: " + method.getTestMethod().getMethodName() + " started");
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("After invoking: " + method.getTestMethod().getMethodName() + " ended");
    }

    @Override
    public void onExecutionStart() {
        System.out.println("Test execution started");
     //   PropertyReader.loadProperties();
        AllureUtlis.AllureCleanResult();
    }

    @Override
    public void onExecutionFinish() {
        System.out.println("Test execution finished");
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test " + result.getName() + " started.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test " + result.getName() + " passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test " + result.getName() + " failed.");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test " + result.getName() + " skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test " + result.getName() + " failed but within success percentage.");
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Suite/Context " + context.getName() + " started.");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Suite/Context " + context.getName() + " finished.");
    }
}

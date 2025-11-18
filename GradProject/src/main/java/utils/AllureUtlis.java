package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class AllureUtlis {

    public static  void AllureCleanResult ()
    {
        FileUtils.deleteQuietly( new File("test-output/allure-results"));
    }
}

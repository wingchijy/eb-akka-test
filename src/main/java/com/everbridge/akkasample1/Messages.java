package com.everbridge.akkasample1;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;


public final class Messages
{
    private Messages() {}

    public static class Init {}

    public static class MailType {}
    public static class PhoneType {}
    public static class AppType {}
    public static class FaxType {}


    public static int MessageAmount = 0;

    public static String DemoMessage;

    // Read demo file data -> message.
    public static void getDemoMessage()
    {
        try{
            File file = new File(Config.MESSAGE_TEST_FILE);
            DemoMessage = FileUtils.readFileToString(file);

            System.out.println( "Demo message length=" +DemoMessage.length() );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

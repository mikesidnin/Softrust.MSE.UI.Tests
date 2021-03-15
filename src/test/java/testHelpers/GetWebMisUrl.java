package testHelpers;

import static com.codeborne.selenide.Selenide.open;

public class GetWebMisUrl {
    public static void openURLWebMis() {

        String ipAddress = "http://192.168.7.54/",
                relativePath = "mis/test2/";

        open(ipAddress + relativePath);
    }
}

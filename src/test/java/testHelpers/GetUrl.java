package testHelpers;

import static com.codeborne.selenide.Selenide.open;

public class GetUrl {

    public static String openURLMseJournal(String docPrvdId) {
        String ipAddress = "http://109.95.224.42:2165/",
                relativePath = "test2/mse/log",
                ticket = "ticket=D9VQnls2TN%2B2s%2FwzBNicUCtcrH1JNeDL6%2BRSxXP6jeJ%2FhYi6e%2FnGu13NyHHvOVBmmP%2BETKS%2FoKu%2FQraiIvDFoVWFUFZEhXMbiAauqiPVXFVP6vTnUOFTt49dWUKrLJu9qQ9jKZrXXXi%2Fv6VkaxQMVqcjfkV2ctNH5UXIdnUymK2FwDrwrUpwEtwKG9yrqvOnTwFM7NNxX%2BzH3lZd1sKNgRRnk1M4GqLT3uJFQ0Tkif%2BxaflrVRtMqRMen58nmCVjM%2FL0b4dFxdL7Yvlbyb5OvlP2qnf%2F5yfz9%2BfhQXSjiK5NMlmnYlwlEiae%2BhdLY2jxvxjjknDJwxIXxmrRvbt7jq1thpE%3D",
                docPrvdIdPart = "DocPrvdId=" + docPrvdId,
                misUrl = "MisUrl=http:%2F%2F192.168.7.54%2Fmis%2Ftest2",
                returnUrl = "ReturnUrl=http:%2F%2F192.168.7.54%2Fmis%2Ftest2%2FMain%2FDefault";

        return ipAddress + relativePath + "?" + ticket+ "&" + docPrvdIdPart + "&" + misUrl + "&" + returnUrl;
    }

    public static String openURLDirectionMse(String mkabId, String tapId, String directionId, String docPrvdId) {

        String ipAddress = "http://109.95.224.42:2165/",
                relativePath = "test2/mse/examination/edit/",
                mkabIdPart = mkabId,
                tapIdPart = tapId,
                directionIdPart = directionId,
                ticket = "ticket=D9VQnls2TN%2B2s%2FwzBNicUCtcrH1JNeDL6%2BRSxXP6jeJ%2FhYi6e%2FnGu13NyHHvOVBmmP%2BETKS%2FoKu%2FQraiIvDFoVWFUFZEhXMbiAauqiPVXFVP6vTnUOFTt49dWUKrLJu9qQ9jKZrXXXi%2Fv6VkaxQMVqcjfkV2ctNH5UXIdnUymK2FwDrwrUpwEtwKG9yrqvOnTwFM7NNxX%2BzH3lZd1sKNgRRnk1M4GqLT3uJFQ0Tkif%2BxaflrVRtMqRMen58nmCVjM%2FL0b4dFxdL7Yvlbyb5OvlP2qnf%2F5yfz9%2BfhQXSjiK5NMlmnYlwlEiae%2BhdLY2jxvxjjknDJwxIXxmrRvbt7jq1thpE%3D",
                docPrvdIdPart = "DocPrvdId=" + docPrvdId;

        return ipAddress + relativePath + mkabIdPart + "/" + tapIdPart + "/" + directionIdPart + "?" + ticket + "&" + docPrvdIdPart;
    }

    public static void openURLWebMis() {

        String ipAddress = "http://192.168.7.54/",
                relativePath = "mis/test2/";

        open(ipAddress + relativePath);
    }
}

package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class AmazonService {
  public static final Pattern PRODUCT_PATTERN = Pattern.compile("<span class=\\\"a-size-medium a-color-base a-text-normal\\\">([^<]+)<\\/span>.*<span class=\\\"a-icon-alt\\\">([^<]+)<\\/span>.*href=\\\"([^?]+)\\?.*<span class=\\\"a-price\\\" data-a-size=\\\"xl\\\" data-a-color=\\\"base\\\"><span class=\\\"a-offscreen\\\">([^<]+)<\\/span>");

    public String searchProducts(String keyword) throws IOException {
        return parseProductHtml(getProductHtml(keyword));
    }

    private String parseProductHtml(String html) {
        String res = "";
        Matcher matcher = PRODUCT_PATTERN.matcher(html);
        int i = 0;
        while (matcher.find()) {
            res += matcher.group(1) + " - " + matcher.group(2) + ", Price: " + matcher.group(4) + ", Link: https://www.amazon.com" + matcher.group(3) +"\n";
            i++;
            if (i == 5)
                break;
        }
        return res;
    }

    private String getProductHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://www.amazon.com/s?i=aps&k=" + keyword + "&ref=nb_sb_noss&url=search-alias%3Daps")
                .method("GET", null)
                .addHeader("authority", "www.amazon.com")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cookie", "aws-target-data=%7B%22support%22%3A%221%22%7D; aws-target-visitor-id=1696684434822-954663.44_0; aws-ubid-main=345-0738252-2428820; remember-account=true; aws-account-alias=995553441267; regStatus=registered; AMCV_7742037254C95E840A4C98A6%40AdobeOrg=1585540135%7CMCIDTS%7C19638%7CMCMID%7C12624985385616467882944299711568303024%7CMCAAMLH-1697300493%7C7%7CMCAAMB-1697300493%7C6G1ynYcLPuiQxYZrsz_pkqfLG9yMXBpb2zX5dvJdYQJzPXImdj0y%7CMCOPTOUT-1696702893s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C4.4.0; aws-userInfo=%7B%22arn%22%3A%22arn%3Aaws%3Aiam%3A%3A995553441267%3Auser%2Fidok%22%2C%22alias%22%3A%22995553441267%22%2C%22username%22%3A%22idok%22%2C%22keybase%22%3A%22CpxF6pPWFClB%2FthxVS6znb3UFLG4NsC7EVREELx1oao%5Cu003d%22%2C%22issuer%22%3A%22http%3A%2F%2Fsignin.aws.amazon.com%2Fsignin%22%2C%22signinType%22%3A%22PUBLIC%22%7D; aws-userInfo-signed=eyJ0eXAiOiJKV1MiLCJrZXlSZWdpb24iOiJ1cy1lYXN0LTIiLCJhbGciOiJFUzM4NCIsImtpZCI6IjU1MWQxODhiLWI3NGItNGNhMi05ZjY1LWY1YjdhZjIyNTVhMCJ9.eyJzdWIiOiI5OTU1NTM0NDEyNjciLCJzaWduaW5UeXBlIjoiUFVCTElDIiwiaXNzIjoiaHR0cDpcL1wvc2lnbmluLmF3cy5hbWF6b24uY29tXC9zaWduaW4iLCJrZXliYXNlIjoiQ3B4RjZwUFdGQ2xCXC90aHhWUzZ6bmIzVUZMRzROc0M3RVZSRUVMeDFvYW89IiwiYXJuIjoiYXJuOmF3czppYW06Ojk5NTU1MzQ0MTI2Nzp1c2VyXC9pZG9rIiwidXNlcm5hbWUiOiJpZG9rIn0.K41bpA1_zO_Z7mJiME5MKmMr9eOmJkr8gvKY8DeqhTdd4ONcGWtcn9Lqf2jFzaCMSeRtwYlzYaOsLpRuRdo0mWb7cST0cQfKZRgF4aA3OXeOAZtStdCVAcNym1nZ3plg; noflush_awsccs_sid=1c8bc07be7ecc25f89508dd76f188612f26a0f2bc2dc47beef68707a127363f5; session-id=147-8291361-4096533; session-id-time=2082787201l; i18n-prefs=USD; skin=noskin; ubid-main=130-5403748-3386164; JSESSIONID=3F0CB00E0A94BD65718C2D6BA4E0508F; session-token=rdQ5fTEEMzCyPACpJStEudjXJZ5nQs6z6iWZPoACGrET4JduY9hx1zdLc0YWEg46Kct7NFdAutWf5zMh2IF5hhqqTEuX2wS5NtFHI66ujtglIEnMNSWF8tQcMKAMYOiP/92zi1/u/p2277rGqA9fQVB5xzi5LsxiPiWzfqTZP3x1ydIuZtdGE/gU3A3Vc6N6YrAPAzXgiQjq+4KHWdVkkVerXPrbtuOfTAo/jgSuMKmXBCNsHPMnG+OdSb47TQ8Er92HPSnvH3yApHfqLe/bwK9uewKopbY7tsGZPcYY2D2bHG514+5AcJS222IbnbMEHJrihEawWScPHZIgJ7tb9PZcuukZa8kP; sp-cdn=\"L5Z9:IL\"; csm-hit=tb:SPEZQPTWDMACXXKPM4WR+s-1JPRYMKSSQ547H4ESY0S|1697652558315&t:1697652558315&adb:adblk_no; session-token=2v1ep3FaPxjTzvDNHuBUbyl3IeY9M+K4nNHBV2m9VczA8ggFRCzJA9kAXRStBQGHtQfortnptZR55zEw8LSvkf4FuB1Kuq15D9TO3AazSw8NyaV2+kzfUa6ihxbaVtYdXc8p7ZkfsK6YN72fj5wWRM28hk2xqOUxWA6lyC/fcZlaONnnPF6Vs/UbY2pqrXA/u7IiCyxfsU6n6u3qIBClNurfdltbWiGD9YgFIiNk4+tS42mWCC4we60CEEgrEvBFEaM19Dru4q0Xxww9fk7KNRkFiL8tF/ZkBoMekGZY+6MZadHVr8RZz+OOCDSATN6A8MzgvvCOgZZmR77cStZwDt/MjQfb+Nhm")
                .addHeader("device-memory", "8")
                .addHeader("downlink", "10")
                .addHeader("dpr", "1")
                .addHeader("ect", "4g")
                .addHeader("referer", "https://www.amazon.com/s?k=ipod&ref=nb_sb_noss")
                .addHeader("rtt", "50")
                .addHeader("sec-ch-device-memory", "8")
                .addHeader("sec-ch-dpr", "1")
                .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .addHeader("sec-ch-viewport-width", "1179")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .addHeader("viewport-width", "1179")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}

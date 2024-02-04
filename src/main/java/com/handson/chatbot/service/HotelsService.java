package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


@Service
public class HotelsService {
    public static final Pattern HOTEL_PATTERN = Pattern.compile("\\);\\\"><span>([^<]+)<\\/span>[^>]+>[^/]+/([^#]+)[^>]+>[^>]+><span class=\\\"[^\\\"]+\\\"alt='([a-zA-Z0-9 ]+)[^>]+[^;]+[^>]+>([^<]+)<\\/a>");


    public String searchHotels(String keyword) throws IOException {
        return parseHotelHtml(getHotelHtml(keyword));
    }

    private String parseHotelHtml(String html) {
        String res = "";
        Matcher matcher = HOTEL_PATTERN.matcher(html);
        int i = 0;
        while (matcher.find()) {
            res += matcher.group(1) + ", Rating - " + matcher.group(3) + ", Reviews: " + matcher.group(4) + ", Link: https://www.tripadvisor.com/" + matcher.group(2) + "\n";
            i++;
            if (i == 5)
                break;
        }
        return res;
    }

    private String getHotelHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("Application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://www.tripadvisor.com/Search?searchSessionId=001038c7f818d6a1.ssid&searchNearby=false&q=" + keyword + "&sid=E190274FA58EEF6CA121EDCA1FE6AC361707030927352&withFilters=true&firstEntry=true")
                .addHeader("authority", "www.tripadvisor.com")                
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "en-US,en;q=0.9,he;q=0.8")
                .addHeader("content-type", "Application/json; charset=utf-8")
                .addHeader("cookie", "TADCID=WX_4q7bTfRApdHJMABQCmq6heh9ZSU2yA8SXn9Wv5HtYn_AYDBfMrRqoEXWXb4Ne93RPFn6vu1luAr1NcrJKaVIfD1cu-UPnE8; TASameSite=1; TAUnique=%1%enc%3AEx%2F7FMiIyJRJ1Rr0XimD5F12m%2FgoaxVlNtu4AQraSk2GS7IQz7N1QLtIbW%2B4unF9Nox8JbUSTxk%3D; TASID=E190274FA58EEF6CA121EDCA1FE6AC36; _abck=80A35BE0D0E31EBEFCBD32A6DCF5159A~-1~YAAQEo4QAtJXomGNAQAAgl/4cgvzf1AO8Dk090nHTiR8hqqM8u0T1akxXNL2B6tGuX0L9fSnXi+6Y8Ouit0SVbQAIjy9bdEPTSmPv0pDPNyfwyyCEJZEtLBZqb7XGCf1Vbz/AfdnNaP9aMi5BNJQs10QWOoVmMEwZMT27+N1ouAI6wqnVctLiCG1QWF1niYsa50hngCgTaKzZfHlhaEL8spEvNa7wyUpDsyF0Jop+M5zZ/u53yR/uQZkzlNHMaYf4G2FpjABAu0/gTiFA8JK7Q6rEJrj8IG8t/4WCuJ1mrOEz1zXWpRwFO2t7oxymupaIoJ6+LaS7Ldp6NjMzG7y/pauYjBhzmcDaxL/QJANEVwjhgMXSlb3gKZQASaFyDZ+/w==-1-1~-1; bm_sz=BB38459C90D57D979220035655C6BD66~YAAQEo4QAtNXomGNAQAAgl/4chZeqegrQ7+d78vg2xQvQFGbIo8F0IkNrGl/HiE/NK7GXTLrhuJxaRCxJhXo21ph7yR52kbYefT4DhUeZmAcwxNMktpP3ijqG//N1tKltD5IAY4uYuHy/IIH/4IqEYvHg0JCxatchQ22AxbF/6CctuiMmaWzbMihbmLMvW+2driOhQ1PiVJQQ70e3ArYNg1yQej4M+NVfJbMFWyY6ea/CLpE/cS675+8Y9YaSXKtLm9MnXmbMo9UUM33tZMNsrlT+FADAyr1V3KkDUvo8TOiZ3LH4vOlE7kjcH5fndXRUIS9PteAabCuNCiMoWHi7wKi~3159602~3618361; TASSK=enc%3AAI5WgVF1xqyPPDY0aS2NIKPt1wO4MRssGFI4CYPFFYEdsccA9E69lA3%2Fze%2BoDmPlmo0cntRfAOclGlL7DlA1UiUOgDj%2BnXOGgwXbP3sW7J7qZUjZ2BQ6ibY2%2BXBqLMOZHw%3D%3D; SRT=TART_SYNC; VRMCID=%1%V1*id.10568*llp.%2F*e.1707635700517; TART=%1%enc%3AFJfXufWD7%2FRlQFu39HMs5eQfBe0TJpAqGb9n0E8Bg%2FtDJ5bxMpglo4YfL7PNpIgXYyknXwNJuDE%3D; __vt=4tLve-OtDnRC4qWEABQCwRB1grfcRZKTnW7buAoPsSs2QH5Byy3fWLUfMjSF3g4nNq1lT8QTtWtiIVZ3qm-2rM3rdt-CQVIQ3khdE2y1t6RrQ6pV7lSU1EpGVznUKlxPBvgIdjpwZNTfemTUYARSZSiUQ; TATrkConsent=eyJvdXQiOiJTT0NJQUxfTUVESUEiLCJpbiI6IkFEVixBTkEsRlVOQ1RJT05BTCJ9; _ga_QX0Q50ZC9P=GS1.1.1707030901.1.0.1707030901.60.0.0; _ga=GA1.1.1074237144.1707030901; ab.storage.deviceId.6e55efa5-e689-47c3-a55b-e6d7515a6c5d=%7B%22g%22%3A%2220d862d2-1c27-d629-90ce-f358592b35ab%22%2C%22c%22%3A1707030901421%2C%22l%22%3A1707030901421%7D; ab.storage.sessionId.6e55efa5-e689-47c3-a55b-e6d7515a6c5d=%7B%22g%22%3A%2267b1643a-b8e3-2fcf-618a-b57d282a29e2%22%2C%22e%22%3A1707030961426%2C%22c%22%3A1707030901419%2C%22l%22%3A1707030901426%7D; pbjs_sharedId=9cf793f4-b68d-41af-bea1-c3733f92f6a5; pbjs_sharedId_cst=zix7LPQsHA%3D%3D; _li_dcdm_c=.tripadvisor.com; _lc2_fpi=b140173de591--01hnsfgwvzfyb564t799gpbrqx; _lc2_fpi_meta=%7B%22w%22%3A1707030901631%7D; _lr_sampling_rate=0; _lr_retry_request=true; _lr_env_src_ats=false; pbjs_unifiedID=%7B%22TDID%22%3A%2215ebbbe2-b0c0-438d-b2bf-cc82e2f4b83f%22%2C%22TDID_LOOKUP%22%3A%22TRUE%22%2C%22TDID_CREATED_AT%22%3A%222024-01-04T07%3A15%3A08%22%7D; pbjs_unifiedID_cst=zix7LPQsHA%3D%3D; OptanonConsent=isGpcEnabled=0&datestamp=Sun+Feb+04+2024+09%3A15%3A26+GMT%2B0200+(Israel+Standard+Time)&version=202310.2.0&browserGpcFlag=0&isIABGlobal=false&hosts=&consentId=3eed160b-7fdb-4231-808a-a66f470073c0&interactionCount=1&landingPath=https%3A%2F%2Fwww.tripadvisor.com%2F&groups=C0001%3A1%2CC0002%3A1%2CC0003%3A1%2CC0004%3A1; TASession=V2ID.E190274FA58EEF6CA121EDCA1FE6AC36*SQ.2*LS.Home*HS.recommended*ES.popularity*DS.5*SAS.popularity*FPS.oldFirst*FA.1*DF.0*TRA.true*EAU.; PAC=AGwEIQN-OvqskS6JvnfnT8oj_2mTss_JmVmgm_srDhrDhmrUF2FL3mzQMvsEVcxIFJiGXBzxdjcbnLd7GmxJrymDSO8MYD0Fx1P7a6MAtGgDfjXdfgmuI5hrN51BUEMy78iPpHrkCsG30MujPeEaIfHZ9TF9vFQF2klEYPkHkf6M6ezPPWFwPvdbd7UrZigPYQ%3D%3D; ServerPool=X; PMC=V2*MS.76*MD.20240204*LD.20240204; TATravelInfo=V2*AY.2024*AM.2*AD.18*DY.2024*DM.2*DD.19*A.2*MG.-1*HP.2*FL.3*DSM.1707030926543*RS.1; TAUD=LA-1707030926385-1*HDD-1-2024_02_18.2024_02_19*RDD-2-2024_02_04; roybatty=TNI1625!AOOd4%2BLg52xKtPtyzZsWz1A%2B8S1d4pRbJMXYZwmFgAExzPpuY6cUNF5nKWXApPFeVEppZ0sEhcxgsP9LUcc50Ah2Q4Pp%2FfrTFLOeS1Cs8QnzSGq5khfQnkJC3Wmuxq0qcOjmmKc1GfInBJ9QabwRssXQsOfuI%2FMee3Q4PyYOzBq7ea6JfY1nVm%2BNf1yJ3r8GdA%3D%3D%2C1; TASession=V2ID.E190274FA58EEF6CA121EDCA1FE6AC36*SQ.3*LS.Home*HS.recommended*ES.popularity*DS.5*SAS.popularity*FPS.oldFirst*FA.1*DF.0*TRA.true*EAU._; TAUD=LA-1707030926385-1*HDD-1-2024_02_18.2024_02_19*RDD-2-2024_02_04*LD-61768-2024.2.18.2024.2.19*LG-61770-2.1.F.; TASID=E190274FA58EEF6CA121EDCA1FE6AC36")
                .addHeader("referer", "https://www.tripadvisor.com/Search?searchSessionId=001038c7f818d6a1.ssid&searchNearby=false&q=las+vegas&sid=E190274FA58EEF6CA121EDCA1FE6AC361707030927352&blockRedirect=true")
                .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"macOS\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .addHeader("x-puid", "36bc917b-f478-4fd6-9038-6c2c7b366d74")
                .addHeader("x-requested-with", "XMLHttpRequest")
                .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        return res;
    }
}


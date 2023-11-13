package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


@Service
public class HotelsService {

    public static final Pattern HOTEL_PATTERN = Pattern.compile("\\);\\\"><span>([^<]+)<\\/span>[^>]+>[^>]+>[^>]+><span class=\\\"[^\\\"]+\\\"alt='([a-zA-Z0-9 ]+)[^>]+[^;]+[^>]+>([^<]+)<\\/a>");

    public String searchHotels(String keyword) throws IOException {
        return parseHotelHtml(getHotelHtml(keyword));
    }

    private String parseHotelHtml(String html) {
        String res = "";
        Matcher matcher = HOTEL_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) + ", Rating - " + matcher.group(2) + ", Reviews: " + matcher.group(3) + "\n";
        }
        return res;
    }

    private String getHotelHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://www.tripadvisor.com/Search?ssrc=h&q=" + keyword + "&searchSessionId=043370E4A987138A28AA450A1A01D29F1699794488158ssid&sid=A6E672B292B942B69552259F6BE4D64F1699795174706&blockRedirect=true&isSingleSearch=true&longitude=34.8740698&latitude=32.2210388&locationRejected=false&firstEntry=false")
                .method("GET", null)
                .addHeader("authority", "www.tripadvisor.com")
                //.addHeader("accept", "text/html, */*")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "he-IL,he;q=0.9,en-US;q=0.8,en;q=0.7,pl;q=0.6")
                .addHeader("content-type", "Application/json; charset=utf-8")
                .addHeader("cookie", "TASameSite=1; TAUnique=%1%enc%3ALiNtwqjQcUl73do261qDCyAccIeJA3g194E74VV2K1EN7Jglwpjk6rkRXv8hOtCaNox8JbUSTxk%3D; TASSK=enc%3AAKGhu2YIX3TD2WHG26iVo75EVIt2Vu8lyBWzAj7TvN2UvG4Mu%2BsACxzO9h9SoQ8hoIU5nBf6t3YCCGE3%2BkepwQxaORjRxfneGW2U8IbkOoayirR4dK40gvVp%2Ftuz4uRWrw%3D%3D; ServerPool=R; TART=%1%enc%3AWeytHWUIqOqkn8wUhrqitbqFBrqcAuKkU1h%2BF7roWSDykm6q1JkXNmS89UvMN2RGq2QV5f39nvg%3D; VRMCID=%1%V1*id.10568*llp.%2F*e.1700116450267; TATrkConsent=eyJvdXQiOiJTT0NJQUxfTUVESUEiLCJpbiI6IkFEVixBTkEsRlVOQ1RJT05BTCJ9; _ga=GA1.1.499030550.1699511655; G_AUTH2_MIGRATION=informational; AMZN-Token=v2FweIBpSGdPYzFiK2huN1BHMm5MMTZvbnFaTmtRYlhnK2tYNXVwSUNMTFlTWEVxUmJlWUFsakxwWXlvK3ZIRFdoU21YUDIzVE04aWFJeW9MOXhlaVB1LzU5bkI1Z2VYNU5TNlp2L3FNNVdHd0pPRUR0SXZXVkpad0tHVnRBOHpBSzFLV2JrdgFiaXZ4IGVuOTlUKysvdmUrL3ZRTHZ2NzFLNzcrOTc3KzlOUT09/w==; TAReturnTo=%1%%2FHotel_Review-g26308655-d143336-Reviews-Paris_Las_Vegas_Hotel_Casino-Paradise_Nevada.html; ab.storage.deviceId.6e55efa5-e689-47c3-a55b-e6d7515a6c5d=%7B%22g%22%3A%22674f55bf-b3fd-7263-8a48-b2bdcca999ea%22%2C%22c%22%3A1699519104784%2C%22l%22%3A1699539621452%7D; _hjSessionUser_1441007=eyJpZCI6IjRiYmJhMTcyLWQ5YjgtNWVlOC1iMWExLTMxNTkwNGJiNTU1ZCIsImNyZWF0ZWQiOjE2OTk1Mzk2MjM5MDAsImV4aXN0aW5nIjpmYWxzZX0=; ab.storage.sessionId.6e55efa5-e689-47c3-a55b-e6d7515a6c5d=%7B%22g%22%3A%2270caa39b-522e-7510-8a54-449e81ef1340%22%2C%22e%22%3A1699543040887%2C%22c%22%3A1699542948511%2C%22l%22%3A1699542980887%7D; TADCID=_CnHPczi69KZHDISABQCCKy0j55CTpGVsECjuwJMq3mBJAca4IT0RJDmNsxM__XcjejL9lvU-CCGkeDlenvh6V_onn7gEctB-IE; TAAUTHEAT=x4-ImDA8fhJIvUvrABQCobW21V9oR1-Dg22GNw6BiDlYJHVJCloVabR0RfyF91xGmLXL3xvydr9EHyWESirI29EjQelf850weNtYc8NW5yeHvkY5r59W1hq_wBlQWHpNtbPhSOKrBx3ZpkNXPCpiTLeyFze7faKiv9Gp7_eL5NSWyGAQhUCtiov0VyL-FuzWfbp0NWwlSCIuFMlTcRUtgxuTFQdAWQ3y1R9a; PMC=V2*MS.50*MD.20231109*LD.20231112; TATravelInfo=V2*AY.2023*AM.11*AD.26*DY.2023*DM.11*DD.27*A.2*MG.-1*HP.2*FL.3*DSM.1699788927353*RS.1; _abck=83780768395388B94FAB6AC3C156E258~-1~YAAQiMPBF+R72a2LAQAAkLFQwwpKfSsd8g3YWn2MdD/dvYOhVgFqOZFE3tmECRTxLxFsecKnQTVeFvSncoayjx+aK6nfrO453bUqbamk/S1ytL8ED2w5XPyfzBERUrzaUPiPEaXhEcdzFTScRLGNSVO7RMccLcwfJY1AIwZV6hKgu07+Q+quNwy7tOBf4H9K7IkgcPpysK5KbjR4Qr7JbDU6tDtOe6nPjqA9PYgg2xNEC9mEqi+ScZQw22/qR7SVaddxX+FD9VI+QG5sZec2Vl1iqN968D7EPezKD2H0O7pxMxzuzSQOjoI0WBUdUY5GXIXR/DwHius7VHESkZh+sjTjkDJu978440zUj4UcANX87CnoMHsEtGWTAiAswoeKY0m439FGa8zNecdajAbi~-1~-1~-1; bm_sz=1C63899036587EEDB5695632CCC6BA13~YAAQiMPBF+V72a2LAQAAkLFQwxUFqQdJwgjrcCR9DYAyD6Ih2rs1yU0mi6knPh5zYxpKDs5AY2YoMSUe7h9v6R5YiKooRkz7Y0XGVL8Ld1E7GLEI4FdJWYiD0IVCKhtRpP2rq0PopalkfoGGoPMcnELeS9ZaY4GHRNa/gUpMpJliIgjEdYaiyEN7fxldGhW4dn1aeZguoOujqqS7D7tsNiZXc2s0E92gFopTBvhWiiw5IV1kwNdk7QgeJftapMXDplSiw3bAhG/zRCv6+cyTKTrXU7WVLjD5SieFdOIrXivG72NW7tzx9A==~4277552~3225153; TASID=A6E672B292B942B69552259F6BE4D64F; PAC=AGo0ZXJgNt2GivFnEDQdi5V8Yqa1haPBsEsEFeGMTmzMsMAUOmBR9klItFTXMVFPPfkqF4PkNco7D8luTGmzZA8bdC3yFVEkgaCvDX_FQ643ntWG7JjOKX-znVAyNNZ8q4J3TlRy8ps9npR6Hgq7CtWnuWMums0cxyM4AcCFhZyElqc608Ia0cL7iQdMfzesHDPIyRxzqsEIe6SGUKFys8qkLJF-zUIu28TYNEMYMmc_; roybatty=TNI1625!AP2N5QCs2TlAtxpQQXezroydfugZttuDbkNlxZ1Lb7e0%2BjOqsPnxBGtmaqn1nayPEquatHiv5npcTqVObCe%2FCbQip47wGp04GGW%2FoH0VFqrExfTeFdcq9D%2FiVnmaGGoDq1gWS9vIqHqbm1xpX5cTuuUNF5QoYriy1KI%2B1ycYhITLr3QXue29SNQFI02pJixa7Q%3D%3D%2C1; OptanonConsent=isGpcEnabled=0&datestamp=Sun+Nov+12+2023+15%3A08%3A13+GMT%2B0200+(Israel+Standard+Time)&version=202209.1.0&isIABGlobal=false&hosts=&consentId=EF35DB26613DC947675FB0DCDDFE008C&interactionCount=1&landingPath=NotLandingPage&groups=C0001%3A1%2CC0002%3A1%2CC0003%3A1%2CC0004%3A1&AwaitingReconsent=false; _ga_QX0Q50ZC9P=GS1.1.1699794494.7.0.1699794494.60.0.0; SRT=%1%enc%3AWeytHWUIqOqkn8wUhrqitbqFBrqcAuKkU1h%2BF7roWSDykm6q1JkXNmS89UvMN2RGq2QV5f39nvg%3D; __vt=zDISltYkKz0IEo22ABQCCQPEFUluRFmojcP0P3EgGilfLGtAOnGY_NPq-IlqIIR1SalYKhZPBgeHoA7JuqBj84rzUDzQQcfldJpwArUF5c-hPRA9EAm3AnyBNqwQYjqeb8lOnpk95w716gzSrIvuhHEgPA; datadome=jhQiYbt71kI97myC8Yn8v0V2pkVkI4akY17CyukXjvyxBq7h5C6HAFIoNTI2xuHyLBhWlSgUQseHCGnAxgbflwEsjVfxmd0h_MiEFq7q8oP27_4zDrWd9beOBRgjfN__; TASession=V2ID.A6E672B292B942B69552259F6BE4D64F*SQ.146*LS.DemandLoadAjax*HS.recommended*ES.popularity*DS.5*SAS.popularity*FPS.oldFirst*TS.EF35DB26613DC947675FB0DCDDFE008C*LF.en*FA.1*DF.0*TRA.false*LD.143336*EAU.L; TAUD=LA-1699511650267-1*RDD-1-2023_11_09*HDD-277277066-2023_11_26.2023_11_27*LD-283515574-2023.11.26.2023.11.27*LG-283515576-2.1.F.; ServerPool=A; TASession=V2ID.A6E672B292B942B69552259F6BE4D64F*SQ.147*LS.DemandLoadAjax*HS.recommended*ES.popularity*DS.5*SAS.popularity*FPS.oldFirst*TS.EF35DB26613DC947675FB0DCDDFE008C*LF.en*FA.1*DF.0*TRA.false*LD.143336*EAU.L; TATravelInfo=V2*AY.2023*AM.11*AD.26*DY.2023*DM.11*DD.27*A.2*MG.-1*HP.2*FL.3*DSM.1699788967188*RS.1; TAUD=LA-1699511650267-1*RDD-1-2023_11_09*HDD-277277066-2023_11_26.2023_11_27*LD-283583976-2023.11.26.2023.11.27*LG-283583978-2.1.F.; TAUnique=%1%enc%3AfRscWhfheDDqgScV829zHcFpwbYUkBoOAr0%2FbWwAfY8qxBW6gpF2sSKIVRrxDjbANox8JbUSTxk%3D; _abck=95E3E2C2D6EB97E7D2C2019BEACFBFBA~-1~YAAQLx/XFzwJRbuLAQAAM01RwwpJnd5KH6hQoWYaGVTrRq8lZ7F0ax9VVcD/X5mEOHBpDHpsFIcGUs481+amlfUHIPA1b/F/WMN2j8+QCCNJdlOilXAhl47oPO2c2dbbrsXni+sEn7O6+4pX+ajHj4Il7scPO3fBMT4c9CY5c0/S22V8mIDv9fIJKx1fnX0fjwqJZnci7EqHkdDcLQ/XiK5VodsihJVCiMQbO4GDk0lv122TZZoqudCIF7whDVmg+ilKk4w93YzGWBYGb9EX/7YS8BQXF6AMICl4KefEK/9AOhPKgMZ2qOgo5Z+xkFE/UBFUMBGmAmQNf7LlJwUgjlDtEDungL4bO5W3fIVgtwCNhUjK906CoZcTmiFAdQ==~-1~-1~-1; bm_sz=773BEF254E0A17730859F091A6682E17~YAAQLx/XFz0JRbuLAQAAM01RwxXK9GOlQtKuQUa0nkY6+eVhN3R2fIAxZKIDvWZC4hUDwWuG5lDogDNjIBp/jiX1V0R+V4Feqi8IIhaT5xIPtwnoEfqnd7/L+f4H3gBmM4uNVb4FGvlmFg5nxUzA7xLpBHDtEOSAjmwkzJB75Co2SVRE6LgwuASnx4c/qdMHk/KGqAXOXfjDjeUsjjpy0ypNDDNz4O0ZNlLAvqseuCSnmOmao4yPI8MIMobDNKC6WDn8h4a8E/sBpv4Kz3dg/tL/CLmejHvUJ6I7zphOQixBANlAPblbuA==~3290678~3228481; datadome=OJxSbJTc65fbl9gz~uYXi3sdWIWiA_xVA~z8XOoR0s98aWa19LMZX~~CKsupdUhH1F6JlWAnr6Jm6OHc6_aaAJurHHw~IOuTxQQQecZOzfoD_m8Pky_2cFxrA_greF5j; PAC=ALPUVQuRLX_DpiD2-RmyJuLDOwBaZ3pmolbZHOB0i6oO0wXyoG9d7DmM_w_IJaR3ICludl-shGwxVua47eMbt8MzzqHh-zOhG4LAv1Us9F_CvXdjUDtCyFE7ZQxDcDpcXzcVncdTMSDwmxRsI7m7eHg%3D; PMC=V2*MS.2*MD.20231112*LD.20231112; TADCID=riYU16IB6WRvsHw_ABQCCKy0j55CTpGVsECjuwJMq3mBJQSap0tcFIpSBds8E9KIE6uI4lElnOIan4rea-xM6MENBQ0apKDFQUA; TART=%1%enc%3A%2B3yqAmlbQyvS9JNYyYIdSVPGMKY3DGHoXvwEOIQ3R1872hmM7xk5MIioP2ztt6usfYtftmnsPgU%3D; TASID=A6E672B292B942B69552259F6BE4D64F; TASSK=enc%3AAJ5lf4l9hVfHlGDcc002zAp9lmNl2oCXpwhM8cN5YcrrjG5Uxy4udAwmRiKWI1hifVrZr6yDUl%2BOk1D%2BlZ0Vtbp75T9A5MV%2Foga%2FE3xgEcERQwhfvXxXDp6ZfEkZDb8UsA%3D%3D; TASameSite=1")
                .addHeader("referer", "https://www.tripadvisor.com/Search?ssrc=h&q=paris&searchSessionId=043370E4A987138A28AA450A1A01D29F1699794488158ssid&sid=A6E672B292B942B69552259F6BE4D64F1699795174706&blockRedirect=true&isSingleSearch=true")
                .addHeader("sec-ch-device-memory", "8")
                .addHeader("sec-ch-ua", "\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"")
                .addHeader("sec-ch-ua-arch", "\"x86\"")
                .addHeader("sec-ch-ua-full-version-list", "\"Google Chrome\";v=\"119.0.6045.107\", \"Chromium\";v=\"119.0.6045.107\", \"Not?A_Brand\";v=\"24.0.0.0\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-model", "\"\"")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36")
                .addHeader("x-puid", "a1766dbc-5169-42b3-a27b-f35bbe9860fe")
                .addHeader("x-requested-with", "XMLHttpRequest")
                .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        return res;
    }

}

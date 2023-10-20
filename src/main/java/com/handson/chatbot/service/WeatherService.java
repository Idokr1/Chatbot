package com.handson.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class WeatherService {
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    @Autowired
    ObjectMapper om;

    public String searchWeather(String keyword) throws IOException {
        return getWeatherLocationData(getWeatherLocationId(keyword));
    }

    public String getWeatherLocationData(Integer locationId) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.yahoo.com/news/_tdnews/api/resource/WeatherService;crumb=I6h95i0Mzcm;enableDeeplinks=true;woeids=%5B" + locationId + "%5D?bkt=fp-US-en-US-def&device=desktop&ecma=modern&feature=cacheContentCanvas%2CdisableCommentsMessage%2CenableCCPAFooter%2CenableCMP%2CenableConsentData%2CenableGDPRFooter%2CenableGuceJs%2CenableGuceJsOverlay%2Clivecoverage%2CnewContentAttribution%2CnewLogo%2CrivendellMigration%2Cuserintent%2CvideoDocking%2CdelayCacheHeaders%2CoathPlayer%2CenableYBar%2CnewsRedesign%2CenableAdlite&intl=us&lang=en-US&partner=none&prid=5k8ja7dhie3e0&region=US&site=fp&tz=Europe%2FBerlin&ver=0.0.12106263&returnMeta=true")
                .get()
                .addHeader("authority", "www.yahoo.com")
                .addHeader("accept", "*/*")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cookie", "GUCS=AWj_CxpC; A3=d=AQABBL7nJmMCEDtm4OwLBV4uXDGdMacpcYUFEgABBwFTKGNaY-A9b2UB9iMAAAcIvucmY6cpcYU&S=AQAAAqnORlwKn0RY7zJbz6qcsT0; A1=d=AQABBL7nJmMCEDtm4OwLBV4uXDGdMacpcYUFEgABBwFTKGNaY-A9b2UB9iMAAAcIvucmY6cpcYU&S=AQAAAqnORlwKn0RY7zJbz6qcsT0; EuConsent=CPff7sAPff7sAAOACBDECgCoAP_AAH_AACiQI0Nd_H__bX9n-_7_6ft0cY1f9_r3ruQzDhfFk-8F3L_W3LwX32E7NF36pq4KmR4ku1bBIQFtHMnUDUmxaolVrzHsak2cpyNKI7JkknsZe2dYGF9Pm9lD-YKZ7_5_9_f52T_9_9v-39z3_9f___d9_-__-vjfV599n_v9fV_78_Kf9_5-_-_-___4IQAAAAQQ_AJMNW4gC7EscCbQMIoAQIwrCQqAUAEFAMLRAYAODgpmVgEusIGACAUARgRAhxBRgwCAAACAJCIAJAiwQCIAiAQAAgARAIQAETAIKACwMAgABANAxACgAECQgyICIpTAgIgSCAlsqEEoK9DTCAOssAKBRGxUACJAABSAAJCwcAwRICViyQJMUbwAw0AGAAII0CIAMAAQRoFQAYAAgjQA; GUC=AQABBwFjKFNjWkIf2wTs&s=AQAAAGw2Ii7-&g=YycKOQ; A1S=d=AQABBL7nJmMCEDtm4OwLBV4uXDGdMacpcYUFEgABBwFTKGNaY-A9b2UB9iMAAAcIvucmY6cpcYU&S=AQAAAqnORlwKn0RY7zJbz6qcsT0&j=GDPR; cmp=t=1663502897&j=1&u=1---&v=51")
                .addHeader("referer", "https://www.yahoo.com/news/weather/")
                .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"101\", \"Google Chrome\";v=\"101\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.0.0 Safari/537.36")
                .addHeader("x-requested-with", "XMLHttpRequest")
                .addHeader("x-webp", "1")
                .build();
        Response response = client.newCall(request).execute();
        WeatherLocationData res = om.readValue(response.body().string(), WeatherLocationData.class);
        return res.getData().getWeathers().get(0).getObservation().getDayPartTexts().get(0).getText();
    }

    public Integer getWeatherLocationId(String keyword) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.yahoo.com/news/_tdnews/api/resource/WeatherSearch;text=" + keyword + "?bkt=fp-US-en-US-def&device=desktop&ecma=modern&feature=cacheContentCanvas%2CdisableCommentsMessage%2CenableCCPAFooter%2CenableCMP%2CenableConsentData%2CenableGDPRFooter%2CenableGuceJs%2CenableGuceJsOverlay%2Clivecoverage%2CnewContentAttribution%2CnewLogo%2CrivendellMigration%2Cuserintent%2CvideoDocking%2CdelayCacheHeaders%2CoathPlayer%2CenableYBar%2CnewsRedesign%2CenableAdlite&intl=us&lang=en-US&partner=none&prid=5k8ja7dhie3e0&region=US&site=fp&tz=Europe%2FBerlin&ver=0.0.12106263&returnMeta=true")
                .get()
                .addHeader("authority", "www.yahoo.com")
                .addHeader("accept", "*/*")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cookie", "GUCS=AWj_CxpC; A3=d=AQABBL7nJmMCEDtm4OwLBV4uXDGdMacpcYUFEgABBwFTKGNaY-A9b2UB9iMAAAcIvucmY6cpcYU&S=AQAAAqnORlwKn0RY7zJbz6qcsT0; A1=d=AQABBL7nJmMCEDtm4OwLBV4uXDGdMacpcYUFEgABBwFTKGNaY-A9b2UB9iMAAAcIvucmY6cpcYU&S=AQAAAqnORlwKn0RY7zJbz6qcsT0; EuConsent=CPff7sAPff7sAAOACBDECgCoAP_AAH_AACiQI0Nd_H__bX9n-_7_6ft0cY1f9_r3ruQzDhfFk-8F3L_W3LwX32E7NF36pq4KmR4ku1bBIQFtHMnUDUmxaolVrzHsak2cpyNKI7JkknsZe2dYGF9Pm9lD-YKZ7_5_9_f52T_9_9v-39z3_9f___d9_-__-vjfV599n_v9fV_78_Kf9_5-_-_-___4IQAAAAQQ_AJMNW4gC7EscCbQMIoAQIwrCQqAUAEFAMLRAYAODgpmVgEusIGACAUARgRAhxBRgwCAAACAJCIAJAiwQCIAiAQAAgARAIQAETAIKACwMAgABANAxACgAECQgyICIpTAgIgSCAlsqEEoK9DTCAOssAKBRGxUACJAABSAAJCwcAwRICViyQJMUbwAw0AGAAII0CIAMAAQRoFQAYAAgjQA; GUC=AQABBwFjKFNjWkIf2wTs&s=AQAAAGw2Ii7-&g=YycKOQ; A1S=d=AQABBL7nJmMCEDtm4OwLBV4uXDGdMacpcYUFEgABBwFTKGNaY-A9b2UB9iMAAAcIvucmY6cpcYU&S=AQAAAqnORlwKn0RY7zJbz6qcsT0&j=GDPR; cmp=t=1663502897&j=1&u=1---&v=51")
                .addHeader("referer", "https://www.yahoo.com/news/weather/")
                .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"101\", \"Google Chrome\";v=\"101\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.0.0 Safari/537.36")
                .addHeader("x-requested-with", "XMLHttpRequest")
                .addHeader("x-webp", "1")
                .build();
        Response response = client.newCall(request).execute();
        WeatherLocationResponse res = om.readValue(response.body().string(), WeatherLocationResponse.class);
        return res.getData().get(0).getWoeid();
    }

    static class WeatherLocationResponse {
        List<WeatherLocationObject> data;

        public List<WeatherLocationObject> getData() {
            return data;
        }
    }

    static class WeatherLocationObject {
        Integer woeid;

        public Integer getWoeid() {
            return woeid;
        }
    }

    static class WeatherLocationData {
        Weathers data;

        public Weathers getData() {
            return data;
        }
    }

    static class Weathers {
        List<Weather> weathers;

        public List<Weather> getWeathers() {
            return weathers;
        }
    }

    static class Weather {
        WObservation observation;

        public WObservation getObservation() {
            return observation;
        }
    }
    static class WObservation {
        List<DayPart> dayPartTexts;

        public List<DayPart> getDayPartTexts() {
            return dayPartTexts;
        }
    }

    static class DayPart {
        String text;

        public String getText() {
            return text;
        }
    }
}
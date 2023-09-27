package ru.example.ExchangeInfoStaticWebservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.example.ExchangeInfoStaticWebservice.service.UniversalXmlParserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequestMapping("/data")
public class XmlApiController {

    /**
     *  Контроллер для запросов Информационно-статистического сервера
     */

    public static final String URL_ALL_SECURITIES = "https://iss.moex.com/iss/history/engines/stock/markets/shares/securities/?start=";
    public static final Integer TOTAL_SIZE = 585;
    @Autowired
    private UniversalXmlParserService universalXmlParserService;

    @GetMapping
    public String showUploadForm() {
        return "upload";
    }


    /**
     *  Чтобы выгрузить полный объём данных по вышеприведенному запросу,
     *  нужно последовательно производить выгрузку, увеличивая значение параметра start на
     *  каждом шаге на значение pagesize:
     */
    @PostMapping
    public String uploadData(Model model) {
        try {   // TODO доработать механизм гибкой настройки через UI
            int start = 0;
            int step = 100;
            boolean isLoadSuccess = true;
            boolean isFinalSuccess = true;
            while (isLoadSuccess) {
                URL url = new URL(URL_ALL_SECURITIES + start);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    String content = convertToString(inputStream);

                    universalXmlParserService.parseAndSaveData(content);

                    if (start >= TOTAL_SIZE) {
                        isLoadSuccess = false;
                        isFinalSuccess = true;
                    }
                    start += step;
                } else { // TODO обработка респонсов
                    isLoadSuccess = false;
                }
            }
            if (isFinalSuccess) {
                model.addAttribute("uploadStatus", "GET-запрос успешно выполнен.");
            } else {
                model.addAttribute("uploadStatus", "Произошла ошибка при GET-запросах.");
            }

            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String convertToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();

        return stringBuilder.toString();
    }
}
package ru.example.ExchangeInfoStaticWebservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.example.ExchangeInfoStaticWebservice.service.UniversalXmlParserService;

@Controller
public class XmlDocController {

    /**
     *  Контроллер для локальной загрузки XML-файлов
     */

    @Autowired
    private UniversalXmlParserService universalXmlParserService;

    @GetMapping("/upload")
    public String showUploadForm() {

        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String content = new String(file.getBytes());
            universalXmlParserService.parseAndSaveData(content);

            boolean isLoadSuccess = true;
            if (isLoadSuccess) {
                model.addAttribute("uploadStatus", "Файл успешно загружен");
            } else {
                model.addAttribute("uploadStatus", "Произошла ошибка при сохранении данных");
            }

            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
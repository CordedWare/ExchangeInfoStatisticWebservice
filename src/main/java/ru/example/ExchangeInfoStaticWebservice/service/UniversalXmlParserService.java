package ru.example.ExchangeInfoStaticWebservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.example.ExchangeInfoStaticWebservice.entity.SecuritiesDataHub;
import ru.example.ExchangeInfoStaticWebservice.entity.TradeHistoryTracker;
import ru.example.ExchangeInfoStaticWebservice.repository.SecuritiesDataHubRepository;
import ru.example.ExchangeInfoStaticWebservice.repository.TradeHistoryTrackerRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UniversalXmlParserService {
    /**
     * Универсальный адаптер для парсинга полученных данных из GET-запросов, или файлов.
     * */
    public static final String SECID = "SECID";
    public static final String REGNUMBER = "REGNUMBER";
    public static final String SHORTNAME = "SHORTNAME";
    public static final String EMITENT_TITLE = "EMITENT_TITLE";
    public static final String TRADEDATE = "TRADEDATE";
    public static final String NUMTRADES = "NUMTRADES";
    public static final String OPEN = "OPEN";
    public static final String CLOSE = "CLOSE";

    @Autowired
    private SecuritiesDataHubRepository securitiesDataHubRepository;

    @Autowired
    private TradeHistoryTrackerRepository tradeHistoryTrackerRepository;

    public void parseAndSaveData(String content) throws Exception {

        List<SecuritiesDataHub> secRows = new ArrayList<>();
        List<TradeHistoryTracker> hisRows = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(content)));
        NodeList rowList = doc.getElementsByTagName("row");

        for (int i = 0; i < rowList.getLength(); i++) {
            Element rowElement = (Element) rowList.item(i);

            SecuritiesDataHub securitiesDataHub = new SecuritiesDataHub();
            securitiesDataHub.setSecid(rowElement.getAttribute(SECID));
            securitiesDataHub.setRegnumber(rowElement.getAttribute(REGNUMBER));
            securitiesDataHub.setName(rowElement.getAttribute(SHORTNAME));
            securitiesDataHub.setEmitentTitle(rowElement.getAttribute(EMITENT_TITLE));

            secRows.add(securitiesDataHub);

            TradeHistoryTracker tradeHistoryTracker = new TradeHistoryTracker();
            tradeHistoryTracker.setSecid(securitiesDataHub);
            if (!rowElement.getAttribute(TRADEDATE).isEmpty()) tradeHistoryTracker.setTradedate(LocalDate.parse(rowElement.getAttribute(TRADEDATE)));
            tradeHistoryTracker.setNumtrades(rowElement.getAttribute(NUMTRADES));
            if (!rowElement.getAttribute(OPEN).isEmpty()) tradeHistoryTracker.setOpen(Double.valueOf(rowElement.getAttribute(OPEN)));
            if (!rowElement.getAttribute(CLOSE).isEmpty()) tradeHistoryTracker.setClose(Double.valueOf(rowElement.getAttribute(CLOSE)));

            hisRows.add(tradeHistoryTracker);
        }
        securitiesDataHubRepository.saveAll(secRows);
        tradeHistoryTrackerRepository.saveAll(hisRows);
    }
}

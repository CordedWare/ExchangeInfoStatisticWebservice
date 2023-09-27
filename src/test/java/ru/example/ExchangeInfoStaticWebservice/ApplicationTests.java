package ru.example.ExchangeInfoStaticWebservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testRequestStatus() throws Exception {
		URL url = new URL("https://iss.moex.com/iss/history/engines/stock/markets/shares/securities/?start=0");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		int responseCode = connection.getResponseCode();

		assertEquals(HttpURLConnection.HTTP_OK, responseCode);

		connection.disconnect();
	}
}
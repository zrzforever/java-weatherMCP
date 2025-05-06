/*
* Copyright 2024 - 2024 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.springframework.ai.mcp.sample.server;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Resource;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class WeatherService {

	private static final String BASE_URL = "https://api.weather.gov";

	private final RestClient restClient;

	public WeatherService() {

		this.restClient = RestClient.builder()
			.baseUrl(BASE_URL)
			.defaultHeader("Accept", "application/geo+json")
			.defaultHeader("User-Agent", "WeatherApiClient/1.0 (your@email.com)")
			.build();
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Points(@JsonProperty("properties") Props properties) {
		@JsonIgnoreProperties(ignoreUnknown = true)
		public record Props(@JsonProperty("forecast") String forecast) {
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Forecast(@JsonProperty("properties") Props properties) {
		@JsonIgnoreProperties(ignoreUnknown = true)
		public record Props(@JsonProperty("periods") List<Period> periods) {
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public record Period(@JsonProperty("number") Integer number, @JsonProperty("name") String name,
				@JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime,
				@JsonProperty("isDaytime") Boolean isDayTime, @JsonProperty("temperature") Integer temperature,
				@JsonProperty("temperatureUnit") String temperatureUnit,
				@JsonProperty("temperatureTrend") String temperatureTrend,
				@JsonProperty("probabilityOfPrecipitation") Map probabilityOfPrecipitation,
				@JsonProperty("windSpeed") String windSpeed, @JsonProperty("windDirection") String windDirection,
				@JsonProperty("icon") String icon, @JsonProperty("shortForecast") String shortForecast,
				@JsonProperty("detailedForecast") String detailedForecast) {
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Alert(@JsonProperty("features") List<Feature> features) {

		@JsonIgnoreProperties(ignoreUnknown = true)
		public record Feature(@JsonProperty("properties") Properties properties) {
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public record Properties(@JsonProperty("event") String event, @JsonProperty("areaDesc") String areaDesc,
				@JsonProperty("severity") String severity, @JsonProperty("description") String description,
				@JsonProperty("instruction") String instruction) {
		}
	}

	/**
	 * Get forecast for a specific latitude/longitude
	 * @param latitude Latitude
	 * @param longitude Longitude
	 * @return The forecast for the given location
	 * @throws RestClientException if the request fails
	 */
	@Tool(description = "Get weather forecast for a specific latitude/longitude")
	public String getWeatherForecastByLocation(double latitude, double longitude) {

		var points = restClient.get()
			.uri("/points/{latitude},{longitude}", latitude, longitude)
			.retrieve()
			.body(Points.class);

		var forecast = restClient.get().uri(points.properties().forecast()).retrieve().body(Forecast.class);

		String forecastText = forecast.properties().periods().stream().map(p -> {
			return String.format("""
					%s:
					Temperature: %s %s
					Wind: %s %s
					Forecast: %s
					""", p.name(), p.temperature(), p.temperatureUnit(), p.windSpeed(), p.windDirection(),
					p.detailedForecast());
		}).collect(Collectors.joining());

		return forecastText;
	}

	/**
	 * Get alerts for a specific area
	 * @param state Area code. Two-letter US state code (e.g. CA, NY)
	 * @return Human readable alert information
	 * @throws RestClientException if the request fails
	 */
	@Tool(description = "Get weather alerts for a US state. Input is Two-letter US state code (e.g. CA, NY)")
	public String getAlerts(String state) {
		Alert alert = restClient.get().uri("/alerts/active/area/{state}", state).retrieve().body(Alert.class);

		return alert.features()
			.stream()
			.map(f -> String.format("""
					Event: %s
					Area: %s
					Severity: %s
					Description: %s
					Instructions: %s
					""", f.properties().event(), f.properties.areaDesc(), f.properties.severity(),
					f.properties.description(), f.properties.instruction()))
			.collect(Collectors.joining("\n"));
	}

	@Resource
	private MenuRepository menuRepository;
	@Resource
	private OrderRepository orderRepository;

	@Tool(description = "只是新增菜品的，不是点单菜品")
	public String addFood(@ToolParam( description =  "food_cost_price") String food_cost_price, @ToolParam(description = "foodName") String foodName,
						  @ToolParam(description = "foodPrice") String foodPrice, @ToolParam(description = "foodNum") String foodNum) {
//		Alert alert = restClient.get().uri("/alerts/active/area/{state}", state).retrieve().body(Alert.class);
		Menu menu = new Menu();
		System.out.println(food_cost_price);
		menu.setId(UUID.randomUUID().hashCode());
//		System.out.println(Integer.valueOf(generateRandomId()));
		menu.setName(foodName);
		menu.setPrice(foodPrice);
		menu.setStore_num(foodNum);
		menu.setCost_price(food_cost_price);
		System.out.println(menu);
		menuRepository.save(menu);
		return "ok";
	}
	@Tool(description = "存储订单信息")
	public String addOrderDetail(@ToolParam( description =  "orderId") String orderId, @ToolParam(description = "productName") String productName,
						  @ToolParam(description = "unitPrice") BigDecimal unitPrice, @ToolParam(description = "quantity") String quantity, @ToolParam(description = "totalPrice") BigDecimal totalPrice, @ToolParam(description = "deliveryAddress") String deliveryAddress, @ToolParam(description = "contactPerson") String contactPerson, @ToolParam(description = "contactPhone") String contactPhone) {
//		Alert alert = restClient.get().uri("/alerts/active/area/{state}", state).retrieve().body(Alert.class);
		Orders order = new Orders();
		order.setOrderId(orderId);
		order.setId(100 + (int)(Math.random() * 900));
		order.setProductName(productName);
		order.setUnitPrice(unitPrice);
		order.setContactPerson(contactPerson);
		order.setContactPhone(contactPhone);
		order.setDeliveryAddress(deliveryAddress);
		System.out.println(orderId);
		System.out.println(productName);
		System.out.println(contactPerson);
		order.setQuantity(Integer.valueOf(quantity));
		order.setTotalPrice(totalPrice);
		System.out.println(deliveryAddress);
		orderRepository.save(order);
		return "ok";
	}

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int ID_LENGTH = 3;
	private static final Random RANDOM = new Random();

	public static String generateRandomId() {
		StringBuilder sb = new StringBuilder(ID_LENGTH);
		for (int i = 0; i < ID_LENGTH; i++) {
			int index = RANDOM.nextInt(CHARACTERS.length());
			sb.append(CHARACTERS.charAt(index));
		}
		return sb.toString();
	}

}
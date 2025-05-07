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
package org.springframework.ai.mcp.sample.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.modelcontextprotocol.spec.McpSchema.CallToolRequest;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.ListToolsResult;

/**
 * @author Christian Tzolov
 */

public class SampleClient {

	private final McpClientTransport transport;

	public SampleClient(McpClientTransport transport) {
		this.transport = transport;
	}

	public void run() {

		var client = McpClient.sync(this.transport).build();

		client.initialize();

		client.ping();

		// List and demonstrate tools
		ListToolsResult toolsList = client.listTools();
		System.out.println("Available Tools = " + toolsList);

		CallToolResult weatherForcastResult = client.callTool(new CallToolRequest("addOrderDetail", generateRandomOrder()));
		System.out.println("Weather Forcast: " + weatherForcastResult);
//
//		CallToolResult alertResult = client.callTool(new CallToolRequest("getAlerts", Map.of("state", "NY")));
//		System.out.println("Alert Response = " + alertResult);

		client.closeGracefully();

	}


	public static Map<String, Object> generateRandomOrder() {
		return Map.of(
				"orderId", generateRandomOrderId(),
				"contactPerson", generateRandomContactPerson(),
				"deliveryAddress", generateRandomDeliveryAddress(),
				"contactPhone", generateRandomContactPhone(),
				"details", generateRandomOrderDetails(),
				"totalAmount", generateRandomTotalAmount()
		);
	}

	private static String generateRandomOrderId() {
		return String.valueOf(System.currentTimeMillis()).substring(5, 10);
	}

	private static String generateRandomContactPerson() {
		String[] names = {"张三", "李四", "王五", "赵六", "钱七","老刘","刘三", "刘四", "刘五", "刘六", "刘七", "刘八", "刘九", "刘十", "刘十一", "刘十二", "刘十三", "刘十四", "刘十五", "刘十六", "刘十七", "刘十八", "刘十九", "刘二十", "刘二十一", "刘二十二", "刘二十三", "刘二十四", "刘二十五"};
		return names[new Random().nextInt(names.length)];
	}

	private static String generateRandomDeliveryAddress() {
		String[] addresses = {"北京市朝阳区", "上海市浦东新区", "广州市天河区", "深圳市南山区", "成都市高新区",  "杭州市西湖区", "武汉市武昌区", "南京市江宁区", "广东省深圳市", "广东省广州市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市", "广东省深圳市",};
		return addresses[new Random().nextInt(addresses.length)];
	}

	private static String generateRandomContactPhone() {
		return "1" + ThreadLocalRandom.current().nextInt(1000000000, (int) 9999999999L);
	}

	private static List<Map<String, Object>> generateRandomOrderDetails() {
		List<Map<String, Object>> details = new ArrayList<>();
		int detailCount = ThreadLocalRandom.current().nextInt(1, 8); // 随机生成1到5条订单详情
		String[] productNames = { "鼠标", "键盘", "运动鞋", "运动服","手机", "耳机", "电脑", "平板", "相机","路由器",};
		for (int i = 0; i < detailCount; i++) {
			details.add(Map.of(
					"productName", productNames[ThreadLocalRandom.current().nextInt(1, 6)],
					"unitPrice", new BigDecimal(ThreadLocalRandom.current().nextDouble(100.0, 10000.0)).setScale(2, BigDecimal.ROUND_HALF_UP),
					"quantity", ThreadLocalRandom.current().nextInt(1, 10)
			));
		}
		return details;
	}

	private static BigDecimal generateRandomTotalAmount() {
		return new BigDecimal(ThreadLocalRandom.current().nextDouble(100.0, 10000.0)).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

}

package org.springframework.ai.mcp.sample.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.ai.mcp.sample.server.OrderDetails;

@Mapper
public interface OrderDetailsMapper {
    int insertOrderDetails(OrderDetails orderDetails);
}

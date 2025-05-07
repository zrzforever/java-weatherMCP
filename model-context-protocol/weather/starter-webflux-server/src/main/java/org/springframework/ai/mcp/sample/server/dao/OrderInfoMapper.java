package org.springframework.ai.mcp.sample.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.ai.mcp.sample.server.OrderInfo;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    List<OrderInfo> selectUserById(int id);

    int insertUser(OrderInfo orderInfo);
}

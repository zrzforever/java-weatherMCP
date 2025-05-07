package org.springframework.ai.mcp.sample.server;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单信息实体类，与数据库中的 order_info 表对应，
 * 用于存储订单的整体信息，如订单 ID、联系人、配送地址、联系电话和总金额等。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    /**
     * 订单的唯一标识
     */
    private String orderId;
    /**
     * 联系人姓名
     */
    private String contactPerson;
    /**
     * 配送地址
     */
    private String deliveryAddress;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;
}
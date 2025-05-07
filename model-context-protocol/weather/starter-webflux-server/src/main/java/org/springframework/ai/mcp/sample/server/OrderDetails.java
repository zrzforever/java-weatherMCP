package org.springframework.ai.mcp.sample.server;



import java.math.BigDecimal;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单详情实体类，用于表示订单中具体商品的详细信息，
 * 与数据库中的 order_detail 表对应，包含商品名称、单价、数量、详情 ID 和订单 ID 等信息。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    /**
     * 订单详情的唯一标识
     */
    private Integer detailId;
    /**
     * 关联的订单 ID
     */
    private String orderId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品单价
     */
    private BigDecimal unitPrice;
    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 计算该商品在订单中的总价
     * @return 商品总价
     */
    public BigDecimal getTotalPrice() {
        if (unitPrice != null && quantity != null) {
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }
}
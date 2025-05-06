package org.springframework.ai.mcp.sample.server;



import java.math.BigDecimal;

public class OrderDetails {
    private String productName;
    private BigDecimal unitPrice;
    private Integer quantity;

    public OrderDetails(String productName, BigDecimal unitPrice, Integer quantity) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
    // 无参构造函数
    public OrderDetails() {
    }
    public String getProductName() {
        return productName;
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

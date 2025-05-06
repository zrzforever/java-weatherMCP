package org.springframework.ai.mcp.sample.server;

import jakarta.persistence.*;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderId;
    private List<OrderDetails> details;
    private BigDecimal totalPrice;
    private String deliveryAddress;
    private String contactPerson;
    private String contactPhone;

    // 无参构造方法
    public Orders() {
    }

    // 全参构造方法
    public Orders(String orderId, String productName, BigDecimal unitPrice, Integer quantity, BigDecimal totalPrice, String deliveryAddress, String contactPerson, String contactPhone) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
    }

    public void setDetails(List<OrderDetails> details) {
        this.details = details;
    }

    public List<OrderDetails> getDetails() {
        return details;
    }

    // Getter 和 Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", totalPrice=" + totalPrice +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                '}';
    }
}
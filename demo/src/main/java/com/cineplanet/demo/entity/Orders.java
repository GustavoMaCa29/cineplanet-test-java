package com.cineplanet.demo.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Orders extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetails> details;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    public Orders(User user, Set<OrderDetails> details, BigDecimal total, Timestamp date) {
        this.user = user;
        this.details = details;
        this.total = total;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(Set<OrderDetails> details) {
        this.details = details;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}

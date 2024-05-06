package com.cgrecords.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class InventoryItem {
    @Id
    @GeneratedValue
    Long inventory_item_id;
    String title;
    int quantity = 0;
}

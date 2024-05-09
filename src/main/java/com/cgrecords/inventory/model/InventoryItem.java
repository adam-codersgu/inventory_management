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
    Long inventoryItemId;
    String title;
    int quantity = 0;
}

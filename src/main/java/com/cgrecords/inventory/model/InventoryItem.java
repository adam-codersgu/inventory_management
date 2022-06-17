package com.cgrecords.inventory.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class InventoryItem {
    @Id
    @GeneratedValue
    Long inventory_item_id;
    String title;
    int quantity = 0;
}

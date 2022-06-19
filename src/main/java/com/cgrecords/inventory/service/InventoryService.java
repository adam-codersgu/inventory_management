package com.cgrecords.inventory.service;

import com.cgrecords.inventory.model.InventoryItem;

import java.util.List;

public interface InventoryService {
    void deleteInventoryItemByID(Long id);
    List<InventoryItem> getAllInventoryItems();
    InventoryItem getInventoryItemByID(Long id);
    InventoryItem saveInventoryItem(InventoryItem item);
}

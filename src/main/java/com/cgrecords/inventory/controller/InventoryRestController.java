package com.cgrecords.inventory.controller;

import com.cgrecords.inventory.model.InventoryItem;
import com.cgrecords.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryRestController {
    InventoryService inventoryService;

    public InventoryRestController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping(value = "/items", produces = "application/json")
    public List<InventoryItem> getInventory() {
        return inventoryService.getAllInventoryItems();
    }
}

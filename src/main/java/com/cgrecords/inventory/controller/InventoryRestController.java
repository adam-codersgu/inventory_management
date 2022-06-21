package com.cgrecords.inventory.controller;

import com.cgrecords.inventory.model.InventoryItem;
import com.cgrecords.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/items/{id}", produces = "application/json")
    public ResponseEntity<InventoryItem> getInventoryItemByID(@PathVariable Long id) {
        InventoryItem item = inventoryService.getInventoryItemByID(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok()
                    .body(item);
        }
    }
}

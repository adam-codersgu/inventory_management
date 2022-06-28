package com.cgrecords.inventory.controller;

import com.cgrecords.inventory.model.InventoryItem;
import com.cgrecords.inventory.model.InventoryItemAssembler;
import com.cgrecords.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryRestController {
    InventoryService inventoryService;
    InventoryItemAssembler inventoryItemAssembler;

    public InventoryRestController(InventoryService inventoryService, InventoryItemAssembler inventoryItemAssembler) {
        this.inventoryService = inventoryService;
        this.inventoryItemAssembler = inventoryItemAssembler;
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

    @PostMapping(value = "/items", consumes = "application/json")
    ResponseEntity<InventoryItem> addItemToInventory(@RequestBody InventoryItem item) {
        InventoryItem createdItem = inventoryService.saveInventoryItem(item);
        if (createdItem == null) {
            return ResponseEntity.internalServerError().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/items/{id}")
                    .buildAndExpand(createdItem.getInventory_item_id())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdItem);
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Object> deleteInventoryItem(@PathVariable("id") Long id) {
        InventoryItem item = inventoryService.getInventoryItemByID(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        } else {
            inventoryService.deleteInventoryItemByID(id);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping(value = "/items/{id}", consumes = "application/json")
    ResponseEntity<InventoryItem> updateInventoryItem(
            @RequestBody InventoryItem item, @PathVariable("id") Long id) {
        InventoryItem createdItem = inventoryService.getInventoryItemByID(id);
        if (createdItem == null) {
            return ResponseEntity.notFound().build();
        } else {
            item.setInventory_item_id(id);
            inventoryService.saveInventoryItem(item);
            return ResponseEntity.ok()
                    .body(item);
        }
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<InventoryItem> updateItemQuantity(
            @RequestBody Map<String, Object> payload, @PathVariable("id") Long id) {
        InventoryItem item = inventoryService.getInventoryItemByID(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        } else {
            Object newQuantity = payload.get("quantity");
            if (newQuantity instanceof Integer) {
                item.setQuantity((int) newQuantity);
                inventoryService.saveInventoryItem(item);
                return ResponseEntity.ok()
                        .body(item);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }
}

package com.cgrecords.inventory.controller;

import com.cgrecords.inventory.model.InventoryItem;
import com.cgrecords.inventory.model.InventoryItemAssembler;
import com.cgrecords.inventory.service.InventoryService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/items")
    public ResponseEntity<CollectionModel<EntityModel<InventoryItem>>> getInventory() {
        List<InventoryItem> items = inventoryService.getAllInventoryItems();
        CollectionModel<EntityModel<InventoryItem>> collectionModel = inventoryItemAssembler.toCollectionModel(items);

        return ResponseEntity.ok()
                .body(collectionModel);
    }

    @GetMapping(value = "/items/{id}")
    public ResponseEntity<EntityModel<InventoryItem>> getInventoryItemByID(@PathVariable Long id) {
        InventoryItem item = inventoryService.getInventoryItemByID(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        } else {
            EntityModel<InventoryItem> entityModel = inventoryItemAssembler.toModel(item);
            return ResponseEntity.ok()
                    .body(entityModel);
        }
    }

    @PostMapping("/items")
    ResponseEntity<EntityModel<InventoryItem>> addItemToInventory(@RequestBody InventoryItem item) {
        InventoryItem createdItem = inventoryService.saveInventoryItem(item);

        if (createdItem == null) {
            return ResponseEntity.notFound().build();
        } else {
            EntityModel<InventoryItem> entityModel = inventoryItemAssembler.toModel(createdItem);
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        }
    }

    @PutMapping(value = "/items/{id}", consumes = "application/json")
    ResponseEntity<EntityModel<InventoryItem>> updateInventoryItem(
            @RequestBody InventoryItem item, @PathVariable("id") Long id) {
        InventoryItem createdItem = inventoryService.getInventoryItemByID(id);
        if (createdItem == null) {
            return ResponseEntity.notFound().build();
        } else {
            item.setInventoryItemId(id);
            inventoryService.saveInventoryItem(item);
            EntityModel<InventoryItem> entityModel = inventoryItemAssembler.toModel(item);
            return ResponseEntity.ok()
                    .body(entityModel);
        }
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<EntityModel<InventoryItem>> updateItemQuantity(
            @RequestBody Map<String, Object> payload, @PathVariable("id") Long id) {
        InventoryItem item = inventoryService.getInventoryItemByID(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        } else {
            Object newQuantity = payload.get("quantity");
            if (newQuantity instanceof Integer) {
                item.setQuantity((int) newQuantity);
                inventoryService.saveInventoryItem(item);
                EntityModel<InventoryItem> entityModel = inventoryItemAssembler.toModel(item);
                return ResponseEntity.ok()
                        .body(entityModel);
            } else {
                return ResponseEntity.badRequest().build();
            }
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
}

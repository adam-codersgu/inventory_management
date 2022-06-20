package com.cgrecords.inventory.service;

import com.cgrecords.inventory.model.InventoryItem;
import com.cgrecords.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this. inventoryRepository = inventoryRepository;
    }

    @Override
    public void deleteInventoryItemByID(Long id) { inventoryRepository.deleteById(id); }

    @Override
    public List<InventoryItem> getAllInventoryItems() {
        return new ArrayList<>(inventoryRepository.findAll());
    }

    @Override
    public InventoryItem getInventoryItemByID(Long id) {
        Optional<InventoryItem> optionalItem = inventoryRepository.findById(id);
        return optionalItem.orElse(null);
    }

    @Override
    public InventoryItem saveInventoryItem(InventoryItem item) {
        return inventoryRepository.save(item);
    }
}

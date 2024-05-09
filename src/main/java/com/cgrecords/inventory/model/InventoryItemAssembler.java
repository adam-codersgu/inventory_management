package com.cgrecords.inventory.model;

import com.cgrecords.inventory.controller.InventoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InventoryItemAssembler implements RepresentationModelAssembler<InventoryItem, EntityModel<InventoryItem>> {
    @Override
    public EntityModel<InventoryItem> toModel(InventoryItem item) {

        return EntityModel.of(item,
                linkTo(methodOn(InventoryRestController.class).getInventoryItemByID(item.getInventoryItemId())).withSelfRel(),
                linkTo(methodOn(InventoryRestController.class).getInventory()).withRel("items"));
    }

    @Override
    public CollectionModel<EntityModel<InventoryItem>> toCollectionModel(Iterable<? extends InventoryItem> items) {
        List<EntityModel<InventoryItem>> entityModelItems = StreamSupport.stream(items.spliterator(), false)
                .map(this::toModel).toList();

        return CollectionModel.of(entityModelItems,
                linkTo(methodOn(InventoryRestController.class).getInventory()).withSelfRel()
        );
    }
}

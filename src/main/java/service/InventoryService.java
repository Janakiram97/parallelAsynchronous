package service;

import domain.Inventory;
import domain.ProductOptions;

import java.util.concurrent.CompletableFuture;

import static util.CommonTimeUtil.delay;

public class InventoryService {

    public Inventory addInventory(ProductOptions productOption)
    {
        delay(500);
        return Inventory.builder()
                .count(2).build();
    }

    public CompletableFuture<Inventory> addInventory_CF(ProductOptions productOption)
    {
       /* CompletableFuture<Inventory> inventoryCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return Inventory.builder()
                    .count(2)
                    .build();
        });
        return inventoryCompletableFuture;*/
        return CompletableFuture.supplyAsync(() ->
                Inventory.builder()
                        .count(2)
                        .build());
    }
}

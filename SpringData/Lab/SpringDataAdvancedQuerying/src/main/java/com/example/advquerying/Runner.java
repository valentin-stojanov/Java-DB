package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
public class Runner implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public Runner(
            ShampooService shampooService,
            IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }


    @Override
    public void run(String... args) throws Exception {

//        _01_selectShampoosBySize();
//        _02_selectShampoosBySizeOrLabel();
//        _03_selectShampoosByPrice();
//        _04_selectIngredientsByName();
//        _05_ingredientsByName();
//        _05_2_ingredientsByName();
//        _06_countShampoosByPrice();
//        _07_selectShampoosByIngredients();
//        _08_shampoosByIngredientCount();
//        _10_updateIngredientsByPrice();
//        _11_updateIngredientsByNames();
        _09_deleteIngredientsByName();
    }

    private void _01_selectShampoosBySize() {
        this.shampooService.selectBySize(Size.valueOf("MEDIUM"))
                .forEach(System.out::println);
    }

    private void _02_selectShampoosBySizeOrLabel() {
        this.shampooService.selectBySizeOrLabel(Size.valueOf("MEDIUM"), 10)
                .forEach(System.out::println);
    }

    private void _03_selectShampoosByPrice() {
        this.shampooService.selectShampoosByPrice(BigDecimal.valueOf(5))
                .forEach(System.out::println);
    }

    private void _04_selectIngredientsByName() {
        this.ingredientService.selectIngredientsByName()
                .forEach(System.out::println);
    }

    private void _05_ingredientsByName() {
        this.ingredientService.ingredientsByNames()
                .forEach(System.out::println);
    }

    private void _05_2_ingredientsByName() {
        this.ingredientService.ingredientsInNames()
                .forEach(System.out::println);
    }

    private void _06_countShampoosByPrice() {
        System.out.println(this.shampooService.countAllShampoosByPrice(BigDecimal.valueOf(8.50)));
    }

    private void _07_selectShampoosByIngredients() {
        this.shampooService.selectShampoosByIngredients(Set.of("Berry", "Mineral-Collagen"))
                .forEach(System.out::println);
    }

    private void _08_shampoosByIngredientCount() {
        this.shampooService.selectShampoosByIngredientsCount(2)
                .forEach(System.out::println);
    }

    private void _09_deleteIngredientsByName() {
        this.ingredientService.deleteIngredientsByName("Apple");
    }

    private void _10_updateIngredientsByPrice() {
        this.ingredientService.updateIngredientsPrice(10);
    }

    private void _11_updateIngredientsByNames(){
        this.ingredientService.updateIngredientsByNames(List.of("Macadamia Oil", "Mineral-Collagen"));
    }

}

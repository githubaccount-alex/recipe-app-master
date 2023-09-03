import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from '../../models/recipe';
import { FormArray, FormControl, FormGroup, NonNullableFormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Ingredient } from '../../models/ingredient';
import { getUnitDisplayValue, UnitOfMeasure } from '../../models/unitOfMeasure';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { RecipeService } from '../../services/recipe.service';

@Component({
    selector: 'app-recipe-form',
    templateUrl: './recipe-form.component.html',
    styleUrls: ['./recipe-form.component.css']
})
export class RecipeFormComponent implements OnInit{
    eUnitOfMeasure = UnitOfMeasure;
    recipeForm: FormGroup;
    products?: Product[];
    getUnitDisplayValue: (unit: string) => string = getUnitDisplayValue;

    @Input()
    recipeToEdit?: Recipe;

    constructor(private fb: NonNullableFormBuilder,
                private productService: ProductService,
                private recipeService: RecipeService,
                private router: Router) {
        this.recipeForm = this.fb.group({
            id: new FormControl,
            name: new FormControl(this.recipeToEdit?.name, Validators.required),
            instructions: new FormControl(this.recipeToEdit?.instructions, Validators.required),
            imageUrl: new FormControl(this.recipeToEdit?.instructions),
            ingredients: this.fb.array([])
        });

        this.productService
            .getAllProducts()
            .subscribe(products => this.products = products);
    }

    ngOnInit(): void {
        if (this.recipeToEdit) {
            this.recipeForm.patchValue(this.recipeToEdit);
            for (let ingredient of this.recipeToEdit.ingredients)
                this.addIngredientControlWithValues(ingredient);
        }

        if (!this.recipeToEdit)
            this.addIngredientControl();

        this.productService
            .getAllProducts()
            .subscribe(products => this.products = products);
    }

    get ingredientControls(): FormArray {
        return this.recipeForm?.get('ingredients') as FormArray;
    }

    addIngredientControl() {
        const ingredientGroup = this.fb.group({
            id: new FormControl(),
            name: new FormControl('', Validators.required),
            amountInRecipe: new FormControl('', Validators.required),
            unitOfMeasure: new FormControl(this.eUnitOfMeasure.PIECE, Validators.required)
        });

        this.ingredientControls.push(ingredientGroup);
    }

    addIngredientControlWithValues(ingredient: Ingredient) {
        const ingredientGroup = this.fb.group({
            id: new FormControl(ingredient.id),
            name: new FormControl(ingredient.name, Validators.required),
            amountInRecipe: new FormControl(ingredient.amountInRecipe, Validators.required),
            unitOfMeasure: new FormControl(ingredient.unitOfMeasure, Validators.required)
        });

        this.ingredientControls.push(ingredientGroup);
    }

    removeIngredientControl(index: number): void {
        this.ingredientControls.removeAt(index);
    }

    saveRecipe(recipe: Recipe): void {
        if (this.recipeToEdit)
            this.recipeService
                .updateRecipe(recipe)
                .subscribe( () => this.router.navigate([`/recipes`]));
        else
            this.recipeService
                .createRecipe(recipe)
                .subscribe(() => this.router.navigate([`/recipes`]));
    }
}

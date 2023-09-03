import { Component, OnInit } from '@angular/core';
import { Recipe } from '../../models/recipe';
import { RecipeService } from '../../services/recipe.service';

@Component({
    selector: 'app-recipe-list',
    templateUrl: './recipe-list.component.html',
    styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {
    filter: string = '';
    cookable: boolean = false;
    recipes?: Recipe[];
    cookableRecipes?: Recipe[];

    constructor(private recipeService: RecipeService) { }

    ngOnInit(): void {
        this.recipeService
            .getRecipes()
            .subscribe(recipes => this.recipes = recipes);
        this.recipeService
            .getCookableRecipes(true)
            .subscribe(recipes => this.cookableRecipes = recipes);
    }

    setCookable(): void {
        this.cookable = !this.cookable;
    }

    cookRecipe(id: number): void {
        this.recipeService
            .cookRecipe(id)
            .subscribe(() => {
                this.recipeService
                    .getRecipes()
                    .subscribe(recipes => this.recipes = recipes);
                this.recipeService
                    .getCookableRecipes(true)
                    .subscribe(recipes => this.cookableRecipes = recipes);
            });
    }

    filterRecipes(recipes: Recipe[] | undefined) {
        if (!this.filter)
            return recipes;

        return recipes?.filter(recipe => recipe
            .name
            .toLowerCase()
            .includes(this.filter.toLowerCase()));
    }
}

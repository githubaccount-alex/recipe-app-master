import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Recipe } from '../../models/recipe';
import { RecipeService } from '../../services/recipe.service';
import { getUnitDisplayValue } from '../../models/unitOfMeasure';

@Component({
    selector: 'app-recipe-details',
    templateUrl: './recipe-details.component.html',
    styleUrls: ['./recipe-details.component.css']
})
export class RecipeDetailsComponent implements OnInit {
    private sub: any;
    edit: boolean = false;
    recipe?: Recipe;
    getUnitDisplayValue: (unit: string) => string = getUnitDisplayValue;

    constructor(private recipeService: RecipeService,
                private activatedRoute: ActivatedRoute,
                private router: Router) { }

    ngOnInit(): void {
        this.sub = this.activatedRoute
            .params
            .subscribe(params=> {
                this.recipeService
                    .getRecipeById(+params['id'])
                    .subscribe(recipe => this.recipe = recipe);
            });
    }

    editRecipe(): void {
        this.edit = true;
    }

    delete(): void {
        this.recipeService
            .deleteRecipeById(this.recipe?.id)
            .subscribe(() => {
                this.recipe?.name.concat(" wurde erfolgreich gelöscht!");
                this.router.navigate([`/recipes`]);
            });
    }

    cookRecipe(id: number | undefined): void {
        this.recipeService
            .cookRecipe(id)
            .subscribe(() => this.ngOnInit());
    }

    isCookable(): boolean | undefined {
        return this.recipe?.cookable;
    }
}

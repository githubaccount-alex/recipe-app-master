import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Recipe } from '../models/recipe';
import { Product } from '../models/product';

@Injectable({
    providedIn: 'root'
})
export class RecipeService {
    private url = 'http://localhost:8080/recipes';

    constructor(private http: HttpClient) { }

    public getRecipes(): Observable<Recipe[]> {
        return this.http.get<Recipe[]>(this.url);
    }

    public getRecipeById(id: number | undefined): Observable<Recipe> {
        return this.http.get<Recipe>(this.url + '/' + id);
    }

    public createRecipe(recipe: Recipe): Observable<Recipe> {
        return this.http.post<Recipe>(this.url, recipe);
    }

    public updateRecipe(recipe: Recipe): Observable<Recipe> {
        return this.http.put<Recipe>(this.url, recipe);
    }

    public deleteRecipeById(id: number | undefined): Observable<void> {
        return this.http.delete<void>(this.url + '/' + id);
    }

    public getCookableRecipes(cookable: boolean): Observable<Recipe[]> {
        return this.http.get<Recipe[]>(`${this.url}?cookable=${cookable}`);
    }

    public cookRecipe(id : number | undefined): Observable<Product[]> {
        return this.http.put<Product[]>(this.url + '/cook/' + id, null);
    }
}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecipeListComponent } from './components/recipe-list/recipe-list.component';
import { RecipeDetailsComponent } from './components/recipe-details/recipe-details.component';
import { RecipeFormComponent } from './components/recipe-form/recipe-form.component';
import { ProductOverviewComponent } from './components/product-overview/product-overview.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { ProductFormComponent } from './components/product-form/product-form.component';
import { ErrorPageComponent } from './components/error-page/error-page.component';

const routes: Routes = [
    {path: '', redirectTo: 'recipes', pathMatch: 'full'},
    {path: 'recipes', component: RecipeListComponent},
    {path: 'recipes/:id', component: RecipeDetailsComponent},
    {path: 'createRecipe', component: RecipeFormComponent},
    {path: 'ingredients/:id', component: RecipeDetailsComponent},
    {path: 'product-overview', component: ProductOverviewComponent},
    {path: 'product-detail/:id', component: ProductDetailsComponent},
    {path: 'product-form', component: ProductFormComponent},
    {path: 'product-form/:id', component: ProductFormComponent},
    {path: '**', component: ErrorPageComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],

})
export class AppRoutingModule { }

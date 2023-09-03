import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgOptimizedImage } from "@angular/common";
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RecipeListComponent } from './components/recipe-list/recipe-list.component';
import { RecipeDetailsComponent } from './components/recipe-details/recipe-details.component';
import { RecipeFormComponent } from './components/recipe-form/recipe-form.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { ProductFormComponent } from './components/product-form/product-form.component';
import { ProductItemComponent } from './components/product-item/product-item.component';
import { ProductOverviewComponent } from './components/product-overview/product-overview.component';
import { ErrorPageComponent } from './components/error-page/error-page.component';

@NgModule({
    declarations: [
        AppComponent,
        RecipeListComponent,
        RecipeDetailsComponent,
        RecipeFormComponent,
        ProductDetailsComponent,
        ProductFormComponent,
        ProductItemComponent,
        ProductOverviewComponent,
        ErrorPageComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientModule,
        NgOptimizedImage
    ],
    providers: [HttpClientModule],
    bootstrap: [AppComponent]
})
export class AppModule { }

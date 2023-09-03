import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';

@Component({
    selector: 'app-product-overview',
    templateUrl: './product-overview.component.html',
    styleUrls: ['./product-overview.component.css']
})
export class ProductOverviewComponent implements OnInit {
    products: Product[] = [];
    filter: string = '';

    constructor(private productService: ProductService) { }

    ngOnInit(): void {
        this.productService
            .getAllProducts()
            .subscribe(products => this.products = products);
    }

    filterProducts(products: Product[]) {
        if (!this.filter)
            return products;

        return products.filter(product => product
            .name
            .toLowerCase()
            .includes(this.filter.toLowerCase()));
    }
}

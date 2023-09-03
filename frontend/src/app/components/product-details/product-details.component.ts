import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { getUnitDisplayValue } from '../../models/unitOfMeasure';

@Component({
    selector: 'app-product-details',
    templateUrl: './product-details.component.html',
    styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
    product?: Product;
    deletable?: boolean;
    getUnitDisplayValue: (unit: string) => string = getUnitDisplayValue;

    constructor(private activatedRoute: ActivatedRoute,
                private productService: ProductService,
                private router: Router) { }

    ngOnInit(): void {
        const id = this.activatedRoute.snapshot.paramMap.get('id');

        if (id) {
            this.productService
                .getProductsById(Number.parseInt(id))
                .subscribe(product => {
                    this.product = product;
                })
        }
    }

    deleteProduct(): void {
        this.productService
            .deleteProducts(this.product?.id)
            .subscribe(() => this.router.navigate(['/product-overview']));
    }
}

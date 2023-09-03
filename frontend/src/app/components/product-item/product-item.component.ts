import { Component, Input } from '@angular/core';
import { Product } from '../../models/product';

@Component({
    selector: 'app-product-item',
    templateUrl: './product-item.component.html',
    styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent {
    currentDate: Date = new Date();

    @Input()
    product!: Product;

    isExpired(expirationDate: Date): boolean {
        return this.currentDate > new Date(expirationDate);
    }
}

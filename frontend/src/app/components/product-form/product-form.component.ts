import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { getUnitDisplayValue, UnitOfMeasure } from '../../models/unitOfMeasure';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';

@Component({
    selector: 'app-product-form',
    templateUrl: './product-form.component.html',
    styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit{
    productForm?: FormGroup;
    units: string[] = Object.values(UnitOfMeasure);
    existingId: number | null = null;
    getUnitDisplayValue: (unit: string) => string = getUnitDisplayValue;

    constructor(private formBuilder: FormBuilder,
                private productService: ProductService,
                private router: Router,
                private activatedRoute: ActivatedRoute) { }

    ngOnInit(): void {
        const id = this.activatedRoute.snapshot.paramMap.get('id');

        if (id) {
            this.existingId = Number.parseInt(id);
            this.productForm?.controls['name'].setErrors({notEditable: true});
            this.productService
                .getProductsById(this.existingId)
                .subscribe(product =>
                    this.productForm = this.formBuilder.group({
                        name: [
                            product.name,
                            Validators.required
                        ],
                        amountInStock: [
                            product.amountInStock,
                            [Validators.required, Validators.min(0)]
                        ],
                        unitOfMeasure: [
                            product.unitOfMeasure,
                            Validators.required
                        ],
                        expirationDate: [
                            product.expirationDate,
                            Validators.required
                        ],
                        imageUrl: [
                            product.imageUrl
                        ]
                    }));
        } else {
            this.productForm = this.formBuilder.group({
                name: [
                    null, {
                        validators: [Validators.required],
                        asyncValidators: [this.productNameValidator(this.productService)]
                    }
                ],
                amountInStock: [
                    null,
                    [Validators.required, Validators.min(0)]
                ],
                unitOfMeasure: [
                    null,
                    [Validators.required]
                ],
                expirationDate: [
                    null,
                    [Validators.required]
                ],
                imageUrl: [null]
            });
        }
    }

    saveProduct(): void {
        if (this.existingId) {
            this.productService
                .updateProducts({...this.productForm?.value, id: this.existingId})
                .subscribe(product => this.router.navigate(['/product-detail', product.id]));
        } else {
            this.productService
                .createProducts(this.productForm?.value)
                .subscribe(() => this.router.navigate(['/product-overview']));
        }
    }

    get isEditing(): boolean {
        return !!this.existingId;
    }

    productNameValidator(productService: ProductService): ValidatorFn {
        return (control: AbstractControl): Observable<ValidationErrors | null> => {
            const productName = control.value;
            return productService
                .getAllProducts()
                .pipe(map((products: Product[]) => {
                        const existingProduct = products.find(product => product.name === productName);
                        return existingProduct ? { duplicateName: true } : null;
                }));
        };
    }
}

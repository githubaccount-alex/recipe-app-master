import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product';

@Injectable({
    providedIn: 'root'
})
export class ProductService {
    private url = 'http://localhost:8080/products';

    constructor(private http: HttpClient) { }

    getAllProducts(): Observable<Product[]> {
        return this.http.get<Product[]>(this.url);
    }

    getProductsById(id: number): Observable<Product> {
        return this.http.get<Product>(this.url + '/' + id)
    }

    createProducts(product: Product): Observable<Product> {
        return this.http.post<Product>(this.url, product)
    }

    deleteProducts(id?: number): Observable<any> {
        return this.http.delete<any>(this.url + '/' + id)
    }

    updateProducts(product: Product): Observable<Product> {
        return this.http.put<Product>(this.url, product)
    }
}

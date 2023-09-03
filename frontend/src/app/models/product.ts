import { UnitOfMeasure } from './unitOfMeasure';

export interface Product {
    id?: number;
    name: string;
    amountInStock: number;
    unitOfMeasure: UnitOfMeasure;
    expirationDate: Date;
    imageUrl?: String;
}

import { UnitOfMeasure } from './unitOfMeasure';

export interface Ingredient {
    id: number;
    name: string;
    amountInRecipe: number;
    unitOfMeasure: UnitOfMeasure;
    enoughInStock: boolean;
}

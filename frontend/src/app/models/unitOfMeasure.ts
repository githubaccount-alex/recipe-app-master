export enum UnitOfMeasure {
    PIECE = "PIECE",
    GRAM = "GRAM",
    KILOGRAM = "KILOGRAM",
    MILLILITER = "MILLILITER",
    LITER = "LITER"
}

export function getUnitDisplayValue(unit: string): string {
    switch (unit) {
        case UnitOfMeasure.PIECE:
            return 'Stück';
        case UnitOfMeasure.GRAM:
            return 'g';
        case UnitOfMeasure.KILOGRAM:
            return 'kg';
        case UnitOfMeasure.MILLILITER:
            return 'ml';
        case UnitOfMeasure.LITER:
            return 'l';
        default:
            return 'unbekannt';
    }
}

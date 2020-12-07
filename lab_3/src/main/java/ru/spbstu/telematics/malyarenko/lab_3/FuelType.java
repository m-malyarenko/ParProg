public enum FuelType
{
    FUEL_92(42),
    FUEL_95(45),
    FUEL_DIESEL(49);

    private int _price;

    FuelType(int price) {
        _price = price;
    }

    public int getPrice() {
        return _price;
    }
}
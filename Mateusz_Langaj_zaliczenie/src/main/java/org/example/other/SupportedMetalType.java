package org.example.other;

public enum SupportedMetalType {
    COPPER(8960),
    TIN(7260),
    IRON(7870),
    LEAD(11300),
    SILVER(10500),
    TUNGSTEN(19300),
    GOLD(19300),
    PLATINUM(21500);

   private final double density;

     SupportedMetalType(double density) {
        this.density = density;
    }

    /**
     * @return Metal density kg/m^3.
     */
    public double getDensity() {
        return density;
    }
}
package org.example.enums;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SupportedMetalTypeTest {
    @Test
    public void testGetDensity() {
        assertEquals(8960, SupportedMetalType.COPPER.getDensity(), 0.001);
        assertEquals(7260, SupportedMetalType.TIN.getDensity(), 0.001);
        assertEquals(7870, SupportedMetalType.IRON.getDensity(), 0.001);
        assertEquals(11300, SupportedMetalType.LEAD.getDensity(), 0.001);
        assertEquals(10500, SupportedMetalType.SILVER.getDensity(), 0.001);
        assertEquals(19300, SupportedMetalType.TUNGSTEN.getDensity(), 0.001);
        assertEquals(19300, SupportedMetalType.GOLD.getDensity(), 0.001);
        assertEquals(21500, SupportedMetalType.PLATINUM.getDensity(), 0.001);
    }

    @Test
    public void testGetAllSupportedMetals() {
        HashSet<SupportedMetalType> supportedMetals = SupportedMetalType.getAllSupportedMetals();

        assertEquals(8, supportedMetals.size());

        assertTrue(supportedMetals.contains(SupportedMetalType.COPPER));
        assertTrue(supportedMetals.contains(SupportedMetalType.TIN));
        assertTrue(supportedMetals.contains(SupportedMetalType.IRON));
        assertTrue(supportedMetals.contains(SupportedMetalType.LEAD));
        assertTrue(supportedMetals.contains(SupportedMetalType.SILVER));
        assertTrue(supportedMetals.contains(SupportedMetalType.TUNGSTEN));
        assertTrue(supportedMetals.contains(SupportedMetalType.GOLD));
        assertTrue(supportedMetals.contains(SupportedMetalType.PLATINUM));
    }
}
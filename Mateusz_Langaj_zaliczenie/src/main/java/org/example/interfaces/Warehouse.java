package org.example.interfaces;

import org.example.other.ClientNotFoundException;
import org.example.other.FullWarehouseException;
import org.example.other.ProhibitedMetalTypeException;
import org.example.other.SupportedMetalType;
import java.util.List;
import java.util.Map;

public interface Warehouse {

    void addMetalIngot(String clientId, SupportedMetalType metalType, double mass)
            throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException;

    Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId);

    double getTotalVolumeOccupiedByClient(String clientId);

    List<SupportedMetalType> getStoredMetalTypesByClient(String clientId);

}

package org.example.implementation;

import org.example.other.ClientNotFoundException;
import org.example.other.FullWarehouseException;
import org.example.other.ProhibitedMetalTypeException;
import org.example.other.SupportedMetalType;
import org.example.interfaces.Warehouse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseImpl implements Warehouse {

    private Map<String, Map<SupportedMetalType, Double>> warehouseMap = new HashMap<>();
    private ClientImpl createClient;

    public WarehouseImpl(ClientImpl createClient) {
        this.createClient = createClient;
    }


  @Override
public void addMetalIngot(String clientId, SupportedMetalType metalType, double mass)
        throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException {
    if (!createClient.clientsMap.containsKey(clientId)) {
        throw new ClientNotFoundException("Klient o podanym ID nie istnieje.");
    }

    if (isProhibitedMetalType(metalType)) {
        throw new ProhibitedMetalTypeException("Ten metal jest zabroniony");
    }

    warehouseMap.putIfAbsent(clientId, new HashMap<>());

    Map<SupportedMetalType, Double> clientMetalMap = warehouseMap.get(clientId);

    if (isWarehouseFull(clientId, clientMetalMap, mass)) {
        throw new FullWarehouseException("Magazyn nie pomieści takiej ilości metalu.");
    }

    double totalMass = clientMetalMap.getOrDefault(metalType, 0.0) + mass;
    clientMetalMap.put(metalType, totalMass);

    if (isWarehouseFull(clientId, clientMetalMap, 0.0)) {
        clientMetalMap.remove(metalType);
        throw new FullWarehouseException("Magazyn nie pomieści takiej ilości metalu.");
    }
}

    @Override
    public Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId) {
        return warehouseMap.getOrDefault(clientId, new HashMap<>());
    }

    @Override
public double getTotalVolumeOccupiedByClient(String clientId) {
    if (!createClient.clientsMap.containsKey(clientId)) {
        throw new ClientNotFoundException("Klient o podanym ID nie istnieje.");
    }

    Map<SupportedMetalType, Double> clientMetalMap = warehouseMap.get(clientId);

    double totalVolume = clientMetalMap.entrySet().stream()
            .mapToDouble(entry -> entry.getKey().getDensity() * entry.getValue())
            .sum();

    return totalVolume;
}

    @Override
    public List<SupportedMetalType> getStoredMetalTypesByClient(String clientId) {
        if (!createClient.clientsMap.containsKey(clientId)) {
            throw new ClientNotFoundException("Klient o podanym ID nie istnieje.");
        }

        Map<SupportedMetalType, Double> clientMetalMap = warehouseMap.get(clientId);

        return new ArrayList<>(clientMetalMap.keySet());
    }

   private boolean isProhibitedMetalType(SupportedMetalType metalType) {
   
    SupportedMetalType[] allMetalTypes = SupportedMetalType.values();

    if (!Arrays.asList(allMetalTypes).contains(metalType)) {
        return true; 
    }
return false;
}

  private boolean isWarehouseFull(String clientId, Map<SupportedMetalType, Double> clientMetalMap, double massToAdd) {
     double maxAllowedDensity = createClient.clientsMap.get(clientId).getMaxAllowedDensity();


    for (Map.Entry<SupportedMetalType, Double> entry : clientMetalMap.entrySet()) {
        SupportedMetalType metalType = entry.getKey();
        
        if (metalType == null) {
            continue;
        }

        double totalMass = entry.getValue() + massToAdd;

        if (metalType.getDensity() * totalMass > maxAllowedDensity) {
            return true; 
        }
    }

    return false; 
}
}
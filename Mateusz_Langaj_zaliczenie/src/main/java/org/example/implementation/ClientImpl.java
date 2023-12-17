package org.example.implementation;

import org.example.other.ClientNotFoundException;
import org.example.interfaces.Clients;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientImpl implements Clients {

    public Map<String, Client> clientsMap = new HashMap<>();
     private double maxAllowedDensity = 250_000;
    private double maxAllowedDensityPremium = 1_000_000;

    public class Client {
        private String clientId;
        private String firstName;
        private String lastName;
        private LocalDate creationDate;
        private boolean isPremium;

        public Client(String clientId, String firstName, String lastName, LocalDate creationDate) {
            this.clientId = clientId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.creationDate = creationDate;
            this.isPremium = false; // Domyślnie ustawione na false
        }

        public String getClientId() {
            return clientId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public LocalDate getCreationDate() {
            return creationDate;
        }

        public boolean isPremium() {
            return isPremium;
        }

        public void setPremium(boolean premium) {
            isPremium = premium;
        }
        
        public double getMaxAllowedDensity() {
            return isPremium ? maxAllowedDensityPremium : maxAllowedDensity;
        }
    }

    @Override
    public String createNewClient(String firstName, String lastName) {
        String clientId = generateClientId();
        Client newClient = new Client(clientId, firstName, lastName, LocalDate.now());
        clientsMap.put(clientId, newClient);
        return clientId;
    }

    @Override
    public String activatePremiumAccount(String clientId) {
        if (!clientsMap.containsKey(clientId)) {
            throw new ClientNotFoundException("Klient o podanym ID nie istnieje.");
        }

        clientsMap.get(clientId).setPremium(true);
        return clientId;
    }

    @Override
    public String getClientFullName(String clientId) {
        if (!clientsMap.containsKey(clientId)) {
           throw new ClientNotFoundException("Klient o podanym ID nie istnieje.");
        }

        Client client = clientsMap.get(clientId);
        return client.getFirstName() + " " + client.getLastName();
    }

    @Override
    public LocalDate getClientCreationDate(String clientId) {
        if (!clientsMap.containsKey(clientId)) {
            throw new ClientNotFoundException("Klient o podanym ID nie istnieje.");
        }

        return clientsMap.get(clientId).getCreationDate();
    }

    @Override
    public boolean isPremiumClient(String clientId) {
        if (!clientsMap.containsKey(clientId)) {
           throw new ClientNotFoundException("Klient o podanym ID nie istnieje.");
        }

        return clientsMap.get(clientId).isPremium();
    }

    @Override
    public int getNumberOfClients() {
        return clientsMap.size();
    }

    @Override
    public int getNumberOfPremiumClients() {
        return (int) clientsMap.values().stream().filter(Client::isPremium).count();
    }

 private String generateClientId() {
        return "Cl_" + UUID.randomUUID().toString();
    }
}
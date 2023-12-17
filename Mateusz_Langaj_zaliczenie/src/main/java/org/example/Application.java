package org.example;

import org.example.other.ClientNotFoundException;
import org.example.implemantations.ClientImpl;
import org.example.implemantations.WarehouseImpl;
import org.example.interfaces.Warehouse;
import org.example.other.FullWarehouseException;
import org.example.other.ProhibitedMetalTypeException;
import org.example.other.SupportedMetalType;


import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Projekt {

    public static void main(String[] args) {
        ClientImpl createClient = new ClientImpl();
        Warehouse warehouse = new WarehouseImpl(createClient);

        Scanner scanner = new Scanner(System.in);

        int choice = 0;

        while (choice != 12) { 
            System.out.println("Menu:");
            System.out.println("1. Dodaj nowego klienta");
            System.out.println("2. Aktywuj konto premium");
            System.out.println("3. Pobierz pełne imię i nazwisko klienta");
            System.out.println("4. Pobierz datę utworzenia klienta");
            System.out.println("5. Sprawdź, czy klient ma konto premium");
            System.out.println("6. Pobierz liczbę klientów");
            System.out.println("7. Pobierz liczbę klientów z kontem premium");
            System.out.println("8. Dodaj metal do magazynu klienta");
            System.out.println("9. Pobierz liste metali i ich liczbę klienta");
            System.out.println("10. Pobierz liczbe zajętego miejsca w magazynie klienta");
            System.out.println("11. Pobierz samą liste metali klienta");
            System.out.println("12. Wyjdź z programu");

            System.out.print("Wybierz opcję: ");
            choice = scanner.nextInt();
 try {
            switch (choice) {
                case 1:
                    System.out.print("Podaj imię klienta: ");
                    String firstName = scanner.next();
                    System.out.print("Podaj nazwisko klienta: ");
                    String lastName = scanner.next();

                    String clientId = createClient.createNewClient(firstName, lastName);
                    System.out.println("Utworzono klienta. ID: " + clientId);
                    break;

                case 2:
                    System.out.print("Podaj ID klienta do aktywacji konta premium: ");
                    String premiumClientId = scanner.next();

                    try {
                        createClient.activatePremiumAccount(premiumClientId);
                        System.out.println("Aktywowano konto premium dla klienta o ID: " + premiumClientId);
                    } catch (ClientNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Podaj ID klienta: ");
                    String fullNameClientId = scanner.next();

                    try {
                        String fullName = createClient.getClientFullName(fullNameClientId);
                        System.out.println("Pełne imię i nazwisko klienta: " + fullName);
                    } catch (ClientNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Podaj ID klienta: ");
                    String creationDateClientId = scanner.next();

                    try {
                        String creationDate = createClient.getClientCreationDate(creationDateClientId).toString();
                        System.out.println("Data utworzenia klienta: " + creationDate);
                    } catch (ClientNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    System.out.print("Podaj ID klienta: ");
                    String isPremiumClientId = scanner.next();

                    try {
                        boolean isPremium = createClient.isPremiumClient(isPremiumClientId);
                        if (isPremium) {
            System.out.println("Klient ma konto premium.");
        } else {
            System.out.println("Klient nie ma konta premium.");
        }
                    } catch (ClientNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                    
                     case 6:
                         
                        int NumberClients = createClient.getNumberOfClients();
                        System.out.println("Lista klientów: " + NumberClients);
                   
                    break;
                    
                     case 7:

                    int NumberofPremiumClients = createClient.getNumberOfPremiumClients();
                        System.out.println("Lista klientów premium: " + NumberofPremiumClients);
                   
                    break;
                    
                    case 8:
                    System.out.print("Podaj ID klienta: ");
                    String addMetalClientId = scanner.next();

                    System.out.print("Podaj typ metalu (COPPER, TIN, IRON, itp.): ");
                    SupportedMetalType metalType = SupportedMetalType.valueOf(scanner.next().toUpperCase());

                    System.out.print("Podaj masę metalu (w kg): ");
                    double metalMass = scanner.nextDouble();

                    try {
                        warehouse.addMetalIngot(addMetalClientId, metalType, metalMass);
                        System.out.println("Dodano metal do magazynu dla klienta o ID: " + addMetalClientId);
                    } catch (ClientNotFoundException | ProhibitedMetalTypeException | FullWarehouseException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                    case 9:
                    System.out.print("Podaj ID klienta: ");
                    String typeofMetalClientId = scanner.next();

                    try {
                       String metalTypes = warehouse.getMetalTypesToMassStoredByClient(typeofMetalClientId).toString();
                        System.out.println("Lista metali z ich ilością (w kg): " + metalTypes);
                    } catch (ClientNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                    case 10:
                    System.out.print("Podaj ID klienta: ");
                    String VolumeClientId = scanner.next();

                    try {
                       double Volume = warehouse.getTotalVolumeOccupiedByClient(VolumeClientId);
                         double maxAllowedDensity = createClient.clientsMap.get(VolumeClientId).getMaxAllowedDensity();
                        System.out.println("Zajęte miejsce w magazynie: " + Volume + "/"+maxAllowedDensity);
                    } catch (ClientNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    
                    break;
                    
                    case 11:
                    System.out.print("Podaj ID klienta: ");
                    String getStoredClientId = scanner.next();

                    try {
                       String metalTypes = warehouse.getStoredMetalTypesByClient(getStoredClientId).toString();
                        System.out.println("Lista metali: " + metalTypes);
                    } catch (ClientNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 12:
                    System.out.println("Wyjście z programu.");
                    break;

                default:
                    System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
                    break;
                    
              }
    } catch (java.util.InputMismatchException e) {
        System.out.println("Wprowadzono nieprawidłowe dane. Spróbuj ponownie.");
        scanner.nextLine();
    } catch (Exception e) {
        System.out.println("Wystąpił błąd: " + e.getMessage());
        // przechwytywaie błędów nie działa bo nie :/
    }
            
        }

        scanner.close();
    }
}
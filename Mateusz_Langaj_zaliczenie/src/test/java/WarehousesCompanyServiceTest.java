import mycompany.implemantations.ClientImpl;
import mycompany.implemantations.WarehouseImpl;
import mycompany.other.ClientNotFoundException;
import mycompany.other.FullWarehouseException;
import mycompany.other.ProhibitedMetalTypeException;
import mycompany.other.SupportedMetalType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseImplTest {

    private WarehouseImpl warehouse;
    private ClientImpl client;

    @BeforeEach
    void setUp() {
        client = new ClientImpl();
        warehouse = new WarehouseImpl(client);
    }

    @Test
    void addMetalIngot_validInput_metalAdded() throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException {
        String clientId = client.createNewClient("John", "Doe");

        warehouse.addMetalIngot(clientId, SupportedMetalType.COPPER, 10.0);

        Map<SupportedMetalType, Double> metalMap = warehouse.getMetalTypesToMassStoredByClient(clientId);
        assertTrue(metalMap.containsKey(SupportedMetalType.COPPER));
        assertEquals(10.0, metalMap.get(SupportedMetalType.COPPER), 0.001);
    }

    @Test
    void addMetalIngot_unknownClient_exceptionThrown() {
        assertThrows(ClientNotFoundException.class, () ->
                warehouse.addMetalIngot("unknownClient", SupportedMetalType.COPPER, 10.0));
    }

    @Test
    void addMetalIngot_prohibitedMetalType_exceptionThrown() {
        String clientId = client.createNewClient("Alice", "Smith");

        assertThrows(ProhibitedMetalTypeException.class, () ->
                warehouse.addMetalIngot(clientId, SupportedMetalType.MERCURY, 5.0));
    }

    @Test
    void addMetalIngot_fullWarehouse_exceptionThrown() {
        String clientId = client.createNewClient("Bob", "Johnson");

        // Załóżmy, że magazyn może pomieścić tylko 5 kg miedzi
        warehouse.setMaxAllowedDensity(5.0);

        // Dodajemy 4 kg miedzi, to powinno się udać
        assertDoesNotThrow(() -> warehouse.addMetalIngot(clientId, SupportedMetalType.COPPER, 4.0));

        // Dodajemy kolejne 2 kg miedzi, to powinno spowodować przekroczenie pojemności
        assertThrows(FullWarehouseException.class, () ->
                warehouse.addMetalIngot(clientId, SupportedMetalType.COPPER, 2.0));
    }

    // Dodaj inne testy w zależności od potrzeb
}
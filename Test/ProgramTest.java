import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramTest {

    BestGymEver bge = new BestGymEver(true);

    @Test
    public void clientObjectTest()  {

        Client client = new Client("7703021234, Alhambra Aromes\n2024-07-01");
        LocalDate clientDate = LocalDate.parse("2024-07-01");
        assertEquals(client.getName(),"Alhambra Aromes");
        assertEquals(client.getNumber(),"7703021234");
        assertEquals(client.getJoinDate(),clientDate);
    }

    @Test
    public void clientListSearch()  {
        Client c1 = new Client("7703021234, Alhambra Aromes\n2024-07-01");
        Client c2 = new Client("8204021234, Bear Belle\n2019-12-02");
        Client c3 = new Client("8512021234, Chamade Coriola \n2018-03-12");
        Client c4 = new Client("7608021234, Diamanda Djedi\n2024-01-30");
        List<Client> clientList = Arrays.asList(c1,c2,c3,c4);
        String foundName1 = "Alhambra Aromes";
        String foundName2 = " dIamanda dJedi ";
        String foundNumber1 = "8512021234";
        String foundNumber2 = "8204021234";
        String notFoundName = "Erik Söderlind";
        assertEquals(c1,bge.objectListSearch(clientList,foundName1));
        assertEquals(c2,bge.objectListSearch(clientList,foundNumber2));
        assertEquals(c3,bge.objectListSearch(clientList,foundNumber1));
        assertEquals(c4,bge.objectListSearch(clientList,foundName2));
        assertNull(bge.objectListSearch(clientList,notFoundName));

    }

    @Test
    public void compareNameToListTest() {
        String foundName1 = "Alhambra Aromes";
        String foundName2 = " alhAmBra aromEs ";
        Path pa = Path.of("Test/TestClientList.txt");
        assertEquals("7703021234, Alhambra Aromes\n2024-07-01", bge.listSearch(foundName1, pa));
        assertEquals("7703021234, Alhambra Aromes\n2024-07-01", bge.listSearch(foundName2, pa));
        assertNotEquals("7703021234, Alhambra Aromes\n2024-07-01", bge.listSearch(" Al",pa));
        assertNotEquals("7703021234, Alhambra Aromes\n2024-07-01", bge.listSearch("es ",pa));
        assertNotEquals("7703021234, Alhambra Aromes\n2024-07-01", bge.listSearch("ham",pa));
    }

    @Test
    public void compareNumberToListTest() {
        String foundNumber = " 7703021234 ";
        Path pa = Path.of("Test/TestClientList.txt");
        assertEquals("7703021234, Alhambra Aromes\n2024-07-01", bge.listSearch(foundNumber, pa));
        assertNotEquals("7703021234, Alhambra Aromes\n2024-07-01",bge.listSearch("77",pa));
        assertNotEquals("7703021234, Alhambra Aromes\n2024-07-01",bge.listSearch("34 ",pa));
        assertNotEquals("7703021234, Alhambra Aromes\n2024-07-01",bge.listSearch("0302",pa));
    }
    @Test
    public void compareNotFoundNameToListTest() {
        String notFoundName = "Erik Söderlind";
        Path pa = Path.of("Test/TestClientList.txt");
        assertEquals("Ingen sökträff!", bge.listSearch(notFoundName, pa));


    }
    @Test
    public void compareNotFoundNumberToListTest() {
        String notFoundNumber = "01898909";
        Path pa = Path.of("Test/TestClientList.txt");
        assertEquals("Ingen sökträff!", bge.listSearch(notFoundNumber, pa));

    }

    @Test
    public void convertStringToDateTest() {

        String clientInfo = "7703021234, Alhambra Aromes\n2024-07-01";
        LocalDate testDate = LocalDate.parse("2024-07-01");
        assertEquals(testDate,bge.clientInfoToDate(clientInfo));

    }

    @Test
    public void convertStringToNameNumberTest() {

        String clientInfo = "7703021234, Alhambra Aromes\n2024-07-01";
        String clientNameNumber = "7703021234, Alhambra Aromes";
        assertEquals(clientNameNumber,bge.clientInfoToNameNumber(clientInfo));

    }

    @Test
    public void activeClientDateTest() {
        LocalDate clientJoinDate = LocalDate.parse("2024-10-16");
        LocalDate currentDate = LocalDate.parse("2024-10-17");
        assertTrue(bge.dateValidater(currentDate,clientJoinDate));
    }

    @Test
    public void activeClientObjectDateTest() {
        Client client = new Client("7703021234, Alhambra Aromes\n2024-07-01");
        LocalDate currentDate = LocalDate.parse("2024-10-17");
        assertTrue(bge.dateValidater(currentDate,client.getJoinDate()));
    }

    @Test
    public void oneYearClientJoinTest() {
        Client client = new Client("7703021234, Alhambra Aromes\n2023-10-17");
        LocalDate currentDate = LocalDate.parse("2024-10-17");
        assertFalse(bge.dateValidater(currentDate,client.getJoinDate()));
    }

    @Test
    public void oldClientDateTest() {
        Client client = new Client("7703021234, Alhambra Aromes\n2022-10-17");
        LocalDate currentDate = LocalDate.parse("2024-10-17");
        assertFalse(bge.dateValidater(currentDate,client.getJoinDate()));
    }

    @Test
    public void addToListTest () throws IOException {

        Client client1 = new Client("7703021234, Alhambra Aromes\n2022-10-17");
        LocalDate clientAttendDate1 = LocalDate.parse("2024-10-17");
        Client client2 = new Client("0189890909, Erik Söderlind\n2021-03-13");
        LocalDate clientAttendDate2 = LocalDate.parse("2002-10-17");
        Path pa = Paths.get("Test/PTListTest.txt");
        Files.delete(pa);
        bge.addToPTList(client1,clientAttendDate1,pa);
        bge.addToPTList(client2,clientAttendDate2,pa);
        List<Client> testPTList = bge.creatClientObjectList(pa);
        assertEquals(client1.getName(),bge.objectListSearch(testPTList,"7703021234").getName());
        assertEquals(client2.getName(),bge.objectListSearch(testPTList,"0189890909").getName());

    }
}

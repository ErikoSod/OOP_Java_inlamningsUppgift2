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
    public void clientObjectCreatTest()  {

        Client client = new Client("7703021234, Alhambra Aromes\n2024-07-01");
        LocalDate currentDate = LocalDate.parse("2024-07-01");
        assertEquals(client.getName(),"Alhambra Aromes");
        assertEquals(client.getNumber(),"7703021234");
        assertEquals(client.getJoinDate(),currentDate);
    }

    @Test
    public void clientListSearchTest()  {
        Client c1 = new Client("7703021234, Alhambra Aromes\n2024-07-01");
        Client c2 = new Client("8204021234, Bear Belle\n2019-12-02");
        Client c3 = new Client("8512021234, Chamade Coriola \n2018-03-12");
        Client c4 = new Client("7608021234, Diamanda Djedi\n2024-01-30");
        List<Client> clientList = Arrays.asList(c1,c2,c3,c4);
        String foundNamec1 = "Alhambra Aromes";
        String foundNamec4 = " dIamanda dJedi ";
        String foundNumberc3 = "8512021234";
        String foundNumberc2 = "8204021234";
        String notFoundName = "Erik Söderlind";
        assertEquals(c1,bge.clientObjectListSearch(clientList,foundNamec1));
        assertEquals(c2,bge.clientObjectListSearch(clientList,foundNumberc2));
        assertEquals(c3,bge.clientObjectListSearch(clientList,foundNumberc3));
        assertEquals(c4,bge.clientObjectListSearch(clientList,foundNamec4));
        assertNull(bge.clientObjectListSearch(clientList,notFoundName));

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
        assertEquals(client1.getName(),bge.clientObjectListSearch(testPTList,"7703021234").getName());
        assertEquals(client2.getName(),bge.clientObjectListSearch(testPTList,"0189890909").getName());

    }

    @Test
    public void clientValidandPrintToPTListTest() throws IOException {
        LocalDate currentDate = LocalDate.parse("2024-10-17");
        Client clientSearch = new Client("7703021234, Alhambra Aromes\n2024-07-01");
        Client clientSearch2 = new Client("0189890909, Erik Söderlind\n2021-03-13");
        Client clientSearch3 = null;
        Path ptTestListpath = Paths.get("Test/PTListTest2.txt");
        assertEquals ("Aktiv medlem" ,bge.clientValidator(currentDate,ptTestListpath, clientSearch));
        assertEquals ("Inaktiv medlem" ,bge.clientValidator(currentDate,ptTestListpath, clientSearch2));
        assertEquals ("Ingen Sökträff!" ,bge.clientValidator(currentDate,ptTestListpath, clientSearch3));
    }

    @Test
    public void throwsIOExceptionTest()  {
        Path wrongPath = Paths.get("Test/wrongtest.txt");
        Throwable _ = assertThrows(IOException.class,()->bge.creatClientObjectList(wrongPath));
    }

    @Test
    public void throwsNullPointerExceptionTest()  {
        Throwable _ = assertThrows(NullPointerException.class,()->bge.creatClientObjectList(null));
    }
}

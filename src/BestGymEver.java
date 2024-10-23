import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BestGymEver {

    public BestGymEver(boolean test) throws NullPointerException{
        if (!test) {

            LocalDate today = LocalDate.now();
            Path clientTextList = Paths.get("src/ClientList.txt");
            Path ptlist = Paths.get("src/PTlist.txt");
            List<Client> clientsList = new ArrayList<>();

            try {
                clientsList = creatClientObjectList(clientTextList);
            }catch (IOException e){
                e.printStackTrace();
            }

            String searchString = JOptionPane.showInputDialog("Namn/Personnummer:");
            Client searchedClient = objectListSearch(clientsList,searchString);
            if (searchedClient != null) {
                if(dateValidater(today,searchedClient.getJoinDate())) {
                    System.out.println("Aktiv medlem");
                    addToPTList(searchedClient, today, ptlist);
                } else {
                    System.out.println("Inaktiv medlem");
                }
            }else {
                System.out.println("Ingen Sökträff!");
            }

        }
    }
    public String listSearch  (String searchString, Path filePath){

        String tempLine;
        String clientInfo = "Ingen sökträff!";

        try(BufferedReader br = new BufferedReader(new FileReader(filePath.toString()))) {

            while ((tempLine = br.readLine()) != null) {

                    String tempNumber = tempLine.substring(0, tempLine.indexOf(","));
                    String tempName = tempLine.substring(tempLine.indexOf(",")+1);

                    if (    tempLine.trim().equalsIgnoreCase(searchString.trim())||
                            tempName.trim().equalsIgnoreCase(searchString.trim())||
                            tempNumber.trim().equalsIgnoreCase(searchString.trim()) ) {

                        clientInfo = tempLine;
                        String clientJoinDate = br.readLine();
                        clientInfo = clientInfo + "\n" + clientJoinDate;
                        break;

                    }
                    br.readLine();
            }
            return clientInfo;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public LocalDate clientInfoToDate(String clientInfo){

        String dateString = clientInfo.substring(clientInfo.indexOf("\n")+1);
        return LocalDate.parse(dateString);
    }

    public String clientInfoToNameNumber(String clientInfo){
        return clientInfo.substring(0,clientInfo.indexOf("\n"));
    }

    public boolean dateValidater (LocalDate currentDate, LocalDate inputDate){

       LocalDate oneYearOld = currentDate.minusYears(1);
       return oneYearOld.isBefore(inputDate);

    }

    public void addToPTList (Client client,LocalDate clientAttendDate,Path path){

        try(BufferedWriter bf = new BufferedWriter(new FileWriter(path.toString(),true))){

            bf.write(client.getNumber()+", "+ client.getName() + "\n" + clientAttendDate);
            bf.newLine();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Client objectListSearch (List<Client> clientList,  String searchString){

        Client tempClient = null;
        for (Client client : clientList) {
            if (    client.getName().equalsIgnoreCase(searchString.trim())||
                    client.getNumber().equalsIgnoreCase(searchString.trim())) {
                tempClient= client;
            }
        }
        return tempClient;
    }

    public List<Client> creatClientObjectList(Path path) throws IOException {

        String templine;
        List<Client> clientList = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(path.toString()))) {

            while ((templine = br.readLine()) != null) {
                String clientInfo = templine;
                String clientJoinDate = br.readLine();
                clientInfo = clientInfo + "\n" + clientJoinDate;
                clientList.add(new Client(clientInfo));
            }
            return clientList;
        }
    }


    public static void main(String[] args) {
        while(true) {
            BestGymEver b = new BestGymEver(false);
        }
    }
}
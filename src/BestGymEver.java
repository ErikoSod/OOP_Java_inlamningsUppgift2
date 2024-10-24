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

            String searchString = JOptionPane.showInputDialog("Namn eller Personnummer:");
            Client searchedClient = clientObjectListSearch(clientsList,searchString);

            String returnMessage = clientValidator(today,ptlist,searchedClient);
            System.out.println(returnMessage);


        }
    }
    public String clientValidator (LocalDate today,Path ptTestListpath,Client clientSearch){
        if (clientSearch != null) {
            if(dateValidater(today,clientSearch.getJoinDate())) {
                addToPTList(clientSearch, today, ptTestListpath);
                return  "Aktiv medlem";
            } else {
                return  "Inaktiv medlem";
            }
        }else {
            return "Ingen Sökträff!";
        }
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

    public Client clientObjectListSearch (List<Client> clientList,  String searchString){

        Client tempClient = null;
        for (Client client : clientList) {
            if (    client.getName().equalsIgnoreCase(searchString.trim())||
                    client.getNumber().equalsIgnoreCase(searchString.trim())) {
                tempClient= client;
            }
        }
        return tempClient;
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

    public static void main(String[] args) {
        try {
            while (true) {
                BestGymEver b = new BestGymEver(false);
            }
        }
        catch (NullPointerException e) {
            System.out.println("Program stängs");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
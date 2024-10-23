import java.time.LocalDate;

public class Client {
    private final String name;
    private final String number;
    private final LocalDate date;

    public Client(String clientInfo) {
            this.name = clientInfo.substring(clientInfo.indexOf(" ")+1,clientInfo.indexOf("\n"));
            this.number = clientInfo.substring(0,clientInfo.indexOf(","));
            this.date = LocalDate.parse(clientInfo.substring(clientInfo.indexOf("\n")+1));
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getJoinDate() {
        return date;
    }
}

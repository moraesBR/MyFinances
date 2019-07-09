package senac.myfinances.models;
import java.time.LocalDate;

public class Finance {
    private LocalDate data;
    private SpendType type;
    private double incoming;

    public Finance(double incoming,SpendType type,LocalDate data) throws Exception {
        if(data == null) throw new Exception("Date is empty");
        if(incoming < 0) throw new Exception("Invalid incoming");
        if(type == null) throw new Exception("Invalid typing incoming");
        this.incoming = incoming;
        this.type = type;
        this.data = data;
    }

    public LocalDate getData() {
        return data;
    }

    public SpendType getType() {
        return type;
    }

    public double getIncoming() {
        return incoming;
    }
}

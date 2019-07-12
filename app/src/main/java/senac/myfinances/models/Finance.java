package senac.myfinances.models;
import java.time.LocalDate;

public class Finance implements Comparable<Finance>{
    private int id;
    private LocalDate data;
    private SpendType type;
    private boolean isSelected = false;
    private double incoming;

    public Finance(int id, double incoming,SpendType type,LocalDate data) throws Exception {
        if(data == null) throw new Exception("Date is empty");
        if(incoming < 0) throw new Exception("Invalid incoming");
        if(type == null) throw new Exception("Invalid typing incoming");
        this.id = id;
        this.incoming = incoming;
        this.type = type;
        this.data = data;
    }

    public int getId() {
        return id;
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

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public int compareTo(Finance finance) {
        return finance.data.compareTo(this.data);
    }
}

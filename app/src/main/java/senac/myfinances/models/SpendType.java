package senac.myfinances.models;

public enum SpendType {
    IN("gain"),
    OUT("spent");

    private final String spendtype;

    private SpendType(String spendtype){
        this.spendtype = spendtype;
    }

    public String getSpendtype(){
        return this.spendtype;
    }
}

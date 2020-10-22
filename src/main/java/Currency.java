public class Currency {
    private String charCode;
    private String nominal;
    private String name;
    private String value;

    public Currency(String charCode, String nominal, String name, String value) {
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public String getCharCode() {
        return this.charCode;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
    public String getNominal() {
        return this.nominal;
    }
}

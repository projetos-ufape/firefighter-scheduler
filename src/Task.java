public enum Task {
    FIRE("Incêndio"),
    SOS("Socorro"),
    PHONE("Telefone");

    public String name;

    Task(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.name;
    }
}

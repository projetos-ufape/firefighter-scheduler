public class Person {
    public String name;

    public int fireExpected = 0;
    public int fireAmount = 0;

    public int sosExpected = 0;
    public int sosAmount = 0;

    public int phoneExpected = 0;
    public int phoneAmount = 0;

    public Person(String line) {
        String[] fields = line.split(",");

        this.name = fields[0];
        this.fireExpected = Integer.parseInt(fields[1]);
        this.sosExpected = Integer.parseInt(fields[2]);
        this.phoneExpected = Integer.parseInt(fields[3]);
    }

    public  Boolean isFireOk() {
        return fireExpected == fireAmount;
    }

    public  Boolean isSosOk() {
        return sosExpected == sosAmount;
    }

    public  Boolean isPhoneOk() {
        return phoneExpected == phoneAmount;
    }
    @Override
    public String toString() {
        return name;
    }
}

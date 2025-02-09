package project_1;

public class WeekControl {
    private  Day[] week = new Day[7];
    private int amount = 0;

    public void set(int numberOfDay, Day day) {
        week[numberOfDay] = day;
        amount++;
    }

    public void remove(int numberOfDay) {
        week[numberOfDay] = null;
        amount--;
    }

    public Day get(int numberOfDay) {
        return week[numberOfDay];
    }

    public boolean isWeekValid() {
        return amount == 7;
    }
}

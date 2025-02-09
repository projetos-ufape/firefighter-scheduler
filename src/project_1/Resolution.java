package project_1;

import java.util.ArrayList;

public class Resolution {

//    private int currentDay = 0;
//
//    private int getCurreyDay() {
//        int result = this.currentDay;
//        if (result < 6) {
//            this.currentDay++;
//            return result;
//        }
//
//        throw new RuntimeException("FOI D+");
//    }

    public void execute(ArrayList<Person> people, StringBuilder saida) {
        Schedule schedule = new Schedule();
        this.acao(people, schedule, 0);

        for (int i = 0; i < schedule.week.length; i++) {
            for (int j = 0; j < schedule.week[i].length; j++) {
                saida.append(schedule.week[i][j] + " ");
            }
            saida.append("\n");
        }

        System.out.println(saida);

    }

    public void acao(ArrayList<Person> people, Schedule schedule, int currentDay) {
//        if (schedule.isValid()) return;
        for (int i = 0; i < schedule.week.length; i++) {
              for (int j = 0; j < schedule.week[i].length; j++) {
                  for (Person person : people) {
                      while ((i == 0 || i == 1) && schedule.week[i][j] == null && !person.isFireOk()) {
                          if (i == 0) {
                              if (schedule.week[3][j] == person || schedule.week[5][j] == person) {

                              }
                          }
                          schedule.week[i][j] = person;
                          person.fireAmount++;
                      }


                      while ((i == 2 || i == 3) && schedule.week[i][j] == null && !person.isSosOk()) {
                          schedule.week[i][j] = person;
                          person.sosAmount++;
                      }

                      while ((i == 4 || i == 5) && schedule.week[i][j] == null && !person.isPhoneOk()) {
                          schedule.week[i][j] = person;
                          person.phoneAmount++;
                      }
                  }

              }

        }



    }
}

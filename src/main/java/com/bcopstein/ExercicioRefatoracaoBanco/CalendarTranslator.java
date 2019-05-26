package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Calendar;

public class CalendarTranslator {

    private CalendarTranslator() {
    }

    public static String getCurrentMonth() {
        int x = Calendar.getInstance().get(Calendar.MONTH) + 1;
        switch (x) {
            case 1:
                return "Janeiro";

            case 2:
                return "Fevereiro";
            case 3:
                return "Março";
            case 4:
                return "Abril";
            case 5:
                return "Maio";
            case 6:
                return "Junho";
            case 7:
                return "Julho";
            case 8:
                return "Agosto";
            case 9:
                return "Setembro";
            case 10:
                return "Outubro";
            case 11:
                return "Novembro";
            case 12:
                return "Dezembro";
            default:
                throw new IllegalArgumentException();
        }

    }

    public static int getMonthValue(String strValue) {
        switch (strValue) {
            case "Janeiro":
                return 1;
            case "Fevereiro":
                return 2;
            case "Março":
                return 3;
            case "Abril":
                return 4;
            case "Maio":
                return 5;
            case "Junho":
                return 6;
            case "Julho":
                return 7;
            case "Agosto":
                return 8;
            case "Setembro":
                return 9;
            case "Outubro":
                return 10;
            case "Novembro":
                return 11;
            case "Dezembro":
                return 12;
            default:
                throw new IllegalArgumentException();
        }

    }

}

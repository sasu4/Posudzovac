package sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Posudok {

    private String crit1=" "; // Aktuálnosť a náročnosť zadanej témy
    private String crit2=" "; // Zorientovanie sa študenta v danej problematike predovšetkým analýzou domácej a zahraničnej literatúry
    private String crit3=" "; // Vhodnosť zvolených metód spracovania riešenej problematiky
    private String crit4=" "; // Formulácia cieľov práce a ich miera splnenia
    private String crit5=" "; // Rozsah a úroveň dosiahnutých výsledkov
    private String crit6=" "; // Analýza a interpretácia výsledkov a formulácia záverov práce
    private String crit7=" "; // Využiteľnosť výsledkov v praxi
    private String crit8=" "; // Prehľadnosť a logická štruktúra práce
    private String crit9=" "; // Formálna, jazyková a štylistická úroveň práce
    private String crit10=" "; // Prínos (silné stránky) práce
    private String crit11=" "; // Nedostatky (slabé stránky) práce
    private String crit12=" "; // Odporúčania, otázky alebo námety týkajúce sa obhajoby záverečnej práce
    private String crit13=" "; // Prácu v predloženej podobe obhajovať
    private String eval1=" ";
    private String eval2=" ";
    private String eval3=" ";
    private String eval4=" ";
    private String eval5=" ";
    private String eval6=" ";
    private String eval7=" ";
    private String eval8=" ";
    private String eval9=" ";
    private String complex_evaluation = " ";

    public Posudok(String filename) {
        try {
            BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream("data/"+filename),"UTF-8"));
            String riadok = ""; //alebo nacitat cely subor naraz a prehladavat oddelovace, v principe jeden subor, jeden posudok, cize takto to bude jeden riadok
            String text = "";
            while(riadok!=null) {
                riadok = file.readLine();
                if(riadok!=null) {
                    text = text + riadok;
                }
            }
            file.close();
            text = text.replaceAll("\\R", " ");
            String[] pom = text.split("\\|");
            crit1 = pom[0]; eval1 = pom[1]; crit2 = pom[2]; eval2 = pom[3]; crit3 = pom[4]; eval3 = pom[5]; crit4 = pom[6]; eval4 = pom[7];
            crit5 = pom[8]; eval5 = pom[9]; crit6 = pom[10]; eval6 = pom[11]; crit7 = pom[12]; eval7 = pom[13]; crit8 = pom[14]; eval8 = pom[15];
            crit9 = pom[16]; eval9 = pom[17]; crit10 = pom[18]; crit11 = pom[19]; crit12 = pom[20]; crit13 = pom[21]; complex_evaluation = pom[22];
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Posudok() {
    }

    public Posudok(String crit1, String crit2, String crit3, String crit4, String crit5, String crit6, String crit7, String crit8, String crit9, String crit10, String crit11, String crit12, String crit13, String eval1, String eval2, String eval3, String eval4, String eval5, String eval6, String eval7, String eval8, String eval9, String complex_evaluation) {
        this.crit1 = crit1; this.crit2 = crit2; this.crit3 = crit3; this.crit4 = crit4; this.crit5 = crit5; this.crit6 = crit6; this.crit7 = crit7;
        this.crit8 = crit8; this.crit9 = crit9; this.crit10 = crit10; this.crit11 = crit11; this.crit12 = crit12; this.crit13 = crit13;
        this.eval1 = eval1; this.eval2 = eval2; this.eval3 = eval3; this.eval4 = eval4; this.eval5 = eval5; this.eval6 = eval6; this.eval7 = eval7;
        this.eval8 = eval8; this.eval9 = eval9; this.complex_evaluation = complex_evaluation;
    }

    public void savePosudok(String crit1, String crit2, String crit3, String crit4, String crit5, String crit6, String crit7, String crit8, String crit9, String crit10, String crit11, String crit12, String crit13, String eval1, String eval2, String eval3, String eval4, String eval5, String eval6, String eval7, String eval8, String eval9, String complex_evaluation) {
        this.crit1 = crit1; this.crit2 = crit2; this.crit3 = crit3; this.crit4 = crit4; this.crit5 = crit5; this.crit6 = crit6; this.crit7 = crit7;
        this.crit8 = crit8; this.crit9 = crit9; this.crit10 = crit10; this.crit11 = crit11; this.crit12 = crit12; this.crit13 = crit13;
        this.eval1 = eval1; this.eval2 = eval2; this.eval3 = eval3; this.eval4 = eval4; this.eval5 = eval5; this.eval6 = eval6; this.eval7 = eval7;
        this.eval8 = eval8; this.eval9 = eval9; this.complex_evaluation = complex_evaluation;
    }

    public String toString() {
        String del = "|";
        return new StringBuilder().append(crit1).append(del).append(eval1).append(del)
                .append(crit2).append(del).append(eval2).append(del)
                .append(crit3).append(del).append(eval3).append(del)
                .append(crit4).append(del).append(eval4).append(del)
                .append(crit5).append(del).append(eval5).append(del)
                .append(crit6).append(del).append(eval6).append(del)
                .append(crit7).append(del).append(eval7).append(del)
                .append(crit8).append(del).append(eval8).append(del)
                .append(crit9).append(del).append(eval9).append(del)
                .append(crit10).append(del).append(crit11).append(del).append(crit12).append(del)
                .append(crit13).append(del).append(complex_evaluation).toString();
    }

    public String getCrit1() {
        return crit1;
    }

    public void setCrit1(String crit1) {
        this.crit1 = crit1;
    }

    public String getCrit2() {
        return crit2;
    }

    public void setCrit2(String crit2) {
        this.crit2 = crit2;
    }

    public String getCrit3() {
        return crit3;
    }

    public void setCrit3(String crit3) {
        this.crit3 = crit3;
    }

    public String getCrit4() {
        return crit4;
    }

    public void setCrit4(String crit4) {
        this.crit4 = crit4;
    }

    public String getCrit5() {
        return crit5;
    }

    public void setCrit5(String crit5) {
        this.crit5 = crit5;
    }

    public String getCrit6() {
        return crit6;
    }

    public void setCrit6(String crit6) {
        this.crit6 = crit6;
    }

    public String getCrit7() {
        return crit7;
    }

    public void setCrit7(String crit7) {
        this.crit7 = crit7;
    }

    public String getCrit8() {
        return crit8;
    }

    public void setCrit8(String crit8) {
        this.crit8 = crit8;
    }

    public String getCrit9() {
        return crit9;
    }

    public void setCrit9(String crit9) {
        this.crit9 = crit9;
    }

    public String getCrit10() {
        return crit10;
    }

    public void setCrit10(String crit10) {
        this.crit10 = crit10;
    }

    public String getCrit11() {
        return crit11;
    }

    public void setCrit11(String crit11) {
        this.crit11 = crit11;
    }

    public String getCrit12() {
        return crit12;
    }

    public void setCrit12(String crit12) {
        this.crit12 = crit12;
    }

    public String getCrit13() {
        return crit13;
    }

    public void setCrit13(String crit13) {
        this.crit13 = crit13;
    }

    public String getEval1() {
        return eval1;
    }

    public void setEval1(String eval1) {
        this.eval1 = eval1;
    }

    public String getEval2() {
        return eval2;
    }

    public void setEval2(String eval2) {
        this.eval2 = eval2;
    }

    public String getEval3() {
        return eval3;
    }

    public void setEval3(String eval3) {
        this.eval3 = eval3;
    }

    public String getEval4() {
        return eval4;
    }

    public void setEval4(String eval4) {
        this.eval4 = eval4;
    }

    public String getEval5() {
        return eval5;
    }

    public void setEval5(String eval5) {
        this.eval5 = eval5;
    }

    public String getEval6() {
        return eval6;
    }

    public void setEval6(String eval6) {
        this.eval6 = eval6;
    }

    public String getEval7() {
        return eval7;
    }

    public void setEval7(String eval7) {
        this.eval7 = eval7;
    }

    public String getEval8() {
        return eval8;
    }

    public void setEval8(String eval8) {
        this.eval8 = eval8;
    }

    public String getEval9() {
        return eval9;
    }

    public void setEval9(String eval9) {
        this.eval9 = eval9;
    }

    public String getComplex_evaluation() {
        return complex_evaluation;
    }

    public void setComplex_evaluation(String complex_evaluation) {
        this.complex_evaluation = complex_evaluation;
    }
}

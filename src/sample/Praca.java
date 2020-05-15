package sample;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

public class Praca {

    private String student;
    private String aprobacia;
    private String nazov;
    private String typ;
    private String akademicky_rok;
    private String skolitel;
    private String oponent;
    private String znamka_skolitel;
    private String znamka_oponent;
    private String file_skolitel;
    private String file_oponent;
    private Posudok posudok_skolitel, posudok_oponent;

    public Praca(String student, String aprobacia, String nazov, String typ, String akademicky_rok, String skolitel, String oponent) {
        this(student,aprobacia,nazov,typ,akademicky_rok,skolitel," "," ",oponent," "," ");
    }


    public Praca(String student, String aprobacia, String nazov, String typ, String akademicky_rok, String skolitel, String znamka_skolitel, String file_skolitel, String oponent, String znamka_oponent, String file_oponent) {
        this.student = student;
        this.aprobacia = aprobacia;
        this.nazov = nazov;
        this.typ = typ;
        this.akademicky_rok = akademicky_rok;
        this.skolitel = skolitel;
        this.oponent = oponent;
        this.znamka_skolitel = znamka_skolitel;
        this.znamka_oponent = znamka_oponent;
        this.file_skolitel = file_skolitel;
        this.file_oponent = file_oponent;
        if(!file_skolitel.equals(" ")) posudok_skolitel = new Posudok(file_skolitel);
        else posudok_skolitel = new Posudok();
        if(!file_oponent.equals(" ")) posudok_oponent = new Posudok(file_oponent);
        else posudok_oponent = new Posudok();
    }

    public String toString() {
        return getStudent()+";"+getAprobacia()+";"+getNazov()+";"+getTyp()+";"+getAkademicky_rok()+";"+getSkolitel()+";"+getZnamka_skolitel()+";"+getFile_skolitel()+";"+getOponent()+";"+getZnamka_oponent()+";"+getFile_oponent();
    }

    public void ulozPosudky() {
        try {
            if(file_skolitel.equals(" ")) file_skolitel = getStudent()+"_skolitel.dat";
            if(file_oponent.equals(" ")) file_oponent = getStudent()+"_oponent.dat";
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/"+file_skolitel), "UTF-8"));
            bw.write(posudok_skolitel.toString());
            bw.close();
            znamka_skolitel = posudok_skolitel.getComplex_evaluation();
            BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/"+file_oponent), "UTF-8"));
            bw2.write(posudok_oponent.toString());
            bw2.close();
            znamka_oponent = posudok_oponent.getComplex_evaluation();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getAprobacia() {
        return aprobacia;
    }

    public void setAprobacia(String aprobacia) {
        this.aprobacia = aprobacia;
    }

    public String getFile_skolitel() {
        return file_skolitel;
    }

    public String getOponent() {
        return oponent;
    }

    public void setOponent(String oponent) {
        this.oponent = oponent;
    }

    public void setFile_skolitel(String file_skolitel) {
        this.file_skolitel = file_skolitel;
    }

    public String getFile_oponent() {
        return file_oponent;
    }

    public void setFile_oponent(String file_oponent) {
        this.file_oponent = file_oponent;
    }

    public String getAkademicky_rok() {
        return akademicky_rok;
    }

    public void setAkademicky_rok(String akademicky_rok) {
        this.akademicky_rok = akademicky_rok;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getZnamka_skolitel() {
        return znamka_skolitel;
    }

    public void setZnamka_skolitel(String znamka_skolitel) {
        this.znamka_skolitel = znamka_skolitel;
    }

    public String getZnamka_oponent() {
        return znamka_oponent;
    }

    public void setZnamka_oponent(String znamka_oponent) {
        this.znamka_oponent = znamka_oponent;
    }


    public Posudok getPosudok_skolitel() {
        return posudok_skolitel;
    }

    public Posudok getPosudok_oponent() {
        return posudok_oponent;
    }

    public String getSkolitel() {
        return skolitel;
    }

    public void setSkolitel(String skolitel) {
        this.skolitel = skolitel;
    }
}

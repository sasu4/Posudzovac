package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class Main extends Application {

    private String version = "0.9.1";
    private TableView tabulka = new TableView();
    private ObservableList<Praca> zoznam = FXCollections.observableArrayList();
    ObservableList<String> ludia = FXCollections.observableArrayList("Zoltán Balogh", "Ľubomír Benko", "Mária Burianová","Martin Cápay","Martin Drlík","Jan Francisti","Dominik Halvoník","Jozef Kapusta","Nika Klimová","Michal Kohútek","Štefan Koprda","Roman Krnáč","Gabriela Lovászová","Martin Magdin","Viera Michaličková","Marián Mudrák","Michal Munk","Juraj Obonya","Jaroslav Reichel","Ján Skalka","Peter Švec","Júlia Tomanová","Tomáš Tóth","Milan Turčáni","Martin Vozár");

    @Override
    public void start(Stage primaryStage) throws Exception{
        HBox root = new HBox(10);
        VBox tlacidla = new VBox(10);

        tabulka.setEditable(false);
        tabulka.setMinWidth(1000);

        naplnTabulku();
        nacitajPrace();

        Label popis = new Label("Evidencia posudkov\nVersion "+version+"\n@ 2020 Ľubomír Benko");
        Button pridaj = new Button("Pridaj novú prácu");
        pridaj.setOnAction(e->{
            try {
                oknoNovaPraca();
            } catch (Exception er) {
                er.printStackTrace();
            }
        });

        Button posudokSK = new Button("Posudok školiteľa");
        posudokSK.setOnAction(e->{
            try {
                int index = tabulka.getSelectionModel().getFocusedIndex();
                System.out.println(index);
                if(index>-1) oknoPosudok(zoznam.get(index), 1, index);
            } catch (Exception er) {
                er.printStackTrace();
            }
        });

        Button posudokOP = new Button("Posudok oponenta");
        posudokOP.setOnAction(e->{
            try {
                int index = tabulka.getSelectionModel().getFocusedIndex();
                System.out.println(index);
                if(index>-1) oknoPosudok(zoznam.get(index), 0, index);
            } catch (Exception er) {
                er.printStackTrace();
            }
        });

        tlacidla.getChildren().addAll(popis, pridaj, posudokSK, posudokOP);
        root.getChildren().addAll(tabulka, tlacidla);
        primaryStage.setTitle("Evidencia posudkov ZP");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.setOnCloseRequest(e->ukonci(e,primaryStage));
        primaryStage.show();
    }

    private void oknoNovaPraca() {
        HBox groot = new HBox(10);
        Stage stage = new Stage();

        Label lab1 = new Label("Meno:");
        TextField meno = new TextField();
        meno.setPromptText("Zadaj meno študenta");
        Label lab8 = new Label("Aprobácia:");
        TextField apr = new TextField();
        Label lab2 = new Label("Názov práce:");
        TextField naz = new TextField();
        naz.setPromptText("Zadaj názov práce");
        Label lab3 = new Label("Typ práce:");
        ComboBox<String> type = new ComboBox();
        type.getItems().addAll("bakalárska práca","diplomová práca","rozsirujúca práca");
        Label lab4 = new Label("Akademický rok:");
        ComboBox<String> roky = new ComboBox();
        roky.getItems().addAll("2016/2017","2017/2018","2018/2019","2019/2020","2020/2021","2021/2022");
        Label lab6 = new Label("Školiteľ:");
        ComboBox<String> skolitelia = new ComboBox(ludia);
        new AutoComboBox<String>(skolitelia);
        Label lab7 = new Label("Oponent:");
        ComboBox<String> oponenti = new ComboBox(ludia);
        new AutoComboBox<String>(oponenti);
        Label lab5 = new Label("");
        lab5.setTextFill(Color.RED);
        Button create = new Button("Uložiť");
        //create.setStyle("-fx-background-color: #69d100;");
        create.setOnAction(e->{
            if(meno.getText()==null||meno.getText().length()==0||apr.getText()==null||apr.getText().length()==0||naz.getText()==null||naz.getText().length()==0||type.getValue()==null||roky.getValue()==null||skolitelia.getValue()==null||oponenti.getValue()==null) {
                lab5.setText("Musíte vyplniť všetky polia!");
            } else {
                zoznam.add(new Praca(
                        meno.getText(),
                        apr.getText(),
                        naz.getText(),
                        type.getValue(),
                        roky.getValue(),
                        skolitelia.getValue(),
                        oponenti.getValue()
                ));
                stage.close();
            }
        });
        Button back = new Button("Späť");
        back.setOnAction(e->zatvorStage1(stage));

        GridPane g = new GridPane();
        g.setHgap(5); g.setVgap(5);
        g.getColumnConstraints().add(new ColumnConstraints(5)); //0
        g.getColumnConstraints().add(new ColumnConstraints(100)); //1
        g.getColumnConstraints().add(new ColumnConstraints(280-100-20));  //2

        g.add(lab1,1,0); g.add(meno,2,0);
        g.add(lab8,1,1); g.add(apr,2,1);
        g.add(lab2,1,2); g.add(naz,2,2);
        g.add(lab3,1,3); g.add(type,2,3);
        g.add(lab4,1,4); g.add(roky,2,4);
        g.add(lab6,1,5); g.add(skolitelia,2,5);
        g.add(lab7,1,6); g.add(oponenti,2,6);
        g.add(create,1,7); g.add(back,2,7);
        g.add(lab5,2,8);



        groot.getChildren().addAll(g);
        stage.setTitle("Pridaj novú prácu");
        stage.setScene(new Scene(groot, 450,280));
        stage.setOnCloseRequest(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrdenie");
            alert.setHeaderText("Naozaj chcete opustiť vytváranie novej práce?");
            ButtonType buttonTypeOne = new ButtonType("ÁNO");
            ButtonType buttonTypeCancel = new ButtonType("NIE", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                stage.close();
            } else {
                e.consume();
            }
        });
        stage.show();
    }

    private void oknoPosudok(Praca praca, int koho, int index) {
        HBox groot = new HBox(5);
        groot.setPadding(new Insets(0,0,0,5));
        ScrollPane scrollPane = new ScrollPane(groot);
        scrollPane.setFitToWidth(true);
        BorderPane root = new BorderPane(scrollPane);
        Stage stage = new Stage();
        ObservableList<String> hodnotenia = FXCollections.observableArrayList("A", "B", "C", "D", "E", "Fx");

        Label lab1 = new Label("Aktuálnosť a náročnosť zadanej témy\n");
        TextArea crit1;
        if(koho==1) crit1 = new TextArea(praca.getPosudok_skolitel().getCrit1());
        else crit1 = new TextArea(praca.getPosudok_oponent().getCrit1());
        crit1.setPrefColumnCount(4); crit1.setPrefRowCount(4); crit1.setWrapText(true);
        ComboBox<String> eval1 = new ComboBox(hodnotenia);
        if(koho==1) eval1.setValue(praca.getPosudok_skolitel().getEval1());
        else eval1.setValue(praca.getPosudok_oponent().getEval1());

        Label lab2 = new Label("Zorientovanie sa študenta v danej problema-\ntike predovšetkým analýzou domácej a\nzahraničnej literatúry");
        TextArea crit2;
        if(koho==1) crit2 = new TextArea(praca.getPosudok_skolitel().getCrit2());
        else crit2 = new TextArea(praca.getPosudok_oponent().getCrit2());
        crit2.setPrefColumnCount(4); crit2.setPrefRowCount(4); crit2.setWrapText(true);
        ComboBox<String> eval2 = new ComboBox(hodnotenia);
        if(koho==1) eval2.setValue(praca.getPosudok_skolitel().getEval2());
        else eval2.setValue(praca.getPosudok_oponent().getEval2());

        Label lab3 = new Label("Vhodnosť zvolených metód spracovania\nriešenej problematiky");
        TextArea crit3;
        if(koho==1) crit3 = new TextArea(praca.getPosudok_skolitel().getCrit3());
        else crit3 = new TextArea(praca.getPosudok_oponent().getCrit3());
        crit3.setPrefColumnCount(4); crit3.setPrefRowCount(4); crit3.setWrapText(true);
        ComboBox<String> eval3 = new ComboBox(hodnotenia);
        if(koho==1) eval3.setValue(praca.getPosudok_skolitel().getEval3());
        else eval3.setValue(praca.getPosudok_oponent().getEval3());

        Label lab4 = new Label("Formulácia cieľov práce a ich miera splnenia");
        TextArea crit4;
        if(koho==1) crit4 = new TextArea(praca.getPosudok_skolitel().getCrit4());
        else crit4 = new TextArea(praca.getPosudok_oponent().getCrit4());
        crit4.setPrefColumnCount(4); crit4.setPrefRowCount(4); crit4.setWrapText(true);
        ComboBox<String> eval4 = new ComboBox(hodnotenia);
        if(koho==1) eval4.setValue(praca.getPosudok_skolitel().getEval4());
        else eval4.setValue(praca.getPosudok_oponent().getEval4());

        Label lab5 = new Label("Rozsah a úroveň dosiahnutých výsledkov");
        TextArea crit5;
        if(koho==1) crit5 = new TextArea(praca.getPosudok_skolitel().getCrit5());
        else crit5 = new TextArea(praca.getPosudok_oponent().getCrit5());
        crit5.setPrefColumnCount(4); crit5.setPrefRowCount(4); crit5.setWrapText(true);
        ComboBox<String> eval5 = new ComboBox(hodnotenia);
        if(koho==1) eval5.setValue(praca.getPosudok_skolitel().getEval5());
        else eval5.setValue(praca.getPosudok_oponent().getEval5());

        Label lab6 = new Label("Analýza a interpretácia výsledkov a \nformulácia záverov práce");
        TextArea crit6;
        if(koho==1) crit6 = new TextArea(praca.getPosudok_skolitel().getCrit6());
        else crit6 = new TextArea(praca.getPosudok_oponent().getCrit6());
        crit6.setPrefColumnCount(4); crit6.setPrefRowCount(4); crit6.setWrapText(true);
        ComboBox<String> eval6 = new ComboBox(hodnotenia);
        if(koho==1) eval6.setValue(praca.getPosudok_skolitel().getEval6());
        else eval6.setValue(praca.getPosudok_oponent().getEval6());

        Label lab7 = new Label("Využiteľnosť výsledkov v praxi\n\n");
        TextArea crit7;
        if(koho==1) crit7 = new TextArea(praca.getPosudok_skolitel().getCrit7());
        else crit7 = new TextArea(praca.getPosudok_oponent().getCrit7());
        crit7.setPrefColumnCount(4); crit7.setPrefRowCount(4); crit7.setWrapText(true);
        ComboBox<String> eval7 = new ComboBox(hodnotenia);
        if(koho==1) eval7.setValue(praca.getPosudok_skolitel().getEval7());
        else eval7.setValue(praca.getPosudok_oponent().getEval7());

        Label lab8 = new Label("Prehľadnosť a logická štruktúra práce");
        TextArea crit8;
        if(koho==1) crit8 = new TextArea(praca.getPosudok_skolitel().getCrit8());
        else crit8 = new TextArea(praca.getPosudok_oponent().getCrit8());
        crit8.setPrefColumnCount(4); crit8.setPrefRowCount(4); crit8.setWrapText(true);
        ComboBox<String> eval8 = new ComboBox(hodnotenia);
        if(koho==1) eval8.setValue(praca.getPosudok_skolitel().getEval8());
        else eval8.setValue(praca.getPosudok_oponent().getEval8());

        Label lab9 = new Label("Formálna, jazyková a štylistická úroveň práce");
        TextArea crit9;
        if(koho==1) crit9 = new TextArea(praca.getPosudok_skolitel().getCrit9());
        else crit9 = new TextArea(praca.getPosudok_oponent().getCrit9());
        crit9.setPrefColumnCount(4); crit9.setPrefRowCount(4); crit9.setWrapText(true);
        ComboBox<String> eval9 = new ComboBox(hodnotenia);
        if(koho==1) eval9.setValue(praca.getPosudok_skolitel().getEval9());
        else eval9.setValue(praca.getPosudok_oponent().getEval9());

        Label lab10 = new Label("Prínos (silné stránky) práce");
        TextArea crit10;
        if(koho==1) crit10 = new TextArea(praca.getPosudok_skolitel().getCrit10());
        else crit10 = new TextArea(praca.getPosudok_oponent().getCrit10());
        crit10.setPrefColumnCount(4); crit10.setPrefRowCount(4); crit10.setWrapText(true);

        Label lab11 = new Label("Nedostatky (slabé stránky) práce");
        TextArea crit11;
        if(koho==1) crit11 = new TextArea(praca.getPosudok_skolitel().getCrit11());
        else crit11 = new TextArea(praca.getPosudok_oponent().getCrit11());
        crit11.setPrefColumnCount(4); crit11.setPrefRowCount(4); crit11.setWrapText(true);

        Label lab12 = new Label("Odporúčania, otázky alebo námety týkajúce sa obhajoby záverečnej práce");
        TextArea crit12;
        if(koho==1) crit12 = new TextArea(praca.getPosudok_skolitel().getCrit12());
        else crit12 = new TextArea(praca.getPosudok_oponent().getCrit12());
        crit12.setPrefColumnCount(4); crit12.setPrefRowCount(4); crit12.setWrapText(true);

        Label lab13 = new Label("Prácu v predloženej podobe obhajovať");
        TextArea crit13;
        if(koho==1) crit13 = new TextArea(praca.getPosudok_skolitel().getCrit13());
        else crit13 = new TextArea(praca.getPosudok_oponent().getCrit13());
        crit13.setPrefColumnCount(4); crit13.setPrefRowCount(4); crit13.setWrapText(true);

        Label lab14 = new Label("Záverečnú prácu hodnotím");
        ComboBox<String> evalCOM = new ComboBox(hodnotenia);
        if(koho==1) evalCOM.setValue(praca.getPosudok_skolitel().getComplex_evaluation());
        else evalCOM.setValue(praca.getPosudok_oponent().getComplex_evaluation());

        Button back = new Button("Späť");
        back.setOnAction(e->zatvorStage(stage, praca));

        Button save = new Button("Uložiť a zavrieť");
        save.setOnAction(e->{
            if(koho==1) {
                praca.getPosudok_skolitel().savePosudok(crit1.getText(),crit2.getText(),crit3.getText(),crit4.getText(),crit5.getText(),crit6.getText(),crit7.getText(),crit8.getText(),crit9.getText(),crit10.getText(),crit11.getText(),crit12.getText(),crit13.getText(),eval1.getValue(),eval2.getValue(),eval3.getValue(),eval4.getValue(),eval5.getValue(),eval6.getValue(),eval7.getValue(),eval8.getValue(),eval9.getValue(),evalCOM.getValue());
            } else {
                praca.getPosudok_oponent().savePosudok(crit1.getText(),crit2.getText(),crit3.getText(),crit4.getText(),crit5.getText(),crit6.getText(),crit7.getText(),crit8.getText(),crit9.getText(),crit10.getText(),crit11.getText(),crit12.getText(),crit13.getText(),eval1.getValue(),eval2.getValue(),eval3.getValue(),eval4.getValue(),eval5.getValue(),eval6.getValue(),eval7.getValue(),eval8.getValue(),eval9.getValue(),evalCOM.getValue());
            }
            praca.ulozPosudky();
            zoznam.set(index,praca);
            stage.close();
        });

        Button notes = new Button("Poznámky");
        notes.setOnAction(e->{
            if(koho==1) {
                praca.getPosudok_skolitel().savePosudok(crit1.getText(),crit2.getText(),crit3.getText(),crit4.getText(),crit5.getText(),crit6.getText(),crit7.getText(),crit8.getText(),crit9.getText(),crit10.getText(),crit11.getText(),crit12.getText(),crit13.getText(),eval1.getValue(),eval2.getValue(),eval3.getValue(),eval4.getValue(),eval5.getValue(),eval6.getValue(),eval7.getValue(),eval8.getValue(),eval9.getValue(),evalCOM.getValue());
            } else {
                praca.getPosudok_oponent().savePosudok(crit1.getText(),crit2.getText(),crit3.getText(),crit4.getText(),crit5.getText(),crit6.getText(),crit7.getText(),crit8.getText(),crit9.getText(),crit10.getText(),crit11.getText(),crit12.getText(),crit13.getText(),eval1.getValue(),eval2.getValue(),eval3.getValue(),eval4.getValue(),eval5.getValue(),eval6.getValue(),eval7.getValue(),eval8.getValue(),eval9.getValue(),evalCOM.getValue());
            }
            praca.ulozPosudky();
            oknoPoznamky(praca, koho, index);
        });

        //rozmiestnenie textov do gridov
        GridPane g = new GridPane();
        g.getColumnConstraints().add(new ColumnConstraints(250)); //0
        g.getColumnConstraints().add(new ColumnConstraints(100)); //1
        g.getColumnConstraints().add(new ColumnConstraints(10));  //2
        g.getColumnConstraints().add(new ColumnConstraints(250)); //3
        g.getColumnConstraints().add(new ColumnConstraints(100)); //4
        g.getColumnConstraints().add(new ColumnConstraints(10));  //5
        g.getColumnConstraints().add(new ColumnConstraints(250)); //6
        g.getColumnConstraints().add(new ColumnConstraints(100)); //7

        //prvy stlpec
        g.add(lab1,0,0); g.add(eval1,1,0); g.add(crit1,0,1,2,1);
        g.add(lab2,0,2); g.add(eval2,1,2); g.add(crit2,0,3,2,1);
        g.add(lab3,0,4); g.add(eval3,1,4); g.add(crit3,0,5,2,1);
        g.add(lab4,0,6); g.add(eval4,1,6); g.add(crit4,0,7,2,1);
        g.add(lab5,0,8); g.add(eval5,1,8); g.add(crit5,0,9,2,1);

        //druhy stlpec
        g.add(lab6,3,0); g.add(eval6,4,0); g.add(crit6,3,1,2,1);
        g.add(lab7,3,2); g.add(eval7,4,2); g.add(crit7,3,3,2,1);
        g.add(lab8,3,4); g.add(eval8,4,4); g.add(crit8,3,5,2,1);
        g.add(lab9,3,6); g.add(eval9,4,6); g.add(crit9,3,7,2,1);

        //treti stlpec
        g.add(lab10,6,0,2,1); g.add(crit10,6,1,2,1);
        g.add(lab11,6,2,2,1); g.add(crit11,6,3,2,1);
        g.add(lab12,6,4,2,1); g.add(crit12,6,5,2,1);
        g.add(lab13,6,6,2,1); g.add(crit13,6,7,2,1);
        g.add(lab14,6,8); g.add(evalCOM,7,8);
        g.add(save,6,9); g.add(back,7,9);
        g.add(notes,4,9);



        groot.getChildren().addAll(g);
        stage.setTitle("Vypracuj posudok - "+praca.getStudent());
        stage.setScene(new Scene(root, 1200,625));
        stage.setOnCloseRequest(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrdenie");
            alert.setHeaderText("Naozaj chcete opustiť úpravu posudku?");
            ButtonType buttonTypeOne = new ButtonType("ÁNO");
            ButtonType buttonTypeCancel = new ButtonType("NIE", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                praca.ulozPosudky();
                zoznam.set(index,praca);
                stage.close();
            } else {
                e.consume();
            }
        });
        stage.show();
    }

    private void oknoPoznamky(Praca praca, int koho, int index) {
        HBox groot = new HBox(5);
        groot.setPadding(new Insets(0, 0, 0, 5));
        ScrollPane scrollPane = new ScrollPane(groot);
        scrollPane.setFitToHeight(true);
        BorderPane root = new BorderPane(scrollPane);
        Stage stage = new Stage();

        Label label = new Label("Poznámky k práci\n");
        TextArea text;
        if(koho==1) {
            praca.getPosudok_skolitel().nacitajPoznamka(praca.getStudent());
            text = new TextArea(praca.getPosudok_skolitel().getPoznamky());
        }
        else {
            praca.getPosudok_oponent().nacitajPoznamka(praca.getStudent());
            text = new TextArea(praca.getPosudok_oponent().getPoznamky());
        }
        text.setWrapText(true); text.setMaxWidth(380);

        Button back = new Button("Späť");
        back.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrdenie");
            alert.setHeaderText("Naozaj chcete opustiť úpravu poznámky?");
            ButtonType buttonTypeOne = new ButtonType("ÁNO");
            ButtonType buttonTypeCancel = new ButtonType("NIE", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                if(koho==1) praca.getPosudok_skolitel().ulozPoznamka(praca, text.getText());
                else praca.getPosudok_oponent().ulozPoznamka(praca, text.getText());
                stage.close();
            } else {
                e.consume();
            }
        });

        Button save = new Button("Uložiť a zavrieť");
        save.setOnAction(e->{
            if(koho==1) praca.getPosudok_skolitel().ulozPoznamka(praca, text.getText());
            else praca.getPosudok_oponent().ulozPoznamka(praca, text.getText());
            stage.close();
        });

        GridPane g = new GridPane();
        g.getColumnConstraints().add(new ColumnConstraints(200)); //0
        g.getColumnConstraints().add(new ColumnConstraints(200)); //1
        g.add(label,0,0,2,1); g.add(text,0,1,2,1);
        g.add(save,0,2); g.add(back,1,2);

        groot.getChildren().addAll(g);
        stage.setTitle("Vlastné poznámky - "+praca.getStudent());
        stage.setScene(new Scene(root, 400,250));
        stage.setOnCloseRequest(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrdenie");
            alert.setHeaderText("Naozaj chcete opustiť úpravu poznámky?");
            ButtonType buttonTypeOne = new ButtonType("ÁNO");
            ButtonType buttonTypeCancel = new ButtonType("NIE", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                if(koho==1) praca.getPosudok_skolitel().ulozPoznamka(praca, text.getText());
                else praca.getPosudok_oponent().ulozPoznamka(praca, text.getText());
                stage.close();
            } else {
                e.consume();
            }
        });
        stage.show();

    }


    private void zatvorStage(Stage stage, Praca praca) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrdenie");
        alert.setHeaderText("Naozaj chcete zatvoriť toto okno?");
        ButtonType buttonTypeOne = new ButtonType("ÁNO");
        ButtonType buttonTypeCancel = new ButtonType("NIE", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            praca.ulozPosudky();
            stage.close();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    private void zatvorStage1(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrdenie");
        alert.setHeaderText("Naozaj chcete zatvoriť toto okno?");
        ButtonType buttonTypeOne = new ButtonType("ÁNO");
        ButtonType buttonTypeCancel = new ButtonType("NIE", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            stage.close();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    private void naplnTabulku() {
        // vytvorenie hlaviciek
        TableColumn student = new TableColumn("Študent");
        TableColumn aprobacia = new TableColumn("Aprobácia");
        TableColumn nazov = new TableColumn("Názov práce");
        TableColumn posudky = new TableColumn("Posudok");
        TableColumn typ = new TableColumn("Typ práce");
        TableColumn rok = new TableColumn("Akad. rok");
        TableColumn men_skolitel = new TableColumn("Školiteľ prace");
        TableColumn skolitel = new TableColumn("Školiteľ");
        TableColumn oponent = new TableColumn("Oponent");
        posudky.getColumns().addAll(skolitel, oponent);
        tabulka.getColumns().addAll(student, aprobacia, nazov, typ, rok, men_skolitel, posudky);
        // naplnenie obsahu
        student.setCellValueFactory(new PropertyValueFactory<Praca, String>("student"));
        aprobacia.setCellValueFactory(new PropertyValueFactory<Praca, String>("aprobacia"));
        nazov.setCellValueFactory(new PropertyValueFactory<Praca, String>("nazov"));
        nazov.setMaxWidth(400);
        typ.setCellValueFactory(new PropertyValueFactory<Praca, String>("typ"));
        rok.setCellValueFactory(new PropertyValueFactory<Praca, String>("akademicky_rok"));
        men_skolitel.setCellValueFactory(new PropertyValueFactory<Praca, String>("skolitel"));
        skolitel.setCellValueFactory(new PropertyValueFactory<Praca, String>("znamka_skolitel"));
        oponent.setCellValueFactory(new PropertyValueFactory<Praca, String>("znamka_oponent"));
        tabulka.setItems(zoznam);
    }

    private void ukonci(WindowEvent e, Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrdenie");
        alert.setHeaderText("Naozaj chcete ukonciť program?");
        ButtonType buttonTypeOne = new ButtonType("ÁNO");
        ButtonType buttonTypeCancel = new ButtonType("NIE", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            ulozPrace();
            stage.close();
        } else {
            e.consume();
        }
    }

    private void nacitajPrace() {
        try {
            BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream("data/zoznam_prac.dat"),"UTF-8"));
            String riadok = "";
            while(riadok!=null) {
                riadok = file.readLine();
                if(riadok!=null) {
                    String[] pom = riadok.split(";");
                    zoznam.add(new Praca(pom[0], pom[1], pom[2], pom[3], pom[4], pom[5], pom[6], pom[7], pom[8], pom[9], pom[10]));
                }
            }
            file.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void ulozPrace() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/zoznam_prac.dat"), "UTF-8"));
            Iterator<Praca> iter = zoznam.iterator();
            while(iter.hasNext()) {
                Praca pol = iter.next();
                //pol.ulozPosudky(); //teoreticky zbytocne
                bw.write(pol.toString());
                bw.newLine();
            }
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

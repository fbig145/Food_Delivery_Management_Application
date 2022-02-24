package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Orders;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class reportsController {
    @FXML
    private Button cancelButton;
    @FXML
    private TextField startTF;
    @FXML
    private TextField endTF;
    @FXML
    private TextField numberTF;
    @FXML
    private TextField number2TF;
    @FXML
    private TextField amountTF;
    @FXML
    private  TextField dateTF;

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void generateButton1OnAction(ActionEvent event){
        hourReport();
    }

    public void generateButton2OnAction(ActionEvent event){
        appearReport();
    }
    public void generateButton3OnAction(ActionEvent event){
        clientsAndAmountReport();
    }
    public void generateButton4OnAction(ActionEvent event){
        searchDateRaport();
    }

    public void searchDateRaport(){
        Pattern pattern = Pattern.compile(",");
        try (Stream<String> lines = Files.lines(Path.of("src/orders.txt"))) {
            List<Orders> items = lines.map(line -> {
                String[] arr = pattern.split(line);
                return new Orders(
                        arr[0],
                        arr[1],
                        arr[2],
                        arr[3],
                        arr[4],
                        Integer.parseInt(arr[5].substring(0,2)));
            }).collect(Collectors.toList());

            List<Orders> auxProd = items
                    .stream()
                    .filter(c ->c.getDate().contains(dateTF.getText()))
                    .collect(Collectors.toList());

            auxProd.forEach(p->System.out.println(p));

            File file = new File("src/4.txt");
            FileWriter fw=new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw=new BufferedWriter(fw);
            //bw.write();
            bw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clientsAndAmountReport(){
        Pattern pattern = Pattern.compile(" ,");

        try (Stream<String> lines = Files.lines(Path.of("src/orders.txt"))) {

            List<Orders> items = lines.map(line -> {
                String[] arr = pattern.split(line);
                return new Orders(
                        arr[0],
                        arr[1],
                        arr[2],
                        arr[3],
                        arr[4],
                        Integer.parseInt(arr[5].substring(0,2)));
            }).filter(distinctByKey(p->p.getUsername()))
                    .collect(Collectors.toList());

            //items.forEach(p->System.out.println(p));
            int actualSize = items.size() + 1;

            List<Orders> auxProd = items
                    .stream()
                    .filter(p->Integer.parseInt(p.getPrice()) >= Integer.parseInt(amountTF.getText()))
                    .collect(Collectors.toList());


            if(actualSize >= Integer.parseInt(number2TF.getText()) ){
                //System.out.println(items.get(0).getFirstMeal());
                File file = new File("src/3.txt");
                FileWriter fw=new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw=new BufferedWriter(fw);
                bw.write(auxProd.get(0).getUsername());
                bw.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void appearReport(){
        Pattern pattern = Pattern.compile(",");

        try (Stream<String> lines = Files.lines(Path.of("src/orders.txt"))) {

            List<Orders> items = lines.map(line -> {
                String[] arr = pattern.split(line);
                return new Orders(
                        arr[0],
                        arr[1],
                        arr[2],
                        arr[3],
                        arr[4],
                        Integer.parseInt(arr[5].substring(0,2)));
            }).filter(distinctByKey(p->p.getFirstMeal()))
                    .collect(Collectors.toList());

            //items.forEach(p->System.out.println(p));
            int actualSize = items.size() + 1;

            if(actualSize >= Integer.parseInt(numberTF.getText()) ){
                //System.out.println(items.get(0).getFirstMeal());
                File file = new File("src/2.txt");
                FileWriter fw=new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw=new BufferedWriter(fw);
                bw.write(items.get(0).getFirstMeal() + " " + items.get(0).getSecondMeal());
                bw.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void hourReport(){
        Pattern pattern = Pattern.compile(",");

        try (Stream<String> lines = Files.lines(Path.of("src/orders.txt"))) {

            List<Orders> items = lines.map(line -> {
                String[] arr = pattern.split(line);
                return new Orders(
                        arr[0],
                        arr[1],
                        arr[2],
                        arr[3],
                        arr[4],
                        Integer.parseInt(arr[5].substring(0,2)));
            }).collect(Collectors.toList());

            int startTime = Integer.parseInt(startTF.getText().substring(0,2));
            int endTime = Integer.parseInt(endTF.getText().substring(0,2));

           // System.out.println(startTime);

            List<Orders> auxProd = items
                    .stream()
                    .filter(c -> c.getHour() >= startTime &&
                                    c.getHour() <= endTime)
                    .collect(Collectors.toList());


            auxProd.forEach(a->System.out.println(a));

//            FileOutputStream fileOut = new FileOutputStream("src/1.txt");
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//            objectOut.writeObject(auxProd);
//            objectOut.close();

            File file = new File("src/1.txt");
            FileWriter fw=new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(auxProd.toString());
            bw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) != null;
    }

}

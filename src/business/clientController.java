package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BaseProduct;
import model.CompositeProduct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class clientController {

    @FXML
    private Button cancelButton;
    @FXML
    private ListView productsView;
    @FXML
    private ListView compView;
    @FXML
    private TextField searchTextField;
    @FXML
    private TextField searchTextField1;
    @FXML
    private TextField searchTextField2;
    @FXML
    private TextField searchAddTextField;
    @FXML
    private Label priceLabel;
    @FXML
    private ListView cartList;
    @FXML
    private Label successLabel;
    int pr = 0;
    public String username;

    public void searchButtonOnAction(ActionEvent event){
        searchTitle();
        searchComposites();
    }
    public void searchButtonOnAction1(ActionEvent event){
        searchRating();
    }
    public void searchButtonOnAction2(ActionEvent event){
        searchMacros();
    }

    public void searchTitle(){
        productsView.getItems().clear();
        Pattern pattern = Pattern.compile(",");
        try (Stream<String> lines = Files.lines(Path.of("src/products.csv"))) {
            List<BaseProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new BaseProduct(
                        arr[0],
                        Float.valueOf(arr[1]),
                        Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]),
                        Integer.parseInt(arr[4]),
                        Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6]));
            }).filter(distinctByKey(p -> p.getTitle()))
                    .collect(Collectors.toList());

            List<BaseProduct> auxProd = products
                    .stream()
                    .filter(c ->c.getTitle().contains(searchTextField.getText()))
                    .collect(Collectors.toList());
            auxProd.forEach(a->productsView.getItems().add(a));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void searchRating(){
        productsView.getItems().clear();
        Pattern pattern = Pattern.compile(",");
        try (Stream<String> lines = Files.lines(Path.of("src/products.csv"))) {
            List<BaseProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new BaseProduct(
                        arr[0],
                        Float.valueOf(arr[1]),
                        Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]),
                        Integer.parseInt(arr[4]),
                        Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6]));
            }).filter(distinctByKey(p -> p.getTitle()))
                    .collect(Collectors.toList());

            List<BaseProduct> auxProd = products
                    .stream()
                    .filter(c->c.getRating() == Float.parseFloat(searchTextField1.getText()))
                    .collect(Collectors.toList());
            auxProd.forEach(a->productsView.getItems().add(a));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void searchMacros(){
        productsView.getItems().clear();
        Pattern pattern = Pattern.compile(",");

        try (Stream<String> lines = Files.lines(Path.of("src/products.csv"))) {

            List<BaseProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new BaseProduct(
                        arr[0],
                        Float.valueOf(arr[1]),
                        Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]),
                        Integer.parseInt(arr[4]),
                        Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6]));
            }).filter(distinctByKey(p -> p.getTitle()))
                    .collect(Collectors.toList());

            List<BaseProduct> auxProd = products
                    .stream()
                    .filter(c-> c.getCalories() == Integer.parseInt(searchTextField2.getText()) ||
                                c.getProteins() == Integer.parseInt(searchTextField2.getText()) ||
                                c.getFats() == Integer.parseInt(searchTextField2.getText()) ||
                                c.getSodium() == Integer.parseInt(searchTextField2.getText()) ||
                                c.getPrice() == Integer.parseInt(searchTextField2.getText()))
                    .collect(Collectors.toList());
            auxProd.forEach(a->productsView.getItems().add(a));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void viewProductsButtonOnAction(ActionEvent event) throws IOException {
        takeFromFile();
        takeFromComposite();
    }

    public void takeFromFile(){
        productsView.getItems().clear();
        Pattern pattern = Pattern.compile(",");
        try (Stream<String> lines = Files.lines(Path.of("src/products.csv"))) {
            List<BaseProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new BaseProduct(
                        arr[0],
                        Float.valueOf(arr[1]),
                        Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]),
                        Integer.parseInt(arr[4]),
                        Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6]));
            }).filter(distinctByKey(p -> p.getTitle()))
                    .collect(Collectors.toList());

            products.forEach(a -> productsView.getItems().add(a));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void takeFromComposite(){
        compView.getItems().clear();
        Pattern pattern = Pattern.compile(",");

        try (Stream<String> lines = Files.lines(Path.of("src/CompositeProducts.txt"))) {

            List<CompositeProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new CompositeProduct(
                        arr[0],
                        arr[1]);
            }).filter(distinctByKey(p -> p.getTitle()))
                    .collect(Collectors.toList());

            products.forEach(a -> compView.getItems().add(a));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void searchComposites(){
        compView.getItems().clear();
        Pattern pattern = Pattern.compile(",");

        try (Stream<String> lines = Files.lines(Path.of("src/CompositeProducts.txt"))) {

            List<CompositeProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new CompositeProduct(
                        arr[0],
                        arr[1]);
            }).filter(distinctByKey(p -> p.getTitle()))
                    .collect(Collectors.toList());

            List<CompositeProduct> auxProd = products
                    .stream()
                    .filter(c-> c.getTitle().startsWith(searchTextField.getText()) ||
                                c.getComposite().contains(searchTextField.getText()))
                    .collect(Collectors.toList());
            auxProd.forEach(a->compView.getItems().add(a));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addCartButtonOnAction(ActionEvent event){
        searchItem();
    }

    public void searchItem(){
        Pattern pattern = Pattern.compile(",");
        try (Stream<String> lines = Files.lines(Path.of("src/products.csv"))) {

            List<BaseProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new BaseProduct(
                        arr[0],
                        Float.valueOf(arr[1]),
                        Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]),
                        Integer.parseInt(arr[4]),
                        Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6]));
            }).filter(distinctByKey(p -> p.getTitle()))
                    .collect(Collectors.toList());

            List<BaseProduct> auxProd = products
                    .stream()
                    .filter(c ->c.getTitle().contains(searchAddTextField.getText()))
                    .collect(Collectors.toList());

            pr = pr + auxProd.get(0).getPrice();
           String price = Integer.toString(pr);
           priceLabel.setText(price);
           cartList.getItems().add(searchAddTextField.getText());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ,HH:mm");
        return formatter.format(date);
    }

    public void placeButtonOnAction(ActionEvent event) throws IOException {
        String finalStr = username;
        List<String> items = cartList.getItems();
        for( String item : items){
            finalStr = finalStr + " ," + item;
        }
        finalStr = finalStr + " ,";
        finalStr = finalStr + priceLabel.getText() + " ," +getDate() + "\n";
        Path path = Paths.get("src/orders.txt");

        if (Files.exists(path)) {
            Files.write(Paths.get("src/orders.txt"),
                    finalStr.getBytes(),
                    StandardOpenOption.APPEND);
        }
        successLabel.setText("ORDER PLACED SUCCESSFULLY");
    }

    public void transferUsername(String text){
        username = text;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
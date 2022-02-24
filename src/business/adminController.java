package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BaseProduct;
import model.CompositeProduct;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class adminController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button importButton;
    @FXML
    private ListView productsView;
    @FXML
    private Button addProductButton;
    @FXML
    private TextField addTtitle;
    @FXML
    private TextField addRating;
    @FXML
    private TextField addCalories;
    @FXML
    private TextField addProteins;
    @FXML
    private TextField addFat;
    @FXML
    private TextField addSodium;
    @FXML
    private TextField addPrice;

    @FXML
    private TextField deleteTtitle;
    @FXML
    private TextField deleteRating;
    @FXML
    private TextField deleteCalories;
    @FXML
    private TextField deleteProteins;
    @FXML
    private TextField deleteFat;
    @FXML
    private TextField deleteSodium;
    @FXML
    private TextField deletePrice;
    @FXML
    private Button deleteButton;

    @FXML
    private TextField searchTitle;
    @FXML
    private TextField searchRating;
    @FXML
    private TextField searchCalories;
    @FXML
    private TextField searchProteins;
    @FXML
    private TextField searchFat;
    @FXML
    private TextField searchSodium;
    @FXML
    private TextField searchPrice;

    @FXML
    private TextField modifyTitle;
    @FXML
    private TextField modifyRating;
    @FXML
    private TextField modifyCalories;
    @FXML
    private TextField modifyProteins;
    @FXML
    private TextField modifyFat;
    @FXML
    private TextField modifySodium;
    @FXML
    private TextField modifyPrice;

    @FXML
    private TextField compositeTitle;
    @FXML
    private TextArea compositeRest;
    @FXML
    private Button createButton;
    @FXML
    private ListView compositeView;

    public void modifyButtonOnAction(ActionEvent event) throws IOException {
        String lineToSearch = searchTitle.getText() + " ," + searchRating.getText() + "," + searchCalories.getText() + "," + searchProteins.getText() + "," + searchFat.getText() + "," + searchSodium.getText() + "," + searchPrice.getText();
        String lineToModify = modifyTitle.getText() + " ," + modifyRating.getText() + "," + modifyCalories.getText() + "," + modifyProteins.getText() + "," + modifyFat.getText() + "," + modifySodium.getText() + "," + modifyPrice.getText();
        try (Stream<String> lines = Files.lines(Path.of("src/products.csv"))) {
            List<String> replaced = lines
                    .map(line-> line.replaceAll(lineToSearch, lineToModify))
                    .collect(Collectors.toList());
            Files.write(Path.of("src/products.csv"), replaced);
        }
    }

    public void deleteButtonOnAction(ActionEvent event) throws IOException {
        deleteProduct();
    }

    public void deleteProduct() throws IOException {
        String lineToRemove = deleteTtitle.getText() + " ," + deleteRating.getText() + "," + deleteCalories.getText() + "," + deleteProteins.getText() + "," + deleteFat.getText() + "," + deleteSodium.getText() + "," + deletePrice.getText();
//        try (Stream<String> stream = Files.lines(Paths.get("src/products.csv"))) {
//            stream.filter(line->!line.trim().equals(lineToRemove));
//        }
        File file = new File("src/products.csv");
        List<String> out = Files.lines(file.toPath())
                .filter(line -> !line.contains(lineToRemove))
                .collect(Collectors.toList());
        Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

    }

    public void addProductButtonOnAction(ActionEvent event) throws IOException {
        String finalStr = addTtitle.getText() + " ," + addRating.getText() + "," + addCalories.getText() + "," + addProteins.getText() + "," + addFat.getText() + "," + addSodium.getText() + "," + addPrice.getText() + "\n";
//        BufferedWriter writer = new BufferedWriter(new FileWriter("src/products.csv"));
//        writer.write(finalStr);
//
//        writer.close();
        Path path = Paths.get("src/products.csv");

        if (Files.exists(path)) {
            Files.write(Paths.get("src/products.csv"),
                    finalStr.getBytes(),
                    StandardOpenOption.APPEND);
        }
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void importButtonOnAction(ActionEvent event) throws IOException {
        takeFromFile();
//        Stream<String> lines = Files.lines(Path.of("src/products.csv"));
//        lines.forEach(a -> productsView.getItems().add(a));
    }

    public void takeFromFile(){
        compositeView.getItems().clear();
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

//            products.forEach(a -> System.out.println(a));
            products.forEach(a -> productsView.getItems().add(a));
//            System.out.println(products.get(1).getTitle());
//            System.out.println(products.get(1));

//            products.stream()
//                    .forEach(System.out::println);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void createButtonOnAction(ActionEvent event) throws IOException {
        // SerializeProducts();
        // DeserializeProducts();

        String compProducts = compositeTitle.getText() + "," + compositeRest.getText() + "\n";

        Path path = Paths.get("src/CompositeProducts.txt");

        if (Files.exists(path)) {
            Files.write(Paths.get("src/CompositeProducts.txt"),
                    compProducts.getBytes(),
                    StandardOpenOption.APPEND);
        }
    }

    public void viewCompButtonOnAction(ActionEvent event){
        compositeView.getItems().clear();
        Pattern pattern = Pattern.compile(",");

        try (Stream<String> lines = Files.lines(Path.of("src/CompositeProducts.txt"))) {

            List<CompositeProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new CompositeProduct(
                        arr[0],
                        arr[1]);
            }).filter(distinctByKey(p -> p.getTitle()))
                    .collect(Collectors.toList());

            products.forEach(a -> compositeView.getItems().add(a));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void genButtonOnAction(ActionEvent event){
        openGen();
    }

    public void openGen(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../presentation/reports.fxml"));
            Stage genStage = new Stage();
            genStage.initStyle(StageStyle.UNDECORATED);
            genStage.setScene(new Scene(root, 781, 400));
            genStage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

//    ArrayList<CompositeProduct> compositeProduct = new ArrayList<>();
//
//    public void SerializeProducts(){
//
//        compositeProduct.add(new CompositeProduct(compositeTitle.getText(), compositeRest.getText()));
//       // CompositeProduct aux = new CompositeProduct(compositeTitle.getText(), compositeRest.getText());
//       // compositeProduct.add(aux);
//
//        try {
//            FileOutputStream fileOut = new FileOutputStream("src/CompositeProducts.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(compositeProduct);
//            out.close();
//            fileOut.close();
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
//    }
//
//    public void DeserializeProducts(){
//        try {
//           //compositeProduct = null;
//
//            FileInputStream fileIn = new FileInputStream("src/CompositeProducts.ser");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            compositeProduct = (ArrayList) in.readObject();
//            //System.out.println(in.readObject().toString());
//            in.close();
//            fileIn.close();
//        } catch (IOException i) {
//            i.printStackTrace();
//            return;
//        } catch (ClassNotFoundException c) {
//            c.printStackTrace();
//            return;
//        }
//
//        for(CompositeProduct ceva : compositeProduct){
//            System.out.println(ceva);
//        }
//
//    }

}

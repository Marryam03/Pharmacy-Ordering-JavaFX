import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import java.io.*;

public class Main extends Application {
    private static String fileName = "pharmacy.txt";
    private static int capacity;
    private static int numDrugs = 0;
    public static double totalSales = 0;
    //public Tab addDrugTab;
    private TabPane tabPane;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pharmacy System");
    tabPane = new TabPane();
        // Initialize controls for each tab
    TextField capacityField = new TextField();
    Button submitButton = new Button("Submit Capacity");
    Label resultCapacityLabel = new Label();
    
    TextField nameField = new TextField();
    TextField idFieldForAddDrug = new TextField();
    TextField categoryField = new TextField();
    TextField priceField = new TextField();
    TextField quantityFieldForAddDrug = new TextField();
    Button addDrugButton = new Button("Add drug");
    Label resultAddDrugLabel = new Label();

    TextField idFieldForRemoveDrug = new TextField();
    Button removeDrugButton = new Button("Remove drug");
    Label resultRemoveDrugLabel = new Label();

    TextField idFieldForOrder = new TextField();
    TextField quantityFieldForOrder = new TextField();
    TextField prescriptionForOrder = new TextField();
    Button placeOrderButton = new Button("Place Order");
    Label resultPlaceOrderLabel = new Label();
    Label drugDetailsLabel = new Label();

    Label totalSalesLabel = new Label();
    Button totalSalesButton = new Button("Get total sales of the day");

    Button exitButton = new Button("Exit");

        ChoiceBox<String> categoryChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
                "Cosmetics", "Prescription Drugs", "Other"
        ));

        TextField idFieldForSearchDrug = new TextField();
        Button searchDrugButton = new Button("Search drug");
        Label resultSearchDrugLabel = new Label();

        // Event handlers

    addDrugButton.setOnAction(e -> {
        try {
            int id = Integer.parseInt(idFieldForAddDrug.getText());
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityFieldForAddDrug.getText());
            if (id < 0 || price < 0 || quantity < 0) {
                resultAddDrugLabel.setText("ID, price or quantity cannot be less than zero.");
                return;
            }
            addDrug(nameField.getText(), id, categoryChoiceBox.getSelectionModel().getSelectedIndex() + 1, price, quantity);
            resultAddDrugLabel.setText("Drug added.");
            nameField.clear();
            idFieldForAddDrug.clear();
            categoryChoiceBox.getSelectionModel().clearSelection();
            priceField.clear();
            quantityFieldForAddDrug.clear();
        } catch (NumberFormatException ex) {
            resultAddDrugLabel.setText("Invalid ID, price or quantity value entered.");
        } catch (IOException ex) {
            resultAddDrugLabel.setText("Error adding drug: " + ex.getMessage());
        }
    });

    removeDrugButton.setOnAction(e -> {
        try {
            int id = Integer.parseInt(idFieldForRemoveDrug.getText());
            if (id < 0) {
                resultRemoveDrugLabel.setText("ID cannot be less than zero.");
                return;
            }
            removeDrug(id);
            resultRemoveDrugLabel.setText("Drug removed.");
            idFieldForRemoveDrug.clear();
        } catch (NumberFormatException ex) {
                        resultRemoveDrugLabel.setText("Invalid ID value entered.");
        } catch (IOException ex) {
            resultRemoveDrugLabel.setText("Error removing drug: " + ex.getMessage());
        }
    });

        searchDrugButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idFieldForSearchDrug.getText());
                if (id < 0) {
                    resultSearchDrugLabel.setText("ID cannot be less than zero.");
                    return;
                }
                String drug = searchDrug(id);
                if (drug != null) {
                    resultSearchDrugLabel.setText("Drug found: " + drug);
                } else {
                    resultSearchDrugLabel.setText("Drug not found.");
                }
                idFieldForSearchDrug.clear();
            } catch (NumberFormatException ex) {
                resultSearchDrugLabel.setText("Invalid ID value entered.");
            } catch (IOException ex) {
                resultSearchDrugLabel.setText("Error searching drug: " + ex.getMessage());
            }
        });

        placeOrderButton.setOnAction(e -> {
    try {
        int id = Integer.parseInt(idFieldForOrder.getText());
        int quantity = Integer.parseInt(quantityFieldForOrder.getText());
        String prescription = prescriptionForOrder.getText();

        String drugDetails = getDrugDetails(id);
        if(drugDetails == null) {
            resultPlaceOrderLabel.setText("Drug not found.");
            idFieldForOrder.clear();
            quantityFieldForOrder.clear();
            prescriptionForOrder.clear();
        } else {
            drugDetailsLabel.setText("Drug Details: " + drugDetails);
            placeOrder(id, quantity, prescription);
            resultPlaceOrderLabel.setText("Order placed.");
            idFieldForOrder.clear();
            quantityFieldForOrder.clear();
            prescriptionForOrder.clear();
        }
    } catch (IOException ex) {
        resultPlaceOrderLabel.setText("Error placing order: " + ex.getMessage());
    }
});
    totalSalesButton.setOnAction(e -> {
        totalSalesLabel.setText("Total sales is: " + totalSales);
    });
    
    exitButton.setOnAction(e -> primaryStage.close());
    
    // Set up tabs
    TabPane tabPane = new TabPane();

        // Tab for searching drugs
        VBox searchDrugBox = new VBox(10, new Label("Enter drug ID to search:\nThe Format is: Name, ID, Category, Price, Quantity\nFor the Category: 1 is for Cosmetics ; 2 is for Prescription ; 3 is for Others."), idFieldForSearchDrug, searchDrugButton, resultSearchDrugLabel);
        searchDrugBox.setAlignment(Pos.CENTER);
        Tab searchDrugTab = new Tab("Search Drug", searchDrugBox);
        searchDrugTab.setClosable(false);

            // Tab for setting capacity
    VBox capacityBox = new VBox(10, new Label("Enter capacity of pharmacy:"), capacityField, submitButton, resultCapacityLabel);
    capacityBox.setAlignment(Pos.CENTER);
    Tab capacityTab = new Tab("Set Capacity", capacityBox);
    capacityTab.setClosable(false);
    tabPane.getTabs().remove(capacityTab);
    //tabPane.getTabs().remove(capacityTab);
    //capacityTab.setVi<|endoftext|>
    

        // Tab for adding drugs
        VBox addDrugBox = new VBox(10, new Label("Enter drug name:"), nameField, new Label("Enter drug ID:"), idFieldForAddDrug, new Label("Select drug category:"),
                categoryChoiceBox, new Label("Enter drug price:"), priceField, new Label("Enter drug quantity:"), quantityFieldForAddDrug, addDrugButton, resultAddDrugLabel);
        addDrugBox.setAlignment(Pos.CENTER);
        Tab addDrugTab = new Tab("Add Drug", addDrugBox);
        addDrugTab.setClosable(false);
        // Event handlers
    submitButton.setOnAction(e -> {
        try {
            capacity = Integer.parseInt(capacityField.getText());
            if (capacity < 0) {
                resultCapacityLabel.setText("Capacity cannot be less than zero.");
                return;
            }
            capacityField.clear();
            initializeFile();
            resultCapacityLabel.setText("Capacity set successfully.");
            submitButton.setDisable(true);
            tabPane.getSelectionModel().select(addDrugTab);
            //capacityTab.setVisible(false);
          
        } catch (NumberFormatException ex) {
            resultCapacityLabel.setText("Invalid capacity value entered.");
        } catch (IOException ex) {
            resultCapacityLabel.setText("Failed to initialize the file: " + ex.getMessage());
        }
    });

    // Tab for removing drugs
    VBox removeDrugBox = new VBox(10, new Label("Enter drug ID to remove:"), idFieldForRemoveDrug, removeDrugButton, resultRemoveDrugLabel);
    removeDrugBox.setAlignment(Pos.CENTER);
    Tab removeDrugTab = new Tab("Remove Drug", removeDrugBox);
    removeDrugTab.setClosable(false);

    // Tab for placing orders
    VBox orderBox = new VBox(10, new Label("Enter drug ID to order:"), idFieldForOrder, drugDetailsLabel, new Label("Enter quantity to order:"), quantityFieldForOrder, new Label ("Add the prescription if it is a drug: "), prescriptionForOrder, placeOrderButton, resultPlaceOrderLabel);
    orderBox.setAlignment(Pos.CENTER);
    Tab orderTab = new Tab("Place Order", orderBox);
    orderTab.setClosable(false);

    // Tab for showing total sales
    VBox totalSalesBox = new VBox(10, totalSalesButton, totalSalesLabel);
    totalSalesBox.setAlignment(Pos.CENTER);
    Tab totalSalesTab = new Tab("Total Sales", totalSalesBox);
    totalSalesTab.setClosable(false);

    // Exit button
    VBox exitBox = new VBox(10, exitButton);
    exitBox.setAlignment(Pos.CENTER);
    Tab exitTab = new Tab("Exit", exitBox);
    exitTab.setClosable(false);

        tabPane.getTabs().addAll(capacityTab, addDrugTab, removeDrugTab, searchDrugTab, orderTab, totalSalesTab, exitTab);

        // Set the scene and show the stage
        Scene scene = new Scene(tabPane, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String searchDrug(int id) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] drugDetails = line.split(",");
            if (Integer.parseInt(drugDetails[1]) == id) {
                reader.close();
                return line;
            }
        }
        reader.close();
        return null;
    }

        // Method to initialize the pharmacy file.
    private static void initializeFile() throws IOException {
        // Checking if the file exists, and creating it if it doesn't.
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            // Writing the header line in the file.
            writer.write("Name,ID,Category,Price,Quantity");
            writer.newLine();
            writer.close();
        }
        // Reading the file line by line and counting the number of drugs.
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().equals("Name,ID,Category,Price,Quantity")) {
                numDrugs++;
            }
        }
        reader.close();
    }
    
// Method to add a new drug to the pharmacy file.
    private static void addDrug(String name, int id, int category, double price, int quantity) throws IOException {
        // Checking if pharmacy capacity is full.
        if (numDrugs >= capacity) {
            throw new IOException("Pharmacy is full, cannot add new drug.");
        }
        // Check if the drug with the same ID already exists.
        if (checkDrugExists(id)) {
            throw new IOException("Drug with this ID already exists.");
        }
        // Write the new drug details to the file.
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(name + "," + id + "," + category + "," + price + "," + quantity);
        writer.newLine();
        writer.close();
        // Increment the number of drugs in the pharmacy.
        numDrugs++;
    }

    // Method to check if a drug with a given ID exists in the pharmacy file.
    private static boolean checkDrugExists(int id) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                try {
                    int drugId = Integer.parseInt(parts[1]);
                    if (drugId == id) {
                        reader.close();
                        return true;
                    }
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid drug ID in file: " + e.getMessage());
                }
            }
        }
        reader.close();
        return false;
    }

    // Method to remove a drug from the pharmacy file.
private static void removeDrug(int id) throws IOException {
    // Create a temporary file to hold the remaining drugs after the removal.
    File inputFile = new File(fileName);
    File tempFile = new File("temp.txt");
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    // Read the pharmacy file line by line.
    String line;
    boolean found = false;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 2) {
            try {
                int drugId = Integer.parseInt(parts[1]);
                // If the drug to be removed is found, don't write it to the temporary file.
                if (drugId == id) {
                    found = true;
                    continue;
                }
            } catch (NumberFormatException e) {
                throw new IOException("Invalid drug ID in file: " + e.getMessage());
            }
        }
        // Write the line to the temporary file.
        writer.write(line);
        writer.newLine();
    }
    writer.close();
    reader.close();

    // If the drug was found and removed, replace the original file with the temporary file.
    if (found) {
        if (!inputFile.delete()) {
            throw new IOException("Failed to delete the original file");
        }
        if (!tempFile.renameTo(inputFile)) {
            throw new IOException("Failed to rename temporary file to the original file");
        }
        numDrugs--;
        System.out.println("Drug with ID " + id + " removed successfully.");
    } else {
        // If the drug was not found, delete the temporary file.
        tempFile.delete();
        throw new IOException("Drug not found.");
    }
}

    

private String getDrugDetails(int id) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    String line;
    while ((line = reader.readLine()) != null) {
        String[] drugDetails = line.split(",");
        if (Integer.parseInt(drugDetails[1]) == id) {
            reader.close();
            return "Drug Details:\n" + "Name: " + drugDetails[0] + "\n" + "Category: " + drugDetails[2] + "\n" + "Price: " + drugDetails[3] + "\n" + "Available Quantity: " + drugDetails[4];
        }
    }
    reader.close();
    return null;
}
    // Method to place an order for a drug.
private static void placeOrder(int id, int orderQuantity, String prescription) throws IOException {
    // Read the pharmacy file to find the drug.
    File inputFile = new File(fileName);
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    String line;
    boolean found = false;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 5) {
            try {
                int drugId = Integer.parseInt(parts[1]);
                if (drugId == id) {
                    found = true;
                    String name = parts[0];
                    // Parsing the details of the drug from the line.
                    int category = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    int quantity = Integer.parseInt(parts[4]);

                    // Display the drug's details before the ordering process
                    System.out.println("Drug Details: ");
                    System.out.println("Name: " + name);
                    System.out.println("Category: " + category);
                    System.out.println("Price: " + price);
                    System.out.println("Available Quantity: " + quantity);

                    // If the drug is out of stock, inform the user and return.
                    if (quantity <= 0) {
                        throw new IOException("Sorry, this drug is currently out of stock.");
                    }
                    // If the user wants to order more than what's in stock, inform the user and return.
                    if (orderQuantity > quantity) {
                        throw new IOException("Sorry, we only have " + quantity + " units in stock.");
                    }
                    // Deduct the ordered quantity from the stock.
                    quantity -= orderQuantity;
                    // If the drug is a cosmetic, add 20% to the price.
                    if (category == 1) {
                        price *= 1.2;
                    }
                    // Add the price of the ordered quantity to the total sales.
                    totalSales += price * orderQuantity;
                    // Update the quantity of the drug in the pharmacy file.
                    updateDrugQuantity(id, quantity);
                    return;
                }
            } catch (NumberFormatException e) {
                throw new IOException("Invalid drug ID in file: " + e.getMessage());
            }
        }
    }
    // If the drug was not found in the pharmacy file, inform the user.
    if (!found) {
        throw new IOException("Drug not found.");
    }
}
    // Method to update the quantity of a drug in the pharmacy file.
private static void updateDrugQuantity(int id, int newQuantity) throws IOException {
    // Create a temporary file to hold the updated drug quantities.
    File inputFile = new File(fileName);
    File tempFile = new File("temp.txt");
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    // Read the pharmacy file line by line.
    String line;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 5) {
            try {
                int drugId = Integer.parseInt(parts[1]);
                // If the drug to be updated is found, replace its quantity.
                if (drugId == id) {
                    parts[4] = String.valueOf(newQuantity);
                    line = String.join(",", parts);
                }
            } catch (NumberFormatException e) {
                throw new IOException("Invalid drug ID in file: " + e.getMessage());
            }
        }
        // Write the line to the temporary file.
        writer.write(line);
        writer.newLine();
    }
    writer.close();
    reader.close();
    // Replace the original file with the temporary file.
    inputFile.delete();
    tempFile.renameTo(inputFile);
}
}
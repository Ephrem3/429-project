//Specify package
package userinterface;

import java.sql.SQLException;
import java.util.Observable;
// system imports
import java.util.Properties;
import java.time.*;
// nothing
//git practice commit amend
import java.time.format.DateTimeFormatter;

import exception.InvalidPrimaryKeyException;
import javafx.event.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import model.ArticleType;
import model.*;
import model.InventoryItem;
import javafx.scene.Scene;
import javafx.scene.input.*;

// project imports
import impresario.IModel;

/*ADDINVENTORYITEMVIEW CLASS
 * Class contains view for adding InventoryItem Objects
 */
//======================================================================
public class AddInventoryItemView extends View {

    //error message
    private MessageView statusLog;

    //GUI Textfield components
    protected TextField barcodeField;
    protected TextField genderField;
    protected TextField articleField;
    protected TextField colorField;
    protected TextField color2Field;
    protected TextField sizeField;
    protected TextField brandField;
    protected TextField notesField;
    protected TextField fnameField;
    protected TextField lnameField;
    protected TextField phoneField;
    protected TextField emailField;

    protected String articleTypeId;

    protected String color1Id;

    protected String color2Id = "NULL";

    /*CONSTRUCTOR
     * Takes model Object
     */
    public AddInventoryItemView(IModel invenItem){

        super(invenItem, "AddInventoryItemView");
        String css = getClass().getResource("Styles.css").toExternalForm();
        getStylesheets().add(css);



        //Create container for view
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        container.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());

		// Error message area
		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);

        //Subscription will facilitate functionality
        myModel.subscribe("TransactionStatus", this);

	}//END CONSTRUCTOR================================================


    /*createTitle-------------------------------------------------------
     * create Title text
     */
    private Node createTitle() {
		Text titleText = new Text("Inventory Item Info:");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
		return titleText;
	}//End createTitle--------------------------------------------------

    /*createFormContents-------------------------------------------------
     * creates actual form for data entry
     * 
     */
    private VBox createFormContents() {
		
        VBox form = new VBox();

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

        //-----Barcode Label and Field-----
        Text barcodeLabel = new Text("Barcode: ");
        Font myFont = Font.font("Arial", FontWeight.BOLD, 16);
        barcodeLabel.setFont(myFont);
        barcodeLabel.setWrappingWidth(150);
        barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
        barcodeLabel.setFill(Color.DARKGREEN);
        grid.add(barcodeLabel, 0, 0);

        barcodeField = new TextField();
        barcodeField.setEditable(true);
        grid.add(barcodeField, 1, 0);

        //-----Gender Label and Field-----
        Text genderLabel = new Text("Gender (M/W): ");
        genderLabel.setFont(myFont);
        genderLabel.setWrappingWidth(150);
        genderLabel.setTextAlignment(TextAlignment.RIGHT);
        genderLabel.setFill(Color.DARKGREEN);
        grid.add(genderLabel, 0, 1);
        
        genderField = new TextField();
        genderField.setEditable(false);
        grid.add(genderField, 1, 1);

        //-----Article Label and Field-----
        Text articleLabel = new Text("Article Type (Barcode Prefix): ");
        articleLabel.setFont(myFont);
        articleLabel.setWrappingWidth(150);
        articleLabel.setTextAlignment(TextAlignment.RIGHT);
        articleLabel.setFill(Color.DARKGREEN);
        grid.add(articleLabel, 0, 2);

        articleField = new TextField();
        articleField.setEditable(false);
        grid.add(articleField, 1, 2);

        //-----Primary Color Label and Field-----
        Text colorLabel = new Text("Primary Color (Barcode Prefix): ");
        colorLabel.setFont(myFont);
        colorLabel.setWrappingWidth(150);
        colorLabel.setTextAlignment(TextAlignment.RIGHT);
        colorLabel.setFill(Color.DARKGREEN);
        grid.add(colorLabel, 0, 3);

        colorField = new TextField();
        colorField.setEditable(false);
        grid.add(colorField, 1, 3);

        //------Color2 Label and Field-----
        Text color2Label = new Text("Secondary Color (Barcode Prefix): ");
        color2Label.setFont(myFont);
        color2Label.setWrappingWidth(150);
        color2Label.setTextAlignment(TextAlignment.RIGHT);
        color2Label.setFill(Color.DARKGREEN);
        grid.add(color2Label, 0, 4);

        color2Field = new TextField();
        color2Field.setEditable(true);
        grid.add(color2Field, 1, 4);

        //-----Size Label and Field----
        Text sizeLabel = new Text("Size: ");
        sizeLabel.setFont(myFont);
        sizeLabel.setWrappingWidth(150);
        sizeLabel.setTextAlignment(TextAlignment.RIGHT);
        sizeLabel.setFill(Color.DARKGREEN);
        grid.add(sizeLabel, 0, 5);

        sizeField = new TextField();
        sizeField.setEditable(true);
        grid.add(sizeField, 1, 5);

        //-----Brand Name Label and Field-----
        Text brandLabel = new Text("Brand: ");
        brandLabel.setFont(myFont);
        brandLabel.setWrappingWidth(150);
        brandLabel.setTextAlignment(TextAlignment.RIGHT);
        brandLabel.setFill(Color.DARKGREEN);
        grid.add(brandLabel, 0, 6);

        brandField = new TextField();
        brandField.setEditable(true);
        grid.add(brandField, 1, 6);

        //-----Notes Label and Field-----
        Text notesLabel = new Text("Notes: ");
        notesLabel.setFont(myFont);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        notesLabel.setFill(Color.DARKGREEN);
        grid.add(notesLabel, 0, 7);

        notesField = new TextField();
        notesField.setEditable(true);
        grid.add(notesField, 1, 7);

        //-----Donor First Name Label and Field-----
        Text fnameLabel = new Text("Donor First name: ");
        fnameLabel.setFont(myFont);
        fnameLabel.setWrappingWidth(150);
        fnameLabel.setTextAlignment(TextAlignment.RIGHT);
        fnameLabel.setFill(Color.DARKGREEN);
        grid.add(fnameLabel, 0, 8);
        
        fnameField = new TextField();
        fnameField.setEditable(true);
        grid.add(fnameField, 1, 8);

        //-----Donor Last Name Label and Field------
        Text lnameLabel = new Text("Donor Last Name: ");
        lnameLabel.setFont(myFont);
        lnameLabel.setWrappingWidth(150);
        lnameLabel.setTextAlignment(TextAlignment.RIGHT);
        lnameLabel.setFill(Color.DARKGREEN);
        grid.add(lnameLabel, 0, 9);
        
        lnameField = new TextField();
        lnameField.setEditable(true);
        grid.add(lnameField, 1, 9);

        //-----Donor Phone Number Label and Field------
        Text phoneLabel = new Text("Phone Number: ");
        phoneLabel.setFont(myFont);
        phoneLabel.setWrappingWidth(150);
        phoneLabel.setTextAlignment(TextAlignment.RIGHT);
        phoneLabel.setFill(Color.DARKGREEN);
        grid.add(phoneLabel, 0, 10);

        phoneField = new TextField();
        phoneField.setEditable(true);  
        grid.add(phoneField, 1, 10);

        //-----Donor Email Label and Field-----
        Text emailLabel = new Text("Email: ");
        emailLabel.setFont(myFont);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        emailLabel.setFill(Color.DARKGREEN);
        grid.add(emailLabel, 0, 11);

        emailField = new TextField();
        emailField.setEditable(true);
        grid.add(emailField, 1, 11);

        /** ---BARCODE LOSS OF FOCUS---
         * barcodeField upon losing focus:
         * -verify user input
         * -parse gender, article id, and primary color
         * -all other textfields will become editable
         *
         * 4/22/2024
         * - Doesn't parse strings correctly
         */
        barcodeField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                //String from barcode field
                String barcodeTempString = barcodeField.getText();
                
                /*Try block will check if barcode was numeric. If so:
                 * -Verify input is 8 digits
                 * -Parse input for gender, article, color
                 */
                try {
                    int barcodeInt = Integer.parseInt(barcodeTempString);

                    //Check that input is 8 digits
                    if (barcodeTempString.length() != 8){
                        barcodeField.setText("Barcode is incorrect length!");
                    } else {
                        //parse barcode for info
                        //char[] barcodeArray = barcodeTempString.toCharArray();
                        
                        //reset parse fields
                        //genderField.clear();
                        articleField.clear();
                        colorField.clear();

                        //Determine gender from first digit
                        //char genderChar = barcodeArray[0];
                        String genderString = barcodeTempString.substring(0, 1);
                        if (genderString.equals("0")){
                            genderField.setText("M");
                            genderField.setEditable(true);
                        } else
                        if (genderString.equals("1")){
                            genderField.setText("W");
                            genderField.setEditable(true);
                        } else {
                            genderField.setText("Barcode was incorrect");
                            genderField.setEditable(false);
                        };//End gender verification

                        //Determine article type from next two digits
                        try {
                            String articleBarPrefix = barcodeTempString.substring(1, 3);
                            ArticleType article = new ArticleType(articleBarPrefix);
                            articleField.setText(article.getState("barcodePrefix").toString() +
                                    ", (" + article.getState("description").toString() + ")");
                            articleTypeId = article.getState("id").toString();
                            articleField.setEditable(true);
                        } catch (InvalidPrimaryKeyException exc) {
                            articleField.setText("Article was not found. Please enter Article Barcode Prefix");
                        }
                        //End Article Type----------------------------------------

                        //Determine color1 from next two digits
                        try {
                            String colorBarPrefix = barcodeTempString.substring(3, 5);
                            colorBarPrefix.toCharArray();
                            model.Color color = new model.Color(colorBarPrefix);
                            colorField.setText(color.getState("barcodePrefix").toString() +
                                    ", (" + color.getState("description").toString() + ")");
                            color1Id = color.getState("id").toString();
                            colorField.setEditable(true);
                        } catch (InvalidPrimaryKeyException exc) {
                            colorField.setText("Color was not found. Please enter Color Barcode Prefix");
                        }
                        //End Color---------------------------------------------

                    }//End if else for barcode parse
                } catch (NumberFormatException exc){
                    barcodeField.setText("Please Enter Barcode");
                }//End trycatch block                
            }//End changed
        });//End focusProperty

        /**---ARTICLETYPE LOSS OF FOCUS---
         * Upon article field losing focus,
         * search for article by barcode prefix.
         * 
         * This is intended to run only if user has edited article type after initial barcode parse
         */
        articleField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                
                if (articleField.getText().length() == 2){

                    String articleBarPrefix = articleField.getText();
                    try {
                        ArticleType article = new ArticleType(articleBarPrefix);
                                articleField.setText(article.getState("barcodePrefix").toString() +
                                        ", (" + article.getState("description").toString() + ")");
                                articleTypeId = article.getState("id").toString();
                                articleField.setEditable(true);
                    } catch (InvalidPrimaryKeyException exc) {
                        articleField.setText("Article Type could not be found");
                    }//End try catch 
                    
                }//End if article field was changed

            }//End changed

        });//End loss of focus for article type

        /**---COLOR LOSS OF FOCUS---
         * Upon loss of focus on primary color field,
         * Search and create new Color object and 
         */
        colorField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {

                if (colorField.getText().length() == 2){
                    
                    String colorBarPrefix = colorField.getText();
                    try {
                        colorBarPrefix.toCharArray();
                        model.Color color = new model.Color(colorBarPrefix);
                        colorField.setText(color.getState("barcodePrefix").toString() +
                                ", (" + color.getState("description").toString() + ")");
                        color1Id = color.getState("id").toString();
                        colorField.setEditable(true);
                    } catch (InvalidPrimaryKeyException exc) {
                        colorField.setText("Color was not found. Please enter Color Barcode Prefix");
                    }//End try catch block

                }//End if color field was changed
            }//End color1 changed
        });//End color1 loss of focus

        /**---COLOR2 LOSS OF FOCUS---
         * Upon loss of focus on secondary color field
         * search for color and create object.
         * take id.
         */
        color2Field.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {

                if (color2Field.getText().length() == 2 && !color2Field.getText().isEmpty()){
                    
                    String color2BarPrefix = color2Field.getText();
                    try {
                        color2BarPrefix.toCharArray();
                        model.Color color2 = new model.Color(color2BarPrefix);
                        color2Field.setText(color2.getState("barcodePrefix").toString() +
                                ", (" + color2.getState("description").toString() + ")");
                        color2Id = color2.getState("id").toString();
                        color2Field.setEditable(true);
                    } catch (InvalidPrimaryKeyException exc) {
                        colorField.setText("Color was not found. Please enter Color Barcode Prefix");
                    }//End try catch block
                }//End if color2 field was changed
            }//End color2 changed
        });//End color2 loss of focus


        //====BUTTONS=====
        //Setup separate Hbox for submit and cancel button
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        //-----Submit Button------
        Button subButton = new Button("Submit");
        subButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e){
                processSubAction(e);
            }
        });
        buttons.getChildren().add(subButton);

        //-----Cancel Button-----
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e){
                clearErrorMessage();
                myModel.stateChangeRequest("CancelAddInventoryItem", null);
            }
        });
        buttons.getChildren().add(cancelButton);

        //Add form and buttons
        form.getChildren().add(grid);
        form.getChildren().add(buttons);        

        return form;
    
    }//End createFormContents------------------------------------------

    /**==========================================================================
     * processSubAction
     *
     * on submit button press, validate required fields and then pass properties
     * object to constructor via transaction.
     *
     * @param e submit button press event.
     *
     */
    public void processSubAction(Event e){

        //Validate user input
        if (barcodeField.getText().isEmpty() || barcodeField.getText().length() != 8){

            displayMessage("Barcode Field Incorrect!");

        } else if (sizeField.getText().isEmpty()) {

            displayMessage("Size Field Cannot Be Empty!");

        } else if (genderField.getText().isEmpty() || (!genderField.getText().equals("M") && !genderField.getText().equals("W"))) {

            displayMessage("Gender Field Cannot Be Empty!");

        } else if (articleField.getText().isEmpty() || articleTypeId.isEmpty()) {

            displayMessage("Article Field Incorrect! Please reenter article type barcode prefix!");

        } else if (colorField.getText().isEmpty() || color1Id.isEmpty()) {

            displayMessage("Color Field Incorrect! Please reenter primary color barcode prefix!");

        } else if ( !color2Field.getText().isEmpty() && color2Id.isEmpty()) {

            displayMessage("Secondary Color Field Incorrect!");
            
        } else if (!phoneField.getText().isEmpty() && phoneField.getText().length() != 12) {
            
            displayMessage("Phone Entered is incorrect format!" + 
                            "\nPlease reenter in XXX-XXX-XXXX format!");      

        } else if (!emailField.getText().isEmpty() && !emailField.getText().contains("@")){

            displayMessage("Email Entered is invalid!");

        } else {//If all fields correct            

            //Create properties and keys
            Properties insertProperties = new Properties();
            insertProperties.setProperty("barcode", barcodeField.getText());
            insertProperties.setProperty("gender", genderField.getText());
            insertProperties.setProperty("size", sizeField.getText());
            insertProperties.setProperty("articleTypeId", articleTypeId);
            insertProperties.setProperty("color1Id", color1Id);
            insertProperties.setProperty("color2Id", color2Id);
            insertProperties.setProperty("brand", brandField.getText());
            insertProperties.setProperty("notes", notesField.getText());
            insertProperties.setProperty("donorLastName", lnameField.getText());
            insertProperties.setProperty("donorFirstName", fnameField.getText());
            insertProperties.setProperty("donorPhone", phoneField.getText());
            insertProperties.setProperty("donorEmail", emailField.getText());

            //TEST get date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String currentDateString = currentDate.format(myFormat);
            insertProperties.setProperty("dateDonated", currentDateString);


            //Try to add inventory item
            try {
                //Tell transaction to DoAddIventoryItem
                myModel.stateChangeRequest("DoAddInventoryItem", insertProperties);

                //Message
                displayMessage("Inventory Item was successfully added!");
            } catch (Exception ex) {
                displayMessage("Inventory Item was not added!");
            }//End try catch block
        }//End if else block

//        //Convert input to strings
//        String barcodeString = barcodeField.getText();
//        String genderString = genderField.getText();
//        String articleString = articleField.getText();
//        String colorString = colorField.getText();
//        String color2String = colorField.getText();
//        String sizeString = sizeField.getText();
//        String brandString = brandField.getText();
//        String notesString = notesField.getText();
//        String fnameString = fnameField.getText();
//        String lnameString = lnameField.getText();
//        String phoneString = phoneField.getText();
//        String emailString = emailField.getText();
    }//End processSubAction-----------------------------------------------------------------

    // Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

    //updateState-----------------------------------------------
    public void updateState(String key, Object value) {
        clearErrorMessage();

        switch (key) {
            case "TransactionStatus":
                displayMessage((String)value);
        }
    }

    //displayMessage()------------------------------------------
    public void displayMessage(String message){
        statusLog.displayMessage(message);
    }

    //clearErrorMessage()----------------------------------------
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }//---------------------------------------------------------
}//END CLASS=============================================================

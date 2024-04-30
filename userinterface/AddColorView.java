// specify the package
package userinterface;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ComboBox;

import java.util.Properties;


// project imports
import impresario.IModel;


/** The class containing the Account View  for the ATM application */
//==============================================================
public class AddColorView extends View
{

    // GUI components
    protected TextField description;

    protected TextField barcode;
    protected TextField alpha_code;
    protected ComboBox <String> status;

    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public AddColorView(IModel color)
    {
        super(color, "AddColorView");

        String css = getClass().getResource("Styles.css").toExternalForm();
        getStylesheets().add(css);

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));
        container.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog());

        getChildren().add(container);

        myModel.subscribe("TransactionStatus", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Brockport Closet ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("COLOR INFORMATION");
        prompt.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.DARKGREEN);
        grid.add(prompt, 0, 0, 2, 1);

        Text descriptionLabel = new Text("Description: ");
        Font myFont = Font.font("Arial", FontWeight.BOLD, 16);
        descriptionLabel.setFill(Color.BLACK);
        descriptionLabel.setFont(myFont);
        descriptionLabel.setWrappingWidth(150);
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(descriptionLabel, 0, 1);

        description = new TextField();
        description.setEditable(true);
        grid.add(description, 1, 1);

        Text barcodeLabel = new Text("Barcode Prefix: ");
        barcodeLabel.setFont(myFont);
        barcodeLabel.setFill(Color.BLACK);
        barcodeLabel.setWrappingWidth(150);
        barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(barcodeLabel, 0, 2);

        barcode = new TextField();
        barcode.setEditable(true);
        grid.add(barcode, 1, 2);



        Text alpha_codeLabel = new Text("Alpha Code: ");
        alpha_codeLabel.setFont(myFont);
        alpha_codeLabel.setFill(Color.BLACK);
        alpha_codeLabel.setWrappingWidth(150);
        alpha_codeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(alpha_codeLabel, 0, 3);

        alpha_code = new TextField(); // Initialize alpha_code TextField
        alpha_code.setEditable(true);
        grid.add(alpha_code, 1, 3);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        cancelButton = new Button("Back");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelAddColor", null);
            }
        });
        doneCont.getChildren().add(cancelButton);

        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clearErrorMessage();
                processAction(actionEvent);

            }
        });
        doneCont.getChildren().add(submitButton);
        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    public void processAction(Event event) {

        if(description.getText() == null || description.getText().isEmpty()) {
            displayErrorMessage("Please enter a color description.");
            description.requestFocus();
        }

        else if (barcode.getText() == null || barcode.getText().isEmpty()) {
            displayErrorMessage("Please enter an barcode prefix.");
            barcode.requestFocus();
        }
        else if (alpha_code.getText().isEmpty()) {
            displayErrorMessage("Please enter a color alpha code.");
            alpha_code.requestFocus();
        }
        else {
            Properties colorProperties = new Properties();
            colorProperties.setProperty("description", description.getText());
            colorProperties.setProperty("barcodePrefix", barcode.getText());
            colorProperties.setProperty("alphaCode", alpha_code.getText());
            myModel.stateChangeRequest("DoAddColor", colorProperties);
        }
    }

    private boolean alpha_codeHasLetter(String str) {
        for(int i = 0; i < str.length(); i++) {
            if(Character.isLetter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }




    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog()
    {
        statusLog = new MessageView("   ");

        return statusLog;
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value) {
        clearErrorMessage();

        if (key.equals("TransactionStatus")) {
            String val = (String) value;
            if ((val.startsWith("ERR")) || (val.startsWith("Err"))) {
                displayErrorMessage(val);
            } else {
                displayMessage(val);

            }
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }


}

//---------------------------------------------------------------
//	Revision History:
//



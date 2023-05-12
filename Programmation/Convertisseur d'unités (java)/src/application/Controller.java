package application;

import application.cells.MeasuresCell;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logger.LoggerFactory;
import measures.MeasureType;
import measures.units.DummyUnit;
import measures.units.SortOrder;
import measures.units.Unit;

import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller associated with EditorFrame.fxml
 * Contains:
 * <ul>
 * 	<li>FXML UI elements that need to be referenced in business logic</li>
 * 	<li>onXXXX() callback methods handling UI requests</li>
 * </ul>
 * @author davidroussel
 * @see Initializable so it can initialize FXML related attributes.
 */
public class Controller implements Initializable //, ChangeListener<String>
{
	// -------------------------------------------------------------------------
	// internal attributes
	// -------------------------------------------------------------------------
	/**
	 * Logger to show debug message or only log them in a file
	 */
	private Logger logger = null;

	/**
	 * Reference to parent stage so it can be quickly closed on quit
	 * Initialized through {@link #setParentStage(Stage)} in
	 * {@link Main#start(Stage)}
	 */
	private Stage parentStage = null;

	// -------------------------------------------------------------------------
	// FXML identified attributes (with fx:id)
	// WARNING Every attribute featuring an fx:id shall be referenced in
	// ConverterFrame.fxml
	// -------------------------------------------------------------------------

	/**
	 * Toolbar ComboBox to choose the measures
	 */
	@FXML
	private ComboBox<MeasureType> measuresComboBox;

	/**
	 * Toolbar copy to clipboard button
	 * @implSpec Should be part of {@link #styleableButtons}
	 */
	@FXML
	private Button copyButton;

	/**
	 * Toolbar clear button
	 * @implSpec Should be part of {@link #styleableButtons}
	 */
	@FXML
	private Button clearButton;

	/**
	 * Toolbar "Quit" Button
	 * @implSpec Should be part of {@link #styleableButtons}
	 */
	@FXML
	private Button quitButton;

	/**
	 * "Switch" Button to switch between source and destination units (iff possible)
	 * @implSpec Should be part of {@link #styleableButtons}
	 */
	@FXML
	private Button switchButton;

	/**
	 * The Source Unit combobox
	 */
	@FXML
	private ComboBox<Unit<Double>> sourceUnitComboBox;

	/**
	 * The Source Unit Sorting Method combobox
	 */
	@FXML
	private ComboBox<SortOrder> sourceUnitSortingComboBox;

	/**
	 * The Destination Unit combobox
	 */
	@FXML
	private ComboBox<Unit<Double>> destinationUnitComboBox;

	/**
	 * The Destination Unit Sorting Method combobox
	 */
	@FXML
	private ComboBox<SortOrder> destinationUnitSortingComboBox;

	/**
	 * Source input text field
	 */
	@FXML
	private TextField sourceTextField;

	/**
	 * Source Unit Label
	 */
	@FXML
	private Label sourceUnitLabel;

	/**
	 * Destination Label
	 */
	@FXML
	private Label destinationLabel;

	/**
	 * Destination Unit Label
	 */
	@FXML
	private Label destinationUnitLabel;

	/**
	 * Message Label at the bottom of UI
	 * (to be cleared at startup and used for info messages)
	 */
	@FXML
	private Label messageLabel;
	private Converter converter;

	// -------------------------------------------------------------------------
	// Other FXML attributes
	// -------------------------------------------------------------------------
	/**
	 * List of buttons with display style that can change.
	 * These buttons are:
	 * <ul>
	 * 	<li>{@link #quitButton}</li>
	 * </ul>
	 */
	private List<Labeled> styleableButtons;

	/**
	 * Default constructor.
	 * Initialize all non FXML attributes
	 * @see ModifiableObservableList
	 */
	public Controller()
	{
		/*
		 * Can't get parent logger now, so standalone logger.
		 * Parent logger will be set in Main.
		 */
		logger = LoggerFactory.getParentLogger(getClass(), null, Level.INFO);
	}

	/**
	 * Controller initialization to initialize FXML related attributes.
	 * @param location The location used to resolve relative paths for the root
	 * object, or null if the location is not known.
	 * @param resources Resource Bundle containing translations resources for
	 * the UI (or null)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// --------------------------------------------------------------------
		// Initialize FXML related attributes
		// --------------------------------------------------------------------
		/*
		 * Create #styleableButtons so they can be style updated in
		 * 	- #onDisplayButtonsWithGraphicsOnlyAction
		 * 	- #onDisplayButtonsWithTextOnlyAction
		 * 	- #onDisplayButtonsWithTextAndGraphicsAction
		 * TODO uncomment the following styleableButtons when ConverterFrame.fxml is ready
		 */
		styleableButtons = new ArrayList<Labeled>();
		styleableButtons.add(copyButton);
		styleableButtons.add(clearButton);
		styleableButtons.add(switchButton);
		styleableButtons.add(quitButton);

		/*
		 * Setup #measuresComboBox's content with MeasureTypes
		 * TODO uncomment the following measuresComboBox when ConverterFrame.fxml is ready
		 */
		Collection<MeasureType> measureTypes = MeasureType.all();
		measuresComboBox.getItems().addAll(measureTypes);
		MeasureType measureValue = measureTypes.iterator().next();
		measuresComboBox.setValue(measureValue);

		/*
		 * Setup Dummy content for #sourceUnitComboBox & #destinationUnitComboBox
		 * TODO uncomment the following Dummy content when ConverterFrame.fxml is ready
		 */
		/*ArrayList<Unit<Double>> sourceUnits = new ArrayList<>();
		Unit<Double> mUnit = new DummyUnit("mètres", "m");
		Unit<Double> yUnit = new DummyUnit("yard", "yd");
		sourceUnits.add(mUnit);
		sourceUnits.add(yUnit);
		sourceUnitComboBox.getItems().addAll(sourceUnits);
		ArrayList<Unit<Double>> destinationUnits = new ArrayList<>(sourceUnits);
		destinationUnitComboBox.getItems().addAll(destinationUnits);
		sourceUnitComboBox.setValue(mUnit);
		destinationUnitComboBox.setValue(yUnit);*/

		/*
		 * Setup  #sourceUnitSortingComboBox & #destinationUnitSortingComboBox
		 * with SortOrders
		 *  TODO uncomment the following SortOrder ComboBoxes when ConverterFrame.fxml is ready
		 */
		Collection<SortOrder> sortOrders = SortOrder.all();
		SortOrder sortValue = sortOrders.iterator().next();
		sourceUnitSortingComboBox.getItems().addAll(sortOrders);
		sourceUnitSortingComboBox.setValue(sortValue);
		destinationUnitSortingComboBox.getItems().addAll(sortOrders);
		destinationUnitSortingComboBox.setValue(sortValue);

		/*
		 * Setup custom cell renderer for #measuresComboBox
		 * TODO uncomment the following Measures Custom Cell when ConverterFrame.fxml is ready
		 */
		measuresComboBox.setButtonCell(new MeasuresCell());
		measuresComboBox.setCellFactory(combobox -> new MeasuresCell());

		/*
		 * Setup a custom cell renderer for #sourceUnitSortingComboBox &
		 * #destinationUnitSortingComboBox
		 */

		/*
		 * Clear #messageLabel
		 * TODO uncomment the following messageLabel when ConverterFrame.fxml is ready
		 */
		try {
			this.converter=new Converter(MeasureType.LENGTH);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		//On récupère toutes les sources du converter et on les met dans la liste déroulante de Combobox
		sourceUnitComboBox.setItems(converter.getSourceUnits());
		//Même chose
		destinationUnitComboBox.setItems(converter.getdestinationUnits());
		//Set la valeur en cours
		sourceUnitComboBox.setValue(converter.getSourceUnit());
		destinationUnitComboBox.setValue(converter.getDestinationUnit());
		//Différentes bind de propriétes pour màj rapide
		converter.sourceUnitProperty().bindBidirectional(sourceUnitComboBox.valueProperty());
		converter.destinationUnitProperty().bindBidirectional(destinationUnitComboBox.valueProperty());
		converter.sourceOrderProperty().bind(sourceUnitSortingComboBox.valueProperty());
		converter.destinationOrderProperty().bind(destinationUnitSortingComboBox.valueProperty());
		converter.inputTextProperty().bindBidirectional(sourceTextField.textProperty());
		destinationLabel.textProperty().bind(converter.outputTextProperty());
		//Màj des unités
		sourceUnitLabel.setText(converter.getSourceUnit().getSymbol());
		destinationUnitLabel.setText(converter.getDestinationUnit().getSymbol());
		messageLabel.setText("Welcome");
	}

	/**
	 * Sets parent logger
	 * @param logger the new parent logger
	 */
	public void setParentLogger(Logger logger)
	{
		this.logger.setParent(logger);
	}

	/**
	 * Set parent stage (so it can be closed on quit)
	 * @param stage the new parent stage to set
	 */
	public void setParentStage(Stage stage)
	{
		parentStage = stage;
	}

	/**
	 * Action to quit the application
	 * @param event event associated with this action
	 */
	@FXML
	public void onQuitAction(ActionEvent event)
	{
		quitActionImpl(event);
	}

	/**
	 * Implementation of the quit logic.
	 * Closes the stage.
	 * @param event the event passed to this callback (either {@link ActionEvent}
	 * or {@link WindowEvent} depending on what triggered this action).
	 */
	protected void quitActionImpl(Event event)
	{
		/*
		 * 	- closes the stage by
		 * 		- getting the stage from source if event is a WindowEvent
		 * 		- getting the stage from #parentStage or otherwise if event is
		 * 		an ActionEvent
		 */
		logger.info("Quit action triggered");

		Object source = event.getSource();
		Stage stage = null;

		if (event instanceof WindowEvent)
		{
			// Stage is the source
			stage = (Stage) source;
		}
		else if (event instanceof ActionEvent)
		{
			if (parentStage != null)
			{
				// We already have a registered stage
				stage = parentStage;
			}
			else
			{
				// Search for the stage
				if (source instanceof Button)
				{
					Button sourceButton = (Button) source;
					stage = (Stage) sourceButton.getScene().getWindow();
				}
				else
				{
					logger.warning("Unable to get Stage to close from: "
					    + source.getClass().getSimpleName());
				}
			}
		}
		else
		{
			logger.warning("Unknwon event source: " + event.getSource());
		}

		if (stage != null)
		{
			stage.close();
		}
		else
		{
			logger.warning("Window not closed");
		}
	}

	/**
	 * Action when Measures are changed
	 * @param event event associated with this action
	 */
	@FXML
	public void onMeasuresChangedAction(ActionEvent event) throws ParseException {
		Object source = event.getSource();
		String content = "";
		if (source instanceof ComboBox<?>)
		{
			/*
			 * FIXME when ConverterFrame.fxml will be ready it will be much easier to access #measuresComboBox
			 */
			ComboBox<?> combo = (ComboBox<?>) source;
			content = combo.getValue().toString();
			//Bah juste on change la mesure en cours, merci le converter
			try {
				converter.setMeasureType((MeasureType)combo.getValue());
			}
			catch (ParseException e) {
				e.printStackTrace();
			}

		}
		logger.info("Measures changed action triggered: Measures =  "
			+ content);
	}

	/**
	 * Action to copy (formatted) result (formatted) in clipboard
	 * @param event event associated with this action
	 */
	@FXML
	public void onCopyToClipboardAction(ActionEvent event)
	{
		logger.info("Copy to clipboard action triggered");
	}

	/**
	 * Action to recycle input and output to blank
	 * @param event event associated with this action
	 */
	@FXML
	public void onClearAction(ActionEvent event)
	{
		logger.info("Clear action triggered");
	}

	/**
	 * Action when unit source is changed
	 * @param event event associated with this action
	 */
	@FXML
	public void onChangeSourceUnitAction(ActionEvent event)
	{
		Object source = event.getSource();
		String content = "";
		if (source instanceof ComboBox<?>)
		{
			/*
			 * FIXME when ConverterFrame.fxml will be ready it will be much easier to access #sourceUnitComboBox
			 */
			ComboBox<?> combo = (ComboBox<?>) source;
			//Ce if, c'est parce que j'ai eu une erreur de merde que j'ai pu corriger que comme cela
			if(combo.getValue()!=null){
			content = combo.getValue().toString();
			String src=sourceTextField.getText();
			sourceUnitLabel.setText(converter.getSourceUnit().getSymbol());
			//Ce if ne sert qu'à maj la nouvelle valeur en convertissant
				//Tu peux l'enlever si tu n'as pas la flemme d'appuyer sur entrée
			if(!src.isEmpty()) {
				converter.getSourceUnit().setValue(Double.parseDouble(src));
				converter.convert();
			}}

		}
		logger.info("Change source unit action triggered. source unit = " + content);
	}

	/**
	 * Action when destination unit is changed
	 * @param event event associated with this action
	 */
	@FXML
	public void onChangeDestinationUnitAction(ActionEvent event)
	{
		Object source = event.getSource();
		String content = "";
		if (source instanceof ComboBox<?>)
		{
			/*
			 * FIXME when ConverterFrame.fxml will be ready it will be much easier to access #destinationUnitComboBox
			 */
			//Même chose que pour Source
			ComboBox<?> combo = (ComboBox<?>) source;
			if(combo.getValue()!=null){
			content = combo.getValue().toString();
			String dest=sourceTextField.getText();
			destinationUnitLabel.setText(converter.getDestinationUnit().getSymbol());
			if(!dest.isEmpty()) {
				converter.getSourceUnit().setValue(Double.parseDouble(dest));
				converter.convert();
			}}
			//this.destinationUnitComboBox.setValue();
		}
		logger.info("Change destination unit action triggered. destination unit = " + content);
	}

	/**
	 * Action when source units sorting is changed
	 * @param event event associated with this action
	 */
	@FXML
	public void onChangeSourceUnitOrderAction(ActionEvent event)
	{
		Object source = event.getSource();
		String content = "";
		if (source instanceof ComboBox<?>)
		{
			/*
			 * FIXME when ConverterFrame.fxml will be ready it will be much easier to access #sourceUnitSortingComboBox
			 */
			ComboBox<?> combo = (ComboBox<?>) source;
			content = combo.getValue().toString();
			//C'est tout le temps la même chose
			converter.setSourceSortOrder(sourceUnitSortingComboBox.getValue());
		}
		logger.info("Change source unit sorting action triggered. source unit sorting = " + content);
	}

	/**
	 * Action when destination units sorting is changed
	 * @param event event associated with this action
	 */
	@FXML
	public void onChangeDestinationUnitOrderAction(ActionEvent event)
	{
		Object source = event.getSource();
		String content = "";
		if (source instanceof ComboBox<?>)
		{
			/*
			 * FIXME when ConverterFrame.fxml will be ready it will be much easier to access #destinationUnitSortingComboBox
			 */
			ComboBox<?> combo = (ComboBox<?>) source;
			content = combo.getValue().toString();
			//Petit changement
			converter.setDestinationSortOrder(destinationUnitSortingComboBox.getValue());
		}
		logger.info("Change destination unit sorting action triggered. destination unit sorting = " + content);
	}

	/**
	 * Action when enter key is pressed in {@link #sourceTextField}
	 * @param event event associated with this action
	 */
	@FXML
	public void onChangeSourceTextdAction(ActionEvent event)
	{
		Object source = event.getSource();
		String text;
		double value = 0.0;
		if (source instanceof TextField)
		{
			/*
			 * FIXME It will be much easier to access #sourceTextField when ConverterFrame.fxml is ready
			 */
			TextField field = (TextField) source;
			text = field.getText();
			try
			{
				value = Double.parseDouble(text);
			}
			catch (NumberFormatException e)
			{
				logger.severe("Unable to parse \"" + text + "\" to double :" + e.getLocalizedMessage());
			}
			//On convertit tout ça tout ça
			converter.getSourceUnit().setValue(value);
			converter.convert();
		}
		logger.info("Source Text changed action triggered with current value " +  value);
	}

	/**
	 * Action to switch source and destination values and units
	 * @param event event associated with this action
	 */
	@FXML
	public void onSwitchSourceAndDestinationAction(ActionEvent event)
	{
		logger.info("Switch Source and Destination action triggered");
		sourceUnitComboBox.setValue(converter.getSourceUnit());
		destinationUnitComboBox.setValue(converter.getDestinationUnit());
		converter.switchUnits();
	}

	/**
	 * Action to XXX
	 * @param event event associated with this action
	 */
	@FXML
	public void onXXXAction(ActionEvent event)
	{
		logger.info("XXX action triggered");
		// Either write an info message with
//		messageLabel.setText("message");
		// Or clear message with
//		clearMessage();
	}

	/**
	 * Action to show buttons with Graphics only
	 * @param event event associated with this action
	 */
	@FXML
	public void onDisplayButtonsWithGraphicsOnlyAction(ActionEvent event)
	{
		logger.info("Display Buttons with Graphics only action triggered");
		styleableButtons.forEach((Labeled labeled) -> {
			labeled.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		});
	}

	/**
	 * Action to show buttons with Text and Graphics
	 * @param event event associated with this action
	 */
	@FXML
	public void onDisplayButtonsWithTextAndGraphicsAction(ActionEvent event)
	{
		logger.info("Display Buttons with Text and Graphics action triggered");
		styleableButtons.forEach((Labeled labeled) -> {
			labeled.setContentDisplay(ContentDisplay.LEFT);
		});
	}

	/**
	 * Action to show buttons with Text only
	 * @param event event associated with this action
	 */
	@FXML
	public void onDisplayButtonsWithTextOnlyAction(ActionEvent event)
	{
		logger.info("Display Buttons with Text only action triggered");
		styleableButtons.forEach((Labeled labeled) -> {
			labeled.setContentDisplay(ContentDisplay.TEXT_ONLY);
		});
	}

	/**
	 * Action to set {@link #logger} level to {@link Level#INFO}
	 * @param event event associated with this action
	 */
	@FXML
	public void onSetLoggerLevelUpToInfoAction(ActionEvent event)
	{
		setLoggerLevel(Level.INFO);
		logger.info("Set Logger level up to INFO");
	}

	/**
	 * Action to set {@link #logger} level to {@link Level#WARNING}
	 * @param event event associated with this action
	 */
	@FXML
	public void onSetLoggerLevelUpToWarningAction(ActionEvent event)
	{
		setLoggerLevel(Level.WARNING);
		logger.warning("Set Logger level up to WARNING");
	}

	/**
	 * Action to set {@link #logger} level to {@link Level#SEVERE}
	 * @param event event associated with this action
	 */
	@FXML
	public void onSetLoggerLevelUpToSevereAction(ActionEvent event)
	{
		setLoggerLevel(Level.SEVERE);
		logger.severe("Set Logger level up to SEVERE");
	}

	/**
	 * Action to set {@link #logger} level to {@link Level#OFF}
	 * @param event event associated with this action
	 */
	@FXML
	public void onSetLoggerLevelOffAction(ActionEvent event)
	{
		logger.info("Set Logger level to OFF");
		setLoggerLevel(Level.OFF);
	}

	/**
	 * Set {@link #logger} level
	 * @param level the level to set on {@link #logger}
	 */
	private void setLoggerLevel(Level level)
	{
		if (logger != null)
		{
			logger.setLevel(level);
		}
	}
}

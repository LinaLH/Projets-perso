package application.cells;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.ImageView;

/**
 * Custom {@link ComboBoxListCell} (also suitable for a {@link ListCell}) to
 * show Measures or sorting options in either a
 * {@link javafx.scene.control.ComboBox} or a
 * {@link javafx.scene.control.ListView}
 * Such cells are composed of an {@link ImageView} and a {@link Label} which
 * can both be determined based on content type <T>
 * @param <T> The type of content of this of cell
 * @author davidroussel
 */
public class CustomCell<T> extends ComboBoxListCell<T>
{
	/**
	 * The root node which will be loaded from FXML
	 */
	private Node graphic;

	/**
	 * Flag indicating text should be used in cell
	 */
	private boolean useText;

	/**
	 * The controller to use on this cell.
	 * @implSpec The actual concrete controller will be specified within the
	 * loaded FXML file
	 */
	private AbstractCustomCellController<T> controller;

	/**
	 * Constructor from FXML file and use text flag.
	 * Loads provided FXML file to layout the cell and binds controller
	 * @param fxmlFile the FXML file name to load
	 * @param useText to use text in rendered cell
	 * @implSpec the actual {@link #controller} shall be specified in the provided
	 * #fxmlFile
	 */
	protected CustomCell(String fxmlFile, boolean useText)
	{
		this.useText = useText;
		FXMLLoader loader = new FXMLLoader(CustomCell.class.getResource(fxmlFile));
		try
		{
			graphic = loader.load();
			controller = loader.getController();
		}
		catch (IOException e)
		{
			System.err.println("Unable to load " + fxmlFile);
			e.printStackTrace();
		}
	}

	/**
	 * Constructor from FXML file
	 * Loads provided FXML file to layout the cell and binds controller
	 * @param fxmlFile the FXML file name to load
	 * @implSpec the actual {@link #controller} shall be specified in the provided
	 * #fxmlFile
	 */
	protected CustomCell(String fxmlFile)
	{
		this(fxmlFile, true);
	}

	/**
	 * Cell update
	 * @param item to display in this cell
	 * @param empty indicates if this cell represents data or not
	 */
	@Override
	public void updateItem(T item, boolean empty)
	{
		super.updateItem(item, empty);

		if (empty || (item == null))
		{
			setText(null);
			setGraphic(null);
		}
		else
		{
			if (useText)
			{
				controller.setName(item);
			}
			else
			{
				controller.setName(null);
			}
			controller.setIcon(item);
			setText(null);
			setGraphic(graphic);
		}
	}
}

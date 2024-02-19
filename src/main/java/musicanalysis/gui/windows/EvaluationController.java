package musicanalysis.gui.windows;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import musicanalysis.algorithms.AnalysisAlgorithm;
import musicanalysis.evaluate.ResultTable;
import musicanalysis.evaluate.EvaluationModel;
import musicanalysis.structure.PitchPluginLoader;

import java.util.ArrayList;

public class EvaluationController<T extends AnalysisAlgorithm>
{
    @FXML
    private ListView<T> algorithmsListView;

	private SimpleBooleanProperty[] isTicked;

	@FXML
	private TabPane tableTabs;

	private EvaluationModel<T> evalModel;

    public void initialize()
    {
        PitchPluginLoader loader = new PitchPluginLoader();

        algorithmsListView.setFocusTraversable( false );

        algorithmsListView.setCellFactory(CheckBoxListCell.forListView(item ->
        {
/*			System.out.println(isTicked[algorithmsListView.getItems().indexOf(item)]);*/
            return isTicked[algorithmsListView.getItems().indexOf(item)];
        }));
    }

	public void setModel(EvaluationModel<T> evalModel)
	{
		this.evalModel = evalModel;

		algorithmsListView.setItems(evalModel.getAlgorithms());
		isTicked = new SimpleBooleanProperty[algorithmsListView.getItems().size()];
		for(int currentTickBox = 0; currentTickBox < isTicked.length; currentTickBox++)
		{
			isTicked[currentTickBox] = new SimpleBooleanProperty(false);
		}
	}

	public void runEvaluation(ActionEvent actionEvent)
	{
		ResultTable[] evalOutput = evalModel.evaluate(isTicked);
		tableTabs.getTabs().clear();

		for(ResultTable currentTable : evalOutput)
		{
			TableView<String[]> newTable = populateTable(currentTable.getHeaders(), currentTable.getRows());
			Tab newTab = new Tab(currentTable.getTitle());
			newTab.setContent(newTable);
			tableTabs.getTabs().add(newTab);
		}
	}

	public TableView<String[]> populateTable(String[] headers, ArrayList<String[]> table)
	{
		TableView<String[]> newTable = new TableView<String[]>();
		for (int loopVar = 0; loopVar < headers.length; loopVar++)
		{
			final int finalLoopVar = loopVar;
			TableColumn<String[], String> currentColumn = new TableColumn<>(headers[loopVar]);
			currentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[finalLoopVar]));
			newTable.getColumns().add(currentColumn);
		}

		for (String[] row : table)
		{
			newTable.getItems().add(row);
		}

		return newTable;
	}
}

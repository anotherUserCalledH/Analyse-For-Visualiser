package musicanalysis.evaluate;

import java.util.ArrayList;

public class ResultTable
{
	private String title;
	private String[] headers;

	private ArrayList<String[]> rows;


	public ResultTable(String title, String[] headers, ArrayList<String[]> rows)
	{
		this.title = title;
		this.headers = headers;
		this.rows = rows;
	}

	public String getTitle()
	{
		return title;
	}

	public String[] getHeaders()
	{
		return headers;
	}

	public ArrayList<String[]> getRows()
	{
		return rows;
	}

}

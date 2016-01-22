package tass;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 844971311069301959L;
	private JPanel panel;
	private Browser browser;
	private JTextField poitf;
	private JTextField status;
	private JTextField dtfrom;
	private JTextField dtto;
	private POI poi;

	public MainFrame() {
		super("JxBrowser Google Maps");

		// browser
		browser = new Browser();
		BrowserView view = new BrowserView(browser);
		this.add(view, BorderLayout.CENTER);
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		browser.loadURL("D:\\workspace\\tass\\tass\\src\\main\\resources\\map.html");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// menu
		panel = new JPanel();
		// panel.setLayout(null);
		this.add(panel, BorderLayout.NORTH);

		// get locations(POI) from database
		// DefaultComboBoxModel model = new DefaultComboBoxModel();
		// model.addElement("Colosseo");
		/*
		 * ResultSet rs = getPOI(); while(rs.next()) {
		 * model.addElement(rs.getString(1)); }
		 */

		// add locations to comboBox
		// JComboBox comboBox = new JComboBox(model);
		// panel.add(comboBox);
		poitf = new JTextField("Colosseo");
		panel.add(poitf);

		// search POI status
		status = new JTextField("status");
		status.setEnabled(false);
		panel.add(status);

		// search POI button
		JButton searchPOI = new JButton("Search POI");
		panel.add(searchPOI);
		searchPOI.addActionListener(new searchPOIButtonListener());

		// date time from
		dtfrom = new JTextField("2013-03-02 12:33:12");
		panel.add(dtfrom);

		// date time to
		dtto = new JTextField("2013-03-02 12:33:12");
		panel.add(dtto);

		// search button
		JButton search = new JButton("Search");
		panel.add(search);
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Configuration conf = Configuration.getInstance();
				try {
					Connection conn = conf.getConnection();
					String sql = "Select * from taxi where latitude BETWEEN ? and ? "
							+ "and longtitude BETWEEN ? and ? " + "and date BETWEEN  ? and ?";
					PreparedStatement ps = conn.prepareStatement(sql);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

	private ResultSet getPOI() throws SQLException, ClassNotFoundException {
		Configuration conf = Configuration.getInstance();
		Connection conn = conf.getConnection();
		String sql = "Select * from poi where name=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, poitf.getText());

		return ps.executeQuery();
	}

	private void markPOI() {
		browser.executeJavaScript("var poiLatLng = new google.maps.LatLng(" + poi.getLatitude() + "."
				+ poi.getLongtitude() + ");\n" 
				+ "var marker = new google.maps.Marker({\n" 
				+ "    position: myLatLng,\n"
				+ "    map: map,\n" 
				+ "    title: 'Hello World!'\n" 
				+ "});");
	}

	private class searchPOIButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				ResultSet result = getPOI();
				if (result.next()) {
					status.setText("FOUND");
					poi = new POI(result.getString(1), result.getDouble(2), result.getDouble(3));
					markPOI();
				} else {
					status.setText("NOT FOUND");
				}
			} catch (ClassNotFoundException e1) {
				status.setText("ERROR");
				e1.printStackTrace();
			} catch (SQLException e1) {
				status.setText("ERROR");
				e1.printStackTrace();
			}

		}

	}

}

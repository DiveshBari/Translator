import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;

import com.utils.RestUtil;

import javax.swing.JTextArea;
import java.awt.Choice;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.HashMap;

public class Translater {

	private JFrame frame;
	private Choice listLangSrc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Translater window = new Translater();
					window.frame.setVisible(true);					
				} catch (
				Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Translater() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 923, 537);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea txtContentSrc = new JTextArea();
		txtContentSrc.setBounds(10, 71, 397, 360);
		frame.getContentPane().add(txtContentSrc);
		
		JTextArea txtContentTrg = new JTextArea();
		txtContentTrg.setBounds(500, 71, 397, 360);
		frame.getContentPane().add(txtContentTrg);
		
		listLangSrc = new Choice();
		listLangSrc.setBounds(10, 45, 397, 20);
		frame.getContentPane().add(listLangSrc);
		
		Choice listLangTrg = new Choice();
		listLangTrg.setBounds(500, 45, 397, 20);
		frame.getContentPane().add(listLangTrg);
		
		JButton btnSrcToTrg = new JButton("New button");
		btnSrcToTrg.setBounds(417, 134, 73, 23);
		frame.getContentPane().add(btnSrcToTrg);
		
		JButton btnTrgToSrc = new JButton("New button");
		btnTrgToSrc.setBounds(417, 187, 73, 23);
		frame.getContentPane().add(btnTrgToSrc);
		
		JLabel lblNewLabel = new JLabel("Translater");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 187, 30);
		frame.getContentPane().add(lblNewLabel);
		
		//FIll list buttons listLangSrc and listLangTrg
		fillListButtons();		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */	
	private void fillListButtons() {
		//TODO the API call to get the languages list.
		listLangSrc.add("Lang 1");
		getLanguagesAndPrepareLists();
	}	

	/**
	 * getLanguages - It uses google-translate1 api to fet all the available languages.
	 */	
	private void getLanguagesAndPrepareLists() {
		RestUtil objRestUtil = new RestUtil();
		String strUrl = "https://google-translate1.p.rapidapi.com/language/translate/v2/languages?target=en";
		
		HashMap<String, String> hmResquestParams = new HashMap<String, String>();
		hmResquestParams.put("Accept-Encoding", "application/gzip");
		hmResquestParams.put("X-RapidAPI-Key", "b10ddfcdd1msh8e68be4ab8ea1a9p15fd6cjsnfaad95ca0340");
		hmResquestParams.put("X-RapidAPI-Host", "google-translate1.p.rapidapi.com");
		
		HashMap<String, String> hmResponse = new HashMap<String, String>();
		hmResponse = objRestUtil.doGetCall(strUrl, hmResquestParams);
		
		if(hmResponse!=null && !hmResponse.isEmpty() && hmResponse.get("Status").toString()=="OK") {
			// Get Array of languages
			String strResponse = hmResponse.get("Response").toString();
			// TODO using json utils convert json string into hashmap object
		}
	}
}

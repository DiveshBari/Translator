import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;

import com.utils.JsonUtil;
import com.utils.RestUtil;

import javax.swing.JTextArea;
import java.awt.Choice;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Translater {

	private JFrame frame;
	private Choice listLangSrc;
	private Choice listLangTrg;
	private JTextArea txtContentSrc;
	private JTextArea txtContentTrg;
	private ArrayList arrLanguages;
	private RestUtil objRestUtil;
	private JsonUtil objJsonUtil;

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
		objRestUtil = new RestUtil();
		objJsonUtil = new JsonUtil();
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 923, 652);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtContentSrc = new JTextArea();
		txtContentSrc.setBounds(10, 71, 397, 360);
		frame.getContentPane().add(txtContentSrc);
		
		txtContentTrg = new JTextArea();
		txtContentTrg.setBounds(500, 71, 397, 125);
		frame.getContentPane().add(txtContentTrg);
		
		listLangSrc = new Choice();
		listLangSrc.setBounds(10, 45, 397, 20);
		frame.getContentPane().add(listLangSrc);
		
		listLangTrg = new Choice();
		listLangTrg.setBounds(500, 45, 397, 20);
		frame.getContentPane().add(listLangTrg);
		
		JButton btnSrcToTrg = new JButton("Translate");
		btnSrcToTrg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				translateFromSrcToTrg();
			}
		});
		btnSrcToTrg.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSrcToTrg.setBounds(417, 134, 73, 23);
		frame.getContentPane().add(btnSrcToTrg);
		
		JButton btnTrgToSrc = new JButton("Translate");
		btnTrgToSrc.setFont(new Font("Tahoma", Font.PLAIN, 10));
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
		getLanguagesAndPrepareLists();
	}	

	/**
	 * getLanguages - It uses google-translate1 api to fet all the available languages.
	 */	
	@SuppressWarnings({ "unused", "rawtypes" })
	private void getLanguagesAndPrepareLists() {		
		String strUrl = "https://google-translate1.p.rapidapi.com/language/translate/v2/languages?target=en";
		
		HashMap<String, String> hmResquestParams = new HashMap<String, String>();
		hmResquestParams.put("Accept-Encoding", "application/gzip");
		hmResquestParams.put("X-RapidAPI-Key", "b10ddfcdd1msh8e68be4ab8ea1a9p15fd6cjsnfaad95ca0340");
		hmResquestParams.put("X-RapidAPI-Host", "google-translate1.p.rapidapi.com");
		
		HashMap<String, String> hmResponse = new HashMap<String, String>();
		hmResponse = objRestUtil.doRestCall("GET", strUrl, hmResquestParams, "");
		
		if(hmResponse!=null && !hmResponse.isEmpty() && hmResponse.get("Status").toString()=="OK") {
			// Get Array of languages
			String strResponse = hmResponse.get("Response").toString();
			
			// using json utils convert json string into hashmap object
			HashMap hmData = JsonUtil.getAsHashMap(strResponse);
			
			// Get the array of languages in arrLanguages 
			arrLanguages = (ArrayList)((HashMap)hmData.get("data")).get("languages");
			
			// Prepare languages list from arrLanguages
			for(Object listElement : arrLanguages) {
				String curLang = ((HashMap)listElement).get("name").toString();
				listLangSrc.add(curLang);
				listLangTrg.add(curLang);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void translateFromSrcToTrg() {
		// Get the text of the Source Text field
		String strSourceText = txtContentSrc.getText();
		if(strSourceText.trim()!="") {
			// Get the selected language of target 
			int indSelectedLang = listLangTrg.getSelectedIndex();
			String strTrgLang = ((HashMap)arrLanguages.get(indSelectedLang)).get("language").toString();
			
			// Translate the source text 
			String strUrl = "https://google-translate1.p.rapidapi.com/language/translate/v2";
			
			HashMap<String, String> hmResquestParams = new HashMap<String, String>();
			hmResquestParams.put("Accept-Encoding", "application/gzip");
			hmResquestParams.put("content-type", "application/x-www-form-urlencoded");
			hmResquestParams.put("X-RapidAPI-Key", "b10ddfcdd1msh8e68be4ab8ea1a9p15fd6cjsnfaad95ca0340");
			hmResquestParams.put("X-RapidAPI-Host", "google-translate1.p.rapidapi.com");
			
			HashMap<String, String> hmResponse = new HashMap<String, String>();
			String payloadToBeTranslated = "";
			try {
				payloadToBeTranslated = URLEncoder.encode(strSourceText, "UTF-8");
				payloadToBeTranslated = "q="+payloadToBeTranslated;
				// Add target language (The language into which the sorce should be translated)
				payloadToBeTranslated = payloadToBeTranslated+"&target="+strTrgLang;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}; 
			hmResponse = objRestUtil.doRestCall("POST", strUrl, hmResquestParams, payloadToBeTranslated);
			if(hmResponse!=null && !hmResponse.isEmpty() && hmResponse.get("Status").toString()=="OK") {
				// Get Array of languages
				String strResponse = hmResponse.get("Response").toString();
				
				// using json utils convert json string into hashmap object
				HashMap hmData = JsonUtil.getAsHashMap(strResponse);
				
				// Get the array of languages in arrLanguages 
				ArrayList arrTranslations = (ArrayList)((HashMap)hmData.get("data")).get("translations");
				
				// Prepare languages list from arrLanguages
				String strTranslatedTxt = ((HashMap)arrTranslations.get(0)).get("translatedText").toString();
				txtContentTrg.setText(strTranslatedTxt);
			}
		}
	}
}


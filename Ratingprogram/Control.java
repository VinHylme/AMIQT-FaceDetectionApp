import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Control implements Initializable {
	final File helen = new File("C:/Users/Sam/Documents/Nottingham University/Computer Science/Year 2/G52GRP/HELEN/trainset");
	final File lfpw = new File("C:/Users/Sam/Documents/Nottingham University/Computer Science/Year 2/G52GRP/LFPW/trainset");
	String folderSelected;
	public String imageSelected;
	public String datasetSelected;
	File imageTxtFile;
	@FXML
	ArrayList<String> datasetNames = new ArrayList<String>();
	
	@FXML
	ObservableList<String> datasets = FXCollections.observableList(datasetNames);
	
	@FXML
	ArrayList<Integer> score = new ArrayList<Integer>();
	
	@FXML
	ObservableList<Integer> scoreNumber = FXCollections.observableList(score);
	
	@FXML
	ArrayList<String> images = new ArrayList<String>();
	
	@FXML
	ObservableList<String> imageList = FXCollections.observableList(images); 
	
	@FXML
	public ComboBox datasetCombo;
	
	@FXML
	public ComboBox imageCombo;
	
	@FXML
	public ComboBox scoreCombo;
	
	@FXML
	public ImageView image;
	
	@FXML
	public Button exit;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		datasetNames.add("HELEN");
		datasetNames.add("LFPW");
		datasetCombo.setItems(datasets);
		for (int i = 1; i < 6; i++) {
			score.add(i);
		}
		scoreCombo.setItems(scoreNumber);
	}
	
	@FXML
	public void exitSystem() {
		System.exit(0);
	}
	
	@FXML
	public void selectDataset() {
		scoreCombo.setValue("");
		datasetSelected = (String) datasetCombo.getSelectionModel().getSelectedItem();
		initializeImages(datasetSelected);
	}
	
	public void initializeImages(String datasetSelected) {
		final File folder;
		if (datasetSelected.equalsIgnoreCase("HELEN")) {
			folderSelected = "HELEN";
			addImageFilesToComboBox(helen);
		} else if (datasetSelected.equalsIgnoreCase("LFPW")) {
			folderSelected = "LFPW";
			addImageFilesToComboBox(lfpw);
		} else {
			folder = null;
		}
	}
	
	public void addImageFilesToComboBox(final File folder) {
		images.removeAll(images);
		for (final File file : folder.listFiles()) {
			try {
				if (!file.getName().endsWith(".txt")) {
					images.add(file.getName());					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		imageList = FXCollections.observableList(images);
		imageCombo.setItems(imageList);
	}
	
	@FXML
	public void selectImage() {
		scoreCombo.setValue("");
		imageSelected = (String) imageCombo.getSelectionModel().getSelectedItem();		
		if (imageSelected != null) {
			String[] fileSplit = imageSelected.split(Pattern.quote("."));
			String imageTxtName = fileSplit[0] + ".txt";
			if (folderSelected.equalsIgnoreCase("HELEN")) {
					Image imageFile = new Image("file:" + helen.getPath() + "/" + imageSelected);
					image.setImage(imageFile);
					imageTxtFile = new File(helen.getPath() + "/" + imageTxtName);
					readCreateFile(imageTxtFile);
			} else if (folderSelected.equalsIgnoreCase("LFPW")) {
				Image imageFile = new Image("file:" + lfpw.getPath() + "/" + imageSelected);
				image.setImage(imageFile);
				imageTxtFile = new File(lfpw.getPath() + "/" + imageTxtName);
				readCreateFile(imageTxtFile);
			}
		}
	}
	
	public void readCreateFile(File imageTxtFile) {
		try {
			if (imageTxtFile.exists()) {
				FileReader fr = new FileReader(imageTxtFile.getPath());
				BufferedReader text = new BufferedReader(fr);
				String textData = text.readLine();
				scoreCombo.setValue(textData);
				text.close();
				fr.close();
			} else {
				imageTxtFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void setScore() {
		Object item = scoreCombo.getSelectionModel().getSelectedItem();
		if (item != null) {
			String score = item.toString();
			try {
				if (!score.equalsIgnoreCase("")) {
					System.out.println(imageTxtFile);
					System.out.println(score);
					PrintWriter txtWriter = new PrintWriter(imageTxtFile);
					txtWriter.print(score);
					txtWriter.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}

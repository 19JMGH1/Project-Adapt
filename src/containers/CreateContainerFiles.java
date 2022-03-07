package containers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import containers.electronics.CoalGenerator;
import containers.electronics.CopperWire;
import containers.electronics.LESU;
import containers.electronics.Refiner;

public class CreateContainerFiles {

	public static Gson json = new Gson();

	public static void createFile(String fileName, Object obj) {
		String jsonFileText = null;
		jsonFileText = json.toJson(obj);
		try {
			FileWriter fw = new FileWriter(fileName);
			fw.write(jsonFileText);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object getContainer(String fileName, ContainerIDs CID) {
		String objectText = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			objectText = br.readLine();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (CID == ContainerIDs.Refiner) {
			return json.fromJson(objectText, Refiner.class);
		}
		else if (CID == ContainerIDs.BlastFurnace) {
			return json.fromJson(objectText, BlastFurnace.class);
		}
		else if (CID == ContainerIDs.CoalGenerator) {
			return json.fromJson(objectText, CoalGenerator.class);
		}
		else if (CID == ContainerIDs.CopperWire) {
			return json.fromJson(objectText, CopperWire.class);
		}
		else if (CID == ContainerIDs.LESU) {
			return json.fromJson(objectText, LESU.class);
		}
		else if (CID == ContainerIDs.Cabinet) {
			return json.fromJson(objectText, Cabinet.class);
		}
		else {
			return null;
		}
	}

}

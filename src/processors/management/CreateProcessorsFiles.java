package processors.management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class CreateProcessorsFiles {

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

	public static Object getContainer(String fileName, ProcessorIDs CID) {
		String objectText = null;
		Class<?> c = null;
		try {
			if (CID.electronic) {
				c = Class.forName("processors.electronics."+CID.toString());
			}
			else {
				c = Class.forName("processors."+CID.toString());
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			objectText = br.readLine();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if (CID == ContainerIDs.Refiner) {  //THIS WAS THE OLD WAY OF DOING THIS, now its more simple
//			return json.fromJson(objectText, Refiner.class);
//		}
//		else if (CID == ContainerIDs.BlastFurnace) {
//			return json.fromJson(objectText, BlastFurnace.class);
//		}
//		else if (CID == ContainerIDs.CoalGenerator) {
//			return json.fromJson(objectText, CoalGenerator.class);
//		}
//		else if (CID == ContainerIDs.CopperWire) {
//			return json.fromJson(objectText, CopperWire.class);
//		}
//		else if (CID == ContainerIDs.LESU) {
//			return json.fromJson(objectText, LESU.class);
//		}
//		else if (CID == ContainerIDs.Cabinet) {
//			return json.fromJson(objectText, Cabinet.class);
//		}
//		else if (CID == ContainerIDs.ElectricFurnace) {
//			return json.fromJson(objectText, ElectricFurnace.class);
//		}
//		else {
//			return null;
//		}
		return json.fromJson(objectText, c);
	}

}

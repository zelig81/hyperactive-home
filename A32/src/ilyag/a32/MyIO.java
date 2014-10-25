package ilyag.a32;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.app.Activity;
import android.util.Log;

public class MyIO {

	@SuppressWarnings("unchecked")
	public static HashMap<String, Integer> load(Activity act) {
		HashMap<String, Integer> map = null;

		FileInputStream fis;
		ObjectInputStream ois = null;
		boolean success = false;
		try {
			fis = act.getApplicationContext().openFileInput("my_filename.ser");
			ois = new ObjectInputStream(fis);
			map = (HashMap<String, Integer>) ois.readObject();
			if (map != null) {
				success = true;
			}
		} catch (IOException e) {
			Log.e("io exception in deserialization", e.getMessage());
		} catch (ClassNotFoundException e) {
			Log.e("class not found in deserialization", e.getMessage());
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				Log.e("troubles in closing stream in deserialization", e.getMessage());
			}
		}
		if (success == false) {
			map = new HashMap<>();
		}

		return map;
	}

	public static void save(Activity act, HashMap<String, Integer> map) {
		FileOutputStream fos;
		ObjectOutputStream oos = null;
		try {
			fos = act.getApplicationContext().openFileOutput("my_filename.ser", Activity.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
		} catch (IOException e) {
			Log.e("onPause in AddActivity happens IOException on saving ArrayList", e.getMessage());
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				Log.e("onPause in AddActivity happens IOException on closing stream for Saving ArrayList",
						e.getMessage());
			}
		}
	}

}

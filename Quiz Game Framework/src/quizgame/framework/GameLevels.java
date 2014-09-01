package quizgame.framework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public abstract class GameLevels {

	private static List<Level> levels=new ArrayList<Level>();
	
	
	
	public static void addLevel(Level level){
		levels.add(level);
	}
	
	public static void removeLevel(Level level){
		levels.remove(level);
	}
	
	public static void setLevels(List<Level> levels){
		GameLevels.levels=levels;
	}
	
	public static List<Level> getLevels(){
		return levels;
	}
	
	public static int getNumberOfLevels(){
		return levels.size();
	}
	
	public static void clearLevels(){
		levels.clear();
	}
	
	
	private static List<Achievement> achievements=new ArrayList<Achievement>();
	
	public static List<Achievement> getAchievements(){
		achievements.clear();
		for(Level level : levels){
			achievements.add(level.getAchievement());
		}
		
		return achievements;
	}
	
	public static Achievement getAchievement(Achievement achievement){

		for(int i=0; i<getAchievements().size(); i++){
			if(achievement.getTitle().equals(getAchievements().get(i).getTitle()) &&
					achievement.getDescription().equals(getAchievements().get(i).getDescription())){
				return getAchievements().get(i);
			}
		}

		return null;
	}
	
	public static void save(Context context){
		try {  
			FileOutputStream fos = context.openFileOutput("gameData", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);  
			oos.writeObject(GameLevels.getLevels());  
			oos.flush();
			oos.close();
		} catch (Exception e) {  
			Log.i("File Write error: ", e.toString()); 
		}
	}
	
	public static void load(Context context){
		try{  
			FileInputStream fis=context.openFileInput("gameData");
			ObjectInputStream ois = new ObjectInputStream(fis);  
			GameLevels.setLevels((List<Level>) ois.readObject()); 
			ois.close();
		}  
		catch (Exception ex) {  
			Log.e("FileUtils", "Failed to load file, opening default one", ex);
			GameLevels.setLevels(new ArrayList<Level>());
		}
	}
}

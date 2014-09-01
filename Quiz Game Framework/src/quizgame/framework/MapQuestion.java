package quizgame.framework;


public class MapQuestion extends Question{
	
	private static final long serialVersionUID = 5942184727952914076L;
	
	private int x, y, mapImageResourceId;
	
	public MapQuestion(String questionText, int x, int y, int mapImage){
		this.setQuestionText(questionText);
		this.setX(x);
		this.setY(y);
		this.setMapImageResourceId(mapImage);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getMapImageResourceId() {
		return mapImageResourceId;
	}

	public void setMapImageResourceId(int mapImageResourceId) {
		this.mapImageResourceId = mapImageResourceId;
	}
}

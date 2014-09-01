package quizgame.framework;

import java.io.Serializable;

public class Hint implements Serializable{

	private static final long serialVersionUID = -1063461470112046101L;
	
	private String content;

	public Hint(String content){
		this.content=content;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}

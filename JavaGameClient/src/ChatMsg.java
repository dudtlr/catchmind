
// ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	public String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image, 500: Mouse Event
	public String UserName;
	public String data;
	public ImageIcon img;
	public MouseEvent mouse_e;
	public int pen_size; // pen size
	
	//좌표 값 
	private int x1, y1, x2, y2; //드래그시 선을 그을 좌표값
	

	public ChatMsg(String UserName, String code, String msg) {
		this.code = code;
		this.UserName = UserName;
		this.data = msg;
	}
	
	
	public void setX1(int x1){	this.x1 = x1;	}
	public void setY1(int y1){	this.y1 = y1;	}
	public void setX2(int x2){	this.x2 = x2;	}
	public void setY2(int y2){	this.y2 = y2;	}

	
	public int getX1(){	return x1;	}
	public int getY1(){	return y1;	}
	public int getX2(){	return x2;	}
	public int getY2(){	return y2;	}
	
}
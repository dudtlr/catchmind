// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.sun.tools.javac.Main;

public class JavaGameClientView extends JFrame {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private JTextField txtInput;
   private String UserName;
   private JButton btnSend;
   private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
   private Socket socket; // 연결소켓
   private InputStream is;
   private OutputStream os;
   private DataInputStream dis;
   private DataOutputStream dos;

   private ObjectInputStream ois;
   private ObjectOutputStream oos;

   private JLabel lblUserName;

   // private JTextArea textArea;
   private JTextPane textArea;

   private Frame frame;
   private FileDialog fd;
   private JButton imgBtn;

   JPanel panel;
   private JLabel backgroundImage;  // 배경에 쓸 이미지 넣어놓는 용도
   private JLabel lblMouseEvent;
   private Graphics gc;
   private int pen_size = 2; // minimum 2
   // 그려진 Image를 보관하는 용도, paint() 함수에서 이용한다.
   private Image panelImage = null;
   private Graphics gc2 = null;

   private boolean isFirst = true;
   private boolean check = false;

   private String author = "user1"; // 출제하는사람

   // 더블 버퍼링을 위한 인스탠스들
   private Image screenImage;
   private Graphics screenGraphic;



   
   

   // 버튼 이미지 아이콘만들기
   private ImageIcon quitButtonBasicImage = new ImageIcon("src/images/exit.png");

   // 버튼에 이미지 넣기
   private JButton quitButton = new JButton(quitButtonBasicImage);

   // 로비 창 선언
   public JLabel myInfo = new JLabel();
   public JLabel lobyInfototal = new JLabel();
   public JLabel lobyInfo = new JLabel();
   public JButton Room[] = new JButton[4];
   
   public int i;
   int j;
   int a;
   public int WhereIAm=0;
   
   
   //게임창 컴포넌트 들 
   public JButton btnNewButton2 = new JButton(""); // 레드 버튼
   public JButton eraser = new JButton("지우개");  // 지우개 버튼
   public JButton trashcan = new JButton("초기화"); // 초기화 버튼
   public JButton greenPen = new JButton(""); // 그린 버튼
   public JButton blackPen = new JButton(""); //블랙 버튼
   public JButton bluePen = new JButton(""); //블루 버튼
   public JButton yellowPen = new JButton(""); //옐로우 버튼
   public JButton rectangle = new JButton(""); //사각형
   public JButton circle = new JButton(""); //원형
   public JButton triangle = new JButton(""); //삼각형
   public JLabel Roomnum = new JLabel(); // 방 번호
   public JLabel Roompeople = new JLabel();
   
   //게임 배경화면
   private final JLabel GameBackground = new JLabel("New label");
   // 대기실 배경화면 
  // private final JLabel WaittingRoomBackground = new JLabel("New label");
   /**
    * Create the frame.
    * 
    * @throws BadLocationException
    */
   // 게임 창 ----------------------------------------------------------
   public JavaGameClientView(String username, String ip_addr, String port_no) {
      
      setUndecorated(true); // 위에 창을 없애준
      setTitle("Catch Mind");
      setSize(1280, 720); // 게임 화면 크기
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x 버튼 누르면 꺼지게
      //setResizable(false); // 사이즈 조절 안되게
      getContentPane().setLayout(null);
      setVisible(true);
      
      
      backgroundImage = new JLabel();
      backgroundImage.setBackground(new Color(255, 255, 255));
      backgroundImage.setIcon(null);
      //backgroundImage.setBorder(new LineBorder(new Color(0, 0, 0)));
      //backgroundImage.setBackground(Color.WHITE);
      backgroundImage.setBounds(0, 0, 1280, 720);
      // contentPane.add(panel);
      backgroundImage.setVisible(false);
      getContentPane().add(backgroundImage);
      
      // GameBackgroundImage = new
      // ImageIcon(Main.class.getResource("src/icon1.jpg")).getImage();
      // setBounds(100, 100, 1280, 720); // 전체 화면 크기

      // contentPane = new JPanel();

      // contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      // setContentPane(contentPane);
      // contentPane.setLayout(null);
      //메뉴바 만들기 
      //private ImageIcon quitButtonBasicImage = new ImageIcon("src/images/Room"+Integer.toString(i+1)+".png");
      // lobby
      
      //웨이팅 룸 배경화면 
     // WaittingRoomBackground.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/backgroundgameroom.png")));
      //WaittingRoomBackground.setBounds(0, 0, 1280, 720);
      //WaittingRoomBackground.setVisible(true);
      //getContentPane().add(WaittingRoomBackground);
////////////웨이팅 룸 시작
      
      for( j=0;j<4;j++) {
         Room[j] = new JButton(new ImageIcon("src/images/Room"+Integer.toString(j+1)+".png"));
         Room[j].setVisible(true);
         Room[j].setBorderPainted(false);
         Room[j].setContentAreaFilled(false);
         Room[j].setFocusPainted(false);
         Room[j].addActionListener(new ActionListener() {
            int temp = j+1;
         
            public void actionPerformed(ActionEvent arg0) {   
               
               goGame();
               //Roomnum.setText(Integer.toString(WhereIAm)); 방번호 
               //pw.println("quit:" + WhereIAm);
               //pw.println("join:" + Integer.toString(temp) + ":" + nickname);
               WhereIAm = temp;
               Roomnum.setText(Integer.toString(WhereIAm));
            
               //drawing board show
               //iDrawing=createImage(550,490);
               //gDrawing=iDrawing.getGraphics();
               //gDrawing.setColor(Color.WHITE);
               //gDrawing.fillRect(0, 0, 550, 490);
               //repaint();
               
            }
         });
         getContentPane().add(Room[j]);
      }
      Room[0].setBounds(125,120,290,290);
      Room[1].setBounds(415,120,290,290);
      Room[2].setBounds(125,410,290,290);
      Room[3].setBounds(415,410,290,290);
       
      
       // "방 번호" 옆에 쓸 숫자 방 번호 int
       
       Roomnum.setVisible(false);
       Roomnum.setBounds(380,25,20,40);
       Roomnum.setText("?");
       Roomnum.setFont(new Font("휴먼편지체", Font.BOLD, 20));
       Roomnum.setForeground(Color.black);
       getContentPane().add(Roomnum);
       // 총 인원 수 int

         lobyInfototal.setVisible(false);
         lobyInfototal.setText("?");
         lobyInfototal.setBounds(550, 25, 20, 30);
         lobyInfototal.setFont(new Font("휴먼편지체", Font.BOLD, 20));
         lobyInfototal.setForeground(Color.BLACK);
         getContentPane().add(lobyInfototal);
      
      ////////////
      ////////////
      myInfo.setBounds(900, 20, 100, 50);
      myInfo.setVisible(false);
      myInfo.setFont(new Font("휴먼편지체", Font.BOLD, 40));
      myInfo.setForeground(Color.BLACK);
      myInfo.setText(username);
      getContentPane().add(myInfo);

      lobyInfo.setVisible(false);
      lobyInfo.setText("?");
      lobyInfo.setBounds(180, 665, 700, 30);
      lobyInfo.setFont(new Font("휴먼편지체", Font.BOLD, 20));
      lobyInfo.setForeground(Color.BLACK);
      getContentPane().add(lobyInfo);


      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(948, 100, 250, 350); // 채팅창!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      // contentPane.add(scrollPane);
      scrollPane.setVisible(true);
      getContentPane().add(scrollPane);

      textArea = new JTextPane();
      textArea.setEditable(true);
      textArea.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
      scrollPane.setViewportView(textArea);

      // textArea

      txtInput = new JTextField();
      txtInput.setFont(new Font("휴먼편지체", Font.PLAIN, 25));
      txtInput.setBounds(1000, 480, 130, 50); // 채팅창 입력 하는 곳!!!!!!!!!!!!!!!!!!!
      // contentPane.add(txtInput);
      txtInput.setVisible(true);
      getContentPane().add(txtInput);
      txtInput.setColumns(10);

      btnSend = new JButton("전송"); // + send 버튼
      btnSend.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
      btnSend.setBounds(1140, 480, 70, 50);
      // contentPane.add(btnSend);
      btnSend.setVisible(true);
      getContentPane().add(btnSend);
      imgBtn = new JButton("+"); // + 버튼
      imgBtn.setFont(new Font("휴먼편지체", Font.PLAIN, 30));
      imgBtn.setBounds(940, 480, 50, 50);
      // contentPane.add(imgBtn);
      imgBtn.setVisible(true);
      getContentPane().add(imgBtn);

      // 이름 라벨
      // lblUserName = new JButton("Name");

      // lblUserName = new JLabel("Name");
      lblUserName = new JLabel("Name");
      lblUserName.setBorder(new LineBorder(new Color(0, 0, 0)));
      // lblUserName.setBackground(Color.WHITE);
      lblUserName.setFont(new Font("휴먼편지체", Font.BOLD, 30));
      lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
      lblUserName.setBounds(970, 20, 210, 50);
      // contentPane.add(lblUserName);
      lblUserName.setVisible(true);
      getContentPane().add(lblUserName);
      setVisible(true);

      AppendText("User " + username + " connecting " + ip_addr + " " + port_no);
      UserName = username;
      lblUserName.setText(username); // 122번 자리에 자기 원래 닉네임으로 바꿔준다.
      
      
      
      //게임 배경 이미지 
      GameBackground.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/backgroundgameroom.png")));
      GameBackground.setBounds(0, 0, 1280, 720);
      GameBackground.setVisible(true);
      getContentPane().add(GameBackground);

//--- 종료 버튼 ----      

      quitButton.setBounds(1205, 20, 64, 64);
      quitButton.setBorderPainted(false);
      quitButton.setContentAreaFilled(false);
      quitButton.setFocusPainted(false);
      quitButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
            SendObject(msg);
            System.exit(0);
         }
      });
      quitButton.setVisible(true);
      getContentPane().add(quitButton);
      
      
      blackPen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/blackpen.png")));
      // -----------------------색깔 바꾸기 버튼

      //// 색깔 바꾸기 버튼
      
      blackPen.setFont(new Font("휴먼편지체", Font.PLAIN, 8));
      blackPen.setBounds(92, 104, 40, 40);
      blackPen.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 색깔 바꾸기
            // gc2.setColor(Color.RED);
            ChatMsg msg = new ChatMsg(UserName, "600", "black");
            SendObject(msg);
            refreshInfo();
         }
      });
      // contentPane.add(btnNewButton2);
      blackPen.setVisible(false);
      getContentPane().add(blackPen);
      
      
      
      btnNewButton2.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/drawRedPen.png")));
      // -----------------------색깔 바꾸기 버튼

      //// 색깔 바꾸기 버튼
      
      btnNewButton2.setFont(new Font("휴먼편지체", Font.PLAIN, 8));
      btnNewButton2.setBounds(92, 161, 40, 40);
      btnNewButton2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 색깔 바꾸기
            // gc2.setColor(Color.RED);
            ChatMsg msg = new ChatMsg(UserName, "600", "red");
            SendObject(msg);
            refreshInfo();
         }
      });
      // contentPane.add(btnNewButton2);
      btnNewButton2.setVisible(false);
      getContentPane().add(btnNewButton2);
      
      greenPen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/drawGreenPen.png")));
      // -----------------------색깔 바꾸기 버튼
      
      //블루 버튼
      greenPen.setFont(new Font("휴먼편지체", Font.PLAIN, 8));
      greenPen.setBounds(92,218,40,40);
      greenPen.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
            // 색깔 바꾸기
            ChatMsg msg = new ChatMsg(UserName,"600","blue");
            SendObject(msg);
            refreshInfo();
         }
      });
      greenPen.setVisible(false);
      getContentPane().add(greenPen);
      
      bluePen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/bluepen.png")));
      // -----------------------색깔 바꾸기 버튼

      //// 색깔 바꾸기 버튼
      
      bluePen.setFont(new Font("휴먼편지체", Font.PLAIN, 8));
      bluePen.setBounds(92, 275, 40, 40);
      bluePen.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 색깔 바꾸기
            // gc2.setColor(Color.RED);
            ChatMsg msg = new ChatMsg(UserName, "600", "blue");
            SendObject(msg);
            refreshInfo();
         }
      });
      // contentPane.add(btnNewButton2);
      bluePen.setVisible(false);
      getContentPane().add(bluePen);
      
      yellowPen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/yellowpen.png")));
      // -----------------------색깔 바꾸기 버튼

      //// 색깔 바꾸기 버튼
      
      yellowPen.setFont(new Font("휴먼편지체", Font.PLAIN, 8));
      yellowPen.setBounds(92, 333, 40, 40);
      yellowPen.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 색깔 바꾸기
            // gc2.setColor(Color.RED);
            ChatMsg msg = new ChatMsg(UserName, "600", "yellow");
            SendObject(msg);
            refreshInfo();
         }
      });
      // contentPane.add(btnNewButton2);
      yellowPen.setVisible(false);
      getContentPane().add(yellowPen);
      
      eraser.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/eraser.png")));
      //// 색깔 바꾸기 버튼
      
      eraser.setFont(new Font("휴먼편지체", Font.PLAIN, 7));
      eraser.setBounds(92, 390, 40, 40);
      eraser.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 색깔 바꾸기
            // gc2.setColor(Color.RED);
            ChatMsg msg = new ChatMsg(UserName, "600", "eraser");
            SendObject(msg);

         }
      });
      // contentPane.add(btnNewButton3);
      eraser.setVisible(false);
      getContentPane().add(eraser);
      
      trashcan.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/trashcan.png")));
      // -----------------------색깔 바꾸기 버튼

      //// 색깔 바꾸기 버튼 
      // 초기화 버튼
      
      trashcan.setFont(new Font("휴먼편지체", Font.PLAIN, 7));
      trashcan.setBounds(92, 447, 40, 40);

      trashcan.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 색깔 바꾸기
            // gc2.setColor(Color.RED);
            ChatMsg msg = new ChatMsg(UserName, "600", "init");
            SendObject(msg);

         }
      });
      // contentPane.add(btnNewButton4);
      // btnNewButton4.setVisible(true);
      trashcan.setVisible(false);
      getContentPane().add(trashcan);
      
      rectangle.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/rectangle.png")));
 
      // 사각형 변환 버튼
      
      rectangle.setFont(new Font("휴먼편지체", Font.PLAIN, 7));
      rectangle.setBounds(798, 161, 40, 40);

      rectangle.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 색깔 바꾸기
            // gc2.setColor(Color.RED);
            ChatMsg msg = new ChatMsg(UserName, "600", "init");
            SendObject(msg);

         }
      });
      // contentPane.add(btnNewButton4);
      // btnNewButton4.setVisible(true);
      rectangle.setVisible(false);
      getContentPane().add(rectangle);
      
      circle.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/circle.png")));

      // 원형 변환 버튼
      
      circle.setFont(new Font("휴먼편지체", Font.PLAIN, 7));
      circle.setBounds(798, 255, 40, 40);

      circle.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 색깔 바꾸기
            // gc2.setColor(Color.RED);
            ChatMsg msg = new ChatMsg(UserName, "600", "init");
            SendObject(msg);

         }
      });
      // contentPane.add(btnNewButton4);
      // btnNewButton4.setVisible(true);
      circle.setVisible(false);
      getContentPane().add(circle);
      
      triangle.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/triangle.png")));

      // 삼각형 변환 버튼
      
      triangle.setFont(new Font("휴먼편지체", Font.PLAIN, 7));
      triangle.setBounds(798, 348, 40, 40);

      triangle.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 색깔 바꾸기
            // gc2.setColor(Color.RED);
            ChatMsg msg = new ChatMsg(UserName, "600", "init");
            SendObject(msg);

         }
      });
      // contentPane.add(btnNewButton4);
      // btnNewButton4.setVisible(true);
      triangle.setVisible(false);
      getContentPane().add(triangle);
      
      // -----------------------색깔 바꾸기 버튼 끝

      panel = new JPanel();
      panel.setBackground(Color.WHITE);
      panel.setBounds(164, 100, 600, 420);
      // contentPane.add(panel);
       panel.setVisible(false);
      getContentPane().add(panel);
      
      gc = panel.getGraphics();

      // Image 영역 보관용. paint() 에서 이용한다.
      panelImage = createImage(panel.getWidth(), panel.getHeight());
      gc2 = panelImage.getGraphics();
      gc2.setColor(panel.getBackground());
      gc2.fillRect(0, 0, panel.getWidth(), panel.getHeight());
      // gc2.setColor(Color.BLACK);
      gc2.drawRect(0, 0, panel.getWidth() - 1, panel.getHeight() - 1);

      lblMouseEvent = new JLabel("<dynamic>");
      lblMouseEvent.setHorizontalAlignment(SwingConstants.CENTER);
      lblMouseEvent.setFont(new Font("휴먼편지체", Font.BOLD, 14));
      lblMouseEvent.setBorder(new LineBorder(new Color(0, 0, 0)));
      lblMouseEvent.setBackground(Color.WHITE);
      lblMouseEvent.setBounds(376, 539, 400, 40); ////// 휠의 위치 알려주는 판 !!!!!!!!
      lblMouseEvent.setVisible(false);
      // contentPane.add(lblMouseEvent);
      // add(lblMouseEvent);

      try {
         socket = new Socket(ip_addr, Integer.parseInt(port_no));
//         is = socket.getInputStream();
//         dis = new DataInputStream(is);
//         os = socket.getOutputStream();
//         dos = new DataOutputStream(os);

         oos = new ObjectOutputStream(socket.getOutputStream());
         oos.flush();
         ois = new ObjectInputStream(socket.getInputStream());

         // SendMessage("/login " + UserName);
         ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
         SendObject(obcm);

         ListenNetwork net = new ListenNetwork();
         net.start();
         TextSendAction action = new TextSendAction();
         btnSend.addActionListener(action);
         txtInput.addActionListener(action);
         txtInput.requestFocus();
         ImageSendAction action2 = new ImageSendAction();
         imgBtn.addActionListener(action2);
         MyMouseEvent mouse = new MyMouseEvent();
         panel.addMouseMotionListener(mouse);
         panel.addMouseListener(mouse);
         MyMouseWheelEvent wheel = new MyMouseWheelEvent();
         panel.addMouseWheelListener(wheel);

      } catch (NumberFormatException | IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         AppendText("connect error");
      }

   }
   // 게임창 끝
   // -------------------------------------------------------------------------------------------------------------------------

   //게임 창 으로 들어가기 
   public void goGame() {
	   
	   
      for(int i=0;i<4;i++)
         Room[i].setVisible(false);
      //WaittingRoomBackground.setVisible(false);
      //GameBackground.setVisible(true);
      GameBackground.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/backgroundgame.png")));
      
       panel.setVisible(true); // 게임판 생성 
      
       //지역변수로 하면 여기서 못써서 위에서 전역변수로 생성 하는거네 아....
       lobyInfo.setVisible(true);
       lobyInfototal.setVisible(true);
       Roomnum.setVisible(true); // 방번호 
       blackPen.setVisible(true);
       
       btnNewButton2.setVisible(true);
       eraser.setVisible(true);
       bluePen.setVisible(true);
       yellowPen.setVisible(true);
       trashcan.setVisible(true);
       rectangle.setVisible(true);
       circle.setVisible(true);
       triangle.setVisible(true);
       greenPen.setVisible(true);
       backgroundImage.setVisible(false);
     
      
       
      
      
   }
   
   
   
   public void refreshInfo() {
      SendInfoReq("/lc");
   }

   public void paint(Graphics g) { 
      
      super.paint(g);
      paintComponents(g);
      
        //g.drawImage(GameBackgroundImage, 0, 0, 1280,720, null);
        // Image 영역이 가려졌다 다시 나타날 때 그려준다.
       // gc.drawImage(panelImage, 0, 0, this);
      // gui ,, jframe에서 가장 먼저 그려주는 함수
//      super.paint(g);

    //paintComponents(g);
      // g.drawImage(GameBackgroundImage, 0, 0, null);

      // Image 영역이 가려졌다 다시 나타날 때 그려준다.
       //screenImage = createImage(1280, 720); // 판생성
       //screenGraphic = screenImage.getGraphics(); // 판에 그래픽객체 얻어오기
       //screenDraw(screenGraphic); // 스크린 그래픽에 어떠한 그림을 그려준다.
//      if(!check)
      // g.drawImage(GameBackgroundImage,0,0,null);
//       if(!check)
      //paintComponents(g);
      // g.drawImage(GameBackgroundImage, 0, 0, null);
   
       
      // paintComponents(g);
      
      

   }

   public void screenDraw(Graphics g) {

      // paintComponents(g);
       //g.drawImage(GameBackgroundImage, 0, 0, null);
       //paintComponents(g);
      //this.repaint();
   }

   // Server Message를 수신해서 화면에 표시
   class ListenNetwork extends Thread {
      public void run() {
         while (true) {
            try {

               Object obcm = null;
               String msg = null;
               ChatMsg cm;
               try {
                  obcm = ois.readObject();
               } catch (ClassNotFoundException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                  break;
               }
               if (obcm == null)
                  break;
               if (obcm instanceof ChatMsg) {
                  cm = (ChatMsg) obcm;
                  msg = String.format("[%s]\n%s", cm.UserName, cm.data);
               } else
                  continue;
               switch (cm.code) {
               case "200": // chat message
                  if (cm.UserName.equals(UserName))
                     AppendTextR(msg); // 내 메세지는 우측에
                  else
                     AppendText(msg);
                  break;
               case "300": // Image 첨부
                  if (cm.UserName.equals(UserName))
                     AppendTextR("[" + cm.UserName + "]");
                  else
                     AppendText("[" + cm.UserName + "]");
                  AppendImage(cm.img);
                  break;
               case "500": // Mouse Event 수신

                  DoMouseEvent(cm);
                  break;
               case "600": // 마우스 색깔 바꿈
                  // DoMouseEvent(cm);
                  switch (cm.data) {
                  case "red":
                     gc2.setColor(Color.RED);
                     break;
                  case "eraser":
                     gc2.setColor(Color.WHITE); // 지우개
                     break;
                  case "init":
                     // panelImage

                     gc2.setColor(Color.WHITE); // 지우개

                     gc2.fillRect(0, 0, 1000, 1000); // (376, 10, 400, 520);
                     gc.drawImage(panelImage, 0, 0, panel);
                     gc2.setColor(Color.BLACK);

                     // gc2.setColor(panel.getBackground()); // 지우개
                     // gc2.setColor(Color.RED);
                     break;

                  default:
                     break;
                  }

                  break;
               case "700":
                  String[] datas = cm.data.split(":");
                  lobyInfototal.setText(datas[0]);
                  String members = "";
                  for (int i = 0; i < Integer.parseInt(datas[0]); i++)
                     members = members + datas[i + 1] + "               ";
                  lobyInfo.setText(members);
                  break;
               case "800":
                  refreshInfo();
               }
            } catch (IOException e) {
               AppendText("ois.readObject() error");
               try {
//                  dos.close();
//                  dis.close();
                  ois.close();
                  oos.close();
                  socket.close();

                  break;
               } catch (Exception ee) {
                  break;
               } // catch문 끝
            } // 바깥 catch문끝

         }
      }
   }

   // Mouse Event 수신 처리
   public void DoMouseEvent(ChatMsg cm) {
      Color c;
      if (cm.UserName.matches(UserName)) // 본인 것은 이미 Local 로 그렸다. 수신한 걸 무시함
         return;
      // c = new Color(255, 0, 0); // 다른 사람 것은 Red
      // gc2.setColor(c);
      // AppendText("hello");
      gc2.fillOval(cm.mouse_e.getX() - pen_size / 2, cm.mouse_e.getY() - cm.pen_size / 2, cm.pen_size, cm.pen_size);
      gc.drawImage(panelImage, 0, 0, panel);
   }

   public void SendMouseEvent(MouseEvent e) {

      ChatMsg cm = new ChatMsg(UserName, "500", "MOUSE");
      cm.mouse_e = e;
      cm.pen_size = pen_size;
      SendObject(cm); // 이벤트를 보낸다. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   }

   class MyMouseWheelEvent implements MouseWheelListener {
      @Override
      public void mouseWheelMoved(MouseWheelEvent e) {

         // TODO Auto-generated method stub
         if (e.getWheelRotation() < 0) { // 위로 올리는 경우 pen_size 증가
            if (pen_size < 20)
               pen_size++;
         } else {
            if (pen_size > 2)
               pen_size--;
         }
         lblMouseEvent.setText("mouseWheelMoved Rotation=" + e.getWheelRotation() + " pen_size = " + pen_size + " "
               + e.getX() + "," + e.getY());

      }

   }

   // Mouse Event Handler
   class MyMouseEvent implements MouseListener, MouseMotionListener {
      @Override
      public void mouseDragged(MouseEvent e) {
         // if(!UserName.equals(author)) return; // 출제자만 그릴 수 있다.
         if (isFirst && gc2.getColor().equals(Color.WHITE)) {
            isFirst = false;
            gc2.setColor(Color.black);

         }
         lblMouseEvent.setText(e.getButton() + " mouseDragged " + e.getX() + "," + e.getY());// 좌표출력가능
         // Color c = new Color(0,0,255);
         // gc2.setColor(c);
         gc2.fillOval(e.getX() - pen_size / 2, e.getY() - pen_size / 2, pen_size, pen_size);
         // panelImnage는 paint()에서 이용한다.
         gc.drawImage(panelImage, 0, 0, panel);
         SendMouseEvent(e);
      }

      @Override
      public void mouseMoved(MouseEvent e) {
         // if(!UserName.equals(author)) return; // 출제자만 그릴 수 있다.
         if (isFirst && gc2.getColor().equals(Color.WHITE)) {
            isFirst = false;
            gc2.setColor(Color.black);

         }
         lblMouseEvent.setText(e.getButton() + " mouseMoved " + e.getX() + "," + e.getY());
      }

      @Override
      public void mouseClicked(MouseEvent e) {
         // if(!UserName.equals(author)) return; // 출제자만 그릴 수 있다.
         lblMouseEvent.setText(e.getButton() + " mouseClicked " + e.getX() + "," + e.getY());
         if (isFirst && gc2.getColor().equals(Color.WHITE)) {
            isFirst = false;
            gc2.setColor(Color.black);

         }
         // Color c = new Color(0,0,255);
         // gc2.setColor(c);
         gc2.fillOval(e.getX() - pen_size / 2, e.getY() - pen_size / 2, pen_size, pen_size);
         gc.drawImage(panelImage, 0, 0, panel);
         SendMouseEvent(e);
      }

      @Override
      public void mouseEntered(MouseEvent e) {
         // if(!UserName.equals(author)) return; // 출제자만 그릴 수 있다.
         if (isFirst && gc2.getColor().equals(Color.WHITE)) {
            isFirst = false;
            gc2.setColor(Color.black);

         }
         lblMouseEvent.setText(e.getButton() + " mouseEntered " + e.getX() + "," + e.getY());
         // panel.setBackground(Color.YELLOW);

      }

      @Override
      public void mouseExited(MouseEvent e) {
         lblMouseEvent.setText(e.getButton() + " mouseExited " + e.getX() + "," + e.getY());
         // panel.setBackground(Color.CYAN);

      }

      @Override
      public void mousePressed(MouseEvent e) {
         lblMouseEvent.setText(e.getButton() + " mousePressed " + e.getX() + "," + e.getY());

      }

      @Override
      public void mouseReleased(MouseEvent e) {
         lblMouseEvent.setText(e.getButton() + " mouseReleased " + e.getX() + "," + e.getY());
         // 드래그중 멈출시 보임

      }
   }

   // keyboard enter key 치면 서버로 전송
   class TextSendAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         // Send button을 누르거나 메시지 입력하고 Enter key 치면
         if (e.getSource() == btnSend || e.getSource() == txtInput) {
            String msg = null;
            // msg = String.format("[%s] %s\n", UserName, txtInput.getText());
            msg = txtInput.getText();
            SendMessage(msg);
            txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
            txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
            if (msg.contains("/exit")) // 종료 처리
               System.exit(0);
         }
      }
   }

   class ImageSendAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         // 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
         if (e.getSource() == imgBtn) {
            frame = new Frame("이미지첨부");
            fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
            // frame.setVisible(true);
            // fd.setDirectory(".\\");
            fd.setVisible(true);
            // System.out.println(fd.getDirectory() + fd.getFile());
            if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
               ChatMsg obcm = new ChatMsg(UserName, "300", "IMG");
               ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
               obcm.img = img;
               SendObject(obcm);
            }
         }
      }
   }

   ImageIcon icon1 = new ImageIcon("src/icon1.jpg");
  

   public void AppendIcon(ImageIcon icon) {
      int len = textArea.getDocument().getLength();
      // 끝으로 이동
      textArea.setCaretPosition(len);
      textArea.insertIcon(icon);
   }

   // 화면에 출력
   public void AppendText(String msg) {
      // textArea.append(msg + "\n");
      // AppendIcon(icon1);
      msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
      // textArea.setCaretPosition(len);
      // textArea.replaceSelection(msg + "\n");

      StyledDocument doc = textArea.getStyledDocument();
      SimpleAttributeSet left = new SimpleAttributeSet();
      StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
      StyleConstants.setForeground(left, Color.BLACK);
      doc.setParagraphAttributes(doc.getLength(), 1, left, false);
      try {
         doc.insertString(doc.getLength(), msg + "\n", left);
      } catch (BadLocationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      int len = textArea.getDocument().getLength();
      textArea.setCaretPosition(len);
      // textArea.replaceSelection("\n");

   }

   // 화면 우측에 출력
   public void AppendTextR(String msg) {
      msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
      StyledDocument doc = textArea.getStyledDocument();
      SimpleAttributeSet right = new SimpleAttributeSet();
      StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
      StyleConstants.setForeground(right, Color.BLUE);
      doc.setParagraphAttributes(doc.getLength(), 1, right, false);
      try {
         doc.insertString(doc.getLength(), msg + "\n", right);
      } catch (BadLocationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      int len = textArea.getDocument().getLength();
      textArea.setCaretPosition(len);
      // textArea.replaceSelection("\n");

   }

   public void AppendImage(ImageIcon ori_icon) {
      int len = textArea.getDocument().getLength();
      textArea.setCaretPosition(len); // place caret at the end (with no selection)
      Image ori_img = ori_icon.getImage();
      Image new_img;
      ImageIcon new_icon;
      int width, height;
      double ratio;
      width = ori_icon.getIconWidth();
      height = ori_icon.getIconHeight();
      // Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
      if (width > 200 || height > 200) {
         if (width > height) { // 가로 사진
            ratio = (double) height / width;
            width = 200;
            height = (int) (width * ratio);
         } else { // 세로 사진
            ratio = (double) width / height;
            height = 200;
            width = (int) (height * ratio);
         }
         new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
         new_icon = new ImageIcon(new_img);
         textArea.insertIcon(new_icon);
      } else {
         textArea.insertIcon(ori_icon);
         new_img = ori_img;
      }
      len = textArea.getDocument().getLength();
      textArea.setCaretPosition(len);
      textArea.replaceSelection("\n");
      // ImageViewAction viewaction = new ImageViewAction();
      // new_icon.addActionListener(viewaction); // 내부클래스로 액션 리스너를 상속받은 클래스로
      // panelImage = ori_img.getScaledInstance(panel.getWidth(), panel.getHeight(),
      // Image.SCALE_DEFAULT);

      gc2.drawImage(ori_img, 0, 0, panel.getWidth(), panel.getHeight(), panel);
      gc.drawImage(panelImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
   }

   // Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
   public byte[] MakePacket(String msg) {
      byte[] packet = new byte[BUF_LEN];
      byte[] bb = null;
      int i;
      for (i = 0; i < BUF_LEN; i++)
         packet[i] = 0;
      try {
         bb = msg.getBytes("euc-kr");
      } catch (UnsupportedEncodingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         System.exit(0);
      }
      for (i = 0; i < bb.length; i++)
         packet[i] = bb[i];
      return packet;
   }

   // Server에게 network으로 전송
   public void SendMessage(String msg) {
      try {
         // dos.writeUTF(msg);
//         byte[] bb;
//         bb = MakePacket(msg);
//         dos.write(bb, 0, bb.length);
         ChatMsg obcm = new ChatMsg(UserName, "200", msg);
         oos.writeObject(obcm);
      } catch (IOException e) {
         // AppendText("dos.write() error");
         AppendText("oos.writeObject() error");
         try {
//            dos.close();
//            dis.close();
            ois.close();
            oos.close();
            socket.close();
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.exit(0);
         }
      }
   }

   public void SendInfoReq(String msg) {
      try {
         // dos.writeUTF(msg);
//         byte[] bb;
//         bb = MakePacket(msg);
//         dos.write(bb, 0, bb.length);
         ChatMsg obcm = new ChatMsg(UserName, "700", msg);
         oos.writeObject(obcm);
      } catch (IOException e) {
         // AppendText("dos.write() error");
         AppendText("oos.writeObject() error");
         try {
//            dos.close();
//            dis.close();
            ois.close();
            oos.close();
            socket.close();
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.exit(0);
         }
      }
   }

   public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
      try {
         oos.writeObject(ob);
      } catch (IOException e) {
         // textArea.append("메세지 송신 에러!!\n");
         AppendText("SendObject Error");
      }
   }
}
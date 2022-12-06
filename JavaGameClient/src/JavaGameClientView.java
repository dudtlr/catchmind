
// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
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
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

import java.awt.BasicStroke;

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

	private JPanel panel;// 원래 퍼블릭이였음
	private JLabel backgroundImage; // 배경에 쓸 이미지 넣어놓는 용도

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
	private ImageIcon quitButtonBasicImage = new ImageIcon("src/images/exit.png"); // 게임호면 나가기 버튼
	private ImageIcon euitButtonBasicImage = new ImageIcon("src/images/exitButtonBasic.png"); // 메뉴바 나가기 버튼 basic
	private ImageIcon euitButtonBasicEnterdImage = new ImageIcon("src/images/exitButtonEntered.png");
	// ---------맨위에 메뉴바 -------------
	private JLabel menuBar = new JLabel(); // 메뉴바
	private JButton exitButton = new JButton(euitButtonBasicImage);

	private int mouseX, mouseY;

	// ---------맨위에 메뉴바 -------------

	// 버튼에 이미지 넣기

	// 로비 창 선언
	public JLabel myInfo = new JLabel();
	public JLabel myInfo2 = new JLabel();
	public JLabel lobyInfototal = new JLabel();
	public JLabel lobyInfo = new JLabel();
	public JButton Room[] = new JButton[4];

	public int i;
	int j;
	int a;
	public int WhereIAm = 0;

	// 게임창 컴포넌트 들
	private JButton quitButton = new JButton(quitButtonBasicImage); // 나가기 버튼
	public JButton btnNewButton2 = new JButton(""); // 레드 버튼
	public JButton eraser = new JButton("지우개"); // 지우개 버튼
	public JButton trashcan = new JButton("초기화"); // 초기화 버튼
	public JButton greenPen = new JButton(""); // 그린 버튼
	public JButton blackPen = new JButton(""); // 블랙 버튼
	public JButton bluePen = new JButton(""); // 블루 버튼
	public JButton yellowPen = new JButton(""); // 옐로우 버튼
	public JButton rectangle = new JButton(""); // 사각형
	public JButton circle = new JButton(""); // 원형
	public JButton triangle = new JButton(""); // 삼각형
	public JLabel Roomnum = new JLabel(); // 방 번호
	public JLabel Roompeople = new JLabel();

	public JButton StartButton = new JButton(""); // 시작버튼
	public JButton PassButton = new JButton(""); // 답지 패스 버튼

	// 게임 배경화면
	private final JLabel GameBackground = new JLabel("New label");

	// 대기실 배경화면
	// private final JLabel WaittingRoomBackground = new JLabel("New label");

	// -------------drawLine에 쓰이는 것들

	private int x1, y1, x2, y2, z1, z2; // 드래그시 선을 그을 좌표값

	// -------------drawLine에 쓰이는 것들

	// --------문제 내는 필드 -----------
	ArrayList<String> quiz = new ArrayList<String>(Arrays.asList("사자성어", "딸기", "인어공주", "타이타닉", "올림픽", "다크서클", "세종대왕",
			"일석이조", "십중팔구", "터미네이터", "오토바이", "요리사", "군인", "야구", "탁구", "박명수", "최기근"));
	// ********문제 내는 필드 ***********
	// private boolean boss = false; //true 이면 방장 , false이면 일반
	private String answer;
	private JLabel answerView; // 답 보여주는 레이블
	private boolean IsGameing = false;// 게임 실행 중 이니?
	private boolean boss = false; // 방장인지 아닌지
	private boolean IsGameStep = false;

	private boolean IsLine = false;
	private boolean IsRect = false;
	private boolean IsCircle = false;

	private boolean GamePage = false;

	private int RoomNumber; // 게임 방번호

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
		// setResizable(false); // 사이즈 조절 안되게
		getContentPane().setLayout(null);
		setVisible(true);

		// 메뉴바 옆에 exit 버튼
		exitButton.setBounds(1245, 0, 32, 32); //
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(euitButtonBasicEnterdImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(euitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// int sel = JOptionPane.showConfirmDialog(frame, "대기실로 나가시겠습니까?");
				ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
				SendObject(msg);
				System.exit(0);
				//// user1

				////

			}
		});

		exitButton.setVisible(true);
		getContentPane().add(exitButton);

		// ---- 메뉴바 옆에 exit 버튼

		// ------------------------메뉴바----------------
		menuBar.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/menuBar.png")));
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();

			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
				// repaint();
			}

		});
		menuBar.setVisible(true);
		getContentPane().add(menuBar);
		// ------------------------메뉴바----------------

////////////웨이팅 룸 시작

		for (j = 0; j < 4; j++) {
			Room[j] = new JButton(new ImageIcon("src/images/Room" + Integer.toString(j + 1) + ".png"));
			Room[j].setVisible(true);
			Room[j].setBorderPainted(false);
			Room[j].setContentAreaFilled(false);
			Room[j].setFocusPainted(false);
			getContentPane().add(Room[j]);
		}
		Room[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Room[0].setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/Room1_hover.png")));
				Room[0].setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				Room[0].setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/Room1.png")));
				Room[0].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ChatMsg cm = new ChatMsg(UserName, "1000", "1");
				cm.setRoomNumber(1);
				SendObject(cm);

				myInfo.setVisible(false);

				Roomnum.setText(Integer.toString(1));
				goGame();
				// refreshInfo();
				
			}
		});
		Room[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Room[1].setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/Room2_hover.png")));
				Room[1].setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				Room[1].setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/Room2.png")));
				Room[1].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ChatMsg cm = new ChatMsg(UserName, "1000", "2");
				cm.setRoomNumber(2);
				SendObject(cm);

				myInfo.setVisible(false);

				Roomnum.setText(Integer.toString(2));
				goGame();
				// refreshInfo();
				
			}
		});
		Room[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Room[2].setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/Room3_hover.png")));
				Room[2].setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				Room[2].setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/Room3.png")));
				Room[2].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ChatMsg cm = new ChatMsg(UserName, "1000", "3");
				cm.setRoomNumber(3);
				SendObject(cm);

				myInfo.setVisible(false);

				Roomnum.setText(Integer.toString(3));
				goGame();
				// refreshInfo();
			}
		});
		Room[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Room[3].setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/Room4_hover.png")));
				Room[3].setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				Room[3].setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/Room4.png")));
				Room[3].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ChatMsg cm = new ChatMsg(UserName, "1000", "4");
				cm.setRoomNumber(4);
				SendObject(cm);
				
				myInfo.setVisible(false);

				Roomnum.setText(Integer.toString(4));
				goGame();
				// refreshInfo();
				
			}
		});
//		Room[0].addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent arg0) {
//
//				ChatMsg cm = new ChatMsg(UserName, "1000", "1");
//				cm.setRoomNumber(1);
//				SendObject(cm);
//
//				myInfo.setVisible(false);
//
//				Roomnum.setText(Integer.toString(1));
//				goGame();
//				// refreshInfo();
//
//			}
//		});
//		Room[1].addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent arg0) {
//
//				ChatMsg cm = new ChatMsg(UserName, "1000", "2");
//				cm.setRoomNumber(2);
//				SendObject(cm);
//
//				myInfo.setVisible(false);
//
//				Roomnum.setText(Integer.toString(2));
//				goGame();
//				// refreshInfo();
//
//			}
//		});
//		Room[2].addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent arg0) {
//
//				ChatMsg cm = new ChatMsg(UserName, "1000", "3");
//				cm.setRoomNumber(3);
//				SendObject(cm);
//				myInfo.setVisible(false);
//
//				Roomnum.setText(Integer.toString(3));
//				goGame();
//				// refreshInfo();
//
//			}
//		});
//		Room[3].addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent arg0) {
//
//				ChatMsg cm = new ChatMsg(UserName, "1000", "4");
//				cm.setRoomNumber(4);
//				System.out.println("4번방클릭");
//				SendObject(cm);
//				myInfo.setVisible(false);
//
//				Roomnum.setText(Integer.toString(4));
//				goGame();
//				// refreshInfo();
//			}
//		});
		Room[0].setBounds(295, 120, 290, 290);
		Room[1].setBounds(585, 120, 290, 290);
		Room[2].setBounds(295, 410, 290, 290);
		Room[3].setBounds(585, 410, 290, 290);

		// "방 번호" 옆에 쓸 숫자 방 번호 int

		Roomnum.setVisible(false);
		Roomnum.setBounds(310, 57, 20, 30);

		Roomnum.setText("?");
		Roomnum.setFont(new Font("휴먼편지체", Font.BOLD, 20));
		Roomnum.setForeground(Color.black);
		getContentPane().add(Roomnum);
		// 총 인원 수 int

		lobyInfototal.setVisible(false);
		lobyInfototal.setText("?");
		lobyInfototal.setBounds(440, 57, 20, 30);
		lobyInfototal.setFont(new Font("휴먼편지체", Font.BOLD, 20));
		lobyInfototal.setForeground(Color.BLACK);
		getContentPane().add(lobyInfototal);

		////////////
		////////////
		myInfo.setBounds(1036, 120, 100, 50);
		myInfo.setVisible(true);
		myInfo.setFont(new Font("휴먼편지체", Font.BOLD, 30));
		myInfo.setForeground(Color.BLACK);
		myInfo.setText(username);
		getContentPane().add(myInfo);

		// 방장표시
		myInfo2.setBounds(820, 50, 100, 50);
		myInfo2.setVisible(false);
		myInfo2.setFont(new Font("휴먼편지체", Font.BOLD, 20));
		myInfo2.setForeground(Color.BLACK);
		myInfo2.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/boss.png")));
		getContentPane().add(myInfo2);

		lobyInfo.setVisible(false);
		lobyInfo.setText("?");
		lobyInfo.setBounds(193, 635, 700, 30);
		lobyInfo.setFont(new Font("휴먼편지체", Font.BOLD, 20));
		lobyInfo.setForeground(Color.BLACK);
		getContentPane().add(lobyInfo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(956, 225, 250, 255); // 채팅
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
		txtInput.setBounds(1005, 510, 130, 50); // 채팅창 입력 하는 곳!!!!!!!!!!!!!!!!!!!

		txtInput.setVisible(true);
		getContentPane().add(txtInput);
		txtInput.setColumns(10);

		btnSend = new JButton("전송"); // + send 버튼
		btnSend.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		btnSend.setBounds(1145, 510, 70, 50);

		btnSend.setVisible(true);
		getContentPane().add(btnSend);
		imgBtn = new JButton("+"); // + 버튼
		imgBtn.setFont(new Font("휴먼편지체", Font.PLAIN, 30));
		imgBtn.setBounds(945, 510, 50, 50);
		// contentPane.add(imgBtn);
		imgBtn.setVisible(true);
		getContentPane().add(imgBtn);

		// 이름 라벨
		// lblUserName = new JButton("Name");

		// lblUserName = new JLabel("Name");
		lblUserName = new JLabel("Name");
		// lblUserName.setBorder(new LineBorder(new Color(0, 0, 0)));
		// lblUserName.setBackground(Color.WHITE);
		lblUserName.setFont(new Font("휴먼편지체", Font.BOLD, 25));
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBounds(650, 55, 100, 30);
		lblUserName.setVisible(false);
		getContentPane().add(lblUserName);
		setVisible(true);

		AppendText("User " + username + " connecting " + ip_addr + " " + port_no);
		UserName = username;
		lblUserName.setText(username); // 122번 자리에 자기 원래 닉네임으로 바꿔준다.

		// 게임 배경 이미지
		// GameBackground.setIcon(new
		// ImageIcon(JavaGameClientView.class.getResource("/images/backgroundgameroom.png")));
		// GameBackground.setBounds(0, 0, 1280, 720);
		// GameBackground.setVisible(true);
		// getContentPane().add(GameBackground);

		// --게임 시작 버튼

		StartButton.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/start.png")));
		StartButton.setFont(new Font("휴먼편지체", Font.PLAIN, 8));
		StartButton.setBounds(770, 480, 150, 150);
		StartButton.setBorderPainted(false);
		StartButton.setContentAreaFilled(false);
		StartButton.setFocusPainted(false);
		StartButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				StartButton.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/start2.png")));
				StartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				StartButton.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/start.png")));
				StartButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (boss == true) {
					answerView.setVisible(true);
					PassButton.setVisible(true);
				}
				// quitButton.setEnabled(false);

				// 게임시작
				ChatMsg cm2 = new ChatMsg(UserName, "901", "게임을 시작하겠습니다!!!");
				SendObject(cm2);

				ChatMsg cm = new ChatMsg(UserName, "900", "퀴즈출제");

				cm.setAnswer(quiz());
				SendObject(cm);
				StartButton.setEnabled(false);
				StartButton.setVisible(false);
			}
		});
//		StartButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (boss == true) {
//					answerView.setVisible(true);
//					PassButton.setVisible(true);
//				}
//				// quitButton.setEnabled(false);
//
//				// 게임시작
//				ChatMsg cm2 = new ChatMsg(UserName, "901", "게임을 시작하겠습니다!!!");
//				SendObject(cm2);
//
//				ChatMsg cm = new ChatMsg(UserName, "900", "퀴즈출제");
//
//				cm.setAnswer(quiz());
//				SendObject(cm);
//				StartButton.setEnabled(false);
//
//			}
//		});
		// contentPane.add(btnNewButton2);
		StartButton.setVisible(false);
		getContentPane().add(StartButton);

		// -- 게임 시작버튼
		// ----------답 패스 버튼

		PassButton.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/skip3.png")));
		PassButton.setFont(new Font("휴먼편지체", Font.PLAIN, 8));
		PassButton.setBounds(770, 380, 150, 150);
		PassButton.setBorderPainted(false);
		PassButton.setContentAreaFilled(false);
		PassButton.setFocusPainted(false);
		PassButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				PassButton.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/skip4.png")));
				PassButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				PassButton.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/skip3.png")));
				PassButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (boss == true) {
					answerView.setVisible(true);
				}
				// 게임시작
				ChatMsg cm2 = new ChatMsg(UserName, "901", "게임을 시작하겠습니다!!!");
				SendObject(cm2);

				ChatMsg cm = new ChatMsg(UserName, "900", "퀴즈출제");

				cm.setAnswer(quiz());
				SendObject(cm);

			}
		});
//		PassButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (boss == true) {
//					answerView.setVisible(true);
//				}
//				// 게임시작
//				ChatMsg cm2 = new ChatMsg(UserName, "901", "게임을 시작하겠습니다!!!");
//				SendObject(cm2);
//
//				ChatMsg cm = new ChatMsg(UserName, "900", "퀴즈출제");
//
//				cm.setAnswer(quiz());
//				SendObject(cm);
//
//			}
//		});
		// contentPane.add(btnNewButton2);
		PassButton.setVisible(false);
		getContentPane().add(PassButton);

		// ----------답 패스 버튼
//--- 종료 버튼 ----      

		quitButton.setBounds(1205, 20, 64, 64);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/exit.png")));
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/exit.png")));
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
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
		blackPen.setBorderPainted(false);
		blackPen.setContentAreaFilled(false);
		blackPen.setFocusPainted(false);
//		blackPen.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// 색깔 바꾸기
//				// gc2.setColor(Color.RED);
//				ChatMsg msg = new ChatMsg(UserName, "600", "black");
//				SendObject(msg);
//				refreshInfo();
//			}
//		});
		blackPen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				blackPen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/blackpen2.png")));
				blackPen.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				blackPen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/blackpen.png")));
				blackPen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
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
		btnNewButton2.setBorderPainted(false);
		btnNewButton2.setContentAreaFilled(false);
		btnNewButton2.setFocusPainted(false);
//		btnNewButton2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// 색깔 바꾸기
//				// gc2.setColor(Color.RED);
//				ChatMsg msg = new ChatMsg(UserName, "600", "red");
//				SendObject(msg);
//				refreshInfo();
//			}
//		});
		btnNewButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton2.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/redpen2.png")));
				btnNewButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton2.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/drawRedPen.png")));
				btnNewButton2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
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

		// 블루 버튼
		greenPen.setFont(new Font("휴먼편지체", Font.PLAIN, 8));
		greenPen.setBounds(92, 218, 40, 40);
		greenPen.setBorderPainted(false);
		greenPen.setContentAreaFilled(false);
		greenPen.setFocusPainted(false);
//		greenPen.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// 색깔 바꾸기
//				ChatMsg msg = new ChatMsg(UserName, "600", "green");
//				SendObject(msg);
//				refreshInfo();
//			}
//		});
		greenPen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				greenPen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/greenpen2.png")));
				greenPen.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				greenPen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/drawGreenPen.png")));
				greenPen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ChatMsg msg = new ChatMsg(UserName, "600", "green");
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
		bluePen.setBorderPainted(false);
		bluePen.setContentAreaFilled(false);
		bluePen.setFocusPainted(false);
//		bluePen.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// 색깔 바꾸기
//				// gc2.setColor(Color.RED);
//				ChatMsg msg = new ChatMsg(UserName, "600", "blue");
//				SendObject(msg);
//				refreshInfo();
//			}
//		});
		bluePen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bluePen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/bluepen2.png")));
				bluePen.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				bluePen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/bluepen.png")));
				bluePen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
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
		yellowPen.setBorderPainted(false);
		yellowPen.setContentAreaFilled(false);
		yellowPen.setFocusPainted(false);
//		yellowPen.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// 색깔 바꾸기
//				// gc2.setColor(Color.RED);
//				ChatMsg msg = new ChatMsg(UserName, "600", "yellow");
//				SendObject(msg);
//				refreshInfo();
//			}
//		});
		yellowPen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				yellowPen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/yellowpen2.png")));
				yellowPen.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				yellowPen.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/yellowpen.png")));
				yellowPen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
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
		eraser.setBorderPainted(false);
		eraser.setContentAreaFilled(false);
		eraser.setFocusPainted(false);
//		eraser.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// 색깔 바꾸기
//				// gc2.setColor(Color.RED);
//				ChatMsg msg = new ChatMsg(UserName, "600", "eraser");
//				SendObject(msg);
//
//			}
//		});
		eraser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				eraser.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/eraser2.png")));
				eraser.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				eraser.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/eraser.png")));
				eraser.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
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
		trashcan.setBorderPainted(false);
		trashcan.setContentAreaFilled(false);
		trashcan.setFocusPainted(false);
//		trashcan.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// 색깔 바꾸기
//				// gc2.setColor(Color.RED);
//				ChatMsg msg = new ChatMsg(UserName, "600", "init");
//				SendObject(msg);
//
//			}
//		});
		trashcan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				trashcan.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/trashcan2.png")));
				trashcan.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				trashcan.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/trashcan.png")));
				trashcan.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
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
		rectangle.setBorderPainted(false);
		rectangle.setContentAreaFilled(false);
		rectangle.setFocusPainted(false);

		rectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

//				ChatMsg msg = new ChatMsg(UserName, "600", "init");
//				SendObject(msg);
				IsRect = true;// 여기서 모든 사람한테 isrect true 인 것을 보내줘야 한다 메시지로 .
				// ChatMsg msg = new ChatMsg(UserName, "600", "init");
//				SendObject(msg);

			}
		});
//		StartButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				StartButton.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/startbutton_hover.png")));
//				StartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//
//				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
//				// false �� ������ �ѹ��� ����ǰ��Ϸ���
//				// buttonEnteredMusic.start();
//
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				StartButton.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/startbutton.png")));
//				StartButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				if (boss == true) {
//					answerView.setVisible(true);
//					PassButton.setVisible(true);
//				}
//				// quitButton.setEnabled(false);
//
//				// 게임시작
//				ChatMsg cm2 = new ChatMsg(UserName, "901", "게임을 시작하겠습니다!!!");
//				SendObject(cm2);
//
//				ChatMsg cm = new ChatMsg(UserName, "900", "퀴즈출제");
//
//				cm.setAnswer(quiz());
//				SendObject(cm);
//				StartButton.setEnabled(false);
//				StartButton.setVisible(false);
//			}
//		});
		// contentPane.add(btnNewButton4);
		// btnNewButton4.setVisible(true);
		rectangle.setVisible(false);
		getContentPane().add(rectangle);

		circle.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/circle.png")));

		// 원형 변환 버튼

		circle.setFont(new Font("휴먼편지체", Font.PLAIN, 7));
		circle.setBounds(798, 255, 40, 40);
		circle.setBorderPainted(false);
		circle.setContentAreaFilled(false);
		circle.setFocusPainted(false);
		circle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				circle.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/circle2.png")));
				circle.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				circle.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/circle.png")));
				circle.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				IsCircle = true;
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
		triangle.setBorderPainted(false);
		triangle.setContentAreaFilled(false);
		triangle.setFocusPainted(false);

		triangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				triangle.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/triangle2.png")));
				triangle.setCursor(new Cursor(Cursor.HAND_CURSOR));//
				// Music buttonEnteredMusic = new Music ("buttonEnteredMusic.mp3",false);//
				// false �� ������ �ѹ��� ����ǰ��Ϸ���
				// buttonEnteredMusic.start();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				triangle.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/triangle.png")));
				triangle.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}
		});
		// contentPane.add(btnNewButton4);
		// btnNewButton4.setVisible(true);
		triangle.setVisible(false);
		getContentPane().add(triangle);

		// -----------------------색깔 바꾸기 버튼 끝

		// -----------------------답 보여주는 메모지 창 ---
		answerView = new JLabel("문제창");
		// answerView.setBorder(new LineBorder(new Color(0, 0, 0)));
		// lblUserName.setBackground(Color.WHITE);
		answerView.setFont(new Font("휴먼편지체", Font.BOLD, 30));
		answerView.setHorizontalAlignment(SwingConstants.CENTER);
		answerView.setBounds(973, 110, 210, 50);
		// contentPane.add(lblUserName);
		answerView.setVisible(false);
		getContentPane().add(answerView);

		// setVisible(false);
		//

		// -----------------------답 보여주는 메모지 창 ---

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(164, 100, 600, 380);
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

		// 게임 배경 이미지
		// GameBackground.setIcon(new
		// ImageIcon(JavaGameClientView.class.getResource("/images/a2.jpg")));
		GameBackground.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/backgroundgameroom.png")));
		GameBackground.setBounds(0, 0, 1280, 720);
		GameBackground.setVisible(true);
		getContentPane().add(GameBackground);

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

	// --------퀴즈 내는 함수

	public String quiz() {
		int index = (int) (Math.random() * quiz.size());

		if (quiz.size() != 0) {

			answer = quiz.get(index);

			return answer;

		} else {
			ChatMsg msg = new ChatMsg(UserName, "902", "게임 종료!!!!!!");
			SendObject(msg);
			PassButton.setEnabled(false);
			StartButton.setEnabled(true);
			quitButton.setEnabled(true);
			StartButton.setVisible(true);
			PassButton.setVisible(false);

			return "문제 없음";
		}

	}
	// --------퀴즈 내는 함수

	// 게임 창 으로 들어가기
	public void goGame() {

		IsGameStep = true;
		for (int i = 0; i < 4; i++)
			Room[i].setVisible(false);

		GameBackground.setIcon(new ImageIcon(JavaGameClientView.class.getResource("/images/backgroundgame.png")));

		panel.setVisible(true); // 게임판 생성
		lblUserName.setVisible(true);
		// 지역변수로 하면 여기서 못써서 위에서 전역변수로 생성 하는거네 아....
		lobyInfo.setVisible(true);
		lobyInfototal.setVisible(true);
		Roomnum.setVisible(true); // 방번호
		blackPen.setVisible(true);

		btnNewButton2.setVisible(true);

		eraser.setVisible(true);
		eraser.revalidate();

		bluePen.setVisible(true);
		yellowPen.setVisible(true);
		trashcan.setVisible(true);
		rectangle.setVisible(true);
		circle.setVisible(true);
		triangle.setVisible(true);
		greenPen.setVisible(true);
		if (boss == true) {
			StartButton.setVisible(true);
			myInfo2.setVisible(true);
			JOptionPane.showMessageDialog(null, UserName + "님이 방장입니다!!!!");
		}

		// PassButton.setVisible(true);

//		if(boss == true && IsGameing == true ) {
//			
//			System.out.print("하우링ㄴㄹㅇㄴ");
//			PassButton.setVisible(true);
//			answerView.setVisible(true);
//		}
//		

		// myInfo2.setVisible(true);

	}

	public void refreshInfo() {
		if (IsGameStep == true) {
			SendInfoReq("/lc");
		}

	}

	public void paint(Graphics g) {

		super.paint(g);
		paintComponents(g);

		// g.drawImage(GameBackgroundImage, 0, 0, 1280,720, null);
		// Image 영역이 가려졌다 다시 나타날 때 그려준다.
		// gc.drawImage(panelImage, 0, 0, this);
		// gui ,, jframe에서 가장 먼저 그려주는 함수
//      super.paint(g);

		// paintComponents(g);
		// g.drawImage(GameBackgroundImage, 0, 0, null);

		// Image 영역이 가려졌다 다시 나타날 때 그려준다.
		// screenImage = createImage(1280, 720); // 판생성
		// screenGraphic = screenImage.getGraphics(); // 판에 그래픽객체 얻어오기
		// screenDraw(screenGraphic); // 스크린 그래픽에 어떠한 그림을 그려준다.
//      if(!check)
		// g.drawImage(GameBackgroundImage,0,0,null);
//       if(!check)
		// paintComponents(g);
		// g.drawImage(GameBackgroundImage, 0, 0, null);

		// paintComponents(g);

	}

	public void screenDraw(Graphics g) {

		// paintComponents(g);
		// g.drawImage(GameBackgroundImage, 0, 0, null);
		// paintComponents(g);
		// this.repaint();
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
						obcm = ois.readObject(); // 서버에서 정보를 읽어온다.
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
					// 이부분을 고쳐야 한다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 말풍선
					case "200": // chat message
						if (cm.UserName.equals(UserName)) {
							AppendTextR(msg); // 내 메세지는 우측에
							AppendTextTest(msg);
						} else
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
					case "501": // Mouse Event 수신 //사각형 그리기

						DoMouseEventRect(cm);
						break;
					case "502": // Mouse Event 수신 // 원그리기

						DoMouseEventCircle(cm);
						break;

					case "600": // 마우스 색깔 바꿈
						// DoMouseEvent(cm);
						switch (cm.data) {
						case "black":
							gc2.setColor(Color.black);
							break;
						case "red":
							gc2.setColor(Color.RED);
							break;
						case "green":
							gc2.setColor(Color.green);
							break;
						case "blue":
							gc2.setColor(Color.blue);
							break;
						case "yellow":
							gc2.setColor(Color.yellow);
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
						// ---------------------switch문 끝

						break;
					case "700":
						String[] datas = cm.data.split(":");
						lobyInfototal.setText(datas[0]);
						if (datas[0].equals("5")) {
							System.out.print("종료");
							ChatMsg msg2 = new ChatMsg(UserName, "400", "Bye");
							SendObject(msg2);
							System.exit(0);
							
						}
						String members = "";
						for (int i = 0; i < Integer.parseInt(datas[0]); i++)
							members = members + datas[i + 1] + "               ";
						lobyInfo.setText(members);
						break;

					case "800":
						refreshInfo();
					case "900":
						answer = cm.getAnswer();
						quiz.remove(answer);
						answerView.setText(cm.getAnswer());
						break;
					case "901": // 게임 시작 알림 창
						// 만약 게임이 실행중이지 않다면
						if (IsGameing == false) {
							JOptionPane.showMessageDialog(null, cm.data);
						} // 게임시작 진행 알림
						IsGameing = true; // 모두의 게임 진행상황 true 로 바꾸기
						quitButton.setEnabled(false); // 게임이 시작되면 나가기 버튼 못하게

						break;

					case "902": // 게임 종료 알림창
						// 게임 종료!
						if (IsGameing == true) {
							JOptionPane.showMessageDialog(null, cm.data);
						} // 게임종료
						IsGameing = false; // 모두의 게임 진행상황 true 로 바꾸기
						quitButton.setEnabled(true);
						break;

					case "903": // 정답 알림창
						// 게임 종료!
						JOptionPane.showMessageDialog(null, cm.UserName + "님이 정답을 맞추셨습니다!!!!" + "정답:" + cm.data);

						break;
					case "904": // 방장으로 만듬
						// 게임 종료!
						System.out.println("게임클라에서 보스메시지 받음");
						// JOptionPane.showMessageDialog(null,UserName+"님이 방장입니다!!!!");
						boss = cm.getBoss();

						break;
					} // ----------cm.code의 끝

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

	// Mouse Event 수신 처리 /// 마우스 이벤트 를 서버로 부터 받아와서 수행하는 코드
	public void DoMouseEvent(ChatMsg cm) {
		Color c;
//		if (cm.UserName.matches(UserName)) // 본인 것은 이미 Local 로 그렸다. 수신한 걸 무시함
//			return;

		pen_size = cm.pen_size;
		((Graphics2D) gc2).setStroke(new BasicStroke(pen_size, BasicStroke.CAP_ROUND, 0)); // 선굵기 조절
		x1 = cm.getX1();
		y1 = cm.getY1();
		x2 = cm.getX2();
		y2 = cm.getY2();

		gc2.drawLine(x1, y1, x2, y2);

		// gc2.fillOval(cm.mouse_e.getX() - pen_size / 2, cm.mouse_e.getY() -
		// cm.pen_size / 2, cm.pen_size, cm.pen_size);
		gc.drawImage(panelImage, 0, 0, panel);
	}

	// 사각형그리기
	public void DoMouseEventRect(ChatMsg cm) {

//		if (cm.UserName.matches(UserName)) // 본인 것은 이미 Local 로 그렸다. 수신한 걸 무시함
//			return;

		pen_size = cm.pen_size;
		((Graphics2D) gc2).setStroke(new BasicStroke(pen_size, BasicStroke.CAP_ROUND, 0)); // 선굵기 조절
		x1 = cm.getX1();
		y1 = cm.getY1();
		x2 = cm.getX2();
		y2 = cm.getY2();

		gc2.fillRect(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));

		// gc2.fillOval(cm.mouse_e.getX() - pen_size / 2, cm.mouse_e.getY() -
		// cm.pen_size / 2, cm.pen_size, cm.pen_size);
		gc.drawImage(panelImage, 0, 0, panel);
		IsRect = false; // 다그리고 false
	}

	// 그리기@@@@@@@@@@@@@@@@@
	public void DoMouseEventCircle(ChatMsg cm) {

//		if (cm.UserName.matches(UserName)) // 본인 것은 이미 Local 로 그렸다. 수신한 걸 무시함
//			return;

		pen_size = cm.pen_size;
		((Graphics2D) gc2).setStroke(new BasicStroke(pen_size, BasicStroke.CAP_ROUND, 0)); // 선굵기 조절
		x1 = cm.getX1();
		y1 = cm.getY1();
		x2 = cm.getX2();
		y2 = cm.getY2();

		gc2.fillOval(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));

		// gc2.fillOval(cm.mouse_e.getX() - pen_size / 2, cm.mouse_e.getY() -
		// cm.pen_size / 2, cm.pen_size, cm.pen_size);
		gc.drawImage(panelImage, 0, 0, panel);
		IsCircle = false; // 다그리고 false
	}

	// 내가 이벤트를 하고 서버로 메시지르 보낸다.
	public void SendMouseEvent(MouseEvent e) {

		if (boss && IsGameing) {
			ChatMsg cm = new ChatMsg(UserName, "500", "MOUSE");
			cm.mouse_e = e;
			cm.pen_size = pen_size;
			SendObject(cm); // 이벤트를 보낸다. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		}
	}

//	public void SendMouseEvent2(MouseEvent e) {
//
//		ChatMsg cm = new ChatMsg(UserName, "500", "MOUSE");
//		cm.mouse_e = e;
//		cm.pen_size = pen_size;
//		cm.x = 0;
//		cm.y = 0;
//		cm.x1 = 0;
//		cm.y1 = 0;
//		//SendObject(cm); // 이벤트를 보낸다. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//	}

	class MyMouseWheelEvent implements MouseWheelListener {
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {

			if (boss && IsGameing) {
				// TODO Auto-generated method stub
				if (e.getWheelRotation() < 0) { // 위로 올리는 경우 pen_size 증가
					if (pen_size < 20)
						pen_size++;
				} else {
					if (pen_size > 2)
						pen_size--;
				}

			}
		}

	}

	// Mouse Event Handler
	class MyMouseEvent implements MouseListener, MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {

			if (boss && IsGameing && IsRect == false && IsCircle == false) {

				x2 = e.getX();
				y2 = e.getY();

				ChatMsg cm = new ChatMsg(UserName, "500", "MOUSE");

				cm.setX1(z1); // 아주 짧은 선을 그릴 첫 좌표 두개
				cm.setY1(z2);
				cm.setX2(x2); // 아주 짧은 선을 그릴 두번째 좌표 두개
				cm.setY2(y2);
				cm.pen_size = pen_size;

				SendObject(cm);
				z1 = e.getX();
				z2 = e.getY();

			}

		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

			// System.out.println("안녕");

			if (IsRect == true || IsCircle == true) {

				x1 = e.getX();
				y1 = e.getY();
			} else {
				z1 = e.getX();
				z2 = e.getY();
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {

			// System.out.println("안녕2");
			if (IsRect == true) {

				x2 = e.getX();
				y2 = e.getY();
				ChatMsg cm = new ChatMsg(UserName, "501", "rect");
				cm.setX1(x1); // 아주 짧은 선을 그릴 첫 좌표 두개
				cm.setY1(y1);
				cm.setX2(x2); // 아주 짧은 선을 그릴 두번째 좌표 두개
				cm.setY2(y2);
				cm.pen_size = pen_size;

				SendObject(cm);

			}
			if (IsCircle == true) {
				x2 = e.getX();
				y2 = e.getY();
				ChatMsg cm = new ChatMsg(UserName, "502", "circle");
				cm.setX1(x1); // 아주 짧은 선을 그릴 첫 좌표 두개
				cm.setY1(y1);
				cm.setX2(x2); // 아주 짧은 선을 그릴 두번째 좌표 두개
				cm.setY2(y2);
				cm.pen_size = pen_size;

				SendObject(cm);

			}

		}
	}

	// keyboard enter key 치면 서버로 전송
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				String msg = null;
				String msg2 = null;
				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				msg = txtInput.getText();
				msg2 = msg;

				if (boss == false) {
					SendMessage(msg);

					txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
					txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
					if (IsGameing) {
						if (answer.equals(msg2)) {
							ChatMsg obcm = new ChatMsg(UserName, "903", msg2);
							SendObject(obcm);

						}
					}
				}

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

	public void AppendTextTest(String msg) {
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		// myInfo2.setText(msg);

//	      StyledDocument doc = textArea.getStyledDocument();
//	      SimpleAttributeSet right = new SimpleAttributeSet();
//	      StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
//	      StyleConstants.setForeground(right, Color.BLUE);
//	      doc.setParagraphAttributes(doc.getLength(), 1, right, false);
//	      try {
//	         doc.insertString(doc.getLength(), msg + "\n", right);
//	      } catch (BadLocationException e) {
//	         // TODO Auto-generated catch block
//	         e.printStackTrace();
//	      }
//	      int len = textArea.getDocument().getLength();
//	      textArea.setCaretPosition(len);
//	      // textArea.replaceSelection("\n");

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
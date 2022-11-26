// JavaObjClient.java
// ObjecStream 사용하는 채팅 Client

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;

public class JavaGameClientMain extends JFrame {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private JTextField txtUserName;
   private JTextField txtIpAddress;
   private JTextField txtPortNumber;
   private JLabel lblNewLabel_1;
   private JLabel lblNewLabel_2;
   private JLabel lblNewLabel_3;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               JavaGameClientMain frame = new JavaGameClientMain();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    */
   public JavaGameClientMain() {
    
      setSize(1280, 720); // 게임 화면 크기
       setLocationRelativeTo(null);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x 버튼 누르면 꺼지게
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      JLabel lblNewLabel = new JLabel("닉네임을 입력하세요 :");
      lblNewLabel.setFont(new Font("휴먼편지체", Font.PLAIN, 35));
      lblNewLabel.setBounds(330, 191, 350, 40);
      contentPane.add(lblNewLabel);
      
      txtUserName = new JTextField();
      txtUserName.setFont(new Font("휴먼편지체", Font.PLAIN, 35));
      txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
      txtUserName.setBounds(670, 188, 200, 45);
      contentPane.add(txtUserName);
      txtUserName.setColumns(10);
      
      JLabel lblIpAddress = new JLabel("IP Address :");
      lblIpAddress.setFont(new Font("휴먼편지체", Font.PLAIN, 35));
      lblIpAddress.setBounds(400, 295, 200, 40);
      contentPane.add(lblIpAddress);
      
      txtIpAddress = new JTextField();
      txtIpAddress.setFont(new Font("휴먼편지체", Font.PLAIN, 35));
      txtIpAddress.setHorizontalAlignment(SwingConstants.CENTER);
      txtIpAddress.setText("127.0.0.1");
      txtIpAddress.setColumns(10);
      txtIpAddress.setBounds(670, 292, 200, 45);
      contentPane.add(txtIpAddress);
      
      JLabel lblPortNumber = new JLabel("Port Number :");
      lblPortNumber.setFont(new Font("휴먼편지체", Font.PLAIN, 35));
      lblPortNumber.setBounds(385, 407, 250, 40);
      contentPane.add(lblPortNumber);
      
      txtPortNumber = new JTextField();
      txtPortNumber.setFont(new Font("휴먼편지체", Font.PLAIN, 35));
      txtPortNumber.setText("30000");
      txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
      txtPortNumber.setColumns(10);
      txtPortNumber.setBounds(670, 404, 200, 45);
      contentPane.add(txtPortNumber);
      
      JButton btnConnect = new JButton("접속");
      btnConnect.setFont(new Font("휴먼편지체", Font.PLAIN, 50));
      btnConnect.setBounds(565, 522, 150, 80);
      contentPane.add(btnConnect);
      
      lblNewLabel_1 = new JLabel("");
      lblNewLabel_1.setIcon(new ImageIcon(JavaGameClientMain.class.getResource("/images/title.png")));
      lblNewLabel_1.setBounds(395, 35, 490, 80);
      contentPane.add(lblNewLabel_1);
      
      lblNewLabel_2 = new JLabel("");
      lblNewLabel_2.setIcon(new ImageIcon(JavaGameClientMain.class.getResource("/images/quill-pen (1).png")));
      lblNewLabel_2.setBounds(927, 81, 263, 521);
      contentPane.add(lblNewLabel_2);
      
      lblNewLabel_3 = new JLabel("New label");
      lblNewLabel_3.setIcon(new ImageIcon(JavaGameClientMain.class.getResource("images/backgroundimage.png")));
      //lblNewLabel_3.setIcon(new ImageIcon(JavaGameClientMain.class.getResource("images/h2.jpg")));
      lblNewLabel_3.setBounds(0, 0, 1280, 720);
      contentPane.add(lblNewLabel_3);
      Myaction action = new Myaction();
      btnConnect.addActionListener(action);
      txtUserName.addActionListener(action);
      txtIpAddress.addActionListener(action);
      txtPortNumber.addActionListener(action);
   }
   class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
   {
      @Override
      public void actionPerformed(ActionEvent e) {
         String username = txtUserName.getText().trim();
         String ip_addr = txtIpAddress.getText().trim();
         String port_no = txtPortNumber.getText().trim();
         JavaGameClientView view = new JavaGameClientView(username, ip_addr, port_no);
         setVisible(false);
      }
   }
}


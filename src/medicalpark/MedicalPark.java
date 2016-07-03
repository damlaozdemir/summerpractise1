package medicalpark;

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class MedicalPark
{
    JFrame f = new JFrame("MedicalPark");
    JPanel girisPaneli = new JPanel(new GridBagLayout());
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    JTextField t1 = new JTextField(10);
    JPasswordField t2 = new JPasswordField(10); 
    
    static JTable table;
    
    public void basla()
    {
        f.setSize(450,150);
        girisPaneli.setBackground(Color.WHITE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
        JLabel lb = new JLabel(new ImageIcon("C:\\Users\\damla.ozdemir\\Documents\\NetBeansProjects\\MedicalPark\\medicalpark.jpg"));
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.insets = new Insets(2,5,2,2); 
        c.gridx = 0; 
        c.gridy = 0;
        girisPaneli.add(lb,c);
        JButton button1 = new JButton("Üye Ol");
        JButton button2= new JButton("Giriş Yap");
        c.gridx = 1; 
        c.gridy = 0;
        girisPaneli.add(button1,c);
        c.gridx = 2; 
        c.gridy = 0;
            
        girisPaneli.add(button2,c);
        f.add(girisPaneli);
        f.repaint();
        
        button1.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    uyeOl();
                }
                catch(Exception ex)
                {
                    System.out.println(ex);
                }
            }
        });
        
        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                girisYap();
                }
                catch(Exception ex)
                {
                    System.out.println(ex);
                }
            }
        }); 

        try
        {
           Class.forName("com.mysql.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicalpark","root","984540");
           st = con.createStatement();
        }
        catch(ClassNotFoundException | SQLException ex)
        {
           System.out.println("Error:" + ex);
        }
    }
    
    JComboBox Box4 = new JComboBox(new String[]{"","Adana","Adıyaman","Afyonkarahisar","Ağrı","Aksaray","Amasya","Ankara",
            "Antalya","Ardahan","Artvin","Aydın","Balıkesir","Bartın","Batman","Bayburt","Bilecik","Bingöl","Bitlis","Bolu",
            "Burdur","Bursa","Çanakkale","Çankırı","Çorum","Denizli","Diyarbakır","Düzce","Edirne","Elazığ","Erzincan",
            "Erzurum","Eskişehir","Gaziantep","Giresun","Gümüşhane","Hakkâri","Hatay","Iğdır","Isparta",
            "İzmir","İstanbul","Kahramanmaraş","Karabük","Karaman","Kars","Kastamonu","Kayseri","Kırıkkale","Kırklareli","Kırşehir","Kilis","Kocaeli","Konya","Kütahya","Malatya",
            "Manisa","Mardin","Mersin","Muğla","Muş","Nevşehir","Niğde","Ordu","Osmaniye","Rize",
            "Sakarya","Samsun","Siirt","Sinop","Sivas","Şırnak","Tekirdağ","Tokat","Trabzon","Tunceli","Şanlıurfa","Uşak","Van",
            "Yalova","Yozgat","Zonguldak"});
    
    public void uyeOl()
    {
        f.setVisible(false);
        
        final JTextField field1 = new JTextField(10);
        final JTextField field2 = new JTextField(10);
        final JTextField field3 = new JTextField(10);
        final JTextField field4 = new JTextField(10);
        final JPasswordField field5 = new JPasswordField(10);
        final JTextField field6 = new JTextField(10);
                
        field1.addKeyListener(new KeyAdapter()
        {   
            @Override
            public void keyTyped(KeyEvent e)
            {
                char c = e.getKeyChar();
                ArrayList<String> chars = new ArrayList<>(Arrays.asList(" ","A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z",
                "a", "b", "c", "ç", "d", "e", "f", "g", "ğ", "h", "ı", "i", "j", "k", "l", "m", "n", "o", "ö", "p", "r", "s", "ş", "t", "u", "ü", "v", "y", "z"));
                
                if (!chars.contains(String.valueOf(c)) || field1.getText().length() > 30 ) 
                {
                    Toolkit.getDefaultToolkit().beep(); 
                    e.consume();  
                }
            }
        });

        field2.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                char c = e.getKeyChar();
                ArrayList<String> chars = new ArrayList<>(Arrays.asList(" ","A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z",
                "a", "b", "c", "ç", "d", "e", "f", "g", "ğ", "h", "ı", "i", "j", "k", "l", "m", "n", "o", "ö", "p", "r", "s", "ş", "t", "u", "ü", "v", "y", "z"));
                
                if (!chars.contains(String.valueOf(c)) || field2.getText().length() > 30 ) 
                {
                    Toolkit.getDefaultToolkit().beep(); 
                    e.consume();  
                }
            }
        });

        field3.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(field3.getText().length() > 20)
                {
                    e.consume();
                }
            }
        });  

        field4.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(field4.getText().length() > 20)
                {
                    e.consume();
                }
            }
        });  

        field5.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(field5.getPassword().length > 20)
                {
                    e.consume();
                }
            }
        });  

        field6.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(field6.getText().length() > 12) 
                {
                    Toolkit.getDefaultToolkit().beep(); 
                    e.consume();  
                }
            }
        });  
                
        final JFrame frame2 = new JFrame("MedicalPark");
        frame2.setBounds(0,0,350,550);
        frame2.setResizable(false);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        
        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        jp.setBackground(Color.RED);
        
        JLabel lb = new JLabel("ÜYE BİLGİLERİ",JLabel.CENTER);
        lb.setForeground(Color.WHITE);
        lb.setFont(new Font("Serif",Font.BOLD ,15));
        jp.add(lb);
        frame2.add(jp);
                
        JButton bt1 = new JButton();   
        bt1.setText("Kaydet");
        JButton bt2 = new JButton(); 
        bt2.setText("İptal");
                
        JPanel panel1 = new JPanel(new GridLayout(0, 1));
        panel1.setLayout(new GridBagLayout());
        panel1.setBackground(Color.WHITE);

        try
        {
            GridBagConstraints c = new GridBagConstraints();
        
            c.insets = new Insets(10, 10, 5, 5);
            c.gridx = 0; 
            c.gridy = 0;
            
            JLabel ladı = new JLabel("Adı                     ");
            panel1.add(ladı,c);
            
            
            c.gridx = 1; 
            c.gridy = 0;
            panel1.add(field1,c);

            c.gridx = 0; 
            c.gridy = 1;
            panel1.add(new JLabel("Soyadı                "),c);
            
            c.gridx = 1; 
            c.gridy = 1;
            panel1.add(field2,c);

            c.gridx = 0; 
            c.gridy = 2;
            panel1.add(new JLabel("E-Posta Adresi"),c);
            JLabel ornLabel1 = new JLabel("(Orn: birisi@gmail.com)");
            ornLabel1.setFont(new Font("Serif", Font.PLAIN, 11));
            
            c.gridx = 0; 
            c.gridy = 3;
            panel1.add(ornLabel1,c);
            
            c.gridx = 1; 
            c.gridy = 2;
            panel1.add(field3,c);

            c.gridx = 0; 
            c.gridy = 4;
            panel1.add(new JLabel("Kullanıcı Adı      "),c);
            
            c.gridx = 1; 
            c.gridy = 4;
            panel1.add(field4,c);

            c.gridx = 0; 
            c.gridy = 5;
            panel1.add(new JLabel("Şifre                     "),c);
            
            c.gridx = 1; 
            c.gridy = 5;
            panel1.add(field5,c);

            c.gridx = 0; 
            c.gridy = 6;
            panel1.add(new JLabel("Doğum Tarihi    "),c);
            
            UtilDateModel model = new UtilDateModel();
            
            JDatePanelImpl datePanel = new JDatePanelImpl(model);
            final JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
            datePicker.setPreferredSize(new Dimension(115, 20));
            
            c.gridx = 1; 
            c.gridy = 6;
            panel1.add(datePicker,c);

            final JComboBox Box5 = new JComboBox(new String[]{"","Kadın","Erkek"});
            Box5.setPreferredSize(new Dimension(115, 20));
            Box5.setBackground(Color.WHITE);
            c.gridx = 0; 
            c.gridy = 7;
            panel1.add(new JLabel("Cinsiyet             "),c);
            
            c.gridx = 1; 
            c.gridy = 7;
            panel1.add(Box5,c);

            c.gridx = 0; 
            c.gridy = 8;
            panel1.add(new JLabel("Telefon             "),c);
            
            JLabel ornLabel2 = new JLabel("(Orn:+90*********)");
            ornLabel2.setFont(new Font("Serif", Font.PLAIN, 11));
            c.gridx = 0; 
            c.gridy = 9;
            panel1.add(ornLabel2,c);
            
            c.gridx = 1; 
            c.gridy = 8;
            panel1.add(field6,c);

            c.gridx = 0; 
            c.gridy = 10;
            panel1.add(new JLabel("Şehir                 "),c);
            
            c.gridx = 1; 
            c.gridy = 10;
            Box4.setPreferredSize(new Dimension(115, 20));
            Box4.setBackground(Color.WHITE);
            panel1.add(Box4,c);
            
            c.gridx = 0; 
            c.gridy = 12;
            panel1.add(new JLabel("Güvenlik Kodu"),c);
        
            final String secure = getRandomPassword(8);

            final JLabel jl = new JLabel("  " + secure);
            jl.setFont(new Font("Papyrus", Font.HANGING_BASELINE + Font.ITALIC ,15));
            jl.setForeground(Color.RED);

            jl.setPreferredSize(new Dimension(75, 30));
            jl.setBackground(Color.ORANGE);
            jl.setOpaque(true);
            c.gridx = 1; 
            c.gridy = 11;
            panel1.add(jl,c);

            final JTextField t3 = new JTextField(10);
            c.gridx = 1; 
            c.gridy = 12;
            panel1.add(t3,c);

            c.gridx = 0; 
            c.gridy = 13;
            panel1.add(bt1,c);
            
            c.gridx = 1; 
            c.gridy = 13;
            panel1.add(bt2,c);
            
            frame2.add(panel1, BorderLayout.PAGE_END);
            
            
            t3.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    if(t3.getText().length() > 7)
                    {
                        e.consume();
                    }
                }
            }); 
            
            bt1.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                { 
                    try
                    {
                        String ad = field1.getText();
                        String soyad = field2.getText();
                        String posta = field3.getText();
                        String kullanıcıAdı = field4.getText();
                        String sifre = new String(field5.getPassword());
                        String tel = field6.getText();
                        String cins = Box5.getSelectedItem().toString();
                        String sehir = (String)Box4.getSelectedItem();
                        Date date = (Date) datePicker.getModel().getValue();
                        String isValEmail = validateEmailAddress(posta);
                        String isValPhone = validateMobileNumber(tel);
                        String ans = t3.getText();
                                
                        if(ad.equals("") || soyad.equals("") || posta.equals("") || kullanıcıAdı.equals("") || sifre.equals("") || tel.equals("") || date == null || sehir.equals("") ||cins.equals("")|| ans.equals(""))
                        {   
                            JOptionPane.showMessageDialog(null, "Eksik Alan Bıraktınız!");
                        } 
                        else
                        {
                            String query = "select count(username) as adet from users where username = '"+kullanıcıAdı +"'";
                            rs = st.executeQuery(query);

                            while(rs.next())
                            {   
                                int kul_sayi = rs.getInt("adet");
                                if(kul_sayi >= 1)
                                {
                                    JOptionPane.showMessageDialog(null,"Bu kullanıcı ismi daha önce alındı.");
                                }  
                                else
                                {
                                    if(isValEmail.equalsIgnoreCase("Valid"))
                                    {
                                        if(isValPhone.equals("Valid")) 
                                        {   
                                            if(ans.equals(secure))
                                            {
                                                String q = "Insert Into users(name,surname,mail,username,password,birthdate,sex,city,phone) Values('"+ad+"','"+soyad+"','"+posta+"','"+kullanıcıAdı+"','"+sifre+"','"+date+"','"+cins+"','"+sehir+"','"+tel+"')";
                                                st.executeUpdate(q); 
                                                JOptionPane.showMessageDialog(null,"Üyelik İşleminiz Tamamlandı!");
                                                frame2.dispose();
                                                girisYap();
                                                String compname=Inet4Address.getLocalHost().getHostName();
                                                String ipAdress = Inet4Address.getLocalHost().getHostAddress();

                                                Calendar cal = Calendar.getInstance();
                                                int year = cal.get(Calendar.YEAR);
                                                int month = cal.get(Calendar.MONTH); 
                                                int month2 = month + 1;
                                                int day= cal.get(Calendar.DAY_OF_MONTH); 
                                                String thatDate = day + "-" + month2 + "-" + year;

                                                cal.getTime();
                                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                                String time = sdf.format(cal.getTime());
                                                String allTime = thatDate + " " + time;

                                                String qe = "Select id from users where username = '"+ kullanıcıAdı + "'";
                                                rs = st.executeQuery(qe);
                                                while(rs.next())
                                                {
                                                    int id = rs.getInt("id");
                                                    String qe2 = "Insert Into usersignup(User_id, Signup_time,compName, ip_number) Values ('"+id+"', '"+allTime+"','"+compname+"','"+ipAdress+"')";
                                                    st.executeUpdate(qe2);
                                                }  
                                            }
                                            else
                                            {
                                                JOptionPane.showMessageDialog(null,"Güvenlik kodu yanlış girildi!");
                                                
                                            }
                                        }
                                        else
                                        {
                                        JOptionPane.showMessageDialog(null,"Geçersiz Telefon Numarası!");    
                                        }
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null,"Geçersiz E Posta Adresi!");
                                    }
                                }
                            }    
                        }
                    }
                    catch(HeadlessException | SQLException | UnknownHostException ex2)
                    {
                        System.out.println("Error " + ex2);
                    }
                    
                }
            });

            bt2.addActionListener(new ActionListener()
            {   
                @Override
                public void actionPerformed(ActionEvent e)
                { 
                    frame2.dispose();
                    JOptionPane.showMessageDialog(null, "Üyelik İşleminiz iptal edildi!");
                    basla();
                }
            });                

        }
        catch(Exception ex)
        {
            System.out.println("Error: " + ex);
        }
    }
    
    public String getRandomPassword(int length) 
    {
        String charset = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            int pos = rand.nextInt(charset.length());
            sb.append(charset.charAt(pos));
        }
        return sb.toString();
    }
    
    public void girisYap()
    {
        f.setVisible(false);
        
        final JFrame jf = new JFrame("Üye Girişi");
        jf.setSize(300,200);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        
        JPanel panel3 = new JPanel(new GridLayout(0, 1));
        panel3.setLayout(new GridBagLayout());
        panel3.setBackground(Color.WHITE);
        
        jf.add(panel3);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 0; 
        c.gridy = 0;
        panel3.add(new JLabel("Kullanıcı Adı "),c);
        
        c.gridx = 1; 
        c.gridy = 0;
        panel3.add(t1,c);
        
        t1.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(t1.getText().length() > 30)
                {
                    Toolkit.getDefaultToolkit().beep(); 
                    e.consume();
                }
            }
        });  
        
        c.gridx = 0; 
        c.gridy = 1;
        panel3.add(new JLabel("Şifre                "),c);
        
        c.gridx = 1; 
        c.gridy = 1;
        panel3.add(t2,c);
        
        t2.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(t2.getPassword().length > 20)
                {
                    Toolkit.getDefaultToolkit().beep(); 
                    e.consume();
                }
            }
        });  
        
        JButton giris = new JButton("Giriş");
        c.gridx = 0; 
        c.gridy = 3;
        panel3.add(giris,c);
        
        JButton iptal = new JButton("İptal");
        c.gridx = 1; 
        c.gridy = 3;
        panel3.add(iptal,c);
        
        giris.addActionListener(new ActionListener()
        { 
            @Override
            public void actionPerformed(ActionEvent e)
            { 
                try
                {
                    jf.dispose();
                    String user = t1.getText();
                    String pass = new String(t2.getPassword());
                    
                    String query = "select username, count(distinct concat(username, password)) as up, isAdmin from users where username = '"+user+"' and password = '"+pass+"'";    
                    rs = st.executeQuery(query);

                    while(rs.next())
                    {
                        int kullanici = rs.getInt("up");
                        String usertype = rs.getString("isAdmin");
                        String usern = rs.getString("username");

                        if(kullanici == 1)
                        {
                            if(usertype.equals("Admin") || usern.equals("dozdemir93"))
                                    {
                                        getUsersInfo();
                                    }
                                    else
                                    {
                                        getSpecUserInfo();
                                    }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Kullanıcı bulunamadı!");
                        } 
                    }
                }
                catch(SQLException | HeadlessException ex)
                {
                    System.out.println(ex);
                }
            }
        }); 
        
        iptal.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            { 
                jf.dispose();
            }
        });   
    }
    
    private Pattern regexPattern;
    private Matcher regMatcher;

    public String validateEmailAddress(String emailAddress)
    {
        regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        regMatcher   = regexPattern.matcher(emailAddress);
        if(regMatcher.matches())
        {
            return "Valid";
        } 
        else 
        {
            return "Invalid";
        }
    }
    
    public String validateMobileNumber(String mobileNumber)
    {
        regexPattern = Pattern.compile("^\\+[0-9]{2,3}+[0-9]{9}$");
        regMatcher   = regexPattern.matcher(mobileNumber);
        if(regMatcher.matches())
        {
            return "Valid";
        } 
        else
        {
            return "Invalid";
        }
    }
    
    public void getUsersInfo()
    {
        try 
        {   
            final JFrame frame = new JFrame("Üye Bilgileri");
            frame.setBounds(0,0,800,700);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            
            String totalNum = "select count(id) as total from users";
            rs = st.executeQuery(totalNum);
            
            while(rs.next())
            {   
                int t = rs.getInt("total");
                table = new JTable(t,14);
                table.setBounds(t,14,100,100);
                
                String tümBilgiler = "select * from users, usersignup where id = User_id ";
                  
                rs= st.executeQuery(tümBilgiler);
                int row = 0;
                while(rs.next())
                { 
                    table.setValueAt(rs.getString("id"),row,0);
                    table.setValueAt(rs.getString("name"),row,1);
                    table.setValueAt(rs.getString("surname"),row,2);
                    table.setValueAt(rs.getString("mail"),row,3);
                    table.setValueAt(rs.getString("username"),row,4);
                    table.setValueAt(rs.getString("password"),row,5);
                    table.setValueAt(rs.getString("birthdate"),row,6);
                    table.setValueAt(rs.getString("sex"),row,7);
                    table.setValueAt(rs.getString("city"),row,8);
                    table.setValueAt(rs.getString("phone"),row,9);
                    table.setValueAt(rs.getString("isAdmin"),row,10);
                    table.setValueAt(rs.getString("Signup_time"),row,11);
                    table.setValueAt(rs.getString("compName"),row,12);
                    table.setValueAt(rs.getString("ip_number"),row,13);
                    row++;
                }
                
                JPanel p = new JPanel();
                p.setLayout(new GridBagLayout());
                p.setBackground(Color.LIGHT_GRAY);
                frame.add(p, BorderLayout.PAGE_START);
                
                table.setPreferredScrollableViewportSize(new Dimension(775,400));
                table.setFillsViewportHeight(true);
                table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("ID");  
                table.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Adı");  
                table.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Soyadı");  
                table.getTableHeader().getColumnModel().getColumn(3).setHeaderValue("E Posta");  
                table.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Kullanıcı Adı");  
                table.getTableHeader().getColumnModel().getColumn(5).setHeaderValue("Şifre");  
                table.getTableHeader().getColumnModel().getColumn(6).setHeaderValue("Doğum Tarihi");  
                table.getTableHeader().getColumnModel().getColumn(7).setHeaderValue("Cinsiyet");  
                table.getTableHeader().getColumnModel().getColumn(8).setHeaderValue("Şehir");  
                table.getTableHeader().getColumnModel().getColumn(9).setHeaderValue("Telefon"); 
                table.getTableHeader().getColumnModel().getColumn(10).setHeaderValue("isAdmin"); 
                table.getTableHeader().getColumnModel().getColumn(11).setHeaderValue("Kayıt Tarihi"); 
                table.getTableHeader().getColumnModel().getColumn(12).setHeaderValue("Bilgisayar Adı"); 
                table.getTableHeader().getColumnModel().getColumn(13).setHeaderValue("IP Numarası"); 
                
                p.add(table);
                JScrollPane vertical = new JScrollPane(table);
                vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                vertical.setVisible(true);
                p.add(vertical);
                frame.add(p, BorderLayout.NORTH);
                
            }
                
                JPanel jp = new JPanel();
                jp.setLayout(new GridBagLayout());
                jp.setBackground(Color.LIGHT_GRAY);
                frame.add(jp, BorderLayout.SOUTH);
                
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(2, 2, 2, 2);
                c.gridx = 1; 
                c.gridy = 0;
                JLabel l1 = new JLabel("ID                  ");
                jp.add(l1,c);
                
               
                c.gridx = 2; 
                c.gridy = 0;
                final JTextField fid = new JTextField(10);
                jp.add(fid,c);
                
                c.gridx = 1; 
                c.gridy = 1;
                JLabel l2 = new JLabel("Adı                 ");
                jp.add(l2,c);
                
                c.gridx = 2; 
                c.gridy = 1;
                final JTextField fAdı = new JTextField(10);
                jp.add(fAdı,c);
                
                c.gridx = 1; 
                c.gridy = 2;
                JLabel l3= new JLabel("Soyadı           ");
                jp.add(l3,c);
                
                c.gridx = 2; 
                c.gridy = 2;
                final JTextField fSoyadı= new JTextField(10);
                jp.add(fSoyadı,c);
                
                c.gridx = 1; 
                c.gridy = 3;
                JLabel l4 = new JLabel("E-Posta         ");
                jp.add(l4,c);
                
                c.gridx = 2; 
                c.gridy = 3;
                final JTextField fposta = new JTextField(10);
                jp.add(fposta,c);
                
                c.gridx = 1; 
                c.gridy = 4;
                JLabel l5 = new JLabel("Telefon          ");
                jp.add(l5,c);
                
                c.gridx = 2; 
                c.gridy = 4;
                final JTextField ftel = new JTextField(10);
                
                jp.add(ftel,c);
                
                c.gridx = 1; 
                c.gridy = 5;
                JLabel l6 = new JLabel("Şehir               ");
                jp.add(l6,c);
                
                c.gridx = 2; 
                c.gridy = 5;
                final JTextField fsehir = new JTextField(10);
                jp.add(fsehir,c);
                
                c.gridx = 1; 
                c.gridy = 6;
                jp.add(new JLabel("Kullanıcı Tipi"),c);
                
                c.gridx = 2; 
                c.gridy = 6;
                final JComboBox adminBox = new JComboBox(new String[]{"","Admin","User"});
                adminBox.setPreferredSize(new Dimension(115, 20));
                adminBox.setBackground(Color.WHITE);
                jp.add(adminBox,c);
                
                c.gridx = 1; 
                c.gridy = 7;
                jp.add(new JLabel("İşlemler        "),c);
                
                c.gridx = 2; 
                c.gridy = 7;
                final JComboBox cb = new JComboBox(new String[]{"","Hepsi","Silinen","Güncellenen"});
                cb.setPreferredSize(new Dimension(115, 20));
                cb.setBackground(Color.WHITE);
                jp.add(cb,c);
                
                c.gridx = 0; 
                c.gridy = 8;
                JButton byenile = new JButton("Güncelle");
                byenile.setPreferredSize(new Dimension(120, 25));
                jp.add(byenile,c);
                
                c.gridx = 1; 
                c.gridy = 8;
                JButton del = new JButton("Sil");
                del.setPreferredSize(new Dimension(120, 25));
                jp.add(del,c);
                
                c.gridx = 2; 
                c.gridy = 8;
                JButton ex = new JButton("Excel'e Aktar");
                ex.setPreferredSize(new Dimension(120, 25));
                jp.add(ex,c);
               
                c.gridx = 3; 
                c.gridy = 8;
                JButton kayıtbutton = new JButton("Kaydet");
                kayıtbutton.setPreferredSize(new Dimension(120, 25));
                jp.add(kayıtbutton,c);
                frame.add(jp);
                
                table.addMouseListener(new java.awt.event.MouseAdapter() 
                {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    try
                    {
                        int row = table.getSelectedRow();
                        String tableClick = (table.getModel().getValueAt(row, 0).toString());
                        String sql = "Select * from users where id = '"+tableClick+"'";

                        rs = st.executeQuery(sql);
                        if(rs.next())
                        {
                            String sadd1 = rs.getString("id");
                            fid.setText(sadd1);
                            String sadd2 = rs.getString("name");
                            fAdı.setText(sadd2);
                            String sadd3= rs.getString("surname");
                            fSoyadı.setText(sadd3);
                            String sadd4 = rs.getString("mail");
                            fposta.setText(sadd4);
                            String sadd5 = rs.getString("phone");
                            ftel.setText(sadd5);
                            String sadd6 = rs.getString("city");
                            fsehir.setText(sadd6);
                            String sadd7= rs.getString("isAdmin");
                            adminBox.setSelectedItem(sadd7);
                        }
                    }
                    catch(SQLException ex)
                    {
                        System.out.println("Error :" + ex);
                    }
                }
                });
                
                cb.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    { 
                    try
                    {
                        getOptions(cb);
                        
                    }
                    catch(Exception er)
                    {
                        System.out.println(er);
                    }
                }
                });
                
                fAdı.addKeyListener(new KeyAdapter()
                {   
                    @Override
                    public void keyTyped(KeyEvent e)
                    {
                        char c = e.getKeyChar();
                        ArrayList<String> chars = new ArrayList<>(Arrays.asList(" ","A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z",
                        "a", "b", "c", "ç", "d", "e", "f", "g", "ğ", "h", "ı", "i", "j", "k", "l", "m", "n", "o", "ö", "p", "r", "s", "ş", "t", "u", "ü", "v", "y", "z"));

                        if (!chars.contains(String.valueOf(c)) || fAdı.getText().length() > 30 ) 
                        {
                            Toolkit.getDefaultToolkit().beep(); 
                            e.consume();  
                        }
                    }
                });
                
                fSoyadı.addKeyListener(new KeyAdapter()
                {   
                    @Override
                    public void keyTyped(KeyEvent e)
                    {
                        char c = e.getKeyChar();
                        ArrayList<String> chars = new ArrayList<>(Arrays.asList(" ","A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z",
                        "a", "b", "c", "ç", "d", "e", "f", "g", "ğ", "h", "ı", "i", "j", "k", "l", "m", "n", "o", "ö", "p", "r", "s", "ş", "t", "u", "ü", "v", "y", "z"));

                        if (!chars.contains(String.valueOf(c)) || fSoyadı.getText().length() > 30 ) 
                        {
                            Toolkit.getDefaultToolkit().beep(); 
                            e.consume();  
                        }
                    }
                });

                ftel.addKeyListener(new KeyAdapter()
                {
                    @Override
                    public void keyTyped(KeyEvent e)
                    {
                        if(ftel.getText().length() > 12)
                        {
                            e.consume();
                        }
                    }
                });  
                
                fsehir.addKeyListener(new KeyAdapter()
                {
                    @Override
                    public void keyTyped(KeyEvent e)
                    {
                        char c = e.getKeyChar();
                        if(!Character.isAlphabetic(c))
                        {
                            Toolkit.getDefaultToolkit().beep(); 
                            e.consume();
                        }
                        if(fsehir.getText().length() > 30)
                        {
                            e.consume();
                        }
                    }
                });
                
                byenile.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    { 
                        try
                        {
                            String id = fid.getText();
                            String adı = fAdı.getText();
                            String soyadı = fSoyadı.getText();
                            String posta = fposta.getText();
                            String tel = ftel.getText();
                            String sehir = fsehir.getText();
                            String isAdmin = adminBox.getSelectedItem().toString();
                            
                            if(id.equals("") || adı.equals("") || soyadı.equals("") || posta.equals("") || tel.equals("") || sehir.equals(""))
                            {   
                                JOptionPane.showMessageDialog(null, "Eksik Alan Bıraktınız!");
                            } 
                            else
                            {
                                try
                                {
                                    String sql = "Select * from users where id = '"+fid.getText()+"'";
                                    rs = st.executeQuery(sql);
                                    if(rs.next())
                                    {
                                        String sadd1 = rs.getString("id");
                                        String sadd2 = rs.getString("name");
                                        String sadd3= rs.getString("surname");
                                        String sadd4 = rs.getString("mail");
                                        String username = rs.getString("username");
                                        String pass = rs.getString("password");
                                        String birth = rs.getString("birthdate");
                                        String sex = rs.getString("sex");
                                        String sadd5 = rs.getString("phone");
                                        String sadd6 = rs.getString("city");
                                        String sadd7= rs.getString("isAdmin");
                                        String ipAdress = Inet4Address.getLocalHost().getHostAddress();

                                        Calendar cal = Calendar.getInstance();
                                        int year = cal.get(Calendar.YEAR);
                                        int month = cal.get(Calendar.MONTH); 
                                        int month2 = month + 1;
                                        int day= cal.get(Calendar.DAY_OF_MONTH); 
                                        String thatDate = day + "-" + month2 + "-" + year;

                                        int typeid = 2;
                                        String changedBy = t1.getText();

                                        cal.getTime();
                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                        String time = sdf.format(cal.getTime());
                                        String allTime = thatDate + " " + time;
                                        String querych = "Insert Into changes(type_id,changedBy,comp_id,date,user_id,user_name,user_surname,user_email,user_username,user_password,user_birth,user_sex,user_city,user_phone, user_isAdmin ) Values('"+typeid+"','"+changedBy+"','"+ipAdress+"','"+allTime+"','"+sadd1+"','"+sadd2+"','"+sadd3+"','"+sadd4+"','"+username+"','"+pass+"','"+birth+"','"+sex+"','"+sadd6+"','"+sadd5+"','"+sadd7+"')";
                                        st.executeUpdate(querych); 

                
                                        String isValid = "Select count(id) as num from users where id = '"+id+"'";
                                        rs = st.executeQuery(isValid);
                                        while(rs.next())
                                        {
                                            int sayi = rs.getInt("num");
                                            if(sayi == 1)
                                            {
                                                String isValEmail = validateEmailAddress(posta);
                                                String isValPhone = validateMobileNumber(tel);
                                                if(isValEmail.equalsIgnoreCase("Valid"))
                                                {
                                                    if(isValPhone.equals("Valid")) 
                                                    { 
                                                        String update = "Update users Set name = '"+adı+"', surname = '"+soyadı+"', mail = '"+posta+"', phone= '"+tel+"', city = '"+sehir+"', isAdmin = '"+isAdmin+"' where id = '"+id+"' ";
                                                        PreparedStatement statement = con.prepareStatement(update);
                                                        statement.executeUpdate();
                                                        JOptionPane.showMessageDialog(null,"Kullanıcı Güncellendi!"); 
                                                        updateAllTables();
                                                    }
                                                    else
                                                    {
                                                        JOptionPane.showMessageDialog(null,"Hatalı telefon numarası!");
                                                    }
                                                }
                                                else
                                                {
                                                    JOptionPane.showMessageDialog(null,"Hatalı e posta!");
                                                }
                                            }
                                            else
                                            {
                                                JOptionPane.showMessageDialog(null,"Bu ID numarası hiçbir kullanıcıya ait değil!");
                                            }   
                                        }
                                    }
                                }
                                catch(SQLException | UnknownHostException | HeadlessException er)
                                {
                                    System.out.println(er);
                                }
                                
                            }
                        }
                        catch(HeadlessException er)
                        {
                            System.out.println(er);
                        }
                    }
                });
                
                del.addActionListener(new ActionListener()
                {   
                    @Override
                    public void actionPerformed(ActionEvent e)
                    { 
                        try
                        {   
                            String id = fid.getText();
                            if(id.equals(""))
                            {
                                JOptionPane.showMessageDialog(null,"Lütfen silmek istediğiniz kişinin id numarasını giriniz.");    
                            }
                            else
                            {
                                addDeletedOnes();
                                String delquery = "select count(id) as idNumber from users where id = '"+id+"'";
                                rs = st.executeQuery(delquery);
                                while(rs.next())
                                {
                                    int totalid = rs.getInt("idNumber");
                                    if(totalid == 1)
                                    {
                                        String delQuery = "Delete from users where id = '"+id+"'";
                                        st.executeUpdate(delQuery);
                                        String delQuery2 = "Delete from usersignup where User_id = '"+id+"'";
                                        st.executeUpdate(delQuery2);
                                        
                                        JOptionPane.showMessageDialog(null,"Kullanıcı silindi.");
                                        updateAllTables();
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null,"Bu ID'ye ait kullanıcı bulunamadı!");
                                    }   
                                }
                            }
                        }
                        catch(HeadlessException | SQLException excp)
                        {
                            System.out.println("Error found ! " + excp);
                        }
                    }
                });
                
                ex.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    { 
                        try
                        {
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setAcceptAllFileFilterUsed(false);
                            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files" ,".xls");
                            fileChooser.setFileFilter(filter);
                            fileChooser.setCurrentDirectory(new File("C:\\ "));
                            fileChooser.showSaveDialog(null);
                            
                            String uzantı = fileChooser.getSelectedFile().toString();
                            uzantı = uzantı + ".xls";
                            File file = new File(uzantı);
                            
                            System.out.println(file);
                            toExcel(table, file);
                        } 
                        catch (HeadlessException ex) 
                        {
                            ex.printStackTrace();
                        }
                    }        
                });
                
                kayıtbutton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    { 
                        try
                        {
                            JFileChooser fileChooser = new JFileChooser();
                            
                            fileChooser.setAcceptAllFileFilterUsed(false);
                            FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf Files" ,".pdf");
                            fileChooser.setFileFilter(filter);
                            fileChooser.setCurrentDirectory(new File("C:\\ "));
                            fileChooser.showSaveDialog(null);
                            
                            String uzantı = fileChooser.getSelectedFile().toString();
                            uzantı = uzantı + ".pdf";
                            File file = new File(uzantı);
                            
                            System.out.println(file);
                            printToPdf(table,file);
                        }
                        catch(HeadlessException ex)
                        {
                            System.out.println(ex);
                        }
                    }
                });
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    } 
    
    public void getOptionsupdate(JComboBox cb, JTable table)
    {
        try
        {
            String secenek = cb.getSelectedItem().toString();
            if(secenek.equals("Hepsi"))
            {
                String tümDegisiklikler = "select * from changes";
                rs= st.executeQuery(tümDegisiklikler);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            }
            else if(secenek.equals("Silinen"))
            {
                String tümDegisiklikler = "select * from changes where type_id = '"+1+"'";
                rs= st.executeQuery(tümDegisiklikler);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            }
            if(secenek.equals("Güncellenen"))
            {
                String tümDegisiklikler = "select * from changes where type_id = '"+2+"'";
                rs= st.executeQuery(tümDegisiklikler);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
    }
    
    public void getOptions(JComboBox cb)
    {
        String secenek = cb.getSelectedItem().toString();
        if(secenek.equals("Hepsi"))
        {
            JFrame framech = new JFrame();
            framech.setBounds(0,0,800,500);
            framech.setResizable(false);
            framech.setVisible(true);
            framech.setLocationRelativeTo(null);
                            
            JPanel pch = new JPanel();
            pch.setLayout(new GridBagLayout());
            pch.setBackground(Color.LIGHT_GRAY);
            framech.add(pch, BorderLayout.NORTH);
                            
            final JComboBox cb1 = new JComboBox(new String[]{"","Hepsi","Silinen","Güncellenen"});
            cb1.setPreferredSize(new Dimension(115, 20));
            cb1.setBackground(Color.WHITE);
                            
            JPanel jp = new JPanel();
            jp.setLayout(new GridBagLayout());
            jp.setBackground(Color.LIGHT_GRAY);
            framech.add(jp, BorderLayout.CENTER);

            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(2, 2, 2, 2);
            c.gridx = 1; 
            c.gridy = 0;
            jp.add(cb1,c);
                
            try
            {
                String totalch = "select count(idchanges) as total from changes";
                rs = st.executeQuery(totalch);
                while(rs.next())
                {   
                    int t = rs.getInt("total");
                    final JTable tablech = new JTable(t,16);
                    tablech.setBounds(t,16,800,700);

                    String tümDegisiklikler = "select * from changes";
                    rs= st.executeQuery(tümDegisiklikler);
                    
                    int row = 0;
                    while(rs.next())
                    { 
                        tablech.setValueAt(rs.getString("idchanges"),row,0);
                        tablech.setValueAt(rs.getString("type_id"),row,1);
                        tablech.setValueAt(rs.getString("changedBy"),row,2);
                        tablech.setValueAt(rs.getString("comp_id"),row,3);
                        tablech.setValueAt(rs.getString("date"),row,4);
                        tablech.setValueAt(rs.getString("user_id"),row,5);
                        tablech.setValueAt(rs.getString("user_name"),row,6);
                        tablech.setValueAt(rs.getString("user_surname"),row,7);
                        tablech.setValueAt(rs.getString("user_email"),row,8);
                        tablech.setValueAt(rs.getString("user_username"),row,9);
                        tablech.setValueAt(rs.getString("user_password"),row,10);
                        tablech.setValueAt(rs.getString("user_birth"),row,11);
                        tablech.setValueAt(rs.getString("user_sex"),row,12);
                        tablech.setValueAt(rs.getString("user_city"),row,13);
                        tablech.setValueAt(rs.getString("user_phone"),row,14);
                        tablech.setValueAt(rs.getString("user_isAdmin"),row,15);
                        row++;
                    }
                                    
                    tablech.setPreferredScrollableViewportSize(new Dimension(750,400));
                    tablech.setFillsViewportHeight(true);
                    tablech.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("ID");  
                    tablech.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Islem ID");  
                    tablech.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Islem Yapan");  
                    tablech.getTableHeader().getColumnModel().getColumn(3).setHeaderValue("IP numarası");  
                    tablech.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Islem Tarihi");  
                    tablech.getTableHeader().getColumnModel().getColumn(5).setHeaderValue("Kullanıcı ID");  
                    tablech.getTableHeader().getColumnModel().getColumn(6).setHeaderValue("Adı");  
                    tablech.getTableHeader().getColumnModel().getColumn(7).setHeaderValue("Soyadı");  
                    tablech.getTableHeader().getColumnModel().getColumn(8).setHeaderValue("E Posta ");  
                    tablech.getTableHeader().getColumnModel().getColumn(9).setHeaderValue("Kullanıcı Adı"); 
                    tablech.getTableHeader().getColumnModel().getColumn(10).setHeaderValue("Sifre"); 
                    tablech.getTableHeader().getColumnModel().getColumn(11).setHeaderValue("Dogum Tarihi"); 
                    tablech.getTableHeader().getColumnModel().getColumn(12).setHeaderValue("Cinsiyet"); 
                    tablech.getTableHeader().getColumnModel().getColumn(13).setHeaderValue("Sehir"); 
                    tablech.getTableHeader().getColumnModel().getColumn(14).setHeaderValue("Telefon"); 
                    tablech.getTableHeader().getColumnModel().getColumn(15).setHeaderValue("isAdmin"); 
                                    
                    pch.add(tablech);
                    JScrollPane vertical = new JScrollPane(tablech);
                    vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    vertical.setVisible(true);
                    pch.add(vertical);
                                    
                    cb1.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        { 
                            try
                            {
                                getOptionsupdate(cb1,tablech);
                            }
                            catch(Exception er)
                            {
                                System.out.println(er);
                            }
                        }
                    });
                }
            }
            catch(SQLException err)
            {
                 System.out.println(err);
            }
        }
        else if(secenek.equals("Silinen"))
                {
                    JFrame framech = new JFrame();
                    framech.setBounds(0,0,800,500);
                    framech.setResizable(false);
                    framech.setVisible(true);
                    framech.setLocationRelativeTo(null);

                    JPanel pch = new JPanel();
                    pch.setLayout(new GridBagLayout());
                    pch.setBackground(Color.LIGHT_GRAY);
                                
                    framech.add(pch, BorderLayout.NORTH);

                    final JComboBox cb1 = new JComboBox(new String[]{"","Hepsi","Silinen","Güncellenen"});
                    cb1.setPreferredSize(new Dimension(115, 20));
                    cb1.setBackground(Color.WHITE);

                    JPanel jp = new JPanel();
                    jp.setLayout(new GridBagLayout());
                    jp.setBackground(Color.LIGHT_GRAY);
                    framech.add(jp, BorderLayout.CENTER);

                    GridBagConstraints c = new GridBagConstraints();
                    c.insets = new Insets(2, 2, 2, 2);
                    c.gridx = 1; 
                    c.gridy = 0;
                    jp.add(cb1,c);
                                
                    try
                    {
                        String totalch = "select count(idchanges) as total from changes where type_id = '"+1+"'";
                        rs = st.executeQuery(totalch);
                        while(rs.next())
                        {   
                            int t = rs.getInt("total");
                            final JTable tabledel = new JTable(t,16);
                            tabledel.setBounds(t,16,100,100);

                            String tümDegisiklikler = "select * from changes where type_id = '"+1+"'";

                            rs= st.executeQuery(tümDegisiklikler);
                            int row = 0;
                            while(rs.next())
                            { 
                                tabledel.setValueAt(rs.getString("idchanges"),row,0);
                                tabledel.setValueAt(rs.getString("type_id"),row,1);
                                tabledel.setValueAt(rs.getString("changedBy"),row,2);
                                tabledel.setValueAt(rs.getString("comp_id"),row,3);
                                tabledel.setValueAt(rs.getString("date"),row,4);
                                tabledel.setValueAt(rs.getString("user_id"),row,5);
                                tabledel.setValueAt(rs.getString("user_name"),row,6);
                                tabledel.setValueAt(rs.getString("user_surname"),row,7);
                                tabledel.setValueAt(rs.getString("user_email"),row,8);
                                tabledel.setValueAt(rs.getString("user_username"),row,9);
                                tabledel.setValueAt(rs.getString("user_password"),row,10);
                                tabledel.setValueAt(rs.getString("user_birth"),row,11);
                                tabledel.setValueAt(rs.getString("user_sex"),row,12);
                                tabledel.setValueAt(rs.getString("user_city"),row,13);
                                tabledel.setValueAt(rs.getString("user_phone"),row,14);
                                tabledel.setValueAt(rs.getString("user_isAdmin"),row,15);
                                row++;
                            }
                            tabledel.setPreferredScrollableViewportSize(new Dimension(750,400));
                            tabledel.setFillsViewportHeight(true);
                            tabledel.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("ID");  
                            tabledel.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Islem ID");  
                            tabledel.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Islem Yapan");  
                            tabledel.getTableHeader().getColumnModel().getColumn(3).setHeaderValue("IP numarası");  
                            tabledel.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Islem Tarihi");  
                            tabledel.getTableHeader().getColumnModel().getColumn(5).setHeaderValue("Kullanıcı ID");  
                            tabledel.getTableHeader().getColumnModel().getColumn(6).setHeaderValue("Adı");  
                            tabledel.getTableHeader().getColumnModel().getColumn(7).setHeaderValue("Soyadı");  
                            tabledel.getTableHeader().getColumnModel().getColumn(8).setHeaderValue("E Posta ");  
                            tabledel.getTableHeader().getColumnModel().getColumn(9).setHeaderValue("Kullanıcı Adı"); 
                            tabledel.getTableHeader().getColumnModel().getColumn(10).setHeaderValue("Sifre"); 
                            tabledel.getTableHeader().getColumnModel().getColumn(11).setHeaderValue("Dogum Tarihi"); 
                            tabledel.getTableHeader().getColumnModel().getColumn(12).setHeaderValue("Cinsiyet"); 
                            tabledel.getTableHeader().getColumnModel().getColumn(13).setHeaderValue("Sehir"); 
                            tabledel.getTableHeader().getColumnModel().getColumn(14).setHeaderValue("Telefon"); 
                            tabledel.getTableHeader().getColumnModel().getColumn(15).setHeaderValue("isAdmin"); 

                            pch.add(tabledel);
                            JScrollPane vertical = new JScrollPane(tabledel);
                            vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                            vertical.setVisible(true);
                            pch.add(vertical);
                            
                            cb1.addActionListener(new ActionListener()
                            {
                                @Override
                                public void actionPerformed(ActionEvent e)
                                { 
                                    try
                                    {
                                        getOptionsupdate(cb1,tabledel);
                                    }
                                    catch(Exception er)
                                    {
                                        System.out.println(er);
                                    }
                                }
                            });
                        }
                    }
                    catch(SQLException er)
                    {
                        System.out.println(er);
                    }   
                }
                        
        if(secenek.equals("Güncellenen"))
        {
            JFrame framech = new JFrame();
            framech.setBounds(0,0,800,500);
            framech.setResizable(false);
            framech.setVisible(true);
            framech.setLocationRelativeTo(null);

            JPanel pch = new JPanel();
            pch.setLayout(new GridBagLayout());
            pch.setBackground(Color.LIGHT_GRAY);
            framech.add(pch, BorderLayout.NORTH);

            final JComboBox cb1 = new JComboBox(new String[]{"","Hepsi","Silinen","Güncellenen"});
            cb1.setPreferredSize(new Dimension(115, 20));
            cb1.setBackground(Color.WHITE);

            JPanel jp = new JPanel();
            jp.setLayout(new GridBagLayout());
            jp.setBackground(Color.LIGHT_GRAY);
                                
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(2, 2, 2, 2);
            c.gridx = 1; 
            c.gridy = 0;
            jp.add(cb1,c);
                                
            framech.add(jp, BorderLayout.CENTER);
                            
            try
            {
                String totalch = "select count(idchanges) as total from changes where type_id = '"+2+"'";
                rs = st.executeQuery(totalch);
                while(rs.next())
                {   
                    int t = rs.getInt("total");
                    final JTable tableg = new JTable(t,16);
                    tableg.setBounds(t,16,100,100);

                    String tümDegisiklikler = "select * from changes where type_id = '"+2+"'";
                    rs= st.executeQuery(tümDegisiklikler);
                    int row = 0;
                    while(rs.next())
                    { 
                        tableg.setValueAt(rs.getString("idchanges"),row,0);
                        tableg.setValueAt(rs.getString("type_id"),row,1);
                        tableg.setValueAt(rs.getString("changedBy"),row,2);
                        tableg.setValueAt(rs.getString("comp_id"),row,3);
                        tableg.setValueAt(rs.getString("date"),row,4);
                        tableg.setValueAt(rs.getString("user_id"),row,5);
                        tableg.setValueAt(rs.getString("user_name"),row,6);
                        tableg.setValueAt(rs.getString("user_surname"),row,7);
                        tableg.setValueAt(rs.getString("user_email"),row,8);
                        tableg.setValueAt(rs.getString("user_username"),row,9);
                        tableg.setValueAt(rs.getString("user_password"),row,10);
                        tableg.setValueAt(rs.getString("user_birth"),row,11);
                        tableg.setValueAt(rs.getString("user_sex"),row,12);
                        tableg.setValueAt(rs.getString("user_city"),row,13);
                        tableg.setValueAt(rs.getString("user_phone"),row,14);
                        tableg.setValueAt(rs.getString("user_isAdmin"),row,15);
                        row++;
                    }
                                       
                    tableg.setPreferredScrollableViewportSize(new Dimension(750,400));
                    tableg.setFillsViewportHeight(true);
                    tableg.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("ID");  
                    tableg.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Islem ID");  
                    tableg.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Islem Yapan");  
                    tableg.getTableHeader().getColumnModel().getColumn(3).setHeaderValue("IP numarası");  
                    tableg.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Islem Tarihi");  
                    tableg.getTableHeader().getColumnModel().getColumn(5).setHeaderValue("Kullanıcı ID");  
                    tableg.getTableHeader().getColumnModel().getColumn(6).setHeaderValue("Adı");  
                    tableg.getTableHeader().getColumnModel().getColumn(7).setHeaderValue("Soyadı");  
                    tableg.getTableHeader().getColumnModel().getColumn(8).setHeaderValue("E Posta ");  
                    tableg.getTableHeader().getColumnModel().getColumn(9).setHeaderValue("Kullanıcı Adı"); 
                    tableg.getTableHeader().getColumnModel().getColumn(10).setHeaderValue("Sifre"); 
                    tableg.getTableHeader().getColumnModel().getColumn(11).setHeaderValue("Dogum Tarihi"); 
                    tableg.getTableHeader().getColumnModel().getColumn(12).setHeaderValue("Cinsiyet"); 
                    tableg.getTableHeader().getColumnModel().getColumn(13).setHeaderValue("Sehir"); 
                    tableg.getTableHeader().getColumnModel().getColumn(14).setHeaderValue("Telefon"); 
                    tableg.getTableHeader().getColumnModel().getColumn(15).setHeaderValue("isAdmin"); 

                    pch.add(tableg);
                    JScrollPane vertical = new JScrollPane(tableg);
                    vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    vertical.setVisible(true);
                    pch.add(vertical);
                                   
                    cb1.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        { 
                            try
                            {
                                getOptionsupdate(cb1,tableg);
                            }
                            catch(Exception er)
                            {
                                System.out.println(er);
                            }
                        }
                    });
                }
            }
            catch(SQLException error)
            {
                System.out.println(error);
            }   
        }
    }
    
    public void addDeletedOnes()
    {
        try
        {
            int typeid = 1;
            String sadd1 = rs.getString("id");
            String sadd2 = rs.getString("name");
            String sadd3= rs.getString("surname");
            String sadd4 = rs.getString("mail");
            String username = rs.getString("username");
            String pass = rs.getString("password");
            String birth = rs.getString("birthdate");
            String sex = rs.getString("sex");
            String sadd5 = rs.getString("phone");
            String sadd6 = rs.getString("city");
            String sadd7= rs.getString("isAdmin");
            String ipAdress = Inet4Address.getLocalHost().getHostAddress();
            
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH); 
            int month2 = month + 1;
            int day= cal.get(Calendar.DAY_OF_MONTH); 
            String thatDate = day + "-" + month2 + "-" + year;

            String changedBy = t1.getText();

            cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(cal.getTime());
            String allTime = thatDate + " " + time;
            String querych = "Insert Into changes(type_id,changedBy,comp_id,date,user_id,user_name,user_surname,user_email,user_username,user_password,user_birth,user_sex,user_city,user_phone, user_isAdmin ) Values('"+typeid+"','"+changedBy+"','"+ipAdress+"','"+allTime+"','"+sadd1+"','"+sadd2+"','"+sadd3+"','"+sadd4+"','"+username+"','"+pass+"','"+birth+"','"+sex+"','"+sadd6+"','"+sadd5+"','"+sadd7+"')";
            st.executeUpdate(querych); 
        }
        catch(SQLException | UnknownHostException ex)
        {
            System.out.println(ex);
        }
    }
    
    public void updateAllTables()
    {
        try
        {
            String tümBilgiler = "select id, name, surname, mail, username, password,birthdate,sex, city,phone, isAdmin,Signup_time,compName,ip_number from users, usersignup where id = User_id";
            rs= st.executeQuery(tümBilgiler);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
    }
    
    public void updateTable()
    {
        try
        {
            final String user = t1.getText();
            String tümBilgiler = "select * from users where username = '"+user+"' ";
            rs= st.executeQuery(tümBilgiler);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
    }
    
    public void toExcel(JTable t, File file) 
    {
        try 
        {
            TableModel model = table.getModel();
            try (FileWriter excel = new FileWriter(file)) 
            {
                String[] columnNames = {"ID","Adi","Soyadi","E Posta","Kullanici Adi","Sifre","Dogum Tarihi","Cinsiyet","Sehir","Telefon","isAdmin","Kayit Tarihi","Bilgisayar Adi","IP Numarasi"};
                for(String columnName : columnNames) 
                {
                    excel.write(columnName + "\t");
                }
                excel.write("\n");
                for(int i=0; i< model.getRowCount(); i++) 
                {
                    for(int j=0; j < model.getColumnCount(); j++) 
                    {
                        excel.write(model.getValueAt(i,j).toString()+"\t");
                    }
                    excel.write("\n");
                }  
            }
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void printToPdf(JTable t,File f)
    {
        try
        {
            int count=t.getRowCount();
            Document document=new Document();
            PdfWriter.getInstance(document, new FileOutputStream(f));
            document.open();
            PdfPTable tab=new PdfPTable(14);
            tab.setWidthPercentage(100);

            float[] columnWidths = new float[] {15f,30f,30f,30f,30f,20f,25f,25f,30f,20f,15f,30f,30f,30f};
            tab.setWidths(columnWidths);
            tab.getRowHeight(10);

            tab.addCell(new Phrase("ID", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Adi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Soyadi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("E Posta", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Kullanici Adi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Sifre", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Dogum Tarihi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Cinsiyet", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Sehir", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Telefon", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("isAdmin", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Kayit Tarihi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Bilgisayar Adi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("IP Numarasi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            
            for(int i=0;i<count;i++)
            {
                for(int j=0; j<14; j++)
                {
                    Object obj1 = GetData(t, i, j);
                    String value1=obj1.toString();
                    tab.addCell(new Phrase(value1, FontFactory.getFont(FontFactory.HELVETICA, 4)));
                }
            }
            document.add(tab);
            document.close();
        }
        catch(FileNotFoundException | DocumentException e)
        {
            System.out.println(e);
        }
    }
    
    public Object GetData(JTable table, int row_index, int col_index)
    {
        return table.getModel().getValueAt(row_index, col_index);
    }
    
    public void toExcel2(JTable t, File file) 
    {
        try
        {
            TableModel model = table.getModel();
            try(FileWriter excel = new FileWriter(file)) 
            {
                String[] columnNames = {"ID","Adı","Soyadı","E Posta","Kullanıcı Adı","Şifre","Doğum Tarihi","Cinsiyet","Şehir","Telefon","isAdmin"};
                for (String columnName : columnNames)
                {
                    excel.write(columnName + "\t");
                }
                excel.write("\n");
                for(int i=0; i< model.getRowCount(); i++)
                {
                    for(int j=0; j < model.getColumnCount(); j++) 
                    {
                        excel.write(model.getValueAt(i,j).toString()+"\t");
                    }
                    excel.write("\n");
                }  
            }
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }
    
    public void printToPdf2(JTable t,File f)
    {
        try
        {
            int count=t.getRowCount();
            Document document=new Document();
            PdfWriter.getInstance(document, new FileOutputStream(f));
            document.open();
            PdfPTable tab=new PdfPTable(11);
            tab.setWidthPercentage(100);

            float[] columnWidths = new float[] {15f,30f,30f,30f,30f,20f,25f,25f,30f,20f,15f};
            tab.setWidths(columnWidths);
            tab.getRowHeight(10);
            tab.addCell(new Phrase("ID", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Adi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Soyadi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("E Posta", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Kullanici Adi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Sifre", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Dogum Tarihi", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Cinsiyet", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Sehir", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("Telefon", FontFactory.getFont(FontFactory.HELVETICA, 4)));
            tab.addCell(new Phrase("isAdmin", FontFactory.getFont(FontFactory.HELVETICA, 4)));

            for(int i=0;i<count;i++)
            {
                for(int j=0; j<11; j++)
                {
                    Object obj1 = GetData(t, i, j);
                    String value1=obj1.toString();
                    tab.addCell(new Phrase(value1, FontFactory.getFont(FontFactory.HELVETICA, 4)));
                }
            }
            document.add(tab);
            document.close();
        }
        catch(FileNotFoundException | DocumentException e)
        {
            System.out.println(e);
        }
    }
   
    public void getSpecUserInfo()
    {   
        try 
        {   
            final JFrame frame = new JFrame("Üye Bilgileri");
            frame.setBounds(0,0,700,300);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            final String user = t1.getText();
            String tümBilgiler = "select * from users where username = '"+user+"' ";
            rs= st.executeQuery(tümBilgiler);
            
            table = new JTable(1,11);
            table.setBounds(1,11,100,100);
            
            int li_row = 0;
            while(rs.next())
            {
                table.setValueAt(rs.getString("id"),li_row,0);
                table.setValueAt(rs.getString("name"),li_row,1);
                table.setValueAt(rs.getString("surname"),li_row,2);
                table.setValueAt(rs.getString("mail"),li_row,3);
                table.setValueAt(rs.getString("username"),li_row,4);
                table.setValueAt(rs.getString("password"),li_row,5);
                table.setValueAt(rs.getString("birthdate"),li_row,6);
                table.setValueAt(rs.getString("sex"),li_row,7);
                table.setValueAt(rs.getString("city"),li_row,8);
                table.setValueAt(rs.getString("phone"),li_row,9);
                table.setValueAt(rs.getString("isAdmin"),li_row,10);
                li_row++;
            }
            
            JPanel p = new JPanel(new GridLayout(0,1,10,10));
            table.setPreferredScrollableViewportSize(new Dimension(650, 20));
            table.setFillsViewportHeight(true);
            table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("ID");  
            table.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Adı");  
            table.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Soyadı");  
            table.getTableHeader().getColumnModel().getColumn(3).setHeaderValue("E Posta");  
            table.getTableHeader().getColumnModel().getColumn(4).setHeaderValue("Kullanıcı Adı");  
            table.getTableHeader().getColumnModel().getColumn(5).setHeaderValue("Şifre");  
            table.getTableHeader().getColumnModel().getColumn(6).setHeaderValue("Doğum Tarihi");  
            table.getTableHeader().getColumnModel().getColumn(7).setHeaderValue("Cinsiyet");  
            table.getTableHeader().getColumnModel().getColumn(8).setHeaderValue("Şehir");  
            table.getTableHeader().getColumnModel().getColumn(9).setHeaderValue("Telefon");  
            table.getTableHeader().getColumnModel().getColumn(10).setHeaderValue("isAdmin"); 
                
            p.add(table);
            JScrollPane hori = new JScrollPane(table);
            hori.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            hori.setVisible(true);
            p.add(hori);
            p.setBackground(Color.LIGHT_GRAY);
            frame.add(p,BorderLayout.NORTH);
                
            JPanel p2 = new JPanel();
            p2.setLayout(new GridBagLayout());
            p2.setBackground(Color.LIGHT_GRAY);
            frame.add(p2, BorderLayout.CENTER);
                
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(2, 2, 2, 2);
                c.gridx = 1; 
                c.gridy = 0;
                JLabel l1 = new JLabel("Ad:");
                p2.add(l1,c);
                
                c.gridx = 2; 
                c.gridy = 0;
                final JTextField jtf1 = new JTextField(10);
                p2.add(jtf1,c);
                
                c.gridx = 1; 
                c.gridy = 1;
                JLabel l2 = new JLabel("Soyad:");
                p2.add(l2,c);
                
                c.gridx = 2; 
                c.gridy = 1;
                final JTextField jtf2 = new JTextField(10);
                p2.add(jtf2,c);
                
                c.gridx = 1; 
                c.gridy = 2;
                JLabel l3 = new JLabel("E-Mail:");
                p2.add(l3,c);
                
                c.gridx = 2; 
                c.gridy = 2;
                final JTextField jtf3= new JTextField(10);
                p2.add(jtf3,c);
                
                c.gridx = 1; 
                c.gridy = 3;
                JLabel l4= new JLabel("Şifre:");
                p2.add(l4,c);
                
                c.gridx = 2; 
                c.gridy = 3;
                final JTextField jtf4 = new JTextField(10);
                p2.add(jtf4,c);
                
                c.gridx = 1; 
                c.gridy = 4;
                JLabel l5= new JLabel("Şehir:");
                p2.add(l5,c);
                
                c.gridx = 2; 
                c.gridy = 4;
                final JTextField jtf5 = new JTextField(10);
                p2.add(jtf5,c);
                
                c.gridx = 1; 
                c.gridy = 5;
                JLabel l6 = new JLabel("Telefon:");
                p2.add(l6,c);
                
                c.gridx = 2; 
                c.gridy = 5;
                final JTextField jtf6 = new JTextField(10);
                p2.add(jtf6,c);
                
                c.gridx = 0; 
                c.gridy = 6;
                JButton b2 = new JButton("Güncelle");
                b2.setPreferredSize(new Dimension(120, 25));
                p2.add(b2,c);
                
                c.gridx = 1; 
                c.gridy = 6;
                JButton b3 = new JButton("Hesabımı Sil");
                b3.setPreferredSize(new Dimension(120, 25));
                p2.add(b3,c);
                
                c.gridx = 2; 
                c.gridy = 6;
                JButton b4 = new JButton("Excel'e Aktar");
                b4.setPreferredSize(new Dimension(120, 25));
                p2.add(b4,c);
                
                c.gridx = 3; 
                c.gridy = 6;
                JButton pdfbutton2 = new JButton("Kaydet");
                pdfbutton2.setPreferredSize(new Dimension(120, 25));
                p2.add(pdfbutton2,c);
                frame.add(p2);
                
                jtf1.addKeyListener(new KeyAdapter()
                {   
                    @Override
                    public void keyTyped(KeyEvent e)
                    {
                        char c = e.getKeyChar();
                        ArrayList<String> chars = new ArrayList<>(Arrays.asList(" ","A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z",
                        "a", "b", "c", "ç", "d", "e", "f", "g", "ğ", "h", "ı", "i", "j", "k", "l", "m", "n", "o", "ö", "p", "r", "s", "ş", "t", "u", "ü", "v", "y", "z"));

                        if (!chars.contains(String.valueOf(c)) || jtf1.getText().length() > 30 ) 
                        {
                            Toolkit.getDefaultToolkit().beep(); 
                            e.consume();  
                        }
                    }
                });
                
                jtf2.addKeyListener(new KeyAdapter()
                {   
                    @Override
                    public void keyTyped(KeyEvent e)
                    {
                        char c = e.getKeyChar();
                        ArrayList<String> chars = new ArrayList<>(Arrays.asList(" ","A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z",
                        "a", "b", "c", "ç", "d", "e", "f", "g", "ğ", "h", "ı", "i", "j", "k", "l", "m", "n", "o", "ö", "p", "r", "s", "ş", "t", "u", "ü", "v", "y", "z"));

                        if (!chars.contains(String.valueOf(c)) || jtf2.getText().length() > 30 ) 
                        {
                            Toolkit.getDefaultToolkit().beep(); 
                            e.consume();  
                        }
                    }
                });
                
                jtf5.addKeyListener(new KeyAdapter()
                {
                    @Override
                    public void keyTyped(KeyEvent e)
                    {
                        char c = e.getKeyChar();
                        if(!Character.isAlphabetic(c))
                        {
                            Toolkit.getDefaultToolkit().beep(); 
                            e.consume();
                        }
                        if(jtf5.getText().length() > 30)
                        {
                            e.consume();
                        }
                    }
                });
                
                jtf6.addKeyListener(new KeyAdapter()
                {
                    @Override
                    public void keyTyped(KeyEvent e)
                    {
                        if(jtf6.getText().length() > 12)
                        {
                            e.consume();
                        }
                    }
                }); 
            
                table.addMouseListener(new java.awt.event.MouseAdapter() 
                {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt)
                    {
                        try
                        {
                            int row = table.getSelectedRow();
                            String tableClick = (table.getModel().getValueAt(row, 0).toString());
                            String sql = "Select * from users where id = '"+tableClick+"'";
                            rs = st.executeQuery(sql);
                            if(rs.next())
                            {
                                String fadd1 = rs.getString("name");
                                jtf1.setText(fadd1);
                                String fadd2= rs.getString("surname");
                                jtf2.setText(fadd2);
                                String fadd3 = rs.getString("mail");
                                jtf3.setText(fadd3);
                                String fadd4= rs.getString("password");
                                jtf4.setText(fadd4);
                                String fadd6 = rs.getString("city");
                                jtf5.setText(fadd6);
                                String fadd5 = rs.getString("phone");
                                jtf6.setText(fadd5);
                            }
                        }
                        catch(SQLException ex)
                        {
                            System.out.println("Error :" + ex);
                        }
                    }
                });
                
                b2.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    { 
                        
                        String adı = jtf1.getText();
                        String soyadı = jtf2.getText();
                        String posta = jtf3.getText();
                        String sifre = jtf4.getText();
                        String sehir = jtf5.getText();
                        String tel = jtf6.getText();
                        try
                        {
                            String findId = "Select id from users where username = '"+user+"'";
                            rs= st.executeQuery(findId);
                            while(rs.next())
                            {
                                String idVal = rs.getString("id");
                                try
                                {
                                    String sql = "Select * from users where id = '"+idVal+"'";
                                    rs = st.executeQuery(sql);
                                    if(rs.next())
                                    {
                                        String sadd1 = rs.getString("id");
                                        String sadd2 = rs.getString("name");
                                        String sadd3= rs.getString("surname");
                                        String sadd4 = rs.getString("mail");
                                        String username = rs.getString("username");
                                        String pass = rs.getString("password");
                                        String birth = rs.getString("birthdate");
                                        String sex = rs.getString("sex");
                                        String sadd5 = rs.getString("phone");
                                        String sadd6 = rs.getString("city");
                                        String sadd7= rs.getString("isAdmin");
                                        String ipAdress = Inet4Address.getLocalHost().getHostAddress();

                                        Calendar cal = Calendar.getInstance();
                                        int year = cal.get(Calendar.YEAR);
                                        int month = cal.get(Calendar.MONTH); 
                                        int month2 = month + 1;
                                        int day= cal.get(Calendar.DAY_OF_MONTH); 
                                        String thatDate = day + "-" + month2 + "-" + year;

                                        int typeid = 2;
                                        String changedBy = t1.getText();

                                        cal.getTime();
                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                        String time = sdf.format(cal.getTime());
                                        String allTime = thatDate + " " + time;
                                        String querych = "Insert Into changes(type_id,changedBy,comp_id,date,user_id,user_name,user_surname,user_email,user_username,user_password,user_birth,user_sex,user_city,user_phone, user_isAdmin ) Values('"+typeid+"','"+changedBy+"','"+ipAdress+"','"+allTime+"','"+sadd1+"','"+sadd2+"','"+sadd3+"','"+sadd4+"','"+username+"','"+pass+"','"+birth+"','"+sex+"','"+sadd6+"','"+sadd5+"','"+sadd7+"')";
                                        st.executeUpdate(querych); 

                                    }
                                }
                                catch(SQLException | UnknownHostException er)
                                {
                                    System.out.println(er);
                                }
                                
                                if(adı.equals("") ||soyadı.equals("") || posta.equals("") || sifre.equals("") || sehir.equals("") ||tel.equals(""))
                                {
                                    JOptionPane.showMessageDialog(null,"Eksik alan bıraktınız!");  
                                }
                                else
                                {
                                    String isValEmail = validateEmailAddress(posta);
                                    String isValPhone = validateMobileNumber(tel);
                                    if(isValEmail.equalsIgnoreCase("Valid"))
                                    {
                                        if(isValPhone.equals("Valid")) 
                                        {  
                                            String update = "Update users Set name = '"+adı+"', surname = '"+soyadı+"', mail = '"+posta+"',password = '"+sifre+"', phone= '"+tel+"', city = '"+sehir+"' where id = '"+idVal+"' ";
                                            PreparedStatement statement = con.prepareStatement(update);
                                            statement.executeUpdate();
                                            JOptionPane.showMessageDialog(null,"Kullanıcı Bilgileriniz Güncellendi!");  
                                            updateTable();
                                        } 
                                        else
                                        {
                                            JOptionPane.showMessageDialog(null,"Hatalı Telefon Numarası!");  
                                        }
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null,"Hatalı E-posta Adresi!");  
                                    }
                                    
                                }
                            }
                        }
                        catch(SQLException | HeadlessException excp)
                        {
                            System.out.println("Error found " + excp);
                        }
                    }  
                });
                
                b3.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    { 
                        try
                        {
                            addDeletedOnes();
                            String findId = "Select id from users where username = '"+user+"'";
                            rs= st.executeQuery(findId);
                            while(rs.next())
                            {
                                String idVal = rs.getString("id");
                                try
                                {
                                    String silSql = "Delete from users where id = '"+idVal+"'";
                                    st.executeUpdate(silSql);
                                    String silSql2 = "Delete from usersignup where User_id = '"+idVal+"'";
                                    st.executeUpdate(silSql2);
                                    JOptionPane.showMessageDialog(null,"Hesabınız Silindi"); 
                                    frame.dispose();
                                }
                                catch(SQLException | HeadlessException ex)
                                {
                                    System.out.println(ex);
                                }
                            }
                        }
                        catch(SQLException ex)
                        {
                            System.out.println("Error: " + ex);
                        }
                    }
                }); 
                
                b4.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    { 
                        try
                        {
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setAcceptAllFileFilterUsed(false);
                            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files" ,".xls");
                            fileChooser.setFileFilter(filter);
                            fileChooser.setCurrentDirectory(new File("C:\\ "));
                            fileChooser.showSaveDialog(null);
                            
                            String uzantı = fileChooser.getSelectedFile().toString();
                            uzantı = uzantı + ".xls";
                            File file = new File(uzantı);
                            toExcel2(table, file);
                        } 
                        catch (HeadlessException ex) 
                        {
                            ex.printStackTrace();
                        }
                    }        
                });
                
                pdfbutton2.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    { 
                        try
                        {
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setAcceptAllFileFilterUsed(false);
                            FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf Files" ,".pdf");
                            fileChooser.setFileFilter(filter);
                            fileChooser.setCurrentDirectory(new File("C:\\ "));
                            fileChooser.showSaveDialog(null);
                            
                            String uzantı = fileChooser.getSelectedFile().toString();
                            uzantı = uzantı + ".pdf";
                            File file = new File(uzantı);
                            printToPdf2(table,file);
                        }
                        catch(HeadlessException ex)
                        {
                            System.out.println(ex);
                        }
                    }
                });
        }
        catch(HeadlessException | SQLException excp)
        {
            System.out.println(excp);
        }
    }
}

    
    
    
            
       
                
    

    


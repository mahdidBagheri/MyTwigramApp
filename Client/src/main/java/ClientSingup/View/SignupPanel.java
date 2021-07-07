package ClientSingup.View;

import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import MainFrame.View.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

public class SignupPanel extends JPanel implements ActionListener {

    LocalDate Date = LocalDate.now();

    JLabel TitleLable;

    JLabel FirstNameField ;
    JTextArea FirstNameText;

    JLabel LastNameField ;
    JTextArea LastNameText;

    JLabel EmailLable ;
    JTextArea EmailText;

    JLabel PhoneLable ;
    JTextArea PhoneText;

    JLabel UserNameLable ;
    JTextArea UserNameText;

    JLabel Password1Lable ;
    JTextArea Password1Text;

    JLabel Password2Lable ;
    JTextArea Password2Text;

    JLabel BioLable ;
    JTextArea BioText;

    JLabel BirthDateLable ;
    JComboBox<Integer> YearCombo;
    JComboBox<Integer> MonthCombo;
    JComboBox<Integer> DayCombo;

    JCheckBox BirthDateCheck;

    JButton SingUpBotton;

    public SignupPanel(MainPanel mainPanel) throws IOException {
        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBackground(colorConfig.getColor02());
        this.setLayout(null);
        this.setBounds(0,80,(int)(frameConfig.getWidth()),(int)frameConfig.getHeight()-80);

        TitleLable = new JLabel();
        TitleLable.setBounds(220,20,150,20);
        TitleLable.setText("SingUp!");
        TitleLable.setVisible(true);

        FirstNameField = new JLabel();
        FirstNameField.setBounds(50,50,150,20);
        FirstNameField.setText("Insert your first name*");
        FirstNameField.setVisible(true);

        FirstNameText = new JTextArea();
        FirstNameText.setBounds(50,80,150,40);
        FirstNameText.setVisible(true);

        LastNameField = new JLabel();
        LastNameField.setBounds(300,50,150,20);
        LastNameField.setText("Insert your last name*");
        LastNameField.setVisible(true);

        LastNameText = new JTextArea();
        LastNameText.setBounds(300,80,150,40);
        LastNameText.setVisible(true);

        EmailLable = new JLabel();
        EmailLable.setBounds(50,140,150,20);
        EmailLable.setText("Insert your email*");
        EmailLable.setVisible(true);

        EmailText = new JTextArea();
        EmailText.setBounds(50,170,150,40);
        EmailText.setVisible(true);

        PhoneLable = new JLabel();
        PhoneLable.setBounds(300,140,150,20);
        PhoneLable.setText("Insert your phone");
        PhoneLable.setVisible(true);

        PhoneText = new JTextArea();
        PhoneText.setBounds(300,170,150,40);
        PhoneText.setVisible(true);

        BirthDateLable = new JLabel();
        BirthDateLable.setBounds(50+250,240,150,20);
        BirthDateLable.setText("Choose Your Birth Date:");
        BirthDateLable.setVisible(true);

        BirthDateCheck = new JCheckBox();
        BirthDateCheck.setSelected(true);

        BirthDateCheck.setBackground(colorConfig.getColor01());
        BirthDateCheck.setBounds(450,240,20,20);
        BirthDateCheck.addActionListener(this);

        Integer[] DayArray = CreateSeqArrayOfIntegers(1,31);
        DayCombo = new JComboBox<Integer>(DayArray);
        DayCombo.setBounds(170+250,270,40,20);

        Integer[] MonthArray = CreateSeqArrayOfIntegers(1,13);
        MonthCombo = new JComboBox<Integer>(MonthArray);
        MonthCombo.setBounds(125+250,270,40,20);

        Integer[] YearArray = CreateSeqArrayOfIntegers(Date.getYear()-1,Date.getYear()-100);
        YearCombo = new JComboBox<Integer>(YearArray);
        YearCombo.setBounds(50+250,270,70,20);

        UserNameLable = new JLabel();
        UserNameLable.setBounds(50,240,150,20);
        UserNameLable.setText("Insert your UserName*");
        UserNameLable.setVisible(true);

        UserNameText = new JTextArea();
        UserNameText.setBounds(50,270,150,40);
        UserNameText.setVisible(true);

        Password1Lable = new JLabel();
        Password1Lable.setBounds(50,310,150,20);
        Password1Lable.setText("Insert your Password*");
        Password1Lable.setVisible(true);

        Password1Text = new JTextArea();
        Password1Text.setBounds(50,340,150,40);
        Password1Text.setVisible(true);

        Password2Lable = new JLabel();
        Password2Lable.setBounds(50,390,200,20);
        Password2Lable.setText("Insert your Password again*");
        Password2Lable.setVisible(true);

        Password2Text = new JTextArea();
        Password2Text.setBounds(50,420,150,40);
        Password2Text.setVisible(true);

        BioLable = new JLabel();
        BioLable.setBounds(300,310,150,20);
        BioLable.setText("Insert Bio");
        BioLable.setVisible(true);

        BioText = new JTextArea();
        BioText.setBounds(300,340,150,40);
        BioText.setVisible(true);

        SingUpBotton = new JButton("SingUp!");
        SingUpBotton.setText("SingUp!");
        SingUpBotton.setFocusable(false);
        SingUpBotton.setBounds(300,400,110,50);
        SingUpBotton.addActionListener(this);

        this.add(TitleLable);
        this.add(FirstNameField);
        this.add(LastNameField);
        this.add(FirstNameText);
        this.add(LastNameText);
        this.add(EmailLable);
        this.add(EmailText);
        this.add(PhoneLable);
        this.add(PhoneText);
        this.add(BirthDateLable);
        this.add(BirthDateCheck);
        this.add(DayCombo);
        this.add(MonthCombo);
        this.add(YearCombo);
        this.add(UserNameLable);
        this.add(UserNameText);
        this.add(Password1Lable);
        this.add(Password1Text);
        this.add(Password2Lable);
        this.add(Password2Text);
        this.add(SingUpBotton);
        this.add(BioLable);
        this.add(BioText);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public Integer[] CreateSeqArrayOfIntegers(int start, int stop){
        Integer[] arr = new Integer[Math.abs(start-stop)];
        if(start > stop){
            for(int i = 0; i< Math.abs(start-stop); i++){
                arr[i] = start - i;
            }
        }
        else {
            for(int i = 0; i< Math.abs(start-stop); i++){
                arr[i] = start + i;
            }
        }

        return arr;
    }


}

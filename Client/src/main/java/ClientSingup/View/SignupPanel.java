package ClientSingup.View;

import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Exceptions.EmailExistException;
import Connection.Exceptions.UsernameExistsException;
import ClientSingup.Events.SignupEvent;
import ClientSingup.Exceptions.EmptyFieldException;
import ClientSingup.Exceptions.PasswordsNotMatchException;
import ClientSingup.Exceptions.UserNameStartsWithDigitException;
import ClientSingup.Listener.SignupListener;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import MainFrame.View.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

public class SignupPanel extends JPanel implements ActionListener {

    LocalDate date = LocalDate.now();

    JLabel titleLable;

    JLabel firstNameField ;
    JTextArea firstNameText;

    JLabel lastNameField ;
    JTextArea lastNameText;

    JLabel emailLable ;
    JTextArea emailText;

    JLabel phoneLable ;
    JTextArea phoneText;

    JLabel userNameLable ;
    JTextArea userNameText;

    JLabel password1Lable ;
    JTextArea password1Text;

    JLabel password2Lable ;
    JTextArea password2Text;

    JLabel bioLable ;
    JTextArea bioText;

    JLabel birthDateLable ;
    JComboBox<Integer> yearCombo;
    JComboBox<Integer> monthCombo;
    JComboBox<Integer> dayCombo;

    JCheckBox birthDateCheck;

    JButton singUpBotton;

    SignupListener signupListener;

    public SignupPanel(MainPanel mainPanel) throws IOException {
        signupListener = new SignupListener(mainPanel);

        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBackground(colorConfig.getColor02());
        this.setLayout(null);
        this.setBounds(0,80,(int)(frameConfig.getWidth()),(int)frameConfig.getHeight()-80);

        titleLable = new JLabel();
        titleLable.setBounds(220,20,150,20);
        titleLable.setText("SingUp!");
        titleLable.setVisible(true);

        firstNameField = new JLabel();
        firstNameField.setBounds(50,50,150,20);
        firstNameField.setText("Insert your first name*");
        firstNameField.setVisible(true);

        firstNameText = new JTextArea();
        firstNameText.setBounds(50,80,150,40);
        firstNameText.setVisible(true);

        lastNameField = new JLabel();
        lastNameField.setBounds(300,50,150,20);
        lastNameField.setText("Insert your last name*");
        lastNameField.setVisible(true);

        lastNameText = new JTextArea();
        lastNameText.setBounds(300,80,150,40);
        lastNameText.setVisible(true);

        emailLable = new JLabel();
        emailLable.setBounds(50,140,150,20);
        emailLable.setText("Insert your email*");
        emailLable.setVisible(true);

        emailText = new JTextArea();
        emailText.setBounds(50,170,150,40);
        emailText.setVisible(true);

        phoneLable = new JLabel();
        phoneLable.setBounds(300,140,150,20);
        phoneLable.setText("Insert your phone");
        phoneLable.setVisible(true);

        phoneText = new JTextArea();
        phoneText.setBounds(300,170,150,40);
        phoneText.setVisible(true);

        birthDateLable = new JLabel();
        birthDateLable.setBounds(50+250,240,150,20);
        birthDateLable.setText("Choose Your Birth Date:");
        birthDateLable.setVisible(true);

        birthDateCheck = new JCheckBox();
        birthDateCheck.setSelected(true);
        birthDateCheck.setBackground(colorConfig.getColor01());
        birthDateCheck.setBounds(450,240,20,20);
        birthDateCheck.addActionListener(this);

        Integer[] DayArray = CreateSeqArrayOfIntegers(1,31);
        dayCombo = new JComboBox<Integer>(DayArray);
        dayCombo.setBounds(170+250,270,40,20);

        Integer[] MonthArray = CreateSeqArrayOfIntegers(1,13);
        monthCombo = new JComboBox<Integer>(MonthArray);
        monthCombo.setBounds(125+250,270,40,20);

        Integer[] YearArray = CreateSeqArrayOfIntegers(date.getYear()-1,date.getYear()-100);
        yearCombo = new JComboBox<Integer>(YearArray);
        yearCombo.setBounds(50+250,270,70,20);

        userNameLable = new JLabel();
        userNameLable.setBounds(50,240,150,20);
        userNameLable.setText("Insert your UserName*");
        userNameLable.setVisible(true);

        userNameText = new JTextArea();
        userNameText.setBounds(50,270,150,40);
        userNameText.setVisible(true);

        password1Lable = new JLabel();
        password1Lable.setBounds(50,310,150,20);
        password1Lable.setText("Insert your Password*");
        password1Lable.setVisible(true);

        password1Text = new JTextArea();
        password1Text.setBounds(50,340,150,40);
        password1Text.setVisible(true);

        password2Lable = new JLabel();
        password2Lable.setBounds(50,390,200,20);
        password2Lable.setText("Insert your Password again*");
        password2Lable.setVisible(true);

        password2Text = new JTextArea();
        password2Text.setBounds(50,420,150,40);
        password2Text.setVisible(true);

        bioLable = new JLabel();
        bioLable.setBounds(300,310,150,20);
        bioLable.setText("Insert Bio");
        bioLable.setVisible(true);

        bioText = new JTextArea();
        bioText.setBounds(300,340,150,40);
        bioText.setVisible(true);

        singUpBotton = new JButton("SingUp!");
        singUpBotton.setText("SingUp!");
        singUpBotton.setFocusable(false);
        singUpBotton.setBounds(300,400,110,50);
        singUpBotton.addActionListener(this);

        this.add(titleLable);
        this.add(firstNameField);
        this.add(lastNameField);
        this.add(firstNameText);
        this.add(lastNameText);
        this.add(emailLable);
        this.add(emailText);
        this.add(phoneLable);
        this.add(phoneText);
        this.add(birthDateLable);
        this.add(birthDateCheck);
        this.add(dayCombo);
        this.add(monthCombo);
        this.add(yearCombo);
        this.add(userNameLable);
        this.add(userNameText);
        this.add(password1Lable);
        this.add(password1Text);
        this.add(password2Lable);
        this.add(password2Text);
        this.add(singUpBotton);
        this.add(bioLable);
        this.add(bioText);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == singUpBotton){
            SignupEvent signupEvent = new SignupEvent(firstNameText.getText(),
                                                        lastNameText.getText(),
                                                        userNameText.getText(),
                                                        password1Text.getText(),
                                                        password2Text.getText(),
                                                        emailText.getText(),
                                                        getDayCombo(),
                                                        getMonthCombo(),
                                                        getYearCombo(),
                                                        phoneText.getText(),
                                                        bioText.getText());
            try {
                signupListener.listen(signupEvent);
            } catch (UserNameStartsWithDigitException userNameStartsWithDigitException) {
                userNameStartsWithDigitException.printStackTrace();
            } catch (EmptyFieldException emptyFieldException) {
                emptyFieldException.printStackTrace();
            } catch (PasswordsNotMatchException passwordsNotMatchException) {
                passwordsNotMatchException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (UsernameExistsException usernameExistsException) {
                usernameExistsException.printStackTrace();
            } catch (EmailExistException emailExistException) {
                emailExistException.printStackTrace();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        }
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

    public String getYearCombo() {
        return String.valueOf(yearCombo.getSelectedItem());
    }

    public String getMonthCombo() {
        return String.valueOf(monthCombo.getSelectedItem());
    }

    public String getDayCombo() {
        return String.valueOf(dayCombo.getSelectedItem());
    }



}
